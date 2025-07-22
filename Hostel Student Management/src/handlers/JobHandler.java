package handlers;

import beans.JobDetails;
import constant.InputConstants;
import constant.QueryConstants;
import db.DBManager;
import exceptions.DatabaseException;
import interfaces.Table_Operations;
import utils.CsvUtil;
import utils.JsonUtil;
import utils.QueryUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class JobHandler implements Table_Operations.InsertDisplayOperations<JobDetails>, Table_Operations.UpdateOperation, Table_Operations.DeleteByIdOperation, Table_Operations.ExportToCsv, Table_Operations.ExportToJson, Table_Operations.DropTable, Table_Operations.TruncateTable {

    @Override
    public void insert(DBManager db, JobDetails job) throws SQLException {
        int personId = QueryUtil.getPersonIdByName(db, job.getPerson_name());
        String query = String.format(QueryConstants.PROMPT_INSERT_JOB_TEMPLATE, db.getSchema_name());
        try (PreparedStatement ps = db.getConnection().prepareStatement(query);) {
            ps.setString(1, job.getPerson_name());
            ps.setString(2, job.getJob_name());
            ps.setInt(3, personId);
            ps.executeUpdate();
            System.out.println("Inserted successfully.");
        }
    }

    @Override
    public void update(DBManager db, Scanner sc) throws SQLException, DatabaseException {
        System.out.println(InputConstants.PROMPT_UPDATE_JOB);
        String column = sc.nextLine();
        System.out.println(InputConstants.PROMPT_NEW_VALUE);
        String newValue = sc.nextLine();
        System.out.println(InputConstants.PROMPT_WHERE_COLUMN);
        String whereCol = sc.nextLine();
        System.out.println(InputConstants.PROMPT_WHERE_VALUE);
        String whereValue = sc.nextLine();

        QueryUtil.executeSafeUpdateByColumn(db, "job_details", column, newValue, whereCol, whereValue);
    }

    @Override
    public void deleteById(DBManager db, int id) throws SQLException {
        QueryUtil.executeDeleteById(db, "job_details", id);
    }

    @Override
    public void display(DBManager db) throws SQLException {
        QueryUtil.displayTable(db, "job_details");
    }

    @Override
    public void exportToCsv(DBManager db) throws SQLException {
        String tableName = "job_details";
        CsvUtil.writeTableToCSV(db, tableName, tableName + ".csv");
    }

    @Override
    public void exportToJson(DBManager db) throws SQLException {
        String tableName = "job_details";
        JsonUtil.writeTableToJson(db, tableName, tableName + ".json");
    }

    @Override
    public void truncateTable(DBManager db) throws SQLException {
        QueryUtil.truncateTable(db, "job_details");
    }

    @Override
    public void dropTable(DBManager db) throws SQLException {
        QueryUtil.dropTable(db, "job_details");
    }
}
