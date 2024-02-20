package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;
import java.util.Properties;

public class Util {
    private static final String NAME_USER = "root";
    private static final String PASSWORD = "Wthrjdm8412rfh.";
    private static final String URL = "jdbc:mysql://localhost:3306/data_base";

    private static Connection connection;




    public static String getURL() {
        return URL;
    }

    public static String getPassword() {
        return PASSWORD;
    }

    public static String getNameUser() {
        return NAME_USER;
    }

    public static Connection getConnection() {


        try {
            connection = DriverManager.getConnection(getURL(), getNameUser(), getPassword());
            System.out.println("Соединение с базой данных установленно");
        } catch (SQLException e) {
            System.out.println("Случился анлак и вы не подключились");
            e.printStackTrace();
        }

        return connection;
    }

    private static SessionFactory sessionFactory;

    public static SessionFactory getSession() {

            try {
                Configuration configuration = new Configuration();
                Properties properties = new Properties();

                properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                properties.put(Environment.URL, URL);
                properties.put(Environment.USER, NAME_USER);
                properties.put(Environment.PASS, PASSWORD);
                properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
                properties.put(Environment.SHOW_SQL, "true");
                properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                properties.put(Environment.HBM2DDL_AUTO, "");

                configuration.setProperties(properties);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Throwable e) {
                e.printStackTrace();
            }

        return sessionFactory;
    }
    public static void closeSession() {
        try {
            if (sessionFactory != null) {
                sessionFactory.close();
            }
        } catch (HibernateException e) {
            e.printStackTrace();
        }
    }

    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Соединение с базой данных закрыто");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


