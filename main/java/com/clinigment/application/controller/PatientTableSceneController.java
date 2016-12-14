/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clinigment.application.controller;


import com.clinigment.application.abstracts.LayoutController;
import com.clinigment.application.clients.PatientClient;
import com.clinigment.application.model.Patient;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

/**
 *
 * @author csaba
 */
public class PatientTableSceneController extends LayoutController implements Initializable {
    private static PatientTableSceneController instance = null;

    public static PatientTableSceneController getInstance() {
        if(instance == null) {
            instance =  new PatientTableSceneController();
        }
        return instance;
    }

    @FXML
    private AnchorPane tablePane, container, patientInfoPane;
   
    @FXML
    private StackPane stackPane;
    
    @FXML
    private TableView tableView;
    
    @FXML
    private Button addNewPatientButton;
    
    @FXML
    private TableColumn colAction;
    @FXML
    private TableColumn<Patient, String> colPatientId,
                                         colFirstName,
                                         colLastName,
                                         colPpsNumber,
                                         colDateOfBirth,
                                         colGender,
                                         colBloodType,
                                         colMobilePhone,
                                         colHomePhone,
                                         colEmail;




    private static ObservableList<Patient> patients;
    
    @Override
    public void setLayout(Node node) {
    }

    @Override
    public void setLayout(Node node, Pane container) {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
        instance = this;
        this.patients = getPatients();

        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colPpsNumber.setCellValueFactory(new PropertyValueFactory<>("ppsNumber"));
        colDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        colMobilePhone.setCellValueFactory(new PropertyValueFactory<>("mobilePhone"));
        colHomePhone.setCellValueFactory(new PropertyValueFactory<>("homePhone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        //Set items
        tableView.setItems(this.patients);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        colAction.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object, Boolean>,ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Object, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
        colAction.setCellFactory(new Callback<TableColumn<Object, Boolean>, TableCell<Object, Boolean>>() {
            @Override
            public TableCell<Object, Boolean> call(TableColumn<Object, Boolean> p) {
                return new ActionTableCell(tableView);
            }
        });

        addNewPatientButton.setOnAction(e -> {
            MainLayoutController.getInstance().setActive(MainLayoutController.SCENES.get(MainLayoutController.NEW_PATIENT_PANE_INDEX));
        });

    }

    public void update(Patient newPatient){
        patients = getPatients();
        for(Patient p:patients){
            System.out.println(p);
            //arrayList.add(p);
        }

        tableView.getItems().add(newPatient);

    }

    public void update(){
        patients = getPatients();
        for(Patient p:patients){
            System.out.println(p);
            //arrayList.add(p);
        }
        Patient nullPatient = new Patient();
        tableView.getItems().add(nullPatient);
        tableView.getItems().remove(nullPatient);

    }

    public void edit(Patient oldPatient, Patient newPatient){
        tableView.getItems().remove(oldPatient);
        tableView.getItems().add(newPatient);
    }

    @FXML
    public void savePatient() {}
    
    @FXML
    public void goBack() {}
    
    @FXML
    public void addNewPatient() {}
    
    @FXML
    public void showPatientData(MouseEvent event) {}
    
    protected ObservableList<Patient> getPatients() {
        this.patients = FXCollections.observableArrayList();

        PatientClient patientClient = new PatientClient();
        GenericType<List<Patient>> gType = new GenericType<List<Patient>>() {};
        List<Patient> receivedPatients = new ArrayList<>();

        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");

        ClientConfig cc = new ClientConfig().register(new JacksonFeature());
        cc.register(feature);

        Client client = ClientBuilder.newClient(cc);

        WebTarget allPatientsTarget = client
                .target("http://localhost:8080/ClinicManagementRestAPI/webapi/patients");

        Response allPatientsResponse = allPatientsTarget.request(MediaType.APPLICATION_JSON).get();

        switch (allPatientsResponse.getStatus()) {
            case 200:
                receivedPatients = allPatientsResponse.readEntity(new GenericType<List<Patient>>(){});
                patients = FXCollections.observableArrayList(receivedPatients);
                break;
            case 404:
                System.out.println("Patient was not found.");
                break;
            case 204:
                System.out.println("Patient was not found.");
                break;
            default:
                System.out.println("An error occured.");
                break;
        }

        allPatientsResponse.close();
        return patients;

    }
    
    private class ActionTableCell extends TableCell<Object, Boolean> {
        
        final Hyperlink viewButton = new Hyperlink("View");
        final Hyperlink editButton = new Hyperlink("Edit");
        final HBox hb = new HBox(viewButton, editButton);
        
        ActionTableCell (final TableView tblView){
            hb.setSpacing(4);
            viewButton.setOnAction((ActionEvent t) -> {
                //status = 1;
                int row = getTableRow().getIndex();
                Patient patientIn = (Patient) tableView.getItems().get(row);
                System.out.println(patientIn);

                MainLayoutController.getInstance().setActive(MainLayoutController.SCENES.get(MainLayoutController.VIEW_PATIENT_PANE_SINGLE));

                System.out.println(ViewPatientDetailsSingleController.getInstance());
                ViewPatientDetailsSingleController controller = ViewPatientDetailsSingleController.getInstance();
                controller.putData(patientIn);
            });
            
            editButton.setOnAction((ActionEvent event) -> {
                //status = 1;
                int row = getTableRow().getIndex();
                Patient patientIn = (Patient) tableView.getItems().get(row);
                System.out.println(patientIn);

                MainLayoutController.getInstance().setActive(MainLayoutController.SCENES.get(MainLayoutController.EDIT_PATIENT_PANE_INDEX));

                System.out.println(EditPatientController.getInstance());
                EditPatientController controller = EditPatientController.getInstance();
                controller.putData(patientIn);
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if(!empty){
                setGraphic(hb);
            }else{
                setGraphic(null);
            }
        }
    }
}


