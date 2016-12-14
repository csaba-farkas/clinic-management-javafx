/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clinigment.application.controller;

import com.clinigment.application.abstracts.LayoutController;
import com.clinigment.application.clients.PatientClient;
import com.clinigment.application.main.App;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.clinigment.application.model.Allergy;
import com.clinigment.application.model.Patient;
import com.clinigment.application.model.PatientAddress;
import com.clinigment.model.enums.Gender;
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


/**
 *
 * @author csaba
 */
public class EditPatientController extends LayoutController implements Initializable {


    private static EditPatientController INSTANCE;

    public static EditPatientController getInstance() {
        return INSTANCE;
    }

    //ControlFX validation support
    ValidationSupport validationSupport = new ValidationSupport();

    //@FXML
    //private ComboBox titleComboBox;                //Title combo box

    @FXML
    private ScrollPane scrollPane;

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
        scrollPane = new ScrollPane();
        Set<Node> nodes = scrollPane.lookupAll(".scroll-bar");
        for (final Node node : nodes) {
            if (node instanceof ScrollBar) {
                ScrollBar sb = (ScrollBar) node;
                sb.setValue(sb.getMin());
            }
        }

    }


    private Patient patientRef;
    /*****
     * NEW PATIENT VARIABLE
     *****/
    private Patient newPatient;

    //All Strings, will add conversion methods if necessary
    private String ID = "",
            title = "",
            firstName = "",
            middleName = "",
            lastName = "",
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


    public void putData(Patient patientIn) {
        this.patientRef = patientIn;
        setDataFromPatient();
        init();
        //update();
    }

    private void setDataFromPatient() {


        try {
            this.title = patientRef.getTitle();
            this.firstName = patientRef.getFirstName();
            if (patientRef.getMiddleName() != null || patientRef.equals("")) {
                this.middleName = patientRef.getMiddleName();
            }
            this.lastName = patientRef.getLastName();
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
            for (Allergy a : allergyList) {
                this.allergyString = "";
                this.allergyString += a.getAllergyType();
            }

        } catch (NullPointerException e) {

        }
    }

    @FXML
    public void init() {
        //System.out.println(name);
        //Assisgns from private fields
        //idDisplay.setText(ID);
        firstNameField.setText(firstName);
        lastTextField.setText(lastName);
        middleNameField.setText(middleName);
        ppsNumberField.setText(ppsNum);
        dateOfBirth.setValue(patientRef.getDateOfBirth());
        //genderDisplay.setText(gender);
        mobilePhoneField.setText(mobileNum);
        homePhoneField.setText(homeNum);
        emailField.setText(email);
        addressLine1Field.setText(addr1);
        addressLine2Field.setText(addr2);
        addressLine3Field.setText(addr3);
        cityTextField.setText(town);
        countyTextField.setText(county);
        countryTextField.setText(country);
        nextOfKinFNameField.setText(kinName);
        nextOfKinContactField.setText(kinContact);
        allergyTextArea.setText(allergyString);
        titleComboBox.setValue(title);
        genderComboBox.setValue(gender);

        String[] arr = kinName.split(" ");
        if (arr.length == 3) {
            nextOfKinFNameField.setText(arr[0]);
            nextOfKinMNameField.setText(arr[1]);
            nextOfKinLNameField.setText(arr[2]);
        }
        for (String name : arr) {

            System.out.println(name);
        }
    }


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
        } catch (NullPointerException n) {
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
        Gender gender;
        if (genString.equals(Gender.MALE.toString())) {
            gender = Gender.MALE;
        } else if (genString.equals(Gender.FEMALE.toString())) {
            gender = Gender.FEMALE;
        } else {
            gender = Gender.OTHER;
        }

        Boolean error = false;


        if (firstName == null || firstName.equals("")) {
            errorMessage += "Please provide a First Name\n";
            error = true;
        }
        if (lastName == null || lastName.equals("")) {
            errorMessage += "Please provide a Surname\n";
            error = true;
        }
        if (ppsNumber == null || ppsNumber.equals("")) {
            errorMessage += "Please provide a PPS Number\n";
            error = true;
        }
        if (mobilePhone == null || mobilePhone.equals("")) {
            errorMessage += "Please provide a valid Phone Nummber\n";
            error = true;
        } else {
            Pattern pattern = Pattern.compile("\\d{10}");
            Matcher matcher = pattern.matcher(mobilePhone);
            if (matcher.matches()) {
                System.out.println("Phone Number Valid");
            } else {
                errorMessage += "Please provide a valid Phone Nummber\n";
                error = true;
            }
        }
        if (firstName == null || firstName == "") {
            errorMessage += "Please provide a First Name\n";
            error = true;
        }


        if (error == true) {
            newAlert(errorMessage);
        } else {
            Patient SWAP = new Patient();
            SWAP.setId(patientRef.getId());
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
            SWAPAddress.setPatientId(patientRef.getId());
            SWAPAddress.setAddressLine1(addressLine1);
            SWAPAddress.setAddressLine2(addressLine2);
            SWAPAddress.setAddressLine3(addressLine3);
            SWAPAddress.setCityTown(city);
            SWAPAddress.setCountry(country);
            SWAPAddress.setCounty(county);


            SWAP.setHomePhone(homePhone);
            SWAP.setMobilePhone(mobilePhone);
            SWAP.setEmail(email);

            SWAP.setAddress(SWAPAddress);


            this.newPatient = SWAP;
            //System.out.println("New Patient: " + this.newPatient);
            SWAP = null;
        }

        return error;
    }


    private void newAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message);
        alert.initStyle(StageStyle.UTILITY);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            //Patient p = new Patient();
            //p.setId(Integer.valueOf(txtId.getText()));
            //crud.delete(p);
            //selectData();
        } else {
            //selectData();
            //auto();
            //loadConfirmScene();
            loadConfirmScene();
        }
    }

    private String allergyCompositeString;

    @FXML
    public void addAlergyToTextBox() {
        System.out.println(addAllergyTextField.getText());
        allergyCompositeString += addAllergyTextField.getText();


        //allergyTextArea.setText();
        allergyTextArea.appendText(addAllergyTextField.getText() + " : ");
    }

    public void clearForm() {
        firstNameField.clear();
        lastTextField.clear();
        middleNameField.clear();
        ppsNumberField.clear();
        //genderDisplay.setText(gender);
        mobilePhoneField.clear();
        homePhoneField.clear();
        emailField.setText(email);
        addressLine1Field.clear();
        addressLine2Field.clear();
        addressLine3Field.clear();
        cityTextField.clear();
        countyTextField.clear();
        countryTextField.clear();
        nextOfKinFNameField.clear();
        nextOfKinContactField.clear();
        allergyTextArea.clear();

        nextOfKinFNameField.clear();
        nextOfKinMNameField.clear();
        nextOfKinLNameField.clear();
    }

    @FXML
    public void goToNextScene() {
        if (!loadConfirmScene()) {
            /*MainLayoutController.getInstance().setActive(MainLayoutController.SCENES.get(MainLayoutController.VIEW_NEW_PATIENT_PANE_INDEX_5));

            //System.out.println(ViewPatientDetailsSingleController.getInstance());
            ViewPatientDetailsController controller = ViewPatientDetailsController.getInstance();
            //System.out.println("Controller from second instantiation"+ViewPatientDetailsController.getInstance());
            controller.putData(this.newPatient);*/
            loadConfirmScene();

            try {
                this.patientRef.update(this.newPatient);
                System.out.println("this.patientref: " + this.patientRef);
                System.out.println("this.newpatient: " + this.newPatient);
                this.newPatient = PatientClient.updatePatient(this.patientRef);
                MainLayoutController.getInstance().setActive(MainLayoutController.SCENES.get(MainLayoutController.PATIENT_LIST_PANE_INDEX));
                PatientTableSceneController controller = PatientTableSceneController.getInstance();
                updateTheGUI();
                clearForm();
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.initStyle(StageStyle.UTILITY);
                alert.show();
            }
        } else {
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
                        MainLayoutController.PATIENT_LIST_PANE_INDEX
                )
        );
    }

    public void updateTheGUI(){
        MainLayoutController.getInstance().setActive(MainLayoutController.SCENES.get(MainLayoutController.PATIENT_LIST_PANE_INDEX));
        PatientTableSceneController controller = PatientTableSceneController.getInstance();
        //System.out.println("Controller from second instantiation"+ViewPatientDetailsController.getInstance());

        controller.edit(this.patientRef, this.newPatient);
    }


}
