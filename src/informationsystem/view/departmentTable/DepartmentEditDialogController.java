/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem.view.departmentTable;

import informationsystem.controller.Controller;
import informationsystem.controller.ControllerToServer;
import informationsystem.model.dataClasses.Department;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Игорь
 */
public class DepartmentEditDialogController {

    @FXML
    private TextField nameField;
    @FXML
    private ChoiceBox<ViewEmployee> directorNameChoiceBox;

    private Stage dialogStage;
    private ViewDepartment department;
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

    public void setDepartment(ViewDepartment department) {
        this.department = department;
        nameField.setText(department.getName());
        ObservableList<ViewEmployee> employees = FXCollections.observableArrayList();

        for (ViewDepartment dep : main.getDepartments()) {
            Employee[] emps = con.getEmployeesOfDepartment(dep.getName());
            if (emps != null) {
                for (Employee emp : emps) {
                    employees.add(new ViewEmployee(emp.getId(), emp.getFirstName(),
                            emp.getLastName(), emp.getFunction(), emp.getSalary(), dep.getName()));
                }
            }
        }
        ViewEmployee vEmp = new ViewEmployee();
        vEmp.setLastName("-");
        vEmp.setFirstName("");

        employees.add(vEmp);

        directorNameChoiceBox.setItems(employees);

        if (department.getDirectorId() != -1) {
            for (ViewEmployee viewEmp : employees) {
                if (viewEmp.toString().equals(department.getDirectorName())) {
                    directorNameChoiceBox.getSelectionModel().select(viewEmp);
                    break;
                }
            }

        } else {
            directorNameChoiceBox.getSelectionModel().select(0);
        }

    }

    public void setController(ControllerToServer con) {
        this.con = con;
    }

    public void setPurpose(boolean isNew) {
        this.isNew = isNew;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public boolean isOkClicked() {
        return okClicked;
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

        if (nameField.getText() == null || nameField.getText().length() == 0) {
            errorMessage += "Неверное имя!\n";
        }
        if (errorMessage.length() == 0) {
            if (isNew) {
                if (!con.addDepartment(nameField.getText())) {
                    errorMessage += "Отдел с таким именем уже существует!\n";
                } else {

                    department.setName(nameField.getText());
                    department.setId(con.getDepartment(nameField.getText()).getId());

                    department.setDirectorName(directorNameChoiceBox.getSelectionModel().getSelectedItem().toString());
                    department.setDirectorId(directorNameChoiceBox.getSelectionModel().getSelectedItem().getId());
                    if (department.getDirectorId() != -1) {
                        con.editDepartment(department.getId(), department.getName(), department.getDirectorId());
                    }
                }
            } else {
                switch (con.editDepartment(department.getId(), nameField.getText(),
                        directorNameChoiceBox.getSelectionModel().getSelectedItem().getId())) {
                    case -1:
                        errorMessage += "Отдел с таким именем уже существует!\n";
                        break;
                    case 0:
                        errorMessage += "Не существует такого сотрудника!\n";
                        break;
                    case 1:
                        String prevName = department.getName();
                        department.setName(nameField.getText());

                        department.setDirectorName(directorNameChoiceBox.getSelectionModel().getSelectedItem().toString());
                        department.setDirectorId(directorNameChoiceBox.getSelectionModel().getSelectedItem().getId());

                        for (ViewEmployee vEmp : main.getEmployees()) {
                            if (vEmp.getDepartment().equals(prevName)) {
                                vEmp.setDepartment(department.getName());
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
