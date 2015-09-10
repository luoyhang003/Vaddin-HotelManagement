package com.luoyuanhang.core;

import com.luoyuanhang.dbconnect.DBConnector;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jason on 15/9/10.
 */








/*=================TEST INSTANCE=====================*/
//test query and update

//public class MainPage extends UI {
//
//    protected void init(VaadinRequest request) {
//        VerticalLayout layout = new VerticalLayout();
//        setContent(layout);
//
//        //create a connection
//        DBConnector connector = new DBConnector();
//
//        String id = "";
//        String name = "";
//        String salary = "";
//
//        ResultSet resultSet = connector.query("SELECT * FROM teacher");
//        try {
//            while(resultSet.next()){
//                id += resultSet.getInt("id")+" ";
//                name += resultSet.getString("name")+" ";
//                salary += resultSet.getInt("salary")+" ";
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        Label lid = new Label(id);
//        Label lname = new Label(name);
//        Label lsalary = new Label(salary);
//
//        layout.addComponent(lid);
//        layout.addComponent(lname);
//        layout.addComponent(lsalary);
//
//        //disconnect database
//        connector.close();
//
//
//    }
//}


/*=================TEST INSTANCE=====================*/
//test connecting and disconnecting to the database

//public class MainPage extends UI {
//    protected void init(VaadinRequest request){
//        //create a connection
//        DBConnector connector = new DBConnector();
//        //disconnect database
//        connector.close();
//        String log;
//        log = connector.getLog();
//
//        VerticalLayout layout = new VerticalLayout();
//        setContent(layout);
//
//        Label label = new Label(log);
//        layout.addComponent(label);
//    }
//}
