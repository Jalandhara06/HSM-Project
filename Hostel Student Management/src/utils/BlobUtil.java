package utils;

import constant.QueryConstants;
import db.DBManager;
import org.apache.tika.Tika;
import java.io.*;
import java.sql.*;

public class BlobUtil {

    private static final Tika tika = new Tika();

    public static void exportBlobs(DBManager db, String tableName, String destinationFolder) {
        String query = String.format(QueryConstants.SELECT_QUERY_TEMPLATE, db.getSchema_name(), tableName);

        try (Connection conn = db.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            File dir = new File(destinationFolder);
            if (!dir.exists()) dir.mkdirs();

            while (rs.next()) {
                int id = rs.getInt(1); // assuming person_id is the first column

                for (int i = 1; i <= colCount; i++) {
                    int type = meta.getColumnType(i);
                    String columnName = meta.getColumnName(i);

                    if (type == Types.BLOB || type == Types.BINARY || type == Types.VARBINARY || type == Types.LONGVARBINARY) {
                        byte[] blobData = rs.getBytes(i);
                        if (blobData != null && blobData.length > 0) {
                            String mimeType = tika.detect(blobData);
                            String extension = getExtensionFromMimeType(mimeType);
                            if (extension == null) extension = "bin";

                            String filename = destinationFolder + "/" + tableName + "_" + columnName + "_" + id + "." + extension;
                            try (FileOutputStream fos = new FileOutputStream(filename)) {
                                fos.write(blobData);
                                System.out.println("Saved: " + new File(filename).getAbsolutePath());
                            } catch (IOException e) {
                                System.err.println("Failed writing file: " + e.getMessage());
                            }
                        }
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }

    private static String getExtensionFromMimeType(String mimeType) {
        switch (mimeType) {
            case "image/jpeg": return "jpg";
            case "image/png": return "png";
            case "image/gif": return "gif";
            case "application/pdf": return "pdf";
            case "image/bmp": return "bmp";
            case "text/plain": return "txt";
            case "application/msword": return "doc";
            case "application/vnd.openxmlformats-officedocument.wordprocessingml.document": return "docx";
            case "application/vnd.ms-excel": return "xls";
            case "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet": return "xlsx";
            default: return null;
        }
    }

}

