/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem.view.main;

import informationsystem.controller.Controller;
import informationsystem.view.employeeTable.EmployeeTableController;
import informationsystem.view.departmentTable.DepartmentTableController;
import informationsystem.controller.ControllerToServer;
import informationsystem.model.dataClasses.Department;
import informationsystem.model.dataClasses.Employee;
import informationsystem.view.departmentTable.DepartmentEditDialogController;
import informationsystem.view.employeeTable.EmployeeEditDialogController;
import informationsystem.view.model.ViewDepartment;
import informationsystem.view.model.ViewEmployee;
import java.io.IOException;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Игорь
 */
public class Main extends Application {

    private final int PORT = 7777;
    private Stage mainStage;
    private BorderPane mainLayout;
    private ControllerToServer con = new ControllerToServer(PORT);

    private ObservableList<ViewDepartment> departments = FXCollections.observableArrayList();
    private ObservableList<ViewEmployee> employees = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        this.mainStage = primaryStage;
        this.mainStage.setTitle(con.getCompanyName());
        this.mainStage.setMinWidth(750);
        this.mainStage.setMinHeight(465);
        mainStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent we) {
                con.closeConnection();
            }
        });
        initMainLayout();
        showTables();

    }

    public void initMainLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("MainLayout.fxml"));
            mainLayout = (BorderPane) loader.load();
            Scene scene = new Scene(mainLayout);
            mainStage.setScene(scene);
            mainStage.show();
            MainLayoutController controller = loader.getController();
            controller.setMain(this);
            controller.setController(con);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showTables() {
        try {
            FXMLLoader loader1 = new FXMLLoader();
            loader1.setLocation(Main.class.getResource("/informationsystem/view/departmentTable/DepartmentTable.fxml"));
            AnchorPane depTable = (AnchorPane) loader1.load();
            FXMLLoader loader2 = new FXMLLoader();
            loader2.setLocation(Main.class.getResource("/informationsystem/view/employeeTable/EmployeeTable.fxml"));
            AnchorPane empTable = (AnchorPane) loader2.load();

            SplitPane sp = new SplitPane(depTable, empTable);
            mainLayout.setCenter(sp);

            DepartmentTableController controller1 = loader1.getController();
            EmployeeTableController controller2 = loader2.getController();
            controller1.setMain(this);
            controller1.setController(con);
            controller1.setEmpTableCon(controller2);
            controller2.setMain(this);
            controller2.setController(con);
            controller2.setDepTableCon(controller1);

            getViewDepartmentsFromServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<ViewDepartment> getDepartments() {
        return departments;
    }

    public ObservableList<ViewEmployee> getEmployees() {
        return employees;
    }

    public Stage getPrimaryStage() {
        return mainStage;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public boolean showDepartmentEditDialog(ViewDepartment dep, boolean isNew) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/informationsystem/view/departmentTable//DepartmentEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            if (isNew) {
                dialogStage.setTitle("Добавление отдела");
            } else {
                dialogStage.setTitle("Редактирование отдела");
            }
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            DepartmentEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setMain(this);
            controller.setPurpose(isNew);
            controller.setController(con);
            controller.setDepartment(dep);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean showEmployeeEditDialog(ViewEmployee emp, boolean isNew) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/informationsystem/view/employeeTable//EmployeeEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            if (isNew) {
                dialogStage.setTitle("Добавление сотрудника");
            } else {
                dialogStage.setTitle("Редактирование сотрудника");
            }
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(mainStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            EmployeeEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setController(con);
            controller.setMain(this);
            controller.setPurpose(isNew);
            controller.setEmployee(emp);

            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void getViewDepartmentsFromServer() {
        Department[] deps = con.getAllDepartments();
        for (Department dep : deps) {
            Employee emp = con.getEmployee(dep.getDirectorId());
            if (emp != null) {
                departments.add(new ViewDepartment(dep.getId(), dep.getName(),
                        dep.getDirectorId(), emp.getLastName() + " " + emp.getFirstName()));
            } else {
                departments.add(new ViewDepartment(dep.getId(), dep.getName(),
                        dep.getDirectorId(), "-"));
            }
        }
    }

    public void getViewEmployeesFromServer(String departmentName) {
        Employee[] emps = con.getEmployeesOfDepartment(departmentName);
        for (Employee emp : emps) {
            boolean exist = false;
            for (ViewEmployee vEmp : getEmployees()) {
                if (vEmp.getId() == emp.getId()) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                employees.add(new ViewEmployee(emp.getId(), emp.getFirstName(),
                        emp.getLastName(), emp.getFunction(), emp.getSalary(), departmentName));
            }

        }
    }

    public void clearDepartmentTable() {
        departments = FXCollections.observableArrayList();
    }

    public void clearEmployeeTable() {
        employees = FXCollections.observableArrayList();
    }
}
