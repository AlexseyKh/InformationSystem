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
public class EmployeeWithSuchIdDoesNotExist extends Exception {

    /**
     * Creates a new instance of <code>EmployeeWithSuchIdDoesNotExist</code>
     * without detail message.
     */
    public EmployeeWithSuchIdDoesNotExist() {
    }

    /**
     * Constructs an instance of <code>EmployeeWithSuchIdDoesNotExist</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public EmployeeWithSuchIdDoesNotExist(String msg) {
        super(msg);
    }
}
