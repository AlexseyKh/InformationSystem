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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
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
    JFrame thisFrame;
    public static ObjectOutputStream objOut;
    public static ObjectInputStream objIn;
    public static final int PORT = 7777;

    long currdep = -1;

    public FrameTable() throws IOException {
        //Инициализация
        InetAddress addr = InetAddress.getLocalHost();
        Socket s = new Socket(addr, PORT);
        objOut = new ObjectOutputStream(s.getOutputStream());
        objIn = new ObjectInputStream(s.getInputStream());
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
        thisFrame = this;

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
                    try {
                        objOut.writeObject("addDep");
                        objOut.writeObject(s);
                        createDepartmentTable();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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
                JFrame fr = new AddEmployeeFrame(objOut, objIn, currdep,(FrameTable)thisFrame);
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
                //con = new Controller();
                //con.createCompanyFromXML(f.getSelectedFile().getPath());
                try {
                    objOut.writeObject("createCF");
                    objOut.writeObject(f.getSelectedFile().getPath());
                    createDepartmentTable();
                    createEmployeeTable(-1);
                }
                catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        });
        companyDownItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                JFileChooser f = new JFileChooser("Save as..");
                f.showSaveDialog(null);
                //con.saveCompanyToXML(f.getSelectedFile().getPath());
                try {
                    objOut.writeObject("save");
                    objOut.writeObject(f.getSelectedFile().getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        companyCreate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String s = JOptionPane.showInputDialog("Enter a name for company");
                if(!"".equals(s)){
                //con = new Controller();
                //con.createCompany(s);
                    try {
                        objOut.writeObject("createCS");
                        objOut.writeObject(s);
                        createEmployeeTable(-1);
                        createDepartmentTable();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

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
    
    public  void createEmployeeTable(long id){
        DefaultTableModel empModel = new DefaultTableModel(new String[]{"ID", "Name", "Surname", "Function", "Salary"}, 0);
        Employee[] allEmps = null;
        Employee[] empsOfDep = null;
        try {
            objOut.writeObject("getAllEmployees");
            allEmps = (Employee[]) objIn.readObject();
            objOut.writeObject("getEmployeesOfDepartmentById");
            objOut.writeObject(id);
            empsOfDep = (Employee[]) objIn.readObject();
            Employee[] emps = (id == -1) ? allEmps : empsOfDep;
            for (Employee emp : emps) {
                empModel.addRow(new String[]{String.valueOf(emp.getId()), emp.getFirstName(), emp.getLastName(), emp.getFunction(), String.valueOf(emp.getSalary())});
            }
            employeeTable.setModel(empModel);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Employee[] emps = (id == -1)? con.getAllEmployees() : con.getEmployeesOfDepartment(id);


        TableButton delEmp = new TableButton("Delete");
        TableColumn tcDelEmp = new TableColumn();
        tcDelEmp.setHeaderValue("Delete");
        delEmp.addTableButtonListener(new TableButtonListener() {
            @Override
            public void tableButtonClicked(int row, int col) {
                long id = Long.valueOf((String) employeeTable.getModel().getValueAt(row, 0));
                //con.deleteEmployee(id);
                //((DefaultTableModel) employeeTable.getModel()).removeRow(row);
                try {
                    objOut.writeObject("deleteEmployee");
                    objOut.writeObject(id);
                    ((DefaultTableModel) employeeTable.getModel()).removeRow(row);
                } catch (IOException e) {
                    e.printStackTrace();
                }

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
                long id = Long.valueOf((String) employeeTable.getModel().getValueAt(row, 0));
                Employee emp = null;
                try {
                    objOut.writeObject("getEmployee");
                    objOut.writeObject(id);
                    emp = (Employee)objIn.readObject();
                } catch (IOException | ClassNotFoundException e1) {
                    e1.printStackTrace();
                }
                new EmployeeEditFrame(emp, (FrameTable) thisFrame);
                //new EmployeeEditFrame(con.getEmployee(id), (FrameTable) thisFrame);
            }
        });
        tcEditEmp.setCellEditor(editEmp);
        tcEditEmp.setCellRenderer(editEmp);
        employeeTable.addColumn(tcEditEmp);
    }
    
    public  void createDepartmentTable(){
        DefaultTableModel depModel = new DefaultTableModel(new String[]{"ID","Name", "Director ID"}, 0);
        //Department[] deps = con.getAllDepartments();
        Department[] deps = null;
        try {
            objOut.writeObject("getAllDepartments");
            deps = (Department[])objIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
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
                //con.deleteDepartment(Long.valueOf((String)departmentTable.getModel().getValueAt(row, 0)));
                //((DefaultTableModel) departmentTable.getModel()).removeRow(row);
                try {
                    objOut.writeObject("deleteDepS");
                    objOut.writeObject(Long.valueOf((String)departmentTable.getModel().getValueAt(row, 0)));
                    ((DefaultTableModel) departmentTable.getModel()).removeRow(row);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
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
                //Department d = con.getDepartment((String) departmentTable.getModel().getValueAt(row, 1));
                //new EditDepartmentFrame(d, (FrameTable) thisFrame);
                try {
                    objOut.writeObject("getDepS");
                    Department d = (Department)objIn.readObject();
                    new EditDepartmentFrame(d, (FrameTable) thisFrame);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    System.out.println("p");
                }


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
