package service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface DatabaseService
{
    void createTable(String tableName) throws SQLException, ClassNotFoundException;
    void createDB(String nameDB) throws SQLException, ClassNotFoundException;
    void makingDeposit(String tableName, String paying, String receiving, int sum) throws SQLException, ClassNotFoundException;
    void createUser(String tableName, String name, int balance) throws SQLException, ClassNotFoundException;
    void updateUser (String tableName, String oldName, String newName) throws SQLException, ClassNotFoundException;
    void deleteUser (String tableName, String name) throws SQLException, ClassNotFoundException;
    ArrayList<String> getOneUserById (String tableName, int index) throws SQLException, ClassNotFoundException;
    String getOneUserByName (String tableName, String name) throws SQLException, ClassNotFoundException;
    List<String> getAllUsers (String tableName) throws SQLException, ClassNotFoundException;
}
