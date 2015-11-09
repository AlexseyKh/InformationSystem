/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem.model.dataClasses;

import informationsystem.model.dataClasses.*;
import java.io.*;
import java.util.ArrayList;
import javax.xml.bind.*;
import javax.xml.bind.annotation.*;

/**
 *
 * @author Игорь
 */
@XmlRootElement
public class Company {

    @XmlElement
    private ArrayList<Department> departments;
    @XmlElement
    private ArrayList<Employee> employees;

    public Company(ArrayList<Department> departments, ArrayList<Employee> Employees) {
        this.departments = departments;
        this.employees = Employees;
    }

    public Company() {
    }

    public Company(String fileName) {
        InputStream is = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(Company.class);
            Unmarshaller um = jc.createUnmarshaller();
            is = new FileInputStream(fileName);
            Company m = (Company) um.unmarshal(is);
            this.departments = m.departments;
            this.employees = m.employees;
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();

            } catch (IOException e) {

            } finally {

            }
        }
    }

    public void saveDataXML(String fileName) {
        OutputStream os = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(Company.class);
            Marshaller m = jc.createMarshaller();
            os = new FileOutputStream(fileName);
            m.marshal(this, os);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                os.close();

            } catch (IOException e) {

            } finally {

            }
        }

    }

    public int generateId() {
        int newId = 1;
        if (employees != null) {
            int[] arr = new int[employees.size()];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = employees.get(i).getId();
            }
            
            int basket;
            for (int i = 1; i < arr.length; i++) {
                for (int j = i - 1; j >= 0; j--) {
                    if (arr[j + 1] < arr[j]) {
                        basket = arr[j + 1];
                        arr[j + 1] = arr[j];
                        arr[j] = basket;
                    } else {
                        break;
                    }
                }
            }
            
            for(int i = 0; i < arr.length; i++){
                if(arr[i] != i + 1){
                    newId = i + 1;
                    break;
                }               
            }
        }
        return newId;
    }

    //
    //Методы работы с отделом.
    //
    //Название отдела - ключ.
    //Если id == 0, то отдел без начальника.
    //
    public int departmentCount() {
        return departments.size();
    }

    public Department getDepartment(int index) {
        return departments.get(index);
    }

    public Department getDepartment(String name) {
        for (int i = 0; i < departments.size(); i++) {
            if (departments.get(i).getName().equalsIgnoreCase(name)) {
                return departments.get(i);
            }
        }
        return null;
    }

    public boolean addDepartment(String name, int directorID) {
        if (!suchDepartmentExist(name) && (directorID == 0 || suchEmployeeExist(directorID))) {
            departments.add(new Department(name, directorID));
            return true;
        }
        return false;
    }

    public boolean deleteDepartment(int index) {
        if (index >= 0 && index < departmentCount()) {
            departments.remove(index);
            return true;
        }
        return false;
    }

    public boolean deleteDepartment(String name) {
        for (int i = 0; i < departments.size(); i++) {
            if (departments.get(i).getName().equalsIgnoreCase(name)) {
                departments.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean updateDepartment(int index, String newName, int newDirectorID) {
        if ((index >= 0 && index < departmentCount())
                && (newName.equalsIgnoreCase(getDepartment(index).getName()) || !suchDepartmentExist(newName))
                && (suchEmployeeExist(newDirectorID) || newDirectorID == 0)) {
            departments.set(index, new Department(newName, newDirectorID));
            return true;
        }
        return false;
    }

    public boolean updateDepartment(String name, String newName, int newDirectorID) {
        if ((newName.equalsIgnoreCase(name) || !suchDepartmentExist(newName))
                && (newDirectorID == 0) || suchEmployeeExist(newDirectorID)) {
            for (int i = 0; i < departments.size(); i++) {
                if (departments.get(i).getName() == name) {
                    departments.set(i, new Department(newName, newDirectorID));
                    return true;
                }
            }
        }
        return false;
    }

    //
    //Методы работы с сотрудником.
    //
    //Поле department не может быть пустым.
    //ID сотрудника - ключ.
    //ID может быть только положительным.
    //  
    public int employeeCount() {
        return employees.size();
    }

    public int employeeCount(int departmentIndex) {
        int count = 0;
        if (departmentIndex >= 0 && departmentIndex < departmentCount()) {

            for (int i = 0; i < employees.size(); i++) {
                if (employees.get(i).getDepartment().equalsIgnoreCase(getDepartment(departmentIndex).getName())) {
                    count++;
                }
            }
        }
        return count;
    }

    public int employeeCount(String departmentName) {
        int count = 0;
        if (suchDepartmentExist(departmentName)) {

            for (int i = 0; i < employees.size(); i++) {
                if (employees.get(i).getDepartment().equalsIgnoreCase(departmentName)) {
                    count++;
                }
            }
        }
        return count;
    }

    public Employee getEmployee(int id) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == id) {
                return employees.get(i);
            }
        }
        return null;
    }

    public Employee getEmployee(int departmentIndex, int employeeIndex) {
        if (departmentIndex >= 0 && departmentIndex < departmentCount()) {
            int i = -1;
            for (Employee emp : employees) {
                if (getDepartment(departmentIndex).getName().equalsIgnoreCase(emp.getDepartment())) {
                    i++;
                    if (i == employeeIndex) {
                        return emp;
                    }
                }
            }
        }
        return null;
    }

    public Employee getEmployee(String departmentName, int employeeIndex) {
        if (suchDepartmentExist(departmentName)) {
            int i = -1;
            for (Employee emp : employees) {
                if (departmentName.equalsIgnoreCase(emp.getDepartment())) {
                    i++;
                    if (i == employeeIndex) {
                        return emp;
                    }
                }
            }
        }
        return null;
    }

    public boolean addEmployee(int id, String firstName, String secondName,
            String function, String department, int salary) {
        if (id > 0 && !suchEmployeeExist(id) && suchDepartmentExist(department)) {
            employees.add(new Employee(id, firstName, secondName, function, department, salary));
            return true;
        }
        return false;
    }

    public boolean deleteEmployee(int id) {
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == id) {
                employees.remove(i);
                for (int j = 0; j < departments.size(); j++) {
                    if (departments.get(j).getDirectorID() == id) {
                        updateDepartment(departments.get(j).getName(), departments.get(j).getName(), 0);
                    }
                }
                return true;
            }
        }
        return false;
    }

    public boolean deleteEmployee(int departmentIndex, int employeeIndex) {
        if (departmentIndex >= 0 && departmentIndex < departmentCount()) {
            int i = -1;
            int j = -1;
            for (Employee emp : employees) {
                j++;
                if (getDepartment(departmentIndex).getName().equalsIgnoreCase(emp.getDepartment())) {
                    i++;
                    if (i == employeeIndex) {
                        employees.remove(j);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean deleteEmployee(String departmentName, int employeeIndex) {
        if (suchDepartmentExist(departmentName)) {
            int i = -1;
            int j = -1;
            for (Employee emp : employees) {
                j++;
                if (departmentName.equalsIgnoreCase(emp.getDepartment())) {
                    i++;
                    if (i == employeeIndex) {
                        employees.remove(j);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean updateEmployee(int id, int newId, String newFirstName, String newSecondName,
            String newFunction, String newDepartment, int newSalary) {
        if (newId == id || newId > 0 && !suchEmployeeExist(newId)
                && suchDepartmentExist(newDepartment)) {
            for (int i = 0; i < employees.size(); i++) {
                if (employees.get(i).getId() == id) {
                    employees.set(i, new Employee(newId, newFirstName, newSecondName,
                            newFunction, newDepartment, newSalary));
                    return true;
                }
            }
        }
        return false;
    }

    public boolean updateEmployee(int departmentIndex, int employeeIndex,
            int newId, String newFirstName, String newSecondName,
            String newFunction, String newDepartment, int newSalary) {
        if (newId > 0 && !suchEmployeeExist(newId) && suchDepartmentExist(newDepartment)) {
            int i = -1;
            int j = -1;
            for (Employee emp : employees) {
                j++;
                if (getDepartment(departmentIndex).getName().equalsIgnoreCase(emp.getDepartment())) {
                    i++;
                    if (i == employeeIndex) {
                        employees.set(j, new Employee(newId, newFirstName, newSecondName,
                                newFunction, newDepartment, newSalary));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean updateEmployee(String departmentName, int employeeIndex,
            int newId, String newFirstName, String newSecondName,
            String newFunction, String newDepartment, int newSalary) {
        if (newId > 0 && !suchEmployeeExist(newId) && suchDepartmentExist(newDepartment)) {
            int i = -1;
            int j = -1;
            for (Employee emp : employees) {
                j++;
                if (departmentName.equalsIgnoreCase(emp.getDepartment())) {
                    i++;
                    if (i == employeeIndex) {
                        employees.set(j, new Employee(newId, newFirstName, newSecondName,
                                newFunction, newDepartment, newSalary));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //
    //приватные вспомогательные методы
    //
    private boolean suchDepartmentExist(String name) {
        for (Department department : departments) {
            if (department.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    private boolean suchEmployeeExist(int id) {
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                return true;
            }
        }
        return false;
    }

}
