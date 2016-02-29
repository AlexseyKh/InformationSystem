/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem.view.departmentTable;

import informationsystem.controller.Controller;
import informationsystem.controller.ControllerToServer;
import informationsystem.model.dataClasses.Department;
import informationsystem.view.employeeTable.EmployeeTableController;
import informationsystem.view.main.Main;
import informationsystem.view.model.ViewDepartment;
import informationsystem.view.model.ViewEmployee;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Игорь
 */
public class DepartmentTableController {

    @FXML
    private TableView<ViewDepartment> departmentTable;
    @FXML
    private TableColumn<ViewDepartment, String> nameColumn;
    @FXML
    private TableColumn<ViewDepartment, String> directorColumn;

    // Reference to the main application.
    private Main main;
    private EmployeeTableController empTableCon;
    private ControllerToServer con;

    public DepartmentTableController() {
    }

    @FXML
    private void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        directorColumn.setCellValueFactory(cellData -> cellData.getValue().directorNameProperty());

        departmentTable.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    main.getViewEmployeesFromServer(departmentTable.getSelectionModel().getSelectedItem().getName());
                }
            }
        });
        
        
    }

    public void setMain(Main main) {
        this.main = main;
        departmentTable.setItems(main.getDepartments());
    }

    public void setEmpTableCon(EmployeeTableController empTableCon) {
        this.empTableCon = empTableCon;
    }

    public void setController(ControllerToServer con) {
        this.con = con;
    }

    @FXML
    private void handleDeleteDepartment() {
        int selectedIndex = departmentTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            for (int i = main.getEmployees().size() - 1; i >= 0; i--) {
                ViewEmployee vEmp = main.getEmployees().get(i);
                if (vEmp.getDepartment().equals(departmentTable.getSelectionModel().getSelectedItem().getName())) {
                    for (ViewDepartment vDep : main.getDepartments()) {
                        if (vDep.getDirectorName().equals(vEmp.getLastName() + " " + vEmp.getFirstName())) {
                            vDep.setDirectorName("-");
                        }
                    }
                    main.getEmployees().remove(vEmp);
                }
            }
            con.deleteDepartment(departmentTable.getSelectionModel().getSelectedItem().getId());
            departmentTable.getItems().remove(selectedIndex);
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Не выбран отдел");
            alert.setContentText("Пожалуйста, выберите отдел");

            alert.showAndWait();
        }
    }

    @FXML
    private void handleNewDepartment() {

        ViewDepartment tempDepartment = new ViewDepartment();
        boolean okClicked = main.showDepartmentEditDialog(tempDepartment, true);
        if (okClicked) {
            main.getDepartments().add(tempDepartment);
        }

    }

    @FXML
    private void handleEditDepartment() {

        ViewDepartment selectedDepartment = departmentTable.getSelectionModel().getSelectedItem();
        if (selectedDepartment != null) {
            boolean okClicked = main.showDepartmentEditDialog(selectedDepartment, false);
            if (okClicked) {

            }

        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(main.getPrimaryStage());
            alert.setTitle("Ничего не выбрано");
            alert.setHeaderText("Ни один отдел не выбран");
            alert.setContentText("Пожалуйста, выберите отдел в таблице");

            alert.showAndWait();
        }
    }

}
