/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem;

import informationsystem.controller.Controller;
import informationsystem.model.dataClasses.Department;
import informationsystem.model.dataClasses.Employee;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import javax.swing.*;
import javax.swing.tree.*;


/**
 *
 * @author Алексей
 */
public class Frame1 extends JFrame {
    //Controller con;
    JPanel mainPanel;
    JMenuBar menuBar;
    JMenu menuFile;
    JMenuItem companyUpItem;
    JMenuItem companyDownItem;
    DefaultMutableTreeNode level0;
    JTree jt;
    Frame1 mainFrame = this;
    JScrollPane jsp;
    public static final int PORT = 7777;
    static ObjectOutputStream objOut;
    static ObjectInputStream objIn;



    public Frame1() throws IOException {
        InetAddress addr = InetAddress.getLocalHost();
        Socket s = new Socket(addr, PORT);
        try {
            objOut = new ObjectOutputStream(s.getOutputStream());
            objIn = new ObjectInputStream(s.getInputStream());
            try {
                menuBar = new JMenuBar();
                menuFile = new JMenu("File");
                companyUpItem = new JMenuItem("Import XML..");
                companyDownItem = new JMenuItem("Export XML..");
                menuFile.add(companyUpItem);
                menuFile.add(companyDownItem);
                menuBar.add(menuFile);


                this.setJMenuBar(menuBar);
                this.setBounds(100, 100, 400, 400);
                this.setDefaultCloseOperation(EXIT_ON_CLOSE);
                this.setTitle("Information System");
                this.setVisible(true);

                companyUpItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                        boolean res = false;
                        JFileChooser f = new JFileChooser();
                        f.setMultiSelectionEnabled(false);
                        f.showDialog(null, "Open ");
                        try {
                            objOut.writeObject("createCF");
                            objOut.writeObject(f.getSelectedFile().getPath());
                            createTree();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        } catch (ClassNotFoundException e1) {
                            e1.printStackTrace();
                        }


                    }
                });
                companyDownItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        JFileChooser f = new JFileChooser("Save as..");
                        f.showSaveDialog(null);
                        try {
                            objOut.writeObject("save");
                            objOut.writeObject(f.getSelectedFile().getPath());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                    }
                });
            }
            finally{
                objOut.writeObject("end");
                s.close();
            }
        }
        catch (IOException e) {
                e.getMessage();
        }

    }

    public void createTree() throws IOException, ClassNotFoundException {
                    if(jsp != null){
                        mainFrame.remove(jsp);
                        mainFrame.revalidate();
                        mainFrame.repaint();
                    }
                objOut.writeObject("getCompanyName");
                String companyName = (String) objIn.readObject();
                    level0 =  new DefaultMutableTreeNode(companyName);
                    jt = new JTree(level0);
                    objOut.writeObject("departmentCount");
                    int numberOfDep = (int) objIn.readObject();
                    DefaultMutableTreeNode[] departments = new DefaultMutableTreeNode[numberOfDep];
                    for(int i = 0; i < departments.length; i++){
                        objOut.writeObject("getDepI");
                        objOut.writeObject(i);
                        Department dep = (Department) objIn.readObject();
                        departments[i] = new DefaultMutableTreeNode(dep.getName());
                        objOut.writeObject("getAllDepartments");
                        Department[] deps = (Department[]) objIn.readObject();
                        for(int j = 0; j < deps[i].getEmployeeCount(); j++){
                            DefaultMutableTreeNode employee = new DefaultMutableTreeNode(j+1);                            
                            departments[i].add(employee);
                        }
                        level0.add(departments[i]);
                    }
                    jt.addMouseListener(new MouseAdapter() {
                        JPopupMenu popup;
                        @Override
                        public void mouseClicked(MouseEvent me) {
                            final TreePath tp = jt.getPathForLocation(me.getX(), me.getY());
                            if (tp != null && tp.getPathCount() == 3 && me.getClickCount() == 2){
                                String departmentName = tp.getParentPath().getLastPathComponent().toString();
                                int employeeId = Integer.valueOf(tp.getLastPathComponent().toString());


                                try {
                                    objOut.writeObject("getEmployeesOfDepartmentByName");
                                    objOut.writeObject(departmentName);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                                Employee[] emps = new Employee[0];
                                try {
                                    emps = (Employee[]) objIn.readObject();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                                Employee emp = emps[employeeId - 1];

                                //Employee emp =con.getDepartment(departmentName).getEmployee(employeeId - 1);
                                    new Frame2(emp);                                                 
                                                       
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
                                            objOut.writeObject("getEmployeesOfDepartmentByName");
                                            objOut.writeObject(departmentName);
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        }
                                        Employee[] emps = new Employee[0];
                                        Employee emp = null;
                                        try {
                                            emps = (Employee[]) objIn.readObject();
                                            emp = emps[employeeId - 1];
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        } catch (ClassNotFoundException e1) {
                                            e1.printStackTrace();
                                        }

                                        //Employee emp =con.getDepartment(departmentName).getEmployee(employeeId - 1);
                                                new Frame2(emp);                                                 
       
                                    }
                                });
                                delete.addActionListener(new ActionListener() {
                                    
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if(JOptionPane.showConfirmDialog(mainFrame, "Удалить сотрудника?") == 0){
                                            String departmentName = tp.getParentPath().getLastPathComponent().toString();
                                            int employeeId = Integer.valueOf(tp.getLastPathComponent().toString());
                                            Employee[] emps = new Employee[0];
                                            try {
                                                objOut.writeObject("getEmployeesOfDepartmentByName");
                                                objOut.writeObject(departmentName);
                                                emps = (Employee[]) objIn.readObject();
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                            } catch (ClassNotFoundException e1) {
                                                e1.printStackTrace();
                                            }

                                            long id = emps[employeeId - 1].getId();

                                            try {
                                                objOut.writeObject("deleteEmployee");
                                                objOut.writeObject(id);
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                            }
                                            //con.getDepartment(departmentName).deleteEmployee(employeeId - 1);
                                            try {
                                                createTree();
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                            } catch (ClassNotFoundException e1) {
                                                e1.printStackTrace();
                                            }
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

                                                String departmentName = tp.getLastPathComponent().toString();
                                            try {
                                                objOut.writeObject("deleteDepS");
                                                objOut.writeObject(departmentName);
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                            }

                                            try {
                                                createTree();
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                            } catch (ClassNotFoundException e1) {
                                                e1.printStackTrace();
                                            }

                                        }
                                    }
                                });
                                add.addActionListener(new ActionListener() {
                                    
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        
                                            String departmentName = tp.getLastPathComponent().toString();
                                            Department dep = null;
                                        try {
                                            objOut.writeObject("getDepS");
                                            objOut.writeObject(departmentName);
                                            dep = (Department) objIn.readObject();
                                        } catch (IOException e1) {
                                            e1.printStackTrace();
                                        } catch (ClassNotFoundException e1) {
                                            e1.printStackTrace();
                                        }

                                        new Frame2(dep);
                                        
                                        
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

    
    
    
    
    

