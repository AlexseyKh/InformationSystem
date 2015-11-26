package informationsystem.controller;


import informationsystem.exceptions.DepartmentWithSuchNameDoesNotExist;
import informationsystem.exceptions.DepartmentWithSuchNameExist;
import informationsystem.exceptions.EmployeeWithSuchIdDoesNotExist;
import informationsystem.exceptions.UncorrectXML;

import informationsystem.exceptions.*;

import informationsystem.model.dataClasses.*;
import informationsystem.model.dataClasses.Company;
import java.util.ArrayList;

/**
 *
 * @author ������
 */


public class Controller {
    public Company c;
    public Controller() {
    }

    public void createCompany(String filename) throws UncorrectXML {
        c = new Company(filename);
    }
    public void createCompany(ArrayList<Department> departments) {
        c = new Company(departments);
    }
    public void saveDataXML(String fileName) {
        c.saveDataXML(fileName);
    }
    public int generateId() {
        return c.generateId();
    }
    public int departmentCount() {
        return c.departmentCount();
    }
    public Department getDepartment(int index) {
        return c.getDepartment(index);
    }
    public Department getDepartment(String name) throws DepartmentWithSuchNameDoesNotExist {
        return c.getDepartment(name);
    }
    public void addDepartment(String name, int directorID) throws DepartmentWithSuchNameExist, EmployeeWithSuchIdDoesNotExist {
        c.addDepartment(name, directorID);
    }
    public void deleteDepartment(int index) {
        c.deleteDepartment(index);
    }
    public void deleteDepartment(String name) throws DepartmentWithSuchNameDoesNotExist {
        c.deleteDepartment(name);
    }

    public int employeeCount() {
        return c.employeeCount();
    }
    public Employee getEmployee(int index) {
        return c.getEmployee(index);
    }

    public void addEmployee(int department, int id, String firstName, String secondName,
                            String function, int salary) throws UncorrectId, EmployeeWithSuchIdExist {
        c.getDepartment(department).addEmployee(id, firstName, secondName, function, salary);
    }
    public void delEmployee(int department, int id) {
        c.getDepartment(department).deleteEmployee(id);
    }

}
