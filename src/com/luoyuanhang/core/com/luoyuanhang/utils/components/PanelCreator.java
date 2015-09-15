package com.luoyuanhang.core.com.luoyuanhang.utils.components;

import com.luoyuanhang.dbconnect.DBConnector;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Page;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;


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

    /**
     * Main Login Page
     *
     * @param connection
     * @return
     */
    public static VerticalLayout createLoginWindow(final DBConnector connection){

        final VerticalLayout layout = new VerticalLayout();

        layout.setHeight(Page.getCurrent().getBrowserWindowHeight()+"");
        layout.setWidth(Page.getCurrent().getBrowserWindowWidth()+"");


        Image logo = new Image();
        logo.setHeight(65, Sizeable.Unit.PERCENTAGE);

        Label space = new Label();
        space.setWidth("500px");

        final TextField textfield_username = new TextField();
        textfield_username.setImmediate(true);
        textfield_username.setInputPrompt("用户名");
        textfield_username.setMaxLength(10);

        final PasswordField passwordField = new PasswordField();
        passwordField.setImmediate(true);
        passwordField.setInputPrompt("********");

        Button login = new Button("登录", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                int flag = 0;

                String username = textfield_username.getValue();
                String password = passwordField.getValue();


                try{
                    String sql1 = "SELECT eid FROM employee where eid = '" + username +
                            "'and epassword='"+password+"';";
                    ResultSet resultSet1 = connection.query(sql1);
                    if(resultSet1.next()){
                        flag = 1;
                    }

                    if(flag == 0) {

                        String sql = "SELECT cid FROM customer where cid = '" + username +
                                "'and cpassword='" + password + "';";

                        ResultSet resultSet = connection.query(sql);

                        if (!resultSet.next()) {
                            Notification notification = new Notification("登录错误", "请先注册或者重新输入！");
                            notification.show(Page.getCurrent());

                        } else {
                            Notification notification = new Notification("SUCCESS");
                            notification.show(Page.getCurrent());

                        }


                    }

               }catch (SQLException e){
                    e.printStackTrace();
                }

                if(flag == 0){
                    Notification notification = new Notification("用户身份登录！");
                    notification.show(Page.getCurrent());
                }

                if(flag == 1){
                    Notification notification = new Notification("员工身份登录！");
                    notification.show(Page.getCurrent());
                }

            }
        });



        Button register = new Button("注册", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
//                final String username = "";
//                String password = "";
//                String name = "";
//                int sex = 0;
//                String tel = "";

                Window window = new Window("注册");

                FormLayout formLayout = new FormLayout();
                formLayout.setSizeUndefined();

                final TextField username_tf = new TextField("*帐号：");
                final PasswordField password_pf = new PasswordField("*密码：");
                final TextField name_tf = new TextField("*姓名：");
                final OptionGroup sex_og = new OptionGroup("*性别：");
                sex_og.setNullSelectionAllowed(false);
                sex_og.addItem(1);
                sex_og.setItemCaption(1,"男");
                sex_og.addItem(2);
                sex_og.setItemCaption(2,"女");
                final TextField tel_tf = new TextField("*电话：");
                Button submit_btn = new Button("注册", new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        String username = username_tf.getValue();
                        String password = password_pf.getValue();
                        String name = name_tf.getValue();
                        char sex = '0';
                        if(sex_og.getValue().equals("2")){
                            sex = '1';
                        }
                        String tel = tel_tf.getValue();
                        String vip = "00000";

                        if(!(username!=null&&username.equals("")||password!=null&&password.equals("")||
                                name!=null&&name.equals("")||tel!=null&&tel.equals(""))) {
                            String sql = "INSERT INTO customer (cid,cname,csex,cphone,vip,cpassword) VALUES('"
                                    + username + "','" + name + "','" +
                                    sex + "','" + tel + "','" + vip + "','"+password+"');";
                            connection.update(sql);
                            Notification notification = new Notification("注册成功","请登录！");
                            notification.show(Page.getCurrent());
                        }else{
                            Notification notification = new Notification("请把信息填写完整！");
                            notification.show(Page.getCurrent());
                        }
                    }
                });


                formLayout.addComponent(username_tf);
                formLayout.addComponent(password_pf);
                formLayout.addComponent(name_tf);
                formLayout.addComponent(sex_og);
                formLayout.addComponent(tel_tf);
                formLayout.addComponent(submit_btn);

                window.setContent(formLayout);
                window.setWidth(260.0f, Sizeable.Unit.PIXELS);
                window.setDraggable(false);
                window.setResizable(false);
                window.setPosition((int)(Page.getCurrent().getBrowserWindowWidth()-window.getWidth())/2,
                        200);

                UI.getCurrent().addWindow(window);



            }
        });



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


    /**
     * 用户登录系统
     *
     * @return Tabsheet
     */
    public static TabSheet createUserTab(DBConnector connector, String id){
        TabSheet tabSheet = new TabSheet();

        tabSheet.setWidth(Page.getCurrent().getBrowserWindowWidth()+"");
        tabSheet.setHeight(Page.getCurrent().getBrowserWindowHeight()+"");

        tabSheet.setSizeFull();

        VerticalLayout info = new VerticalLayout();

        String SQL_info = "SELECT cid,cname,csex,cphone,vip FROM customer WHERE cid = '"+id+"'";

        info.addComponent(new Label("您好，"));

        try {

            ResultSet resultSet_info = connector.query(SQL_info);

            if (resultSet_info.next()) {

                String cid = resultSet_info.getString("cid");
                String cname = resultSet_info.getString("cname");
                String csex = resultSet_info.getString("csex");
                String ctel = resultSet_info.getString("cphone");
                String cvip = resultSet_info.getString("vip");


                Label label_cid = new Label("帐号:   "+cid);
                Label label_cname = new Label("姓名:   "+cname);
                Label label_csex = new Label("性别:   "+(csex.equals("0")?"男":"女"));
                Label label_ctel = new Label("手机:   "+ctel);
                Label label_vip = new Label("会员:   "+ (cvip.equals("00000")?"普通会员":"VIP 会员"));

                info.addComponent(label_cid);
                info.addComponent(label_cname);
                info.addComponent(label_csex);
                info.addComponent(label_ctel);
                info.addComponent(label_vip);

            }
        }catch (SQLException e){
            e.printStackTrace();
//            Notification notification = new Notification("ERROR");
//            notification.show(Page.getCurrent());
        }

        VerticalLayout roominfo = new VerticalLayout();



        VerticalLayout record = new VerticalLayout();




        tabSheet.addTab(info,"个人信息");
        tabSheet.addTab(roominfo,"房间信息");
        tabSheet.addTab(record,"个人记录");


        return tabSheet;
    }

    private static Panel
}
