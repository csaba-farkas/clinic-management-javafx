
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clinigment.application.controller;

import com.clinigment.application.abstracts.LayoutController;
import com.clinigment.application.model.Allergy;
import com.clinigment.application.model.Patient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

//import javafx.scene.control.TextField;

/**
 *
 * @author Cormac
 */
public class ViewPatientDetailsSingleController extends LayoutController implements Initializable {

    private static ViewPatientDetailsSingleController instance = null;

    public static ViewPatientDetailsSingleController getInstance() {
        if(instance == null) {
            instance =  new ViewPatientDetailsSingleController();
        }
        return instance;
    }
    private Patient patientRef;

    //All Strings, will add conversion methods if necessary
    private String ID = "",
            name = "",
            ppsNum = "",
            dob = "",
            gender = "",
            mobileNum = "",
            homeNum = "",
            email = "",
            addr1 = "",
            addr2 = "",
            addr3 = "",
            town = "",
            county = "",
            country = "",
            kinName = "",
            kinContact = "",
            allergyString = "";

    //*****ADD FXML STUFF*****
    @FXML
    private Label idDisplay, nameDisplay, ppsDisplay, dobDisplay, genderDisplay, bloodDisplay, mobileDisplay,
            homeDisplay, emailDisplay, address1Display, address2Display, address3Display, townDisplay, countyDisplay,
            countryDisplay, kinNameDisplay, kinContactDisplay;

    @FXML
    private TextArea allergyTextBox;

    public ViewPatientDetailsSingleController() {
        instance = this;
        this.patientRef = null;
    }

    public void putData(Patient patientIn) {
        this.patientRef = patientIn;
        setDataFromPatient();
        init();
        //update();
    }


    public ViewPatientDetailsSingleController(Patient patientIn) {

        this.patientRef = patientIn;


        setDataFromPatient();
        //nameTextField.setText(name);
    }

    
    private void setDataFromPatient(){
        try {
            if (patientRef.getMiddleName() != null || patientRef.equals("")) {
                this.name = patientRef.getTitle() + " " + patientRef.getFirstName() + " " + patientRef.getMiddleName() + " " + patientRef.getLastName();
            } else {
                this.name = patientRef.getTitle() + " " + patientRef.getFirstName() + " " + patientRef.getLastName();
            }
            System.out.println("Patient Ref: " + this.patientRef.getFirstName());
            this.ppsNum = patientRef.getPpsNumber();
            this.dob = patientRef.getDateOfBirth().toString(); //*****CHECK LAYOUT OF DOB MAKES SENSE*****
            this.gender = "" + patientRef.getGender(); //*****MAP INT TO CORRECT GENDERS*****
            this.mobileNum = patientRef.getMobilePhone(); //*****CHECK*****
            this.homeNum = patientRef.getHomePhone(); //*****CHECK*****
            this.email = patientRef.getEmail();
            this.homeNum = patientRef.getHomePhone();
            this.addr1 = patientRef.getAddress().getAddressLine1();
            this.addr2 = patientRef.getAddress().getAddressLine2();
            this.addr3 = patientRef.getAddress().getAddressLine3();
            this.town = patientRef.getAddress().getCityTown();
            this.country = patientRef.getAddress().getCountry();
            this.county = patientRef.getAddress().getCounty();
            this.kinName = patientRef.getNextOfKinName();
            this.kinContact = patientRef.getNextOfKinContact();
            this.ID = patientRef.getId().toString();

            List<Allergy> allergyList = patientRef.getAllergyCollection();
            for(Allergy a:allergyList){
                this.allergyString = "";
                this.allergyString += a.getAllergyType();
            }

        } catch (NullPointerException e) {

        }
    }
    
    @FXML
    public void init() {
        System.out.println(name);
        //Assisgns from private fields
        idDisplay.setText(ID);
        nameDisplay.setText(name);
        ppsDisplay.setText(ppsNum);
        dobDisplay.setText(dob);
        genderDisplay.setText(gender);
        mobileDisplay.setText(mobileNum);
        homeDisplay.setText(homeNum);
        emailDisplay.setText(email);
        address1Display.setText(addr1);
        address2Display.setText(addr2);
        address3Display.setText(addr3);
        townDisplay.setText(town);
        countyDisplay.setText(county);
        countryDisplay.setText(country);
        kinNameDisplay.setText(kinName);
        kinContactDisplay.setText(kinContact);
        allergyTextBox.setText(allergyString);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        System.out.println("My New Patient: "+ AddNewPatientController.getInstance());
        init();
        
    }

    @FXML
    public void goBack() {
        MainLayoutController.getInstance().setActive(
                MainLayoutController.SCENES.get(
                        MainLayoutController.PATIENT_LIST_PANE_INDEX
                )
        );
    }

    @Override
    public void setLayout(Node node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLayout(Node node, Pane container) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
