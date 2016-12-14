package com.clinigment.application.controller;

import com.clinigment.application.abstracts.LayoutController;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

/**
 * <p>Controller for the entire layout.</p>
 * @author lastminute84
 */
public class PrimaryStageController extends LayoutController {
    
    private static PrimaryStageController instance;
    
    public static PrimaryStageController getInstance() {
        if(instance == null) {
            instance = new PrimaryStageController();
        }
        return instance;
    }
    
    public PrimaryStageController() {
        super();
    }
    
    //Holder of switchable scene
    @FXML
    private StackPane layout;
    
    /**
        * <p>Method switches the layout node displayed in the 'layout' with a new Node.</p>
        * @param node 
        */
    @Override
    public void setLayout(Node node) {
        layout.getChildren().setAll(node);
    }

    @Override
    public void setLayout(Node node, Pane container) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
