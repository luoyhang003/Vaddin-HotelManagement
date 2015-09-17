package com.luoyuanhang.core.com.luoyuanhang.utils.components;

import com.luoyuanhang.dbconnect.DBConnector;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.MouseEvents;
import com.vaadin.server.*;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.server.Sizeable.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;
import java.util.Calendar;


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
        logo.setHeight(65, Unit.PERCENTAGE);

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

                        }
                    }

               }catch (SQLException e){
                    e.printStackTrace();
                }

                if(flag == 0){
                    Notification notification = new Notification("用户身份登录！");
                    notification.show(Page.getCurrent());

                    layout.removeAllComponents();
                    layout.addComponent(PanelCreator.createUserTab(connection,username));
                }

                if(flag == 1){
                    Notification notification = new Notification("员工身份登录！");
                    notification.show(Page.getCurrent());

                    String role_flag = "";

                    try{
                        String SQL_QUERY_EMP_ROLE = "SELECT role FROM employee WHERE eid='"+username+"'";
                        ResultSet rs_query_emp_role = connection.query(SQL_QUERY_EMP_ROLE);
                        if(rs_query_emp_role.next()) {
                            role_flag = rs_query_emp_role.getString("role");
                        }
                    }catch (SQLException e){
                        e.printStackTrace();
                    }
                    //前台登录
                    if(role_flag.equals("2")) {
                        layout.removeAllComponents();
                        layout.addComponent(createReciptorLayout(connection, username));
                    }
                    //经理登录
                    else if(role_flag.equals("0")){
                        layout.removeAllComponents();
                        layout.addComponent(createManagerLayout(connection,username));
                    }

                }

            }
        });



        Button register = new Button("注册", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
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
     * 进入用户系统的面板
     *
     * @return Tabsheet
     */
    public static TabSheet createUserTab(DBConnector connector, String id){
        //用户面板
        TabSheet tabSheet = new TabSheet();
        tabSheet.setWidth(Page.getCurrent().getBrowserWindowWidth()+"");
        tabSheet.setHeight(Page.getCurrent().getBrowserWindowHeight()+"");
        tabSheet.setSizeFull();

        //个人信息切换卡
        VerticalLayout info = new VerticalLayout();
        info.setMargin(true);

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

        for(int i = 1; i <= 5; i++){
            Label floor = new Label("第 " + i +" 层：");

            HorizontalLayout list = new HorizontalLayout();
            for(int j = 0; j < 5; j++){
                String rid = "8"+i+"0"+j;
                String rtype = "";
                String rstate = "";
                String rprice = "";

                String SQL_roomInfo = "SELECT * FROM room WHERE rid = '"+rid+"'";

                try{
                    ResultSet resultSet_roominfo = connector.query(SQL_roomInfo);
                    if(resultSet_roominfo.next()){
                        rtype = resultSet_roominfo.getString("type");
                        rstate = resultSet_roominfo.getString("state");
                        rprice = resultSet_roominfo.getString("price");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }

                Panel roominfoPanel = createRoomInfo(rid,rtype,rstate,rprice,connector,id);

                list.addComponent(roominfoPanel);


            }

            HorizontalLayout list1 = new HorizontalLayout();
            for(int j = 5; j < 10; j++){
                String rid = "8"+i+"0"+j;
                String rtype = "";
                String rstate = "";
                String rprice = "";

                String SQL_roomInfo = "SELECT * FROM room WHERE rid = '"+rid+"'";

                try{
                    ResultSet resultSet_roominfo = connector.query(SQL_roomInfo);
                    if(resultSet_roominfo.next()){
                        rtype = resultSet_roominfo.getString("type");
                        rstate = resultSet_roominfo.getString("state");
                        rprice = resultSet_roominfo.getString("price");
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }

                Panel roominfoPanel = createRoomInfo(rid,rtype,rstate,rprice,connector,id);

                list1.addComponent(roominfoPanel);

            }

            roominfo.addComponent(floor);
            roominfo.addComponent(list);
            roominfo.addComponent(list1);

        }



        VerticalLayout record = new VerticalLayout();

        Table bookTable = new Table("预订");
        bookTable.setImmediate(true);

        bookTable.setSizeFull();
//        bookTable.setColumnHeaders(new String[]{"房间号","预订时间","入住时间","经办"});
        bookTable.addContainerProperty("房间号",String.class,null);
        bookTable.addContainerProperty("预订时间",Timestamp.class,null);
        bookTable.addContainerProperty("入住时间",String.class,null);

        try{
            String SQL_queryBook = "SELECT rid,indate,bdate,bemployee FROM book WHERE book.cid = '"+id+"'";

            ResultSet rs_book = connector.query(SQL_queryBook);

            for(int i = 1;rs_book.next();i++){
                String book_rid = rs_book.getString("rid");
                String book_indate = rs_book.getString("indate");
                Timestamp bdate_timestamp = rs_book.getTimestamp("bdate");

                bookTable.addItem(new Object[]{book_rid,bdate_timestamp,
                        book_indate},new Integer(i));
            }


        }catch (SQLException e){
            e.printStackTrace();
        }

        record.addComponent(bookTable);



        Table inTable = new Table("入住");

        inTable.setImmediate(true);
        inTable.setSizeFull();
        inTable.addContainerProperty("房间号",String.class,null);
        inTable.addContainerProperty("入住时间",Timestamp.class,null);
        inTable.addContainerProperty("经办",String.class,null);

        try{
            String SQL_QUERY_IN_INFO = "SELECT rid,idate,ename FROM checkin " +
                    "NATURAL JOIN employee WHERE checkin.cid = '"+id+"' AND iemployee = eid";
            ResultSet rs_query_in_info = connector.query(SQL_QUERY_IN_INFO);
            for(int i = 1; rs_query_in_info.next();i++){
                String rid_checkin = rs_query_in_info.getString("rid");
                Timestamp idate_checkin = rs_query_in_info.getTimestamp("idate");
                String iemployee_checkin = rs_query_in_info.getString("ename");

                inTable.addItem(new Object[]{rid_checkin,idate_checkin,iemployee_checkin},new Integer(i));


            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        record.addComponent(inTable);




        Table outTable = new Table("退房");
        outTable.setImmediate(true);
        outTable.setSizeFull();
        outTable.addContainerProperty("房间号",String.class,null);
        outTable.addContainerProperty("入住时间",Timestamp.class,null);
        outTable.addContainerProperty("退房时间",Timestamp.class,null);
        outTable.addContainerProperty("花费",Integer.class,null);

        try{
            String SQL_QUERY_CHECKOUT = "SELECT rid,idate,odate,fee FROM book NATURAL JOIN "+
                    "checkin NATURAL JOIN checkout WHERE cid = '"+id+"'";
            ResultSet rs_query_out = connector.query(SQL_QUERY_CHECKOUT);

            for(int i = 1; rs_query_out.next();i++){
                String str_rid = rs_query_out.getString("rid");
                Timestamp ts_idate = rs_query_out.getTimestamp("idate");
                Timestamp ts_odate = rs_query_out.getTimestamp("odate");
                int int_fee = rs_query_out.getInt("fee");

                outTable.addItem(new Object[]{str_rid,ts_idate,ts_odate,int_fee},new Integer(i));
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        record.addComponent(outTable);


        tabSheet.addTab(info,"个人信息");
        tabSheet.addTab(roominfo,"房间信息");
        tabSheet.addTab(record,"个人记录");




        return tabSheet;
    }

    /**
     * 创建房间信息
     *
     * @param roomId 房间号
     * @param type 房间类型
     * @param state 房间状态
     * @param price 房间价格
     * @param connector 数据库连接
     * @param userID 用户 ID
     * @return 房间信息面板
     */
    public static Panel createRoomInfo(final String roomId,String type,String state,String price,
                                       final DBConnector connector, final String userID){
        Panel info = new Panel(roomId);
        info.setHeight(100.0f, Unit.PERCENTAGE);
        info.setImmediate(true);

        VerticalLayout roomInfoLayout = new VerticalLayout();
        roomInfoLayout.setMargin(true);
        roomInfoLayout.setImmediate(true);

        if(state.equals("0"))
            state = "空闲";
        else if(state.equals("1")){
            state = "已预订";
        }
        else if(state.equals("2")){
            state = "已入住";
        }

        if(type.equals("0")){
            type = "单人间";
        }
        else if (type.equals("1")){
            type = "标准间";
        }
        else if(type.equals("2")){
            type = "豪华间";
        }
        else{
            type = "商务间";
        }

        Label roomType =new Label("房间类型："+ type);
        roomType.setHeight(33.3f, Unit.PIXELS);
        Label roomState = new Label("房间状态："+ state);
        roomState.setHeight(33.3f, Unit.PIXELS);
        Label roomPrice = new Label("房间价格："+ price);
        roomPrice.setHeight(33.3f,Unit.PIXELS);

        roomInfoLayout.addComponent(roomType);
        roomInfoLayout.addComponent(roomState);
        roomInfoLayout.addComponent(roomPrice);


        if(state.equals("空闲")) {
            info.addClickListener(new MouseEvents.ClickListener() {
                @Override
                public void click(MouseEvents.ClickEvent clickEvent) {
                    String SQL_book_info = "SELECT * FROM room WHERE rid='"+roomId+"'";

                    ResultSet resultSet_book_info = connector.query(SQL_book_info);
                    String bookinfo_type = "";
                    String bookinfo_price = "";

                    try {
                        if(resultSet_book_info.next()){
                            bookinfo_type = resultSet_book_info.getString("type");
                            bookinfo_price = resultSet_book_info.getString("price");
                        }


                    } catch (SQLException e) {
                        e.printStackTrace();
                    }

                    switch (bookinfo_type){
                        case "0":
                            bookinfo_type = "单人间";
                            break;
                        case "1":
                            bookinfo_type = "标准间";
                            break;
                        case "2":
                            bookinfo_type = "豪华间";
                            break;
                        case "3":
                            bookinfo_type = "商务间";
                            break;
                    }

                    Window book = new Window("预订: " + roomId);
                    book.setImmediate(true);

                    VerticalLayout bookLayout = new VerticalLayout();
                    bookLayout.setMargin(true);

                    Label roomid_label = new Label("房间号： "+roomId);
                    Label roomtype_label = new Label("房间类型： "+bookinfo_type);
                    Label roomprice_label = new Label("房间价格： "+bookinfo_price);

                    final PopupDateField book_date = new PopupDateField("日期：");


//                    String inDate = ""+date.getYear() + date.getMonth() + date.getDate();
//                    Notification n = new Notification(date.toString());
//                    n.show(Page.getCurrent());

                    Button book_btn = new Button("确认预订", new Button.ClickListener() {
                        @Override
                        public void buttonClick(Button.ClickEvent clickEvent) {
                            String cid = userID;

                            Date date = book_date.getValue();
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTime(date);

                            String inDate = ""+calendar.get(Calendar.YEAR)+calendar.get(Calendar.MONTH)+
                                    calendar.get(Calendar.DATE);

                            String SQL_book_customer = "INSERT INTO book(rid,cid,indate) values('"+roomId+"','"+
                                    cid +"','"+inDate+ "')";
                            connector.update(SQL_book_customer);

                            String SQL_setroomstate = "UPDATE room SET state='1' WHERE rid='"+roomId+"'";
                            connector.update(SQL_setroomstate);

                            Notification notification_book_success = new Notification("恭喜您！","预订成功");
                            notification_book_success.show(Page.getCurrent());

                            Page.getCurrent().reload();




                        }
                    });
                    book_btn.setImmediate(true);

                    bookLayout.addComponent(roomid_label);
                    bookLayout.addComponent(roomtype_label);
                    bookLayout.addComponent(roomprice_label);
                    bookLayout.addComponent(book_date);
                    bookLayout.addComponent(book_btn);



                    book.setContent(bookLayout);




                    UI.getCurrent().addWindow(book);

                }
            });
        }

        info.setContent(roomInfoLayout);

        return info;

    }


    /**
     * 创建前台界面
     *
     * @param connector 数据库连接
     * @param eid 员工号
     * @return
     */
    public static VerticalLayout createReciptorLayout(final DBConnector connector,final String eid){
        final VerticalLayout layout = new VerticalLayout();

        String ename="";

        try{
            String SQL_QUERY_EMPLOYEENAME = "SELECT ename FROM employee WHERE eid = '"+ eid +"';";
            ResultSet rs_employee_name = connector.query(SQL_QUERY_EMPLOYEENAME);
            if(rs_employee_name.next()) {
                ename = rs_employee_name.getString("ename");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        Label label = new Label("登录身份：  前台"+ename);
        label.setSizeFull();

        Button btn_roominfo = new Button("查看房间", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Window win_roominfo = new Window("房间情况");
                win_roominfo.setSizeFull();

                VerticalLayout layout_room_info = new VerticalLayout();
                layout_room_info.setSizeFull();

                Table tb_room_info = new Table();
                tb_room_info.setSizeFull();

                tb_room_info.addContainerProperty("房间号",String.class,null);
                tb_room_info.addContainerProperty("房间类型",String.class,null);
                tb_room_info.addContainerProperty("房间价格",Integer.class,null);
                tb_room_info.addContainerProperty("房间状态",String.class,null);

                ItemClickEvent.ItemClickListener itemClickListener = new ItemClickEvent.ItemClickListener() {
                    @Override
                    public void itemClick(ItemClickEvent itemClickEvent) {
                        String rid = itemClickEvent.getItem().getItemProperty("房间号").toString();

                        Window win_recp_roominfo = new Window(rid);

                        VerticalLayout layout_recp_roominfo = new VerticalLayout();

                        //TODO
                    }
                };

                tb_room_info.addItemClickListener(itemClickListener);

                try{
                    String SQL_QUERY_RECPT_ROOM = "SELECT rid,type,price,state FROM room;";
                    ResultSet rs_query_recpt_room = connector.query(SQL_QUERY_RECPT_ROOM);
                    for(int i = 1;rs_query_recpt_room.next();i++ ){
                        String str_rid = rs_query_recpt_room.getString("rid");
                        String str_type = rs_query_recpt_room.getString("type");
                        int int_price = rs_query_recpt_room.getInt("price");
                        String str_state  = rs_query_recpt_room.getString("state");

                        switch (str_type){
                            case "0":
                                str_type = "单人间";
                                break;
                            case "1":
                                str_type = "标准间";
                                break;
                            case "2":
                                str_type = "豪华间";
                                break;
                            case "3":
                                str_type = "商务间";
                                break;
                        }

                        switch (str_state){
                            case "0":
                                str_state = "空闲";
                                break;
                            case "1":
                                str_state = "已预订";
                                break;
                            case "2":
                                str_state = "已入住";
                                break;
                        }

                        tb_room_info.addItem(new Object[]{str_rid,str_type,int_price,str_state},new Integer(i));
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }

                layout_room_info.addComponent(tb_room_info);

                win_roominfo.setContent(layout_room_info);

                UI.getCurrent().addWindow(win_roominfo);




            }
        });
        btn_roominfo.setSizeFull();

        Button btn_bookroom = new Button("办理预订", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Window win_book = new Window("办理预订");
                win_book.setSizeFull();
                win_book.setImmediate(true);

                VerticalLayout layout_book = new VerticalLayout();
                layout_book.setImmediate(true);
                layout_book.setSizeFull();

                Table tb_book = new Table("办理预订");
                tb_book.setSizeFull();

                tb_book.setHeight(100.0f, Unit.PERCENTAGE);

                tb_book.addContainerProperty("房间号",String.class,null);
                tb_book.addContainerProperty("房间类型",String.class,null);
                tb_book.addContainerProperty("价格",Integer.class,null);

                try{
                    String SQL_QUERY_BOOK_INFO = "SELECT rid,type,price FROM room WHERE"
                            +" state = '0'";
                    ResultSet rs_query_book_info = connector.query(SQL_QUERY_BOOK_INFO);
                    String str_rid = "";
                    String str_state = "";
                    String str_type = "";
                    int int_price = 0;

                    for(int i = 1; rs_query_book_info.next();i++){
                        str_rid = rs_query_book_info.getString("rid");
                        str_type = rs_query_book_info.getString("type");
                        int_price = rs_query_book_info.getInt("price");

                        switch (str_type){
                            case "0":
                                str_type = "单人间";
                                break;
                            case "1":
                                str_type = "标准间";
                                break;
                            case "2":
                                str_type = "豪华间";
                                break;
                            case "3":
                                str_type = "商务间";
                                break;
                        }


                        tb_book.addItem(new Object[]{str_rid,str_type,int_price},new Integer(i));


                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
                final ItemClickEvent.ItemClickListener itemClickListener_book = new ItemClickEvent.ItemClickListener() {
                    @Override
                    public void itemClick(ItemClickEvent itemClickEvent) {
                        final String str_item_rid = itemClickEvent.getItem().getItemProperty("房间号").toString();
                        String str_item_type = itemClickEvent.getItem().getItemProperty("房间类型").toString();
                        String str_item_price = itemClickEvent.getItem().getItemProperty("价格").toString();

                        final Window win_recp_book = new Window("预订");
                        win_recp_book.setImmediate(true);

                        VerticalLayout layout_win_recp_book = new VerticalLayout();
                        layout_win_recp_book.setImmediate(true);
                        layout_win_recp_book.setMargin(true);

                        Label label_win_recp_book = new Label("房间号： "+str_item_rid);
                        Label label_win_recp_type = new Label("房间类型： "+str_item_type);
                        Label label_win_recp_price = new Label("价格： "+str_item_price);

                        final TextField tx_win_recp_cid = new TextField("身份证号：");
                        tx_win_recp_cid.setInputPrompt("请输入身份证号");

                        final TextField tx_win_recp_name = new TextField("姓名：");
                        tx_win_recp_name.setSizeFull();
                        tx_win_recp_name.setInputPrompt("请输入姓名");

                        final OptionGroup og_win_recp_sex = new OptionGroup("性别：");
                        og_win_recp_sex.setNullSelectionAllowed(false);
                        og_win_recp_sex.addItem(1);
                        og_win_recp_sex.setItemCaption(1,"男");
                        og_win_recp_sex.addItem(2);
                        og_win_recp_sex.setItemCaption(2,"女");
                        og_win_recp_sex.setSizeFull();

                        final TextField tx_win_recp_phone = new TextField("手机号：");
                        tx_win_recp_phone.setInputPrompt("请输入手机号");
                        tx_win_recp_phone.setSizeFull();

                        final PopupDateField df_win_recp_indate = new PopupDateField("入住日期");


                        Button btn_win_recp_admit = new Button("确定", new Button.ClickListener() {
                            @Override
                            public void buttonClick(Button.ClickEvent clickEvent) {
                                String cid = tx_win_recp_cid.getValue();
                                String cname = tx_win_recp_name.getValue();
                                String cphone = tx_win_recp_phone.getValue();
                                String csex = (og_win_recp_sex.getValue()==1)?"0":"1";
                                String vip = "00000";

                                Date date = df_win_recp_indate.getValue();
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(date);
                                String inDate = ""+calendar.get(Calendar.YEAR)+calendar.get(Calendar.MONTH)+
                                        calendar.get(Calendar.DATE);

                                String SQL_WIN_REPT_BOOK_CUS = "INSERT INTO customer(cid,cname,cphone,csex,vip)"
                                        +" VALUES('"+cid+"','"+cname+"','"+cphone+"','"+csex+"','"+vip+"')";

                                connector.update(SQL_WIN_REPT_BOOK_CUS);

                                String SQL_WINREPT_BOOK_INSERT = "INSERT INTO book(rid,cid,bemployee,indate)"+
                                        " VALUES ('"+str_item_rid+"','"+cid+"','"+eid+"','"+inDate+"')";

                                connector.update(SQL_WINREPT_BOOK_INSERT);

                                String SQL_WIN_RECP_SET_ROOM_STATE = "UPDATE room SET state = '1' WHERE "+
                                        "rid = '"+str_item_rid+"'";

                                connector.update(SQL_WIN_RECP_SET_ROOM_STATE);

                                



                                win_recp_book.close();

                                Notification notification_success = new Notification("预订成功");
                                notification_success.setDelayMsec(2000);
                                notification_success.show(Page.getCurrent());

                            }
                        });

                        layout_win_recp_book.addComponent(label_win_recp_book);
                        layout_win_recp_book.addComponent(label_win_recp_type);
                        layout_win_recp_book.addComponent(label_win_recp_price);
                        layout_win_recp_book.addComponent(tx_win_recp_name);
                        layout_win_recp_book.addComponent(tx_win_recp_cid);
                        layout_win_recp_book.addComponent(og_win_recp_sex);
                        layout_win_recp_book.addComponent(tx_win_recp_phone);
                        layout_win_recp_book.addComponent(df_win_recp_indate);
                        layout_win_recp_book.addComponent(btn_win_recp_admit);

                        win_recp_book.setContent(layout_win_recp_book);

                        UI.getCurrent().addWindow(win_recp_book);

                    }
                };

                tb_book.addItemClickListener(itemClickListener_book);



                Table tb_book_cancel = new Table("取消预订");
                tb_book_cancel.setSizeFull();
                tb_book_cancel.setHeight(100.0f,Unit.PERCENTAGE);

                tb_book_cancel.addContainerProperty("房间号",String.class,null);
                tb_book_cancel.addContainerProperty("房间类型",String.class,null);
                tb_book_cancel.addContainerProperty("预订人",String.class,null);


                try{
                    String SQL_QUERY_BOOK_INFO_CANCEL = "SELECT rid,type,cname FROM room NATURAL JOIN "
                            +"book NATURAL JOIN customer WHERE state = '1'";
                    ResultSet rs_query_book_info_cancel = connector.query(SQL_QUERY_BOOK_INFO_CANCEL);
                    String str_rid = "";
                    String str_cname = "";
                    String str_type = "";

                    for(int i = 1; rs_query_book_info_cancel.next();i++){
                        str_rid = rs_query_book_info_cancel.getString("rid");
                        str_type = rs_query_book_info_cancel.getString("type");
                        str_cname = rs_query_book_info_cancel.getString("cname");

                        switch (str_type){
                            case "0":
                                str_type = "单人间";
                                break;
                            case "1":
                                str_type = "标准间";
                                break;
                            case "2":
                                str_type = "豪华间";
                                break;
                            case "3":
                                str_type = "商务间";
                                break;
                        }


                        tb_book_cancel.addItem(new Object[]{str_rid,str_type,str_cname},new Integer(i));


                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }



                layout_book.addComponent(tb_book);
                layout_book.addComponent(tb_book_cancel);

                win_book.setContent(layout_book);

                UI.getCurrent().addWindow(win_book);
            }
        });
        btn_bookroom.setSizeFull();

        final Button btn_checkin = new Button("办理入住", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Window win_checkin = new Window("办理入住");
                win_checkin.setImmediate(true);
                win_checkin.setSizeFull();

                VerticalLayout layout_checkin = new VerticalLayout();
                layout_checkin.setSizeFull();
                layout_checkin.setImmediate(true);

                Table tb_checkin = new Table();
                tb_checkin.setSizeFull();

                tb_checkin.addContainerProperty("房间号",String.class,null);
                tb_checkin.addContainerProperty("预订人",String.class,null);
                tb_checkin.addContainerProperty("预订时间",Timestamp.class,null);
                tb_checkin.addContainerProperty("房间类型",String.class,null);
                tb_checkin.addContainerProperty("价格",Integer.class,null);


                try{
                    String SQL_QUERY_CHECKIN = "SELECT rid,cname,bdate,type,price FROM book NATURAL "
                            +" JOIN room NATURAL JOIN customer WHERE state = '1'";
                    ResultSet rs_checkin = connector.query(SQL_QUERY_CHECKIN);

                    for(int i = 1; rs_checkin.next(); i++){
                        String str_rid = rs_checkin.getString("rid");
                        String str_cname = rs_checkin.getString("cname");
                        Timestamp ts_bdate = rs_checkin.getTimestamp("bdate");
                        String str_type = rs_checkin.getString("type");
                        int int_price = rs_checkin.getInt("price");

                        switch (str_type){
                            case "0":
                                str_type = "单人间";
                                break;
                            case "1":
                                str_type = "标准间";
                                break;
                            case "2":
                                str_type = "豪华间";
                                break;
                            case "3":
                                str_type = "商务间";
                                break;
                        }

                        tb_checkin.addItem(new Object[]{str_rid,str_cname,ts_bdate,str_type,int_price},
                                new Integer(i));
                    }


                }catch (SQLException e){
                    e.printStackTrace();
                }

                ItemClickEvent.ItemClickListener itemClickListener = new ItemClickEvent.ItemClickListener() {
                    @Override
                    public void itemClick(ItemClickEvent itemClickEvent) {
                        final Window win_checkin_confirm = new Window("确认入住");

                        VerticalLayout layout_checkin_confirm = new VerticalLayout();

                        final String rid = itemClickEvent.getItem().getItemProperty("房间号").toString();



                        Button btn_checkin = new Button("入住", new Button.ClickListener() {
                            @Override
                            public void buttonClick(Button.ClickEvent clickEvent) {

                                String cid = "";

                                try{
                                    String SQL_QUERY_BOOK_CID = "SELECT cid FROM book NATURAL JOIN room"+
                                            " WHERE state = 1 AND rid = '"+rid+"'";
                                    ResultSet rs_query_book_cid = connector.query(SQL_QUERY_BOOK_CID);
                                    if(rs_query_book_cid.next())
                                        cid = rs_query_book_cid.getString("cid");
                                }catch(SQLException e){
                                    e.printStackTrace();
                                }


                                String SQL_CHECKIN_INSERT = "INSERT INTO checkin(rid,cid,iemployee) values('"+
                                        rid+"','"+cid+"','"+eid+"')";
                                connector.update(SQL_CHECKIN_INSERT);

                                String SQL_CHECKIN_OK = "UPDATE room SET state = '2' WHERE rid='"+rid+"'";
                                connector.update(SQL_CHECKIN_OK);

                                Notification notification_ok = new Notification("已办理入住");
                                notification_ok.show(Page.getCurrent());

                                win_checkin_confirm.close();



                            }
                        });


                        String SQL_QUERY_CHECKIN_CONFIRM = "SELECT rid,cname,bdate,type,price FROM book NATURAL "
                                +" JOIN room NATURAL JOIN customer WHERE state = '1' and rid = '"+rid+"'";
                        String confirm_rid = "";
                        String confirm_cname = "";
                        String confirm_type = "";
                        int confirm_price = 0;
                        try{
                            ResultSet rs_checkin_confirm = connector.query(SQL_QUERY_CHECKIN_CONFIRM);
                            if(rs_checkin_confirm.next()){
                                confirm_rid = rs_checkin_confirm.getString("rid");
                                confirm_cname = rs_checkin_confirm.getString("cname");
                                confirm_type = rs_checkin_confirm.getString("type");
                                confirm_price = rs_checkin_confirm.getInt("price");

                            }
                        }catch (SQLException e){
                            e.printStackTrace();
                        }

                        Label label_rid = new Label("房间号：  "+confirm_rid);
                        label_rid.setSizeFull();
                        Label label_cname = new Label("预订人：  "+confirm_cname);
                        label_cname.setSizeFull();
                        Label label_type = new Label();
                        switch (confirm_type){
                            case "0":
                                label_type = new Label("房间类型： 单人间");
                                break;
                            case "1":
                                label_type = new Label("房间类型： 标准间");
                                break;
                            case "2":
                                label_type = new Label("房间类型： 豪华间");
                                break;
                            case "3":
                                label_type = new Label("房间类型： 商务间");
                                break;

                        }
                        Label label_price = new Label("价格：   "+confirm_price);

                        layout_checkin_confirm.addComponent(label_rid);
                        layout_checkin_confirm.addComponent(label_cname);
                        layout_checkin_confirm.addComponent(label_type);
                        layout_checkin_confirm.addComponent(label_price);
                        layout_checkin_confirm.addComponent(btn_checkin);

                        win_checkin_confirm.setContent(layout_checkin_confirm);

                        UI.getCurrent().addWindow(win_checkin_confirm);


                    }
                };

                tb_checkin.addItemClickListener(itemClickListener);

                layout_checkin.addComponent(tb_checkin);
                win_checkin.setContent(layout_checkin);
                UI.getCurrent().addWindow(win_checkin);

            }
        });
        btn_checkin.setSizeFull();



        Button btn_checkout = new Button("办理退房", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                final Window win_checkout = new Window("办理退房");
                win_checkout.setSizeFull();
                win_checkout.setImmediate(true);

                final VerticalLayout layout_win_checkout = new VerticalLayout();
                layout_win_checkout.setImmediate(true);
                layout_win_checkout.setSizeFull();

                Table tb_win_checkin = new Table();
                tb_win_checkin.setSizeFull();
                tb_win_checkin.setImmediate(true);

                tb_win_checkin.addContainerProperty("房间号",String.class,null);
                tb_win_checkin.addContainerProperty("入住时间",Timestamp.class,null);
                tb_win_checkin.addContainerProperty("入住人",String.class,null);
                tb_win_checkin.addContainerProperty("房费",Integer.class,null);


                try{
                    String SQL_CHECKOUT_QUERY_ALL_ROOM = "SELECT rid,idate,cname,price,cid FROM room"+
                            " NATURAL JOIN checkin NATURAL JOIN customer WHERE state = '2'";
                    ResultSet rs_checkout_all_room = connector.query(SQL_CHECKOUT_QUERY_ALL_ROOM);
                    for(int i = 1; rs_checkout_all_room.next();i++){
                        String str_rid = rs_checkout_all_room.getString("rid");
                        Timestamp ts_idate = rs_checkout_all_room.getTimestamp("idate");
                        String str_cname = rs_checkout_all_room.getString("cname");
                        int int_price = rs_checkout_all_room.getInt("price");

                        tb_win_checkin.addItem(new Object[]{str_rid,ts_idate,str_cname,int_price},
                                new Integer(i));
                    }
                }catch(SQLException e){
                    e.printStackTrace();
                }

                final ItemClickEvent.ItemClickListener itemClickListener_checkout =
                        new ItemClickEvent.ItemClickListener() {
                            @Override
                            public void itemClick(ItemClickEvent itemClickEvent) {
                                final Window win_checkout_confirm = new Window("退房确认");
                                win_checkout_confirm.setImmediate(true);

                                VerticalLayout layout_win_checkout_confirm = new VerticalLayout();
//                                layout_win_checkout_confirm.setSizeFull();
                                layout_win_checkout_confirm.setImmediate(true);
                                layout_win_checkout_confirm.setMargin(true);

                                final String str_rid = itemClickEvent.getItem().getItemProperty("房间号").toString();
                                String str_cname = itemClickEvent.getItem().getItemProperty("入住人").toString();
                                String int_fee = itemClickEvent.getItem().getItemProperty("房费").toString();

                                Label label_rid = new Label("房间号： "+str_rid);
                                Label label_cname = new Label("入住人： "+str_cname);
                                label_cname.setSizeFull();
                                Label label_fee = new Label("房费： "+int_fee);

                                Button btn_win_checkout_confirm = new Button("确认退房", new Button.ClickListener() {
                                    @Override
                                    public void buttonClick(Button.ClickEvent clickEvent) {
                                        String SQL_QUERY_CHECKINFO ="SELECT cid,price FROM room"+
                                                " NATURAL JOIN checkin NATURAL JOIN customer"+
                                                " WHERE state = '2' AND rid = '"+str_rid+"'";

                                        String str_cid_conf = "";
                                        int str_price = 0;

                                        try {
                                            ResultSet rs_query_checkininfo = connector.query(SQL_QUERY_CHECKINFO);

                                            if (rs_query_checkininfo.next()) {
                                                str_cid_conf = rs_query_checkininfo.getString("cid");
                                                str_price = rs_query_checkininfo.getInt("price");
                                            }
                                        }catch (SQLException e){
                                            e.printStackTrace();
                                        }

                                        String SQL_INSERT_CHECKOUT_INFO = "INSERT INTO " +
                                                "checkout(rid,cid,oemployee,fee) VALUES("+
                                                str_rid+"','"+str_cid_conf+"','"+eid+"','"+str_price+"')";
                                        connector.update(SQL_INSERT_CHECKOUT_INFO);

                                        String SQL_UPDATE_CHECKOUT_STATE = "UPDATE room SET state = '0' WHERE "
                                                +"rid = '"+str_rid+"'";
                                        connector.update(SQL_UPDATE_CHECKOUT_STATE);

                                        Notification notification_checkout_succ = new Notification("退房成功");
                                        notification_checkout_succ.show(Page.getCurrent());

                                        win_checkout_confirm.close();

                                    }
                                });


                                layout_win_checkout_confirm.addComponent(label_rid);
                                layout_win_checkout_confirm.addComponent(label_cname);
                                layout_win_checkout_confirm.addComponent(label_fee);
                                layout_win_checkout_confirm.addComponent(btn_win_checkout_confirm);



                                win_checkout_confirm.setContent(layout_win_checkout_confirm);

                                UI.getCurrent().addWindow(win_checkout_confirm);



                            }
                        };

                tb_win_checkin.addItemClickListener(itemClickListener_checkout);




                layout_win_checkout.addComponent(tb_win_checkin);

                win_checkout.setContent(layout_win_checkout);

                UI.getCurrent().addWindow(win_checkout);


            }
        });
        btn_checkout.setSizeFull();


        layout.addComponent(label);

        layout.addComponent(btn_roominfo);
        layout.addComponent(btn_bookroom);
        layout.addComponent(btn_checkin);
        layout.addComponent(btn_checkout);


        return layout;
    }



    public static VerticalLayout createAccountantLaout(final DBConnector connector,final String eid){
        VerticalLayout layout = new VerticalLayout();

        String ename="";

        try{
            String SQL_QUERY_EMPLOYEENAME = "SELECT ename FROM employee WHERE eid = '"+ eid +"';";
            ResultSet rs_employee_name = connector.query(SQL_QUERY_EMPLOYEENAME);
            if(rs_employee_name.next()) {
                ename = rs_employee_name.getString("ename");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        Label label = new Label("登录身份：  会计："+ename);
        label.setSizeFull();


        return  layout;
    }



    public static VerticalLayout createManagerLayout(final DBConnector connector,final String eid){
        final VerticalLayout layout = new VerticalLayout();

        Button btn_employee_info = new Button("查看员工", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Window win_manager_employee_info = new Window("查看员工");
                win_manager_employee_info.setSizeFull();
                win_manager_employee_info.setImmediate(true);

                VerticalLayout layout_win_manager_employee_info = new VerticalLayout();
                layout_win_manager_employee_info.setSizeFull();
                layout_win_manager_employee_info.setImmediate(true);

                Table tb_manager_employee_info = new Table();
                tb_manager_employee_info.setImmediate(true);
                tb_manager_employee_info.setSizeFull();

                tb_manager_employee_info.addContainerProperty("员工号",String.class,null);
                tb_manager_employee_info.addContainerProperty("姓名",String.class,null);
                tb_manager_employee_info.addContainerProperty("性别",String.class,null);
                tb_manager_employee_info.addContainerProperty("手机号",String.class,null);
                tb_manager_employee_info.addContainerProperty("职位",String.class,null);
                tb_manager_employee_info.addContainerProperty("工资",String.class,null);
                tb_manager_employee_info.addContainerProperty("状态",String.class,null);

                try{
                    String SQL_QUERY_ALL_EMPLOYEE_INFO = "SELECT * FROM employee";
                    ResultSet rs_query_all_employee_info = connector.query(SQL_QUERY_ALL_EMPLOYEE_INFO);

                    for(int i = 1; rs_query_all_employee_info.next(); i++){
                        String str_eid = rs_query_all_employee_info.getString("eid");
                        String str_ename = rs_query_all_employee_info.getString("ename");
                        String str_sex = rs_query_all_employee_info.getString("esex");
                        String str_ephone = rs_query_all_employee_info.getString("ephone");
                        String str_role = rs_query_all_employee_info.getString("role");
                        int int_salary = rs_query_all_employee_info.getInt("salary");
                        String str_isexist = rs_query_all_employee_info.getString("isexist");


                        switch (str_sex){
                            case "0":
                                str_sex = "男";
                                break;
                            case "1":
                                str_sex = "女";
                                break;
                        }

                        switch (str_role){
                            case "0":
                                str_role = "经理";
                                break;
                            case "1":
                                str_role = "会计";
                                break;
                            case "2":
                                str_role = "前台";
                                break;
                        }

                        switch (str_isexist){
                            case "0":
                                str_isexist = "在职";
                                break;
                            case "1":
                                str_isexist = "离职";
                                break;
                        }



                        tb_manager_employee_info.addItem(new Object[]{str_eid,str_ename,str_sex,
                        str_ephone,str_role,""+int_salary,str_isexist},new Integer(i));
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }


                layout_win_manager_employee_info.addComponent(tb_manager_employee_info);

                win_manager_employee_info.setContent(layout_win_manager_employee_info);

                UI.getCurrent().addWindow(win_manager_employee_info);


            }
        });
        btn_employee_info.setSizeFull();

        Button btn_manage_employee = new Button("任免员工", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Window win_manage_employee = new Window("员工任免");
                win_manage_employee.setImmediate(true);
                win_manage_employee.setSizeFull();

                final VerticalLayout layout_win_manage_employee = new VerticalLayout();
                layout_win_manage_employee.setImmediate(true);
                layout_win_manage_employee.setSizeFull();

                Button btn_add_employee = new Button("添加员工", new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent clickEvent) {
                        final Window win_add_employee = new Window("添加员工");
                        win_add_employee.setImmediate(true);
//                        win_add_employee.setSizeFull();

                        VerticalLayout layout_add_employee = new VerticalLayout();
                        layout_win_manage_employee.setImmediate(true);

                        FormLayout formLayout = new FormLayout();
                        formLayout.setSizeUndefined();

                        final TextField username_tf = new TextField("工号：");
                        final PasswordField password_pf = new PasswordField("密码：");
                        final TextField name_tf = new TextField("姓名：");
                        final OptionGroup sex_og = new OptionGroup("性别：");
                        sex_og.setNullSelectionAllowed(false);
                        sex_og.addItem(1);
                        sex_og.setItemCaption(1,"男");
                        sex_og.addItem(2);
                        sex_og.setItemCaption(2,"女");
                        final TextField tel_tf = new TextField("电话：");
                        final OptionGroup role_og = new OptionGroup("职位");
                        role_og.setNullSelectionAllowed(false);
                        role_og.addItem(1);
                        role_og.setItemCaption(1,"会计");
                        role_og.addItem(2);
                        role_og.setItemCaption(2,"前台");
                        final TextField salary_tf = new TextField("工资：");

                        Button btn_add_employee_admit = new Button("确认", new Button.ClickListener() {
                            @Override
                            public void buttonClick(Button.ClickEvent clickEvent) {
                                String str_eid = username_tf.getValue();
                                String str_epassword = password_pf.getValue();
                                String str_ename = name_tf.getValue();
                                String str_esex = "0";
                                if(sex_og.getValue().equals("2"))
                                    str_esex = "1";
                                String str_ephone = tel_tf.getValue();
                                String str_role = "2";
                                if(role_og.getValue().equals("1"))
                                    str_role = "1";
                                int int_salary = Integer.parseInt(salary_tf.getValue());

                                String SQL_ADD_EMPLOYEE = "INSERT INTO employee(eid,ename,esex,epassword,"+
                                        "ephone,role,salary) VALUES('"+str_eid+"','"+str_ename+"','"+str_esex+
                                        "','"+str_epassword+"','"+str_ephone+"','"+str_role+"','"
                                        +int_salary+"')";

                                connector.update(SQL_ADD_EMPLOYEE);

                                Notification notification_add_success = new Notification("添加成功");
                                notification_add_success.show(Page.getCurrent());

                                win_add_employee.close();


                            }
                        });






                        formLayout.addComponent(username_tf);
                        formLayout.addComponent(password_pf);
                        formLayout.addComponent(name_tf);
                        formLayout.addComponent(tel_tf);
                        formLayout.addComponent(sex_og);
                        formLayout.addComponent(role_og);
                        formLayout.addComponent(salary_tf);
                        formLayout.addComponent(btn_add_employee_admit);

                        win_add_employee.setContent(formLayout);

                        UI.getCurrent().addWindow(win_add_employee);

                    }


                });


                Table tb_manager_employee_info = new Table("开除员工");
                tb_manager_employee_info.setImmediate(true);
                tb_manager_employee_info.setSizeFull();

                tb_manager_employee_info.addContainerProperty("员工号",String.class,null);
                tb_manager_employee_info.addContainerProperty("姓名",String.class,null);
                tb_manager_employee_info.addContainerProperty("性别",String.class,null);
                tb_manager_employee_info.addContainerProperty("手机号",String.class,null);
                tb_manager_employee_info.addContainerProperty("职位", String.class, null);
                tb_manager_employee_info.addContainerProperty("工资", String.class, null);
                tb_manager_employee_info.addContainerProperty("状态",String.class,null);


                ItemClickEvent.ItemClickListener itemClickListener_remove_employee =
                        new ItemClickEvent.ItemClickListener() {
                            @Override
                            public void itemClick(ItemClickEvent itemClickEvent) {
                                String str_eid = itemClickEvent.getItem().getItemProperty("员工号").toString();
                                String str_ename = itemClickEvent.getItem().getItemProperty("姓名").toString();
                                String str_role = itemClickEvent.getItem().getItemProperty("职位").toString();
                                String str_salary = itemClickEvent.getItem().getItemProperty("工资").toString();

                                Window win_remove_confirm = new Window("开除员工");
                                win_remove_confirm.setImmediate(true);

                                VerticalLayout layout_remove_confirm = new VerticalLayout();
                                layout_remove_confirm.setImmediate(true);
                                layout_remove_confirm.setMargin(true);

                                Label label_eid = new Label("员工号： "+ str_eid);
                                Label label_ename = new Label("姓名： "+str_ename);
                                Label label_role = new Label("职位： "+str_role);
                                Label label_salary = new Label("工资： "+str_salary);

                                Button btn_remove_admit = new Button("确认", new Button.ClickListener() {
                                    @Override
                                    public void buttonClick(Button.ClickEvent clickEvent) {

                                    }
                                });

                                layout_remove_confirm.addComponent(label_eid);
                                layout_remove_confirm.addComponent(label_ename);
                                layout_remove_confirm.addComponent(label_role);
                                layout_remove_confirm.addComponent(label_salary);
                                layout_remove_confirm.addComponent(btn_remove_admit);





                            }
                        };

                tb_manager_employee_info.addItemClickListener(itemClickListener_remove_employee);

                try{
                    String SQL_QUERY_ALL_EMPLOYEE_INFO = "SELECT * FROM employee";
                    ResultSet rs_query_all_employee_info = connector.query(SQL_QUERY_ALL_EMPLOYEE_INFO);

                    for(int i = 1; rs_query_all_employee_info.next(); i++){
                        String str_eid = rs_query_all_employee_info.getString("eid");
                        String str_ename = rs_query_all_employee_info.getString("ename");
                        String str_sex = rs_query_all_employee_info.getString("esex");
                        String str_ephone = rs_query_all_employee_info.getString("ephone");
                        String str_role = rs_query_all_employee_info.getString("role");
                        int int_salary = rs_query_all_employee_info.getInt("salary");
                        String str_isexist = rs_query_all_employee_info.getString("isexist");


                        switch (str_sex){
                            case "0":
                                str_sex = "男";
                                break;
                            case "1":
                                str_sex = "女";
                                break;
                        }

                        switch (str_role){
                            case "0":
                                str_role = "经理";
                                break;
                            case "1":
                                str_role = "会计";
                                break;
                            case "2":
                                str_role = "前台";
                                break;
                        }

                        switch (str_isexist){
                            case "0":
                                str_isexist = "在职";
                                break;
                            case "1":
                                str_isexist = "离职";
                                break;
                        }



                        tb_manager_employee_info.addItem(new Object[]{str_eid,str_ename,str_sex,
                                str_ephone,str_role,""+int_salary,str_isexist},new Integer(i));
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }



                layout_win_manage_employee.addComponent(btn_add_employee);
                layout_win_manage_employee.addComponent(tb_manager_employee_info);

                win_manage_employee.setContent(layout_win_manage_employee);

                UI.getCurrent().addWindow(win_manage_employee);


            }
        });
        btn_manage_employee.setSizeFull();

        Button btn_chart = new Button("查看报表", new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {

            }
        });
        btn_chart.setSizeFull();



        layout.addComponent(btn_employee_info);
        layout.addComponent(btn_manage_employee);
        layout.addComponent(btn_chart);

        return layout;
    }

}
