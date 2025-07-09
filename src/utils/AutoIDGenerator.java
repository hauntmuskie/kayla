package utils;

import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Utility class for auto-generating IDs in a reusable and agnostic way
 * Supports different table types with customizable prefixes
 * 
 * @author Elza Kayla
 */
public class AutoIDGenerator {

    private static Connection conn = new DatabaseConnection().connect();

    /**
     * Generates next available ID for a given table and column
     * 
     * @param tableName  The database table name
     * @param columnName The ID column name
     * @param prefix     The prefix for the ID (e.g., "C", "K", "A")
     * @return Next available ID with prefix
     */
    public static String generateNextID(String tableName, String columnName, String prefix) {
        String nextID = prefix + "1"; // Default starting ID

        try {
            String sql = "SELECT " + columnName + " FROM " + tableName +
                    " WHERE " + columnName + " LIKE '" + prefix + "%' " +
                    " ORDER BY CAST(SUBSTRING(" + columnName + ", " + (prefix.length() + 1)
                    + ") AS UNSIGNED) DESC LIMIT 1";

            PreparedStatement stat = conn.prepareStatement(sql);
            ResultSet rs = stat.executeQuery();

            if (rs.next()) {
                String lastID = rs.getString(columnName);
                if (lastID != null && lastID.startsWith(prefix)) {
                    try {
                        int num = Integer.parseInt(lastID.substring(prefix.length()));
                        nextID = prefix + (num + 1);
                    } catch (NumberFormatException e) {
                        nextID = prefix + "1";
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error generating ID: " + e.getMessage());
            nextID = prefix + "1";
        }

        return nextID;
    }

    /**
     * Generates next available ID for siswa table
     * 
     * @return Next available siswa ID (C1, C2, C3...)
     */
    public static String generateSiswaID() {
        return generateNextID("siswa", "id_siswa", "C");
    }

    /**
     * Generates next available ID for kriteria table
     * 
     * @return Next available kriteria ID (K1, K2, K3...)
     */
    public static String generateKriteriaID() {
        return generateNextID("kriteria", "kode_kriteria", "K");
    }

    /**
     * Generates next available ID for alternatif table
     * 
     * @return Next available alternatif ID (A1, A2, A3...)
     */
    public static String generateAlternatifID() {
        return generateNextID("alternatif", "id_siswa", "A");
    }

    /**
     * Checks if an ID already exists in a table
     * 
     * @param tableName  The database table name
     * @param columnName The ID column name
     * @param id         The ID to check
     * @return true if ID exists, false otherwise
     */
    public static boolean idExists(String tableName, String columnName, String id) {
        try {
            String sql = "SELECT COUNT(*) FROM " + tableName + " WHERE " + columnName + " = ?";
            PreparedStatement stat = conn.prepareStatement(sql);
            stat.setString(1, id);
            ResultSet rs = stat.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error checking ID existence: " + e.getMessage());
        }

        return false;
    }

    /**
     * Generates a unique ID ensuring no conflicts
     * 
     * @param tableName  The database table name
     * @param columnName The ID column name
     * @param prefix     The prefix for the ID
     * @return Guaranteed unique ID
     */
    public static String generateUniqueID(String tableName, String columnName, String prefix) {
        String id = generateNextID(tableName, columnName, prefix);

        // Extra safety check - if ID somehow exists, increment until unique
        int counter = 1;
        String baseId = id;
        while (idExists(tableName, columnName, id)) {
            try {
                int num = Integer.parseInt(baseId.substring(prefix.length()));
                id = prefix + (num + counter);
                counter++;
            } catch (NumberFormatException e) {
                id = prefix + counter;
                counter++;
            }
        }

        return id;
    }
}
