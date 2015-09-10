package com.luoyuanhang.core;

import com.luoyuanhang.dbconnect.DBConnector;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by jason on 15/9/10.
 */



/*=================TEST INSTANCE=====================*/
//test connecting and disconnecting to the database

public class MainPage extends UI {
    protected void init(VaadinRequest request){
        //create a connection
        DBConnector connector = new DBConnector();
        //disconnect database
        connector.close();
        String log;
        log = connector.getLog();

        VerticalLayout layout = new VerticalLayout();
        setContent(layout);

        Label label = new Label(log);
        layout.addComponent(label);
    }
}
