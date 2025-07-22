package interfaces;

import db.DBManager;
import exceptions.DatabaseException;

import java.sql.SQLException;

public interface Table_Operations {
    public interface InsertDisplayOperations<T> {
        void insert(DBManager db, T bean) throws SQLException;
        void display(DBManager db) throws SQLException;
    }

    public interface UpdateOperation {
        void update(DBManager db, java.util.Scanner sc) throws SQLException, DatabaseException;
    }

    public interface DeleteByIdOperation {
        void deleteById(DBManager db, int id) throws SQLException;
    }

    public interface ExportToCsv{
        void exportToCsv(DBManager db) throws SQLException;
    }

    public interface ExportToJson{
        void exportToJson(DBManager db) throws SQLException;
    }

    public interface DropTable{
        void dropTable(DBManager db) throws SQLException;
    }

    public interface TruncateTable{
        void truncateTable(DBManager db) throws SQLException;
    }

}


