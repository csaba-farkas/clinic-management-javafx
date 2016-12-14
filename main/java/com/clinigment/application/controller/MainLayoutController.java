package com.clinigment.application.controller;

import com.clinigment.application.abstracts.LayoutController;
import com.clinigment.application.navigator.LayoutContentNavigator;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Csaba Farkas
 */
public class MainLayoutController extends LayoutController implements Initializable {

    //StackPane indexes
    protected static final Integer HOME_PANE_INDEX = 0;
    protected static final Integer PATIENT_LIST_PANE_INDEX = 1;
    protected static final Integer NEW_PATIENT_PANE_INDEX = 2;
    protected static final Integer NEW_PATIENT_PANE_INDEX_2 = 3;
    protected static final Integer NEW_APPOINTMENT_INDEX = 4;
    protected static final Integer VIEW_NEW_PATIENT_PANE_INDEX_5 = 5;
    protected static final Integer APPOINTMENT_LIST_PANE_INDEX = 6;
    protected static final Integer VIEW_PATIENT_PANE_SINGLE = 7;
    protected static final Integer EDIT_PATIENT_PANE_INDEX = 8;
    protected static final Integer NEW_EMPLOYEE_PANE_INDEX = 9;
    protected static final Integer VIEW_APPOINTMENT_PANE_INDEX = 10;
    protected static final Integer EDIT_APPOINTMENT_PANE_INDEX = 11;
    protected static final Integer VIEW_NEW_EMPLOYEE_PANE_INDEX = 12;
    protected static final Integer EMPLOYEE_LIST_PANE_INDEX = 13;
    protected static final Integer VIEW_EMPLOYEE_PANE_SINGLE = 14;
    protected static final Integer EDIT_EMPLOYEE_PANE_INDEX = 15;
    protected static final Integer ADD_NEW_USERACCOUNT = 16;


    private StackPane homePane,
            patientList,
            addNewPatient,
            addNewPatient2,
            editPatient,
            viewPatient1,
            viewPatient2,
            appointmentList,
            addNewAppointment,
            addNewEmployee,
            viewAppointmentDetails,
            editAppointment,
            viewEmployee1,
            viewEmployee2,
            employeeList,
            editEmployee,
            addNewUserAccount;

    private static MainLayoutController instance;

    public static MainLayoutController getInstance() {
        return instance;
    }

    public MainLayoutController() {
        super();
    }

    @FXML
    private TreeView<String> menuTree;

    @FXML
    private BorderPane container;

    StackPane activePane = null;

    protected static final Map<Integer, StackPane> SCENES = new HashMap<>();

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        instance = this;
        TreeItem<String> root, home, patients, employee, appointments;
        root = new TreeItem<>();
        root.setExpanded(false);

        home = makeBranch("Home", root);

        patients = makeBranch("Patients", root);

        makeBranch("Patient List", patients);
        makeBranch("New Patient", patients);
        patients.setExpanded(false);

        appointments = makeBranch("Calendar", root);
        makeBranch("Calendar", appointments);
        makeBranch("New Appointment", appointments);
        appointments.setExpanded(false);

        employee = makeBranch("Employees", root);
        makeBranch("Employee List", employee);
        makeBranch("New Employee", employee);
        employee.setExpanded(false);

        root.setExpanded(false);
        menuTree.setRoot(root);
        menuTree.setShowRoot(false);

        ChangeListener<Boolean> expandedListener = (obs, wasExpanded, isNowExpanded) -> {
            if (isNowExpanded) {
                ReadOnlyProperty<?> expandedProperty = (ReadOnlyProperty<?>) obs ;
                Object itemThatWasJustExpanded = expandedProperty.getBean();
                for (TreeItem<String> item : menuTree.getRoot().getChildren()) {
                    if (item != itemThatWasJustExpanded) {
                        item.setExpanded(false);
                    }
                }
            }
        };

        PseudoClass subElementPseudoClass = PseudoClass.getPseudoClass("sub-tree-item");

