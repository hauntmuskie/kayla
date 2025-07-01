/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Utility class for managing MySQL database connections.
 */
public class DatabaseConnection {
    private Connection connection;

    /**
     * Establishes a connection to the database.
     *
     * @return the database connection, or null if the connection failed
     */
    public Connection connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.err.println("MySQL JDBC Driver not found: " + ex.getMessage());
            return null;
        }
        String url = "jdbc:mysql://localhost/skripsikurir";
        try {
            connection = DriverManager.getConnection(url, "root", "");
        } catch (SQLException ex) {
            System.err.println("Failed to connect to database: " + ex.getMessage());
            return null;
        }
        return connection;
    }
}
