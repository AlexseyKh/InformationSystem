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
public class DepartmentWithSuchNameDoesNotExist extends Exception {

    /**
     * Creates a new instance of <code>DepartmentWithSuchNameDoesNotExist</code>
     * without detail message.
     */
    public DepartmentWithSuchNameDoesNotExist() {
    }

    /**
     * Constructs an instance of <code>DepartmentWithSuchNameDoesNotExist</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public DepartmentWithSuchNameDoesNotExist(String msg) {
        super(msg);
    }
}
