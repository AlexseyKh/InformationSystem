/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem.model.dataClasses;

import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Игорь
 */
public class Department {

    @XmlElement
    private String name;
    @XmlElement
    private int directorID;

    public Department() {
    }

    public Department(String name, int directorID) {
        this.name = name;
        this.directorID = directorID;
    }

    @Override
    public String toString() {
        return "Department{" + "name=" + name + ", directorID=" + directorID + '}';
    }
  
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the directorID
     */
    public int getDirectorID() {
        return directorID;
    }
}
