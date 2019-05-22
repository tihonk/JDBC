package service;

import dao.DatabaseDAO;
import dao.DatabaseDAOImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseServiceImpl implements DatabaseService
{
    private static DatabaseDAO databaseDAO = new DatabaseDAOImpl();

    @Override
    public void createDB(String nameDB) throws SQLException, ClassNotFoundException
    {
         databaseDAO.createDB(nameDB);
    }

    @Override
    public void makingDeposit(String tableName, String paying, String receiving, int sum) throws SQLException, ClassNotFoundException
    {
        databaseDAO.createTransaction(tableName, paying, receiving, sum);
    }

    @Override
    public void createTable(String tableName) throws ClassNotFoundException, SQLException
    {
        databaseDAO.createTable(tableName);
    }


    @Override
    public void createUser(String tableName, String name, int balance) throws SQLException, ClassNotFoundException
    {
        databaseDAO.create(tableName, name, balance);
    }

    @Override
    public void updateUser(String tableName, String oldName, String newName) throws SQLException, ClassNotFoundException
    {
        int userId = databaseDAO.getOneByName(tableName, oldName);
        databaseDAO.update(tableName, userId, newName);
    }

    @Override
    public void deleteUser(String tableName, String name) throws SQLException, ClassNotFoundException
    {
        databaseDAO.delete(tableName, name);
    }

    @Override
    public ArrayList<String> getOneUserById(String tableName, int index) throws SQLException, ClassNotFoundException
    {
        return databaseDAO.getOneById(tableName, index);
    }

    @Override
    public String getOneUserByName(String tableName, String name) throws SQLException, ClassNotFoundException
    {
        databaseDAO.getOneByName(tableName, name);
        return "null";
    }

    @Override
    public List<String> getAllUsers(String tableName) throws SQLException, ClassNotFoundException
    {
        List<String> allUsers = databaseDAO.getAll(tableName);
        return allUsers;
    }
}
