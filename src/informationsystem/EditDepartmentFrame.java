/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem;

import informationsystem.model.dataClasses.Department;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Алексей
 */
public class EditDepartmentFrame extends JFrame {
    
    JPanel mainPanel;
    JLabel nameLabel;    
    JLabel directorLabel;
    JTextField nameText;
    JTextField directorText;
    JButton saveButton;
    FrameTable mainFrame;
    
    public EditDepartmentFrame(Department dep, FrameTable mainFrame) {
        this.mainFrame = mainFrame;
        mainPanel = new JPanel(new GridLayout(3,2));
        nameLabel = new JLabel("Name");
        directorLabel = new JLabel("Director");
        nameText = new JTextField(dep.getName());
        directorText = new JTextField(String.valueOf(dep.getDirectorId()));
        saveButton = new JButton("Save");
        mainPanel.add(nameLabel);
        mainPanel.add(nameText);
        mainPanel.add(directorLabel);
        mainPanel.add(directorText);
        mainPanel.add(saveButton);
        this.add(mainPanel);        
        this.setVisible(true);
        this.setResizable(false);
        this.setBounds(20,20,300,300);
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String s = nameText.getText();
                dep.setName(nameText.getText());
                dep.setDirectorId(Long.valueOf(directorText.getText()));
                mainFrame.createDepartmentTable();
            }
        });
    }
    
    
}
