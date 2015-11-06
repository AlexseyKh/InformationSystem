/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.*;

/**
 *
 * @author Алексей
 */
public class Frame1 extends JFrame {
    
    JMenuBar menuBar;
    JMenu menuFile;
    JMenuItem companyUpItem;
    JMenuItem companyDownItem;
    JPanel panel;
    JPanel panelTree;
    JPanel panelInfo;
    ImagePanel employeeImg;
    JPanel employeeInfo;
    
    
    public Frame1() {
        
        //Меню бар
        menuBar = new JMenuBar();
        menuFile = new JMenu("File");
        companyUpItem = new JMenuItem("Import XML..");
        companyDownItem = new JMenuItem("Export XML..");
        menuFile.add(companyUpItem);
        menuFile.add(companyDownItem);
        menuBar.add(menuFile);
        //Интерфейс
        panel = new JPanel(new GridLayout(1,2));
        
        panelTree = new JPanel();
        panelTree.setBackground(Color.red);
        panelInfo = new JPanel(new GridLayout(2,1)); 
        employeeImg = new ImagePanel();        
        employeeImg.setImage("src\\img\\Nikita_Dzhigurda.jpg");
        employeeInfo = new JPanel();
        
        panelInfo.add(employeeImg);
        panelInfo.add(employeeInfo);
        panel.add(panelTree);
        panel.add(panelInfo);
        
        //Общая компановка
        this.add(panel);
        this.setJMenuBar(menuBar);
        this.setBounds(300,300,800,400);        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);          
        this.setTitle("Information System");
        this.setVisible(true);
    }
    
   
}
