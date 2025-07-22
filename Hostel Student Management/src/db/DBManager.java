package db;

import java.sql.*;

import java.util.*;
import exceptions.DatabaseException;

public class DBManager {

    private Connection conn;
    private final String schema_name ;

    public DBManager(Connection conn,String schema_name) throws SQLException, DatabaseException {
        this.conn = Objects.requireNonNull(conn, "Connection cannot be null.");
        this.schema_name = Objects.requireNonNull(schema_name, "Schema name cannot be null.");
        try{
            setSchema();
        }catch (SQLException e){
            throw new DatabaseException("Failed to set Schema: " + e.getMessage());
        }
    }

    public Connection getConnection() throws SQLException{
        if (this.conn == null || this.conn.isClosed()) {
            this.conn = DriverManager.getConnection(DatabaseConfiguration.getUrl(), DatabaseConfiguration.getUsername(), DatabaseConfiguration.getPassword());
        }
        return this.conn;
    }

    public String getSchema_name(){

        return this.schema_name;
    }

    public void setSchema() throws SQLException{
        try (Statement st = this.conn.createStatement();){
            st.execute("SET search_path TO " + schema_name + ";");
        }
    }

    public void executeUpdate(String query) throws DatabaseException {
        try(Statement st = conn.createStatement();){
            st.executeUpdate(query);
        } catch (SQLException e) {
            throw new DatabaseException("SQL Execution failed : " + e.getMessage());
        }
    }

}