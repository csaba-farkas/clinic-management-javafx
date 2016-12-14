/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clinigment.application.controller;

import com.clinigment.application.abstracts.LayoutController;
import com.clinigment.application.main.App;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.clinigment.application.model.Patient;
import com.clinigment.application.model.PatientAddress;
import com.clinigment.model.enums.Gender;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import org.controlsfx.validation.ValidationSupport;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


/**
 *
 * @author csaba
 */
public class AddNewPatientController extends LayoutController implements Initializable {


    private static AddNewPatientController INSTANCE;

    public static AddNewPatientController getInstance() {
        return INSTANCE;
    }

    //ControlFX validation support
    ValidationSupport validationSupport = new ValidationSupport();

    //@FXML
    //private ComboBox titleComboBox;                //Title combo box

    @FXML
    private TextField firstNameField;           //Textfield: first name

    @FXML
    private TextField lastTextField;            //TextField: last name

    @FXML
    private TextField ppsNumberField;           //TextField: PPS Number

    @FXML
    private TextField nextOfKinFNameField;       //TextField: Next of kin name

    @FXML
    private TextField nextOfKinMNameField;       //TextField: Next of kin name

    @FXML
    private TextField nextOfKinLNameField;       //TextField: Next of kin name

    @FXML
    private TextField nextOfKinContactField;    //TextField: Next of kin contact

    @FXML
    private TextField addressLine1Field;        //TextField: address line 1

    @FXML
    private TextField addressLine2Field;        //TextField: address line 2

    @FXML
    private TextField mobilePhoneField;         //TextField: mobile phone

    @FXML
    private TextField emailField;               //TextField: email

    //TODO
    //After database integration, finish patient ID
    @FXML
    private TextField patientId;                //TextField: patient ID

    @FXML
    private DatePicker dateOfBirth;             //DatePicker: DOB

    @FXML
    private TextField middleNameField;          //TextField: middle name

    @FXML
    private TextField addressLine3Field;        //TextField: address line 3

    @FXML
    private TextField homePhoneField;            //TextField: home phone

    @FXML
    private TextField countyTextField;               //ComboBox: counties

    @FXML
    private TextField countryTextField;               //ComboBox: counties

    @FXML
    private TextField cityTextField;                //ComboBox: countries

    @FXML
    private Button topNextButton;

    @FXML
    private Button bottomNextButton;


    @FXML
    private AnchorPane container;

    @FXML
    private Button nextButton1,     //Next button of first scene
            backButton,      //Back button from second scene
            uploadButton;    //Upload button from second scene

    @FXML
    private Text uploadFeedBackText;    //Text next to the 'Upload' button on second scene

    @FXML
    private GridPane secondSceneGridPane;   //Grid Pane of the second page

    @FXML
    private ComboBox titleComboBox, genderComboBox;

    @FXML
    private Button addAllergyButton;

    @FXML
    private TextArea allergyTextArea;

    @FXML
    private TextField addAllergyTextField;


    @Override
    public void setLayout(Node node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLayout(Node node, Pane container) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        INSTANCE = this;

    }



    /*****NEW PATIENT VARIABLE*****/
    private Patient newPatient;


