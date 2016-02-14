/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem;

import informationsystem.controller.Controller;
import informationsystem.model.dataClasses.Department;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.*;

/**
 *
 * @author Алексей
 */
public class AddEmployeeFrame extends JFrame {

    JPanel mainPanel;
    Font f;
    JLabel lastNameLabel;
    JLabel firstNameLabel;
    JLabel functionLabel;
    JLabel salaryLabel;
    JTextField lastNameText;
    JTextField firstNameText;
    JTextField functionText;
    JTextField departmentText;
    JTextField salaryText;
    JPanel buttonPanel;
    JButton addButton;
    JFrame thisFrame;
    JFrame mainFrame;

    //Controller c;
    long id;

    public AddEmployeeFrame(final Controller con, final long id, final FrameTable mainFrame) {
        thisFrame = this;
        //this.c = c;
        this.id = id;
        mainPanel = new JPanel(new GridLayout(6, 2));
        buttonPanel = new JPanel();
        this.f = new Font("TimesRoman", Font.BOLD, 15);
        this.lastNameLabel = new JLabel("Last name");
        this.firstNameLabel = new JLabel("First name");
        this.functionLabel = new JLabel("Function");
        this.salaryLabel = new JLabel("Salary");
        this.lastNameText = new JTextField();
        this.firstNameText = new JTextField();
        this.functionText = new JTextField();
        this.salaryText = new JTextField();
        this.departmentText = new JTextField();
        this.addButton = new JButton("ADD");
        mainPanel.add(lastNameLabel);
        mainPanel.add(lastNameText);
        mainPanel.add(firstNameLabel);
        mainPanel.add(firstNameText);
        mainPanel.add(functionLabel);
        mainPanel.add(functionText);
        mainPanel.add(salaryLabel);
        mainPanel.add(salaryText);
        //mainPanel.add(departmentText);
        mainPanel.add(addButton);
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //c.addEmployee(c.getDepartment(id).getName(), firstNameText.getText(), secondNameText.getText(), functionText.getText(), Integer.valueOf(salaryText.getText()));
                Department dep = null;

                boolean isInputCorrect = true;
                if (firstNameText.getText().equals("")) {
                    isInputCorrect = false;
                    JOptionPane.showMessageDialog(rootPane, "FirstName must be fill!");
                }
                if (lastNameText.getText().equals("")) {
                    isInputCorrect = false;
                    JOptionPane.showMessageDialog(rootPane, "LastName must be fill!");
                }
                if (functionText.getText().equals("")) {
                    isInputCorrect = false;
                    JOptionPane.showMessageDialog(rootPane, "Function must be fill!");
                }
                try {
                    Integer.parseInt(salaryText.getText());
                } catch (Exception e) {
                    isInputCorrect = false;
                    JOptionPane.showMessageDialog(rootPane, "Sulary must be number!");
                }
                if (isInputCorrect) {
                    dep = con.getDepartment(id);
                    int res = con.addEmployee(dep.getName(), firstNameText.getText(), lastNameText.getText(),
                            functionText.getText(), Integer.parseInt(salaryText.getText()));
                    if (res == -1) {
                        JOptionPane.showMessageDialog(rootPane, "Such employee alredy exists!");
                    }
                    if (res == 0) {
                        JOptionPane.showMessageDialog(rootPane, "Such department not exists!");
                    }
                    if (res == 1) {
                        mainFrame.createEmployeeTable(id);
                        processWindowEvent(new WindowEvent(thisFrame, WindowEvent.WINDOW_CLOSING));

                    }
                }

            }
        });
        this.add(mainPanel);
        this.setVisible(true);
        this.setResizable(false);
        this.setBounds(20, 20, 300, 300);

    }

}
