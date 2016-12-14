/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clinigment.application.clients;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;
import javax.ws.rs.core.GenericType;

import com.clinigment.application.model.Employee;
import com.clinigment.application.model.Patient;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 *
 * @author csaba
 */
public class PatientClient {

    private final static String BASE_URI = "http://localhost:8080/ClinicManagementRestAPI/webapi/";
    private static String username = "admin";
    private static String password = "admin";

    List<Patient> patientList;

    public static Client getClient() {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);

        ClientConfig cc = new ClientConfig().register(new JacksonFeature());
        cc.register(feature);

        Client client = ClientBuilder.newClient(cc);
        return client;
    }

    public static List getAllPatient() {
        WebTarget allPatientsTarget = getClient()
                .target(BASE_URI + "patients");

        Response allPatientsResponse = allPatientsTarget.request(MediaType.APPLICATION_JSON).get();
        List<Patient> patients = null;
        switch (allPatientsResponse.getStatus()) {
            case 200:
                patients = allPatientsResponse.readEntity(new GenericType<List<Patient>>() {
                });
                allPatientsResponse.close();
                getClient().close();
                System.out.println("Patients: " + patients);
                break;
            case 404:
                allPatientsResponse.close();
                getClient().close();
                System.out.println("Patient was not found.");
                break;
            case 204:
                allPatientsResponse.close();
                getClient().close();
                System.out.println("Patient was not found.");
                break;
            default:
                allPatientsResponse.close();
                getClient().close();
                System.out.println("An error occured.");
                break;
        }
        allPatientsResponse.close();
        return patients;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        PatientClient.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        PatientClient.password = password;
    }

    public List<Patient> getpatientList() {
        return patientList;
    }

    /**
     * Add new patient to the database.
     *
     * @param newpatientIn New patient that is added to the database.
     */
    public static Patient updatePatient(Patient newpatientIn) throws Exception {

        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);

        ClientConfig cc = new ClientConfig().register(new JacksonFeature());
        cc.register(feature);

        Client client = ClientBuilder.newClient(cc);

        WebTarget putPatient = client.target("http://ec2-52-19-236-212.eu-west-1.compute.amazonaws.com/webapi/patients" + "/" + newpatientIn.getId());

        Response putPatientResponse = putPatient.request()
                .put(Entity.json(newpatientIn));


        Patient patient = new Patient();
        switch (putPatientResponse.getStatus()) {
            case 200:
                patient = putPatientResponse.readEntity(Patient.class);
                putPatientResponse.close();
                client.close();
                return patient;
            case 404:
                putPatientResponse.close();
                client.close();
                throw new Exception("Resource not found. Status code 404.");
            case 204:
                putPatientResponse.close();
                client.close();
                throw new Exception("Appointments were not found.Status code 204.");
            default:
                putPatientResponse.close();
                client.close();
                throw new Exception("An error occured.");
        }
    }
}