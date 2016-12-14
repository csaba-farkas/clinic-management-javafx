package com.clinigment.application.controller;

import com.clinigment.application.abstracts.LayoutController;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 *
 * @author lastminute84
 */
public class AddNewPatientController2 extends LayoutController implements Initializable {
    
    private static AddNewPatientController2 instance;
    
    public static AddNewPatientController2 getInstance() {
        if(instance == null) {
            instance = new AddNewPatientController2();
        }
        return instance;
    }
    
    //Fields
    private String title;
    private String firstName;
    private String lastName;
    private String middleName;
    private String dob;
    private String pps;
    private String nextOfKin;
    private String nextOfKinContact;
    private String address1;
    private String address2;
    private String address3;
    private String city;
    private String county;
    private String country;
    private String homePhone;
    private String mobilePhone;
    private String email;
    private LocalDate dobDate;
    
    public AddNewPatientController2() {
        super();
    }
    
    @FXML
    private Label   confPatientId, 
                    confPatientTitle, 
                    confPatientFirstName, 
                    confPatientLastName,
                    confPatientDOB,
                    confPatientNextOfKin, 
                    confPatientMiddleName,
                    confPatientPPSNumber,
                    confPatientNextOfKinContact,
                    confAddressLine1,
                    confAddressLine2,
                    confAddressLine3,
                    confCity,
                    confCounty,
                    confHomePhone,
                    confMobilePhone,
                    confEmail,
                    confCountry;
    
    @FXML
    private Button  topFinishButton,
                    bottomFinishButton;
                    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        instance = this;
        
        // Get values from Map
        title = (String) AddNewPatientController1.getInstance().getData(TITLE_PATIENT);
        firstName = (String) AddNewPatientController1.getInstance().getData(FIRST_NAME_PATIENT);
        lastName = (String) AddNewPatientController1.getInstance().getData(LAST_NAME_PATIENT);
        middleName = (String) AddNewPatientController1.getInstance().getData(MIDDLE_NAME_PATIENT);
        dob = (String) AddNewPatientController1.getInstance().getData(DOB_PATIENT);
        dobDate = (LocalDate) getData(DOB_DATE_PATIENT);
        pps = (String) AddNewPatientController1.getInstance().getData(PPS_NUMBER_PATIENT);
        nextOfKin = (String) AddNewPatientController1.getInstance().getData(NEXT_OF_KIN_PATIENT);
        nextOfKinContact = (String) AddNewPatientController1.getInstance().getData(NEXT_OF_KIN_CONTACT_PATIENT);
        address1 = (String) AddNewPatientController1.getInstance().getData(ADDRESS1_PATIENT);
        address2 = (String) AddNewPatientController1.getInstance().getData(ADDRESS2_PATIENT);
        address3 = (String) AddNewPatientController1.getInstance().getData(ADDRESS3_PATIENT);
        city = (String) AddNewPatientController1.getInstance().getData(CITY_PATIENT);
        county = (String) AddNewPatientController1.getInstance().getData(COUNTY_PATIENT);
        country = (String) AddNewPatientController1.getInstance().getData(COUNTRY_PATIENT);
        homePhone = (String) AddNewPatientController1.getInstance().getData(HOME_PHONE_PATIENT);
        mobilePhone = (String) AddNewPatientController1.getInstance().getData(MOBILE_PHONE_PATIENT);
        email = (String) AddNewPatientController1.getInstance().getData(EMAIL_PATIENT);
        
        ///////////////////TEST
        System.out.println(
                        "Title: " + title +
                        "\nFirst name: " + firstName + 
                        "\nLast name: " + lastName + 
                        "\nMiddle Name: " + middleName +
                        "\nDOB: " + dob +
                        "\nPPS Number: " + pps +
                        "\nNext Of Kin Name: " + nextOfKin + 
                        "\nNext Of Kin Contact: " + nextOfKinContact +
                        "\nAddress Line1: " + address1 +
                        "\nAddress Line2: " + address2 +
                        "\nAddress Line3: " + address3 + 
                        "\nCity: " + city + 
                        "\nCounty: " + county +
                        "\nCountry: " + country +
                        "\nHome Phone: " + homePhone + 
                        "\nMobile: " + mobilePhone + 
                        "\nEmail: " + email
        );
        /////////////////////END TEST
        
        //Display values
        confPatientTitle.setText(title);
        confPatientFirstName.setText(firstName);
        confPatientLastName.setText(lastName);
        confPatientDOB.setText(dob);
        confPatientNextOfKin.setText(nextOfKin);
        confPatientMiddleName.setText(middleName);
        confPatientPPSNumber.setText(pps);
        confPatientNextOfKinContact.setText(nextOfKinContact);
        confAddressLine1.setText(address1);
        confAddressLine2.setText(address2);
        confAddressLine3.setText(address3);
        confCity.setText(city);
        confCounty.setText(county);
        confHomePhone.setText(homePhone);
        confMobilePhone.setText(mobilePhone);
        confEmail.setText(email);
        confCountry.setText(country);
    }    

    @Override
    public void setLayout(Node node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @FXML
    public void finishCreatePatient() {
        //Create address first
        //Address address = new Address(address1, address2, address3, city, county, country);
        
        //Create patient
        //Patient patient = new Patient(title, firstName, lastName, middleName, dobDate, pps, nextOfKin, nextOfKinContact, address, homePhone, mobilePhone, email);
        //System.out.println(patient);
    }

    @Override
    public void setLayout(Node node, Pane container) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
