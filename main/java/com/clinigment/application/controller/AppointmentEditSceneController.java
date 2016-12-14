package com.clinigment.application.controller;

import com.clinigment.application.clients.AppointmentClient;
import com.clinigment.application.clients.EmployeeClient;
import com.clinigment.application.clients.PatientClient;
import com.clinigment.application.controller.exception.InvalidInputException;
import com.clinigment.application.model.Appointment;
import com.clinigment.application.model.Employee;
import com.clinigment.application.model.Patient;
import com.sun.javaws.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by csaba on 14/05/2016.
 */
public class AppointmentEditSceneController implements Initializable {

    private static AppointmentEditSceneController INSTANCE;

    public static AppointmentEditSceneController getInstance() {
        return INSTANCE;
    }

    private static Appointment appointment;

    @FXML
    private GridPane gridPane;

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<String> startTimeCombo,
            endTimeCombo,
            patientSearchCombo,
            doctorSearchCombo;
    @FXML
    private CheckBox isExistingPatient;

    @FXML
    private TextField firstNameField,
            lastNameField,
            contactNumberField,
            idField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private Button updateButton,
                   backButton;

    private ObservableList<String> time;

    private List<Appointment> apps;
    private List<Employee> emps;
    private ArrayList<String> toReturn;
    private List<Patient> allPatients;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        INSTANCE = this;

