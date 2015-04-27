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
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import logan.Main;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class Screen1Controller extends BasicController implements Initializable {

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
    public void tailFile(ActionEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Logon - Open file to log");
        File file = fileChooser.showOpenDialog(getStage());
        if(file!=null)
        gotoTableView(file, "tail");
    }

    public void gotoTableView(File file) {
        try {
            LogDataTableController loginController = (LogDataTableController) replaceSceneContent("tableViewLog.fxml", 800, 500);
            loginController.setApp(getApplication(), file);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void clickedClipboard(MouseEvent event) {
    }

    @FXML
    public void clickedStreamLog(MouseEvent event) throws Exception {
         LogMonitorController logMonController = (LogMonitorController) replaceSceneContent("logMonitor.fxml", 800, 500);
            logMonController.setApp(getApplication());
        
    }

    @FXML
    public void clickedArchives(MouseEvent event) {
    }

    @FXML
    public void clickedAlerts(MouseEvent event) {
    }

    @FXML
    public void clickedReports(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Logon - Open file to log");
        File file = fileChooser.showOpenDialog(getStage());
        gotoReportsView(file);
    }

    @FXML
    public void openFile(ActionEvent event) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Logon - Open file to log");
        File file = fileChooser.showOpenDialog(getStage());
        if(file!=null)
        gotoTableView(file);
    }

    @FXML
    public void clickedHelp(MouseEvent event) {
        try {
            HelpFXMLController helpController = (HelpFXMLController) replaceSceneContent("helpFXML.fxml", 800, 500);
            helpController.setApp(getApplication());
            helpController.setScreen1Controller(this);

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void gotoTableView(File file, String tail) {
        try {
            LogDataTableController loginController = (LogDataTableController) replaceSceneContent("tableViewLog.fxml", 800, 500);
            loginController.setApp(getApplication(), file, tail);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void gotoReportsView(File file) {
        try {
            ReportsController controller = (ReportsController) replaceSceneContent("reports.fxml", 800, 500);
            controller.setApp(getApplication(), file);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
