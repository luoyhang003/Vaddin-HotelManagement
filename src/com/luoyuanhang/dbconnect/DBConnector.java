package com.luoyuanhang.dbconnect;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
            addLog("DATABASE: CONNECT SUCCESS!\n");
        }catch(Exception exception){
            exception.printStackTrace();
            System.out.println("DATABASE: CONNECT FAIL!");
            log += "DATABASE: CONNECT FAIL! \n";
            addLog("DATABASE: CONNECT FAIL!\n");
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
                addLog("DATABASE: CLOSE RESULT SET SUCCESS!\n");
            }catch (SQLException e){
                e.printStackTrace();
                isClose = false;
                System.out.println("DATABASE: CLOSE RESULT SET FAIL!");
                log += "DATABASE: CLOSE RESULT SET FAIL! \n";
                addLog("DATABASE: CLOSE RESULT SET FAIL!\n");
            }
        }
        //try to close prepared statement
        if(preparedStatement != null){
            try{
                preparedStatement.close();
                preparedStatement = null;
                System.out.println("DATABASE: CLOSE PREPARED STATEMENT SUCCESS!");
                log += "DATABASE: CLOSE PREPARED STATEMENT SUCCESS! \n";
                addLog("DATABASE: CLOSE PREPARED STATEMENT SUCCESS!\n");
            }catch (SQLException e){
                isClose = false;
                e.printStackTrace();
                System.out.println("DATABASE: CLOSE PREPARED STATEMENT SET FAIL!");
                log += "DATABASE: CLOSE PREPARED STATEMENT SET FAIL! \n";
                addLog("DATABASE: CLOSE PREPARED STATEMENT SET FAIL!\n");
            }
        }
        //try to close connection
        if(connection != null){
            try {
                connection.close();
                connection = null;
                System.out.println("DATABASE: CLOSE CONNECTION SUCCESS!");
                log += "DATABASE: CLOSE CONNECTION SUCCESS! \n";
                addLog("DATABASE: CLOSE CONNECTION SUCCESS! \n");
            } catch (SQLException e) {
                isClose = false;
                e.printStackTrace();
                System.out.println("DATABASE: CLOSE CONNECTION FAIL!");
                log += "DATABASE: CLOSE CONNECTION FAIL! \n";
                addLog("DATABASE: CLOSE CONNECTION FAIL! \n");
            }
        }
        addLog("DATABASE: DISCONNECT SUCCESS");
        return  isClose;
    }

    /**
     * Execute query
     *
     * @param sql
     * @return result set
     */
    public ResultSet query(String sql){
        try{
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            addLog("DATABASE: QUERY <" + sql + "> SUCCESS!\n");
        }catch (SQLException e){
            e.printStackTrace();
            addLog("DATABASE: QUERY FAIL!\n");
        }finally {
            return resultSet;
        }
    }

    /**
     * Execute update
     *
     * @param sql
     * @return count
     */
    public int update(String sql){
        try{
            preparedStatement = connection.prepareStatement(sql);
            count = preparedStatement.executeUpdate();
            addLog("DATABASE: UPDATE <" + sql + "> SUCCESS!\n");
        }catch(SQLException e){
            e.printStackTrace();
            addLog("DATABASE: UPDATE FAIL!\n");
        }
        return  count;
    }

    /**
     * Output Log to file "DB_LOG.txt"
     * @param logs
     */
    private static void addLog(String logs){
        try{
            File file = new File("DB_LOG.txt");
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getName(),true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(logs);
            bufferedWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    /**
     * Get logs
     * @return log
     */
    public String getLog(){
        return  log;
    }


    public static void main(String args[]){
        String id = "";
        String name = "";
        String salary = "";

        DBConnector con = new DBConnector();

        ResultSet resultSet = con.query("SELECT * FROM teacher");
        try {
            while(resultSet.next()){
                id += resultSet.getString("id")+" ";
                name += resultSet.getString("name")+" ";
                salary += resultSet.getInt("salary")+" ";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println(id);

    }

}
