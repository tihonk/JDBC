package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface DatabaseDAO
{
    void createDB (String nameDB) throws SQLException, ClassNotFoundException;
    void createTable (String tableName ) throws SQLException, ClassNotFoundException;
    void create(String tableName, String user_name, int balance) throws SQLException, ClassNotFoundException;
    void createTransaction(String tableName, String paying, String receiving, int sum) throws ClassNotFoundException, SQLException;
    void update(String tableName, int id, String user_name) throws SQLException, ClassNotFoundException;
    void delete(String tableName, String user_name) throws SQLException, ClassNotFoundException;
    ArrayList<String> getOneById(String tableName, int id) throws SQLException, ClassNotFoundException;
    int getOneByName(String tableName, String user_name) throws SQLException, ClassNotFoundException;
    List<String> getAll(String tableName) throws SQLException, ClassNotFoundException;
}
