package handlers;

import constant.QueryConstants;
import db.DBManager;
import exceptions.DatabaseException;
import interfaces.Table_Operations;
import beans.RoomDetails;
import constant.InputConstants;
import utils.CsvUtil;
import utils.JsonUtil;
import java.sql.*;
import utils.QueryUtil;
import java.util.Scanner;

public class RoomHandler implements Table_Operations.InsertDisplayOperations<RoomDetails>, Table_Operations.UpdateOperation, Table_Operations.DeleteByIdOperation, Table_Operations.ExportToCsv, Table_Operations.ExportToJson, Table_Operations.DropTable, Table_Operations.TruncateTable {

    @Override
    public void insert(DBManager db, RoomDetails rd) throws SQLException {

        String query = String.format(QueryConstants.PROMPT_INSERT_ROOM_TEMPLATE, db.getSchema_name());
        try (PreparedStatement ps = db.getConnection().prepareStatement(query);) {
            ps.setString(1, rd.getBed_sharing().toUpperCase());
            ps.setString(2, rd.getPerson1_name());
            ps.setString(3, rd.getPerson2_name());
            ps.setInt(4, rd.getFloor_no());
            if (rd.getPerson1_id() != null) {
                ps.setInt(5, rd.getPerson1_id());
            }else {
                ps.setNull(5, java.sql.Types.INTEGER);
            }
            if (rd.getPerson2_id() != null) {
                ps.setInt(6, rd.getPerson2_id());
            }else {
                ps.setNull(6, java.sql.Types.INTEGER);
            }
            ps.executeUpdate();
        }
    }

    @Override
    public void update(DBManager db,Scanner sc) throws SQLException, DatabaseException {
        System.out.println(InputConstants.PROMPT_UPDATE_ROOM);
        String column = sc.nextLine();
        System.out.println(InputConstants.PROMPT_NEW_VALUE);
        String newValue = sc.nextLine();
        System.out.println(InputConstants.PROMPT_WHERE_COLUMN);
        String whereCol = sc.nextLine();
        System.out.println(InputConstants.PROMPT_WHERE_VALUE);
        String whereValue = sc.nextLine();

        QueryUtil.executeSafeUpdateByColumn(db, "room_details", column, newValue, whereCol, whereValue);
    }

    @Override
    public void deleteById(DBManager db, int id) throws SQLException {
        QueryUtil.executeSafeDeleteById(db, "room_details", id);
    }

    @Override
    public void display(DBManager db) throws SQLException {
        QueryUtil.displayTable(db, "room_details");
    }

    @Override
    public void exportToCsv(DBManager db) throws SQLException {
        String tableName = "room_details";
        CsvUtil.writeTableToCSV(db, tableName, tableName + ".csv");
    }

    @Override
    public void exportToJson(DBManager db) throws SQLException {
        String tableName = "room_details";
        JsonUtil.writeTableToJson(db, tableName, tableName + ".json");
    }

    @Override
    public void truncateTable(DBManager db) throws SQLException {
        QueryUtil.truncateTable(db, "room_details");
    }

    @Override
    public void dropTable(DBManager db) throws SQLException {
        QueryUtil.dropTable(db, "room_details");
    }
}
