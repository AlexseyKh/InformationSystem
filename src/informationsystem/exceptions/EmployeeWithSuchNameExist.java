/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem.exceptions;

/**
 *
 * @author Игорь
 */
public class EmployeeWithSuchNameExist extends Exception {

    public EmployeeWithSuchNameExist() {
    }


    public EmployeeWithSuchNameExist(String msg) {
        super(msg);
    }
}
