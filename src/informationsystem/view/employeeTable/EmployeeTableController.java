/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem.view.employeeTable;

import informationsystem.controller.Controller;
import informationsystem.controller.ControllerToServer;
import informationsystem.view.main.Main;
import informationsystem.view.departmentTable.DepartmentTableController;
import informationsystem.view.model.ViewDepartment;
import informationsystem.view.model.ViewEmployee;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author Игорь
 */
public class EmployeeTableController {

    @FXML
    private TableView<ViewEmployee> employeeTable;
    @FXML
    private TableColumn<ViewEmployee, String> departmentColumn;
    @FXML
    private TableColumn<ViewEmployee, String> firstNameColumn;
    @FXML
    private TableColumn<ViewEmployee, String> lastNameColumn;
    @FXML
    private TableColumn<ViewEmployee, String> functionColumn;
    @FXML
    private TableColumn<ViewEmployee, Integer> salaryColumn;

    private Main main;
    private DepartmentTableController depTableCon;
    private ControllerToServer con;

    public EmployeeTableController() {

    }

    @FXML
    private void initialize() {
        departmentColumn.setCellValueFactory(cellData -> cellData.getValue().departmentProperty());
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());
        functionColumn.setCellValueFactory(cellData -> cellData.getValue().functionProperty());
        salaryColumn.setCellValueFactory(cellData -> cellData.getValue().salaryProperty().asObject());
    }

    public void setMain(Main main) {
        this.main = main;
        employeeTable.setItems(main.getEmployees());
    }

    public void setDepTableCon(DepartmentTableController depTableCon) {
        this.depTableCon = depTableCon;
    }

    public void setController(ControllerToServer con) {
        this.con = con;
    }

    @FXML
    private void handleDeleteEmployee() {

        int selectedIndex = employeeTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            for (ViewDepartment vDep : main.getDepartments()) {
                if (vDep.getDirectorName().equals(employeeTable.getSelectionModel().getSelectedItem().getLastName() + " "
                        + employeeTable.getSelectionModel().getSelectedItem().getFirstName())) {
                    vDep.setDirectorName("-");
                }

            }
            con.deleteEmployee(employeeTable.getSelectionModel().getSelectedItem().getId());
            employeeTable.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Не выбран сотудник");
            alert.setContentText("Пожалуйста, выберите сотрудника");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewEmployee() {
        if (main.getDepartments().size() == 0) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Нет отделов");
            alert.setHeaderText("Нет существует ни одного отдела");
            alert.setContentText("Сначала создайте отдел");

            alert.showAndWait();
        } else {
            ViewEmployee tempEmployee = new ViewEmployee();
            boolean okClicked = main.showEmployeeEditDialog(tempEmployee, true);
            if (okClicked) {
                main.getEmployees().add(tempEmployee);
            }
        }
    }

    @FXML
    private void handleEditEmployee() {
        if (main.getDepartments().size() == 0) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Нет отделов");
            alert.setHeaderText("Нет существует ни одного отдела");
            alert.setContentText("Сначала создайте отдел");

            alert.showAndWait();
        } else {
            ViewEmployee selectedEmployee = employeeTable.getSelectionModel().getSelectedItem();
            if (selectedEmployee != null) {
                boolean okClicked = main.showEmployeeEditDialog(selectedEmployee, false);
                if (okClicked) {

                }

            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.initOwner(main.getPrimaryStage());
                alert.setTitle("Ничего не выбрано");
                alert.setHeaderText("Не выбран сотудник");
                alert.setContentText("Пожалуйста, выберите сотрудника");

                alert.showAndWait();
            }
        }
    }
    
    @FXML
    private void handleClearEmployee() {
        main.clearEmployeeTable();
    }
}
