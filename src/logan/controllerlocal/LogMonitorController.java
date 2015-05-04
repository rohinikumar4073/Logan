/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logan.controllerlocal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import logan.Main;
import logan.model.Alerts;
import logan.utiliies.Filehandler;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class LogMonitorController extends BasicController implements Initializable {

    @FXML
    HBox radioGroupBox;
    @FXML
    RadioButton rb1;
    @FXML
    RadioButton rb2;
    @FXML
    HBox localAdd;
    @FXML
    HBox ipAddress;
    @FXML
    HBox password;
    @FXML
    HBox remoteFileLocation;
    @FXML
    HBox parsingPatternsOld;
    @FXML
    HBox parsingPatternsNew;
    @FXML
    HBox buttonBox;
    @FXML
    ComboBox menuButton;
    @FXML
    Button browseFiles;
    @FXML
    Button runFile;
    @FXML
    Button tailFile;
     @FXML
    Button reportFile;
     @FXML
     TextField localFileLocation;
     private File file;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        createTable();
        loadMenu();
        loadButtons();
    }

    void setApp(Main application) {
        setApplication(application);
    }

    public void createTable() {
        final ToggleGroup group = new ToggleGroup();

        if (rb1 != null && rb2 != null) {
            rb1.setToggleGroup(group);
            rb1.setUserData("local");

            rb2.setToggleGroup(group);
            rb2.setUserData("remote");

        }

        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                    Toggle old_toggle, Toggle new_toggle) {
                if (group.getSelectedToggle() != null) {

                    String selected = group.getSelectedToggle().getUserData().toString();
                    if (selected.equals("local")) {
                        openLocalForm();
                    } else if (selected.equals("remote")) {
                        openRemoteForm();
                    }
                }
            }

            private void openLocalForm() {
                localAdd.setVisible(true);
                parsingPatternsOld.setVisible(true);
                buttonBox.setVisible(true);
                ipAddress.setVisible(false);
                password.setVisible(false);
                remoteFileLocation.setVisible(false);

            }

            private void openRemoteForm() {
                localAdd.setVisible(false);
                parsingPatternsOld.setVisible(false);
                buttonBox.setVisible(true);
                ipAddress.setVisible(true);
                password.setVisible(true);
                remoteFileLocation.setVisible(true);
            }

        });

    }
    public void loadMenu(){
        
         File f = new File("./");
            String filePath;
        try {
            filePath = f.getCanonicalPath();
             filePath= filePath.concat("\\src\\logan\\resources\\patterns");
            File folder = new File(filePath);
        ArrayList filesList=listFilesForFolder(folder);
   ObservableList<String> dataList = FXCollections.observableArrayList();
        for (Iterator iterator = filesList.iterator(); iterator.hasNext();) {
            String next = (String) iterator.next();
         dataList.add(next);
            
        }
         menuButton.getItems().addAll(dataList);
        } catch (IOException ex) {
            Logger.getLogger(LogMonitorController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    
    }
    public ArrayList listFilesForFolder(final File folder) {
        ArrayList arrayList = new ArrayList();
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                arrayList.add(fileEntry.getName());
            }
        }
        return arrayList;
    }

    public String saveFilePattern(String type, String pattern, String dateFormat, String name, String charset, String fileName) {
        PrintWriter writer;
        try {
            File f = new File("./");
            String filePath = f.getCanonicalPath();
            filePath = filePath.concat("\\src\\logan\\resources\\").concat(fileName).concat(".txt");
            System.out.println(filePath);
            writer = new PrintWriter(filePath, "UTF-8");
            writer.println("type=" + type);
            writer.println("pattern=" + pattern);
            writer.println("dateFormat=" + dateFormat);
            writer.println("name=" + name);
            writer.println("charset=" + charset);

            writer.close();
        } catch (FileNotFoundException ex) {
            //   Logger.getLogger(LogMonitorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            //  Logger.getLogger(LogMonitorController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            // Logger.getLogger(LogMonitorController.class.getName()).log(Level.SEVERE, null, ex);
        }

        return "";
    }

    private void loadButtons() {
        browseFiles.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                
         FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Logon - Open file to log");
         file = fileChooser.showOpenDialog(getStage());
        if(file!=null){
         try {
             localFileLocation.setText(file.getCanonicalPath());
         } catch (IOException ex) {
             Logger.getLogger(LogMonitorController.class.getName()).log(Level.SEVERE, null, ex);
         }
        }
            } });  
 runFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
               String pattern=menuButton.getValue().toString();
                Filehandler.selectedFile="//"+pattern;
                gotoTableView(file);
 

            } });  
  tailFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
              String pattern=menuButton.getValue().toString();
                String fileLocation =localFileLocation.getText();
                 Filehandler.selectedFile="//"+pattern;
                 gotoTableView(file, "tail");

            } });  
   reportFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                 String pattern=menuButton.getValue().toString();
                String fileLocation =localFileLocation.getText();
                     gotoReportsView(file);

            } });  

    

}  private void gotoTableView(File file, String tail) {
        try {
            TailTableViewController loginController = (TailTableViewController) replaceSceneContent("TailTableView.fxml", 800, 500);
            loginController.setApp(getApplication(), file, tail);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }private void gotoReportsView(File file) {
        try {
            ReportsController controller = (ReportsController) replaceSceneContent("reports.fxml", 800, 500);
            controller.setApp(getApplication(), file);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }}