package com.clinigment.application.abstracts;

import java.util.HashMap;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

/**
 *
 * @author lastminute84
 */
public abstract class LayoutController {
    
    //Strings used as KEYS for passing data when managing patients
    protected final static String ID_PATIENT = "patientId";
    protected final static String TITLE_PATIENT = "title";
    protected final static String FIRST_NAME_PATIENT = "firstName";
    protected final static String LAST_NAME_PATIENT = "lastName";
    protected final static String MIDDLE_NAME_PATIENT = "middleName";
    protected final static String DOB_PATIENT = "dob";
    protected final static String DOB_DATE_PATIENT = "dobDate";
    protected final static String PPS_NUMBER_PATIENT = "pps";
    protected final static String NEXT_OF_KIN_PATIENT = "nameNextOfKin";
    protected final static String NEXT_OF_KIN_CONTACT_PATIENT = "contactNextOfKin";
    protected final static String ADDRESS1_PATIENT = "address1";
    protected final static String ADDRESS2_PATIENT = "address2";
    protected final static String ADDRESS3_PATIENT = "address3";
    protected final static String CITY_PATIENT = "city";
    protected final static String COUNTY_PATIENT = "county";
    protected final static String COUNTRY_PATIENT = "country";
    protected final static String HOME_PHONE_PATIENT = "homePhone";
    protected final static String MOBILE_PHONE_PATIENT = "mobilePhone";
    protected final static String EMAIL_PATIENT = "email";
    
    private static final HashMap<String, Object> dataBundle = new HashMap<>();
       
    public abstract void setLayout(Node node);
    public abstract void setLayout(Node node, Pane container);
    
    public synchronized void addData(String key, Object object) {
        dataBundle.put(key, object);
    }
    
    public synchronized Object getData(String key) {
        return dataBundle.get(key);
    }
    
    public synchronized void clearData() {
        dataBundle.clear();
    }
    
    public synchronized void deleteDataByKey(List<String> keys) {
        keys.stream().filter((key) -> (dataBundle.containsKey(key))).forEach((key) -> {
            dataBundle.remove(key);
        });
    }
}
