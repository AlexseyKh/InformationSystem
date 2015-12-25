/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem;

import informationsystem.controller.Controller;
import informationsystem.exceptions.DepartmentWithSuchNameDoesNotExist;
import informationsystem.exceptions.UncorrectXML;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Алексей
 */
public class FrameTable extends JFrame{
    private static final String DEPARTMENT = "Departments"; 
    private static final String EMPLOYEE = "Employeers";     
    JPanel departmentPanel;
    JPanel employeePanel;
    JTabbedPane tabbedPane;
    JTable departmentTable;
    JTable employeeTable;
    JMenuBar menuBar;
    JMenu menuFile;
    JMenuItem companyUpItem;
    JMenuItem companyDownItem;
    Controller con;
    
    public FrameTable() {
        //Инициализация        
        departmentPanel = new JPanel(new GridLayout());
        employeePanel = new JPanel(new GridLayout());
        tabbedPane = new JTabbedPane();
        String[] ss = {"1","2","3"};
        departmentTable = new JTable();
        departmentTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        departmentTable.setSize(departmentPanel.getSize());        
        employeeTable = new JTable();
        employeeTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        employeeTable.setSize(employeePanel.getSize()); 
        menuBar = new JMenuBar();
        menuFile = new JMenu("File");
        companyUpItem = new JMenuItem("Import XML..");
        companyDownItem = new JMenuItem("Export XML..");
          
        //Компановка
        menuFile.add(companyUpItem);
        menuFile.add(companyDownItem);
        menuBar.add(menuFile);      
        JScrollPane jspD = new JScrollPane(departmentTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane jspE = new JScrollPane(employeeTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        jspD.setSize(departmentPanel.getSize());
        jspE.setSize(employeePanel.getSize());
        departmentPanel.add(jspD);
        employeePanel.add(jspE);
        tabbedPane.addTab(DEPARTMENT, departmentPanel);
        tabbedPane.addTab(EMPLOYEE, employeePanel);   
        //События
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
                    DefaultTableModel depModel = new DefaultTableModel(new String[]{"Name", "Director ID"}, 0);
                    for(int i = 0; i < con.departmentCount(); i++){
                        depModel.addRow(new String[] {String.valueOf(con.getDepartment(i).getName()),String.valueOf(con.getDepartment(i).getDirectorID())});
                    }
                    departmentTable.setModel(depModel);
                    TableButton delDep = new TableButton("Delete");
                    TableColumn tcDelDep = new TableColumn();
                    tcDelDep.setHeaderValue("Delete");
                    delDep.addTableButtonListener(new TableButtonListener() {
                        @Override
                        public void tableButtonClicked(int row, int col) {
                           ((DefaultTableModel) departmentTable.getModel()).removeRow(row);
                        }
                    });
                    tcDelDep.setCellEditor(delDep);
                    tcDelDep.setCellRenderer(delDep);
                    departmentTable.addColumn(tcDelDep);
                    TableButton editDep = new TableButton("Edit");
                    TableColumn tcEditDep = new TableColumn();
                    tcEditDep.setHeaderValue("Edit");
                    editDep.addTableButtonListener(new TableButtonListener() {
                        @Override
                        public void tableButtonClicked(int row, int col) {
                            try {
                                new Frame2(con.getDepartment((String)departmentTable.getModel().getValueAt(row,0)));
                            } catch (DepartmentWithSuchNameDoesNotExist ex) {}
                        }
                    });
                    tcEditDep.setCellEditor(editDep);
                    tcEditDep.setCellRenderer(editDep);
                    departmentTable.addColumn(tcEditDep);
                    DefaultTableModel empModel = new DefaultTableModel(new String[]{"ID", "Name", "Surname", "Function", "Salary"}, 0);
                    for(int i = 0; i < con.employeeCount(); i++){
                        empModel.addRow(new String[]{String.valueOf(con.getEmployee(i).getId()), con.getEmployee(i).getFirstName(), con.getEmployee(i).getSecondName(), con.getEmployee(i).getFunction(), String.valueOf(con.getEmployee(i).getSalary())});
                    }
                    employeeTable.setModel(empModel);
                    TableButton delEmp = new TableButton("Delete");
                    TableColumn tcDelEmp = new TableColumn();
                    tcDelEmp.setHeaderValue("Delete");
                    delEmp.addTableButtonListener(new TableButtonListener() {
                        @Override
                        public void tableButtonClicked(int row, int col) {
                           ((DefaultTableModel) employeeTable.getModel()).removeRow(row);
                        }
                    });
                    tcDelEmp.setCellEditor(delEmp);
                    tcDelEmp.setCellRenderer(delEmp);
                    employeeTable.addColumn(tcDelEmp);
                    TableButton editEmp = new TableButton("Edit");
                    TableColumn tcEditEmp = new TableColumn();
                    tcEditEmp.setHeaderValue("Edit");
                    editEmp.addTableButtonListener(new TableButtonListener() {
                        @Override
                        public void tableButtonClicked(int row, int col) {
                            System.out.println(row + " " + col);
                            new Frame2(con.getEmployee(Integer.valueOf((String)employeeTable.getModel().getValueAt(row,0))-1));
                        }
                    });
                    tcEditEmp.setCellEditor(editEmp);
                    tcEditEmp.setCellRenderer(editEmp);
                    employeeTable.addColumn(tcEditEmp);
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
        
        //&&&&&&&&&
        this.setLayout(new BorderLayout());
        this.add(tabbedPane);  
        this.setJMenuBar(menuBar);
        this.setBounds(100,100,800,400);        
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);          
        this.setTitle("Information System");
        this.setVisible(true);
        
    }
    
    
}
