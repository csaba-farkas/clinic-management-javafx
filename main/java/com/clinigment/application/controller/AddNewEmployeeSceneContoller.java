
package com.clinigment.application.controller;

import com.clinigment.application.abstracts.LayoutController;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.clinigment.application.model.*;
import com.clinigment.model.enums.EmpRole;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Popup;
import javafx.stage.StageStyle;


public class AddNewEmployeeSceneContoller extends LayoutController implements Initializable {
    // Hold instance of this class
    private static AddNewEmployeeSceneContoller instance;

    // reference xml objects in code aka instance variables
    @FXML private DatePicker dateOfBirthComboBox;
    @FXML private DatePicker employStartComboBox;
    @FXML private ComboBox titleComboBox, roleComboBox;
    @FXML private TextField firstNameField;
    @FXML private TextField middleNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField ppsNumberField;
    @FXML private Button nextButton;
    @FXML private Button clearButton;
    @FXML private TextField mobileField;
    @FXML private TextField homePhoneField;
    @FXML private TextField emailField;

    //*****ADDRESS*****//
    @FXML private TextField addressLine1Field;
    @FXML private TextField addressLine2Field;
    @FXML private TextField addressLine3Field;
    @FXML private TextField cityTownField;
    @FXML private TextField countyField;
    @FXML private TextField countryField;
    //*****ADDRESS*****//

    // used to check the data is valid by value and disable next button if one is set to false

    Popup calenderMessage;
    Alert alert;
    LocalDate date = LocalDate.now();

    /*
     private boolean dateOfBirthComboBoxOk;
     private boolean firstNameFieldOk;
     private boolean middleNameFieldOk;
     private boolean lastNameFieldOK;
     private boolean ppsNumberFieldOk;

     private boolean mobileFieldOk;
     private boolean homePhoneFieldOk;
     private boolean emailFieldOk;
     private boolean addressLine1FieldOK;
     private boolean addressLine2FieldOk;

     private boolean addressLine3FieldOk;
     private boolean cityTownFieldOk;
     private boolean countyFieldOk;
     private boolean countryFieldOK;
    */
    private String ID = "",
            title = "",
            firstName = "",
            middleName = "",
            lastName = "",
            role = "",
            ppsNum = "",
            dob = "",
            mobileNum = "",
            homeNum = "",
            email = "",
            addr1 = "",
            addr2 = "",
            addr3 = "",
            town = "",
            county = "",
            country = "",
            employedSince = "",
            employedUntil = "";

    List<Appointment> appointmentList = null;
    UserAccount userAccount;

    // get the instance of this class or create a new one if no one exists
    public static AddNewEmployeeSceneContoller getInstance() {
        if(instance == null) {
            instance = new AddNewEmployeeSceneContoller();
        }
        return instance;
    }


    public AddNewEmployeeSceneContoller() {
        super();
    }

