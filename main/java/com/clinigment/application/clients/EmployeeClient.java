package com.clinigment.application.clients;

import com.clinigment.application.model.Employee;
import com.clinigment.model.enums.EmpRole;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by csaba on 13/05/2016.
 */
public class EmployeeClient {

    private static final String BASE_URI = "http://localhost:8080/ClinicManagementRestAPI/webapi/";
    private static String username = "admin";
    private static String password = "admin";

    public static void setUsername(String username) {
        EmployeeClient.username = username;
    }

    public static void setPassword(String password) {
        EmployeeClient.password = password;
    }

    public static Employee findById(Long id) throws Exception {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);

        ClientConfig cc = new ClientConfig().register(new JacksonFeature());
        cc.register(feature);

        Client client = ClientBuilder.newClient(cc);

        WebTarget appTarget = client
                .target(BASE_URI + "employees/" + id);

        Response empResource = appTarget.request(MediaType.APPLICATION_JSON).get();

        Employee employee = new Employee();

        switch (empResource.getStatus()) {
            case 200:
                employee = empResource.readEntity(Employee.class);
                empResource.close();
                client.close();
                return employee;
            case 404:
                empResource.close();
                client.close();
                throw new Exception("Resource not found. Status code 404.");
            case 204:
                empResource.close();
                client.close();
                throw new Exception("Appointments were not found.Status code 204.");
            default:
                empResource.close();
                client.close();
                throw new Exception("An error occured.");
        }
    }

    public static List<Employee> getAllDoctorsAndHygienists() throws Exception {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);

        ClientConfig cc = new ClientConfig().register(new JacksonFeature());
        cc.register(feature);

        Client client = ClientBuilder.newClient(cc);

        WebTarget appTarget = client
                .target(BASE_URI + "employees");

        Response empResource = appTarget.request(MediaType.APPLICATION_JSON).get();

        List<Employee> emps = new ArrayList<>();

        switch (empResource.getStatus()) {
            case 200:
                emps = empResource.readEntity(new GenericType<List<Employee>>() {
                });
                empResource.close();
                client.close();
                break;
            case 404:
                empResource.close();
                client.close();
                throw new Exception("Resource not found. Status code 404.");
            case 204:
                empResource.close();
                client.close();
                throw new Exception("Employees were not found.Status code 204.");
            default:
                empResource.close();
                client.close();
                throw new Exception("An error occured.");
        }

        //Remove no-doctors and no-hygienists
        List<Employee> empsToReturn = new ArrayList<>();
        for (Employee e : emps) {
            if (e.getRole() == EmpRole.DENTIST || e.getRole() == EmpRole.HYGIENIST) {
                empsToReturn.add(e);
            }
        }

        return empsToReturn;

    }

    public static List<Employee> getAllEmployees() throws Exception {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);

        ClientConfig cc = new ClientConfig().register(new JacksonFeature());
        cc.register(feature);

        Client client = ClientBuilder.newClient(cc);

        WebTarget appTarget = client
                .target(BASE_URI + "employees");

        Response empResource = appTarget.request(MediaType.APPLICATION_JSON).get();

        List<Employee> emps = new ArrayList<>();

        switch (empResource.getStatus()) {
            case 200:
                emps = empResource.readEntity(new GenericType<List<Employee>>() {
                });
                empResource.close();
                client.close();
                break;
            case 404:
                empResource.close();
                client.close();
                throw new Exception("Resource not found. Status code 404.");
            case 204:
                empResource.close();
                client.close();
                throw new Exception("Employees were not found.Status code 204.");
            default:
                empResource.close();
                client.close();
                throw new Exception("An error occured.");
        }

        return emps;

    }

    public static Employee createEmployee(Employee e) throws Exception {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);

        ClientConfig cc = new ClientConfig().register(new JacksonFeature());
        cc.register(feature);

        Client client = ClientBuilder.newClient(cc);

        WebTarget appTarget = client
                .target(BASE_URI + "employees");

        Response empResource = appTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(e));

        Employee emp = new Employee();

        switch (empResource.getStatus()) {
            case 201:
                emp = empResource.readEntity(Employee.class);
                empResource.close();
                client.close();
                return emp;
            case 404:
                empResource.close();
                client.close();
                throw new Exception("Resource not found. Status code " + empResource.getStatus());
            case 304:
                empResource.close();
                client.close();
                throw new Exception("Data was not modified. Status code " + empResource.getStatus());
            default:
                empResource.close();
                client.close();
                throw new Exception("An error occured. Status code " + empResource.getStatus());
        }
    }

    public static Employee updateEmployee(Employee employee) throws Exception {

        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);

        ClientConfig cc = new ClientConfig().register(new JacksonFeature());
        cc.register(feature);

        Client client = ClientBuilder.newClient(cc);

        WebTarget putPatient = client.target(BASE_URI + "employees" + "/" + employee.getId());

        Response putPatientResponse = putPatient.request()
                .put(Entity.json(employee));


        Employee patient = new Employee();
        switch (putPatientResponse.getStatus()) {
            case 200:
                patient = putPatientResponse.readEntity(Employee.class);
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
