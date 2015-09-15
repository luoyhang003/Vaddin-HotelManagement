package com.luoyuanhang.main;

import com.luoyuanhang.com.luoyuanhang.views.OutView;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

/**
 * Created by jason on 15/9/15.
 */
public class MainPage extends UI {
    Navigator navigator;

    protected void init(VaadinRequest request){
        navigator = new Navigator(this,this);

        navigator.addView("",new LoginView());
        navigator.addView(TestView.NAME,new TestView());




    }

    public class LoginView extends AbsoluteLayout implements View{
        public static final String NAME = "login";

        public LoginView(){
            setSizeFull();






        }

        @Override
        public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

        }
    }

    public class TestView extends VerticalLayout implements View{
        public static final String NAME = "test";

        public TestView(){
            setSizeFull();

            Button btn = new Button("EXIT", new Button.ClickListener() {
                @Override
                public void buttonClick(Button.ClickEvent clickEvent) {
                    navigator.navigateTo("");
                }
            });
            addComponent(btn);
        }
        @Override
        public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

        }
    }

}
