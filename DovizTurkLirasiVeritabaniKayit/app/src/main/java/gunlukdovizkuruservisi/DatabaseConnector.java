package gunlukdovizkuruservisi;

import java.sql.*;

public class DatabaseConnector {
   
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/exchange_rates_db";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1234";;
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL,USERNAME,PASSWORD);
    }
}