/*
 * Popup Enhancement Utilities
 * Common functionality for all popup windows
 */
package utils;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Utility class for common popup functionality
 */
public class PopupUtils {

    /**
     * Add ESC key listener to close popup window
     */
    public static void addEscapeKeyListener(JFrame popup) {
        popup.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    popup.dispose();
                }
            }
        });
        popup.setFocusable(true);
    }

    /**
     * Safely close database connection
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }

    /**
     * Safely close prepared statement
     */
    public static void closeStatement(PreparedStatement pst) {
        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException e) {
                System.err.println("Error closing statement: " + e.getMessage());
            }
        }
    }

    /**
     * Show standardized error message
     */
    public static void showError(JFrame parent, String message, Exception e) {
        String errorMsg = message;
        if (e != null) {
            errorMsg += ": " + e.getMessage();
        }
        JOptionPane.showMessageDialog(parent, errorMsg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Validate table selection
     */
    public static boolean isValidSelection(JTable table) {
        return table.getSelectedRow() >= 0;
    }

    /**
     * Create search pattern for LIKE queries
     */
    public static String createSearchPattern(String searchText) {
        return "%" + (searchText != null ? searchText.trim() : "") + "%";
    }
}
