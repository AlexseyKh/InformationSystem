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
    public void loadData(String fileName);
    public void saveData(String fileName);
    public String getCompanyName();
    public void setCompanyName(String name);
    public int departmentCount();
    public Department getDepartment(long id);
    public Department getDepartment(String name);
    public Department[] getAllDepartments();
    public boolean addDepartment(String name);
    public boolean deleteDepartment(long id);
    public boolean deleteDepartment(String name);
    public int editDepartment(long departmentId, String newName, long newDirectorId);
    public int employeeCount();
    public Employee getEmployee(long id);
    public Employee[] getAllEmployees();
    public Employee[] getEmployeesOfDepartment(long id);
    public Employee[] getEmployeesOfDepartment(String name);
    public int addEmployee(String departmentName, String firstName, String lastName,
            String function, int salary);
    public boolean deleteEmployee(long id);
    public int editEmployee(long employeeId, String newDepartmentName, String newFirstName, String newLastName,
            String newFunction, int newSalary);
}
