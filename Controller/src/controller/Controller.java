/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author karan
 */
public class Controller extends Application {
    
        @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("MainScreen.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("MainScreen.fxml")
        );
        Parent root = (Parent) fxmlLoader.load();
        MainScreenController mainScreenController = 
                (MainScreenController) fxmlLoader.getController();
        mainScreenController.setMainScreenController(mainScreenController);
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("ControlPC");
        stage.setOnCloseRequest(e -> {
            Platform.exit();
            System.exit(0);
        });
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
