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
    private static final String URL = "jdbc:mysql://localhost/penilaian_siswa_berprestasi";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    /**
     * Establishes a connection to the database.
     *
     * @return the database connection, or null if the connection failed
     */
    public Connection connect() {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            System.err.println("MySQL JDBC Driver not found: " + ex.getMessage());
            return null;
        }
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            System.err.println("Failed to connect to database: " + ex.getMessage());
            return null;
        }
        return connection;
    }

    /**
     * Closes the database connection if it is open.
     */
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.err.println("Failed to close database connection: " + ex.getMessage());
            }
        }
    }
}
