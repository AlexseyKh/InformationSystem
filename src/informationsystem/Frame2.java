/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem;

import java.awt.Color;
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
    
    JPanel panelTree;
    JPanel panelInfo;
    ImagePanel employeeImg;
    JPanel employeeInfo;
    
    
    public Frame2(String s) {     
           
        panelTree = new JPanel();               
        panelInfo = new JPanel(new GridLayout(2,1)); 
        employeeImg = new ImagePanel();        
        employeeImg.setImage("src\\img\\Nikita_Dzhigurda.jpg");
        employeeInfo = new JPanel();    
        employeeInfo.add(new JTextField(s));
        panelInfo.add(employeeImg);
        panelInfo.add(employeeInfo); 
        
        //Общая компановка
        this.add(panelInfo);
        this.setBounds(200,200,400,500);  
        this.setTitle("");
        this.setVisible(true);
        
        

    }
    
   
}
