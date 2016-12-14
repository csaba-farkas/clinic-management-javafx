package com.clinigment.application.navigator;

import com.clinigment.application.abstracts.LayoutController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

/**
 * <p>This navigator is used to switch scenes in the primaryStage.</p>
 * @author lastminute84
 */
public class LayoutContentNavigator {
    
    //Scenes that can be used are declared and stored in constants

    public static final String VIEW_EMPLOYEE_SCENE = "/com/clinigment/application/view/ViewEmployeeDetails.fxml";
    public static final String VIEW_EMPLOYEE_SCENE_SINGLE = "/com/clinigment/application/view/ViewEmployeeDetailsSingle.fxml";
    public static final String PRIMARY_STAGE = "/com/clinigment/application/view/primary_stage.fxml";            
    public static final String LOGIN = "/com/clinigment/application/view/login.fxml";                              
    public static final String MAIN_LAYOUT = "/com/clinigment/application/view/main_layout.fxml";
    public static final String ADD_NEW_EMPLOYEE = "/com/clinigment/application/view/AddNewEmployeeScene.fxml";
    public static final String VIEW_PATIENT_SCENE_SINGLE = "/com/clinigment/application/view/ViewPatientDetailsSingle.fxml";
    public static final String ADD_NEW_APPOINTMENT = "/com/clinigment/application/view/AddNewAppointmentScene.fxml";
    public static final String EDIT_APPOINTMENT = "/com/clinigment/application/view/AppointmentEditScene.fxml";
    public static final String VIEW_APPOINTMENT = "/com/clinigment/application/view/AppointmentDetailsScene.fxml";
    public static final String APPOINTMENT_TABLE_SCENE = "/com/clinigment/application/view/AppointmentTableScene.fxml";
    public static final String VIEW_PATIENT_SCENE = "/com/clinigment/application/view/ViewPatientDetails.fxml";
    public static final String ADD_NEW_PATIENT_SCENE_1 = "/com/clinigment/application/view/AddNewPatient.fxml";
    public static final String ADD_NEW_PATIENT_SCENE_2 = "/com/clinigment/application/view/AddNewPatient2.fxml";
    public static final String EDIT_PATIENT_SCENE_1 = "/com/clinigment/application/view/EditPatient.fxml";
    public static final String PATIENT_TABLE_SCENE = "/com/clinigment/application/view/PatientTableScene.fxml";
    public static final String HOME_SCENE = "/com/clinigment/application/view/Home.fxml";
    public static final String EDIT_EMPLOYEE_SCENE = "/com/clinigment/application/view/EditEmployee.fxml";
    public static final String EMPLOYEE_TABLE_SCENE = "/com/clinigment/application/view/EmployeeTableScene.fxml";
    public static final String ADD_NEW_USER_ACCOUNT_SCENE = "/com/clinigment/application/view/AddUserAccountScene.fxml";


    //Layout controller of primaryStage
    private static LayoutController layoutController;
    
  
    public static void setLayoutController(LayoutController controller) {
        layoutController = controller;
    }

    public static void loadLayout(String fxml) {
        try {
            layoutController.setLayout(FXMLLoader.load(LayoutContentNavigator.class.getResource(fxml)));
        } catch (IOException iox) {
            iox.printStackTrace();
        }
    }
    
    
    public static void loadLayout(String fxml, LayoutController controller) {
        try {
            setLayoutController(controller);
            layoutController.setLayout(FXMLLoader.load(LayoutContentNavigator.class.getResource(fxml)));
        } catch (IOException iox) {
            iox.printStackTrace();
        }
    }
    
    /**
     * 
     * @param fxml The fxml view which you want to insert into the view
     * @param controller The controller of your current view -> most of the time value = this
     * @param container The Pane is where you want to insert the fxml view (part of the page)
     */
    public static void loadLayout(String fxml, LayoutController controller, Pane container) {
        try {
            setLayoutController(controller);
            layoutController.setLayout(FXMLLoader.load(LayoutContentNavigator.class.getResource(fxml)), container);
        } catch (IOException iox) {
            iox.printStackTrace();
        }
    }
    
}
