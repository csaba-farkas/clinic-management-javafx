
package com.clinigment.application.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author csaba
 */
@XmlRootElement
public class PatientAddress implements Serializable {
    
    @XmlID
    private Long patientId;
    
    private Patient patient;
    
    private String addressLine1;
    
    private String addressLine2;
    
    private String addressLine3;
    
    private String cityTown;
    
    private String county;
    
    private String country;

    public PatientAddress(Patient patient, 
            String addressLine1, 
            String addressLine2, 
            String addressLine3, 
            String cityTown, 
            String county, 
            String country) {
        this.patientId = patient.getId();
        this.patient = patient;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.cityTown = cityTown;
        this.county = county;
        this.country = country;
    }

    public PatientAddress() {
        //Empty constructor for JPA
    }

    @XmlTransient
    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    @XmlTransient
    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patientId = patient.getId();
        this.patient = patient;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getCityTown() {
        return cityTown;
    }

    public void setCityTown(String cityTown) {
        this.cityTown = cityTown;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "PatientAddress{" + "patientId=" + patientId + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", addressLine3=" + addressLine3 + ", cityTown=" + cityTown + ", county=" + county + ", country=" + country + '}';
    }

    void update(PatientAddress patientAddress) {
        this.addressLine1 = patientAddress.getAddressLine1();
        this.addressLine2 = patientAddress.getAddressLine2();
        this.addressLine3 = patientAddress.getAddressLine3();
        this.cityTown = patientAddress.getCityTown();
        this.county = patientAddress.getCounty();
        this.country = patientAddress.getCountry();
    }
}
