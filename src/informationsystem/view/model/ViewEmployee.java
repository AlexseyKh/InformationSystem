/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem.view.model;

import java.util.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Игорь
 */
public class ViewEmployee {

    private LongProperty id;
    private StringProperty firstName;
    private StringProperty lastName;
    private StringProperty function;
    private IntegerProperty salary;
    private StringProperty department;
    
    public ViewEmployee(){
        this.id = new SimpleLongProperty(-1);
        this.firstName = new SimpleStringProperty("");
        this.lastName = new SimpleStringProperty("");
        this.function = new SimpleStringProperty("");
        this.salary = new SimpleIntegerProperty(0);
        this.department = new SimpleStringProperty("");
    }

    public ViewEmployee(long id, String firstName, String lastName, String function, int salary, String department) {
        this.id = new SimpleLongProperty(id);
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.function = new SimpleStringProperty(function);
        this.salary = new SimpleIntegerProperty(salary);
        this.department = new SimpleStringProperty(department);
    }

    @Override
    public String toString(){
        return lastName.get() + " " + firstName.get();
    }
    /**
     * @return the id
     */
    public long getId() {
        return id.get();
    }

    public void setId(long id) {
        this.id.set(id);
    }

    public LongProperty idProperty() {
        return id;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName.get();
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName.set(firstName);
    }

    public StringProperty firstNameProperty() {
        return firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName.get();
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName.set(lastName);
    }

    public StringProperty lastNameProperty() {
        return lastName;
    }

    /**
     * @return the function
     */
    public String getFunction() {
        return function.get();
    }

    /**
     * @param function the function to set
     */
    public void setFunction(String function) {
        this.function.set(function);
    }

    public StringProperty functionProperty() {
        return function;
    }

    /**
     * @return the salary
     */
    public int getSalary() {
        return salary.get();
    }

    /**
     * @param salary the salary to set
     */
    public void setSalary(int salary) {
        this.salary.set(salary);
    }

    public IntegerProperty salaryProperty() {
        return salary;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department.get();
    }

    /**
     * @param department the function to set
     */
    public void setDepartment(String department) {
        this.department.set(department);
    }

    public StringProperty departmentProperty() {
        return department;
    }

}
