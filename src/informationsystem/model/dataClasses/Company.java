/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem.model.dataClasses;

import informationsystem.model.dataClasses.*;
import java.io.*;
import java.util.ArrayList;
import informationsystem.exceptions.*;
import java.util.Iterator;
import java.util.LinkedHashSet;
import javax.xml.bind.*;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Игорь
 */
    @XmlRootElement
    public class Company implements Serializable {
        
    private LinkedHashSet<Department> departments;
    private String name;
    private long idSequenceForDepartments;
    private long idSequenceForEmployees;
    
    public Company() {
        departments = new LinkedHashSet<>();
    }  
    public Company(String name){
        this.name = name;
        departments = new LinkedHashSet<>();
        idSequenceForDepartments = 0;
        idSequenceForEmployees = 0;
    };
    
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
     * @return the departments
     */
    public LinkedHashSet<Department> getDepartments() {
        return departments;
    }


    /**
     * @param departments the departments to set
     */
    public void setDepartments(LinkedHashSet<Department> departments) {
        this.departments = departments;
    }

    /**
     * @return the idSequenceForDepartments
     */
    public long getIdSequenceForDepartments() {
        return idSequenceForDepartments;
    }

    /**
     * @param idSequenceForDepartments the idSequenceForDepartments to set
     */
    public void setIdSequenceForDepartments(long idSequenceForDepartments) {
        this.idSequenceForDepartments = idSequenceForDepartments;
    }

    /**
     * @return the idSequenceForEmployees
     */
    public long getIdSequenceForEmployees() {
        return idSequenceForEmployees;
    }

    /**
     * @param idSequenceForEmployees the idSequenceForEmployees to set
     */
    public void setIdSequenceForEmployees(long idSequenceForEmployees) {
        this.idSequenceForEmployees = idSequenceForEmployees;
    }
}
