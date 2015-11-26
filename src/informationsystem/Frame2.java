/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem;

import informationsystem.controller.Controller;
import informationsystem.exceptions.DepartmentWithSuchNameDoesNotExist;
import informationsystem.exceptions.EmployeeWithSuchIdExist;
import informationsystem.exceptions.UncorrectId;
import informationsystem.model.dataClasses.Department;
import informationsystem.model.dataClasses.Employee;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import javax.swing.*;

/**
 *
 * @author Алексей
 */
public class Frame2 extends JFrame {
    
    
    JPanel panelInfo;
    ImagePanel employeeImg;
    JPanel employeeInfo;
    Font f = new Font("TimesRoman", Font.BOLD,15); 
    JLabel lastNameLabel = new JLabel("Last name");
    JLabel firstNameLabel = new JLabel("First name");
    JLabel functionLabel = new JLabel("Function");
    JLabel departmentLabel = new JLabel("Department");
    JLabel salaryLabel = new JLabel("Salary");
    JTextField lastNameText = new JTextField();
    JTextField firstNameText = new JTextField();
    JTextField functionText = new JTextField();
    JTextField departmentText = new JTextField();
    JTextField salaryText = new JTextField();    
    JButton saveButton = new JButton("Save");
    JButton editButton = new JButton("Edit");
    JFrame thisFrame = this;
    
    public Frame2(Employee emp) { 
        
        panelInfo = new JPanel(new GridLayout(2,1)); 
        employeeImg = new ImagePanel();        
        employeeImg.setImage("src\\img\\Nikita_Dzhigurda.jpg");
        employeeInfo = new JPanel(new GridLayout(5,2));  
        lastNameLabel.setFont(f);
        employeeInfo.add(lastNameLabel);
        lastNameText.setFont(f);
        employeeInfo.add(lastNameText); 
        firstNameLabel.setFont(f);
        employeeInfo.add(firstNameLabel);
        firstNameText.setFont(f);
        employeeInfo.add(firstNameText);
        functionLabel.setFont(f);
        employeeInfo.add(functionLabel);
        functionText.setFont(f);
        employeeInfo.add(functionText);       
        salaryLabel.setFont(f);
        employeeInfo.add(salaryLabel);
        salaryText.setFont(f);
        employeeInfo.add(salaryText);
        employeeInfo.add(editButton);
        employeeInfo.add(saveButton);        
        setTextEditable(false);
        panelInfo.add(employeeImg);
        panelInfo.add(employeeInfo); 
        
        lastNameText.setText(emp.getSecondName());
        firstNameText.setText(emp.getFirstName());
        functionText.setText(emp.getFunction());
        salaryText.setText(String.valueOf(emp.getSalary()));
        
        
        //Общая компановка
        this.add(panelInfo);
        this.setBounds(200,200,400,500);  
        this.setTitle("");
        this.setVisible(true);
        
        
        //события
        editButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                setTextEditable(true);
            }
        });
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                emp.setFirstName(firstNameText.getText());
                emp.setSecondName(lastNameText.getText());
                emp.setFunction(functionText.getText());
                emp.setSalary(Integer.valueOf(salaryText.getText()));
                processWindowEvent(new WindowEvent(thisFrame,WindowEvent.WINDOW_CLOSING));
            }
        });
       

    }
    
    public Frame2(Controller con, String s, Frame1 parent) { 
        panelInfo = new JPanel(new GridLayout(2,1)); 
        employeeImg = new ImagePanel();        
        employeeImg.setImage("src\\img\\Nikita_Dzhigurda.jpg");
        employeeInfo = new JPanel(new GridLayout(5,2));  
        lastNameLabel.setFont(f);
        employeeInfo.add(lastNameLabel);
        lastNameText.setFont(f);
        employeeInfo.add(lastNameText); 
        firstNameLabel.setFont(f);
        employeeInfo.add(firstNameLabel);
        firstNameText.setFont(f);
        employeeInfo.add(firstNameText);
        functionLabel.setFont(f);
        employeeInfo.add(functionLabel);
        functionText.setFont(f);
        employeeInfo.add(functionText);       
        salaryLabel.setFont(f);
        employeeInfo.add(salaryLabel);
        salaryText.setFont(f);
        employeeInfo.add(salaryText);          
        employeeInfo.add(saveButton);  
        panelInfo.add(employeeImg);
        panelInfo.add(employeeInfo); 
        
        
        
        
        //Общая компановка
        this.add(panelInfo);
        this.setBounds(200,200,400,500);  
        this.setTitle("");
        this.setVisible(true);
        
        
        //события
       
        saveButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {                
                try {
                    
                    con.getDepartment(s).addEmployee(con.generateId(), firstNameText.getText(), lastNameText.getText(), functionText.getText(), Integer.valueOf(salaryText.getText()));
                    parent.createTree();      
                    processWindowEvent(new WindowEvent(thisFrame,WindowEvent.WINDOW_CLOSING));
                    } catch (EmployeeWithSuchIdExist ex) {                      
                    } catch (UncorrectId ex) {                        
                    } catch (DepartmentWithSuchNameDoesNotExist ex) {  }
            }
        });
       

    }  
    
    private void setTextEditable(boolean b){
        lastNameText.setEditable(b);
        firstNameText.setEditable(b);
        functionText.setEditable(b);
        departmentText.setEditable(b);
        salaryText.setEditable(b);        
    }
    
   
}
