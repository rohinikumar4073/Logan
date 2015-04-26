/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logan.controllerlocal;

import logan.controllerlocal.LogDataTableController;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logan.Main;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Screen1Controller implements Initializable {

    private Stage stage;
    private Main application;

    @FXML
    private VBox vbox;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setApp(Main application) {
        setApplication(application);
        setStage(application.getStage());
    }

    private void createTable(TableView tableView) {
        loadheader(tableView);
    }

    private void loadheader(TableView table) {

    }

    @FXML
    public void openFile(ActionEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Logon - Open file to log");
        File file = fileChooser.showOpenDialog(stage);
       gotoTableView(file);
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

    public Main getApplication() {
        return application;
    }

    public void setApplication(Main application) {
        this.application = application;
    }
    
    private void gotoTableView(File file){
    try {
            LogDataTableController loginController = (LogDataTableController) replaceSceneContent("tableViewLog.fxml",1000,500);
            loginController.setApp(getApplication(),file);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
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
  
    @FXML
    public void clickedClipboard(MouseEvent event) {
    }
    
    @FXML
    public void clickedStreamLog(MouseEvent event) {
    }
    
    @FXML
    public void clickedArchives(MouseEvent event) {
    }
    
    @FXML
    public void clickedAlerts(MouseEvent event) {
    }
    
    @FXML
    public void clickedReports(MouseEvent event) {
    }
    
    @FXML
    public void clickedHelp(MouseEvent event) {
         try {
            HelpFXMLController helpController = (HelpFXMLController) replaceSceneContent("helpFXML.fxml",1000,500);
            helpController.setApp(getApplication());
            helpController.setScreen1Controller(this);
           
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
