package informationsystem.controller;

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

public class ControllerXML implements Controller {

    private Company company;

    public ControllerXML() {
    }

    public void createCompany(String name) {
        company = new Company(name);
    }

    public void loadData(String fileName) {
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

    public void saveData(String fileName) {
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

    public String getCompanyName() {
        return company.getName();
    }

    public void setCompanyName(String name) {
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

    public boolean addDepartment(String name) {
        if (company.getDepartments().contains(new Department(-1, name))) {
            return false;
        }
        company.getDepartments().add(
                new Department(company.getIdSequenceForDepartments(), name));
        company.setIdSequenceForDepartments(company.getIdSequenceForDepartments() + 1);
        return true;
    }

    public boolean deleteDepartment(long id) {

        for (Department dep : company.getDepartments()) {
            if (dep.getId() == id) {
                company.getDepartments().remove(dep);
                return true;
            }
        }
        return false;
    }

    public boolean deleteDepartment(String name) {
        for (Department dep : company.getDepartments()) {
            if (dep.getName() == name) {
                company.getDepartments().remove(dep);
                return true;
            }
        }
        return false;
    }

    public int editDepartment(long departmentId, String newName, long newDirectorId) {
        boolean exist = false;
        if (newDirectorId != -1) {
            Employee[] emps = getAllEmployees();
            for (int i = 0; i < emps.length; i++) {
                if (newDirectorId == emps[i].getId()) {
                    exist = true;
                }
            }
        } else {
            exist = true;
        }
        if(!exist){
            return 0;
        }
        for (Department dep : company.getDepartments()) {
            if (dep.getName().equals(newName)) {
                if (departmentId == dep.getId()) {
                    break;
                } else {
                    return -1;
                }
            }
        }
        for (Department dep : company.getDepartments()) {
            if(departmentId == dep.getId()){
                dep.setName(newName);
                dep.setDirectorId(newDirectorId);
                break;
            }
        }
        return 1;
    }

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
        return emps.toArray(new Employee[emps.size()]);
    }

    public Employee[] getEmployeesOfDepartment(long id) {
        for (Department dep : company.getDepartments()) {
            if (dep.getId() == id) {
                return dep.getEmployees().toArray(new Employee[dep.getEmployees().size()]);
            }
        }
        return null;
    }

    public Employee[] getEmployeesOfDepartment(String name) {
        for (Department dep : company.getDepartments()) {
            if (dep.getName() == name) {
                return dep.getEmployees().toArray(new Employee[dep.getEmployees().size()]);
            }
        }
        return null;
    }

    public int addEmployee(String departmentName, String firstName, String lastName,
            String function, int salary) {

        Employee emp = new Employee(company.getIdSequenceForEmployees(), firstName, lastName, function, salary);
        for (Department dep : company.getDepartments()) {
            if (dep.getEmployees().contains(emp)) {
                return -1;//уже есть сотрудник с таким названием
            }
        }

        for (Department dep : company.getDepartments()) {
            if (dep.getName().equals(departmentName)) {
                dep.getEmployees().add(emp);
                company.setIdSequenceForEmployees(company.getIdSequenceForEmployees() + 1);
                return 1;
            }
        }
        return 0;//отдела с таким названием нет
    }

    public boolean deleteEmployee(long id) {

        for (Department dep : company.getDepartments()) {
            if (dep.getDirectorId() == id) {
                dep.setDirectorId(-1);
            }
        }
        
        for (Department dep : company.getDepartments()) {
            for (Employee emp : dep.getEmployees()) {
                if (emp.getId() == id) {
                    dep.getEmployees().remove(emp);
                    return true;
                }
            }
        }
        return false;
    }

    public int editEmployee(long employeeId, String newDepartmentName, String newFirstName, String newLastName,
            String newFunction, int newSalary) {
        int exist = 0;
        for (Department dep : getAllDepartments()) {
            if (dep.getName().equals(newDepartmentName)) {
                exist = 1;
                break;
            }
        }
        if (exist == 0) {
            return 0;
        } else {
            Employee newEmp = new Employee(employeeId, newFirstName, newLastName, newFunction, newSalary);
            outer:
            for (Department dep : getAllDepartments()) {
                for (Employee emp : dep.getEmployees()) {
                    if (emp.equals(newEmp)) {
                        if (employeeId == emp.getId()) {
                            break outer;
                        } else {
                            return -1;
                        }
                    }
                }
            }
            for (Department dep : getAllDepartments()) {
                for (Employee emp : dep.getEmployees()) {
                    if (employeeId == emp.getId()) {
                        if (!dep.getName().equals(newDepartmentName)) {
                            deleteEmployee(employeeId);
                            dep.getEmployees().add(newEmp);
                        }
                        emp.setFirstName(newFirstName);
                        emp.setLastName(newLastName);
                        emp.setFunction(newFunction);
                        emp.setSalary(newSalary);
                        return 1;
                    }
                }
            }
        }
        return -2;
    }
    
    public void merge(String fileName) {
        Company companyXML = null;
        InputStream is = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(Company.class);
            Unmarshaller um = jc.createUnmarshaller();
            is = new FileInputStream(fileName);
            if (company == null) {
                company = (Company) um.unmarshal(is);
                return;
            }
            companyXML = (Company) um.unmarshal(is);

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

        for (Department depXML : companyXML.getDepartments()) {
            addDepartment(depXML.getName());
            for (Employee empXML : depXML.getEmployees()) {
                addEmployee(depXML.getName(), empXML.getFirstName(), empXML.getLastName(),
                        empXML.getFunction(), empXML.getSalary());
            }
        }
    }
}
