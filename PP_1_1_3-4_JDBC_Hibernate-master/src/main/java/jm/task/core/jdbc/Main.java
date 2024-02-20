package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;

import static jm.task.core.jdbc.util.Util.closeConnection;
import static jm.task.core.jdbc.util.Util.closeSession;


public class Main {
    private final static UserServiceImpl user = new UserServiceImpl();

    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Ivan", "Ivanov", (byte) 20);
        userService.saveUser("Petr", "Petrov", (byte) 24);
        userService.saveUser("Pavel", "Pavlov", (byte) 33);
        userService.saveUser("Lesha", "Leshov", (byte) 54);
        System.out.println(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
        closeSession();






    }
}
