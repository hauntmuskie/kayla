/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import com.placeholder.PlaceHolder;
import database.DatabaseConnection;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Base class for popup windows to reduce code duplication and improve resource
 * management
 * 
 * @author Elza Kayla
 */
public abstract class BasePopup extends JFrame {

    protected Connection conn;
    protected DefaultTableModel tabmode;
    protected PlaceHolder pl;

    public BasePopup() {
        conn = new DatabaseConnection().connect();
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Add window listener to properly close resources
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                closeResources();
            }
        });
    }

    /**
     * Close database connection and other resources
     */
    protected void closeResources() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                // Log error but don't show to user
                System.err.println("Error closing database connection: " + e.getMessage());
            }
        }
    }

    /**
     * Execute a search query with parameters to prevent SQL injection
     */
    protected ResultSet executeSearchQuery(String sql, String... parameters) throws SQLException {
        PreparedStatement pst = conn.prepareStatement(sql);
        for (int i = 0; i < parameters.length; i++) {
            pst.setString(i + 1, parameters[i]);
        }
        return pst.executeQuery();
    }

    /**
     * Show error message in a consistent way
     */
    protected void showError(String message, Exception e) {
        String errorMsg = message;
        if (e != null) {
            errorMsg += ": " + e.getMessage();
        }
        JOptionPane.showMessageDialog(this, errorMsg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Set up placeholder text for search field
     */
    protected void setupSearchPlaceholder(JTextField searchField, String placeholderText) {
        pl = new PlaceHolder(searchField, placeholderText);
    }

    /**
     * Handle Enter key press for search
     */
    protected void handleSearchKeyPress(KeyEvent evt) {
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            performSearch();
        }
    }

    /**
     * Override this method to implement search functionality
     */
    protected abstract void performSearch();

    /**
     * Override this method to load initial data
     */
    protected abstract void loadData();

    /**
     * Handle back button click
     */
    protected void handleBackButton() {
        this.dispose();
    }

    @Override
    public void dispose() {
        closeResources();
        super.dispose();
    }
}
