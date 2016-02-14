/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem;

import informationsystem.controller.Controller;
import informationsystem.controller.ControllerXML;
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
public class EmployeeEditFrame extends JFrame {

    JPanel mainPanel;
    Font f;
    JLabel lastNameLabel;
    JLabel firstNameLabel;
    JLabel functionLabel;
    JLabel salaryLabel;
    JTextField lastNameText;
    JTextField firstNameText;
    JTextField functionText;
    JTextField salaryText;
    JPanel buttonPanel;
    JButton editButton;
    JButton saveButton;
    JFrame thisFrame;
    JFrame mainFrame;

    ControllerXML c;
    long id;

    public EmployeeEditFrame(final Controller con, final Employee emp, final FrameTable mainFrame) {
        thisFrame = this;
        mainPanel = new JPanel(new GridLayout(6, 2));
        buttonPanel = new JPanel();
        this.f = new Font("TimesRoman", Font.BOLD, 15);
        this.lastNameLabel = new JLabel("Last name");
        this.firstNameLabel = new JLabel("First name");
        this.functionLabel = new JLabel("Function");
        this.salaryLabel = new JLabel("Salary");
        this.lastNameText = new JTextField(emp.getLastName());
        this.firstNameText = new JTextField(emp.getFirstName());
        this.functionText = new JTextField(emp.getFunction());
        this.salaryText = new JTextField(String.valueOf(emp.getSalary()));
        this.editButton = new JButton("EDIT");
        this.saveButton = new JButton("SAVE");
        mainPanel.add(lastNameLabel);
        mainPanel.add(lastNameText);
        mainPanel.add(firstNameLabel);
        mainPanel.add(firstNameText);
        mainPanel.add(functionLabel);
        mainPanel.add(functionText);
        mainPanel.add(salaryLabel);
        mainPanel.add(salaryText);
        mainPanel.add(editButton);
        mainPanel.add(saveButton);
        this.add(mainPanel);
        this.setVisible(true);
        this.setResizable(false);
        this.setBounds(20, 20, 300, 300);
        setTextEditable(false);
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                setTextEditable(true);
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
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
                    int res = con.editEmployee(emp.getId(), con.getDepartment(mainFrame.currdep).getName(), firstNameText.getText(),
                            lastNameText.getText(), functionText.getText(), Integer.valueOf(salaryText.getText()));
                    if (res == 0) {
                        JOptionPane.showMessageDialog(rootPane, "Such employee not exist!");
                    }
                    if (res == -1) {
                        JOptionPane.showMessageDialog(rootPane, "Such department alredy exist!");
                    }
                    if (res == -2) {
                        JOptionPane.showMessageDialog(rootPane, "Этого не должно быть");
                    }
                    if (res == 1) {
                        emp.setSalary(Integer.valueOf(salaryText.getText()));
                        emp.setFirstName(firstNameText.getText());
                        emp.setLastName(lastNameText.getText());
                        emp.setFunction(functionText.getText());
                    }
                    mainFrame.createEmployeeTable(-1);
                    setTextEditable(false);
                }
            }
        });
    }

    private void setTextEditable(boolean b) {
        lastNameText.setEditable(b);
        firstNameText.setEditable(b);
        functionText.setEditable(b);
        salaryText.setEditable(b);
    }

}