        try {
            emps = EmployeeClient.getAllDoctorsAndHygienists();
            List<String> listOfEmps = new ArrayList<>();
            for (Employee e : emps) {
                listOfEmps.add(e.getFirstName() + " " + e.getLastName());
            }
            this.doctorSearchCombo.setItems(
                    FXCollections.observableArrayList(
                            listOfEmps
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.initStyle(StageStyle.UTILITY);
            alert.show();

        }
        //Time values
        this.time = FXCollections.observableArrayList(
                "08:00", "08:30", "09:00", "09:30", "10:00", "10:30",
                "11:00", "11:30", "12:00", "12:30", "13:00", "13:30",
                "14:00", "14:30", "15:00", "15:30", "16:00", "16:30",
                "17:00", "17:30", "18:00", "18:30", "19:00", "19:30",
                "20:00", "20:30", "21:00"
        );

        /**
         * If the appoitnment is created for an existing patient, that is
         * already in the system, an API call is fired to get all the patients.
         * It loads them into the search ComboBox and the user, who creates the
         * appointment can select the patient from the list.
         */
        this.isExistingPatient.setOnAction(event -> {
            if (this.isExistingPatient.isSelected()) {
                this.patientSearchCombo.setDisable(false);
                this.firstNameField.setDisable(true);
                this.lastNameField.setDisable(true);
                this.contactNumberField.setDisable(true);

                this.allPatients = PatientClient.getAllPatient();
                List<String> comboContent = new ArrayList<String>();

                for (Patient p : allPatients) {
                    comboContent.add(p.getFullName() + " " + p.getPpsNumber());
                }

                final ObservableList<String> comboFiller = FXCollections.observableArrayList(
                        comboContent
                );

                this.patientSearchCombo.setItems(comboFiller);

            } else {
                this.patientSearchCombo.setDisable(true);
                this.firstNameField.setDisable(false);
                this.lastNameField.setDisable(false);
                this.contactNumberField.setDisable(false);
                this.firstNameField.clear();
                this.lastNameField.clear();
                this.contactNumberField.clear();
            }
        });

        /**
         * Set event handler on 'Search patient' ComboBox. If a patient is selected
         * from the list, the name fields are populated automatically.
         */
        this.patientSearchCombo.setOnAction(event -> {
            if(this.patientSearchCombo.getSelectionModel().getSelectedIndex() > -1) {
                this.firstNameField.setText(this.allPatients.get(
                        this.patientSearchCombo.getSelectionModel().getSelectedIndex()
                        ).getFirstName()
                );

                this.lastNameField.setText(this.allPatients.get(
                        this.patientSearchCombo.getSelectionModel().getSelectedIndex()
                        ).getLastName()
                );

                this.contactNumberField.setText(this.allPatients.get(
                        this.patientSearchCombo.getSelectionModel().getSelectedIndex()
                        ).getMobilePhone()
                );
            }
        });


        //Customize datepicker
        this.datePicker.setShowWeekNumbers(false);
        //Code is from https://docs.oracle.com/javase/8/javafx/user-interface-tutorial/date-picker.htm
        final Callback<DatePicker, DateCell> dayCellFactory =
                new Callback<DatePicker, DateCell>() {
                    @Override
                    public DateCell call(final DatePicker datePicker) {
                        return new DateCell() {
                            @Override
                            public void updateItem(LocalDate item, boolean empty) {
                                super.updateItem(item, empty);

                                if (item.isBefore(
                                        LocalDate.now())
                                        ) {
                                    setDisable(true);
                                    setStyle("-fx-background-color: #ffc0cb;");
                                }
                            }
                        };
                    }
                };
        datePicker.setDayCellFactory(dayCellFactory);
        datePicker.setOnAction(event -> {
            if (this.doctorSearchCombo.isDisable()) {
                if (this.doctorSearchCombo.getSelectionModel().getSelectedIndex() > -1) {
                    this.doctorSearchCombo.getSelectionModel().clearSelection();
                }
                this.doctorSearchCombo.setDisable(false);
            } else {
                //Disable 'Doctor search', 'Start time' and 'End time'
                if (this.doctorSearchCombo.getSelectionModel().getSelectedIndex() > -1) {
                    this.doctorSearchCombo.getSelectionModel().clearSelection();
                }

                if (this.startTimeCombo.getSelectionModel().getSelectedIndex() > -1) {
                    this.startTimeCombo.getSelectionModel().clearSelection();
                }
                this.startTimeCombo.setDisable(true);

                if (this.endTimeCombo.getSelectionModel().getSelectedIndex() > -1) {
                    this.endTimeCombo.getSelectionModel().clearSelection();
                }
                this.endTimeCombo.setDisable(true);
            }
        });

        /**
         * When the user selects an item from the 'Start Time' combobox
         * that is marked as BOOKED, the 'End Time' combobox is disabled.
         * The 'End Time' combobox is also populated with only those times
         * that can possibly be the end time of the appointment, so they
         * don't conflict with each other.
         */
        this.startTimeCombo.setOnAction(event -> {
            if (this.startTimeCombo.getSelectionModel().equals(null) || this.startTimeCombo.getValue() == null) {
                return;
            }
            String startTime = this.startTimeCombo.getValue();
            if (!startTime.equals("BOOKED")) {
                this.endTimeCombo.setDisable(false);
                populateTimes(endTimeCombo, this.startTimeCombo.getSelectionModel().getSelectedIndex());
            } else {
                this.endTimeCombo.setDisable(true);

            }
        });

        /**
         * When a new employee is selected, the program makes an call to the API.
         * It gets the appointment list of the selected employee, for the selected date.
         * Based on the existing appointments, it customizes the 'Start Time' combo box,
         * so the times that are already booked are marked as BOOKED
         */
        this.doctorSearchCombo.setOnAction(event -> {

            try {
                if (this.doctorSearchCombo.getSelectionModel().getSelectedIndex() > -1) {
                    //Disable 'End Time' combo
                    this.endTimeCombo.setDisable(true);
                    //Clear selection of 'Start Time' combo so its event handler is not called.
                    this.startTimeCombo.setItems(null);

                    DateTimeFormatter myFormatter = DateTimeFormatter.ofPattern("HH:mm");
                    Long doctorId = emps.get(this.doctorSearchCombo.getSelectionModel().getSelectedIndex()).getId();
                    this.apps = AppointmentClient.getAppointmentsByDoctorIdOnDate(this.datePicker.getValue(), doctorId);
                    List<String> startTimesToDisable = new ArrayList<String>();
                    List<String> endTimesToDisable = new ArrayList<String>();
                    for (Appointment a : this.apps) {
                        if(appointment == null) {
                            startTimesToDisable.add(a.getStartTime().format(myFormatter));
                            endTimesToDisable.add(a.getEndTime().format(myFormatter));
                        } else {
                            if(!appointment.getStartTime().equals(a.getStartTime())) {
                                startTimesToDisable.add(a.getStartTime().format(myFormatter));
                            }
                            if(!appointment.getEndTime().equals(a.getEndTime())) {
                                endTimesToDisable.add(a.getEndTime().format(myFormatter));
                            }
                        }

                    }
                    List<String> allTimesToDisable = disableTimes(startTimesToDisable, endTimesToDisable);
                    this.startTimeCombo.setItems(FXCollections.observableArrayList(allTimesToDisable));
                    this.startTimeCombo.setDisable(false);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.initStyle(StageStyle.UTILITY);
                alert.show();
            }
        });

        this.backButton.setOnAction(event ->  {
            this.isExistingPatient.setSelected(false);
            this.patientSearchCombo.setItems(null);
            appointment = null;
            MainLayoutController.getInstance().setActive(
                    MainLayoutController.SCENES.get(
                            MainLayoutController.APPOINTMENT_LIST_PANE_INDEX
                    )
            );
        });

        /**
         * Set up action handler on 'Update' button.
         */
        this.updateButton.setOnAction(event -> {

            try {
                String errorMessage = "";

                //Check date
                if(this.datePicker.getValue() == null) {
                    errorMessage += "Date is required\n";
                } else {
                    appointment.setDate(this.datePicker.getValue());
                }

                //Check doctor/hygienist is selected
                if(this.doctorSearchCombo.getSelectionModel().getSelectedIndex() < 0) {
                    errorMessage += "Doctor/Hygienist must be selected\n";
                } else {
                    appointment.setDoctorId(
                            this.emps.get(
                                    this.doctorSearchCombo.getSelectionModel().getSelectedIndex()
                            )
                                    .getId()
                    );
                }

                //Check start time
                if(this.startTimeCombo.getSelectionModel().getSelectedIndex() < 0) {
                    errorMessage += "Start time must be specified\n";
                } else {
                    String[] startHourAndMinuteArray = this.startTimeCombo.getSelectionModel().getSelectedItem().split(":");

                    if(this.datePicker.getValue() != null) {

                        LocalDateTime startDateTime = this.datePicker.getValue()
                                .atTime(
                                        Integer.parseInt(startHourAndMinuteArray[0]),
                                        Integer.parseInt(startHourAndMinuteArray[1])
                                );

                        appointment.setStartTime(startDateTime);
                    }
                }

                //Check end time
                if(this.endTimeCombo.getSelectionModel().getSelectedIndex() < 0) {
                    errorMessage += "End time must be specified\n";
                } else {
                    String[] endHourAndMinuteArray = this.endTimeCombo.getSelectionModel().getSelectedItem().split(":");

                    if(this.datePicker.getValue() != null) {

                        LocalDateTime endDateTime = this.datePicker.getValue()
                                .atTime(
                                        Integer.parseInt(endHourAndMinuteArray[0]),
                                        Integer.parseInt(endHourAndMinuteArray[1])
                                );
                        appointment.setEndTime(endDateTime);
                    }
                }

                //If 'existing patient'
                if(this.isExistingPatient.isSelected()) {
                    //Check if patient is selected in Patient search Combobox
                    if(this.patientSearchCombo.getSelectionModel().getSelectedIndex() < 0) {
                        errorMessage += "A patient must be selected from the list, or uncheck 'Existing Patient'\n";
                    } else {
                        appointment.setPatientId(
                                this.allPatients.get(
                                        this.patientSearchCombo.getSelectionModel().getSelectedIndex()
                                )
                                        .getId()
                        );

                        appointment.setPatientName(
                                this.allPatients.get(
                                        this.patientSearchCombo.getSelectionModel().getSelectedIndex()
                                )
                                        .getFirstName()
                                        + " " +
                                        this.allPatients.get(
                                                this.patientSearchCombo.getSelectionModel().getSelectedIndex()
                                        )
                                                .getLastName()
                        );

                        appointment.setContactNumber(
                                this.allPatients.get(
                                        this.patientSearchCombo.getSelectionModel().getSelectedIndex()
                                )
                                        .getMobilePhone()
                        );
                    }
                } else {
                    //Check if first name is entered
                    String firstName = "", lastName = "";
                    if(this.firstNameField.getText() == null || this.firstNameField.getText().trim().isEmpty()) {
                        errorMessage += "First name must be specified\n";
                    } else {
                        firstName = this.firstNameField.getText().trim();
                    }

                    //Check if last name is entered
                    if(this.lastNameField.getText() == null || this.lastNameField.getText().trim().isEmpty()) {
                        errorMessage += "Last name must be specified\n";
                    } else {
                        lastName = this.lastNameField.getText().trim();
                    }

                    //Check if contact number is empty
                    if(this.contactNumberField.getText() == null || this.contactNumberField.getText().trim().isEmpty()) {
                        errorMessage += "Contact number must be specified\n";
                    } else {
                        appointment.setContactNumber(this.contactNumberField.getText().trim());
                    }

                    if(!firstName.isEmpty() && !lastName.isEmpty()) {
                        appointment.setPatientName(firstName + " " + lastName);
                    }

                    if(!firstName.isEmpty() && !lastName.isEmpty()) {
                        appointment.setPatientName(firstName + " " + lastName);
                    }
                }

                //Check if description is empty
                if(this.descriptionArea.getText().trim().isEmpty()) {
                    errorMessage += "Description cannot be empty\n";
                } else {
                    appointment.setDescription(this.descriptionArea.getText());
                }

                //If error message length is greater than 0, throw InvalidInputException
                if(!errorMessage.isEmpty()) {
                    appointment = null;
                    throw new InvalidInputException(errorMessage);
                } else {
                    //Call the API to update the Appoitnment
                    appointment = AppointmentClient.updateAppointment(appointment);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment was updated successfully.");
                    alert.initStyle(StageStyle.UTILITY);
                    alert.show();
                    AppointmentTableSceneController.getInstance().updateAfterChange(appointment.getDate(), appointment.getId());
                    MainLayoutController.getInstance().setActive(
                            MainLayoutController.SCENES.get(
                                    MainLayoutController.APPOINTMENT_LIST_PANE_INDEX
                            )
                    );
                    appointment = null;
                }

            } catch (InvalidInputException iie) {
                Alert alert = new Alert(Alert.AlertType.ERROR, iie.getMessage());
                alert.initStyle(StageStyle.UTILITY);
                alert.show();
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.initStyle(StageStyle.UTILITY);
                alert.show();
            }
        });
    }

    /**
     * Populate form with the appointment details that is to be modified.
     *
     */
    public void populateForm() {
        try {
            this.idField.setText(appointment.getId().toString());
            this.datePicker.setValue((appointment.getDate()));
            this.doctorSearchCombo.setDisable(true);
            int selectionIndex = 0;
            for (Employee e : this.emps) {
                if (e.getId() == appointment.getDoctorId()) {
                    selectionIndex = this.emps.indexOf(e);
                }
            }
            this.doctorSearchCombo.setDisable(false);
            this.doctorSearchCombo.getSelectionModel().select(selectionIndex);

            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");

            String startTime = dateTimeFormatter.format(
                    appointment.getStartTime()
            );

            String endTime = dateTimeFormatter.format(
                    appointment.getEndTime()
            );

            this.startTimeCombo.setDisable(false);
            this.startTimeCombo.getSelectionModel().select(startTime);

            this.endTimeCombo.getSelectionModel().select(endTime);

            if(appointment.getPatientId() != null) {
                this.isExistingPatient.setSelected(true);
                this.patientSearchCombo.setDisable(false);
                this.patientSearchCombo.setDisable(false);
                this.firstNameField.setDisable(true);
                this.lastNameField.setDisable(true);
                this.contactNumberField.setDisable(true);

                this.allPatients = PatientClient.getAllPatient();
                List<String> comboContent = new ArrayList<String>();
                int selectIndex = 0;

                for (Patient p : allPatients) {
                    comboContent.add(p.getFullName() + " " + p.getPpsNumber());
                    if(p.getId() == appointment.getPatientId()) {
                        selectIndex = allPatients.indexOf(p);
                    }
                }

                final ObservableList<String> comboFiller = FXCollections.observableArrayList(
                        comboContent
                );

                this.patientSearchCombo.setItems(comboFiller);
                this.patientSearchCombo.getSelectionModel().select(selectIndex);
            } else {
                String[] fullName = appointment.getPatientName().split(" ");
                this.firstNameField.setText(fullName[0]);
                this.lastNameField.setText(fullName[1]);
                this.contactNumberField.setText(appointment.getContactNumber());
                this.firstNameField.setDisable(false);
                this.lastNameField.setDisable(false);
                this.contactNumberField.setDisable(false);
            }

            this.descriptionArea.setText(appointment.getDescription());
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    /**
     * The times that are already booked for a particular employee on a particular date
     * are marked as BOOKED. I tried to disable these elements in the combobox individually,
     * but JavaFX doesn't have any method to do that.
     *
     * @param startTimesToDisable Includes the start times of every booked appointment.
     * @param endTimesToDisable   Includes the end times of every booked appointment.
     * @return The modified list of times with BOOKED values where an appointment already exist.
     */
    private List<String> disableTimes(List<String> startTimesToDisable, List<String> endTimesToDisable) {
        if (toReturn == null) {
            toReturn = new ArrayList<>();
        } else {
            toReturn.clear();
        }

        //Copy times
        for (String s : this.time) {
            toReturn.add(s);
        }

        for (int i = 0; i < startTimesToDisable.size(); i++) {
            for (int j = 0; j < toReturn.size(); j++) {
                if (startTimesToDisable.get(i).equals(toReturn.get(j))) {
                    toReturn.set(j, "BOOKED");
                }
            }
        }

        for (int k = 0; k < endTimesToDisable.size(); k++) {
            for (int l = 0; l < toReturn.size(); l++) {
                if (toReturn.get(l).equals(endTimesToDisable.get(k))) {
                    int counter = l - 1;
                    while (!toReturn.get(counter).equals("BOOKED")) {
                        toReturn.set(counter, "BOOKED");
                        counter--;
                    }
                }
            }
        }
        return toReturn;
    }


    /**
     * Populate the combobox with valid values.
     *
     * @param combo     A ComboBox parameter that is to be populated.
     * @param indexFrom The starting index is the index of the selected value of the 'Start Time' ComboBox.
     */
    private void populateTimes(ComboBox<String> combo, int indexFrom) {
        //Calculate 'end index'. It is the index of the next 'BOOKED' value in the toReturn list.
        int endIndex = this.time.size();
        for (int i = indexFrom; i < this.time.size(); i++) {
            if (i + 1 >= this.time.size()) {
                break;
            } else if (this.toReturn.get(i + 1).equals("BOOKED")) {
                endIndex = i + 2;
                break;
            }
        }
        ObservableList<String> subList = FXCollections.observableArrayList(this.time.subList(indexFrom + 1, endIndex));
        combo.setItems(subList);
    }

    public static void setAppointment(Appointment newAppointment) {
        appointment = newAppointment;
    }
}
