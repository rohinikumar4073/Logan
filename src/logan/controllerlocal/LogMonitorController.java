/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logan.controllerlocal;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import logan.Main;

/**
 * FXML Controller class
 *
 * @author Administrator
 */
public class LogMonitorController extends BasicController  implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    void setApp(Main application) {
        setApplication(application);
    }
    
}
