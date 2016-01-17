package informationsystem.controller;

import informationsystem.exceptions.DepartmentWithSuchNameDoesNotExist;
import informationsystem.exceptions.DepartmentWithSuchNameExist;
import informationsystem.exceptions.EmployeeWithSuchNameDoesNotExist;
import informationsystem.exceptions.UncorrectXML;

import informationsystem.exceptions.*;

import informationsystem.model.dataClasses.*;
import informationsystem.model.dataClasses.Company;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedHashSet;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class Controller {

    private Company company;

    public Controller() {
    }

    public void createCompany(String name) {
        company = new Company(name);
    }

    public void createCompanyFromXML(String fileName) {
        company = new Company();
        InputStream is = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(Company.class);
            Unmarshaller um = jc.createUnmarshaller();
            is = new FileInputStream(fileName);
            company = (Company) um.unmarshal(is);
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

    public void saveCompanyToXML(String fileName) {
        OutputStream os = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(Company.class);
            Marshaller m = jc.createMarshaller();
            os = new FileOutputStream(fileName);
            m.marshal(company, os);
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
    
    public String getCompanyName(){
        return company.getName();
    }
    
    public void setCompanyName(String name){
        company.setName(name);
    }

    public int departmentCount() {
        return company.getDepartments().size();
    }

    public Department getDepartment(long id) {
        for (Department dep : company.getDepartments()) {
            if (dep.getId() == id) {
                return dep;
            }
        }
        return null;
    }

    public Department getDepartment(String name) {
        for (Department dep : company.getDepartments()) {
            if (dep.getName() == name) {
                return dep;
            }
        }
        return null;
    }

    public Department[] getAllDepartments() {
        return (Department[]) company.getDepartments().toArray(new Department[company.getDepartments().size()]);
    }

    public void addDepartment(String name) {
        company.getDepartments().add(
                new Department(company.getIdSequenceForDepartments(), name));
        company.setIdSequenceForDepartments(company.getIdSequenceForDepartments() + 1);
    }

    public void deleteDepartment(long id) {
        for (Department dep : company.getDepartments()) {
            if (dep.getId() == id) {
                company.getDepartments().remove(dep);
            }
        }
    }

    public void deleteDepartment(String name) {
        for (Department dep : company.getDepartments()) {
            if (dep.getName() == name) {
                company.getDepartments().remove(dep);
            }
        }
    }
    
   /* public void setDirectorToDepartment(long departmentId, long directorId){
        Employee[] emps = getAllEmployees();
        boolean exist = false;
        for(int i = 0; i < emps.length; i++){
            if(emps[i].getId() == directorId){
                exist = true;
                break;
            }
        }
        for (Department dep : company.getDepartments()) {
            if (dep.getId() == departmentId) {
                dep.setDirectorId(directorId);
                
                break;
            }
        }
    }
*/
    public int employeeCount() {
        int count = 0;
        for (Department dep : company.getDepartments()) {
            for (Employee emp : dep.getEmployees()) {
                count++;
            }
        }
        return count;
    }

    public Employee getEmployee(long id) {
        for (Department dep : company.getDepartments()) {
            for (Employee emp : dep.getEmployees()) {
                if (emp.getId() == id) {
                    return emp;
                }
            }
        }
        return null;
    }

    public Employee[] getAllEmployees() {
        LinkedHashSet<Employee> emps = new LinkedHashSet<>();
        for (Department dep : company.getDepartments()) {
            emps.addAll(dep.getEmployees());
        }
        return (Employee[]) emps.toArray(new Employee[emps.size()]);
    }

    public Employee[] getEmployeesOfDepartment(long id) {
        for (Department dep : company.getDepartments()) {
            if (dep.getId() == id) {
                return (Employee[]) dep.getEmployees().toArray(new Employee[dep.getEmployees().size()]);
            }
        }
        return null;
    }

    public Employee[] getEmployeesOfDepartment(String name) {
        for (Department dep : company.getDepartments()) {
            if (dep.getName() == name) {
                return (Employee[]) dep.getEmployees().toArray(new Employee[dep.getEmployees().size()]);
            }
        }
        return null;
    }

    public void addEmployee(String departmentName, String firstName, String secondName,
            String function, int salary) {
        for (Department dep : company.getDepartments()) {
            if (dep.getName() == departmentName) {
                dep.getEmployees().add(new Employee(company.getIdSequenceForEmployees(),
                        firstName, secondName, function, salary));
                company.setIdSequenceForEmployees(company.getIdSequenceForEmployees() + 1);
                break;
            }
        }

    }

    public void deleteEmployee(long id) {

        for (Department dep : company.getDepartments()) {
            if (dep.getDirectorId() == id) {
                dep.setDirectorId(-1);
                break;
            }
        }

        for (Department dep : company.getDepartments()) {
            for (Employee emp : dep.getEmployees()) {
                if (emp.getId() == id) {
                    dep.getEmployees().remove(emp);
                }
                break;
            }
        }
    }
    
}
