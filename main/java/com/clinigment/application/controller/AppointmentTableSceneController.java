
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clinigment.application.controller;


import com.clinigment.application.abstracts.LayoutController;
import com.clinigment.application.clients.AppointmentClient;
import com.clinigment.application.clients.EmployeeClient;
import com.clinigment.application.model.Appointment;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import com.clinigment.application.model.Employee;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 *
 * @author csaba
 */
public class AppointmentTableSceneController extends LayoutController implements Initializable {

    private static AppointmentTableSceneController INSTANCE;
    public static String test;

    public static AppointmentTableSceneController getInstance() {
        return INSTANCE;
    }

    @FXML
    private AnchorPane tablePane, container, patientInfoPane;
   
    @FXML
    private StackPane stackPane;
    
    @FXML
    private TableView tableView;
    
    @FXML
    private Button updateButton;
    
    @FXML
    private TableColumn colAction;

    @FXML
    private TableColumn<Appointment, LocalDateTime> colStartTime,
                                                    colEndTime;

    @FXML
    private TableColumn<Appointment, String> colPatientName,
                                             colContactNumber;

    @FXML
    private ComboBox<String> employeeCombo;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Text mainLabel;


    private List<Employee> employees;
    private List<Appointment> appointments;
    private ObservableList<Appointment> tableAppointments;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        INSTANCE = this;

        colStartTime.setCellFactory(column -> {
            return new TimeTableCell();
        });

        colEndTime.setCellFactory(column -> {
            return new TimeTableCell();
        });

        colStartTime.setCellValueFactory(cellData -> cellData.getValue().startTimePropertyProperty());
        colEndTime.setCellValueFactory(cellData -> cellData.getValue().endTimePropertyProperty());
        colPatientName.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        colContactNumber.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));

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

        //Populate 'Select Doctor/Hygienist with employees
        try {
            this.employees = EmployeeClient.getAllDoctorsAndHygienists();
            List<String> listOfEmps = new ArrayList<>();
            for (Employee e : this.employees) {
                listOfEmps.add(e.getFirstName() + " " + e.getLastName());
            }
            this.employeeCombo.setItems(
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

        //Customize datepicker
        this.datePicker.setShowWeekNumbers(false);

        //Set on action on 'Doctor/Hygienist' ComboBox and DatePicker
        this.employeeCombo.setOnAction(event -> {
            if(this.datePicker.getValue() != null) {
                updateCalendar();
            }
        });

        //Set the same action on date picker a bit differently -> call 'update' if employee is selected
        this.datePicker.setOnAction(event -> {
            if(this.employeeCombo.getSelectionModel().getSelectedIndex() > -1) {
                this.mainLabel.setText("Calendar " + this.datePicker.getValue());
                updateCalendar();
            }
        });
    }

    @FXML
    public void updateCalendar() {
        Long empId = this.employees.get(
                this.employeeCombo.getSelectionModel().getSelectedIndex()
        ).getId();

        LocalDate date = this.datePicker.getValue();

        try {
            this.appointments = AppointmentClient.getAppointmentsByDoctorIdOnDate(date, empId);
            this.tableAppointments = FXCollections.observableArrayList(this.appointments);
            this.tableView.setItems(this.tableAppointments);
            this.tableView.refresh();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.initStyle(StageStyle.UTILITY);
            alert.show();
        }
    }

    public void updateAfterChange(LocalDate localDate, Long employeeId) {
        this.datePicker.setValue(localDate);
        int index = 0;
        for(Employee e : this.employees) {
            if(e.getId() == employeeId) {
                index = this.employees.indexOf(e);
            }
        }
        this.employeeCombo.getSelectionModel().select(index);
        updateCalendar();
    }

    @FXML
    private void showAppointmentData(MouseEvent event, int row) {
        MainLayoutController.getInstance().setActive(
                MainLayoutController.SCENES.get(
                        MainLayoutController.VIEW_APPOINTMENT_PANE_INDEX
                )
        );

        AppointmentDetailsSceneController.getInstance().populateForm(
                (Appointment) this.tableView.getSelectionModel().getSelectedItem()
        );
    }

    private void updateAppointmentData() {
        MainLayoutController.getInstance().setActive(
                MainLayoutController.SCENES.get(
                    MainLayoutController.EDIT_APPOINTMENT_PANE_INDEX
                )
        );

        AppointmentEditSceneController.getInstance().setAppointment(
                (Appointment) this.tableView.getSelectionModel().getSelectedItem()
        );
        AppointmentEditSceneController.getInstance().populateForm();
    }

    private void deleteAppointment() {
        try {
            AppointmentClient.deleteteAppointment(
                    (Appointment) this.tableView.getSelectionModel().getSelectedItem()
            );
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Appointment was created successfully.");
            alert.initStyle(StageStyle.UTILITY);
            alert.show();
            updateCalendar();
            this.tableView.refresh();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, e.getMessage());
            alert.initStyle(StageStyle.UTILITY);
            alert.show();
        }
    }

    private class TimeTableCell extends TableCell<Appointment, LocalDateTime> {
        DateTimeFormatter myDateFormatter = DateTimeFormatter.ofPattern("HH:mm");

        @Override
        protected void updateItem(LocalDateTime item, boolean empty) {
            super.updateItem(item, empty);
            if(item == null || empty) {
                setText(null);
            } else {
                setText(myDateFormatter.format(item));
            }

        }
    }

    @Override
    public void setLayout(Node node) {
    }

    @Override
    public void setLayout(Node node, Pane container) {
    }


    private class ActionTableCell extends TableCell<Object, Boolean> {
        
        final Hyperlink viewButton = new Hyperlink("View");
        final Hyperlink editButton = new Hyperlink("Edit");
        final Hyperlink deleteButton = new Hyperlink("Delete");
        final HBox hb = new HBox(viewButton, editButton, deleteButton);
        
        ActionTableCell (final TableView tblView){
            hb.setSpacing(4);
            viewButton.setOnAction((ActionEvent t) -> {
                int row = getTableRow().getIndex();
                tableView.getSelectionModel().select(row);
                showAppointmentData(null, row);

            });
            
            editButton.setOnAction((ActionEvent event) -> {
                int row = getTableRow().getIndex();
                tableView.getSelectionModel().select(row);
                updateAppointmentData();
            });

            deleteButton.setOnAction((ActionEvent event) -> {
                int row = getTableRow().getIndex();
                tableView.getSelectionModel().select(row);
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete the selected appointment?");
                alert.initStyle(StageStyle.UTILITY);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    deleteAppointment();
                }
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




