package logan.controllerlocal;


import logan.Main;
import logan.model.LoggerData;
import logan.utiliies.Filehandler;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.otros.logview.LogData;


public class LogDataTableController implements Initializable {
    
    private Stage stage;

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
    @FXML
    private TableView<LoggerData> logDataTable;
    @FXML
    private TableColumn<LoggerData, String> dateColumn;
    @FXML
    private TableColumn<LoggerData, String> threadColumn;
   
    @FXML
    private Label date;
    @FXML
    private Label thread;
    @FXML
    private Label priority;
    @FXML
    private Label category;
    @FXML
    private Label message;
   @FXML
    private Label className;
    @FXML
    private Label lineNumber;
    @FXML
    private Label methodName;
        

    // Reference to the main application.

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
  
    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
        }

    public void setApp(Main application,File file) throws FileNotFoundException {
        setApplication(application);
        setStage(application.getStage());
        createTableView(file);
    }

    public void setApp(Main application) {
        setApplication(application);
        setStage(application.getStage());
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

        tableView.setItems(data);
        tableView.getColumns().addAll(timeStamp, priority, thread, message, className, method, lineNumber);

        
        
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

    @FXML
    public void processLogout(ActionEvent event) {
        if (getApplication() == null) {
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
            return;
        }

        getApplication().userLogout();
    
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
      private Initializable replaceSceneContent(String fxml,Integer width,Integer height) throws Exception {
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
     
            Screen1Controller screen1=null;
        try {
            screen1 = (Screen1Controller) replaceSceneContent("Screen1.fxml",1000,500);
        } catch (Exception ex) {
            Logger.getLogger(HelpFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
          
            screen1.setApp(getApplication());
    }

}