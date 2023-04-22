
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Group;

import java.util.*;


public class HelloFX extends Application{

    boolean status;//indicate user or admin
    public ArrayList<String> date_curs = new ArrayList<>();//2022-9-1,cn,us,jp,.....
    public HashMap<String, ArrayList<String>> cur_values = new HashMap<>();// aud,1.0,2.0,...
    public ChoiceBox cb;
    public ChoiceBox cb2;
    public Alert a = new Alert(null);

    public RateWriter rw = new RateWriter();

    public String alert_add = "Incorrect number of inputed rate! Please make sure you inputted rates for all other currencies!" +
            " (Separate by space)";

    double[] row0 = {0.0,0.0,0.0,0.0};
    double[] row1 = {0.0,0.0,0.0,0.0};
    double[] row2 = {0.0,0.0,0.0,0.0};
    double[] row3 = {0.0,0.0,0.0,0.0};

    String[] arrow0 = {"","","",""};
    String[] arrow1 = {"","","",""};
    String[] arrow2 = {"","","",""};
    String[] arrow3 = {"","","",""};

    @Override
    public void start(Stage primaryStage) {
        // First canvas

        //Background image of login page
        Image image =  new Image("file:login.jpg");
        ImageView mv = new ImageView(image);
        load_csv_info();
        Group root = new Group();
        Scene scene = new Scene(root, 300,300);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Login");

        //Font for first two buttons
        Font firstTwo_font = Font.font("Courier New", FontWeight.BOLD, 20);

        //Admin icon
        Image admin_icon =  new Image("file:admin.png");
        ImageView ad_i = new ImageView(admin_icon);

        //text label
        Font login_font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25);
        Text login_text = create_text(43,80,"Please login as :",login_font,root,Color.GOLD);

        //Admin Button
        Button admin_button = create_button(120,25,100,190,
                "Admin", firstTwo_font,ad_i);
        admin_button.setOnAction(actionEvent -> {
            enter_admin_action(primaryStage);
        });

        //User's icon
        Image user_icon =  new Image("file:user.png");
        ImageView us_i = new ImageView(user_icon);

        //User Buttton
        Button user_button = create_button(100,25,106,120,
                "User", firstTwo_font,us_i);
        user_button.setOnAction(actionEvent -> {
            enter_user_action(primaryStage);
        });

