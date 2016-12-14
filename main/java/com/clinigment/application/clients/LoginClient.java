package com.clinigment.application.clients;

import com.clinigment.application.model.Employee;
import com.clinigment.application.model.LoginForm;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by csaba on 16/05/2016.
 */
public class LoginClient {
    private static final String BASE_URI = "http://localhost:8080/ClinicManagementRestAPI/webapi/";
    //"http://ec2-52-19-236-212.eu-west-1.compute.amazonaws.com/webapi/";
    private static String username = "admin";
    private static String password = "admin";

    public static Employee login(LoginForm loginForm) throws Exception {

        Client client = ClientBuilder.newClient();

        WebTarget appTarget = client
                .target(BASE_URI + "login");

        Response empResource = appTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(loginForm));

        Employee emp = new Employee();

        switch (empResource.getStatus()) {
            case 200:
                emp = empResource.readEntity(Employee.class);
                empResource.close();
                client.close();
                return emp;
            case 404:
                empResource.close();
                client.close();
                throw new Exception("Resource not found. Status code " + empResource.getStatus());
            case 401:
                empResource.close();
                client.close();
                throw new Exception("Login Failed! Please enter a valid username and password. Status code " + empResource.getStatus());
            default:
                empResource.close();
                client.close();
                throw new Exception("An error occured. Status code " + empResource.getStatus());
        }
    }
}
