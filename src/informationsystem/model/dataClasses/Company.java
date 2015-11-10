/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem.model.dataClasses;

import informationsystem.model.dataClasses.*;
import java.io.*;
import java.util.ArrayList;
import informationsystem.exceptions.*;
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

    public Company(ArrayList<Department> departments) {
        this.departments = departments;
    }

    public Company() {
    }

    public Company(String fileName) throws UncorrectXML {
        InputStream is = null;
        try {
            JAXBContext jc = JAXBContext.newInstance(Company.class);
            Unmarshaller um = jc.createUnmarshaller();
            is = new FileInputStream(fileName);
            Company m = (Company) um.unmarshal(is);
            this.departments = m.departments;
            for (Department dep : departments) {
                dep.parentCompany = this;
                for (int i = 0; i < dep.employeeCount(); i++) {
                    dep.getEmployee(i).parentDepartment = dep;
                }
            }
            if(!isCompanyDataCorrect()){
                throw new UncorrectXML();
            }
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

        int[] arr = new int[employeeCount()];
        int k = 0;
        for (Department department : departments) {
            for (int i = 0; i < department.employeeCount(); i++) {
                arr[k] = department.getEmployee(i).getId();
                k++;
            }
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

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != i + 1) {
                newId = i + 1;
                break;
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

    public Department getDepartment(String name)
            throws DepartmentWithSuchNameDoesNotExist {
        if (!suchDepartmentExist(name)) {
            throw new DepartmentWithSuchNameDoesNotExist();
        }

        for (int i = 0; i < departments.size(); i++) {
            if (departments.get(i).getName().equalsIgnoreCase(name)) {
                return departments.get(i);
            }
        }
        return null;
    }

    public void addDepartment(String name, int directorID)
            throws DepartmentWithSuchNameExist, EmployeeWithSuchIdDoesNotExist {
        if (suchDepartmentExist(name)) {
            throw new DepartmentWithSuchNameExist();
        }
        if (directorID != 0 && !suchEmployeeExist(directorID)) {
            throw new EmployeeWithSuchIdDoesNotExist();
        }
        departments.add(new Department(name, directorID, this));
    }

    public void deleteDepartment(int index) {
        for (int i = departments.get(index).employeeCount() - 1; i >= 0; i--) {
            departments.get(index).deleteEmployee(i);
        }
        departments.remove(index);
    }

    public void deleteDepartment(String name)
            throws DepartmentWithSuchNameDoesNotExist {
        if (!suchDepartmentExist(name)) {
            throw new DepartmentWithSuchNameDoesNotExist();
        }
        for (int i = 0; i < departments.size(); i++) {
            if (departments.get(i).getName().equalsIgnoreCase(name)) {
                for (int j = departments.get(i).employeeCount() - 1; j >= 0; j--) {
                    departments.get(i).deleteEmployee(j);
                }
                departments.remove(i);
                return;
            }
        }

    }

    //
    //Методы для работы с сотрудниками.
    //
    public int employeeCount() {
        int count = 0;
        for (Department department : departments) {
            for (int i = 0; i < department.employeeCount(); i++) {
                count++;
            }
        }
        return count;
    }

    public Employee getEmployee(int index) {
        if(index < 0 || index >= employeeCount()){
            throw new IndexOutOfBoundsException();
        }
        
        int k = -1;
        for (int i = 0; i < departmentCount(); i++) {
            for (int j = 0; j < getDepartment(i).employeeCount(); j++) {
                k++;
                if(k == index)
                    return getDepartment(i).getEmployee(j);
            }
        }
        
        return null;
    }
//    public Employee getEmployee(int id) {
//        for (Department department : departments) {
//            for (int i = 0; i < department.employeeCount(); i++) {
//                if (department.getEmployee(i).getId() == id) {
//                    return department.getEmployee(i);
//                }
//            }
//        }
//        return null;
//    }

    //
    //
    //
    public boolean suchDepartmentExist(String name) {
        for (Department department : departments) {
            if (department.getName().equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }

    public boolean suchEmployeeExist(int id) {
        for (Department department : departments) {
            for (int i = 0; i < department.employeeCount(); i++) {
                if (department.getEmployee(i).getId() == id) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isCompanyDataCorrect() {
        for(int i = 0; i < departmentCount(); i++)
            for(int j = i+1; j < departmentCount(); j++)
                if(getDepartment(i).getName().equalsIgnoreCase(getDepartment(j).getName()))
                    return false;
        
        for(int i = 0; i < employeeCount(); i++){
            for(int j = i+1; j < employeeCount(); j++)
                if (getEmployee(i).getId() == getEmployee(j).getId())
                    return false;
        }
        
        return true;
    }
}
