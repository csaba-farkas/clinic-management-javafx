package com.clinigment.application.controller;

import com.clinigment.application.clients.UserAccountClient;
import com.clinigment.application.controller.exception.InvalidInputException;
import com.clinigment.application.model.UserAccount;
import com.clinigment.model.enums.AccountType;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by csaba on 16/05/2016.
 */
public class AddUserAccountSceneController implements Initializable {

    private static AddUserAccountSceneController INSTANCE;

    public static AddUserAccountSceneController getInstance() {
        return INSTANCE;
    }

    @FXML
    private TextField empIdField,
                      empNameField;

    @FXML
    private ComboBox<String> accountTypeCombo;

    @FXML
    private PasswordField passwordField,
                          confPasswordField;

    @FXML
    private Button backButton,
                   createButton;


    private Long employeeId;
    private String empName;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        INSTANCE = this;

        this.backButton.setOnAction(event -> {
            MainLayoutController.getInstance().setActive(
                    MainLayoutController.SCENES.get(
                            MainLayoutController.VIEW_EMPLOYEE_PANE_SINGLE
                    )
            );
        });

        List<String> accTypes = new ArrayList<>();
        accTypes.add("Administrator");
        accTypes.add("Receptionist");
        accTypes.add("Other");
        this.accountTypeCombo.setItems(
                FXCollections.observableArrayList(
                    accTypes
                )
        );

        this.createButton.setOnAction(event -> {
            String errorMessage = "";
            UserAccount ua = new UserAccount();
            ua.setEmployeeId(this.employeeId);
            try {
                if (this.accountTypeCombo.getSelectionModel().getSelectedIndex() < 0) {
                    errorMessage += "Account type must be selected\n";
                } else {
                    switch (this.accountTypeCombo.getSelectionModel().getSelectedItem()) {
                        case "Administrator":
                            ua.setAccountType(AccountType.ADMIN);
                            break;
                        case "Receptionist":
                            ua.setAccountType(AccountType.RECEPTIONIST);
                            break;
                        case "Other":
                            ua.setAccountType(AccountType.OTHER);
                            break;
                    }
                }

                if (passwordField.getText() == null || confPasswordField.getText() == null ||
                        passwordField.getText().trim().isEmpty() || confPasswordField.getText().trim().isEmpty()) {
                    errorMessage += "Password field and/or Confirm Password field cannot be empty\n";
                } else {
                    if (!passwordField.getText().equals(confPasswordField.getText())) {
                        errorMessage += "Passwords are not matching";
                    } else {
                        ua.setPassword(passwordField.getText());
                    }
                }

                if (errorMessage.isEmpty()) {
                    //Call the api
                    ua = UserAccountClient.createUserAccount(ua);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Use account added successfully. Username is " + ua.getUsername());
                    alert.initStyle(StageStyle.UTILITY);
                    alert.showAndWait();
                    MainLayoutController.getInstance().setActive(
                            MainLayoutController.SCENES.get(
                                    MainLayoutController.VIEW_EMPLOYEE_PANE_SINGLE
                            )
                    );
                } else {
                    throw new InvalidInputException(errorMessage);
                }
            } catch(InvalidInputException iie) {
                iie.printStackTrace();
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

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
        this.empIdField.setText(this.employeeId.toString());
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
        this.empNameField.setText(this.empName);
    }


}
