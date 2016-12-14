
package com.clinigment.application.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Csaba Farkas
 */
@XmlRootElement
public class EmployeeAddress implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long empId;
    
    private Employee employee;
    
    private String addressLine1;
    
    private String addressLine2;
    
    private String addressLine3;
    
    private String cityTown;
    
    private String county;
    
    private String country;

    public EmployeeAddress(Employee employee, String addressLine1, String addressLine2, String addressLine3, String cityTown, String county, String country) {
        this.empId = employee.getId();
        this.employee = employee;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.cityTown = cityTown;
        this.county = county;
        this.country = country;
    }

    public EmployeeAddress() {
        //Empty constructor for JPA
    }

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    @XmlTransient
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        System.out.println("Log in setEmployee: " + employee);
        this.empId = employee.getId();
        this.employee = employee;
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
        return "EmployeeAddress{" + "empId=" + empId + ", employee=" + employee + ", addressLine1=" + addressLine1 + ", addressLine2=" + addressLine2 + ", addressLine3=" + addressLine3 + ", cityTown=" + cityTown + ", county=" + county + ", country=" + country + '}';
    }

    
}
