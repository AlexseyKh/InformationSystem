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
    private String function;
    private String department;
    private int salary;

    public Employee() {
    }

    public Employee(int id, String firstName, String secondName, String function, String department, int salary) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.department = department;
        this.function = function;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", firstName=" + firstName + ", secondName=" + secondName + ", function=" + function + ", department=" + department + ", salary=" + salary + '}';
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
    public String getDepartment() {
        return department;
    }

    /**
     * @return the function
     */
    public String getFunction() {
        return function;
    }

    /**
     * @return the salary
     */
    public int getSalary() {
        return salary;
    }

}
