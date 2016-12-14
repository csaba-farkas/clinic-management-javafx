package com.clinigment.application.controller;

import com.clinigment.application.abstracts.LayoutController;
import com.clinigment.application.navigator.LayoutContentNavigator;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 * FXML Controller class
 *
 * @author lastminute84
 */
public class AddNewPatientController1 extends LayoutController implements Initializable {

    private static AddNewPatientController1 instance;

    public static AddNewPatientController1 getInstance() {
        if (instance == null) {
            instance = new AddNewPatientController1();
        }
        return instance;
    }

    public AddNewPatientController1() {
        super();
    }

    //Created an arraylist in the case we need to add more steps
    private ArrayList<StackPane> stackPaneList;

    //Pseudo class for form validation
    final PseudoClass errorClass = PseudoClass.getPseudoClass("error");

    //ControlFX validation support
    ValidationSupport validationSupport = new ValidationSupport();

    @FXML
    private StackPane container;

    @FXML
    private StackPane stackPane1;

    @FXML
    private StackPane stackPane2;

    @FXML
    private ComboBox titleCombo;                //Title combo box

    @FXML
    private TextField firstNameField;           //Textfield: first name

    @FXML
    private TextField lastNameField;            //TextField: last name

    @FXML
    private TextField ppsNumberField;           //TextField: PPS Number

    @FXML
    private TextField nextOfKinNameField;       //TextField: Next of kin name

    @FXML
    private TextField nextOfKinContactField;    //TextField: Next of kin contact

    @FXML
    private TextField addressLine1Field;        //TextField: address line 1

    @FXML
    private TextField addressLine2Field;        //TextField: address line 2

    @FXML
    private TextField cityField;                //TextField: address line 3

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
    private ComboBox<String> countyCombo;               //ComboBox: counties

    @FXML
    private ComboBox countryCombo;                //ComboBox: countries

    @FXML
    private Button topNextButton;

    @FXML
    private Button bottomNextButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;

        
        /************ INITIALIZES STACKPANE FUNCTIONALITY*************/
        //Stand in for now, should improve to make more robust
        stackPaneList = new ArrayList<StackPane>();
        stackPaneList.add(stackPane1);
        stackPaneList.add(stackPane2);

