package com.clinigment.application.clients;

import com.clinigment.application.model.Employee;
import com.clinigment.application.model.UserAccount;
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
public class UserAccountClient {

    private static final String BASE_URI = "http://localhost:8080/ClinicManagementRestAPI/webapi/";
    private static String username = "admin";
    private static String password = "admin";


    public static String getUsername() {
        return username;
    }

    public static UserAccount createUserAccount(UserAccount ua) throws Exception {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);

        ClientConfig cc = new ClientConfig().register(new JacksonFeature());
        cc.register(feature);

        Client client = ClientBuilder.newClient(cc);

        WebTarget uaTarget = client
                .target(BASE_URI + "employees/" + ua.getEmployeeId() + "/useraccounts");
        System.out.println("Uri targeted: " + uaTarget.getUri());

        ua.setEmployeeId(null);
        Response empResource = uaTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(ua));

        UserAccount ua1 = new UserAccount();

        switch (empResource.getStatus()) {
            case 201:
                ua1 = empResource.readEntity(UserAccount.class);
                empResource.close();
                client.close();
                return ua1;
            case 404:
                empResource.close();
                client.close();
                throw new Exception("Resource not found. Status code " + empResource.getStatus());
            case 304:
                empResource.close();
                client.close();
                throw new Exception("Not modified. " + empResource.getEntityTag() + " Status code " + empResource.getStatus());
            default:
                empResource.close();
                client.close();
                throw new Exception("An error occured. Status code " + empResource.getStatus());
        }
    }
    public static void setUsername(String username) {
        UserAccountClient.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserAccountClient.password = password;
    }
}
