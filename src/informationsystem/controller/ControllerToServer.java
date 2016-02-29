/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem.controller;

import informationsystem.model.dataClasses.Department;
import informationsystem.model.dataClasses.Employee;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Игорь
 */
public class ControllerToServer{

    public int PORT = 7777;
    private ObjectOutputStream objOut;
    private ObjectInputStream objIn;
    private Socket s;

    public ControllerToServer(int PORT) {
        try {
            this.PORT = PORT;
            InetAddress addr = InetAddress.getLocalHost();
            s = new Socket(addr, PORT);
            objOut = new ObjectOutputStream(s.getOutputStream());
            objIn = new ObjectInputStream(s.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ControllerToServer.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
    
    public void closeConnection(){
        try {
            objOut.writeObject("end");
            objOut.flush();
            objIn.close();
            objOut.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createCompany(String name) {
        try {
            objOut.writeObject("createCS");
            objOut.writeObject(name);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData(String fileName) {
        try {
            objOut.writeObject("createCF");
            objOut.writeObject(fileName);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void saveData(String fileName) {
        try {
            objOut.writeObject("save");
            objOut.writeObject(fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getCompanyName() {
        try {
            objOut.writeObject("getCompanyName");
            return (String) objIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        } 
    }

    public void setCompanyName(String name) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public int departmentCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Department getDepartment(long id) {
        try {
            objOut.writeObject("getDepI");
            objOut.writeObject(id);
            return (Department) objIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Department getDepartment(String name) {
        try {
            objOut.writeObject("getDepS");
            objOut.writeObject(name);
            return (Department) objIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Department[] getAllDepartments() {
        try {
            objOut.writeObject("getAllDepartments");
            return (Department[]) objIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    public boolean addDepartment(String name) {
        try {
            objOut.writeObject("addDep");
            objOut.writeObject(name);
            return (boolean) objIn.readObject();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteDepartment(long id) {
        try {
            objOut.writeObject("delDepI");
            objOut.writeObject(Long.valueOf(id));
            return (boolean) objIn.readObject();
        } catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    public boolean deleteDepartment(String name) {
        try {
            objOut.writeObject("delDepS");
            objOut.writeObject(name);
            return (boolean) objIn.readObject();
        } catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
            return false;
        }
    }

    public int editDepartment(long departmentId, String newName, long newDirectorId) {
        try {
            objOut.writeObject("editDepartment");
            objOut.writeObject(departmentId);
            objOut.writeObject(newName);
            objOut.writeObject(newDirectorId);
            return (int) objIn.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            return -2;
        }
    }

    public int employeeCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Employee getEmployee(long id) {
        try {
            objOut.writeObject("getEmployee");
            objOut.writeObject(id);
            return (Employee) objIn.readObject();
        } catch (IOException | ClassNotFoundException e1) {
            e1.printStackTrace();
            return null;
        }
    }

    public Employee[] getAllEmployees() {
        try {
            objOut.writeObject("getAllEmployees");
            return (Employee[]) objIn.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }

    public Employee[] getEmployeesOfDepartment(long id) {
        try {
            objOut.writeObject("getEmployeesOfDepartmentById");
            objOut.writeObject(id);
            return (Employee[]) objIn.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }

    public Employee[] getEmployeesOfDepartment(String name) {
        try {
            objOut.writeObject("getEmployeesOfDepartmentByName");
            objOut.writeObject(name);
            return (Employee[]) objIn.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }

    public int addEmployee(String departmentName, String firstName, String lastName, String function, int salary) {
        try {
            objOut.writeObject("addEmployee");
            objOut.writeObject(departmentName);
            objOut.writeObject(firstName);
            objOut.writeObject(lastName);
            objOut.writeObject(function);
            objOut.writeObject(Integer.valueOf(salary));
            return (int) objIn.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            return -2;
        }
    }

    public boolean deleteEmployee(long id) {
        try {
            objOut.writeObject("deleteEmployee");
            objOut.writeObject(id);
            return (boolean) objIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int editEmployee(long employeeId, String newDepartmentName,
            String newFirstName, String newLastName, String newFunction, int newSalary) {
        try {
            objOut.writeObject("editEmployee");
            objOut.writeObject(employeeId);
            objOut.writeObject(newDepartmentName);
            objOut.writeObject(newFirstName);
            objOut.writeObject(newLastName);
            objOut.writeObject(newFunction);
            objOut.writeObject(Integer.valueOf(newSalary));
            return (int) objIn.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            return -2;
        }
    }

    public void merge(String fileName) {
        try {
            objOut.writeObject("merge");
            objOut.writeObject(fileName);
        } catch (IOException ex) {
        }
    }

    public Employee getLastAddedEmployee() {
        try {
            objOut.writeObject("getLastAddedEmployee");
            return (Employee) objIn.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }
}
