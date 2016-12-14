/*
Author:
Last Changed: 17/03/2016
Purpose:
*/
package com.clinigment.application.main;
	
import com.clinigment.application.controller.PrimaryStageController;
import com.clinigment.application.navigator.LayoutContentNavigator;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class App extends Application {
	
        public static Stage PRIMARY_STAGE = null;
        
	@Override // Setting the primary Stage Properties and adding 
	public void start(Stage primaryStage) throws Exception {
             
            PRIMARY_STAGE = primaryStage;
         
            // Setting the scene to be displayed and assigned to the stage, by linking FXML and Scene
            primaryStage.setScene(createScene(loadMainLayout()));
            
            // Setting the Propeties of stage object
            primaryStage.setTitle("Clinigment");
            primaryStage.setMaximized(true);
            primaryStage.show();
            
            
            
	}
	// Starts the program
	public static void main(String[] args) {
		launch(args);
                System.out.println("Print of class I want: " + LayoutContentNavigator.PRIMARY_STAGE.getClass());
	}
        
        
        private Pane loadMainLayout() throws IOException {
            FXMLLoader loader = new FXMLLoader();
            //What is this doing?
            Pane mainPane = (Pane) loader.load(getClass().getResourceAsStream(LayoutContentNavigator.PRIMARY_STAGE));
            
            
            // load root then set the controller and display the login screen
            PrimaryStageController controller = loader.getController();
            LayoutContentNavigator.setLayoutController(controller);
            LayoutContentNavigator.loadLayout(LayoutContentNavigator.LOGIN);
            
           
           return mainPane;
           
        }
        
        // creates a new scene object for the primary stage
        private Scene createScene(Pane mainPane) {
            Scene scene = new Scene(mainPane);
            //add the css file from the location below
            String css = getClass().getResource("/com/clinigment/application/view/metro/metrostyle/Metro-UI.css").toExternalForm();
            scene.getStylesheets().add(css);
            return scene;
        }
}
