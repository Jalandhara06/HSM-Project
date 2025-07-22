import constant.InputConstants;
import db.DBManager;
import db.DatabaseConfiguration;
import exceptions.DatabaseException;
import handlers.*;
import utils.Schema_Initializer;
import java.sql.SQLException;
import java.sql.Connection;
import java.util.*;

public class Main {

    public static void main(String[] args) throws DatabaseException{

        try (Scanner sc = new Scanner(System.in);
             Connection conn = DatabaseConfiguration.getConnection();) {

            DBManager db = new DBManager(conn, InputConstants.DB_SCHEMA);
            Schema_Initializer.initialize(db);
            MasterHandler mh = new MasterHandler(sc);

            boolean input = true;

            while (input) {
                System.out.println("\n-------MENU-------");
                System.out.println("1. Insert");
                System.out.println("2. Update");
                System.out.println("3. Delete");
                System.out.println("4. Display");
                System.out.println("5. Export to CSV");
                System.out.println("6. Export to JSON");
                System.out.println("7. Drop");
                System.out.println("8.Truncate");
                System.out.println("0. Exit");
                System.out.println("--------------------");
                System.out.println("Enter your choice: ");

                int choice = Integer.parseInt(sc.nextLine());
                switch (choice) {
                    case 1 :
                        mh.insert(db);
                        break;
                    case 2 :
                        mh.update(db);
                        break;
                    case 3 :
                        mh.delete(db);
                        break;
                    case 4 :
                        mh.displayTable(db);
                        break;
                    case 5 :
                        mh.exportToCsv(db);
                        break;
                    case 6 :
                        mh.exportToJson(db);
                        break;
                    case 7 :
                        mh.dropTable(db);
                        break;
                    case 8 :
                        mh.truncateTable(db);
                        break;
                    case 0 :
                        input = false;
                        break;
                    default :
                        System.out.println("Invalid choice");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("DB Error : " + e.getMessage());
        }
    }

}