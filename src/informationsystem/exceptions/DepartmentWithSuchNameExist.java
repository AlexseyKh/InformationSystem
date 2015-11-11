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
public class DepartmentWithSuchNameExist extends Exception {

    /**
     * Creates a new instance of <code>DepartmentWithSuchNameExist</code>
     * without detail message.
     */
    public DepartmentWithSuchNameExist() {
    }

    /**
     * Constructs an instance of <code>DepartmentWithSuchNameExist</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public DepartmentWithSuchNameExist(String msg) {
        super(msg);
    }
}
