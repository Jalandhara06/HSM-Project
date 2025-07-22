package handlers;

import exceptions.DatabaseException;
import interfaces.Table_Operations;
import beans.PeopleDetails;
import db.DBManager;
import constant.QueryConstants;
import utils.BlobUtil;
import utils.CsvUtil;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
import utils.JsonUtil;
import constant.InputConstants;
import utils.QueryUtil;

public class PeopleHandler implements Table_Operations.InsertDisplayOperations<PeopleDetails>, Table_Operations.UpdateOperation, Table_Operations.DeleteByIdOperation, Table_Operations.ExportToCsv, Table_Operations.ExportToJson, Table_Operations.DropTable, Table_Operations.TruncateTable{

    @Override
    public void insert(DBManager db, PeopleDetails p) throws SQLException {
        String query =  String.format(QueryConstants.PROMPT_INSERT_PEOPLE_TEMPLATE, db.getSchema_name());
        try (PreparedStatement ps = db.getConnection().prepareStatement(query);
             FileInputStream image = new FileInputStream(p.getImage_path());
             FileInputStream form = new FileInputStream(p.getConsent_form());) {
            ps.setString(1, p.getName());
            ps.setDate(2, Date.valueOf(p.getJoined_date()));
            ps.setBinaryStream(3, image, image.available());
            ps.setBinaryStream(4, form, form.available());
            ps.executeUpdate();
        }catch (FileNotFoundException e){
            System.out.println("File not found" + e.getMessage());
        }catch (IOException e){
            System.out.println("Error reading file" + e.getMessage());
        }catch (SQLException e){
            System.out.println("SQL Error" + e.getMessage());
        }
    }

    @Override
    public void update(DBManager db, Scanner sc) throws SQLException, DatabaseException {
        System.out.println(InputConstants.PROMPT_UPDATE_PEOPLE);
        String column = sc.nextLine();
        System.out.println(InputConstants.PROMPT_NEW_VALUE);
        String newValue = sc.nextLine();
        System.out.println(InputConstants.PROMPT_NAME_MATCH);
        String whereCol = sc.nextLine();
        System.out.println(InputConstants.PROMPT_WHERE_VALUE);
        String whereValue = sc.nextLine();

        QueryUtil.executeSafeUpdateByColumn(db,"people",column,newValue,whereCol,whereValue);
    }

    @Override
    public void deleteById(DBManager db, int id) throws SQLException {
        QueryUtil.executeDeleteById(db, "people", id);
    }

    @Override
    public void display(DBManager db) throws SQLException {
        QueryUtil.displayTable(db, "people");
        BlobUtil.exportBlobs(db, "people", "/home/adminp3s/IdeaProjects/Hostel Student Management");
    }

    @Override
    public void exportToCsv(DBManager db) throws SQLException {
        String tableName = "people";
        CsvUtil.writeTableToCSV(db, tableName, tableName + ".csv");
    }

    @Override
    public void exportToJson(DBManager db) throws SQLException {
        String tableName = "people";
        JsonUtil.writeTableToJson(db, tableName, tableName + ".json");
    }

    @Override
    public void truncateTable(DBManager db) throws SQLException {
        QueryUtil.truncateTable(db, "people");
    }

    @Override
    public void dropTable(DBManager db) throws SQLException {
        QueryUtil.dropTable(db, "people");
    }
}
