/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logan.model;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Administrator
 */
public class Combo {
 private final SimpleStringProperty  alert; 

    public String getAlert() {
        return alert.get();
    }

    public Combo(String alert) {
this.alert = new SimpleStringProperty(alert);  
    }

    public void setAlert(String alert) {
      this.alert.set(alert);
    }
   


}
