/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem;

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
    
    
    public Frame1() {
        
        menuBar = new JMenuBar();
        menuFile = new JMenu("File");
        companyUpItem = new JMenuItem("Import XML..");
        companyDownItem = new JMenuItem("Export XML..");
        menuFile.add(companyUpItem);
        menuFile.add(companyDownItem);
        menuBar.add(menuFile);
        
        this.setJMenuBar(menuBar);
        this.setBounds(300,300,800,400);        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);          
        this.setTitle("Information System");
        this.setVisible(true);
    }
    
   
}