    @Override
    public void setLayout(Node node) {
        //  throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLayout(Node node, Pane container) {
        //    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
      alert = new Alert(Alert.AlertType.INFORMATION);
      alert.initStyle(StageStyle.UTILITY);
      alert.setTitle(null);
      alert.setHeaderText("Invalid Date");
      alert.setContentText("You cannot choose a date from the Future");
      DialogPane dialogPane = alert.getDialogPane();
      dialogPane.getStylesheets().add(
      getClass().getResource("/com/clinigment/application/view/metro/metrostyle/Metro-UI.css").toExternalForm());
      dialogPane.getStyleClass().add("myDialog");


        dateOfBirthComboBox.setValue(LocalDate.now());
  /*      ChangeListener<Object> listener = new FormElementChangeStateListener();
            appointmentStartTime.valueProperty().addListener(listener);
            appointmentEndTime.valueProperty().addListener(listener);
            firstNameField.textProperty().addListener(listener);
            lastNameField.textProperty().addListener(listener);
            middleNameField.textProperty().addListener(listener);
            ppsNumberField.textProperty().addListener(listener);
            ppsNumberField.textProperty().addListener(listener);

            contactNumberField.textProperty().addListener(listener);
            treatmentField.textProperty().addListener(listener);
            existingPatientCheckBox.selectedProperty().addListener(listener);

        ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(0.0);

            nextButton.setEffect(colorAdjust);

            nextButton.setOnMouseEntered(e -> {

                Timeline fadeInTimeline = new Timeline(
                        new KeyFrame(Duration.seconds(0),
                                new KeyValue(colorAdjust.brightnessProperty(), colorAdjust.brightnessProperty().getValue(), Interpolator.LINEAR)),
                                new KeyFrame(Duration.seconds(.60), new KeyValue(colorAdjust.brightnessProperty(), -.35, Interpolator.LINEAR)
                                ));
                fadeInTimeline.setCycleCount(1);
                fadeInTimeline.setAutoReverse(false);
                fadeInTimeline.play();

            });

            nextButton.setOnMouseExited(e -> {

                Timeline fadeOutTimeline = new Timeline(
                        new KeyFrame(Duration.seconds(0),
                                new KeyValue(colorAdjust.brightnessProperty(), colorAdjust.brightnessProperty().getValue(), Interpolator.LINEAR)),
                                new KeyFrame(Duration.seconds(.60), new KeyValue(colorAdjust.brightnessProperty(), 0, Interpolator.LINEAR)
                                ));
                fadeOutTimeline.setCycleCount(1);
                fadeOutTimeline.setAutoReverse(false);
                fadeOutTimeline.play();

            });

            ColorAdjust colorAdjust1 = new ColorAdjust();
            colorAdjust1.setBrightness(0.0);

            clearButton.setEffect(colorAdjust1);

            clearButton.setOnMouseEntered(e -> {

                Timeline fadeInTimeline = new Timeline(
                        new KeyFrame(Duration.seconds(0),
                                new KeyValue(colorAdjust1.brightnessProperty(), colorAdjust1.brightnessProperty().getValue(), Interpolator.LINEAR)),
                                new KeyFrame(Duration.seconds(.60), new KeyValue(colorAdjust1.brightnessProperty(), -.35, Interpolator.LINEAR)
                                ));
                fadeInTimeline.setCycleCount(1);
                fadeInTimeline.setAutoReverse(false);
                fadeInTimeline.play();

            });

            clearButton.setOnMouseExited(e -> {

                Timeline fadeOutTimeline = new Timeline(
                        new KeyFrame(Duration.seconds(0),
                                new KeyValue(colorAdjust1.brightnessProperty(), colorAdjust1.brightnessProperty().getValue(), Interpolator.LINEAR)),
                                new KeyFrame(Duration.seconds(.60), new KeyValue(colorAdjust1.brightnessProperty(), 0, Interpolator.LINEAR)
                                ));
                fadeOutTimeline.setCycleCount(1);
                fadeOutTimeline.setAutoReverse(false);
                fadeOutTimeline.play();

            });
        */
    }


    public void clearForm(){

        ppsNumberField.clear();
        firstNameField.clear();
        lastNameField.clear();
        middleNameField.clear();
        mobileField.clear();
        dateOfBirthComboBox.setValue(LocalDate.now());
        homePhoneField.clear();
        emailField.clear();
        addressLine1Field.clear();
        addressLine2Field.clear();
        addressLine3Field.clear();
        cityTownField.clear();
        countyField.clear();
        countryField.clear();

    }

    /*****NEW PATIENT VARIABLE*****/
    private Employee newEmployee;


