package dao;

import com.mysql.jdbc.exceptions.MySQLSyntaxErrorException;
import dto.DatabaseDTO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.List;

public class DatabaseDAOImpl implements DatabaseDAO
{
    private PreparedStatement pStatement;
    private String usingTableName;
    private DatabaseDTO databaseDTO = new DatabaseDTO();

    public DatabaseDAOImpl() { }

    @Override
    public void createDB(String nameDB) throws SQLException, ClassNotFoundException
    {
        databaseDTO.setConnection(connectToDB());
        try
        {
            pStatement = databaseDTO.getConnection().prepareStatement(
                "CREATE DATABASE " + nameDB +
                    " DEFAULT CHARACTER SET utf8 " +
                    "COLLATE utf8_general_ci");
            pStatement.execute();
        }
        catch (SQLException e)
        {
            System.out.println("Error" + e.getMessage());
        }
        finally
        {
            pStatement.close();
            connectToDB().close();
        }
    }

    @Override
    public void createTable(String tableName) throws SQLException, ClassNotFoundException
    {
        usingTableName = tableName;
        try
        {
            pStatement = connectToDB().prepareStatement(
                "CREATE TABLE "+usingTableName+
                    "("+"customer_id INT AUTO_INCREMENT PRIMARY KEY, customer_name CHAR(50), customer_deposit INT) ENGINE=InnoDB DEFAULT CHARSET=utf8");
            pStatement.execute();
            System.out.println("Table was created!");
        } catch (SQLException e)
        {
            System.out.println("Error: " + e.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } finally
        {
            pStatement.close();
            connectToDB().close();
        }
    }

    @Override
    public void create(String tableName, String name_user, int balance) throws SQLException, ClassNotFoundException
    {
        if(getOneByName(tableName, name_user) == 0){
            try
            {
                usingTableName = tableName;
                pStatement = connectToDB().prepareStatement("INSERT INTO "+usingTableName+"  (customer_name, customer_deposit)  VALUES (?, ?)");

                pStatement.setString(1, name_user);
                pStatement.setInt(2, balance);
                pStatement.execute();
                System.out.println("User is create!" );
            } catch (SQLException e){
                System.out.println(e.getMessage());
            } finally
            {
                pStatement.close();
                connectToDB().close();
            }
        } else {
            System.out.println("This name is already in use!");
        }
    }

    @Override
    public void createTransaction(String tableName, String paying, String receiving, int sum) throws ClassNotFoundException, SQLException
    {
        usingTableName = tableName;
        ResultSet rs;
        int payingBalanceBefore;
        int payingBalanceAfter = 0;
        int receivingBalanceBefore;
        int receivingBalanceAfter = 0;

        Connection connection = connectToDB();
        connection.setAutoCommit(false);
        Savepoint savepoint;

        try {
            savepoint = connection.setSavepoint();

            payingBalanceBefore = selectFromDB(connection, usingTableName, paying);
            receivingBalanceBefore = selectFromDB(connection, usingTableName, receiving);

            if (payingBalanceBefore>=sum && sum > 0)
            {
                pStatement = connection.prepareStatement("UPDATE " + usingTableName + " SET customer_deposit= ? WHERE customer_name = ?");
                pStatement.setInt(1, payingBalanceBefore - sum);
                pStatement.setString(2, paying);
                pStatement.executeUpdate();

                payingBalanceAfter = selectFromDB(connection, usingTableName, paying);

                pStatement = connection.prepareStatement("UPDATE " + usingTableName + " SET customer_deposit= ? WHERE customer_name = ?");
                pStatement.setInt(1, receivingBalanceBefore + sum);
                pStatement.setString(2, receiving);
                pStatement.executeUpdate();

                receivingBalanceAfter = selectFromDB(connection, usingTableName, receiving);
            }

            if (payingBalanceAfter == payingBalanceBefore-sum && receivingBalanceAfter == receivingBalanceBefore + sum)
            {
                connection.commit();
                System.out.println("The transaction was successful");
            } else {
                connection.rollback(savepoint);
                System.out.println("Misha, wse h****! Davaj po novoj.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            connection.rollback();
            System.out.println("Misha, wse h****! Davaj po novoj.");
        } finally
        {
            pStatement.close();
            connectToDB().close();
        }
    }

    @Override
    public void update(String tableName, int id, String user_name) throws SQLException, ClassNotFoundException
    {
        usingTableName = tableName;
        try {
            pStatement =  connectToDB().prepareStatement("UPDATE "+usingTableName+" SET name_user= ? WHERE id_user = ?");
            pStatement.setString(1, user_name);
            pStatement.setInt(2, id);
            pStatement.executeUpdate();
            System.out.println("Successful update");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally
        {
            pStatement.close();
            connectToDB().close();
        }

    }

    @Override
    public void delete(String tableName, String name) throws ClassNotFoundException, SQLException
    {
        usingTableName = tableName;
        try
        {
            pStatement =  connectToDB().prepareStatement("DELETE  FROM "+usingTableName+" WHERE customer_name= ? ;");
            pStatement.setString(1, name);
            pStatement.execute();
            System.out.println("User is deleted!");
        }catch (SQLException e)
        {
            System.out.println("Error: "+ e.getMessage());
        } finally
        {
            pStatement.close();
            connectToDB().close();
        }
    }

    @Override
    public ArrayList<String> getOneById(String tableName, int id) throws SQLException, ClassNotFoundException
    {
        usingTableName = tableName;
        pStatement = connectToDB().prepareStatement("SELECT  * FROM "+usingTableName+" WHERE id_user = ?;");
        pStatement.setInt(1, id);
        ArrayList<String> users = new ArrayList<>();

        try(ResultSet rs = pStatement.executeQuery()){
            while (rs.next())
            {
                users.add(("id_user") + " " + rs.getString("name_user"));
                System.out.println(rs.getInt("id_user") + " " + rs.getString("name_user"));
            }
        }
        finally
        {
            pStatement.close();
            connectToDB().close();
        }
        return users;
    }

    @Override
    public int getOneByName(String tableName, String name) throws SQLException, ClassNotFoundException
    {
        usingTableName = tableName;
        int userId = 0;
        pStatement =  connectToDB().prepareStatement("SELECT  * FROM "+usingTableName+" WHERE name_user= ?;");
        pStatement.setString(1, name);
        try(ResultSet rs = pStatement.executeQuery())
        {
           while (rs.next()){
               userId = rs.getInt("id_user");
               System.out.println(""+rs.getInt("id_user") + " " + rs.getString("name_user"));
           }
        } catch (SQLException e)
        {
            System.out.println("Error: "+ e.getMessage());
        }
        finally
        {
            pStatement.close();
            connectToDB().close();
        }
        return userId;
    }

    @Override
    public List<String> getAll(String tableName) throws SQLException, ClassNotFoundException
    {
        usingTableName = tableName;
        pStatement =  connectToDB().prepareStatement("SELECT  * FROM "+usingTableName+" ORDER BY 'id_user'");
        try(ResultSet rs = pStatement.executeQuery())
        {
            while (rs.next())
            {
                System.out.println((rs.getInt("id_user") + " " + rs.getString("name_user")));
            }
        }
        finally
        {
            pStatement.close();
            connectToDB().close();
        }
        return null;
    }

    private Connection connectToDB() throws SQLException, ClassNotFoundException
    {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection =   DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/deposits" +
                "?characterEncoding=utf8", "root", "root");
        return  connection;
    }

    private int selectFromDB(Connection connection, String tableName, String user) throws SQLException
    {
        int balance = 0;
        usingTableName = tableName;
        pStatement =  connection.prepareStatement("SELECT  * FROM "+usingTableName+" WHERE customer_name= ?;");
        pStatement.setString(1, user);
        ResultSet rs = pStatement.executeQuery();
        while (rs.next())
        {
            balance = rs.getInt("customer_deposit");
        }

        return balance;
    }
}
