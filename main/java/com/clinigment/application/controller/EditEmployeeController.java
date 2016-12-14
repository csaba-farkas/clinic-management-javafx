/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clinigment.application.controller;

import com.clinigment.application.abstracts.LayoutController;
import com.clinigment.application.clients.EmployeeClient;
import com.clinigment.application.main.App;
import com.clinigment.application.model.*;
import com.clinigment.application.model.Employee;
import com.clinigment.model.enums.EmpRole;
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
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 *
 * @author csaba
 */
public class EditEmployeeController extends LayoutController implements Initializable {


    private static EditEmployeeController INSTANCE;

    public static EditEmployeeController getInstance() {
        return INSTANCE;
    }

    //ControlFX validation support
    ValidationSupport validationSupport = new ValidationSupport();

    //@FXML
    //private ComboBox titleComboBox;                //Title combo box

    //titleLabel, firstNameLabel, middleNameLabel, lastNameLabel, ppsNumberLabel, dateOfBirthLabel, mobileLabel,
    //roleLabel, employStartLabel, homePhoneLabel, emailLabel, addressLine1Label, addressLine2Label, addressLine3Label,
    //cityTownLabel, countyLabel, countryLabel;



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

    @FXML private DatePicker employeeEndComboBox;
    @FXML private CheckBox enableEmployEndCheckbox;




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


    private Employee employeeRef;
    /*****NEW PATIENT VARIABLE*****/
    private Employee newEmployee;

    //All Strings, will add conversion methods if necessary
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
            employedUntil = null;

    List<Appointment> appointmentList = null;
    UserAccount userAccount;



    public void putData(Employee employeeIn) {
        this.employeeRef = employeeIn;
        setDataFromEmployee();
        init();
        //update();
    }

    private void setDataFromEmployee(){



        try {
            this.title = employeeRef.getTitle();
            this.firstName = employeeRef.getFirstName();
            if (employeeRef.getMiddleName() != null || employeeRef.equals("")) {
                this.middleName = employeeRef.getMiddleName();
            }
            this.lastName = employeeRef.getLastName();
            this.ppsNum = employeeRef.getPpsNumber();
            this.dob = employeeRef.getDateOfBirth().toString(); //*****CHECK LAYOUT OF DOB MAKES SENSE*****
            //this.gender = "" + employeeRef.getGender(); //*****MAP INT TO CORRECT GENDERS*****
            this.mobileNum = employeeRef.getMobilePhone(); //*****CHECK*****
            this.homeNum = employeeRef.getHomePhone(); //*****CHECK*****
            this.email = employeeRef.getEmail();
            this.homeNum = employeeRef.getHomePhone();
            this.addr1 = employeeRef.getEmployeeAddress().getAddressLine1();
            this.addr2 = employeeRef.getEmployeeAddress().getAddressLine2();
            this.addr3 = employeeRef.getEmployeeAddress().getAddressLine3();
            this.town = employeeRef.getEmployeeAddress().getCityTown();
            this.country = employeeRef.getEmployeeAddress().getCountry();
            this.county = employeeRef.getEmployeeAddress().getCounty();
            this.role = employeeRef.getRoleString();



        } catch (NullPointerException e) {

        }
    }

    @FXML
    public void init() {
        titleComboBox.setValue(title);
        roleComboBox.setValue(employeeRef.getRoleString());

        dateOfBirthComboBox.setValue(employeeRef.getDateOfBirth());
        employStartComboBox.setValue(employeeRef.getEmployedSince());

        firstNameField.setText(firstName);
        middleNameField.setText(middleName);
        lastNameField.setText(lastName);
        ppsNumberField.setText(ppsNum);
        //dateOfBirthLabel.setText(dob);
        mobileField.setText(mobileNum);
        //employStartLabel.setText(startDate);
        homePhoneField.setText(homeNum);
        emailField.setText(email);
        addressLine1Field.setText(addr1);
        addressLine2Field.setText(addr2);
        addressLine3Field.setText(addr3);
        cityTownField.setText(town);
        countyField.setText(county);
        countryField.setText(country);
    }



