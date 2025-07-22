package utils;

import db.DBManager;
import java.sql.*;
import exceptions.DatabaseException;

public class Schema_Initializer {
    public static void initialize(DBManager db) throws DatabaseException {
        try{
            createPeopleTable(db);
            createRoomDetailsTable(db);
            createJobDetailsTable(db);
        } catch(SQLException e){
            throw new DatabaseException("Schema initialization failed." + e.getMessage());
        }
    }

    private static void createPeopleTable(DBManager db) throws SQLException, DatabaseException {
        String query = "CREATE TABLE IF NOT EXISTS " + db.getSchema_name() +
                ".people(person_id SERIAL PRIMARY KEY, " +
                "name varchar(55) NOT NULL, " +
                "joined_date DATE, " +
                "image_path BYTEA, " +
                "consent_form BYTEA " +
                ");";
        db.executeUpdate(query);
    }

    private static void createRoomDetailsTable(DBManager db) throws SQLException, DatabaseException {
        String query = "CREATE TABLE IF NOT EXISTS " + db.getSchema_name() +
                ".room_details (room_no SERIAL PRIMARY KEY, " +
                "bed_sharing VARCHAR(20) CHECK (UPPER(bed_sharing) IN ('SINGLE', 'DOUBLE')), " +
                "person1_name VARCHAR(55), " +
                "person2_name VARCHAR(55), " +
                "floor_no INT CHECK(floor_no >= 0), " +
                "person1_id INT, " +
                "person2_id INT, " +
                "FOREIGN KEY(person1_id) REFERENCES " + db.getSchema_name() + ".people(person_id) ON DELETE SET NULL ON UPDATE CASCADE, " +
                "FOREIGN KEY(person2_id) REFERENCES " + db.getSchema_name() + ".people(person_id) ON DELETE SET NULL ON UPDATE CASCADE " +
                ");";
        db.executeUpdate(query);
    }
    private static void createJobDetailsTable(DBManager db) throws SQLException, DatabaseException {
        String query = "CREATE TABLE IF NOT EXISTS " + db.getSchema_name() +
                ".job_details(job_id SERIAL PRIMARY KEY, " +
                "person_name VARCHAR(55) NOT NULL, " +
                "job_name VARCHAR(55) NOT NULL, " +
                "person_id INT, " +
                "FOREIGN KEY(person_id) REFERENCES " + db.getSchema_name() + ".people(person_id) ON DELETE SET NULL ON UPDATE CASCADE " +
                ");";
        db.executeUpdate(query);
    }

}