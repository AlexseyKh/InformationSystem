/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem.model.dataClasses;

import java.util.ArrayList;
import informationsystem.exceptions.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Игорь
 */
public class Department {

    @XmlElement
    private String departmentName;
    @XmlElement
    private int directorIdentificator;
    @XmlElement
    private ArrayList<Employee> employees;

    Company parentCompany;


    
    public Department() {
    }

    public Department(String name, int directorID, Company parentCompany) {
        this.departmentName = name;
        this.directorIdentificator = directorID;
        this.employees = new ArrayList<>();
        this.parentCompany = parentCompany;
    }

    @Override
    public String toString() {
        return "Department{" + "name=" + getName() + ", directorID=" + Department.this.getDirectorID() + '}';
    }

    //
    //Методы работы с сотрудниками.
    //
    //Поле department не может быть пустым.
    //ID сотрудника - ключ.
    //ID может быть только положительным.
    //
    public int employeeCount() {
        return employees.size();
    }

    public Employee getEmployee(int index) {
        return employees.get(index);
    }

    public void addEmployee(int id, String firstName, String secondName,
            String function, int salary) throws EmployeeWithSuchIdExist, UncorrectId {
        if (id <= 0) {
            throw new UncorrectId();
        }
        if (parentCompany.suchEmployeeExist(id)) {
            throw new EmployeeWithSuchIdExist();
        }
        employees.add(new Employee(id, firstName, secondName, function, salary, this));
    }

    public void deleteEmployee(int index) {
        System.out.println(parentCompany);
        for (int i = 0; i < parentCompany.departmentCount(); i++) {
            if (employees.get(index).getId() == parentCompany.getDepartment(i).getDirectorID()) {
                parentCompany.getDepartment(i).directorIdentificator = 0;
            }
        }
        employees.remove(index);
    }

    /**
     * @return the name
     */
    public String getName() {
        return departmentName;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
            throws DepartmentWithSuchNameExist {
        if (!this.departmentName.equalsIgnoreCase(name) && parentCompany.suchDepartmentExist(name)) {
            throw new DepartmentWithSuchNameExist();
        }
        this.departmentName = name;
    }

    @XmlTransient
    public int getDirectorID() {
        return directorIdentificator;
    }

    public void setDirectorID(int directorID)
            throws EmployeeWithSuchIdDoesNotExist {
        if (!parentCompany.suchEmployeeExist(directorID)) {
            throw new EmployeeWithSuchIdDoesNotExist();
        }
        this.directorIdentificator = directorID;
    }

    Company getParentCompany() {
        return parentCompany;
    }

}
