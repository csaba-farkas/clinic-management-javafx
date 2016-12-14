
package com.clinigment.application.model;

import com.clinigment.model.adapters.LocalDateAdapter;
import com.clinigment.model.adapters.LocalDateTimeAdapter;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author csaba
 */


@XmlRootElement
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlID
    private Long id;


    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate date;

    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime startTime;

    @XmlTransient
    private ObjectProperty<LocalDateTime> startTimeProperty;


    @XmlJavaTypeAdapter(LocalDateTimeAdapter.class)
    private LocalDateTime endTime;

    @XmlTransient
    private ObjectProperty<LocalDateTime> endTimeProperty;

    private Long patientId;

    private String patientName;

    private String contactNumber;

    private String description;

    private Long doctorId;

    private Timestamp createdAt;

    private Timestamp updatedAt;

    public Appointment() {
        //Empty constructor for JPA
    }

    public Appointment(Long id) {
        this.id = id;
    }

    public Appointment(Long id,
                       LocalDate date,
                       LocalDateTime startTime,
                       LocalDateTime endTime,
                       Long patientId,
                       String patientName,
                       String contactNumber,
                       String description,
                       Long employeeId,
                       Timestamp createdAt,
                       Timestamp updatedAt) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.patientId = patientId;
        this.patientName = patientName;
        this.contactNumber = contactNumber;
        this.description = description;
        this.doctorId = employeeId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        this.startTimeProperty = new SimpleObjectProperty<>(this.startTime);
        this.endTimeProperty = new SimpleObjectProperty<>(this.endTime);
    }



    public Long getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long empId) {
        this.doctorId = empId;
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
    public LocalDateTime getStartTimeProperty() {
        this.startTimeProperty = new SimpleObjectProperty<>(this.startTime);
        return startTimeProperty.get();
    }

    @XmlTransient
    public ObjectProperty<LocalDateTime> startTimePropertyProperty() {
        this.startTimeProperty = new SimpleObjectProperty<>(this.startTime);
        return startTimeProperty;
    }

    @XmlTransient
    public void setStartTimeProperty(LocalDateTime startTimeProperty) {
        this.startTimeProperty.set(startTimeProperty);
    }

    @XmlTransient
    public LocalDateTime getEndTimeProperty() {
        this.endTimeProperty = new SimpleObjectProperty<>(this.endTime);
        return endTimeProperty.get();
    }

    @XmlTransient
    public ObjectProperty<LocalDateTime> endTimePropertyProperty() {

        this.endTimeProperty = new SimpleObjectProperty<>(this.endTime);
        return endTimeProperty;
    }

    @XmlTransient
    public void setEndTimeProperty(LocalDateTime endTimeProperty) {
        this.endTimeProperty.set(endTimeProperty);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.id);
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
        final Appointment other = (Appointment) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Appointment{" + "id=" + id + ", date=" + date + ", startTime=" + startTime + ", endTime=" + endTime + ", patientId=" + patientId + ", patientName=" + patientName + ", contactNumber=" + contactNumber + ", description=" + description + ", doctorId=" + doctorId + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }

    public void update(Appointment newAppointment) {
        this.contactNumber = newAppointment.getContactNumber();
        this.createdAt = newAppointment.getCreatedAt();
        this.date = newAppointment.getDate();
        this.description = newAppointment.getDescription();
        this.doctorId = newAppointment.getDoctorId();
        this.endTime = newAppointment.getEndTime();
        this.patientId = newAppointment.getPatientId();
        this.patientName = newAppointment.getPatientName();
        this.startTime = newAppointment.getStartTime();
        this.updatedAt = newAppointment.getUpdatedAt();
    }
}
