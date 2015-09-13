package com.luoyuanhang.core.com.luoyuanhang.utils.components;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;


/**
 * Created by jason on 15/9/10.
 */
public class PanelCreator {

    /**
     * Create a Login Panel
     *
     * @return Login Panel
     */
    public static Panel createLoginPanel(){
        Panel login = new Panel();

        login.setHeight(Page.getCurrent().getBrowserWindowHeight()+"");
        login.setWidth(Page.getCurrent().getBrowserWindowWidth()+"");

        //username textfield
        TextField textfield_username = new TextField();
        textfield_username.setImmediate(true);
        textfield_username.setInputPrompt("用户名");
        textfield_username.setMaxLength(10);

        //password field
        PasswordField passwordField = new PasswordField();
        passwordField.setImmediate(true);
        passwordField.setInputPrompt("********");

        //login button
        Button button_login = new Button("登 录");

        //register button
        Button button_register = new Button("注 册");

        //option button
        OptionGroup optionGroup = new OptionGroup();
        optionGroup.addItem("客户");
        optionGroup.addItem("前台");
        optionGroup.addItem("会计");
        optionGroup.addItem("经理");
        optionGroup.setNullSelectionAllowed(false);
        optionGroup.setImmediate(true);




        return login;
    }

    public static VerticalLayout createLoginWindow(){
        String username = "";
        String password = "";s

        VerticalLayout layout = new VerticalLayout();

        layout.setHeight(Page.getCurrent().getBrowserWindowHeight()+"");
        layout.setWidth(Page.getCurrent().getBrowserWindowWidth()+"");


        Image logo = new Image();
        logo.setHeight(65, Sizeable.Unit.PERCENTAGE);

        Label space = new Label();
        space.setWidth("500px");

        TextField textfield_username = new TextField();
        textfield_username.setImmediate(true);
        textfield_username.setInputPrompt("用户名");
        textfield_username.setMaxLength(10);

        PasswordField passwordField = new PasswordField();
        passwordField.setImmediate(true);
        passwordField.setInputPrompt("********");

        Button login = new Button("登录", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

            }
        });



        Button register = new Button("注册");



        HorizontalLayout horizontalLayout1 = new HorizontalLayout();
        horizontalLayout1.setHeight(10, Sizeable.Unit.PERCENTAGE);
        horizontalLayout1.addComponent(space);
        horizontalLayout1.addComponent(textfield_username);
        horizontalLayout1.addComponent(passwordField);
        horizontalLayout1.addComponent(login);
        horizontalLayout1.addComponent(register);

        HorizontalLayout horizontalLayout2 = new HorizontalLayout();
        horizontalLayout2.setHeight(10, Sizeable.Unit.PERCENTAGE);


        layout.addComponent(logo);
//        layout.addComponent(horizontalLayout);
        layout.addComponent(horizontalLayout1);
        layout.addComponent(horizontalLayout2);



//        Image logo = new Image();
//        logo.setHeight("800px");
//        layout.addComponent(logo);
//
//        Label space = new Label(" ");
//        space.setWidth("500px");
//
//        HorizontalLayout textfieldLayout = new HorizontalLayout();
//        textfieldLayout.addComponent(space);
//
//        //username textfield
//        TextField textfield_username = new TextField();
//        textfield_username.setImmediate(true);
//        textfield_username.setInputPrompt("用户名");
//        textfield_username.setMaxLength(10);
//        textfieldLayout.addComponent(textfield_username);
//
//        //password field
//        PasswordField passwordField = new PasswordField();
//        passwordField.setImmediate(true);
//        passwordField.setInputPrompt("********");
//        textfieldLayout.addComponent(passwordField);
//
//        //login button
//        Button button_login = new Button("登 录");
//        textfieldLayout.addComponent(button_login);
//
//        layout.addComponent(textfieldLayout);
//
//        HorizontalLayout gridLayout = new HorizontalLayout();
//        Label label = new Label("新用户?");
//
//
//        Link link = new Link("开始注册!", new ExternalResource("http://www.baidu.com"));
//        gridLayout.addComponent(space);
//        gridLayout.addComponent(label);
//        gridLayout.addComponent(link);
//        layout.addComponent(gridLayout);
//

        return layout;

    }
}
