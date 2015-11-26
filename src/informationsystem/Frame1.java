/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem;

import informationsystem.controller.Controller;
import informationsystem.exceptions.DepartmentWithSuchNameDoesNotExist;
import informationsystem.exceptions.UncorrectXML;
import informationsystem.model.dataClasses.Company;
import informationsystem.model.dataClasses.Employee;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.tree.*;


/**
 *
 * @author Алексей
 */
public class Frame1 extends JFrame {
    Controller con;
    JPanel mainPanel;
    JMenuBar menuBar;
    JMenu menuFile;
    JMenuItem companyUpItem;
    JMenuItem companyDownItem;
    DefaultMutableTreeNode level0;
    JTree jt;
    Frame1 mainFrame = this;
    JScrollPane jsp;
    
    public Frame1() {
       
        //menubar
        menuBar = new JMenuBar();
        menuFile = new JMenu("File");
        companyUpItem = new JMenuItem("Import XML..");
        companyDownItem = new JMenuItem("Export XML..");
        menuFile.add(companyUpItem);
        menuFile.add(companyDownItem);
        menuBar.add(menuFile);        
             

        this.setJMenuBar(menuBar);
        this.setBounds(100,100,400,400);        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);          
        this.setTitle("Information System");
        this.setVisible(true);
        
        companyUpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean res = false;
                    JFileChooser f = new JFileChooser();
                    f.setMultiSelectionEnabled(false);
                    f.showDialog(null, "Open ");
                    con = new Controller();
                    con.createCompany(f.getSelectedFile().getPath()); 
                    createTree();
                } catch (UncorrectXML ex) {}
                
                
                
            }
        });
        companyDownItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {  
                JFileChooser f = new JFileChooser("Save as..");
                f.showSaveDialog(null);
                con.saveDataXML(f.getSelectedFile().getPath());
                
            }                        
        });
        
        
      }
    public void createTree(){
                    if(jsp != null){
                        mainFrame.remove(jsp);
                        mainFrame.revalidate();
                        mainFrame.repaint();
                    }
                    level0 =  new DefaultMutableTreeNode("Название компании");
                    jt = new JTree(level0);
                    DefaultMutableTreeNode[] departments = new DefaultMutableTreeNode[con.departmentCount()];
                    for(int i = 0; i < departments.length; i++){                    
                        departments[i] = new DefaultMutableTreeNode(con.getDepartment(i).getName());
                        for(int j = 0; j < con.getDepartment(i).employeeCount(); j++){
                            DefaultMutableTreeNode employee = new DefaultMutableTreeNode(j+1);                            
                            departments[i].add(employee);
                        }
                        level0.add(departments[i]);
                    }
                    jt.addMouseListener(new MouseAdapter() {
                        JPopupMenu popup;
                        @Override
                        public void mouseClicked(MouseEvent me) {
                            TreePath tp = jt.getPathForLocation(me.getX(), me.getY());
                            if (tp != null && tp.getPathCount() == 3 && me.getClickCount() == 2){
                                String departmentName = tp.getParentPath().getLastPathComponent().toString();
                                int employeeId = Integer.valueOf(tp.getLastPathComponent().toString());
                                try {
                                    Employee emp =con.getDepartment(departmentName).getEmployee(employeeId - 1);
                                    new Frame2(emp);                                                 
                                } catch (DepartmentWithSuchNameDoesNotExist ex) {}                          
                            }
                            if (tp != null && tp.getPathCount() == 3 && me.getButton() == 3){
                                popup = new JPopupMenu();
                                JMenuItem delete = new JMenuItem("Delete");
                                JMenuItem show = new JMenuItem("Show");
                                popup.add(show);
                                popup.add(delete);
                                popup.show(mainFrame, me.getX()  + 25, me.getY() + 50);
                                show.addActionListener(new ActionListener() {
                                    
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                            String departmentName = tp.getParentPath().getLastPathComponent().toString();
                                            int employeeId = Integer.valueOf(tp.getLastPathComponent().toString());
                                            try {
                                                Employee emp =con.getDepartment(departmentName).getEmployee(employeeId - 1);
                                                new Frame2(emp);                                                 
                                            } catch (DepartmentWithSuchNameDoesNotExist ex) {}
                                    }
                                });
                                delete.addActionListener(new ActionListener() {
                                    
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if(JOptionPane.showConfirmDialog(mainFrame, "Удалить сотрудника?") == 0){
                                            String departmentName = tp.getParentPath().getLastPathComponent().toString();
                                            int employeeId = Integer.valueOf(tp.getLastPathComponent().toString());
                                            try {
                                                con.getDepartment(departmentName).deleteEmployee(employeeId - 1); 
                                                createTree();
                                            } catch (DepartmentWithSuchNameDoesNotExist ex) {}
                                        }
                                    }
                                });
                            }
                            if (tp != null && tp.getPathCount() == 2 && me.getButton() == 3){
                                popup = new JPopupMenu();
                                JMenuItem delete = new JMenuItem("Delete");
                                JMenuItem add = new JMenuItem("Add");                                
                                popup.add(delete);
                                popup.add(add);
                                popup.show(mainFrame, me.getX()  + 25, me.getY() + 50);                                
                                delete.addActionListener(new ActionListener() {
                                    
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if(JOptionPane.showConfirmDialog(mainFrame, "Удалить отдел?") == 0){
                                            try {
                                                String departmentName = tp.getLastPathComponent().toString();
                                                con.deleteDepartment(departmentName);
                                                createTree();
                                            } catch (DepartmentWithSuchNameDoesNotExist ex) { }
                                        }
                                    }
                                });
                                add.addActionListener(new ActionListener() {
                                    
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        String departmentName = tp.getLastPathComponent().toString();
                                        new Frame2(con, departmentName, mainFrame);   
                                        
                                    }
                                });
                            }
                            
                        }
                    });
                    int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
                    int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
                    jsp = new JScrollPane(jt, v, h);
                    mainFrame.getContentPane().add(jsp);
                    mainFrame.setVisible(true);
    }
 }

    
    
    
    
    

