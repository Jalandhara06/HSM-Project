package utils;

import constant.QueryConstants;
import db.DBManager;
import exceptions.DatabaseException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.time.LocalDate;

public class QueryUtil {

    public static void executeSafeUpdate(DBManager db, String table, String column, String newValue, String name) throws DatabaseException {
        if (!QueryConstants.ALLOWED_UPDATE_COLUMNS.contains(column)) {
            throw new IllegalArgumentException("Unsafe or invalid column name: " + column);
        }

        String query = String.format(QueryConstants.UPDATE_QUERY_TEMPLATE, db.getSchema_name(), table, column);

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);) {

            if (column.equals("joined_date")) {
                LocalDate date = LocalDate.parse(newValue); // "2025-12-12"
                ps.setDate(1, java.sql.Date.valueOf(date));
            } else {
                ps.setString(1, newValue);
            }

            ps.setString(2, name);

            int updated = ps.executeUpdate();
            if (updated == 0) {
                System.out.println("No records updated.");
            } else {
                System.out.println("Updated " + updated + " record(s).");
            }

        } catch (SQLException e) {
            throw new DatabaseException("Update failed: " + e.getMessage());
        }
    }

    public static void executeDeleteById(DBManager db, String tableName, int id) {
        String query = String.format(QueryConstants.DELETE_QUERY_TEMPLATE, db.getSchema_name(), tableName);
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " record(s) from " + tableName + " where person_id = '" + id + "'.");

        } catch (SQLException e) {
            throw new RuntimeException("Delete operation failed: " + e.getMessage(), e);
        }
    }

    public static void executeSafeUpdateByColumn(DBManager db, String tableName, String setColumn, String newValue, String whereColumn, String whereValue) throws DatabaseException {
        if (!QueryConstants.ALLOWED_UPDATE_COLUMNS.contains(setColumn)) {
            throw new IllegalArgumentException("Unsafe SET column: " + setColumn);
        }

        String query = String.format(QueryConstants.UPDATE_QUERY_TEMPLATE1, db.getSchema_name(), tableName, setColumn, whereColumn);

        try (Connection conn = db.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);) {

            if (setColumn.equalsIgnoreCase("image_path") || setColumn.equalsIgnoreCase("consent_form")) {
                try {
                    byte[] imageBytes = Files.readAllBytes(Paths.get(newValue));
                    ps.setBytes(1, imageBytes);
                } catch (IOException e) {
                    throw new DatabaseException("Failed to read image file: " + e.getMessage());
                }
            } else if (setColumn.equals("joined_date")) {
                LocalDate date = LocalDate.parse(newValue); // "2025-12-12"
                ps.setDate(1, java.sql.Date.valueOf(date));
            } else if(isNumeric(newValue)){
                ps.setInt(1, Integer.parseInt(newValue));
            }else{
                ps.setString(1, newValue);
            }

            ps.setString(2, whereValue);

            int updated = ps.executeUpdate();
            System.out.println("Updated " + updated + " record(s).");

        } catch (SQLException e) {
            throw new DatabaseException("Update by column failed: " + e.getMessage());
        }
    }

    public static void executeSafeDeleteById(DBManager db, String tableName, int id) {
        String query = String.format(QueryConstants.DELETE_QUERY_TEMPLATE1, db.getSchema_name(), tableName);
        try (Connection conn = db.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Deleted " + rowsAffected + " record(s) from " + tableName + " where person1_id = '" + id + "'.");

        } catch (SQLException e) {
            throw new RuntimeException("Delete operation failed: " + e.getMessage(), e);
        }
    }

    private static boolean isNumeric(String str) {
        if (str == null) return false;
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void displayTable(DBManager db, String tableName) {
        String query = String.format(QueryConstants.SELECT_QUERY_TEMPLATE, db.getSchema_name(), tableName);
        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            // Determine BLOB columns
            boolean[] isBlob = new boolean[columnCount + 1]; // 1-based indexing
            for (int i = 1; i <= columnCount; i++) {
                int columnType = metaData.getColumnType(i);
                isBlob[i] = (columnType == Types.BLOB || columnType == Types.BINARY || columnType == Types.VARBINARY || columnType == Types.LONGVARBINARY);
            }

            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    if (!isBlob[i]) {
                        String columnName = metaData.getColumnName(i);
                        System.out.print(columnName + ": " + rs.getString(i) + " | ");
                    }
                }
                System.out.println();
                System.out.println("-----------------------------------------");
            }
        }catch (SQLException e) {
            System.out.println("Error displaying table '" + tableName + "': " + e.getMessage());
        }
    }

    public static int getPersonIdByName(DBManager db, String name) throws SQLException {
        String query = String.format(QueryConstants.SELECT_ID_QUERY_TEMPLATE, db.getSchema_name());
        try (PreparedStatement ps = db.getConnection().prepareStatement(query);) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("person_id");
                } else {
                    throw new SQLException("Person with name '" + name + "' not found.");
                }
            }
        }
    }

    public static void dropTable(DBManager db, String tableName) throws SQLException {
        String query = String.format(QueryConstants.PROMPT_DROP_QUERY_TEMPLATE, db.getSchema_name(), tableName);
        try (Connection conn = db.getConnection();
        Statement stmt = conn.createStatement();){
            int i = stmt.executeUpdate(query);
            if (i == 0) {
                System.out.println("Dropped table " + tableName);
                System.out.println(" If you want to do Insert operation now rerun the program and do insert.");
            }
        }catch (SQLException e) {
            System.out.println("Error dropping table '" + tableName + "': " + e.getMessage());
        }
    }

    public static void truncateTable(DBManager db, String tableName) throws SQLException {
        String query = String.format(QueryConstants.PROMPT_TRUNCATE_QUERY_TEMPLATE, db.getSchema_name(), tableName);
        try (Connection conn = db.getConnection();
        Statement stmt = conn.createStatement();){
            int i = stmt.executeUpdate(query);
            if (i == 0) {
                System.out.println("Truncated table " + tableName);
            }
        }catch (SQLException e) {
            System.out.println("Error truncating table '" + tableName + "': " + e.getMessage());
        }
    }

}