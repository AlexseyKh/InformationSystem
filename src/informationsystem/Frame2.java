/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

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
    
    
    public Frame2(String s) { 
        panelInfo = new JPanel(new GridLayout(2,1)); 
        employeeImg = new ImagePanel();        
        employeeImg.setImage("src\\img\\Nikita_Dzhigurda.jpg");
        employeeInfo = new JPanel(new GridLayout(6,2));  
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
        departmentLabel.setFont(f);
        employeeInfo.add(departmentLabel);
        departmentText.setFont(f);
        employeeInfo.add(departmentText);
        salaryLabel.setFont(f);
        employeeInfo.add(salaryLabel);
        salaryText.setFont(f);
        employeeInfo.add(salaryText);
        employeeInfo.add(editButton);
        employeeInfo.add(saveButton);        
        setTextEditable(false);
        panelInfo.add(employeeImg);
        panelInfo.add(employeeInfo); 
        
        
        
        
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
                setTextEditable(false);
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
