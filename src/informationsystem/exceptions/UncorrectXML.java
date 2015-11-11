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
public class UncorrectXML extends Exception {

    /**
     * Creates a new instance of <code>UncorrectXML</code> without detail
     * message.
     */
    public UncorrectXML() {
    }

    /**
     * Constructs an instance of <code>UncorrectXML</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public UncorrectXML(String msg) {
        super(msg);
    }
}
