/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logan.controllerlocal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
 *
 * @author Administrator
 */
public class BasicController {

    private Main application;
    private Screen1Controller screen1Controller;

    @FXML
    private VBox vbox;

    public VBox getVbox() {
        return vbox;
    }

    public void setVbox(VBox vbox) {
        this.vbox = vbox;
    }

    public Screen1Controller getScreen1Controller() {
        return screen1Controller;
    }

    public void setScreen1Controller(Screen1Controller screen1Controller) {
        this.screen1Controller = screen1Controller;
    }
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
        this.stage=application.getStage();
    }

    @FXML
    public void openFile(ActionEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Logon - Open file to log");
        File file = fileChooser.showOpenDialog(stage);
        if(file!=null)
       
            gotoTableView(file);
       
    }

    @FXML
    public void goToHome(ActionEvent event) {

        Screen1Controller screen1 = null;
        try {
            screen1 = (Screen1Controller) replaceSceneContent("Screen1.fxml", 800, 500);
        } catch (Exception ex) {
            Logger.getLogger(HelpFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        screen1.setApp(getApplication());
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

    public void createTableView(File file) throws FileNotFoundException, IOException {
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

    public String getNonNull(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }

    public String getNonNull(Date data) {
        if (data == null) {
            return "";
        } else {
            return data.toLocaleString();
        }
    }

    public String getNonNull(Level data) {
        if (data == null) {
            return "";
        } else {
            return data.toString();
        }
    }

    public ObservableList createObservableArrayList(LogData[] logdataArray, ObservableList<LoggerData> list) {
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

    public Initializable replaceSceneContent(String fxml, Integer width, Integer height) throws Exception {
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

    public void gotoTableView(File file) {
        try {
            LogDataTableController loginController = (LogDataTableController) replaceSceneContent("tableViewLog.fxml", 800, 500);
            loginController.setApp(getApplication(), file);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }    }

}
