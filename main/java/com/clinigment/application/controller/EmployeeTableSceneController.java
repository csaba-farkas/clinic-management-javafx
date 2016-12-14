/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clinigment.application.controller;


import com.clinigment.application.abstracts.LayoutController;
import com.clinigment.application.clients.EmployeeClient;
import com.clinigment.application.model.Employee;
import com.clinigment.application.model.Employee;
import com.clinigment.application.model.Patient;
import com.clinigment.model.enums.EmpRole;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

import javax.management.relation.Role;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author csaba
 */
public class EmployeeTableSceneController extends LayoutController implements Initializable {
    private static EmployeeTableSceneController instance = null;

    public static EmployeeTableSceneController getInstance() {
        if (instance == null) {
            instance = new EmployeeTableSceneController();
        }
        return instance;
    }

    @FXML
    private AnchorPane tablePane, container, EmployeeInfoPane;

    @FXML
    private StackPane stackPane;

    @FXML
    private TableView tableView;

    @FXML
    private Button addNewEmployeeButton;

    @FXML
    private TableColumn colAction;
    @FXML
    private TableColumn<Employee, String> colEmployeeRole,
            colFirstName,
            colLastName,
            colPpsNumber,
            colDateOfBirth,
            colGender,
            colBloodType,
            colMobilePhone,
            colHomePhone,
            colEmail;


    private static ObservableList<Employee> employees ;

    @Override
    public void setLayout(Node node) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setLayout(Node node, Pane container) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instance = this;
        this.employees = getEmployees();

        colEmployeeRole.setCellValueFactory(new PropertyValueFactory<>("roleString"));
        colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        colPpsNumber.setCellValueFactory(new PropertyValueFactory<>("ppsNumber"));
        colDateOfBirth.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        colMobilePhone.setCellValueFactory(new PropertyValueFactory<>("mobilePhone"));
        colHomePhone.setCellValueFactory(new PropertyValueFactory<>("homePhone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));


        //Set items
        tableView.setItems(this.employees);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        colAction.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object, Boolean>, ObservableValue<Boolean>>() {
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
        try {
            addNewEmployeeButton.setOnAction(e -> {
                System.out.println("Clicked");
                MainLayoutController.getInstance().setActive(MainLayoutController.SCENES.get(MainLayoutController.NEW_EMPLOYEE_PANE_INDEX));
            });
        } catch (NullPointerException e) {

        }

    }

    public void update(Employee newEmployee) {
        employees = getEmployees();
        for (Employee e : employees) {
            System.out.println(e);
        }

        tableView.getItems().add(newEmployee);

    }

    public void edit(Employee oldEmployee, Employee newEmployee){
        //tableView.getItems()
        System.out.println("DURING EDIT-OLD EMPLOYEE: "+oldEmployee);
        System.out.println("DURING EDIT-NEW EMPLOYEE: "+newEmployee);
        tableView.getItems().add(newEmployee);
        tableView.getItems().remove(oldEmployee);

    }

    @FXML
    public void saveEmployee() {
    }

    @FXML
    public void goBack() {
    }

    @FXML
    public void addNewEmployee() {
    }

    @FXML
    public void showEmployeeData(MouseEvent event) {
    }

    public ObservableList<Employee> getEmployees() {

        List<Employee> emps = null;
        try {
            emps = EmployeeClient.getAllEmployees();
        } catch (Exception e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
            alert.initStyle(StageStyle.UTILITY);
            alert.show();
        }

        for (Employee e : emps) {
            e.getRoleString();
        }

        this.employees = FXCollections.observableArrayList(emps);
        return employees;

    }

    public TableView getTableView() {
        return tableView;
    }

    private class ActionTableCell extends TableCell<Object, Boolean> {

        final Hyperlink viewButton = new Hyperlink("View");
        final Hyperlink editButton = new Hyperlink("Edit");
        final HBox hb = new HBox(viewButton, editButton);

        ActionTableCell(final TableView tblView) {
            hb.setSpacing(4);
            viewButton.setOnAction((ActionEvent t) -> {
                //status = 1;
                int row = getTableRow().getIndex();
                Employee employeeIn = (Employee) tableView.getItems().get(row);
                System.out.println(employeeIn);

                MainLayoutController.getInstance().setActive(MainLayoutController.SCENES.get(MainLayoutController.VIEW_EMPLOYEE_PANE_SINGLE));

                ViewEmployeeDetailsSingleController.getInstance().putData(employeeIn);

            });

            editButton.setOnAction((ActionEvent event) -> {
                //status = 1;
                int row = getTableRow().getIndex();
                Employee employeeIn = (Employee) tableView.getItems().get(row);
                System.out.println(employeeIn);

                MainLayoutController.getInstance().setActive(MainLayoutController.SCENES.get(MainLayoutController.EDIT_EMPLOYEE_PANE_INDEX));

                System.out.println(EditEmployeeController.getInstance());
                EditEmployeeController.getInstance().putData(employeeIn);;
                System.out.println("Controller from second instantiation" + EditEmployeeController.getInstance());
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(hb);
            } else {
                setGraphic(null);
            }
        }
    }
}


