package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import db.DBManager;
import java.io.FileWriter;
import java.sql.*;
import java.util.*;

public class JsonUtil {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    public static void writeTableToJson(DBManager db, String tableName, String filePath) {
        String query = String.format(constant.QueryConstants.SELECT_QUERY_TEMPLATE, db.getSchema_name(), tableName);

        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query);
             FileWriter writer = new FileWriter(filePath);) {

            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            // Identify non-BLOB columns
            List<Integer> validColumnIndexes = new ArrayList<>();
            List<String> validColumnNames = new ArrayList<>();

            for (int i = 1; i <= colCount; i++) {
                int columnType = meta.getColumnType(i);
                if (columnType != Types.BLOB && columnType != Types.BINARY && columnType != Types.VARBINARY && columnType != Types.LONGVARBINARY) {
                    validColumnIndexes.add(i);
                    validColumnNames.add(meta.getColumnName(i));
                }
            }

            List<Map<String, Object>> rows = new ArrayList<>();

            while (rs.next()) {
                Map<String, Object> row = new LinkedHashMap<>();
                for (int i = 0; i < validColumnIndexes.size(); i++) {
                    int colIndex = validColumnIndexes.get(i);
                    row.put(validColumnNames.get(i), rs.getObject(colIndex));
                }
                rows.add(row);
            }

            //Gson gson = new Gson();
            writer.write(gson.toJson(rows));
            System.out.println("Exported " + tableName + " to JSON at: " + filePath);

        } catch (Exception e) {
            System.out.println("JSON export failed for " + tableName + ": " + e.getMessage());
        }
    }

}