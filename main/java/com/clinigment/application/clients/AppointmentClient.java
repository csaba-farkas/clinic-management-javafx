package com.clinigment.application.clients;

import com.clinigment.application.main.App;
import com.clinigment.application.model.Appointment;
import com.clinigment.application.model.Patient;
import javafx.collections.FXCollections;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by csaba on 13/05/2016.
 */
public class AppointmentClient {

    private static final String BASE_URI = "http://localhost:8080/ClinicManagementRestAPI/webapi/";
    private static String username = "admin";
    private static String password = "admin";

    public static void setPassword(String password) {
        AppointmentClient.password = password;
    }

    public static void setUsername(String username) {
        AppointmentClient.username = username;
    }

    public static Appointment createAppointment(Appointment appointment) throws Exception {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);

        ClientConfig cc = new ClientConfig().register(new JacksonFeature());
        cc.register(feature);

        Client client = ClientBuilder.newClient(cc);


        WebTarget appTarget = client
                .target(BASE_URI + "appointments");
        Response appointmentsResponse = appTarget.request(MediaType.APPLICATION_JSON)
                                        .post(Entity.json(appointment));
        Appointment newAppointment = null;

        switch (appointmentsResponse.getStatus()) {
            case 201:
                newAppointment = appointmentsResponse.readEntity(Appointment.class);
                client.close();
                System.out.println("appoitnemnt: " + newAppointment);
                return newAppointment;
            case 404:
                client.close();
                throw new Exception("Resource not found. Status code 404.");
            case 204:
                client.close();
                throw new Exception("Appointments were not found.Status code 404.");
            default:
                client.close();
                throw new Exception("An error occured. Status code " + appointmentsResponse.getStatus());
        }
    }



    public static List<Appointment> getAppointmentsByDoctorIdOnDate(LocalDate date, Long doctorId) throws Exception {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);

        ClientConfig cc = new ClientConfig().register(new JacksonFeature());
        cc.register(feature);

        Client client = ClientBuilder.newClient(cc);


        WebTarget appTarget = client
                .target(BASE_URI + "appointments?"
                        + "year=" + date.getYear()
                        + "&month=" + date.getMonthValue()
                        + "&day=" + date.getDayOfMonth()
                        + "&doctorId=" + doctorId);

        Response appointmentsResponse = appTarget.request(MediaType.APPLICATION_JSON).get();

        List<Appointment> apps = new ArrayList<>();

        switch (appointmentsResponse.getStatus()) {
            case 200:
                apps = appointmentsResponse.readEntity(new GenericType<List<Appointment>>(){});
                client.close();
                break;
            case 404:
                client.close();
                throw new Exception("Resource not found. Status code 404.");
            case 204:
                client.close();
                throw new Exception("Appointments were not found.Status code 404.");
            default:
                client.close();
                throw new Exception("An error occured. Status code " + appointmentsResponse.getStatus());
        }
        return apps;
    }

    public static Appointment updateAppointment(Appointment appointment) throws Exception {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);

        ClientConfig cc = new ClientConfig().register(new JacksonFeature());
        cc.register(feature);

        Client client = ClientBuilder.newClient(cc);


        WebTarget appTarget = client
                .target(BASE_URI + "appointments/" + appointment.getId());
        Response appointmentsResponse = appTarget.request(MediaType.APPLICATION_JSON)
                .put(Entity.json(appointment));
        Appointment newAppointment = null;

        switch (appointmentsResponse.getStatus()) {
            case 200:
                newAppointment = appointmentsResponse.readEntity(Appointment.class);
                appointmentsResponse.close();
                client.close();
                return newAppointment;
            case 404:
                appointmentsResponse.close();
                client.close();
                throw new Exception("Resource not found. Status code 404.");
            case 204:
                appointmentsResponse.close();
                client.close();
                throw new Exception("Appointments were not found.Status code 404.");
            default:
                appointmentsResponse.close();
                client.close();
                throw new Exception("An error occured. Status code " + appointmentsResponse.getStatus());
        }
    }

    public static void deleteteAppointment(Appointment appointment) throws Exception {
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic(username, password);

        ClientConfig cc = new ClientConfig().register(new JacksonFeature());
        cc.register(feature);

        Client client = ClientBuilder.newClient(cc);


        WebTarget appTarget = client
                .target(BASE_URI + "appointments/" + appointment.getId());
        Response appointmentsResponse = appTarget.request(MediaType.APPLICATION_JSON)
                .delete();

        switch (appointmentsResponse.getStatus()) {
            case 200:
                appointmentsResponse.close();
                client.close();
                break;
            case 404:
                appointmentsResponse.close();
                client.close();
                throw new Exception("Resource not found. Status code 404.");
            case 204:
                appointmentsResponse.close();
                client.close();
                throw new Exception("Appointments were not found.Status code 404.");
            default:
                appointmentsResponse.close();
                client.close();
                throw new Exception("An error occured. Status code " + appointmentsResponse.getStatus());
        }
    }
}
