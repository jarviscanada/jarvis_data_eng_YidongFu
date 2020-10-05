package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.Properties;

public class DatabaseConnectManager {
    private final String url;
    private final Properties properties;

    public DatabaseConnectManager(String host, String databaseName,
                                  String username, String password){
        this.url = "jdbc:postgresql://"+host+"/"+databaseName;
        this.properties = new Properties();
        this.properties.setProperty("User", username);
        this.properties.setProperty("password", password);
    }

    public Connection getConnection() throws SQLException{
        return DriverManager.getConnection(this.url, this.properties);
    }
}
