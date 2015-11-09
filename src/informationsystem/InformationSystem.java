/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem;

import informationsystem.model.dataClasses.Company;
import java.util.ArrayList;

/**
 *
 * @author Алексей
 */
public class InformationSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

//        Company model = new Company(new ArrayList<>(), new ArrayList<>());
//
//        model.addDepartment("Programmers", 0);
//        model.addEmployee(1, "Georg", "Shultc", "01", "Programmers", 111);
//        model.addEmployee(2, "Georg", "Shultc", "02", "Programmers", 111);
//
//        model.addDepartment("Designers", 0);
//        model.addEmployee(3, "Georg", "Shultc", "02", "Designers", 222);
//        model.addEmployee(4, "Georg", "Shultc", "04", "Designers", 222);
//        model.addEmployee(5, "Georg", "Shultc", "05", "Designers", 222);
//        model.addEmployee(6, "Georg", "Shultc", "06", "Designers", 222);
//        model.addEmployee(7, "Georg", "Shultc", "07", "Designers", 222);
//        model.addEmployee(8, "Georg", "Shultc", "08", "Designers", 222);
//        model.addEmployee(11, "Georg", "Shultc", "11", "Designers", 222);
//
//        model.addDepartment("Testers", 0);
//        model.addEmployee(9, "Georg", "Shultc", "09", "Testers", 333);
//        model.addEmployee(10, "Georg", "Shultc", "10", "Testers", 333);
//        model.addEmployee(12, "Georg", "Shultc", "12", "Testers", 333);
//
//        model.addDepartment("Directors", 0);
//        model.addEmployee(13, "Georg", "Shultc", "13", "Directors", 444);
//        
//        model.updateDepartment("Testers", "Testers", 13);
//        model.updateDepartment("Programmers", "Programmers", 13);
//
//        System.out.println("Вывод информации о фирме:");
//        for (int i = 0; i < model.departmentCount(); i++) {
//            System.out.println(model.getDepartment(i).toString());
//            for (int j = 0; j < model.employeeCount(i); j++) {
//                System.out.println(model.getEmployee(i, j).toString());
//            }
//        }
//        
//        model.saveDataXML("data.xml");
//        
//        for (int i = 0; i < model.departmentCount(); i++) {
//            for (int j = 0; j < model.employeeCount(i); j++) {
//                if(model.getEmployee(i, j).getId()%2 == 0){
//                    model.deleteEmployee(i, j);
//                }
//            }
//        }
//        
//        System.out.println("\nВывод информации о фирме:");
//        for (int i = 0; i < model.departmentCount(); i++) {
//            System.out.println(model.getDepartment(i).toString());
//            for (int j = 0; j < model.employeeCount(i); j++) {
//                System.out.println(model.getEmployee(i, j).toString());
//            }
//        }

        Company model = new Company("data.xml");

        System.out.println("\nВывод информации о фирме:");
        for (int i = 0; i < model.departmentCount(); i++) {
            System.out.println(model.getDepartment(i).toString());
            for (int j = 0; j < model.employeeCount(i); j++) {
                System.out.println(model.getEmployee(i, j).toString());
            }
        }
        
        model.updateDepartment(0, "Programmers", 8);
        model.addDepartment("Producers", 0);
        model.addEmployee(14, "Jack", "Shultc", "14", "Programmers", 555);
        model.addEmployee(15, "Jacob", "Shultc", "15", "Designers", 555);
        model.deleteEmployee(13);
        model.deleteEmployee(2);
        model.updateEmployee(7, 16, "Georg", "Shultc", "07", "Producers", 222);
        model.addEmployee(model.generateId(), "Jan", "Shultc", "007", "Directors", 555);

        System.out.println("\nВывод информации о фирме:");
        for (int i = 0; i < model.departmentCount(); i++) {
            System.out.println(model.getDepartment(i).toString());
            for (int j = 0; j < model.employeeCount(i); j++) {
                System.out.println(model.getEmployee(i, j).toString());
            }
        }
    }
}
