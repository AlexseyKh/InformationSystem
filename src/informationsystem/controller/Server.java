package informationsystem.controller;



import informationsystem.model.dataClasses.Department;
import informationsystem.model.dataClasses.Employee;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;



public class Server {
    public static final int PORT = 7777;
    private static Controller controller = new Controller();

    public static void main(String[] args) throws IOException {
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
        }
        finally{
            ss.close();
        }
    }



    private static class ServerThread extends Thread {
        private static ObjectInputStream in;
        private static ObjectOutputStream out;
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
                while(true) {
                    str = (String)in.readObject();
                    System.out.println("Command from client: "+str);
                    if (str.equals("createCS"))
                        createCS();
                    if (str.equals("createCF"))
                        createCF();
                    if (str.equals("save"))
                        save();
                    if (str.equals("addDep"))
                        addDep();
                    if (str.equals("delDepI"))
                        delDepI();
                    if (str.equals("delDepS"))
                        delDepS();
                    if (str.equals("getCompanyName"))
                        getCompanyName();
                    if (str.equals("departmentCount"))
                        departmentCount();
                    if (str.equals("getDepS"))
                        getDepS();
                    if (str.equals("getDepI"))
                        getDepI();
                    if (str.equals("getAllDepartments"))
                        getAllDepartments();
                    if (str.equals("getEmployee"))
                        getEmployee();
                    if (str.equals("getAllEmployees"))
                        getAllEmployees();
                    if (str.equals("getEmployeesOfDepartmentByName"))
                        getEmployeesOfDepartmentByName();
                    if (str.equals("getEmployeesOfDepartmentById"))
                        getEmployeesOfDepartmentById();
                    if (str.equals("addEmployee"))
                        addEmployee();
                    if (str.equals("deleteEmployee"))
                        delEmployee();
                    if (str.equals("end")) return;
                }
            }
            catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    in.close();
                    out.close();
                    s.close();
                }
                catch (IOException e) {
                    System.err.println("Socket not closed");
                }
            }
        }

        /**
         * <p>�����, ������� � �������� ����� ������ ���� ������� ��������
         * ������ �������� ������ Department ��������, ������� ����� getAllDepartments() �� ������ Controller</p>
         */
        private void getAllDepartments() throws IOException {
            System.out.println("Started method: getAllDepartments");
            Department[] deps = controller.getAllDepartments();
            out.writeObject(deps);
        }

        /**
         * <p>�����, ������� � �������� ����� �����, ��������� �� ��� id;
         * id ����������� �� �������� ������
         * ������ ������ Department ��������, ������� ����� getDepartment() �� ������ Controller, �������� - id</p>
         */
        private void getDepI() throws IOException, ClassNotFoundException {
            System.out.println("Started method: getDepI");
            long departmentId = (long)in.readObject();
            Department dep = controller.getDepartment(departmentId);
            out.writeObject(dep);
        }

        /**
         * <p>�����, ������� � �������� ���������� �������
         * ���������� ������� ��������, ������� ����� departmentCount �� ������ Controller</p>
         */
        private void departmentCount() throws IOException {
            System.out.println("Started method: departmentCount");
            out.writeObject(controller.departmentCount());
        }

        /**
         * <p>�����, ������� � �������� ����� �������� ��������
         * �������� �������� ��������, ������� ����� getCompanyName �� ������ Controller</p>
         */
        private void getCompanyName() throws IOException {
            System.out.println("Started method: getCompanyName");
            String companyName = controller.getCompanyName();
            out.writeObject(companyName);
        }

        /**
         * <p>�����, ������� � �������� ����� �����, ��������� �� ��� ��������
         * ������ ������ Department ��������, ������� ����� getDepartment �� ������ Controller, �������� - �������� ������</p>
         */
        private void getDepS() throws IOException, ClassNotFoundException {
            System.out.println("Started method: getDepS");
            String departmentName = (String)in.readObject();
            Department dep = controller.getDepartment(departmentName);
            out.writeObject(dep);
        }

        /**
         * <p>�����, ��������� �������� �� �� ��������
         * �������� ����������� �� ��������� ������
         * �������� ����� createCompany �� ������ Controller, � �������� ��������� ���������� �������� ��������</p>
         */
        private void createCS() throws IOException, ClassNotFoundException {
            System.out.println("Started method: createCS");
            String companyName = (String)in.readObject();
            controller.createCompany(companyName);
            //out.writeObject("complete");
        }

        /**
         * <p>�����, ��������� �������� �� �����
         * ���� � ����� ����������� �� �������� ������
         * �������� ����� ������CompanyFromXML �� ������ Controller, � �������� ��������� ���������� ���� � �����</p>
         */
        private void createCF() throws IOException, ClassNotFoundException {
            System.out.println("Started method: createCF");
            String fileName = (String)in.readObject();
            controller.createCompanyFromXML(fileName);
            //out.writeObject("complete");
        }

        /**
         * <p>�����, ����������� �������� � ����
         * ����� � ����� ����������� �� �������� ������
         * �������� ����� saveCompanyToXML �� ������ Controller, � �������� ��������� ���������� ���� � �����</p>
         */
        private void save() throws IOException, ClassNotFoundException {
            System.out.println("Started method: save");
            String fileName = (String)in.readObject();
            controller.saveCompanyToXML(fileName);
        }

        /**
         * <p>�����, ����������� �����
         * �������� ������ ����������� �� ��������� ������
         * �������� ����� addDepartment �� ������ Controller, � �������� ��������� ���������� �������� ��������</p>
         */
        private void addDep() throws IOException, ClassNotFoundException {
            System.out.println("Started method: addDep");
            String depName = (String)in.readObject();
            controller.addDepartment(depName);
            System.out.println("Added " + depName);
            //out.writeObject("complete");
        }

        /**
         * <p>�����, ��������� �����, ��������� �� ��� id
         * id ����������� �� �������� ������
         * �������� ����� deleteDepartment �� ������ Controller, � �������� ��������� ���������� id ��������</p>
         */
        private void delDepI() throws IOException, ClassNotFoundException {
            System.out.println("Started method: delDepI");
            long id = (long)in.readObject();
            controller.deleteDepartment(id);
            //out.writeObject("complete");
        }

        /**
         * <p>�����, ��������� �����, ��������� �� ��� ��������
         * �������� ����������� �� �������� ������
         * �������� ����� deleteDepartment �� ������ Controller, � �������� ��������� ���������� �������� ��������</p>
         */
        private void delDepS() throws IOException, ClassNotFoundException {
            System.out.println("Started method: delDepS");
            String depName = (String)in.readObject();
            controller.deleteDepartment(depName);
            //out.writeObject("complete");
        }

        /**
         * <p>�����, ������� � �������� ����� ����������, ���������� �� ��� id
         * id ����������� �� �������� ������
         * ������ ������ Employee ��������, ������� ����� getEmployee �� ������ Controller</p>
         */
        private void getEmployee() throws IOException, ClassNotFoundException {
            System.out.println("Started method: getEmployee");
            long id = (long)in.readObject();
            Employee emp = controller.getEmployee(id);
            out.writeObject(emp);
        }

        /**
         * <p>�����, ������� � �������� ������ ���� �����������
         * ������ ��������, ������� ����� getAllEmployees �� ������ Controller</p>
         */
        private void getAllEmployees() throws IOException, ClassNotFoundException {
            System.out.println("Started method: getAllEmployees");
            Employee[] emps = controller.getAllEmployees(); // ����� �� �������� �� ����������� �����������, ��� ���� �������� getAllDepartments
            out.writeObject(emps);
        }

        /**
         * <p>�����, ������� � �������� ������ ����������� ������, ���������� �� ��� ��������
         * �������� ����������� �� �������� ������
         * ������ ��������, ������� ����� getEmployeesOfDepartment �� ������ Controller</p>
         */
        private void getEmployeesOfDepartmentByName() throws IOException, ClassNotFoundException {
            System.out.println("Started method: getEmployeesOfDepartmentByName");
            String name = (String)in.readObject();
            Employee[] depEmps = controller.getEmployeesOfDepartment(name); // ����� �� �������� �� ����������� �����������
            out.writeObject(depEmps);
        }

        /**
         * <p>�����, ������� � �������� ������ ����������� ������, ���������� �� ��� id
         * id ����������� �� �������� ������
         * ������ ��������, ������� ����� getEmployeesOfDepartment �� ������ Controller</p>
         */
        private void getEmployeesOfDepartmentById() throws IOException, ClassNotFoundException {
            System.out.println("Started method: getEmployeesOfDepartmentById");
            long id = (long)in.readObject();
            Employee[] depEmps = controller.getEmployeesOfDepartment(id); // ����� �� �������� �� ����������� �����������
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
        private void addEmployee() throws IOException, ClassNotFoundException {
            System.out.println("Started method: addEmployee");
            String depName = (String)in.readObject();
            System.out.println("Got from client (depName): " +depName);
            String firstName = (String)in.readObject();
            System.out.println("Got from client (firstName): " +firstName);
            String secondName = (String)in.readObject();
            System.out.println("Got from client (secondName): " +secondName);
            String function = (String)in.readObject();
            System.out.println("Got from client (function): " +function);
            int salary = (int)in.readObject();
            System.out.println("Got from client (salary): " +salary);
            controller.addEmployee(depName, firstName, secondName, function, salary);
            //out.writeObject("complete");
        }

        /**
         * <p>�����, ��������� ���������� �� ��� id
         * id ����������� �� �������� ������;
         * �������� ����� deleteEmployee �� ������ Controller</p>
         */
        private void delEmployee() throws IOException, ClassNotFoundException {
            System.out.println("Started method: delEmployee");
            long employeeId = (long)in.readObject();
            controller.deleteEmployee(employeeId);
            //out.writeObject("complete");
        }
    }
}
