/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 */
package logan;

import java.io.File;
import logan.controllerlocal.Screen1Controller;
import logan.controllerlocal.LoginController;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logan.model.User;
import logan.security.Authenticator;

/**
 * Main Application. This class handles navigation and user session.
 */
public class Main extends Application {

    private Stage stage;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
    private User loggedUser;
    private final double MINIMUM_WINDOW_WIDTH = 300.0;
    private final double MINIMUM_WINDOW_HEIGHT = 500.0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Main.class, (java.lang.String[]) null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            stage = primaryStage;
             File f = new File("./");
            String filePath=f.getCanonicalPath();
            filePath= filePath.concat("\\src\\logan\\images\\logan.png");
            System.out.println(filePath);
             stage.getIcons().add(new Image("file:"+filePath));
            stage.setTitle("Welcome to Logan");
            stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
            stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
            
            gotoLogin();
            primaryStage.show();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public boolean userLogging(String userId, String password) {
        if (Authenticator.validate(userId, password)) {
            loggedUser = User.of(userId);
            gotoScreen1();
            // gotoChooseFile();
            //gotoProfile();
            return true;
        } else {
            return false;
        }
    }

    private void gotoScreen1() {
        try {
           
            Screen1Controller screen1 = (Screen1Controller) replaceSceneContent("Screen1.fxml",800,500);
          
            screen1.setApp(this);
            
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void userLogout() {
        loggedUser = null;
        gotoLogin();
    }


    private void gotoLogin() {
        try {
            LoginController login = (LoginController) replaceSceneContent("login.fxml",400,600);
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Initializable replaceSceneContent(String fxml,Integer width,Integer height) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        AnchorPane page;
        try {
            page = (AnchorPane) loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page, width, height);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }

}
