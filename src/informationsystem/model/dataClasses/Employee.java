/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem.model.dataClasses;

import javax.xml.bind.annotation.XmlElement;
import informationsystem.exceptions.*;

/**
 *
 * @author Игорь
 */
public class Employee {

    @XmlElement
    private int identificator;

    private String firstName;

    private String secondName;

    private String function;

    private int salary;

    Department parentDepartment;

    public Employee() {
    }

    public Employee(int id, String firstName, String secondName, String function, int salary, Department parentDepartment) {
        this.identificator = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.function = function;
        this.salary = salary;
        this.parentDepartment = parentDepartment;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + getId() + ", firstName=" + getFirstName() + ", secondName=" + getSecondName() + ", function=" + getFunction() + ", salary=" + getSalary() + '}';
    }

    public int getId() {
        return identificator;
    }

    public void setId(int id)
            throws EmployeeWithSuchIdExist, UncorrectId {
        if (id <= 0) {
            throw new UncorrectId();
        }
        if (this.identificator != id && parentDepartment.getParentCompany().suchEmployeeExist(id)) {
            throw new EmployeeWithSuchIdExist();
        }
        this.identificator = id;

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
     * @return the secondName
     */
    public String getSecondName() {
        return secondName;
    }

    /**
     * @param secondName the secondName to set
     */
    public void setSecondName(String secondName) {
        this.secondName = secondName;
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

}
