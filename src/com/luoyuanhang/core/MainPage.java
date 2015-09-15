package com.luoyuanhang.core;

import com.luoyuanhang.core.com.luoyuanhang.utils.components.PanelCreator;
import com.luoyuanhang.dbconnect.DBConnector;
import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by jason on 15/9/10.
 *
 * The Main Page of the Project
 *
 *
 */


public class  MainPage extends UI{
    protected void init(VaadinRequest request){

    }
}




/*=================TEST INSTANCE=====================*/
//test Login Panel & User info

//public class MainPage extends UI{
//    protected void init(VaadinRequest request){
//        DBConnector connector = new DBConnector();
//
//        VerticalLayout layout = PanelCreator.createLoginWindow(connector);

//        VerticalLayout layout = new VerticalLayout();
//        layout.addComponent(PanelCreator.createUserTab(connector,"11116"));

//        VerticalLayout info = new VerticalLayout();
//
//
//        info.addComponent(new Label("HELLO"));
//
//        try {
//
//            String SQL_info = "SELECT cid,cname,csex,cphone,vip FROM customer WHERE cid = '11115'";
//
//            ResultSet resultSet_info = connector.query(SQL_info);
//            Notification notification_1 = new Notification("FIRST");
//            notification_1.show(Page.getCurrent());
//
//            if (resultSet_info.next()) {
//
//                String cid = resultSet_info.getString("cid");
//                String cname = resultSet_info.getString("cname");
//                String csex = resultSet_info.getString("csex");
//                String ctel = resultSet_info.getString("cphone");
//                String cvip = resultSet_info.getString("vip");
//
//
//                Notification notification = new Notification("ID:",cid);
//                notification.show(Page.getCurrent());
//
//                Label label_cid = new Label("帐号:   "+cid);
//                Label label_cname = new Label("姓名:   "+cname);
//                Label label_csex = new Label("性别:   "+csex);
//                Label label_ctel = new Label("手机:   "+ctel);
//
//                info.addComponent(label_cid);
//                info.addComponent(label_cname);
//                info.addComponent(label_csex);
//                info.addComponent(label_ctel);
//
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//            Notification notification = new Notification("ERROR");
//            notification.show(Page.getCurrent());
//        }
//
//        setContent(layout);
//
//    }
//}




/*=================TEST INSTANCE=====================*/
//test update

//public class MainPage extends UI{
//    protected  void init (VaadinRequest request){
//        VerticalLayout layout = new VerticalLayout();
//        setContent(layout);
//
//        DBConnector connector = new DBConnector();
//
//        String sql = "INSERT INTO teacher values('3','D','400')";
//        connector.update(sql);
//    }
//}





/*=================TEST INSTANCE=====================*/
//test query

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
