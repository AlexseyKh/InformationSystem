/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem;

import informationsystem.controller.Controller;
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
public class AddEmployeeFrame extends JFrame {
    
    JPanel mainPanel;    
    Font f; 
    JLabel secondNameLabel;
    JLabel firstNameLabel;
    JLabel functionLabel;
    JLabel departmentLabel;
    JLabel salaryLabel;
    JTextField secondNameText;
    JTextField firstNameText;
    JTextField functionText;
    JTextField departmentText;
    JTextField salaryText;
    JPanel buttonPanel;    
    JButton addButton;
    JFrame thisFrame;
    JFrame mainFrame;
    
    Controller c;
    long id;

    public AddEmployeeFrame(Controller c, long id, FrameTable mainFrame) { 
        thisFrame = this;
        this.c = c;
        this.id = id;
        mainPanel = new JPanel(new GridLayout(6,2));
        buttonPanel = new JPanel();
        this.f = new Font("TimesRoman", Font.BOLD,15);
        this.secondNameLabel = new JLabel("Last name");
        this.firstNameLabel = new JLabel("First name");
        this.functionLabel = new JLabel("Function");
        this.departmentLabel = new JLabel("Department");
        this.salaryLabel = new JLabel("Salary");
        this.secondNameText = new JTextField();
        this.firstNameText = new JTextField();
        this.functionText = new JTextField();
        this.salaryText = new JTextField();
        this.departmentText = new JTextField();
        this.addButton = new JButton("ADD");
        mainPanel.add(secondNameLabel);
        mainPanel.add(secondNameText);
        mainPanel.add(firstNameLabel);
        mainPanel.add(firstNameText);
        mainPanel.add(functionLabel);
        mainPanel.add(functionText);
        mainPanel.add(departmentLabel);
        mainPanel.add(departmentText);
        mainPanel.add(salaryLabel);
        mainPanel.add(salaryText);
        mainPanel.add(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                c.addEmployee(c.getDepartment(id).getName(), firstNameText.getText(), secondNameText.getText(), functionText.getText(), Integer.valueOf(salaryText.getText()));   
                mainFrame.createEmployeeTable(id);
                processWindowEvent(new WindowEvent(thisFrame,WindowEvent.WINDOW_CLOSING));
            }
        });
        this.add(mainPanel);        
        this.setVisible(true);
        this.setResizable(false);
        this.setBounds(20,20,300,300);
        
    }
    
    
    
}
