/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem.controller;

import informationsystem.model.dataClasses.Department;
import informationsystem.model.dataClasses.Employee;

/**
 *
 * @author Игорь
 */
public interface Controller {
    public void createCompany(String name);
    public void readData(String fileName);
    public void saveData(String fileName);
    public String getCompanyName();
    public void setCompanyName(String name);
    public int departmentCount();
    public Department getDepartment(long id);
    public Department getDepartment(String name);
    public Department[] getAllDepartments();
    public void addDepartment(String name);
    public void deleteDepartment(long id);
    public void deleteDepartment(String name);
    public int employeeCount();
    public Employee getEmployee(long id);
    public Employee[] getAllEmployees();
    public Employee[] getEmployeesOfDepartment(long id);
    public Employee[] getEmployeesOfDepartment(String name);
    public void addEmployee(String departmentName, String firstName, String secondName,
            String function, int salary);
    public void deleteEmployee(long id);
    public void mergeWithDataInXML(String fileName);
}
