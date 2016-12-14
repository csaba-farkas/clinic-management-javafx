package com.clinigment.application.controller;

import com.clinigment.application.clients.EmployeeClient;
import com.clinigment.application.model.Appointment;
import com.clinigment.application.model.Employee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

/**
 * Created by csaba on 13/05/2016.
 */
public class AppointmentDetailsSceneController implements Initializable {

    //Singleton
    private static AppointmentDetailsSceneController INSTANCE;

    public static AppointmentDetailsSceneController getInstance() {
        return INSTANCE;
    }

    @FXML
    private GridPane gridPane;

    @FXML
    private Button closeButton;

    private Label dateLabel,
                  startTimeLabel,
                  endTimeLabel,
                  doctorLabel,
                  patientNameLabel,
                  contactNumberLabel;

    private Text description;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        INSTANCE = this;

        //Set click handler on 'Close' button
        this.closeButton.setOnAction(event -> {

            resetScene();

            //Go back to the calendar
            MainLayoutController.getInstance().setActive(
                    MainLayoutController.SCENES.get(
                            MainLayoutController.APPOINTMENT_LIST_PANE_INDEX
                    )
            );
        });
    }

    /**
     * Worker method that resets the attributes to null;
     */
    private void resetScene() {

        this.dateLabel.setText("");
        this.startTimeLabel.setText("");
        this.endTimeLabel.setText("");
        this.doctorLabel.setText("");
        this.patientNameLabel.setText("");
        this.contactNumberLabel.setText("");
        this.description.setText("");

        this.gridPane.getChildren().remove(dateLabel);
        this.gridPane.getChildren().remove(startTimeLabel);
        this.gridPane.getChildren().remove(doctorLabel);
        this.gridPane.getChildren().remove(patientNameLabel);
        this.gridPane.getChildren().remove(contactNumberLabel);
        this.gridPane.getChildren().remove(description);
    }

    /**
     * Populate the form with the attributes of the appointment which is passed
     * as a parameter.
     *
     * @param appointment Appointment object to display.
     */
    public void populateForm(Appointment appointment) {

        try {
            this.dateLabel = new Label(appointment.getPatientName());


            DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("HH:mm");

            this.startTimeLabel = new Label(myDateFormatter.format(appointment.getStartTime()));
            this.endTimeLabel = new Label(myDateFormatter.format(appointment.getEndTime()));

            Long doctorId = appointment.getDoctorId();
            Employee employee = EmployeeClient.findById(doctorId);

            this.doctorLabel = new Label(employee.getFirstName() + " " + employee.getLastName());

            this.description = new Text(appointment.getDescription());
            this.description.wrappingWidthProperty().bind(
                    this.gridPane.widthProperty()
                    .divide(3)
                    .multiply(2)
                    .subtract(15)
            );

            this.description.setStyle(
                    "-fx-font-family: \"Segoe UI\", Helvetica, Arial, sans-serif;\n" +
                    "-fx-font-size: 18px;"
            );

            this.description.setFill(Color.web("#5273a4"));

            this.patientNameLabel = new Label(appointment.getPatientName());
            this.contactNumberLabel = new Label(appointment.getContactNumber());

        } catch (Exception ex) {
            ex.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, ex.getMessage());
            alert.initStyle(StageStyle.UTILITY);
            alert.showAndWait();
            MainLayoutController.getInstance().setActive(
                    MainLayoutController.SCENES.get(
                            MainLayoutController.APPOINTMENT_LIST_PANE_INDEX
                    )
            );
        }

        this.gridPane.add(this.dateLabel, 1, 3);
        this.gridPane.add(this.startTimeLabel, 1, 4);
        this.gridPane.add(this.endTimeLabel, 1, 5);
        this.gridPane.add(this.doctorLabel, 1, 6);
        this.gridPane.add(this.description, 1, 7);
        this.gridPane.add(this.patientNameLabel, 1, 13);
        this.gridPane.add(this.contactNumberLabel, 1, 14);

    }
}
