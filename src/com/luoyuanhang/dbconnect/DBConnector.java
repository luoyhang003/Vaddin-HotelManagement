package com.luoyuanhang.dbconnect;


import java.sql.*;

/**
 * Created by jason on 15/9/10.
 *
 * Provide the functions of the MySQL Database services.
 *
 * including connect to the database,
 *           disconnect to the database,
 *           search from database,
 *           update data to database,etc.
 *
 *
 */
public class DBConnector {
    //Database URL
    public static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/example";
    //Driver Name
    public static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    //Database Username
    public static final String DB_USERNAME = "root";
    //Database Password
    public static final String DB_PASSWORD = "haha123";

    //create MySQL connection
    private Connection connection = null;
    //the times of executing query
    private int count = 0;
    //the set for saving result of query
    private ResultSet resultSet = null;
    //
    private PreparedStatement preparedStatement = null;

    //Log String
    public static String log = "";

    public DBConnector(){
        connection = getConnect();
    }

    /**
     * Get connection from MySQL Database
     * @return connection
     */
    public  Connection getConnect(){
        try{
            //Load Driver
            Class.forName(DB_DRIVER).newInstance();
            //Link to MySQL
            connection = DriverManager.getConnection(DB_URL,DB_USERNAME,DB_PASSWORD);
            //Output to Console
            System.out.println("DATABASE: CONNECT SUCCESS!");
            log += "DATABASE: CONNECT SUCCESS! \n";
        }catch(Exception exception){
            exception.printStackTrace();
            System.out.println("DATABASE: CONNECT FAIL!");
            log += "DATABASE: CONNECT FAIL! \n";
        }finally {
            return connection;
        }
    }

    /**
     * Disconnect from database
     * @return statement of disconnection
     */
    public boolean close(){
        //flag of statement
        boolean isClose = false;

        //try to close result set
        if(resultSet != null){
            try{
                resultSet.close();
                resultSet = null;
                System.out.println("DATABASE: CLOSE RESULT SET SUCCESS!");
                log += "DATABASE: CLOSE RESULT SET SUCCESS! \n";

            }catch (SQLException e){
                e.printStackTrace();
                isClose = false;
                System.out.println("DATABASE: CLOSE RESULT SET FAIL!");
                log += "DATABASE: CLOSE RESULT SET FAIL! \n";
            }
        }
        //try to close prepared statement
        if(preparedStatement != null){
            try{
                preparedStatement.close();
                preparedStatement = null;
                System.out.println("DATABASE: CLOSE PREPARED STATEMENT SUCCESS!");
                log += "DATABASE: CLOSE PREPARED STATEMENT SUCCESS! \n";
            }catch (SQLException e){
                isClose = false;
                e.printStackTrace();
                System.out.println("DATABASE: CLOSE PREPARED STATEMENT SET FAIL!");
                log += "DATABASE: CLOSE PREPARED STATEMENT SET FAIL! \n";
            }
        }
        //try to close connection
        if(connection != null){
            try {
                connection.close();
                connection = null;
                System.out.println("DATABASE: CLOSE CONNECTION SUCCESS!");
                log += "DATABASE: CLOSE CONNECTION SUCCESS! \n";
            } catch (SQLException e) {
                isClose = false;
                e.printStackTrace();
                System.out.println("DATABASE: CLOSE CONNECTION FAIL!");
                log += "DATABASE: CLOSE CONNECTION FAIL! \n";
            }
        }
        return  isClose;
    }

    /**
     * Get logs
     * @return log
     */
    public String getLog(){
        return  log;
    }


}
