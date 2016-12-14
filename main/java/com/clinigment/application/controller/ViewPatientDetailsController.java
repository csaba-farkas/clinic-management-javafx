
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clinigment.application.controller;

import com.clinigment.application.abstracts.LayoutController;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.clinigment.application.model.Allergy;
import com.clinigment.application.model.Patient;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.stage.StageStyle;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Cormac
 */
public class ViewPatientDetailsController extends LayoutController implements Initializable {

    private static ViewPatientDetailsController instance = null;

    public static ViewPatientDetailsController getInstance() {
        if(instance == null) {
            instance =  new ViewPatientDetailsController();
        }
        return instance;
    }
    private Patient patientRef;

    //All Strings, will add conversion methods if necessary
    private String ID = "",
            name = null,
            ppsNum = null,
            dob = null,
            gender = "",
            mobileNum = null,
            homeNum = "",
            email = "",
            addr1 = null,
            addr2 = null,
            addr3 = null,
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

    public ViewPatientDetailsController() {
        instance = this;
        this.patientRef = null;
    }

    public void putData(Patient patientIn) {
        this.patientRef = patientIn;
        setDataFromPatient();
        init();
        //update();
    }


    public ViewPatientDetailsController(Patient patientIn) {

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
                        MainLayoutController.NEW_PATIENT_PANE_INDEX
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

    private String errorMessage = "Undefined Error";

    @FXML
    public void confirmPatient() {
        addNewPatientToDB(this.patientRef);

    }

    public void updateTheGUI(){
        MainLayoutController.getInstance().setActive(MainLayoutController.SCENES.get(MainLayoutController.PATIENT_LIST_PANE_INDEX));
        PatientTableSceneController controller = PatientTableSceneController.getInstance();
        //System.out.println("Controller from second instantiation"+ViewPatientDetailsController.getInstance());
        controller.update(this.patientRef);
    }

    public void clearForm(){
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

    private int addNewPatientToDB(Patient newpatientIn){
        int STATUS = -1;

        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");

        ClientConfig cc = new ClientConfig().register(new JacksonFeature());
        cc.register(feature);

        Client client = ClientBuilder.newClient(cc);

        WebTarget postPatient = client.target("http://ec2-52-19-236-212.eu-west-1.compute.amazonaws.com/webapi/patients");

        Response postPatientResponse = postPatient.request().post(Entity.json(newpatientIn));
        STATUS = postPatientResponse.getStatus();
        switch (STATUS) {

            case 201:
                System.out.println("Post Patient Status: " +postPatientResponse.getStatus() + " : "+ postPatientResponse.getStatusInfo());
                System.out.println("Entity Tag: "+ postPatientResponse.getEntityTag());
                this.patientRef = postPatientResponse.readEntity(Patient.class);
                System.out.println("Newly created patient ID: "+this.patientRef.getId());
                //receivedPatients = postPatientResponse.readEntity(new GenericType<List<Patient>>(){});
                //patients = FXCollections.observableArrayList(receivedPatients);
                newAlert("Patient has been created Successfully");
                updateTheGUI();
                break;
            default:
                System.out.println("Post Patient Status: " +postPatientResponse.getStatus() + " : "+ postPatientResponse.getStatusInfo());
                System.out.println("Entity Tag: "+ postPatientResponse.getEntityTag());
                newAlert(postPatientResponse.getEntityTag().toString());
                break;
        }

        System.out.println("FINAL Post Patient Status: " +postPatientResponse.getStatus() + " : "+ postPatientResponse.getStatusInfo());
        System.out.println("FINAL Entity Tag: "+ postPatientResponse.getEntityTag());

        postPatientResponse.close();
        client.close();

        return STATUS;
    }
    private void newAlert(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message);
        alert.initStyle(StageStyle.UTILITY);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {

        }else{

        }
    }


}
