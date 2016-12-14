/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.clinigment.application.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import com.clinigment.application.model.Employee;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;


/**
 *
 * @author csaba
 */
public class HomeSceneController implements Initializable {

    private static HomeSceneController INSTANCE;
    private Label usernameLabel;

    public static HomeSceneController getInstance() {
        return INSTANCE;
    }

    private Employee employee;

    private String username;

    @FXML
    private GridPane gridPane;

    @FXML
    private Text greetings;

    @FXML
    private Button goToCalendarButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        INSTANCE = this;

        this.goToCalendarButton.setOnAction(event ->  {
            MainLayoutController.getInstance().setActive(
                    MainLayoutController.SCENES.get(
                            MainLayoutController.APPOINTMENT_LIST_PANE_INDEX
                    )
            );
            AppointmentTableSceneController.getInstance().updateAfterChange(
                    LocalDate.now(),
                    employee.getId()
            );
        });


    }

    public void setEmployee(Employee emp) {
        employee = emp;
        updateScene();
    }

    private void updateScene() {
        Label nameLabel = new Label(employee.getTitle() + " " + employee.getFirstName() + " " + employee.getLastName());
        Label dobLabel = new Label(employee.getDateOfBirth().toString());
        Label mobileLabel = new Label(employee.getMobilePhone());
        this.usernameLabel = new Label("test");

        this.greetings.setText(
                this.greetings.getText() + employee.getTitle() + " " + employee.getLastName()
        );

        this.gridPane.add(nameLabel, 1, 3);
        this.gridPane.add(dobLabel, 1, 4);
        this.gridPane.add(mobileLabel, 1, 5);
        this.gridPane.add(usernameLabel, 1, 6);
    }

    public void setUsername(String username) {
        this.username = username;
        this.usernameLabel.setText(this.username);
    }

    public String getUsername() {
        return this.username;
    }


}
