
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clinigment.application.controller;

import com.clinigment.application.abstracts.LayoutController;
import com.clinigment.application.clients.EmployeeClient;
import com.clinigment.application.model.Allergy;
import com.clinigment.application.model.Employee;
import com.clinigment.application.model.Patient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

//import javafx.scene.control.TextField;

/**
 *
 * @author Cormac
 */
public class ViewEmployeeDetailsSingleController extends LayoutController implements Initializable {

    private static ViewEmployeeDetailsSingleController instance = null;

    public static ViewEmployeeDetailsSingleController getInstance() {
        if(instance == null) {
            instance =  new ViewEmployeeDetailsSingleController();
        }
        return instance;
    }
    private Employee employeeRef;

    @FXML
    private Button addAccountButton;

    //All Strings, will add conversion methods if necessary
    private String ID = "",
            title = "",
            role = "",
            name = "",
            fName = "",
            mName = "",
            lName = "",
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
            startDate = "";

    //*****ADD FXML STUFF*****
    @FXML
    private Label titleLabel, firstNameLabel, middleNameLabel, lastNameLabel, ppsNumberLabel, dateOfBirthLabel, mobileLabel,
            roleLabel, employStartLabel, homePhoneLabel, emailLabel, addressLine1Label, addressLine2Label, addressLine3Label,
            cityTownLabel, countyLabel, countryLabel;

    @FXML
    private TextArea allergyTextBox;

    public ViewEmployeeDetailsSingleController() {

        this.employeeRef = null;
    }

    public void putData(Employee employeeIn) {
        this.employeeRef = employeeIn;
        setDataFromEmployee();
        init();
        //update();
    }


    public ViewEmployeeDetailsSingleController(Employee employeeIn) {

        this.employeeRef = employeeIn;
        setDataFromEmployee();
        //nameTextField.setText(name);
    }


    private void setDataFromEmployee(){
        try {
            if (employeeRef.getMiddleName() != null || employeeRef.equals("")) {
                this.name = employeeRef.getTitle() + " " + employeeRef.getFirstName() + " " + employeeRef.getMiddleName() + " " + employeeRef.getLastName();
            } else {
                this.name = employeeRef.getTitle() + " " + employeeRef.getFirstName() + " " + employeeRef.getLastName();
            }
            System.out.println("Employee Ref: " + this.employeeRef.getFirstName());
            this.title = employeeRef.getTitle();
            this.fName = employeeRef.getFirstName();
            this.mName = employeeRef.getMiddleName();
            this.lName = employeeRef.getLastName();
            this.startDate = employeeRef.getEmployedSince().toString();
            this.role = employeeRef.getRoleString();
            this.ppsNum = employeeRef.getPpsNumber();
            this.dob = employeeRef.getDateOfBirth().toString(); //*****CHECK LAYOUT OF DOB MAKES SENSE*****
            //this.gender = "" + employeeRef.get(); //*****MAP INT TO CORRECT GENDERS*****
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

            this.ID = employeeRef.getId().toString();

        } catch (NullPointerException e) {

        }
    }

    @FXML
    public void init() {
        System.out.println(name);
        //Assisgns from private fields


        titleLabel.setText(title);
        firstNameLabel.setText(fName);
        middleNameLabel.setText(mName);
        lastNameLabel.setText(lName);
        ppsNumberLabel.setText(ppsNum);
        dateOfBirthLabel.setText(dob);
        mobileLabel.setText(mobileNum);
        roleLabel.setText(role);
        employStartLabel.setText(startDate);
        homePhoneLabel.setText(homeNum);
        emailLabel.setText(email);
        addressLine1Label.setText(addr1);
        addressLine2Label.setText(addr2);
        addressLine3Label.setText(addr3);
        cityTownLabel.setText(town);
        countyLabel.setText(county);
        countryLabel.setText(country);
        //employStartLabel.setText(startDate);

        this.addAccountButton.setOnAction(event -> {
            MainLayoutController.getInstance().setActive(
                    MainLayoutController.SCENES.get(
                            MainLayoutController.ADD_NEW_USERACCOUNT
                    )
            );
            if(this.employeeRef != null) {
                AddUserAccountSceneController.getInstance().setEmployeeId(this.employeeRef.getId());
                AddUserAccountSceneController.getInstance().setEmpName(this.employeeRef.getFirstName() + " " + this.employeeRef.getLastName());
            }
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        init();
    }

    @FXML
    public void goBack() {
        MainLayoutController.getInstance().setActive(
                MainLayoutController.SCENES.get(
                        MainLayoutController.EMPLOYEE_LIST_PANE_INDEX
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

    @FXML
    public void confirmEmployee() {
        try {
            EmployeeClient.createEmployee(this.employeeRef);
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.initStyle(StageStyle.UTILITY);
            alert.show();
        }
        addNewEmployeeToDB(this.employeeRef);


        MainLayoutController.getInstance().setActive(MainLayoutController.SCENES.get(MainLayoutController.EMPLOYEE_LIST_PANE_INDEX));
        EmployeeTableSceneController controller = EmployeeTableSceneController.getInstance();
        //System.out.println("Controller from second instantiation"+ViewEmployeeDetailsController.getInstance());
        controller.update(this.employeeRef);

    }

    private void addNewEmployeeToDB(Employee newemployeeIn){



        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");

        ClientConfig cc = new ClientConfig().register(new JacksonFeature());
        cc.register(feature);

        Client client = ClientBuilder.newClient(cc);

        WebTarget postEmployee = client.target("http://ec2-52-19-236-212.eu-west-1.compute.amazonaws.com/webapi/employees");

        Response postEmployeeResponse = postEmployee.request()
                .post(Entity.json(newemployeeIn));

        switch (postEmployeeResponse.getStatus()) {
            case 200:
                System.out.println("MESSAGE: 200");
                System.out.println("All good to go");
                //receivedEmployees = postEmployeeResponse.readEntity(new GenericType<List<Employee>>(){});
                //employees = FXCollections.observableArrayList(receivedEmployees);
                break;
            case 404:
                System.out.println("MESSAGE: 404");
                break;
            case 204:
                System.out.println("MESSAGE: 204");
                break;
            default:
                System.out.println("An error occured.");
                System.out.println(postEmployeeResponse.getStatus());
                break;
        }

        postEmployeeResponse.close();
        client.close();
    }
}
