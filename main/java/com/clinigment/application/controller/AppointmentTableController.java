/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clinigment.application.controller;

/**
 *
 * @author C.I.T
 */

import com.clinigment.application.main.App;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.clinigment.application.model.Patient;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


/**
 * FXML Controller class
 *
 * @author lastminute84
 */
public class AppointmentTableController implements Initializable {

    private static AppointmentTableController instance;

    public static AppointmentTableController getInstance() {
        return instance;
    }

    @FXML
    private TableColumn<Patient, String> lastNameCol,
            firstNameCol,
            ppsCol,
            mobileCol,
            emailCol;

    @FXML
    private TableColumn<Patient, LocalDate> dobCol;

    @FXML
    private TableView<Patient> table;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        
        //Set columns
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        ppsCol.setCellValueFactory(new PropertyValueFactory<>("ppsNumber"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobilePhone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        table.setItems(getPatients());
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.maxWidthProperty().bind(App.PRIMARY_STAGE.widthProperty().multiply(0.825));
        table.maxHeightProperty().bind(App.PRIMARY_STAGE.heightProperty().multiply(0.8));

        table.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    System.out.println(table.getSelectionModel().getSelectedItem());
                    showPatient();
                }
            }
        });
    }
    
    public void showPatient(){
        //PatientManagementController.getInstance().addTab(table.getSelectionModel().getSelectedItem());
        //new ShowPatientController(table.getSelectionModel().getSelectedItem())
    }

    //////////////////******************************************//////////////////////////////
    //////////////////*******************************************////////////////////////////
    ///////////////// T E S T T E S T T E S T T E S T T E S T T E S T ///////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    //////////*************************\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\////////////****//
    //Get all of the patients
    public ObservableList<Patient> getPatients() {
        //ObservableList<Patient> patients = FXCollections.observableArrayList();
        
        ////PatientClient patientClient = new PatientClient();
        //GenericType<List<Patient>> gType = new GenericType<List<Patient>>() {};
        //List<Patient> patients = new ArrayList<>();
        //patients = (List<Patient>) patientClient.findAll_JSON(gType);
        ObservableList<Patient> patientList = FXCollections.observableArrayList();
        
        return patientList;
    }

}

