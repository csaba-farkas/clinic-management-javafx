
package com.clinigment.application.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.clinigment.model.adapters.LocalDateAdapter;
import com.clinigment.model.enums.Gender;

/**
 *
 * @author csaba
 */

@XmlRootElement
public class Patient implements Serializable {


    private Long id;
    

    private String title;
    

    private String firstName;

    private String middleName;

    @XmlTransient
    private String fullName;

    private String lastName;
   

    private String ppsNumber;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateOfBirth;
    
    @Enumerated(EnumType.STRING)
    private Gender gender;
    

    private String email;
    

    private String mobilePhone;
    

    private String homePhone;
        

    private String nextOfKinName;
    

    private String nextOfKinContact;
   

    private Timestamp createdAt;
    

    private Timestamp updatedAt;

    private List<Allergy> allergyCollection;

    private List<Appointment> appointmentCollection;

    private PatientAddress patientAddress;
    
            
    public Patient() {
        //Empty constructor for JPA
    }

    public Patient(Long id) {
        this.id = id;
    }

    public Patient(Long id, 
            String title, 
            String firstName, 
            String middleName, 
            String lastName, 
            String ppsNumber, 
            LocalDate dateOfBirth, 
            Gender gender, 
            String email, 
            String mobilePhone, 
            String homePhone, 
            String nextOfKinName, 
            String nextOfKinContact, 
            Timestamp createdAt, 
            Timestamp updatedAt) {
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.ppsNumber = ppsNumber;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.email = email;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.nextOfKinName = nextOfKinName;
        this.nextOfKinContact = nextOfKinContact;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        
        //Create new patient address object
        this.patientAddress = new PatientAddress();
        
        //Collections
        this.allergyCollection = new ArrayList<>();
        this.appointmentCollection = new ArrayList<>();
    }

    /**
     * Getter of id property.
     * 
     * @return patient id
     */
    public Long getId() {
        return id;
    }

    /**
     * Setter of id property.
     * 
     * @param id patient's id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter of title property.
     * 
     * @return patient's title
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPpsNumber() {
        return ppsNumber;
    }

    public void setPpsNumber(String ppsNumber) {
        this.ppsNumber = ppsNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public PatientAddress getAddress() {
        return patientAddress;
    }

    public void setAddress(PatientAddress address) {
        this.patientAddress = address;
    }

    public String getNextOfKinName() {
        return nextOfKinName;
    }

    public void setNextOfKinName(String nextOfKinName) {
        this.nextOfKinName = nextOfKinName;
    }

    public String getNextOfKinContact() {
        return nextOfKinContact;
    }

    public void setNextOfKinContact(String nextOfKinContact) {
        this.nextOfKinContact = nextOfKinContact;
    }

    public List<Appointment> getAppointmentCollection() {
        return appointmentCollection;
    }

    public void setAppointmentCollection(List<Appointment> appointmentCollection) {
        this.appointmentCollection = appointmentCollection;
    }
    
    public void addAppointmetn(Appointment appointment) {
        this.appointmentCollection.add(appointment);
    }
    
    public void removeAppointment(int index) {
        this.appointmentCollection.remove(index);
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Allergy> getAllergyCollection() {
        return allergyCollection;
    }

    public void setAllergyCollection(List<Allergy> allergyCollection) {
        this.allergyCollection = allergyCollection;
    }
    
    public PatientAddress getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(PatientAddress patientAddress) {
        this.patientAddress = patientAddress;
    }

    @XmlTransient
    public String getFullName() {
        if(this.middleName.isEmpty()) {
            return firstName + " " + lastName;
        }
        return firstName + " " + middleName + " " + lastName;
    }

    public void update(Patient patient) {
        this.title = patient.getTitle();
        this.firstName = patient.getFirstName();
        this.middleName = patient.getMiddleName();
        this.lastName = patient.getLastName();
        this.ppsNumber = patient.getPpsNumber();
        this.dateOfBirth = patient.getDateOfBirth();
        this.gender = patient.getGender();
        this.email = patient.getEmail();
        this.mobilePhone = patient.getMobilePhone();
        this.homePhone = patient.getHomePhone();
        this.nextOfKinName = patient.getNextOfKinName();
        this.nextOfKinContact = patient.getNextOfKinContact();
        
        //Allergy collection
        if(patient.getAllergyCollection() == null || patient.getAllergyCollection().isEmpty()) {
            //If new collection is empty, clear old collection
            this.allergyCollection.clear();
        } else {
            //If new collection is shorter than old collection, remove the "overflow" allergies
            //from old collection
            if(this.allergyCollection.size() > patient.getAllergyCollection().size()) {
                for(int i = patient.getAllergyCollection().size(); i < this.allergyCollection.size(); i++) {
                    this.allergyCollection.remove(i);
                }
            }
            //Replace old allergy details with new allergies, and add any "overflow" new allergy to 
            //the list
            for(int i = 0; i < patient.getAllergyCollection().size(); i++) {
                if(i < this.allergyCollection.size()) {
                    this.allergyCollection.get(i).setPatientId(
                            patient.getAllergyCollection().get(i).getPatientId()
                    );
                    this.allergyCollection.get(i).setAllergyType(
                            patient.getAllergyCollection().get(i).getAllergyType()
                    );
                } else {
                    this.allergyCollection.add(patient.getAllergyCollection().get(i));
                }
            }
        }
        
        //Patient address
        this.patientAddress.update(patient.getPatientAddress());
        this.updatedAt = patient.getUpdatedAt();
        //createdAt is not updated
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.ppsNumber);
        return hash;
    }

    /**
     * When comparing the equality of two Patient objects, their PPS number
     * property is the base of the comparison. Apart from the id (which doesn't exist
     * before they are written into the database), only the PPS number is unique for
     * every patient.
     * 
     * @param obj Object to compare to
     * @return true if two objects are equal, false if not
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Patient other = (Patient) obj;
        if (!Objects.equals(this.ppsNumber, other.ppsNumber)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Patient{" + "id=" + id + ", title=" + title + ", firstName=" + firstName + ", middleName=" + middleName + ", lastName=" + lastName + ", ppsNumber=" + ppsNumber + ", dateOfBirth=" + dateOfBirth + ", gender=" + gender + ", email=" + email + ", mobilePhone=" + mobilePhone + ", homePhone=" + homePhone + ", nextOfKinName=" + nextOfKinName + ", nextOfKinContact=" + nextOfKinContact + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", allergyCollection=" + allergyCollection + ", patientAddress=" + patientAddress + '}';
    }

       
       
}