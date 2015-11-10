/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem;

import informationsystem.exceptions.*;
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
        Company company = new Company(new ArrayList<>());
        try {

            company.addDepartment("Programmers", 0);
            company.getDepartment(0).addEmployee(1, "Georg", "Shultc", "01", 111);
            company.getDepartment(0).addEmployee(2, "Georg", "Shultc", "02", 111);

            company.addDepartment("Designers", 0);
            company.getDepartment(1).addEmployee(3, "Georg", "Shultc", "03", 111);
            company.getDepartment(1).addEmployee(4, "Georg", "Shultc", "04", 111);
            company.getDepartment(1).addEmployee(5, "Georg", "Shultc", "05", 111);
            company.getDepartment(1).addEmployee(6, "Georg", "Shultc", "06", 111);
            company.getDepartment(1).addEmployee(7, "Georg", "Shultc", "07", 111);
            company.getDepartment(1).addEmployee(8, "Georg", "Shultc", "08", 111);
            company.getDepartment(1).addEmployee(9, "Georg", "Shultc", "09", 111);

            company.addDepartment("Testers", 8);
            company.getDepartment(2).addEmployee(11, "Georg", "Shultc", "10", 111);
            company.getDepartment(2).addEmployee(12, "Georg", "Shultc", "11", 111);
            company.getDepartment(2).addEmployee(13, "Georg", "Shultc", "12", 111);

            company.addDepartment("Directors", 0);
            company.getDepartment(3).addEmployee(14, "Georg", "Shultc", "13", 111);

            System.out.println("Вывод информации о фирме:");
            for (int i = 0; i < company.departmentCount(); i++) {
                System.out.println(company.getDepartment(i).toString());
                for (int j = 0; j < company.getDepartment(i).employeeCount(); j++) {
                    System.out.println(company.getDepartment(i).getEmployee(j).toString());
                }
            }

            company.saveDataXML("data.xml");

            for (int i = 0; i < company.departmentCount(); i++) {
                for (int j = company.getDepartment(i).employeeCount() - 1; j >= 0; j--) {
                    if (company.getDepartment(i).getEmployee(j).getId() % 2 == 0) {
                        company.getDepartment(i).deleteEmployee(j);
                    }
                }
            }

            System.out.println("Вывод информации о фирме:");
            for (int i = 0; i < company.departmentCount(); i++) {
                System.out.println(company.getDepartment(i).toString());
                for (int j = 0; j < company.getDepartment(i).employeeCount(); j++) {
                    System.out.println(company.getDepartment(i).getEmployee(j).toString());
                }
            }

            company = new Company("data.xml");

            System.out.println("Вывод информации о фирме:");
            for (int i = 0; i < company.departmentCount(); i++) {
                System.out.println(company.getDepartment(i).toString());
                for (int j = 0; j < company.getDepartment(i).employeeCount(); j++) {
                    System.out.println(company.getDepartment(i).getEmployee(j).toString());
                }
            }

            company.deleteDepartment("Testers");
            company.addDepartment("Producers", 5);
            company.getDepartment("Producers").addEmployee(company.generateId(), "Jacob", "Shultc", "Manager", 22243);
            company.getDepartment(0).deleteEmployee(0);
            company.getDepartment(0).getEmployee(0).setId(2);

            System.out.println("Вывод информации о фирме:");
            for (int i = 0; i < company.departmentCount(); i++) {
                System.out.println(company.getDepartment(i).toString());
                for (int j = 0; j < company.getDepartment(i).employeeCount(); j++) {
                    System.out.println(company.getDepartment(i).getEmployee(j).toString());
                }
            }
        } catch (DepartmentWithSuchNameDoesNotExist e) {
            System.err.println(e);
        } catch (EmployeeWithSuchIdDoesNotExist e) {
            System.err.println(e);

        } catch (DepartmentWithSuchNameExist e) {
            System.err.println(e);

        } catch (EmployeeWithSuchIdExist e) {
            System.err.println(e);

        } catch (UncorrectId e) {
            System.err.println(e);

        } catch (UncorrectXML e) {
            System.err.println(e);
        }
    }
}