        menuTree.setCellFactory(tv -> {
            TreeCell<String> cell = new TreeCell<String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setDisclosureNode(null);

                    if (empty) {
                        setText("");
                        setGraphic(null);
                    } else {
                        setText(item); // appropriate text for item
                        setOnMouseClicked(event -> {
                            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
                                TreeItem<String> ti = treeItemProperty().get();
                                ti.expandedProperty().set(true);
                            }
                            event.consume();
                        });
                    }
                }

            };
            cell.treeItemProperty().addListener((obs, oldTreeItem, newTreeItem) -> {
                cell.pseudoClassStateChanged(subElementPseudoClass,
                        newTreeItem != null && newTreeItem.getParent() != cell.getTreeView().getRoot());
            });
            return cell ;
        });

        menuTree.getSelectionModel().select(home);
        StackPane containerPane = new StackPane();
        homePane = null;
        patientList = null;
        addNewPatient = null;
        addNewPatient2 = null;
        editPatient = null;

        viewPatient1 = null;
        viewPatient2 = null;
        appointmentList = null;
        addNewAppointment = null;
        addNewEmployee = null;
        viewAppointmentDetails = null;
        editAppointment = null;
        viewEmployee1 = null;
        viewEmployee2 = null;
        editEmployee = null;
        employeeList = null;
        addNewUserAccount = null;
        //

        try {
            homePane = (StackPane) FXMLLoader.load(LayoutContentNavigator.class.getResource(LayoutContentNavigator.HOME_SCENE));
            SCENES.put(HOME_PANE_INDEX, homePane);

            patientList = (StackPane) FXMLLoader.load(LayoutContentNavigator.class.getResource(LayoutContentNavigator.PATIENT_TABLE_SCENE));
            SCENES.put(PATIENT_LIST_PANE_INDEX, patientList);
            addNewPatient = (StackPane) FXMLLoader.load(LayoutContentNavigator.class.getResource(LayoutContentNavigator.ADD_NEW_PATIENT_SCENE_1));
            SCENES.put(NEW_PATIENT_PANE_INDEX, addNewPatient);
            addNewPatient2 = (StackPane) FXMLLoader.load(LayoutContentNavigator.class.getResource(LayoutContentNavigator.ADD_NEW_PATIENT_SCENE_2));
            SCENES.put(NEW_PATIENT_PANE_INDEX_2, addNewPatient2);

            editPatient = (StackPane) FXMLLoader.load(LayoutContentNavigator.class.getResource(LayoutContentNavigator.EDIT_PATIENT_SCENE_1));
            SCENES.put(EDIT_PATIENT_PANE_INDEX, editPatient);

            viewPatient1 = (StackPane) FXMLLoader.load(LayoutContentNavigator.class.getResource(LayoutContentNavigator.VIEW_PATIENT_SCENE));
            SCENES.put(VIEW_NEW_PATIENT_PANE_INDEX_5, viewPatient1);
            viewPatient2 = (StackPane) FXMLLoader.load(LayoutContentNavigator.class.getResource(LayoutContentNavigator.VIEW_PATIENT_SCENE_SINGLE));
            SCENES.put(VIEW_PATIENT_PANE_SINGLE, viewPatient2);

            AddNewPatientController.getInstance().setUpSecondScene();   //Second scene is set up now
            //System.out.println("from main layout controller" + AddNewPatientController.getInstance());

            addNewAppointment = (StackPane)FXMLLoader.load(LayoutContentNavigator.class.getResource(LayoutContentNavigator.ADD_NEW_APPOINTMENT));
            SCENES.put(NEW_APPOINTMENT_INDEX, addNewAppointment);


            appointmentList = (StackPane) FXMLLoader.load(LayoutContentNavigator.class.getResource(LayoutContentNavigator.APPOINTMENT_TABLE_SCENE));
            SCENES.put(APPOINTMENT_LIST_PANE_INDEX, appointmentList);

            addNewEmployee = (StackPane) FXMLLoader.load(LayoutContentNavigator.class.getResource(LayoutContentNavigator.ADD_NEW_EMPLOYEE));
            SCENES.put(NEW_EMPLOYEE_PANE_INDEX, addNewEmployee);

            viewAppointmentDetails = (StackPane) FXMLLoader.load(LayoutContentNavigator.class.getResource(LayoutContentNavigator.VIEW_APPOINTMENT));
            SCENES.put(VIEW_APPOINTMENT_PANE_INDEX, viewAppointmentDetails);

            editAppointment = (StackPane) FXMLLoader.load(LayoutContentNavigator.class.getResource(LayoutContentNavigator.EDIT_APPOINTMENT));
            SCENES.put(EDIT_APPOINTMENT_PANE_INDEX, editAppointment);

            viewEmployee1 = (StackPane) FXMLLoader.load(LayoutContentNavigator.class.getResource(LayoutContentNavigator.VIEW_EMPLOYEE_SCENE));
            SCENES.put(VIEW_NEW_EMPLOYEE_PANE_INDEX, viewEmployee1);

            viewEmployee2 = (StackPane) FXMLLoader.load(LayoutContentNavigator.class.getResource(LayoutContentNavigator.VIEW_EMPLOYEE_SCENE_SINGLE));
            SCENES.put(VIEW_EMPLOYEE_PANE_SINGLE, viewEmployee2);

            employeeList = (StackPane) FXMLLoader.load(LayoutContentNavigator.class.getResource(LayoutContentNavigator.EMPLOYEE_TABLE_SCENE));
            SCENES.put(EMPLOYEE_LIST_PANE_INDEX, employeeList);

            editEmployee = (StackPane) FXMLLoader.load(LayoutContentNavigator.class.getResource(LayoutContentNavigator.EDIT_EMPLOYEE_SCENE));
            SCENES.put(EDIT_EMPLOYEE_PANE_INDEX, editEmployee);

            addNewUserAccount = (StackPane) FXMLLoader.load(LayoutContentNavigator.class.getResource(LayoutContentNavigator.ADD_NEW_USER_ACCOUNT_SCENE));
            SCENES.put(ADD_NEW_USERACCOUNT, addNewUserAccount);

        } catch (IOException ex) {
            Logger.getLogger(MainLayoutController.class.getName()).log(Level.SEVERE, null, ex);
        }


        containerPane.getChildren().addAll(
                addNewUserAccount,
                editEmployee,
                viewEmployee2,
                viewEmployee1,
                employeeList,
                editAppointment,
                viewAppointmentDetails,
                addNewEmployee,
                editPatient,
                addNewAppointment,
                appointmentList,
                viewPatient1,
                viewPatient2,
                addNewPatient2,
                addNewPatient,
                patientList,
                homePane);

        patientList.setVisible(false);
        addNewPatient.setVisible(false);
        addNewPatient2.setVisible(false);
        editPatient.setVisible(false);
        viewPatient1.setVisible(false);
        viewPatient2.setVisible(false);
        appointmentList.setVisible(false);
        addNewAppointment.setVisible(false);
        addNewEmployee.setVisible(false);
        viewAppointmentDetails.setVisible(false);
        editAppointment.setVisible(false);
        employeeList.setVisible(false);
        editEmployee.setVisible(false);
        viewEmployee1.setVisible(false);
        viewEmployee2.setVisible(false);
        addNewUserAccount.setVisible(false);

        container.setCenter(containerPane);

        activePane = SCENES.get(HOME_PANE_INDEX);

        menuTree.getSelectionModel().selectedItemProperty().addListener(
                (ObservableValue<? extends TreeItem<String>> observable,
                 TreeItem<String> oldValue,
                 TreeItem<String> newValue) -> {
                    if(newValue.getValue().equals("Patients") || newValue.getValue().equals("Patient List")) {
                        setActive(SCENES.get(PATIENT_LIST_PANE_INDEX));
                    } else if(newValue.getValue().equals("New Patient")) {
                        setActive(SCENES.get(NEW_PATIENT_PANE_INDEX));
                    } else if(newValue.getValue().equals("Home")) {
                        setActive(SCENES.get(HOME_PANE_INDEX));
                    } else if(newValue.getValue().equals("Calendar") ||newValue.getValue().equals("Calendar")) {
                        setActive(SCENES.get(APPOINTMENT_LIST_PANE_INDEX));
                    } else if (newValue.getValue().equals("New Appointment")) {
                        setActive(SCENES.get(NEW_APPOINTMENT_INDEX));
                    } else if(newValue.getValue().equals("Employees") ||newValue.getValue().equals("Employee List")) {
                        setActive(SCENES.get(EMPLOYEE_LIST_PANE_INDEX));
                    } else if (newValue.getValue().equals("New Employee")) {
                        setActive(SCENES.get(NEW_EMPLOYEE_PANE_INDEX));
                    }
          });

    }

    public StackPane getHomepane(){
        return this.homePane;
    }

    public StackPane getPatientListPane(){
        return this.patientList;
    }

    public StackPane getAddNewPatientPane1(){
        return this.addNewPatient;
    }

    public StackPane getAddNewPatientPane2(){
        return this.addNewPatient2;
    }

    public StackPane getViewPatient1(){
        return this.viewPatient1;
    }

    public StackPane getViewPatient2(){
        return this.viewPatient2;
    }

    public StackPane getAppointmentListPane(){
        return this.homePane;
    }

    public StackPane getAddNewAppointmentPane(){
        return this.addNewAppointment;
    }

    /*public StackPane getAddCalendarPane(){
        return this.addCalendar;
    }*/

    private TreeItem<String> makeBranch(String string, TreeItem<String> root) {
        TreeItem<String> branch = new TreeItem<>(string);
        branch.setExpanded(true);

        root.getChildren().add(branch);
        return branch;
    }

    @Override
    public void setLayout(Node node) {
        container.setCenter(node);
    }

    @Override
    public void setLayout(Node node, Pane container) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Change panel visible
     * @param newActivePane
     */
    public void setActive(StackPane newActivePane) {
        if(activePane != newActivePane) {
            fadeOut(activePane);
            activePane.setVisible(false);
            activePane = newActivePane;
            activePane.setVisible(true);
            fadeIn(activePane);
        }
    }

    /**
     * Fade out panel
     * @param active
     */
    private void fadeOut(StackPane active) {
        FadeTransition ft = new FadeTransition(Duration.millis(500), active);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        ft.play();
    }

    /**
     * Fade in panel
     * @param active
     */
    private void fadeIn(StackPane active) {
        FadeTransition ft = new FadeTransition(Duration.millis(500), active);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }

}
