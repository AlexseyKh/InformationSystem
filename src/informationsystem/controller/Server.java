package informationsystem.controller;

import informationsystem.model.dataClasses.Department;
import informationsystem.model.dataClasses.Employee;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int PORT = 7777;
    private static Controller controller = new ControllerXML();

    public static void main(String[] args) throws IOException {
        controller.loadData("src\\datas\\ChocolateCorporation");
        ServerSocket ss = new ServerSocket(PORT);
        try {
            while (true) {
                Socket s = ss.accept();
                try {
                    new ServerThread(s);
                } catch (IOException e) {
                    s.close();
                }

            }
        } finally {
            ss.close();
        }
    }

    private static class ServerThread extends Thread {

        private ObjectInputStream in;
        private ObjectOutputStream out;
        Socket s;
        

        public ServerThread(Socket s) throws IOException {
            this.s = s;
            out = new ObjectOutputStream(s.getOutputStream());
            in = new ObjectInputStream(s.getInputStream());
            start();
        }

        @Override
        public void run() {
            try {
                String str;
                while (true) {
                    str = (String) in.readObject();
                    System.out.println("Command from client: " + str);
                    if (str.equals("createCS")) {
                        createCS();
                    }
                    if (str.equals("createCF")) {
                        createCF();
                    }
                    if (str.equals("save")) {
                        save();
                    }
                    if (str.equals("addDep")) {
                        addDep();
                    }
                    if (str.equals("delDepI")) {
                        delDepI();
                    }
                    if (str.equals("delDepS")) {
                        delDepS();
                    }
                    if (str.equals("getCompanyName")) {
                        getCompanyName();
                    }
                    if (str.equals("departmentCount")) {
                        departmentCount();
                    }
                    if (str.equals("getDepS"))
                    {
                        getDepS();
                    }
                    if (str.equals("getDepI"))
                    {
                        getDepI();
                    }
                    if (str.equals("getAllDepartments")) {
                        getAllDepartments();
                    }
                    if (str.equals("getEmployee")) {
                        getEmployee();
                    }
                    if (str.equals("getAllEmployees")) {
                        getAllEmployees();
                    }
                    if (str.equals("getEmployeesOfDepartmentByName")) {
                        getEmployeesOfDepartmentByName();
                    }
                    if (str.equals("getEmployeesOfDepartmentById")) {
                        getEmployeesOfDepartmentById();
                    }
                    if (str.equals("addEmployee")) {
                        addEmployee();
                    }
                    if (str.equals("deleteEmployee")) {
                        delEmployee();
                    }
                    if (str.equals("editEmployee")) {
                        editEmployee();
                    }
                    if (str.equals("editDepartment")) {
                        editDepartment();
                    }
                    if (str.equals("merge")) {
                        merge();
                    }
                    if (str.equals("end")) {
                        return;
                    }
                    //out.flush();}
                }
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    in.close();
                    out.close();
                    s.close();
                } catch (IOException e) {
                    System.err.println("Socket not closed");
                }
            }
        }

        /**
         * <p>�����, ������� � �������� ����� ������ ���� ������� ��������
         * ������ �������� ������ Department ��������, ������� ����� getDepartment() �� ������ Controller, �������� - id</p>
         */
        synchronized private void getAllDepartments() throws IOException {
            System.out.println("Started method: getAllDepartments");
            Department[] deps = controller.getAllDepartments();
            out.writeObject(deps);
        }

        /**
         * <p>�����, ������� � �������� ����� �����, ��������� �� ��� id;
         * id ����������� �� �������� ������
         * ������ ������ Department ��������, ������� ����� getDepartment() �� ������ Controller, �������� - id</p>
         */
        synchronized private void getDepI() throws IOException, ClassNotFoundException {
            System.out.println("Started method: getDepI");
            Long departmentId = (Long) in.readObject();
            Department dep = controller.getDepartment(departmentId);
            out.writeObject(dep);
        }

        /**
         * <p>�����, ������� � �������� ���������� �������
         * ���������� ������� ��������, ������� ����� departmentCount �� ������ Controller</p>
         */
        synchronized private void departmentCount() throws IOException {
            System.out.println("Started method: departmentCount");
            out.writeObject(controller.departmentCount());
        }

        /**
         * <p>�����, ������� � �������� ����� �������� ��������
         * �������� �������� ��������, ������� ����� getCompanyName �� ������ Controller</p>
         */
        synchronized private void getCompanyName() throws IOException {
            System.out.println("Started method: getCompanyName");
            String companyName = controller.getCompanyName();
            out.writeObject(companyName);
        }

        /**
         * <p>�����, ������� � �������� ����� �����, ��������� �� ��� ��������
         * ������ ������ Department ��������, ������� ����� getDepartment �� ������ Controller, �������� - �������� ������</p>
         */
        synchronized private void getDepS() throws IOException, ClassNotFoundException {
            System.out.println("Started method: getDepS");
            String departmentName = (String) in.readObject();
            System.out.println("Get department: " + departmentName);
            System.out.println("First dep - " + controller.getDepartment(0).getName());
            Department dep = controller.getDepartment(departmentName);
            System.out.println("Out department: " + dep.getName());
            out.writeObject(dep);
        }

        /**
         * <p>�����, ��������� �������� �� �� ��������
         * �������� ����������� �� ��������� ������
         * �������� ����� createCompany �� ������ Controller, � �������� ��������� ���������� �������� ��������</p>
         */
        synchronized private void createCS() throws IOException, ClassNotFoundException {
            System.out.println("Started method: createCS");
            String companyName = (String) in.readObject();
            controller.createCompany(companyName);
            //out.writeObject("complete");
        }

        /**
         * <p>�����, ��������� �������� �� �����
         * ���� � ����� ����������� �� �������� ������
         * �������� ����� ������CompanyFromXML �� ������ Controller, � �������� ��������� ���������� ���� � �����</p>
         */
        synchronized private void createCF() throws IOException, ClassNotFoundException {
            System.out.println("Started method: createCF");
            String fileName = (String) in.readObject();
            controller.loadData(fileName);
            //out.writeObject("complete");
        }

        /**
         * <p>�����, ����������� �������� � ����
         * ����� � ����� ����������� �� �������� ������
         * �������� ����� saveCompanyToXML �� ������ Controller, � �������� ��������� ���������� ���� � �����</p>
         */
        synchronized private void save() throws IOException, ClassNotFoundException {
            System.out.println("Started method: save");
            String fileName = (String) in.readObject();
            controller.saveData(fileName);
        }

        /**
         * <p>�����, ����������� �����
         * �������� ������ ����������� �� ��������� ������
         * �������� ����� addDepartment �� ������ Controller, � �������� ��������� ���������� �������� ��������</p>
         */
        synchronized private void addDep() throws IOException, ClassNotFoundException {
            System.out.println("Started method: addDep");
            String depName = (String) in.readObject();
            out.writeObject(controller.addDepartment(depName));
            System.out.println("Added " + depName);
            System.out.println("End method: addDep");
            //out.writeObject("complete");
        }

        /**
         * <p>�����, ��������� �����, ��������� �� ��� id
         * id ����������� �� �������� ������
         * �������� ����� deleteDepartment �� ������ Controller, � �������� ��������� ���������� id ��������</p>
         */
        synchronized private void delDepI() throws IOException, ClassNotFoundException {
            System.out.println("Started method: delDepI");
            long id = (long) in.readObject();
            out.writeObject(controller.deleteDepartment(id));
            //out.writeObject("complete");
        }

        /**
         * <p>�����, ��������� �����, ��������� �� ��� ��������
         * �������� ����������� �� �������� ������
         * �������� ����� deleteDepartment �� ������ Controller, � �������� ��������� ���������� �������� ��������</p>
         */
        synchronized private void delDepS() throws IOException, ClassNotFoundException {
            System.out.println("Started method: delDepS");
            String depName = (String) in.readObject();
            out.writeObject(controller.deleteDepartment(depName));
            //out.writeObject("complete");
        }

        /**
         * <p>�����, ������� � �������� ����� ����������, ���������� �� ��� id
         * id ����������� �� �������� ������
         * ������ ������ Employee ��������, ������� ����� getEmployee �� ������ Controller</p>
         */
        synchronized private void getEmployee() throws IOException, ClassNotFoundException {
            System.out.println("Started method: getEmployee");
            long id = (long) in.readObject();
            Employee emp = controller.getEmployee(id);
            out.writeObject(emp);
        }

        /**
         * <p>�����, ������� � �������� ������ ���� �����������
         * ������ ��������, ������� ����� getAllEmployees �� ������ Controller</p>
         */
        synchronized private void getAllEmployees() throws IOException, ClassNotFoundException {
            System.out.println("Started method: getAllEmployees");
            Employee[] emps = controller.getAllEmployees();
            out.writeObject(emps);
            System.out.println("End method: getAllEmployees");
        }

        /**
         * <p>�����, ������� � �������� ������ ����������� ������, ���������� �� ��� ��������
         * �������� ����������� �� �������� ������
         * ������ ��������, ������� ����� getEmployeesOfDepartment �� ������ Controller</p>
         */
        synchronized private void getEmployeesOfDepartmentByName() throws IOException, ClassNotFoundException {
            System.out.println("Started method: getEmployeesOfDepartmentByName");
            String name = (String) in.readObject();
            Employee[] depEmps = controller.getEmployeesOfDepartment(name);
            out.writeObject(depEmps);
        }

        /**
         * <p>�����, ������� � �������� ������ ����������� ������, ���������� �� ��� id
         * id ����������� �� �������� ������
         * ������ ��������, ������� ����� getEmployeesOfDepartment �� ������ Controller</p>
         */
        synchronized private void getEmployeesOfDepartmentById() throws IOException, ClassNotFoundException {
            System.out.println("Started method: getEmployeesOfDepartmentById");
            long id = (long) in.readObject();
            Employee[] depEmps = controller.getEmployeesOfDepartment(id);
            out.writeObject(depEmps);
        }

        /**
         * <p>�����, ����������� ����������
         * �� �������� ������ ����������� ����������� ���������:
         * depName - ��� ������, � ������� ����������� ��������� (��� String);
         * firstName - ��� ���������� (��� String);
         * secondName - ������� (��� String);
         * function - ��������� ���������� (��� String);
         * salary - �������� ���������� (��� int);
         * �������� ����� addEmployee �� ������ Controller</p>
         */
        synchronized private void addEmployee() throws IOException, ClassNotFoundException {
            System.out.println("Started method: addEmployee");
            String depName = (String) in.readObject();
            System.out.println("Got from client (depName): " + depName);
            String firstName = (String) in.readObject();
            System.out.println("Got from client (firstName): " + firstName);
            String lastName = (String) in.readObject();
            System.out.println("Got from client (lastName): " + lastName);
            String function = (String) in.readObject();
            System.out.println("Got from client (function): " + function);
            int salary = (Integer) in.readObject();
            System.out.println("Got from client (salary): " + salary);
            out.writeObject(controller.addEmployee(depName, firstName, lastName, function, salary));
            //out.writeObject("complete");
        }

        /**
         * <p>�����, ��������� ���������� �� ��� id
         * id ����������� �� �������� ������;
         * �������� ����� deleteEmployee �� ������ Controller</p>
         */
        synchronized private void delEmployee() throws IOException, ClassNotFoundException {
            System.out.println("Started method: delEmployee");
            long employeeId = (long) in.readObject();
            out.writeObject(controller.deleteEmployee(employeeId));
            //out.writeObject("complete");
        }

        /**
         * <p>����� ��������� ����������
         * �� �������� ������ ����������� ����������� ���������:
         * depName - ��� ������, � ������� ����������� ��������� (��� String);
         * firstName - ��� ���������� (��� String);
         * secondName - ������� (��� String);
         * function - ��������� ���������� (��� String);
         * salary - �������� ���������� (��� int);
         * �������� ����� addEmployee �� ������ Controller</p>
         */
        synchronized private void editEmployee() throws IOException, ClassNotFoundException {
            System.out.println("Started method: editEmployee");
            long employeeId = (long) in.readObject();
            System.out.println("Got from client (employeeId): " + employeeId);
            String depName = (String) in.readObject();
            System.out.println("Got from client (depName): " + depName);
            String firstName = (String) in.readObject();
            System.out.println("Got from client (firstName): " + firstName);
            String lastName = (String) in.readObject();
            System.out.println("Got from client (lastName): " + lastName);
            String function = (String) in.readObject();
            System.out.println("Got from client (function): " + function);
            int salary = (Integer) in.readObject();
            System.out.println("Got from client (salary): " + salary);
            out.writeObject(controller.editEmployee(employeeId, depName, firstName, lastName, function, salary));
        }

        /**
         * <p>�����, ��������� ������
         * �� �������� ������ ����������� ����������� ���������:
         * depId - id ������ (��� long)
         * depName - �������� ������ (��� String)
         * directorId - �������� ���������� (��� long);
         * �������� ����� addEmployee �� ������ Controller</p>
         */
        synchronized private void editDepartment() throws IOException, ClassNotFoundException {
            System.out.println("Started method: editDepartment");
            long departmentId = (long) in.readObject();
            System.out.println("Got from client (depId): " + departmentId);
            String depName = (String) in.readObject();
            System.out.println("Got from client (depName): " + depName);
            long directorId = (long) in.readObject();
            System.out.println("Got from client (directorId): " + directorId);
            out.writeObject(controller.editDepartment(departmentId, depName, directorId));
        }
        synchronized private void merge() throws IOException, ClassNotFoundException {
            System.out.println("Started method: merge");
            String fileName = (String) in.readObject();  
            controller.merge(fileName);
        }
    }
}