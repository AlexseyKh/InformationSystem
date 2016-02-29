/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem.view.employeeTable;

import informationsystem.controller.Controller;
import informationsystem.controller.ControllerToServer;
import informationsystem.model.dataClasses.Employee;
import informationsystem.view.main.Main;
import informationsystem.view.model.ViewDepartment;
import informationsystem.view.model.ViewEmployee;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Игорь
 */
public class EmployeeEditDialogController {

    @FXML
    private ChoiceBox<String> departmentChoiceBox;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField functionField;
    @FXML
    private TextField salaryField;

    private Stage dialogStage;
    private ViewEmployee employee;
    private boolean okClicked = false;
    private ControllerToServer con;
    private boolean isNew;
    private Main main;

    @FXML
    private void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    public void setEmployee(ViewEmployee employee) {
        this.employee = employee;

        firstNameField.setText(employee.getFirstName());
        lastNameField.setText(employee.getLastName());
        functionField.setText(employee.getFunction());
        salaryField.setText(Integer.toString(employee.getSalary()));

        ObservableList<String> departments = FXCollections.observableArrayList();
        for (ViewDepartment dep : main.getDepartments()) {
            departments.add(dep.getName());
        }
        departmentChoiceBox.setItems(departments);
        if (employee.getId() != -1) {
            departmentChoiceBox.getSelectionModel().select(employee.getDepartment());
        } else if (main.getDepartments().size() != 0) {
            departmentChoiceBox.getSelectionModel().select(main.getDepartments().get(0).getName());
        }
    }

    public void setController(ControllerToServer con) {
        this.con = con;
    }

    public boolean isOkClicked() {
        return okClicked;
    }

    public void setPurpose(boolean isNew) {
        this.isNew = isNew;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    @FXML
    private void handleOk() {
        if (isInputValid()) {

            okClicked = true;
            dialogStage.close();
        }
    }

    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    private boolean isInputValid() {
        String errorMessage = "";

        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "Неверная фамилия!\n";
        }
        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Неверное имя!\n";
        }
        if (functionField.getText() == null || functionField.getText().length() == 0) {
            errorMessage += "Неверная должность!\n";
        }

        if (salaryField.getText() == null || salaryField.getText().length() == 0) {
            errorMessage += "Неверная зарплата!\n";
        } else {
            try {
                Integer.parseInt(salaryField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Неверная зарплата (должно быть неотрицательным числом)!\n";
            }
        }
        if (errorMessage.length() == 0) {
            if (isNew) {
                switch (con.addEmployee(departmentChoiceBox.getSelectionModel().getSelectedItem(),
                        firstNameField.getText(), lastNameField.getText(), functionField.getText(), Integer.parseInt(salaryField.getText()))) {
                    case -1:
                        errorMessage += "Такой сотрудник уже существует!\n";
                        break;
                    case 0:
                        errorMessage += "Не существует такого отдела!\n";
                        break;
                    case 1:
                        employee.setFirstName(firstNameField.getText());
                        employee.setLastName(lastNameField.getText());
                        employee.setFunction(functionField.getText());
                        employee.setSalary(Integer.parseInt(salaryField.getText()));
                        employee.setDepartment(departmentChoiceBox.getSelectionModel().getSelectedItem());
                        employee.setId(con.getLastAddedEmployee().getId());
                        break;
                }
            } else {
                switch (con.editEmployee(employee.getId(), departmentChoiceBox.getSelectionModel().getSelectedItem(),
                        firstNameField.getText(), lastNameField.getText(), functionField.getText(), Integer.parseInt(salaryField.getText()))) {
                    case -1:
                        errorMessage += "Такой сотрудник уже существует!\n";
                        break;
                    case 0:
                        errorMessage += "Не существует такого отдела!\n";
                        break;
                    case 1:
                        String prevName = employee.getLastName() + " " + employee.getFirstName();
                        employee.setFirstName(firstNameField.getText());
                        employee.setLastName(lastNameField.getText());
                        employee.setFunction(functionField.getText());
                        employee.setSalary(Integer.parseInt(salaryField.getText()));
                        employee.setDepartment(departmentChoiceBox.getSelectionModel().getSelectedItem());
                        for (ViewDepartment vDep : main.getDepartments()) {
                            if (vDep.getDirectorName().equals(prevName)) {
                                vDep.setDirectorName(employee.getLastName() + " " + employee.getFirstName());
                            }
                        }

                        break;
                }
            }

            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Неверные значения");
            alert.setHeaderText("Пожалуйста, введите корректные значения");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