        setPane(stackPane1);
        /*************************/
        try {
            // Initialize content of titleCombo
            titleCombo.getItems().addAll(
                    "Mr",
                    "Mrs",
                    "Ms",
                    "Dr"
            );

            titleCombo.setPromptText("--Please Select--");
            validationSupport.registerValidator(titleCombo, Validator.createEmptyValidator("Title required"));

            //Initialize content of county combo
            String filePath = new File("").getAbsolutePath();
            String content = new String(Files.readAllBytes(Paths.get(filePath + "/src/res/text/irish_counties.txt")));
            String[] split = content.split("[\n ]");
            ArrayList<String> comboList = new ArrayList<>(Arrays.asList(split));
            countyCombo.getItems().addAll(comboList);
            countyCombo.setPromptText("--Please Select--");

            //Initialize content of country combo
            String content1 = new String(Files.readAllBytes(Paths.get(filePath + "/src/res/text/countries.txt")));
            String[] split1 = content1.split("[;]");
            ArrayList<String> comboList1 = new ArrayList<>(Arrays.asList(split1));
            countryCombo.getItems().addAll(comboList1);
            countryCombo.getSelectionModel().select("Ireland");

            //Initialize the DatePicker by setting today's date
            dateOfBirth.setValue(LocalDate.now());

            //Set up validation for the required text fields
            setUpValidation();

            //Add change listener to all the textfields and comboboxes
            ChangeListener<Object> listener = new FormElementChangeStateListener();

            titleCombo.valueProperty().addListener(listener);
            firstNameField.textProperty().addListener(listener);
            lastNameField.textProperty().addListener(listener);
            dateOfBirth.valueProperty().addListener(listener);
            nextOfKinNameField.textProperty().addListener(listener);
            ppsNumberField.textProperty().addListener(listener);
            nextOfKinContactField.textProperty().addListener(listener);
            addressLine1Field.textProperty().addListener(listener);
            addressLine2Field.textProperty().addListener(listener);
            cityField.textProperty().addListener(listener);
            countyCombo.valueProperty().addListener(listener);
            countryCombo.valueProperty().addListener(listener);
            mobilePhoneField.textProperty().addListener(listener);
            emailField.textProperty().addListener(listener);

        } catch (IOException ex) {
            Logger.getLogger(AddNewPatientController1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    //Handles the transition between the stackpanes, disables & moves to back all panes to back apart from one told to be show promatically
    private void setPane(StackPane nameOfPaneToShow) {
        nameOfPaneToShow.toFront();
        nameOfPaneToShow.setVisible(true);

        for (int i = 0; i < stackPaneList.size(); i++) {
            if (i != stackPaneList.indexOf(nameOfPaneToShow)) {
                stackPaneList.get(i).toBack();
                stackPaneList.get(i).setVisible(false);
            }
        }
    }

    @FXML
    public void loadConfirmScene() {
        //Parse data from fields
        //Name
        String title = (String) titleCombo.getSelectionModel().getSelectedItem();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String middleName = middleNameField.getText().trim();

        //Date of birth
        LocalDate date = dateOfBirth.getValue();
        addData(DOB_DATE_PATIENT, date);
        String dob = date.format(DateTimeFormatter.ISO_LOCAL_DATE);

        //PPS Number
        String ppsNumber = ppsNumberField.getText().trim();

        //Next of kin
        String nextOfKinName = nextOfKinNameField.getText().trim();
        String nextOfKinContact = nextOfKinContactField.getText().trim();

        //Address
        String addressLine1 = addressLine1Field.getText().trim();
        String addressLine2 = addressLine2Field.getText().trim();
        String addressLine3 = addressLine3Field.getText().trim();
        String city = cityField.getText().trim();
        String country = (String) countryCombo.getSelectionModel().getSelectedItem();
        String county = null;
        if (country.equals("Ireland")) {
            county = countyCombo.getSelectionModel().getSelectedItem();
        }

        //Phone numbers and email
        String homePhone = homePhoneField.getText().trim();
        String mobilePhone = mobilePhoneField.getText().trim();
        String email = emailField.getText().trim();

        ///////////////////TEST
        System.out.println(
                "Title: " + title
                + "\nFirst name: " + firstName
                + "\nLast name: " + lastName
                + "\nMiddle Name: " + middleName
                + "\nDOB: " + dob
                + "\nPPS Number: " + ppsNumber
                + "\nNext Of Kin Name: " + nextOfKinName
                + "\nNext Of Kin Contact: " + nextOfKinContact
                + "\nAddress Line1: " + addressLine1
                + "\nAddress Line2: " + addressLine2
                + "\nAddress Line3: " + addressLine3
                + "\nCity: " + city
                + "\nCounty: " + county
                + "\nCountry: " + country
                + "\nHome Phone: " + homePhone
                + "\nMobile: " + mobilePhone
                + "\nEmail: " + email
        );
        /////////////////////END TEST

        super.addData(TITLE_PATIENT, title);
        super.addData(FIRST_NAME_PATIENT, firstName);
        super.addData(LAST_NAME_PATIENT, lastName);
        super.addData(MIDDLE_NAME_PATIENT, middleName);
        super.addData(DOB_PATIENT, dob);
        super.addData(PPS_NUMBER_PATIENT, ppsNumber);
        super.addData(NEXT_OF_KIN_PATIENT, nextOfKinName);
        super.addData(NEXT_OF_KIN_CONTACT_PATIENT, nextOfKinContact);
        super.addData(ADDRESS1_PATIENT, addressLine1);
        super.addData(ADDRESS2_PATIENT, addressLine2);
        super.addData(ADDRESS3_PATIENT, addressLine3);
        super.addData(CITY_PATIENT, city);
        super.addData(COUNTY_PATIENT, county);
        super.addData(COUNTRY_PATIENT, country);
        super.addData(HOME_PHONE_PATIENT, homePhone);
        super.addData(MOBILE_PHONE_PATIENT, mobilePhone);
        super.addData(EMAIL_PATIENT, email);

        //Sets the second pane as the current pane when "Next" Button is selected
        setPane(stackPane2);
                
        //Commented this out, should no longer be needed
        //LayoutContentNavigator.loadLayout(LayoutContentNavigator.ADD_NEW_PATIENT2, this);
    }

    private void setUpValidation() {
        //Required comboboxes
        validationSupport.registerValidator(countyCombo, Validator.createEmptyValidator("County required"));
        validationSupport.registerValidator(countryCombo, Validator.createEmptyValidator("Country required"));
        //If country selected is not Ireland, disable counties combo
        countryCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.equals("Ireland")) {
                validationSupport.setRequired(countyCombo, false);
                countyCombo.setDisable(true);
            } else {
                validationSupport.setRequired(countyCombo, true);
                countyCombo.setDisable(false);
            }
        });

