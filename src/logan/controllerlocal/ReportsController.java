/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logan.controllerlocal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logan.Main;
import logan.model.LoggerData;
import logan.utiliies.Filehandler;
import pl.otros.logview.LogData;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class ReportsController implements Initializable {

    private File file;
    private Stage stage;

    @FXML
    private TextField filterField;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Main getApplication() {
        return application;
    }

    public void setApplication(Main application) {
        this.application = application;
    }
    private Main application;

    @FXML
    private VBox vbox;
    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    @FXML
    public void openFile(ActionEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Logon - Open file to log");
        File file = fileChooser.showOpenDialog(stage);
        createTableView(file);
    }
    private void createChart(File file) throws FileNotFoundException{
    int infocount=0;
    int debugcount=0;
    int errorcount=0;
     LogData[] logdataArray = new Filehandler().readFile(file);
        for (int i = 0; i < logdataArray.length; i++) {
           LogData logData= logdataArray[i];
           if(logData.getLevel().toString().equals("INFO")){
           infocount++;
                   
           }else if(logData.getLevel().toString().equals("DEBUG")){
           debugcount++;
           }else if(logData.getLevel().toString().equals("ERROR")){
           errorcount++;
           }  
       
            
        }
      PieChart chart=   createChart(infocount,errorcount,debugcount);
         vbox.getChildren().add(chart);
    }
    
    protected PieChart createChart(int infoCount,int errorCount,int debugCount) {
        final PieChart pc = new PieChart(FXCollections.observableArrayList(
            new PieChart.Data("Information", infoCount),
            new PieChart.Data("Error", errorCount),
            new PieChart.Data("Debug", debugCount)
           
        ));
        // setup chart
        pc.setId("BasicPie");
        pc.setTitle("Distribution of Events");
        return pc;
    }

    private void createTableView(File file) throws FileNotFoundException {
        LogData[] logdataArray = new Filehandler().readFile(file);
        TableView tableView = new TableView();
        TableColumn timeStamp = new TableColumn("Time Stamp");
        TableColumn priority = new TableColumn("Priority");
        TableColumn thread = new TableColumn("Thread");
        TableColumn message = new TableColumn("Message");
        TableColumn className = new TableColumn("ClassName");
        TableColumn method = new TableColumn("Method");
        TableColumn lineNumber = new TableColumn("Line Number");
        timeStamp.setCellValueFactory(
                new PropertyValueFactory<LoggerData, String>("date"));
        priority.setCellValueFactory(
                new PropertyValueFactory<LoggerData, String>("priority"));
        thread.setCellValueFactory(
                new PropertyValueFactory<LoggerData, String>("threadId"));
        message.setCellValueFactory(
                new PropertyValueFactory<LoggerData, String>("message"));
        className.setCellValueFactory(
                new PropertyValueFactory<LoggerData, String>("className"));
        method.setCellValueFactory(
                new PropertyValueFactory<LoggerData, String>("method"));
        lineNumber.setCellValueFactory(
                new PropertyValueFactory<LoggerData, String>("lineNumber"));
        vbox.getChildren().add(tableView);
        final ObservableList<LoggerData> data = createObservableArrayList(logdataArray, FXCollections.observableArrayList());

        tableView.getColumns().addAll(timeStamp, priority, thread, message, className, method, lineNumber);

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<LoggerData> filteredData = new FilteredList<>(data, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(logData -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (logData.getPriority().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches first name.
                } else if (logData.getMessage().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else if (logData.getDate().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                } else if (logData.getClassName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        tableView.setItems(filteredData);

    }

    private String getNonNull(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }

    private String getNonNull(Date data) {
        if (data == null) {
            return "";
        } else {
            return data.toLocaleString();
        }
    }

    private String getNonNull(Level data) {
        if (data == null) {
            return "";
        } else {
            return data.toString();
        }
    }

    private ObservableList createObservableArrayList(LogData[] logdataArray, ObservableList<LoggerData> list) {
        for (int i = 0; i < logdataArray.length; i++) {
            LogData logdataArray1 = logdataArray[i];
            String date = getNonNull(logdataArray1.getDate());
            String message = getNonNull(logdataArray1.getMessage());
            String priority = getNonNull(logdataArray1.getLevel());
            String lineNo = getNonNull(logdataArray1.getLine());
            String className = getNonNull(logdataArray1.getClazz());
            String method = getNonNull(logdataArray1.getMethod());
            LoggerData data = new LoggerData(date, "", "", priority, "", message, className, method, lineNo);

            list.add(data);
        }

        return list;
    }
    @FXML
    public void processLogout(ActionEvent event) {
        if (getApplication() == null) {
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
            return;
        }

        getApplication().userLogout();

    }

    

    private Initializable replaceSceneContent(String fxml, Integer width, Integer height) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page, width, height);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }

    public void goToHome(ActionEvent event) {

        Screen1Controller screen1 = null;
        try {
            screen1 = (Screen1Controller) replaceSceneContent("Screen1.fxml", 1000, 500);
        } catch (Exception ex) {
            Logger.getLogger(HelpFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        screen1.setApp(getApplication());
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void setApp(Main application, File file) {
        try {
            setApplication(application);
            this.file=file;
            this.stage=application.getStage();
            createChart(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
