/*
Author:
Last Changed: 17/03/2016
Purpose:
*/
package com.clinigment.application.controller;

import com.clinigment.application.abstracts.LayoutController;
import com.clinigment.application.clients.*;
import com.clinigment.application.model.Employee;
import com.clinigment.application.model.LoginForm;
import com.clinigment.application.model.UserAccount;
import com.clinigment.application.navigator.LayoutContentNavigator;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;
//import org.clingiment.backend.api.LoginEntity;
//import org.clingiment.backend.api.LoginEntity;

public class LoginController extends LayoutController implements Initializable {
	
        private static LoginController instance;
        
        public static LoginController getInstance() {
            if(instance == null) {
                instance = new LoginController();
            }
            return instance;
        }
        
        public LoginController() {
            super();
        }
        
	@FXML private TextField usernameField;
	@FXML private PasswordField passwordField;
	@FXML private Button button;
	@FXML private Text confirmText;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
            instance = this;
            button.setDefaultButton(true);
	}
	
	@FXML
	public void validateLoginDetails() throws IOException {

            LoginForm loginEntity = new LoginForm();
            String username = usernameField.getText();
            String password = passwordField.getText();
            loginEntity.setUsername(username);
            loginEntity.setPassword(password);

                try {
                    Employee employee = LoginClient.login(loginEntity);
                    LayoutContentNavigator.loadLayout(LayoutContentNavigator.MAIN_LAYOUT);

                    HomeSceneController.getInstance().setEmployee(employee);
                    HomeSceneController.getInstance().setUsername(username);

                    AppointmentClient.setUsername(username);
                    AppointmentClient.setPassword(password);
                    EmployeeClient.setUsername(username);
                    EmployeeClient.setPassword(password);
                    PatientClient.setUsername(username);
                    PatientClient.setPassword(password);
                    UserAccountClient.setUsername(username);
                    UserAccountClient.setPassword(password);
                } catch (Exception e) {
                    e.printStackTrace();
                    usernameField.clear();
                    passwordField.clear();
                    usernameField.requestFocus();
                    Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                    alert.initStyle(StageStyle.UTILITY);
                    alert.show();
                }


	}

    @Override
    public void setLayout(Node node) {
       
    }

   
    @Override
    public void setLayout(Node node, Pane container) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

