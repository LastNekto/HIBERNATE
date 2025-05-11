package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection connection = Util.getConnection(); // коннектимся

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS public.users (" +
                "id SERIAL PRIMARY KEY, " +
                "name VARCHAR(255), " +
                "last_name VARCHAR(255), " +
                "age INT)";

        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql); // выполняем запрос создания
        }catch (SQLException e) {
            e.printStackTrace(); // логируем
        }
    }

    public void dropUsersTable() {
        try(Statement statement = connection.createStatement()) {
            statement.executeUpdate("DROP TABLE IF EXISTS public.users"); // безопасное удаление!
        }catch (SQLException e) {
            e.printStackTrace(); // логируем
        }

    }

    public void saveUser(String name, String lastName, byte age) {

        String sql = "INSERT INTO public.users (name, last_name, age) VALUES (?, ?, ?)";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name); // замена 1 ? и тд
            preparedStatement.setString(2, lastName);
            preparedStatement.setInt(3, age);

            preparedStatement.executeUpdate(); // выполняет запрос добавления юзи
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM public.users WHERE id = ?";

        try(PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.setLong(1, id); // подставляет вместо параметра -  ? id юзи
            preparedStatement.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();

        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM public.users")) { // запрос выглядит как выбрать данные все столбцы из таблицы юзи (* Значит все столбцы из строки таблицы юзи) executeUpdate возвращает int

            while (resultSet.next()) { // прогоняем всех юзи из бд (если resultSet выдаст false то строки закончатся)
                User user = new User();
                user.setId(resultSet.getLong("id")); // извлекаем данные из resultSet получаем данные в нужном нам формате тут long
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setAge(resultSet.getByte("age"));
                users.add(user);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return users;
    }

    public void cleanUsersTable() {

        String sql = "DELETE FROM public.users";

        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(sql);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
