/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logan.controllerlocal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TextField;
import logan.Main;
import logan.utiliies.Filehandler;
import pl.otros.logview.LogData;

/**
 * FXML Controller class
 *
 * @author Admin
 */
public class ReportsController  extends BasicController implements Initializable {
    private File file;

    @FXML
    private TextField filterField;

    private void createChart(File file) throws FileNotFoundException{
        try {
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
            getVbox().getChildren().add(chart);
        } catch (IOException ex) {
            Logger.getLogger(ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            setStage(application.getStage());
            createChart(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReportsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
