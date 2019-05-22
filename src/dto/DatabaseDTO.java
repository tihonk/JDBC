package dto;

import java.sql.Connection;

public class DatabaseDTO
{
    Connection connection;

    public Connection getConnection()
    {
        return connection;
    }

    public void setConnection(Connection connection)
    {
        this.connection = connection;
    }
}
