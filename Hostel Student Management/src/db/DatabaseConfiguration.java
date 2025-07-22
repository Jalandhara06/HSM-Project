package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConfiguration {

    private static String url;
    private static String username;
    private static String password;

    static{
        Properties prop = new Properties();

        try (FileInputStream input = new FileInputStream("dbconfig.properties");){
            prop.load(input);
            url = prop.getProperty("db.url");
            username = prop.getProperty("db.username");
            password = prop.getProperty("db.password");
        } catch (IOException ex) {
            throw new ExceptionInInitializerError(ex);
            //System.out.println("Failed to load Database Configuration" + ex.getMessage()); //replace with exception.
        }
    }

    public static String getUrl() {
        return url;
    }
    public static String getUsername() {
        return username;
    }
    public static String getPassword() {
        return password;
    }

    public static Connection getConnection() throws SQLException{
        if (url == null || username == null || password == null){
            throw new IllegalStateException("Missing Database configuration.");
        }
        return DriverManager.getConnection(url, username, password);
    }

}