        //Required textfields
        Map<String, TextField> fieldErrors = new HashMap<>();
        fieldErrors.put("First Name Required", firstNameField);
        fieldErrors.put("Last Name Required", lastNameField);
        fieldErrors.put("PPS Number Required", ppsNumberField);
        fieldErrors.put("Next of Kin Name Required", nextOfKinNameField);
        fieldErrors.put("Next of Kin Contact Required", nextOfKinContactField);
        fieldErrors.put("Address Line 1 Required", addressLine1Field);
        fieldErrors.put("Address Line 2 Required", addressLine2Field);
        fieldErrors.put("City Required", cityField);
        fieldErrors.put("Mobile Phone Required", mobilePhoneField);
        fieldErrors.put("Email Required", emailField);

        for (Map.Entry<String, TextField> entry : fieldErrors.entrySet()) {
            validationSupport.registerValidator(entry.getValue(), Validator.createEmptyValidator(entry.getKey()));
        }

        //Date picker - DOB
        validationSupport.registerValidator(dateOfBirth, true, (Control c, LocalDate newValue)
                -> org.controlsfx.validation.ValidationResult.fromErrorIf(dateOfBirth, "Did the patient come from the future?", (LocalDate.now().compareTo(newValue) == -1)));
    }

    @FXML
    private void validatePatientDetails() {

    }

    @Override
    public void setLayout(Node node) {
        container.getChildren().setAll(node);
    }

    @Override
    public void setLayout(Node node, Pane container) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private class FormElementChangeStateListener implements ChangeListener<Object> {

        @Override
        public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {
            if (checkContactDetails() == true) {
                topNextButton.setDisable(false);
                bottomNextButton.setDisable(false);
            } else {
                topNextButton.setDisable(true);
                bottomNextButton.setDisable(true);
            }
        }

        private boolean checkContactDetails() {
            if (addressLine1Field.getText().trim().length() != 0
                    && addressLine2Field.getText().trim().length() != 0
                    && cityField.getText().trim().length() != 0
                    && mobilePhoneField.getText().trim().length() != 0
                    && emailField.getText().trim().length() != 0
                    && dateOfBirth.getValue().compareTo(LocalDate.now()) <= 0
                    && countryCombo.getSelectionModel().getSelectedIndex() >= 0) {
                if (countryCombo.getSelectionModel().getSelectedItem().equals("Ireland")) {
                    if (countyCombo.getSelectionModel().getSelectedIndex() >= 0) {
                        return true;
                    }
                } else {
                    return true;
                }
            }
            return false;
        }

        private boolean checkPersonalDetails() {
            if (titleCombo.getSelectionModel().getSelectedIndex() >= 0
                    && firstNameField.getText().trim().length() != 0
                    && lastNameField.getText().trim().length() != 0
                    && ppsNumberField.getText().trim().length() != 0
                    && nextOfKinNameField.getText().trim().length() != 0
                    && nextOfKinContactField.getText().trim().length() != 0) {
                if (countryCombo.getSelectionModel().getSelectedItem().equals("Ireland")) {
                    if (countyCombo.getSelectionModel().getSelectedIndex() >= 0) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
            return true;
        }
    }

}