    @FXML
    public Boolean loadConfirmScene() {
        //Parse data from fields
        //Name

        //System.out.println("From Add New patient"+this);
        String title = (String) titleComboBox.getSelectionModel().getSelectedItem();
        System.out.println(titleComboBox);
        String firstName = firstNameField.getText().trim();
        String lastName = lastTextField.getText().trim();
        String middleName = middleNameField.getText().trim();



        LocalDate date = null;
        String errorMessage = "";


        //Date of birth
        try {
            date = dateOfBirth.getValue();
            String dob = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }catch (NullPointerException n){
            errorMessage += "Please provide a Date of Birth\n";
        }

        //PPS Number
        String ppsNumber = ppsNumberField.getText().trim();

        //Next of kin
        String nextOfKinFirstName = nextOfKinFNameField.getText().trim();
        String nextOfKinMiddleName = nextOfKinMNameField.getText().trim();
        String nextOfKinLastName = nextOfKinLNameField.getText().trim();
        String nextOfKinContact = nextOfKinContactField.getText().trim();

        //Address
        String addressLine1 = addressLine1Field.getText().trim();
        String addressLine2 = addressLine2Field.getText().trim();
        String addressLine3 = addressLine3Field.getText().trim();
        String city = cityTextField.getText().trim();
        String country = countryTextField.getText().trim();
        String county = countyTextField.getText().trim();


        //Phone numbers and email
        String homePhone = homePhoneField.getText().trim();
        String mobilePhone = mobilePhoneField.getText().trim();
        String email = emailField.getText().trim();
        String genString = (String) genderComboBox.getSelectionModel().getSelectedItem();
        Gender gender = Gender.OTHER;
        try{
            if(genString.equals(Gender.MALE.toString())){
                gender = Gender.MALE;
            }else if(genString.equals(Gender.FEMALE.toString())){
                gender = Gender.FEMALE;
            }
        }catch(NullPointerException e){

        }

        Boolean error = false;

        System.out.println("Is Error: " + error);


        if(firstName == null || firstName.equals("")){
            errorMessage += "Please provide a First Name\n";
            error = true;
        }
        if(lastName == null || lastName.equals("")){
            errorMessage += "Please provide a Surname\n";
            error = true;
        }
        if(ppsNumber == null || ppsNumber.equals("")){
            errorMessage += "Please provide a PPS Number\n";
            error = true;
        }
        if(mobilePhone == null || mobilePhone.equals("")){
            errorMessage += "Please provide a valid Phone Nummber\n";
            error = true;
        }
        else{
            Pattern pattern = Pattern.compile("\\d{10}");
            Matcher matcher = pattern.matcher(mobilePhone);
            if (matcher.matches()) {
                System.out.println("Phone Number Valid");
            }
            else
            {
                errorMessage += "Please provide a valid Phone Nummber\n";
                error = true;
            }
        }
        if(firstName == null || firstName == ""){
            errorMessage += "Please provide a First Name\n";
            error = true;
        }


        if(error == true){
            newAlert(errorMessage);
        }else {
            Patient SWAP = new Patient();
            SWAP.setTitle(title);
            SWAP.setFirstName(firstName);
            SWAP.setMiddleName(middleName);
            SWAP.setLastName(lastName);
            SWAP.setDateOfBirth(date);
            SWAP.setGender(gender);
            SWAP.setPpsNumber(ppsNumber);
            SWAP.setNextOfKinName(nextOfKinFirstName + " " + nextOfKinMiddleName + " " + nextOfKinLastName);
            SWAP.setNextOfKinContact(nextOfKinContact);

            PatientAddress SWAPAddress = new PatientAddress();
            SWAPAddress.setAddressLine1(addressLine1);
            SWAPAddress.setAddressLine2(addressLine2);
            SWAPAddress.setAddressLine3(addressLine3);
            SWAPAddress.setCityTown(city);
            SWAPAddress.setCountry(country);
            SWAPAddress.setCounty(county);
            SWAP.setAddress(SWAPAddress);

            SWAP.setHomePhone(homePhone);
            SWAP.setMobilePhone(mobilePhone);
            SWAP.setEmail(email);


            this.newPatient = SWAP;
            System.out.println("New Patient: " + this.newPatient);
            SWAP = null;
        }

        return error;
    }



    private void newAlert(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message);
        alert.initStyle(StageStyle.UTILITY);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            //Patient p = new Patient();
            //p.setId(Integer.valueOf(txtId.getText()));
            //crud.delete(p);
            //selectData();
        }else{
            //selectData();
            //auto();
            //loadConfirmScene();
            loadConfirmScene();
        }
    }

    private String allergyCompositeString;

    @FXML
    public void addAlergyToTextBox(){
        System.out.println(addAllergyTextField.getText());
        allergyCompositeString += addAllergyTextField.getText();


        //allergyTextArea.setText();
        allergyTextArea.appendText(addAllergyTextField.getText()+" : ");
    }

    @FXML
    public void goToNextScene() {
        if(!loadConfirmScene()) {
            MainLayoutController.getInstance().setActive(MainLayoutController.SCENES.get(MainLayoutController.VIEW_NEW_PATIENT_PANE_INDEX_5));

            ViewPatientDetailsController controller = ViewPatientDetailsController.getInstance();
            //System.out.println("Controller from second instantiation"+ViewPatientDetailsController.getInstance());
            controller.putData(this.newPatient);
        }else{
            //loadConfirmScene();

        }


    }

    @FXML
    public void goToViewScene() {/*
        MainLayoutController.getInstance().setActive(
                MainLayoutController.SCENES.get(
                        MainLayoutController.VIEW_NEW_PATIENT_PANE_INDEX_5
                )
        );
        */
        loadConfirmScene();

    }

    //Second scene stuff
    public void setUpSecondScene() {
        uploadFeedBackText.setText("");
        uploadButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("PDF Documents", "*.pdf");
            fileChooser.getExtensionFilters().add(extensionFilter);
            File file = fileChooser.showOpenDialog(App.PRIMARY_STAGE);
            uploadFeedBackText.setText(file.getName() + " is selected");
        });
    }

    @FXML
    public void goBack() {
        MainLayoutController.getInstance().setActive(
                MainLayoutController.SCENES.get(
                        MainLayoutController.NEW_PATIENT_PANE_INDEX
                )
        );
    }

}
