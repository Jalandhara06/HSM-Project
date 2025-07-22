package handlers;

import beans.JobDetails;
import beans.PeopleDetails;
import beans.RoomDetails;
import db.DBManager;
import constant.InputConstants;
import exceptions.DatabaseException;
import utils.QueryUtil;
import java.sql.SQLException;
import java.util.Scanner;

public class MasterHandler {

    private final Scanner sc ;

    public MasterHandler(Scanner sc) {
        this.sc = sc;
    }

    public void insert(DBManager db) throws SQLException {
        System.out.println(InputConstants.PROMPT_TABLE_PERSON);
        PeopleDetails person = getPersonInput();
        PeopleHandler ph  = new PeopleHandler();
        ph.insert(db, person);

        System.out.println(InputConstants.PROMPT_TABLE_ROOM);
        RoomDetails room = getRoomInput(db);
        RoomHandler rh = new RoomHandler();
        rh.insert(db, room);

        System.out.println(InputConstants.PROMPT_TABLE_JOB);
        JobDetails job = getJobInput();
        JobHandler jh = new JobHandler();
        jh.insert(db, job);
    }

    public void update(DBManager db) throws SQLException, DatabaseException {
        System.out.print(InputConstants.PROMPT_TABLENAME_UPDATE);
        String table = sc.nextLine().trim().toLowerCase();
        PeopleHandler ph  = new PeopleHandler();
        RoomHandler rh = new RoomHandler();
        JobHandler jh = new JobHandler();

        switch (table) {
            case "people" -> ph.update(db, sc);
            case "room_details" -> rh.update(db, sc);
            case "job_details" -> jh.update(db, sc);
            default -> System.out.println("Invalid table name.");
        }
    }

    public void delete(DBManager db) throws SQLException {
        System.out.print(InputConstants.PROMPT_DELETE);
        int id = Integer.parseInt(sc.nextLine());
        PeopleHandler ph  = new PeopleHandler();
        RoomHandler rh = new RoomHandler();
        JobHandler jh = new JobHandler();

        jh.deleteById(db, id);
        rh.deleteById(db, id);
        ph.deleteById(db, id);

    }

    public void displayTable(DBManager db) throws SQLException {
        System.out.print(InputConstants.PROMPT_DISPLAY_TABLE);
        String table = sc.nextLine().trim().toLowerCase();

        switch (table) {
            case "people" -> new PeopleHandler().display(db);
            case "room_details" -> new RoomHandler().display(db);
            case "job_details" -> new JobHandler().display(db);
            default -> System.out.println("Invalid table name.");
        }
    }

    public void exportToCsv(DBManager db) throws SQLException {
        new PeopleHandler().exportToCsv(db);
        new RoomHandler().exportToCsv(db);
        new JobHandler().exportToCsv(db);
        System.out.println("Exported all tables to CSV files.");
    }

    public void exportToJson(DBManager db) throws SQLException {
        new PeopleHandler().exportToJson(db);
        new RoomHandler().exportToJson(db);
        new JobHandler().exportToJson(db);
        System.out.println("Exported all tables to JSON files.");
    }

    public void dropTable(DBManager db) throws SQLException {
        System.out.print(InputConstants.PROMPT_DROP_TABLE);
        String tableName = sc.nextLine().trim().toLowerCase();

        switch (tableName) {
            case "people" -> new PeopleHandler().dropTable(db);
            case "room_details" -> new RoomHandler().dropTable(db);
            case "job_details" -> new JobHandler().dropTable(db);
            default -> System.out.println("Invalid table name.");
        }
    }

    public void truncateTable(DBManager db) throws SQLException {
        System.out.print(InputConstants.PROMPT_TRUNCATE_TABLE);
        String table = sc.nextLine().trim().toLowerCase();

        switch (table) {
            case "people" -> new PeopleHandler().truncateTable(db);
            case "room_details" -> new RoomHandler().truncateTable(db);
            case "job_details" -> new JobHandler().truncateTable(db);
            default -> System.out.println("Invalid table name.");
        }
    }


    private PeopleDetails getPersonInput() {
        System.out.print(InputConstants.PROMPT_NAME);
        String name = sc.nextLine().trim();
        System.out.print(InputConstants.PROMPT_JOINED_DATE);
        String joined = sc.nextLine();
        System.out.print(InputConstants.PROMPT_IMAGE);
        String image = sc.nextLine();
        System.out.print(InputConstants.PROMPT_CONSENT_FORM);
        String consent = sc.nextLine();

        return new PeopleDetails(name, joined, image, consent);
    }

    private RoomDetails getRoomInput(DBManager db) throws SQLException {
        System.out.print(InputConstants.PROMPT_NAME1);
        String p1 = sc.nextLine().trim();
        Integer p1Id = null;
        try{
            p1Id = QueryUtil.getPersonIdByName(db, p1);
            if(p1Id == null){
                System.out.println("Warning: person1_name '" + p1 + "' not found.");
            }
        }catch (Exception e){
            System.out.println("Error: fetching person1_id: " + e.getMessage());
        }
        System.out.print(InputConstants.PROMPT_BED_SHARING);
        String bed = sc.nextLine().trim().toUpperCase();
        String p2 = null;
        Integer p2Id = null;
        if("DOUBLE".equalsIgnoreCase(bed)){
            System.out.print(InputConstants.PROMPT_NAME2);
            p2 = sc.nextLine().trim();
            try {
                p2Id = QueryUtil.getPersonIdByName(db, p2);
                if(p2Id == null){
                    System.out.println("Warning: person2_name '" + p2 + "' not found.");
                }
            } catch (Exception e) {
                System.out.println("Error: fetching person2_id: " + e.getMessage());
            }
        }
        System.out.print(InputConstants.PROMPT_FLOOR_NO);
        int floor = Integer.parseInt(sc.nextLine());
        RoomDetails room = new RoomDetails(bed, p1, p2, floor);
        room.setPerson1_id(p1Id);
        room.setPerson2_id(p2Id);
        return room;
    }

    private JobDetails getJobInput() {
        System.out.print(InputConstants.PROMPT_NAME);
        String pname = sc.nextLine().trim();
        System.out.print(InputConstants.PROMPT_JOB_NAME);
        String job = sc.nextLine();

        return new JobDetails(pname, job);
    }

}
