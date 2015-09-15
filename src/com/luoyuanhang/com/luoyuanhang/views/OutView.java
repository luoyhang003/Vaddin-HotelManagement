package com.luoyuanhang.com.luoyuanhang.views;

import com.luoyuanhang.main.MainPage;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Created by jason on 15/9/15.
 */
public class OutView extends VerticalLayout implements View {
    Navigator navigator;

    public OutView(){
        setSizeFull();
        navigator = new Navigator(UI.getCurrent(),this);

        Button btn = new Button("GOOO", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
//                navigator.navigateTo();
            }
        });
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

    }
}
