/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem;

import informationsystem.controller.ControllerXML;
import informationsystem.model.dataClasses.Department;
import informationsystem.model.dataClasses.Employee;

/**
 *
 * @author Игорь
 */
public class TestClass {
    public static void main(String[] args) {
        ControllerXML con = new ControllerXML();
        con.createCompany("BigCompany");
        con.addDepartment("BigDepart");
        con.addDepartment("BigDepart");
        con.addDepartment("BigDepart");
        con.addDepartment("SmallDepart");
        con.addEmployee("BigDepart", "111", "111", "000", 1000);
        con.addEmployee("BigDepart", "111", "111", "000", 1000);
        con.addEmployee("BigDepart", "333", "111", "000", 1000);
        con.addEmployee("SmallDepart", "444", "111", "000", 1000);
        con.saveData("111.xml");
        Department[] deps = con.getAllDepartments();
        for(int i = 0; i < deps.length; i++){
            System.out.println(con.departmentCount());
            Employee[] emps = con.getEmployeesOfDepartment(deps[i].getId());
            for(int j = 0; j < emps.length; j++){
                System.out.println(i + ", " + j);
            
            }
        }
    }
}