        root.getChildren().addAll(mv,admin_button,user_button,login_text);
        //SHOW
        primaryStage.show();
    }

    public void load_csv_info(){
        HashMap<String, HashMap<String, String>> list =  readfile.fileInfor("src/main/resources/InitialExchangeRate.csv");
        //System.out.println(list);
        ArrayList<String> keys = new ArrayList<>();
        for (int i = 0; i< list.size();i++){
            //keys.add(list.get[])
        }
        for (String s: list.keySet()){
            ArrayList<String> values = new ArrayList<>();
            date_curs.add(s);
            for (String s2 :list.get(s).keySet()){
                values.add(list.get(s).get(s2));
            }
            cur_values.put(s,values);
        }
        date_curs.add(0,"-");

        RateWriter rw = new RateWriter();
        rw.setRateCurc(cur_values);
        rw.setCurcList(date_curs);
        try{
            rw.rateWriter();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
    public Button create_button(double width,double height, double x, double y,
                                String text, Font font, ImageView img){

        Button but = new Button(text,img);
        but.setMaxSize(width,height);
        but.setText(text);
        if (font != null)
            but.setFont(font);
        but.setLayoutX(x);
        but.setLayoutY(y);
        return but;
    }
    public TextField create_textfield(double x, double y, double width, double height, Font font, Group gp){
        TextField tf = new TextField ();
        if (font != null)
            tf.setFont(font);
        tf.setLayoutX(x);
        tf.setLayoutY(y);
        tf.setMaxSize(width,height);
        gp.getChildren().add(tf);
        return tf;
    }

    public Text create_text(double x, double y, String s, Font font, Group gp, Color c){
        Text text = new Text();
        text.setText(s);
        text.setX(x);
        text.setY(y);
        text.setFont(font);
        text.setFill(c);
        return text;
    }
    public RadioButton create_radiobutton(double x,double y, String s,Font font, Color c){
        RadioButton rb = new RadioButton(s);
        rb.setFont(font);
        rb.setTextFill(c);
        rb.setLayoutX(x);
        rb.setLayoutY(y);
        return rb;
    }
    public ChoiceBox<String> create_choicebox(double x, double y, double width , double height, Group gp){
        ChoiceBox<String> cb = new ChoiceBox<>();
        cb.setMinWidth(width);
        cb.setMinHeight(height);
        cb.setLayoutX(x);
        cb.setLayoutY(y);

        gp.getChildren().add(cb);
        return cb;
    }
    public void enter_admin_action(Stage stage){
        System.out.println("Admin Received clicks");
        status = true;
        invoke_main_platform();
        //stage.close();
    }
    public void enter_user_action(Stage stage){
        System.out.println("User Received clicks");
        status = false;
        invoke_main_platform();
        //stage.close();
    }

    public void invoke_main_platform(){
        Stage stage_main = new Stage();

        Image image =  new Image("file:background.jpg");
        ImageView bg = new ImageView(image);

        Group root2 = new Group();
        root2.getChildren().add(bg);
        Scene scene_main = new Scene(root2, 1200,600);
        stage_main.setScene(scene_main);
        stage_main.setResizable(false);
        stage_main.setTitle("Currency converter");

        Font login_font = Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25);
        Font firstTwo_font = Font.font("Arial", FontWeight.BOLD, 23);


        // left choice box
        cb = create_choicebox(366,285,100,40,root2);
        //load items
            //Currency types!
        Set list = readfile.fileInfor("src/main/resources/InitialExchangeRate.csv").keySet();
        for (Object s: list)
            cb.getItems().addAll((String) s);
        //left text box
        TextField left_hbox = create_textfield(133,285,120,70,firstTwo_font,root2);
        //right choice box
        cb2 = create_choicebox(1200-133-100,285,100,40,root2);
        //load items
        for (Object s: list)
            cb2.getItems().addAll((String) s);
        //right text box
        TextField right_hbox = create_textfield(1200-366-100,285,120,70,firstTwo_font,root2);
        //disable it
        right_hbox.setDisable(true);

        cb.setOnAction(event -> {
        });

        //convert Button
        Button convert_button = create_button(120,25,540,390,
                "Convert", firstTwo_font, null);
        convert_button.setOnAction(actionEvent -> {
            System.out.println();
            right_hbox.setText(Converter.convertCurrency(Double.valueOf(left_hbox.getText()),(String)cb.getValue(),(String)cb2.getValue(),"src/main/resources/InitialExchangeRate.csv"));

        });
        root2.getChildren().add(convert_button);

        //Table button
        Button table_button = create_button(210,25,20,540,
                "Display Table", firstTwo_font, null);
        table_button.setOnAction(actionEvent -> {
            invoke_table();
        });
        root2.getChildren().add(table_button);

        //rate history button
        Button history_button = create_button(210,25,230,540,
                "Rate History", firstTwo_font, null);
        history_button.setOnAction(actionEvent -> {
            invoke_history();
        });
        root2.getChildren().add(history_button);
        //Admin tools button

        Button tools_button = create_button(213,25,1200-225,540,
                "Update currency", firstTwo_font, null);
        tools_button.setOnAction(actionEvent -> {
            invoke_update();
        });
        //invoke setPop
        Button setPop_button = create_button(213,25,1200-420,540,
                "Set Popular", firstTwo_font, null);
        setPop_button.setOnAction(actionEvent -> {
            invoke_update1();
        });

        if (status){
            root2.getChildren().add(tools_button);
        }

        if (status){
            root2.getChildren().add(setPop_button);
        }
        stage_main.show();
    }

    public void invoke_table(){
        Group table_root = new Group();
        Stage table_stage = new Stage();
        Scene table_scene= new Scene(table_root, 500,500);
        table_stage.setScene(table_scene);
        table_stage.setResizable(false);
        table_stage.setTitle("Popular currencies");

        Image image =  new Image("file:table_bg.jpg");
        ImageView bg = new ImageView(image);
        table_root.getChildren().add(bg);
        /*
        Button edit_pop = create_button(40,30,460,470,"Edit",null,null);
        table_root.getChildren().add(edit_pop);
        edit_pop.setOnAction(event -> {
            Group sub_root = new Group();
            Stage sub_stage = new Stage();
            Scene sub_scene= new Scene(sub_root, 500,80);
            sub_stage.setScene(sub_scene);
            sub_stage.setResizable(false);
            sub_stage.show();
        });*/

        ArrayList<String> currencies = new ArrayList<>();
        //SHOULD BE LOADED FROM DATABASE
        currencies.add(readfile.popularCurrency("src/main/resources/PopularCurrencies.csv").get(0));
        currencies.add(readfile.popularCurrency("src/main/resources/PopularCurrencies.csv").get(1));
        currencies.add(readfile.popularCurrency("src/main/resources/PopularCurrencies.csv").get(2));
        currencies.add(readfile.popularCurrency("src/main/resources/PopularCurrencies.csv").get(3));

        ///
        int row_x = 150 - 20;
        int row_y = 50 + 10;

        Font firstTwo_font = Font.font("Arial", FontWeight.BOLD, 20);

        for (String s : currencies){
            Text text = create_text(row_x,row_y,s,firstTwo_font,table_root,Color.GOLD);
            row_x += 100;
            table_root.getChildren().add(text);
        }

        row_x = 50 - 20;
        row_y = 150 + 10;

        for (String s : currencies){
            Text text = create_text(row_x,row_y,s,firstTwo_font,table_root,Color.GOLD);
            row_y += 100;
            table_root.getChildren().add(text);
        }

        int row_x0 = 13 ;
        int row_y0 = 60 ;
        Text text1 = create_text(row_x0,row_y0,"From/to",firstTwo_font,table_root,Color.GOLD);
        row_y0 += 100;
        table_root.getChildren().add(text1);

        // show all  the prices
        row_x = 130;
        row_y = 160;
        int row_x1 = 130;
        int row_x2 = 130;
        int row_x3 = 130;
        int row_y1 = 260;
        int row_y2 = 360;
        int row_y3 = 460;

        int i = 0;

        for (String d : currencies){
            String t = readfile.fileInfor("src/main/resources/InitialExchangeRate.csv").get(currencies.get(0)).get(d);

            Text text = create_text(row_x,row_y,t + arrow0[i],firstTwo_font,table_root,Color.GOLD);
            if (!t.equals("-")){

                if (Double.parseDouble(t) > row0[i] && row0[i] != 0.0 ){
                    arrow0[i] = "(I)";
                    text.setText(t + arrow0[i]);
                }else if (Double.parseDouble(t) < row0[i] && row0[i] != 0.0){
                    arrow0[i] = "(D)";
                    text.setText(t + "(D)");
                }
                row0[i] = Double.parseDouble(t);
            }

            row_x += 100;
            table_root.getChildren().add(text);
            i++;
        }
        i = 0;
        for (String d : currencies){
            String t = readfile.fileInfor("src/main/resources/InitialExchangeRate.csv").get(currencies.get(1)).get(d);

            Text text = create_text(row_x1,row_y1,t + arrow1[i],firstTwo_font,table_root,Color.GOLD);
            row_x1 += 100;
            if (!t.equals("-")){

                if (Double.parseDouble(t) > row1[i] && row1[i] != 0.0){
                    arrow1[i] = "(I)";
                    text.setText(t + "(I)");
                }else if (Double.parseDouble(t) < row1[i] && row1[i] != 0.0){
                    arrow1[i] = "(D)";
                    text.setText(t + "(D)");
                }
                row1[i] = Double.parseDouble(t);
            }

            table_root.getChildren().add(text);
            i++;
        }
        i = 0;
        for (String d : currencies){
            String t = readfile.fileInfor("src/main/resources/InitialExchangeRate.csv").get(currencies.get(2)).get(d);

            Text text = create_text(row_x2,row_y2,t + arrow2[i],firstTwo_font,table_root,Color.GOLD);
            if (!t.equals("-")){

                if (Double.parseDouble(t) > row2[i] && row2[i] != 0.0){
                    arrow2[i] = "(I)";
                    text.setText(t + "(I)");
                }else if (Double.parseDouble(t) < row2[i] && row2[i] != 0.0){
                    arrow2[i] = "(D)";
                    text.setText(t + "(D)");
                }
                row2[i] = Double.parseDouble(t);
            }

            row_x2 += 100;
            table_root.getChildren().add(text);
            i++;
        }
        i = 0;
        for (String d : currencies){
            String t = readfile.fileInfor("src/main/resources/InitialExchangeRate.csv").get(currencies.get(3)).get(d);

            Text text = create_text(row_x3,row_y3,t + arrow0[i],firstTwo_font,table_root,Color.GOLD);
            row_x3 += 100;
            if (!t.equals("-")){

                if (Double.parseDouble(t) > row3[i] && row3[i] != 0.0){
                    arrow3[i] = "(I)";
                    text.setText(t + "(I)");
                }else if (Double.parseDouble(t) < row3[i] && row3[i] != 0.0){
                    arrow3[i] = "(D)";
                    text.setText(t + "(D)");
                }
                row3[i] = Double.parseDouble(t);
            }

            table_root.getChildren().add(text);
            i++;
        }
        table_stage.show();
    }

    public void invoke_history(){
        Group history_root = new Group();
        Stage history_stage = new Stage();
        Scene history_scene= new Scene(history_root, 500,500);
        history_stage.setScene(history_scene);
        history_stage.setResizable(false);
        history_stage.setTitle("Rate History");

        Image image =  new Image("file:update.jpg");
        ImageView bg = new ImageView(image);
        history_root.getChildren().add(bg);

        ChoiceBox from_year = create_choicebox(10,100,80,25,history_root);
        ChoiceBox from_mon = create_choicebox(100,100,50,25,history_root);
        ChoiceBox from_day = create_choicebox(160,100,50,25,history_root);

        ChoiceBox to_year = create_choicebox(290,100,80,25,history_root);
        ChoiceBox to_mon = create_choicebox(380,100,50,25,history_root);
        ChoiceBox to_day = create_choicebox(440,100,50,25,history_root);

        Button find = create_button(80,30,225,150,"Browse",null,null);
        history_root.getChildren().add(find);

        int year = 1970;
        while (year<2023){
            from_year.getItems().add(String.valueOf(year));
            to_year.getItems().add(String.valueOf(year));
            year++;
        }
        int month = 1;
        while (month<13){
            if (month<10) {
                from_mon.getItems().add("0" + String.valueOf(month));
                to_mon.getItems().add("0" + String.valueOf(month));
            }else{
                from_mon.getItems().add(String.valueOf(month));
                to_mon.getItems().add(String.valueOf(month));
            }

            month++;
        }
        int day = 1;
        while (day<32){
            if (day<10) {
                from_day.getItems().add("0"+String.valueOf(day));
                to_day.getItems().add("0"+String.valueOf(day));
            }else{
                from_day.getItems().add(String.valueOf(day));
                to_day.getItems().add(String.valueOf(day));
            }
            day++;
        }

        from_year.setValue("YY");
        from_mon.setValue("MM");
        from_day.setValue("DD");
        to_year.setValue("YY");
        to_mon.setValue("MM");
        to_day.setValue("DD");

        ChoiceBox from_cur = create_choicebox(50,150,100,30,history_root);
        ChoiceBox to_cur = create_choicebox(350,150,100,30,history_root);

        Set curs = readfile.fileInfor("src/main/resources/InitialExchangeRate.csv").keySet();

        for (Object s: curs) {
            from_cur.getItems().addAll((String) s);
            to_cur.getItems().addAll((String) s);
        }

        Font firstTwo_font = Font.font("Arial", FontWeight.BOLD, 20);
        Text ft = create_text(215,50,"From/To",firstTwo_font,history_root,Color.GOLD);
        Text ave_t = create_text(50,210,"",firstTwo_font,history_root,Color.GOLD);
        Text min_t = create_text(50,260,"",firstTwo_font,history_root,Color.GOLD);
        Text max_t = create_text(50,310,"",firstTwo_font,history_root,Color.GOLD);
        Text median_t = create_text(50,360,"",firstTwo_font,history_root,Color.GOLD);
        Text sd_t = create_text(50,410,"",firstTwo_font,history_root,Color.GOLD);
        Text rates_t = create_text(50,460,"",firstTwo_font,history_root,Color.GOLD);

        RateHistory rh = new RateHistory();

        history_root.getChildren().addAll(ave_t,min_t,max_t,median_t,sd_t,rates_t,ft);

        find.setOnAction(event -> {

            String start_date = from_year.getValue() +"/"+(String)from_mon.getValue()+"/"+(String)from_day.getValue();
            String end_date = to_year.getValue() +"/"+(String)to_mon.getValue()+"/"+(String)to_mon.getValue();

            String cur1 = (String) from_cur.getValue();
            String cur2 = (String) to_cur.getValue() ;
            double[] list = rh.doOperation(start_date,cur1,cur2,end_date);
            //ave min max median sd
            double ave = list[0];
            double min = list[1];
            double max = list[2];
            double median = list[3];
            double sd = list[4];
            ArrayList<Double> rates = rh.RateCalList;

            String ave_s = "Average: ";
            String min_s = "Minimum: ";
            String max_s = "Maximum: ";
            String median_s = "Median: ";
            String sd_s = "Standard deviation: ";
            String rates_s = "Rates: " ;

            ave_s += String.valueOf(ave);
            min_s += String.valueOf(min);
            max_s += String.valueOf(max);
            median_s += String.valueOf(median);
            sd_s += String.valueOf(sd);
            rates_s += rates.toString();
            //rates_t += String.valueOf(list);
            ave_t.setText(ave_s);
            min_t.setText(min_s);
            max_t.setText(max_s);
            median_t.setText(median_s);
            sd_t.setText(sd_s);
            rates_t.setText(rates_s);
        });

        Alert date_alert = new Alert(Alert.AlertType.ERROR);
        boolean pass = false;
        if (!pass){
            date_alert.setContentText("Invalid input of time!");
            //date_alert.show();
        }

        history_stage.show();
    }

    public String edit_hint_message(String selected){
        String s = "Please input exchange rate for other currencies in sequence: \n";
        ArrayList<String> curs = new ArrayList<>();
        for (int i = 1; i< date_curs.size();i++){
            curs.add(date_curs.get(i));
        }//KEYSET() WILL SORT THE LIST OF KEYS, SOMEHOW :)
        for (String key: curs){
            s += " ";
            if (!key.equals(selected))
                s += key;
        }
        s += "\n";
        s += "(Seperate by one space)";
        return s;
    }
    public String add_hint_message(){
        String s = "Please input exchange rate for other currencies in sequence: \n";
        ArrayList<String> curs = new ArrayList<>();
        for (int i = 1; i< date_curs.size();i++){
            curs.add(date_curs.get(i));
        }//KEYSET() WILL SORT THE LIST OF KEYS, SOMEHOW :)
        for (String key: curs){
            s += " ";
            s += key;
        }
        s += "\n";
        s += "(Seperate by one space)";
        return s;
    }
    public void invoke_update(){
        Group update_root = new Group();
        Stage update_stage = new Stage();
        Scene update_scene= new Scene(update_root, 500,500);
        update_stage.setScene(update_scene);
        update_stage.setResizable(false);
        update_stage.setTitle("Update currencies");

        Image image =  new Image("file:update.jpg");
        ImageView bg = new ImageView(image);
        update_root.getChildren().add(bg);

        Font small = Font.font("Arial", FontWeight.BOLD, 15);

        // edit area
        RadioButton edit = create_radiobutton(50,100,"Edit",small,Color.GOLD);
        RadioButton add = create_radiobutton(50,153*2-25,"Add",small,Color.GOLD);
        //RadioButton del = create_radiobutton(50,133*3-25,"Delete",small,Color.GOLD);

        ToggleGroup group = new ToggleGroup();
        edit.setToggleGroup(group);
        add.setToggleGroup(group);
        //del.setToggleGroup(group);

        TextField edit_date = create_textfield(150,100,100,25,null,update_root);
        edit_date.setPromptText("Enter Date");
        edit_date.getParent().requestFocus();
        ChoiceBox edit_cur = create_choicebox(280,100,100,25,update_root);
        TextField edit_num = create_textfield(150,143+60,300,25,null,update_root);
        edit_num.setMinWidth(300);
        Button edit_but = create_button(50,25,410,100,"Save",null,null);

        Set list = readfile.fileInfor("src/main/resources/InitialExchangeRate.csv").keySet();
        for (Object s: list)
            edit_cur.getItems().addAll((String) s);

        update_root.getChildren().add(edit_but);
        update_root.getChildren().addAll(edit,add);
        edit_date.setDisable(true);
        edit_cur.setDisable(true);
        edit_num.setDisable(true);
        edit_but.setDisable(true);

        //ADD
        TextField add_date = create_textfield(150,153*2-28,100,25,null,update_root);
        add_date.setPromptText("Enter Date");
        add_date.getParent().requestFocus();
        TextField add_cur = create_textfield(280,153*2-28,100,25,null,update_root);
        add_cur.setPromptText("Enter Currency");
        add_cur.getParent().requestFocus();
        TextField add_value = create_textfield(150,153*2-28+100,300,25,null,update_root);
        add_value.setMinWidth(300);
        Button add_but = create_button(50,25,410,153*2-28,"Add",null,null);


        Text hint0 = new Text("");
        hint0.setFill(Color.YELLOW);
        hint0.setX(150);
        hint0.setY(150);
        update_root.getChildren().add(hint0);
        //Edit choice box small event
        edit_cur.setOnAction(event -> {
            String s0 = edit_hint_message((String) edit_cur.getValue());
            hint0.setText(s0);
        });

        // ADD HINTS
        String s = add_hint_message();
        Text hint = new Text();
        hint.setText(s);
        hint.setFill(Color.YELLOW);
        hint.setX(150);
        hint.setY(153*2-28+50+5);
        update_root.getChildren().add(hint);

        //basic settings
        update_root.getChildren().add(add_but);
        add_value.setDisable(true);
        add_date.setDisable(true);
        add_but.setDisable(true);
        add_cur.setDisable(true);
        //DEL
        /*
        ChoiceBox del_cur = create_choicebox(150,133*3-28,100,25,update_root);
        Button del_but = create_button(70,25,280,133*3-28,"Delete",null,null);
        update_root.getChildren().add(del_but);
        del_cur.setDisable(true);
        del_but.setDisable(true);
        */
        //events
        edit.setOnAction(event -> {
            edit_cur.setDisable(false);
            edit_num.setDisable(false);
            edit_but.setDisable(false);
            edit_date.setDisable(false);
            //del_cur.setDisable(true);
            //del_but.setDisable(true);

            add_but.setDisable(true);
            add_date.setDisable(true);
            add_cur.setDisable(true);
            add_value.setDisable(true);

        });
        /*
        del.setOnAction(event -> {
            edit_cur.setDisable(true);
            edit_num.setDisable(true);
            edit_but.setDisable(true);

            //del_cur.setDisable(false);
            //del_but.setDisable(false);

            add_but.setDisable(true);
            add_date.setDisable(true);
            add_cur.setDisable(true);
            add_value.setDisable(true);

        });
        */

        add.setOnAction(event -> {
            edit_cur.setDisable(true);
            edit_num.setDisable(true);
            edit_but.setDisable(true);
            edit_date.setDisable(true);
            //del_cur.setDisable(true);
            //del_but.setDisable(true);

            add_but.setDisable(false);
            add_date.setDisable(false);
            add_cur.setDisable(false);
            add_value.setDisable(false);

        });
        //EDIT FUNCTION
        edit_but.setOnAction(event ->{
            rw.setCurcList(date_curs);//date,cur1,cur2......
            rw.setRateCurc(cur_values);//cur1 = value... cur2 = value...

            ArrayList<String> values = new ArrayList<>(Arrays.asList(edit_num.getText().split(" ")));

            if (values.size() != cur_values.keySet().size()-1) {
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText(alert_add);
                a.show();
            }else if (edit_date.getText().split("/").length != 3){
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Please input data as YY/MM/DD");
                a.show();

            }else{
                boolean isnew = true;
                if(edit_date.getText().equals(date_curs.get(0)))
                    isnew = false;

                ArrayList<String> curs = new ArrayList<>();
                for (int i = 1; i< date_curs.size();i++)
                    curs.add(date_curs.get(i));

                int index_selected = curs.indexOf(edit_cur.getValue());//index of selected currency
                values.add(index_selected,"-");// FILL ITS PLACE WITH NOTHING
                int index = curs.indexOf(edit_cur.getValue());
                int i = 0;

                for (ArrayList<String> strings : cur_values.values()){
                    strings.set(index, values.get(i));
                    i++;
                }


                rw.setCurcList(date_curs);
                rw.setRateCurc(cur_values);
                rw.rateEditer((String) edit_cur.getValue(),values,edit_date.getText());

                Alert success = new Alert(Alert.AlertType.INFORMATION);
                success.setContentText("Success!");
                success.show();
                String s2 = add_hint_message();
                hint.setText(s2);
            }


        });
        //ADD FUNCTION
        add_but.setOnAction(event -> {
            String cur = add_cur.getText();
            String new_date = add_date.getText();
            String new_values = add_value.getText();

            ArrayList<String> values = new ArrayList<>(Arrays.asList(new_values.split(" ")));

            if (values.size() != cur_values.keySet().size()){
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText(alert_add);
                a.show();
            }else if (add_date.getText().split("/").length != 3){
                a.setAlertType(Alert.AlertType.ERROR);
                a.setContentText("Please input data as YY/MM/DD");
                a.show();
            }else{
                values.add("- ");
                date_curs.set(0,new_date);//update date
                date_curs.add(cur);//add a column for the new currency
                cur_values.put(cur,values);
                System.out.println(cur_values);
                int k = 0;
                for (int i = 1; i < date_curs.size()-1;i++){
                    ArrayList<String> r = cur_values.get(date_curs.get(i));
                    double goal = 1/Double.parseDouble(values.get(k));

                    r.add(String.format("%.2f",goal));
                    cur_values.put(date_curs.get(i), r);
                    System.out.println(r);
                    k++;
                }

                rw.setCurcList(date_curs);//date,cur1,cur2......
                rw.setRateCurc(cur_values);//cur1 = value... cur2 = value...

                try {
                    //rw.rateEditer(cur,values,new_date,true);
                    rw.rateWriter();
                    cb.getItems().add(cur);
                    cb2.getItems().add(cur);
                    edit_cur.getItems().add(cur);
                    Alert success = new Alert(Alert.AlertType.INFORMATION);
                    success.setContentText("Success!");
                    success.show();
                    String s2 = add_hint_message();
                    hint.setText(s2);


                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        update_stage.show();
    }
    public void invoke_update1(){
        Group update_root = new Group();
        Stage update_stage = new Stage();
        Scene update_scene= new Scene(update_root, 500,500);
        update_stage.setScene(update_scene);
        update_stage.setResizable(false);
        update_stage.setTitle("Set Popular currencies");
        Image image =  new Image("file:update.jpg");
        ImageView bg = new ImageView(image);
        update_root.getChildren().add(bg);
        Font small = Font.font("Arial", FontWeight.BOLD, 15);

        // edit area
        RadioButton edit = create_radiobutton(50,100,"Set",small,Color.GOLD);

        ToggleGroup group = new ToggleGroup();
        edit.setToggleGroup(group);

        ChoiceBox edit_cur1 = create_choicebox(270,100,20,25,update_root);
        ChoiceBox edit_cur2 = create_choicebox(200,100,20,25,update_root);
        ChoiceBox edit_cur3 = create_choicebox(130,100,20,25,update_root);
        ChoiceBox edit_cur = create_choicebox(340,100,20,25,update_root);


        Button edit_but = create_button(50,25,410,100,"Save",null,null);

        Set list = readfile.fileInfor("src/main/resources/InitialExchangeRate.csv").keySet();

        for (Object s: list)
            edit_cur.getItems().addAll((String) s);
        for (Object s: list)
            edit_cur1.getItems().addAll((String) s);
        for (Object s: list)
            edit_cur2.getItems().addAll((String) s);
        for (Object s: list)
            edit_cur3.getItems().addAll((String) s);

        update_root.getChildren().add(edit_but);
        update_root.getChildren().addAll(edit);

        edit_cur.setDisable(true);
        edit_cur1.setDisable(true);
        edit_cur2.setDisable(true);
        edit_cur3.setDisable(true);
        edit_but.setDisable(true);

        Text hint0 = new Text("");
        hint0.setFill(Color.YELLOW);
        hint0.setX(150);
        hint0.setY(150);
        update_root.getChildren().add(hint0);

        //basic settings
        edit.setOnAction(event -> {
            edit_cur.setDisable(false);
            edit_cur1.setDisable(false);
            edit_cur2.setDisable(false);
            edit_cur3.setDisable(false);
            edit_but.setDisable(false);

        });
        //EDIT FUNCTION
        edit_but.setOnAction(event ->{
            ArrayList<String> ar = new ArrayList<>();
            ar.add((String) edit_cur1.getValue());
            ar.add((String) edit_cur2.getValue());
            ar.add((String) edit_cur3.getValue());
            ar.add((String) edit_cur.getValue());
            PopCWriter pc = new PopCWriter();
            try {
                pc.PopWriter(ar);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        update_stage.show();
    }


    public static void main(String[] args) {
        launch(args);

    }


}
