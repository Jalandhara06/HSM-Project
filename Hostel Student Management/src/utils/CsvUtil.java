package utils;

import db.DBManager;
import java.io.FileWriter;
import java.io.IOException;
import com.opencsv.CSVWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CsvUtil {

    public static void writeTableToCSV(DBManager db, String tableName, String filePath) {
        String query = String.format(constant.QueryConstants.SELECT_QUERY_TEMPLATE, db.getSchema_name(), tableName);

        try (Statement stmt = db.getConnection().createStatement();
             ResultSet rs = stmt.executeQuery(query);
             CSVWriter writer = new CSVWriter (new FileWriter(filePath));) {

            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            List<Integer> validColumnIndexes = new ArrayList<>();
            List<String> headerList = new ArrayList<>();

            // Write header
            for (int i = 1; i <= colCount; i++) {
                int columnType = meta.getColumnType(i);
                if (columnType != Types.BLOB && columnType != Types.BINARY && columnType != Types.VARBINARY && columnType != Types.LONGVARBINARY) {
                    validColumnIndexes.add(i);
                    headerList.add(meta.getColumnName(i));
                }
            }
            writer.writeNext(headerList.toArray(new String[0]));

            // Write rows
            while (rs.next()) {
                String[] row = new String[validColumnIndexes.size()];
                for (int i = 0; i < validColumnIndexes.size(); i++) {
                    int colIndex = validColumnIndexes.get(i);
                    String val = rs.getString(colIndex);
                    row[i] = val != null ? val : "";
                }
                writer.writeNext(row);
            }
            System.out.println("Exported " + tableName + " to CSV at: " + filePath);

        } catch (IOException | SQLException e) {
            System.out.println("CSV export failed for " + tableName + ": " + e.getMessage());
        }
    }

}
