
//Abhinaya Balakumar
/*
Enter the student number through a textbox -> submit.
Allow for the user to click courses that a student is taking (hard-code for English, French, Biology, Physics, Chemistry, Data Management, Calculus and Vectors, Advanced Functions and Computer Science). If the box is checked off, then allow the user to enter an integer mark. For each subject, have a “count” and “exclude” radio button.... Click “Calculate” to include all “counted subjects” as part of their average. Display the courses that were “counted” and the student’s average.
*/
package com.example.reportcard;

import javafx.application.Application;
import javafx.stage.Stage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.CheckBox;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class Main extends Application {

    private Label l = new Label("Please click a box and enter a grade");
    private final ObservableList<Report> data = FXCollections.observableArrayList(
            new Report("English"),
            new Report("French"),
            new Report("Biology"),
            new Report("Chemistry"),
            new Report("Physics"),
            new Report("Data Management"),
            new Report("Advance Functions"),
            new Report("Computer Science"),
            new Report("Calculus and Vectors"));

    private Button button = new Button("Submit");
    //When the button is clicked, it will show the average
    public void check(ActionEvent event2) {
        l.setText(getAverage());
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Report Card");
        Label labelfirst = new Label("Enter your student number");
        Label label = new Label("The student number must be 6-7 digits long and only numbers");
        Button button = new Button("Submit");
        TextField text = new TextField();
        button.setOnAction(e -> {
            if (login(text.getText()) == true) {
                //If the student number is correct, then show second screen
                primaryStage.setScene(scene2());}
            else if(login(text.getText()) == false){

                label.setText("Please try again");
            }});
        VBox layout = new VBox(5);
        layout.getChildren().addAll(labelfirst, text, button,label);
        Scene scene = new Scene(layout, 300, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public Scene scene2() {
        Scene scene = new Scene(new Group());
        final Label label = new Label("Welcome");
        TableView<Report> table = new TableView<>(data);
        table.setPrefHeight(450);
        table.setPrefWidth(500);

        TableColumn<Report, String> handleCol = new TableColumn<>("Courses");
        handleCol.setCellValueFactory(new PropertyValueFactory<>("handle"));
        table.getColumns().add(handleCol);

        TableColumn courseCheckCol = new TableColumn();
        courseCheckCol.setCellValueFactory(new PropertyValueFactory<Report, CheckBox>("courseCheck"));
        table.getColumns().add(courseCheckCol);

        TableColumn gradesCol = new TableColumn("Grades");
        gradesCol.setMinWidth(80);
        gradesCol.setCellValueFactory(new PropertyValueFactory<Report, TextField>("text"));
        table.getColumns().add(gradesCol);

        TableColumn<Report, VBox> includeExclude = new TableColumn<>("Include/Exclude");
        includeExclude.setCellValueFactory(new PropertyValueFactory<>("rb"));
        table.getColumns().add(includeExclude);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.getChildren().addAll(label, table, button, l);
        button.setOnAction(this::check);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        return scene;
    }
    //This will check if the student number is correct
    public static boolean login(String t) {
        String[] list = t.split("");
        int num = list.length;
        Boolean login = false;
        //If the number has the length of 6 or 7, check if it has only numbers
        if (num == 6 || num == 7) {
            for (int i = 0; i < num; i++) {
                login = false;
                for (int j = 0; j <= 9; j++) {
                    if (list[i].equals(j + "")) {
                        j = 10;
                        login = true;}}

                if (login == false) {
                    i = num;}}}
        //If it doesn't have the length of 6 or 7, then it is incorrect
        else {
            login = false;}

        return login;

    }
    //Below are methods to calculate the average
    private static int n = 0;

    public static void num() {
        n += 1;
    }

    public static void subNum() {
        n -= 1;
    }

    private static int g = 0;

    public static void grade(int gr) {
        g = g + gr;
    }

    public static void subGrade(int gr) {
        g = g - gr;
    }

    private static int average;

    public static String getAverage() {
        if (n == 0) {
            return ("Please check a box and enter the grade");}

        else {
            average = g / n;
            return ("Your average is " + average);}}

    public static class Report {
        private String handle = "";
        private CheckBox courseCheck = new CheckBox();
        private TextField text = new TextField();
        private RadioButton include1 = new RadioButton("Include");
        private RadioButton exclude1 = new RadioButton("Exclude");
        private int count=0;;
        private ToggleGroup tg = new ToggleGroup();
        private VBox rb = new VBox();

        private Report(String h) {
            handle = h;
            exclude1.setToggleGroup(tg);
            include1.setToggleGroup(tg);
            rb.getChildren().addAll(exclude1, include1);
            //Make third and fourth column uneditable
            text.setEditable(false);
            exclude1.setDisable(true);
            include1.setDisable(true);
            //See if the checkbox is on action
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
                public void handle(ActionEvent e) {
                    //If the checkbox is selected, then make the rest of the columns editable
                    if (courseCheck.isSelected()) {
                        text.setEditable(true);
                        exclude1.setDisable(false);
                        include1.setDisable(false);}

                    //If its not selected or deselected, then it would remove any input taken
                    else {
                        text.setEditable(false);
                        exclude1.setDisable(true);
                        include1.setDisable(true);
                        exclude1.setSelected(false);
                        include1.setSelected(false);
                    }

                    text.setText("");
                }
            };

            courseCheck.setOnAction(event);

            //If the include button is selected then it would include the data in the grade
            include1.setOnAction(e -> {
                grade(Integer.parseInt(text.getText()));
                num();
                count ++;});

            //If the exclude button is selected then it would not count the data
            exclude1.setOnAction(e -> {
                if (text.getText().equals("")) {}

                else if (count > 0) {
                    subGrade(Integer.parseInt(text.getText()));
                    subNum();}
                else{
                    count = 0;}});}

        //The rest are setters and getters
        public String getHandle() {
            return handle;
        }

        public CheckBox getCourseCheck() {
            return courseCheck;
        }

        public TextField getText() {
            return text;
        }

        public void setText(TextField t) {
            text = t;
        }
        public VBox getRb() {
            return rb;
        }

        public ToggleGroup getTg() {

            return tg;
        }

        public RadioButton getExclude1() {
            return exclude1;
        }

        public void setExclude1(RadioButton e) {
            exclude1 = e;
        }

        public RadioButton getInclude1() {
            return include1;
        }

        public void setInclude1(RadioButton e) {
            include1 = e;
        }
    }}