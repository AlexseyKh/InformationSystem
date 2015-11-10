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
public class UncorrectId extends Exception {

    /**
     * Creates a new instance of <code>UncorrectId</code> without detail
     * message.
     */
    public UncorrectId() {
    }

    /**
     * Constructs an instance of <code>UncorrectId</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public UncorrectId(String msg) {
        super(msg);
    }
}
