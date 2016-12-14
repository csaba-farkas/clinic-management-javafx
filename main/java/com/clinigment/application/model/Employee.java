
package com.clinigment.application.model;

import com.clinigment.model.adapters.LocalDateAdapter;
import com.clinigment.model.enums.EmpRole;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 *
 * @author csaba
 */


@XmlRootElement
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String title;

    private String firstName;

    private String lastName;

    private String middleName;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate dateOfBirth;

    private String ppsNumber;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate employedSince;

    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate employedUntil;

    private EmpRole role;

    @XmlTransient
    private String roleString;

    private String mobilePhone;

    private String homePhone;

    private String email;

    private EmployeeAddress employeeAddress;

    private UserAccount userAccount;

    private List<Appointment> appointmentCollection;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public Employee() {
        //Empty constructor for JPA
    }

    public Employee(Long id) {
        this.id = id;
    }

    public Employee(Long employeeId,
                    String title,
                    String firstName,
                    String lastName,
                    String middleName,
                    LocalDate dateOfBirth,
                    String ppsNumber,
                    LocalDate employedSince,
                    LocalDate employedUntil,
                    EmpRole role,
                    String mobilePhone,
                    String homePhone,
                    String email,
                    UserAccount userAccount,
                    Timestamp createdAt,
                    Timestamp updatedAt) {
        this.id = employeeId;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.dateOfBirth = dateOfBirth;
        this.ppsNumber = ppsNumber;
        this.employedSince = employedSince;
        this.employedUntil = employedUntil;
        this.role = role;
        this.mobilePhone = mobilePhone;
        this.homePhone = homePhone;
        this.email = email;
        this.employeeAddress = new EmployeeAddress();
        this.userAccount = userAccount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.roleString = getRoleString();

        //Collection
        this.appointmentCollection = new ArrayList<>();
    }

    @XmlID
    public Long getId() {
        return id;
    }

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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPpsNumber() {
        return ppsNumber;
    }

    public void setPpsNumber(String ppsNumber) {
        this.ppsNumber = ppsNumber;
    }

    public LocalDate getEmployedSince() {
        return employedSince;
    }

    public void setEmployedSince(LocalDate employedSince) {
        this.employedSince = employedSince;
    }

    public LocalDate getEmployedUntil() {
        return employedUntil;
    }

    public void setEmployedUntil(LocalDate employedUntil) {
        this.employedUntil = employedUntil;
    }

    public EmpRole getRole() {
        return role;
    }

    public void setRole(EmpRole role) {
        this.role = role;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmployeeAddress getEmployeeAddress() {
        return employeeAddress;
    }

    public void setEmployeeAddress(EmployeeAddress employeeAddress) {
        this.employeeAddress = employeeAddress;
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

    @XmlTransient
    public String getRoleString() {
        if(this.role != null) {
            if (role == EmpRole.ADMIN) {
                this.roleString = "ADMIN";
            } else if (role == EmpRole.HYGIENIST) {
                this.roleString = "HYGIENIST";
            }else if (role == EmpRole.RECEPTIONIST) {
                this.roleString = "RECEPTIONIST";
            }else if (role == EmpRole.DENTAL_NURSE) {
                this.roleString = "DENTAL NURSE";
            }else if (role == EmpRole.DENTIST) {
                this.roleString = "DENTIST";
            }else if (role == EmpRole.MANAGER) {
                this.roleString = "MANAGER";
            }else if (role == EmpRole.OTHER){
                this.roleString = "OTHER";
            }
            //System.out.println("Role String: " + this.roleString);
        }
        return this.roleString;
    }

    public void setRoleString(String role) {
        this.roleString = role;
    }

    @XmlTransient
    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    @XmlTransient
    public List<Appointment> getAppointmentCollection() {
        return appointmentCollection;
    }

    public void setAppointmentCollection(List<Appointment> appointmentCollection) {
        this.appointmentCollection = appointmentCollection;
    }

    //Same as Patient, PPS number is unique in employee table as well.
    //So I used PPS number for hashing and for equals functions.
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.ppsNumber);
        return hash;
    }

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
        final Employee other = (Employee) obj;
        if (!Objects.equals(this.ppsNumber, other.ppsNumber)) {
            return false;
        }
        return true;
    }


    @Override
    public String toString() {
        return "Employee{" + "employeeId=" + id + ", title=" + title + ", firstName=" + firstName + ", lastName=" + lastName + ", middleName=" + middleName + ", dateOfBirth=" + dateOfBirth + ", ppsNumber=" + ppsNumber + ", employedSince=" + employedSince + ", employedUntil=" + employedUntil + ", role=" + role + ", mobilePhone=" + mobilePhone + ", homePhone=" + homePhone + ", email=" + email + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }











}
