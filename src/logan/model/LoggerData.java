/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logan.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Admin
 */
public class LoggerData {
    final private StringProperty date;
    final  private SimpleStringProperty time;

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTime() {
        return time.get();
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getThreadId() {
        return threadId.get();
    }

    public void setThreadId(String threadId) {
        this.threadId.set(threadId);
    }

    public String getPriority() {
        return priority.get();
    }

    public void setPriority(String priority) {
        this.priority.set(priority);
    }

    public String getCategory() {
        return category.get();
    }

    public String getClassName() {
        return className.get();
    }

    public void setClassName(String className) {
        this.className.set(className);
    }

    public String getMethodName() {
        return methodName.get();
    }

    public void setMethodName(String methodName) {
        this.methodName.set(methodName);
    }

    public String getLineNumber() {
        return lineNumber.get();
    }

    public void setLineNumber(String lineNumber) {
        this.lineNumber.set(lineNumber);
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public String getMessage() {
        return message.get();
    }

    public LoggerData(String date, String time, String threadId, String priority, String category, String message, String className, String methodName, String lineNumber) {
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
        this.threadId = new SimpleStringProperty(threadId);
        this.priority = new SimpleStringProperty(priority);
        this.category =new SimpleStringProperty( category);
        this.message = new SimpleStringProperty(message);
        this.className = new SimpleStringProperty(className);
        this.methodName = new SimpleStringProperty(methodName);
        this.lineNumber = new SimpleStringProperty(lineNumber);
    }

    /**
     *
     */
    public void setMessage(String message) {
        this.message.get();
    }
     final private SimpleStringProperty threadId;
     final private SimpleStringProperty priority ;
  final    private SimpleStringProperty category;
     final private SimpleStringProperty message;
     final private SimpleStringProperty className;
     final private SimpleStringProperty methodName;
    final  private SimpleStringProperty lineNumber;

}
