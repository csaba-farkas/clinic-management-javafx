
package com.clinigment.application.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author csaba
 */


@XmlRootElement
public class AppointmentRecord implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @XmlID

    private Long id;
    
    private Long patientId;
    
    private Patient patient;
    
    private Long doctorId;
    
    private Employee employee;
    
    private List<CompletedTreatment> completedTreatments;
    
    private Timestamp createdAt;
    
    private Timestamp updatedAt;

    public AppointmentRecord() {
    }

    public AppointmentRecord(Long id) {
        this.id = id;
    }

    public AppointmentRecord(Long id, Long patientId, Patient patient, Long doctorId, Employee employee, List<CompletedTreatment> completedTreatments) {
        this.id = id;
        this.patientId = patientId;
        this.patient = patient;
        this.doctorId = doctorId;
        this.employee = employee;
        this.completedTreatments = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

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
        this.patient = patient;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    @XmlTransient
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<CompletedTreatment> getCompletedTreatments() {
        return completedTreatments;
    }

    public void setCompletedTreatments(List<CompletedTreatment> completedTreatments) {
        this.completedTreatments = completedTreatments;
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

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.patientId);
        hash = 17 * hash + Objects.hashCode(this.doctorId);
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
        final AppointmentRecord other = (AppointmentRecord) obj;
        if (!Objects.equals(this.patientId, other.patientId)) {
            return false;
        }
        if (!Objects.equals(this.doctorId, other.doctorId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AppointmentRecord{" + "id=" + id + ", patientId=" + patientId + ", patient=" + patient + ", doctorId=" + doctorId + ", employee=" + employee + ", completedTreatments=" + completedTreatments + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }   
}
