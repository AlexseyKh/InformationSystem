/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem.model.dataClasses;

import java.util.Objects;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author Игорь
 */
public class Employee {

    @XmlElement
    private long id;
    private String firstName;
    private String lastName;
    private String function;
    private int salary;

    public Employee() {
        
    }

    public Employee(long id, String firstName, String secondName, String function, int salary) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = secondName;
        this.function = function;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return id + " " + firstName + " " + lastName + " " + function + " " + salary; 
    }

    
    
    
    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the function
     */
    public String getFunction() {
        return function;
    }

    /**
     * @param function the function to set
     */
    public void setFunction(String function) {
        this.function = function;
    }

    /**
     * @return the salary
     */
    public int getSalary() {
        return salary;
    }

    /**
     * @param salary the salary to set
     */
    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public int hashCode(){
        int hash = 37;
        hash += 11*getFirstName().hashCode();
        hash += 11*getLastName().hashCode();
        hash += 11*getSalary();
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
        final Employee other = (Employee) obj;
        if (!Objects.equals(this.firstName, other.firstName)) {
            return false;
        }
        if (!Objects.equals(this.lastName, other.lastName)) {
            return false;
        }
        return true;
    }


}
