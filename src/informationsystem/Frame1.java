/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.*;
import javax.swing.tree.*;


/**
 *
 * @author Алексей
 */
public class Frame1 extends JFrame {
    
    JPanel mainPanel;
    JMenuBar menuBar;
    JMenu menuFile;
    JMenuItem companyUpItem;
    JMenuItem companyDownItem;
    DefaultMutableTreeNode level0;
    JTree jt;
    JFrame mainFrame = this;
    JScrollPane jsp;

    public Frame1() {
        
        menuBar = new JMenuBar();
        menuFile = new JMenu("File");
        companyUpItem = new JMenuItem("Import XML..");
        companyDownItem = new JMenuItem("Export XML..");
        menuFile.add(companyUpItem);
        menuFile.add(companyDownItem);
        menuBar.add(menuFile);
        
        //tree        
       
        
        this.setJMenuBar(menuBar);
        this.setBounds(100,100,400,400);        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);          
        this.setTitle("Information System");
        this.setVisible(true);
        
        companyUpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Random r = new Random();
                int count = r.nextInt(10); 
                if(jt != null){
                mainFrame.remove(jt);
                mainFrame.revalidate();
                mainFrame.repaint();
                }                
                level0 =  new DefaultMutableTreeNode("Название компании");               
                jt = new JTree(level0);
                DefaultMutableTreeNode[] departments = new DefaultMutableTreeNode[count];
                for(int i = 0; i < departments.length; i++){
                    departments[i] = new DefaultMutableTreeNode("Отдел №" + i);                    
                        for(int j = 0; j < count / 2; j++){
                            DefaultMutableTreeNode employee = new DefaultMutableTreeNode("Сотрудник №" + j);                            
                            departments[i].add(employee);
                        }
                    level0.add(departments[i]);
                }
                
                jt.addMouseListener(new MouseAdapter() {
                    boolean is = false;
                    @Override
                    public void mouseClicked(MouseEvent me) {
                       TreePath tp = jt.getPathForLocation(me.getX(), me.getY());
                        if (tp != null && tp.getPathCount() == 3 && me.getClickCount() == 2){
                           new Frame2(tp.getLastPathComponent().toString());                            
                        }                     
                                       
                  }
                });
                int v = ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED;
                int h = ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED;
                jsp = new JScrollPane(jt, v, h);     
                mainFrame.getContentPane().add(jt);                
                mainFrame.setVisible(true);
                
            }
        });
        
        
      }
 }

    
    
    
    
    

