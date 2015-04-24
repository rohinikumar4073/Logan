/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Screen1Controller implements Initializable {
    private Stage stage;
    private Main application;

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
    
    public void openFile(ActionEvent event){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Logon - Open file to log");
        fileChooser.showOpenDialog(stage);
    }  
    public void processLogout(ActionEvent event) {
        if (getApplication() == null){
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
    
}
