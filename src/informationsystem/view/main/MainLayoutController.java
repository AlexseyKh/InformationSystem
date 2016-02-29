/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem.view.main;

import informationsystem.controller.ControllerToServer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;


public class MainLayoutController {

    private Main main;
    private ControllerToServer con;

    public void setMain(Main mainApp) {
        this.main = main;
    }
    
    public void setController(ControllerToServer con){
        this.con = con;
    }

    @FXML
    private void handleUpdate() {
        main.clearDepartmentTable();
        main.clearEmployeeTable();
        main.getViewDepartmentsFromServer();
    }

    @FXML
    private void handleClose() {
        con.closeConnection();
        System.exit(0);
    }
}
