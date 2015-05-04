/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logan.controllerlocal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import logan.Main;
import logan.model.Alerts;
import logan.model.CustomePropeties;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class ClipboardController extends BasicController implements Initializable {
public TableView tableView=new TableView();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initalizerSettings();
    }    

    void setApp(Main application) {
    }

 public ArrayList<File> listFilesForFolder(final File folder) {
       ArrayList<File> arrayList=new ArrayList<File>();
    for (final File fileEntry : folder.listFiles()) {
        if (fileEntry.isDirectory()) {
            listFilesForFolder(fileEntry);
        } else {
           arrayList.add(fileEntry);
        }
    }
    return arrayList;
}
  
final ObservableList<CustomePropeties> dataList = FXCollections.observableArrayList();

    private void initalizerSettings() {

         File f = new File("./");
            String filePath=null;
    try {
        filePath = f.getCanonicalPath();
          filePath= filePath.concat("\\src\\logan\\resources\\patterns");
            File folder = new File(filePath);
        ArrayList<File> filesList=listFilesForFolder(folder);
        setFileSize(filesList.size());
        System.out.println(filesList.size());
        for (Iterator iterator = filesList.iterator(); iterator.hasNext();) {
            File next = (File) iterator.next();
            Properties p=new Properties();
                p.load(new FileInputStream(next.getCanonicalPath()));
                String name=(p.getProperty("name"));
                 String pattern=(p.getProperty("pattern"));
                  String type=(p.getProperty("type"));
                  String dateFormat=(p.getProperty("dateFormat"));
                   String charset=(p.getProperty("charset"));
CustomePropeties cutomePropeties=new CustomePropeties(type, pattern, dateFormat, name, charset);
          dataList.add(cutomePropeties);
             
            
        }
       ;

    } catch (IOException ex) {
        Logger.getLogger(ClipboardController.class.getName()).log(Level.SEVERE, null, ex);
    }
    TableColumn type = new TableColumn("Type");
    TableColumn name = new TableColumn("name");
          TableColumn pattern = new TableColumn("pattern");
TableColumn dateFormat = new TableColumn("dateFormat");

TableColumn charset = new TableColumn("charset");
 tableView.getColumns().addAll(type,name,pattern,dateFormat,charset);
 getVbox().getChildren().add(tableView);
  type.setCellValueFactory(
                new PropertyValueFactory<CustomePropeties, String>("type"));
  name.setCellValueFactory(
                new PropertyValueFactory<CustomePropeties, String>("name"));
    pattern.setCellValueFactory(
                new PropertyValueFactory<CustomePropeties, String>("pattern"));
     dateFormat.setCellValueFactory(
                new PropertyValueFactory<CustomePropeties, String>("dateFormat"));
     charset.setCellValueFactory(
                new PropertyValueFactory<CustomePropeties, String>("charset"));
     tableView.setItems(dataList);
      final TextField addtype = new TextField();
        addtype.setPromptText("Add Type");
        addtype.setMaxWidth(type.getPrefWidth());
         final TextField addName = new TextField();
        addName.setPromptText("Add Name");
        addName.setMaxWidth(name.getPrefWidth());
         final TextField addPattern = new TextField();
        addPattern.setPromptText("Add Pattern");
        addPattern.setMaxWidth(pattern.getPrefWidth());
         final TextField addDateFormat = new TextField();
        addDateFormat.setPromptText("Add Date Format");
        addDateFormat.setMaxWidth(dateFormat.getPrefWidth());
         final TextField addCharset = new TextField();
        addCharset.setPromptText("Add Charset");
        addCharset.setMaxWidth(charset.getPrefWidth());
          final Button addButton = new Button("Add");
        VBox.setMargin(addButton, new Insets(5.0, 5.0, 2.0, 5.0));
        HBox box=new HBox();
        box.getChildren().addAll(addtype,addName,addPattern,addDateFormat,addCharset,addButton);
 getVbox().getChildren().add(box);
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                dataList.add(new CustomePropeties(addtype.getText(), addPattern.getText(),addDateFormat.getText(), addName.getText(), addCharset.getText()) );
                     saveFilePattern(addtype.getText(), addPattern.getText(),addDateFormat.getText(),addName.getText(),addCharset.getText(),"CustomeType"+fileSize);
               
               
                addtype.clear(); addPattern.clear(); addDateFormat.clear(); addName.clear(); addCharset.clear();

            }
        });

    }     
    
    public  String saveFilePattern(String type,String pattern,String dateFormat,String name,String charset,String fileName){
           PrintWriter writer;
         try {File f = new File("./");
            String filePath=f.getCanonicalPath();
            filePath= filePath.concat("\\src\\logan\\resources\\patterns\\").concat(fileName).concat(".txt");
            System.out.println(filePath);
             writer = new PrintWriter(filePath, "UTF-8");
             writer.println("type="+type);
             writer.println("pattern="+pattern);
             writer.println("dateFormat="+dateFormat);
             writer.println("name="+name);
             writer.println("charset="+charset);
     
            writer.close();
         } catch (FileNotFoundException ex) {
          //   Logger.getLogger(LogMonitorController.class.getName()).log(Level.SEVERE, null, ex);
         } catch (UnsupportedEncodingException ex) {
           //  Logger.getLogger(LogMonitorController.class.getName()).log(Level.SEVERE, null, ex);
         }catch (IOException ex) {
            // Logger.getLogger(LogMonitorController.class.getName()).log(Level.SEVERE, null, ex);
         }

           return "";
       } 

    public void setFileSize(int size) {
        fileSize=size;
    }
    int fileSize;
      
}