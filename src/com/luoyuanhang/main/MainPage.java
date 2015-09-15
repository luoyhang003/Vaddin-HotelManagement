package com.luoyuanhang.main;

import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * Created by jason on 15/9/15.
 */
public class MainPage extends UI {
    Navigator navigator;
    protected static final String MAINVIEW = "main";

    protected void init(VaadinRequest request){
        navigator = new Navigator(this,this);

    }
}
