/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem.model.dataClasses;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Игорь
 */
public class Department implements java.io.Serializable {

    @XmlElement
    private long id;
    private String name;
    private long directorId;
    @XmlElement
    private LinkedHashSet<Employee> employees;
    
    public Department() {
        this.employees = new LinkedHashSet<>();
    }

    public Department(long id, String name) {
        this.id = id;
        this.name = name;
        this.directorId = -1;
        this.employees = new LinkedHashSet<>();
    }

    public int getEmployeeCount(){
        return employees.size();
    }

    @Override
    public String toString() {
        return id + " " + name + " " + directorId;
    }
    
    
    
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }
    
    /**
     * @return the employees
     */
    public LinkedHashSet<Employee> getEmployees() {
        return employees;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the directorId
     */
    public long getDirectorId() {
        return directorId;
    }

    /**
     * @param directorId the directorId to set
     */
    public void setDirectorId(long directorId) {
        this.directorId = directorId;
    }

    @Override
    public int hashCode(){
        int hash = 37;
        hash += 13*getName().hashCode();
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Department other = (Department) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
}
