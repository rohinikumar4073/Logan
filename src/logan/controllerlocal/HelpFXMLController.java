/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logan.controllerlocal;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.FileChooser;
import logan.Main;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class HelpFXMLController  extends BasicController implements Initializable {
    
   
    @FXML
    public void openFile(ActionEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Logon - Open file to log");
        File file = fileChooser.showOpenDialog(getStage());
        if(file!=null)
        gotoTableView(file);
    }

    private void gotoTableView(File file) {
        try {
            LogDataTableController loginController = (LogDataTableController) replaceSceneContent("tableViewLog.fxml", 800, 500);
            loginController.setApp(getApplication(), file);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    void setApp(Main application) {
        setApplication(application);
        setStage(application.getStage());
    }
 
}
