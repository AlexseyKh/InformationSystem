/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem;

import informationsystem.model.Model;
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

        Model model = new Model(new ArrayList<>(), new ArrayList<>());

        model.addDepartment("Programmers", 0);
        model.addEmployee(1, "Georg", "Shultc", "01", "Programmers", 111);
        model.addEmployee(2, "Georg", "Shultc", "02", "Programmers", 111);

        model.addDepartment("Designers", 0);
        model.addEmployee(3, "Georg", "Shultc", "02", "Designers", 222);
        model.addEmployee(4, "Georg", "Shultc", "04", "Designers", 222);
        model.addEmployee(5, "Georg", "Shultc", "05", "Designers", 222);
        model.addEmployee(6, "Georg", "Shultc", "06", "Designers", 222);
        model.addEmployee(7, "Georg", "Shultc", "07", "Designers", 222);
        model.addEmployee(8, "Georg", "Shultc", "08", "Designers", 222);
        model.addEmployee(11, "Georg", "Shultc", "11", "Designers", 222);

        model.addDepartment("Testers", 0);
        model.addEmployee(9, "Georg", "Shultc", "09", "Testers", 333);
        model.addEmployee(10, "Georg", "Shultc", "10", "Testers", 333);
        model.addEmployee(12, "Georg", "Shultc", "12", "Testers", 333);

        model.addDepartment("Directors", 0);
        model.addEmployee(13, "Georg", "Shultc", "13", "Directors", 444);
        
        model.updateDepartment("Testers", "Testers", 13);
        model.updateDepartment("Programmers", "Programmers", 13);

        System.out.println("Вывод информации о фирме:");
        for (int i = 0; i < model.departmentCount(); i++) {
            System.out.println(model.getDepartment(i).toString());
            for (int j = 0; j < model.employeeCount(i); j++) {
                System.out.println(model.getEmployee(i, j).toString());
            }
        }
        
        model.saveDataXML("data.xml");
        
        for (int i = 0; i < model.departmentCount(); i++) {
            for (int j = 0; j < model.employeeCount(i); j++) {
                if(model.getEmployee(i, j).getId()%2 == 0){
                    model.deleteEmployee(i, j);
                }
            }
        }
        
        System.out.println("\nВывод информации о фирме:");
        for (int i = 0; i < model.departmentCount(); i++) {
            System.out.println(model.getDepartment(i).toString());
            for (int j = 0; j < model.employeeCount(i); j++) {
                System.out.println(model.getEmployee(i, j).toString());
            }
        }
        
        model = new Model("data.xml");
        
        System.out.println("\nВывод информации о фирме:");
        for (int i = 0; i < model.departmentCount(); i++) {
            System.out.println(model.getDepartment(i).toString());
            for (int j = 0; j < model.employeeCount(i); j++) {
                System.out.println(model.getEmployee(i, j).toString());
            }
        }
        
        System.out.println(model.getEmployee(13).toString());
    }
}
