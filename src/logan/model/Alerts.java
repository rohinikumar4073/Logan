/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logan.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;

/**
 *
 * @author Administrator
 */
public class Alerts {
 private final SimpleStringProperty  alert; 

    public String getAlert() {
        return alert.get();
    }

    public Alerts(String alert) {
this.alert = new SimpleStringProperty(alert);  
    }

    public void setAlert(String alert) {
      this.alert.set(alert);
    }
   


}
