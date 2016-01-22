/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package informationsystem;

import informationsystem.controller.Controller;
import informationsystem.exceptions.DepartmentWithSuchNameDoesNotExist;
import informationsystem.exceptions.UncorrectXML;
import informationsystem.model.dataClasses.Department;
import informationsystem.model.dataClasses.Employee;
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
public class FrameTable extends JFrame {

    private static final String DEPARTMENT = "Departments";
    private static final String EMPLOYEE = "Employeers";
    JPanel departmentPanel;
    JPanel chgDepartmentPanel;
    JPanel employeePanel;
    JPanel chgEmployeePanel;
    JTabbedPane tabbedPane;
    JTable departmentTable;
    JTable employeeTable;
    JMenuBar menuBar;
    JMenu menuFile;
    JMenuItem companyCreate;
    JMenuItem companyUpItem;
    JMenuItem companyDownItem;
    Controller con;
    JButton addDep;
    JButton addEmp;
    JButton viewAllEmp;
    
    long currdep = -1;

    public FrameTable() {
        //Инициализация      
        departmentPanel = new JPanel(new GridBagLayout());
        chgDepartmentPanel = new JPanel(new GridLayout(1, 3));
        employeePanel = new JPanel(new GridBagLayout());
        chgEmployeePanel = new JPanel(new GridLayout(1, 3));
        tabbedPane = new JTabbedPane();
        departmentTable = new JTable();
        departmentTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        employeeTable = new JTable();
        employeeTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        menuBar = new JMenuBar();
        menuFile = new JMenu("File");
        companyCreate = new JMenuItem("Create");
        companyUpItem = new JMenuItem("Import XML..");
        companyDownItem = new JMenuItem("Export XML..");

        //Компановка
        menuFile.add(companyCreate);
        menuFile.add(companyUpItem);
        menuFile.add(companyDownItem);        
        menuBar.add(menuFile);
        JScrollPane jspD = new JScrollPane(departmentTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        JScrollPane jspE = new JScrollPane(employeeTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        GridBagConstraints table = new GridBagConstraints();
        //table.anchor = GridBagConstraints.PAGE_START;
        table.fill = GridBagConstraints.BOTH;
        table.gridx = 0;
        table.gridy = 0;
        table.gridheight = GridBagConstraints.RELATIVE;
        table.gridwidth = GridBagConstraints.REMAINDER;
        table.insets = new Insets(-40, 0, 0, 0);
        table.ipadx = 800;
        table.ipady = 250;
        departmentPanel.add(jspD, table);
        addDep = new JButton("Add Department");
        addDep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String s = JOptionPane.showInputDialog("Enter a name for department");
                if(!"".equals(s)){
                con = new Controller();
                con.addDepartment(s);
                createDepartmentTable();
                } else {JOptionPane.showMessageDialog(rootPane, "Error name!"); }
            }
        });
        chgDepartmentPanel.add(addDep);
        departmentPanel.add(chgDepartmentPanel);
        employeePanel.add(jspE, table);
        addEmp = new JButton("Add Employee");
        addEmp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

            }
        });
        addEmp.setEnabled(false);
        viewAllEmp = new JButton("View All Employee");
        viewAllEmp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                addEmp.setEnabled(false);
                createEmployeeTable(-1);
            }
        });
        chgEmployeePanel.add(addEmp);
        chgEmployeePanel.add(viewAllEmp);
        employeePanel.add(chgEmployeePanel);

        tabbedPane.addTab(DEPARTMENT, departmentPanel);
        tabbedPane.addTab(EMPLOYEE, employeePanel);
        //События
        companyUpItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                boolean res = false;
                JFileChooser f = new JFileChooser();
                f.setMultiSelectionEnabled(false);
                f.showDialog(null, "Open ");
                con = new Controller();
                con.createCompanyFromXML(f.getSelectedFile().getPath());
                createDepartmentTable();
                createEmployeeTable(-1);
                
            }
        });
        companyDownItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser f = new JFileChooser("Save as..");
                f.showSaveDialog(null);
                con.saveCompanyToXML(f.getSelectedFile().getPath());

            }
        });
        companyCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String s = JOptionPane.showInputDialog("Enter a name for company");
                if(!"".equals(s)){
                con = new Controller();
                con.createCompany(s);
                createEmployeeTable(-1);
                createDepartmentTable();
                }
                else{
                    JOptionPane.showMessageDialog(rootPane, "Error name");
                }
            }
        });

        //&&&&&&&&&
        this.setLayout(new BorderLayout());
        this.add(tabbedPane);
        this.setJMenuBar(menuBar);
        this.setBounds(100, 100, 800, 400);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Information System");
        this.setVisible(true);

    }
    
    public void createEmployeeTable(long id){
        DefaultTableModel empModel = new DefaultTableModel(new String[]{"ID", "Name", "Surname", "Function", "Salary"}, 0); 
        Employee[] emps = (id == -1)? con.getAllEmployees() : con.getEmployeesOfDepartment(id);
        for (Employee emp : emps) {
            empModel.addRow(new String[]{String.valueOf(emp.getId()), emp.getFirstName(), emp.getLastName(), emp.getFunction(), String.valueOf(emp.getSalary())});
        }
        employeeTable.setModel(empModel);
        TableButton delEmp = new TableButton("Delete");
        TableColumn tcDelEmp = new TableColumn();
        tcDelEmp.setHeaderValue("Delete");
        delEmp.addTableButtonListener(new TableButtonListener() {
            @Override
            public void tableButtonClicked(int row, int col) {
                long id = Long.valueOf((String) employeeTable.getModel().getValueAt(row, 0));
                con.deleteEmployee(id);
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
                //new Frame2(con.getEmployee( - 1));
            }
        });
        tcEditEmp.setCellEditor(editEmp);
        tcEditEmp.setCellRenderer(editEmp);
        employeeTable.addColumn(tcEditEmp);
    }
    
    public void createDepartmentTable(){
        DefaultTableModel depModel = new DefaultTableModel(new String[]{"ID","Name", "Director ID"}, 0);
        Department[] deps = con.getAllDepartments();
        for (Department dep : deps) {
            depModel.addRow(new String[]{String.valueOf(dep.getId()), String.valueOf(dep.getName()), String.valueOf(dep.getDirectorId())});
        }
        departmentTable.setModel(depModel);
        TableButton delDep = new TableButton("Delete");
        TableColumn tcDelDep = new TableColumn();
        tcDelDep.setHeaderValue("Delete");
        delDep.addTableButtonListener(new TableButtonListener() {
            @Override
            public void tableButtonClicked(int row, int col) {
                con.deleteDepartment(Long.valueOf((String)departmentTable.getModel().getValueAt(row, 0)));
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
                //new Frame2(con.getDepartment((String) departmentTable.getModel().getValueAt(row, 0)));

            }
        });
        tcEditDep.setCellEditor(editDep);
        tcEditDep.setCellRenderer(editDep);
        departmentTable.addColumn(tcEditDep);
        TableButton lookDep = new TableButton("Look");
        TableColumn tcLookDep = new TableColumn();
        tcLookDep.setHeaderValue("Look");
        lookDep.addTableButtonListener(new TableButtonListener() {
            @Override
            public void tableButtonClicked(int row, int col) {
                addEmp.setEnabled(true);
                currdep = Long.valueOf((String)departmentTable.getModel().getValueAt(row, 0));
                createEmployeeTable(currdep);
            }
        });
        tcLookDep.setCellEditor(lookDep);
        tcLookDep.setCellRenderer(lookDep);
        departmentTable.addColumn(tcLookDep);
    }

}