    @FXML
    public Boolean loadConfirmScene() {
        String title = (String) titleComboBox.getSelectionModel().getSelectedItem();
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String middleName = middleNameField.getText().trim();

        LocalDate date = null;
        String errorMessage = "";

        //Date of birth
        try {
            date = dateOfBirthComboBox.getValue();
            String dob = date.format(DateTimeFormatter.ISO_LOCAL_DATE);
        }catch (NullPointerException n){
            errorMessage += "Please provide a Date of Birth\n";
        }

        LocalDate startDate = null;
        try {
            startDate = employStartComboBox.getValue();
        }catch (NullPointerException n){
            errorMessage += "Please provide an Employee Start Time\n";
        }



        //PPS Number
        String ppsNumber = ppsNumberField.getText().trim();


        //Address
        String addressLine1 = addressLine1Field.getText().trim();
        String addressLine2 = addressLine2Field.getText().trim();
        String addressLine3 = addressLine3Field.getText().trim();
        String city = cityTownField.getText().trim();
        String country = countryField.getText().trim();
        String county = countyField.getText().trim();
        System.out.println("Csaba LOG: " + addressLine1 + ", " + addressLine2 + ", " + addressLine3 + ", " + city);


        //Phone numbers and email
        String homePhone = homePhoneField.getText().trim();
        String mobilePhone = mobileField.getText().trim();
        String email = emailField.getText().trim();
        String roleString = (String) roleComboBox.getSelectionModel().getSelectedItem();
/*
        <String fx:value="DENTIST" />
        <String fx:value="HYGIENIST" />
        <String fx:value="RECEPTIONIST" />
        <String fx:value="DENTAL_NURSE" />
        <String fx:value="MANAGER" />
        <String fx:value="ADMIN" />
        <String fx:value="OTHER" />
        */
        EmpRole role = EmpRole.OTHER;
        try {
            if (roleString.equals(EmpRole.ADMIN.toString())) {
                role = EmpRole.ADMIN;
            } else if (roleString.equals(EmpRole.HYGIENIST.toString())) {
                role = EmpRole.HYGIENIST;
            } else if (roleString.equals(EmpRole.RECEPTIONIST.toString())) {
                role = EmpRole.RECEPTIONIST;
            } else if (roleString.equals("DENTAL NURSE")) {
                role = EmpRole.DENTAL_NURSE;
            } else if (roleString.equals(EmpRole.MANAGER.toString())) {
                role = EmpRole.MANAGER;
            } else if (roleString.equals(EmpRole.DENTIST.toString())) {
                role = EmpRole.DENTIST;
            }
        }catch(NullPointerException e){

        }


        Boolean error = false;


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
            errorMessage += "Please provide a valid Phone Number\n";
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
                errorMessage += "Please provide a valid Phone Number\n";
                error = true;
            }
        }




        if(error == true){
            newError(errorMessage);
        }else {
            try {
                Employee SWAP = new Employee();

                SWAP.setTitle(title);
                SWAP.setRole(role);
                SWAP.setFirstName(firstName);
                SWAP.setMiddleName(middleName);
                SWAP.setLastName(lastName);
                SWAP.setDateOfBirth(date);
                SWAP.setPpsNumber(ppsNumber);
                SWAP.setEmployedSince(startDate);

                SWAP.setHomePhone(homePhone);
                SWAP.setMobilePhone(mobilePhone);
                SWAP.setEmail(email);

                EmployeeAddress SWAPAddress = new EmployeeAddress();
                SWAPAddress.setAddressLine1(addressLine1);
                SWAPAddress.setAddressLine2(addressLine2);
                SWAPAddress.setAddressLine3(addressLine3);
                SWAPAddress.setCityTown(city);
                SWAPAddress.setCountry(country);
                SWAPAddress.setCounty(county);

                SWAP.setEmployeeAddress(SWAPAddress);

                System.out.println("CSABA LOG EMPADDRESS: " + SWAP.getEmployeeAddress());

                this.newEmployee = SWAP;
                System.out.println("New Patient: " + this.newEmployee);
                SWAP = null;
            }catch(NullPointerException n){
                System.out.println(n.getMessage());
                newError("Please Fill out the required fields");
            }
        }

        return error;
    }
    //Needed for Patient: id, title, fname, mname,lname, dob, ppsNumber, employedSince, employedUntil, role, mobilePhone, homePhone, email, employeeAddress, userAccount, appointmentCollection, createdAt, updatedAt




    private void newError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait();

    }

    //private String allergyCompositeString;

    @FXML
    public void goToNextScene() throws InvocationTargetException{

        System.out.println(this.newEmployee);
        if(!loadConfirmScene()) {
            System.out.println("New Employee: "+this.newEmployee);
            System.out.println("CSABA LOG: " + this.newEmployee.getEmployeeAddress());
            MainLayoutController.getInstance().setActive(
                    MainLayoutController.SCENES.get(
                            MainLayoutController.VIEW_NEW_EMPLOYEE_PANE_INDEX
                    )
            );

            ViewEmployeeDetailsController.getInstance().putData(this.newEmployee);
        } else{
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

    @FXML
    public void goBack() {
        MainLayoutController.getInstance().setActive(
                MainLayoutController.SCENES.get(
                        MainLayoutController.NEW_PATIENT_PANE_INDEX
                )
        );
    }




}
