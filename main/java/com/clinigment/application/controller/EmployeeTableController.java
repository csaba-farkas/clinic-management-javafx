package com.clinigment.application.controller;


import com.clinigment.application.main.App;
import com.clinigment.application.model.Patient;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * FXML Controller class
 *
 * @author lastminute84
 */
public class EmployeeTableController implements Initializable {

    private static EmployeeTableController instance;

    public static EmployeeTableController getInstance() {
        return instance;
    }

    @FXML
    private TableColumn<Patient, String> lastNameCol,
            firstNameCol,
            ppsCol,
            mobileCol,
            emailCol;

    @FXML
    private TableColumn<Patient, LocalDate> dobCol;

    @FXML
    private TableView<Patient> table;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        
        //Set columns
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        ppsCol.setCellValueFactory(new PropertyValueFactory<>("ppsNumber"));
        dobCol.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("mobilePhone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

        table.setItems(getPatients());
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.maxWidthProperty().bind(App.PRIMARY_STAGE.widthProperty().multiply(0.825));
        table.maxHeightProperty().bind(App.PRIMARY_STAGE.heightProperty().multiply(0.8));

        table.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    System.out.println(table.getSelectionModel().getSelectedItem());
                    showPatient();
                }
            }
        });
        
        getPatients();
    }
    
    public void showPatient(){
        //PatientManagementController.getInstance().addTab(table.getSelectionModel().getSelectedItem());
        //new ShowPatientController(table.getSelectionModel().getSelectedItem())
    }

    //////////////////******************************************//////////////////////////////
    //////////////////*******************************************////////////////////////////
    ///////////////// T E S T T E S T T E S T T E S T T E S T T E S T ///////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////
    //////////*************************\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\////////////****//
    //Get all of the patients
    public ObservableList<Patient> getPatients() {
        //ObservableList<Patient> patients = FXCollections.observableArrayList();
        
        ////PatientClient patientClient = new PatientClient();
        //GenericType<List<Patient>> gType = new GenericType<List<Patient>>() {};
        //List<Patient> patients = new ArrayList<>();
        //patients = (List<Patient>) patientClient.findAll_JSON(gType);
        /*HttpAuthenticationFeature feature = HttpAuthenticationFeature.basicBuilder()
                .nonPreemptive()
                .credentials("admin", "admin")
                .build();
        */
        
        /*String BASE_URL = "http://ec2-52-19-236-212.eu-west-1.compute.amazonaws.com/webapi/";

        
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(feature);
        //clientConfig.getClasses().add(JacksonJsonProvider.class);
        
        
        //Create a new client object
        Client client = ClientBuilder.newClient(clientConfig);
        
        //Create webtarget --> URL of the resources, in this case patient resources
        WebTarget baseTarget = client.target(BASE_URL);
        WebTarget allPatientsTarget = baseTarget.path("patients");
        WebTarget singlePatientTarget = baseTarget.path("{id}");
        
        System.out.println("url: "+ baseTarget.path("patients"));
        
        
        //List<Patient> patient = allPatientsTarget
          //      .request(MediaType.APPLICATION_JSON)
            //    .get(new GenericType<List<Patient>>() {});
        
        //System.out.println("Patient 1: " + patient);
        
        Response response = allPatientsTarget.request(MediaType.APPLICATION_JSON).get();
        System.out.println("Response: " + response.getStatus() + " - " + response.readEntity(String.class));*/
        
        /*
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");
        
        ClientConfig cc = new ClientConfig().register(new JacksonFeature());
        cc.register(feature);
        
        Client client = ClientBuilder.newClient(cc);
        
        
        WebTarget allPatientsTarget = client
                .target("http://ec2-52-19-236-212.eu-west-1.compute.amazonaws.com/webapi/patients");
        
        Response allPatientsResponse = allPatientsTarget.request(MediaType.APPLICATION_JSON).get();
        
        switch (allPatientsResponse.getStatus()) {
            case 200:
                List<Patient> patients = allPatientsResponse.readEntity(new GenericType<List<Patient>>(){});
                System.out.println("Patients: " + patients);
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
        ObservableList<Patient> patientList = FXCollections.observableArrayList();
        System.out.println("**********************"+patientList);
        return patientList;
        
        */
        return null;
    }

}
