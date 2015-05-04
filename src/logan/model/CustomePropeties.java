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
public class CustomePropeties {
   private  SimpleStringProperty  type; 

    public CustomePropeties(String type, String pattern, String dateFormat, String name, String charset) {
        this.type = new SimpleStringProperty(type);
        this.pattern = new SimpleStringProperty(pattern);
        this.dateFormat = new SimpleStringProperty(dateFormat);
        this.name = new SimpleStringProperty(name);
        this.charset = new SimpleStringProperty(charset);
    }
      private  SimpleStringProperty  pattern; 
         private  SimpleStringProperty  dateFormat; 
            private  SimpleStringProperty  name; 
               private  SimpleStringProperty  charset; 

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getPattern() {
        return pattern.get();
    }

    public void setPattern(String pattern) {
        this.pattern.set(pattern);
    }

    public String getDateFormat() {
        return dateFormat.get();
    }

    public void setDateFormat(String dateFormat) {
        this.dateFormat.set( dateFormat);
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getCharset() {
        return charset.get();
    }

    public void setCharset(String charset) {
        this.charset.set(charset);
    }
  
}
