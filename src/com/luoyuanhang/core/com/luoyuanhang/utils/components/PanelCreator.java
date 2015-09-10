package com.luoyuanhang.core.com.luoyuanhang.utils.components;

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
        Panel login = new Panel("登录系统");
        GridLayout gridLayout = new GridLayout(2,2);
        gridLayout.setMargin(true);

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

        gridLayout.addComponent(textfield_username,0,0);
        gridLayout.addComponent(passwordField,0,1);
        gridLayout.addComponent(button_login,1,0,1,1);

        login.setContent(gridLayout);

        return login;
    }
}
