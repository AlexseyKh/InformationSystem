/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem.model.dataClasses;

/**
 *
 * @author Игорь
 */
public class Employee {

    private int id;
    private String firstName;
    private String secondName;
    private Department department;
    private String number;
    private int salary;

    public Employee() {
    }

    public Employee(int id, String firstName, String secondName, Department department, String number, int salary) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.department = department;
        this.number = number;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + getId() + ", firstName=" + getFirstName() + ", secondName=" + getSecondName() + ", department=" + getDepartment() + ", number=" + getNumber() + ", salary=" + getSalary() + '}';
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @return the secondName
     */
    public String getSecondName() {
        return secondName;
    }

    /**
     * @return the department
     */
    public Department getDepartment() {
        return department;
    }

    /**
     * @return the number
     */
    public String getNumber() {
        return number;
    }

    /**
     * @return the salary
     */
    public int getSalary() {
        return salary;
    }

    

 

}
