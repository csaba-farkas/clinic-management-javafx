
package com.clinigment.application.model;

import com.clinigment.model.enums.AccountType;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


/**
 *
 * @author csaba
 */

@XmlRootElement
public class UserAccount implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long employeeId;
    
    private String username;
    
    //Class assumes the passwords that are sent from the clients are
    //already encrypted
    private String password;
    
    private AccountType accountType;
    
    private Timestamp createdAt;
    
    private Timestamp updatedAt;
    
    private Employee employee;

    public UserAccount() {
        //Empty constructor for JPA
    }

    public UserAccount(Long employeeId, String password, AccountType accountType, Timestamp createdAt, Timestamp updatedAt, Employee employee) {
        this.employeeId = employeeId;
        this.password = password;
        this.accountType = accountType;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.employee = employee;
        //Create username from name and id
        this.username = (employee.getFirstName() + "."+ employee.getLastName() + employee.getId()).toUpperCase();
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    //Update username if name is modified
    public void updateUsername() {
        this.username = (employee.getFirstName() + "."+ employee.getLastName() + employee.getId()).toUpperCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
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
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employeeId = employee.getId();
        this.employee = employee;
    }

    //Employee ID is used for hashing and for equals functions.
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.employeeId);
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
        final UserAccount other = (UserAccount) obj;
        if (!Objects.equals(this.employeeId, other.employeeId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "UserAccount{" + "employeeId=" + employeeId + ", username=" + username + ", password=" + password + ", accountType=" + accountType + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", employee=" + employee + '}';
    }
    
    
}