    @FXML
    public Boolean loadConfirmScene() {
        //Parse data from fields
        //Name

        //System.out.println("From Add New employee"+this);
        String title = (String) titleComboBox.getSelectionModel().getSelectedItem();
        System.out.println(titleComboBox);
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


        //PPS Number
        String ppsNumber = ppsNumberField.getText().trim();


        //Address
        String addressLine1 = addressLine1Field.getText().trim();
        String addressLine2 = addressLine2Field.getText().trim();
        String addressLine3 = addressLine3Field.getText().trim();
        String city = cityTownField.getText().trim();
        String country = countryField.getText().trim();
        String county = countyField.getText().trim();


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
                //System.out.println("Phone Number Valid");
            }
            else
            {
                errorMessage += "Please provide a valid Phone Number\n";
                error = true;
            }
        }




        if(error){
            newError(errorMessage);
        }else {
            try {
                Employee SWAP = new Employee(employeeRef.getId()) ;

                SWAP.setTitle(title);
                SWAP.setRole(role);
                SWAP.setFirstName(firstName);
                SWAP.setMiddleName(middleName);
                SWAP.setLastName(lastName);
                SWAP.setDateOfBirth(date);
                SWAP.setPpsNumber(ppsNumber);
                SWAP.setEmployedSince(employStartComboBox.getValue());
                if(employeeEndComboBox.getValue()!=null){
                    SWAP.setEmployedUntil(employeeEndComboBox.getValue());
                }else{
                    SWAP.setEmployedUntil(null);
                }

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


                this.newEmployee = SWAP;



                System.out.println("OLD EMPLOYEE: "+this.employeeRef);
                System.out.println("NEW EMPLOYEE: "+this.newEmployee);
                System.out.println("SWAP EMPLOYEE:"+SWAP);

                SWAP = null;

            }catch(NullPointerException n){
                System.out.println(n.getMessage());
                newError("Please Fill out the required fields");
            }
        }

        return error;
    }



    private void newError(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.initStyle(StageStyle.UTILITY);
        alert.showAndWait();
        /*
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
        */
    }

    private void newAlert(String message){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message);
        alert.initStyle(StageStyle.UTILITY);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            //Employee p = new Employee();
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

    public void updateTheGUI(){
        MainLayoutController.getInstance().setActive(MainLayoutController.SCENES.get(MainLayoutController.EMPLOYEE_LIST_PANE_INDEX));
        EmployeeTableSceneController controller = EmployeeTableSceneController.getInstance();
        //System.out.println("Controller from second instantiation"+ViewPatientDetailsController.getInstance());

        System.out.println("BEFORE EDIT-OLD EMPLOYEE: "+this.employeeRef);
        System.out.println("BEFORE EDIT-NEW EMPLOYEE: "+this.newEmployee);

        controller.edit(this.employeeRef, this.newEmployee);
    }

    @FXML
    public void enableEmployeeTermination(){

        if(enableEmployEndCheckbox.isSelected()){
            System.out.println("ENABLE");
            employeeEndComboBox.setDisable(false);
        }else{
            System.out.println("DISABLE");
            employeeEndComboBox.setDisable(true);
        }

    }


    @FXML
    public void goToNextScene() {
        if(!loadConfirmScene()) {
            /*MainLayoutController.getInstance().setActive(MainLayoutController.SCENES.get(MainLayoutController.VIEW_NEW_PATIENT_PANE_INDEX_5));

            //System.out.println(ViewEmployeeDetailsSingleController.getInstance());
            ViewEmployeeDetailsController controller = ViewEmployeeDetailsController.getInstance();
            //System.out.println("Controller from second instantiation"+ViewEmployeeDetailsController.getInstance());
            controller.putData(this.newEmployee);*/
            //loadConfirmScene();
            //addNewEmployeeToDB(this.newEmployee);


            //System.out.println("Controller from second instantiation"+ViewEmployeeDetailsController.getInstance());
            //controller.edit(this.employeeRef, this.newEmployee);

            try {
                EmployeeClient.updateEmployee(this.newEmployee);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Employee was modified successfully.");
                alert.initStyle(StageStyle.UTILITY);
                alert.show();
                updateTheGUI();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.initStyle(StageStyle.UTILITY);
                alert.show();
            }
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


    @FXML
    public void goBack() {
        MainLayoutController.getInstance().setActive(
                MainLayoutController.SCENES.get(
                        MainLayoutController.EMPLOYEE_LIST_PANE_INDEX
                )
        );
    }

    private int addNewEmployeeToDB(Employee newEmployeeIn){
        int STATUS = -1;

        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");

        ClientConfig cc = new ClientConfig().register(new JacksonFeature());
        cc.register(feature);

        Client client = ClientBuilder.newClient(cc);

        WebTarget putEmployee = client.target("http://ec2-52-19-236-212.eu-west-1.compute.amazonaws.com/webapi/employees" + "/" + employeeRef.getId());
        System.out.println("ACCESSING URL: http://ec2-52-19-236-212.eu-west-1.compute.amazonaws.com/webapi/employees" + "/" + employeeRef.getId());

        Response putEmployeeResponse = putEmployee.request()
                .put(Entity.json(newEmployeeIn));



        STATUS = putEmployeeResponse.getStatus();
        System.out.println("Status: "+STATUS);
        switch (STATUS) {
            case 200:
                System.out.println("SUCCESS: Put Employee Status: " + putEmployeeResponse.getStatus() + " : " + putEmployeeResponse.getStatusInfo());
                System.out.println("SUCCESS: Entity Tag: " + putEmployeeResponse.getEntityTag());
                //this.newEmployee = putEmployeeResponse.readEntity(Employee.class);
                System.out.println("Newly created Employee ID: " + this.employeeRef.getId());
                newAlert("Employee has been created Successfully");
                break;
            default:
                newError("error " + STATUS +" occured\n"+"STATUS: " + putEmployeeResponse.getStatusInfo() + "\n"+ putEmployeeResponse.getEntityTag());
                System.out.println("error " + STATUS +" occured");
                System.out.println(putEmployeeResponse.getStatus());
                break;
        }

        System.out.println("FINAL Post Employee Status: " + putEmployeeResponse.getStatus() + " : " + putEmployeeResponse.getStatusInfo());
        System.out.println("FINAL Entity Tag: " + putEmployeeResponse.getEntityTag());

        putEmployeeResponse.close();
        client.close();
        return STATUS;
    }

}
