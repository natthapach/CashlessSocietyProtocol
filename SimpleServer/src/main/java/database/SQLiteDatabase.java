package database;

import exceptions.IdNotFoundException;
import exceptions.InsufficientFundException;

import java.sql.*;

/**
 * Created by PC301 on 25/10/2560.
 */
public class SQLiteDatabase implements AccountManager {
    private String url = "csp.db";
    public void deposit(String uid, double amt) throws IdNotFoundException {
//        try {
//            Connection conn = prepareConnection();
//            if (conn != null) {
//                String sql = String.format("update account " +
//                                            "set balance=(select balance " +
//                                                            "from account " +
//                                                            "where id=%s" +
//                                            "where id=%")
//            }
//        }
    }

    public void withdraw(String uid, double amt) throws InsufficientFundException, IdNotFoundException {

    }

    public double getBalance(String uid) throws IdNotFoundException {
        try {
            Connection conn = prepareConnection();
            if (conn != null){
                String sql = "select balance from account where id=" + uid;
                Statement statement = conn.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
                if (resultSet.next())
                    return resultSet.getDouble("balance");
                throw new IdNotFoundException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    private Connection prepareConnection(){
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:" + url;
            Connection conn = DriverManager.getConnection(dbURL);

            return conn;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("connection Fail cannot find database");
        }

        return null;
    }
}
