package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь\

        UserService userService = new UserServiceImpl();
        try{
            userService.createUsersTable();

            userService.saveUser("Ivan", "Ivanov", (byte) 25);

            userService.saveUser("Petr", "Petrov", (byte) 30);

            userService.saveUser("Sergey", "Sergeev", (byte) 35);

            userService.saveUser("Anna", "Karenina", (byte) 28);

            List<User> users = userService.getAllUsers();
            for(User user : users){
                System.out.println(user);
            }

            userService.cleanUsersTable();

            userService.dropUsersTable();

        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
