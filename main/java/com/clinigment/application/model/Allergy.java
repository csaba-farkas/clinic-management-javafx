
package com.clinigment.application.model;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author csaba
 */

@XmlRootElement
public class Allergy implements Serializable {
    
    @XmlID
    private Long patientId;
    
    private String allergyType;

    public Allergy(Long patientId, String allergyType) {
        this.patientId = patientId;
        this.allergyType = allergyType;
    }
    
    
    public Allergy() { 
        //Empty constructor for JPA    
    }

    public Long getPatientId() {
        return patientId;
    }
    
    public void setPatientId(Long patientId) {
        //patient Id can only be set if it is null (ie. at creation)
        if(this.patientId == null && patientId != null) {
            this.patientId = patientId;
        }
    }

    public String getAllergyType() {
        return allergyType;
    }

    public void setAllergyType(String allergyType) {
        this.allergyType = allergyType;
    }

    @Override
    public String toString() {
        return "Allergy{" + "patientId=" + patientId + ", allergyType=" + allergyType + '}';
    }
    
    
    
    
}
