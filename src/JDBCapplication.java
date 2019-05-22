import service.DatabaseService;
import service.DatabaseServiceImpl;

import java.sql.SQLException;

public class JDBCapplication
{
    private static DatabaseService databaseService = new DatabaseServiceImpl();

    public static void main(String[] args) throws SQLException, ClassNotFoundException
    {
        String tableName = "customers";
//        databaseService.createTable(tableName);
//        databaseService.makingDeposit(tableName, "Tihon", "Eugene", 1);
//        databaseService.updateUser(tableName,"userOne", "newUserOne");
//        databaseService.createUser(tableName, "Dmitrij", 3000);
//        databaseService.getOneUserByName(tableName, "userOne");
//        databaseService.getOneUserById(tableName, 1);
//        databaseService.deleteUser(tableName, "Genadij");
//        databaseService.getAllUsers(tableName);
    }
}
