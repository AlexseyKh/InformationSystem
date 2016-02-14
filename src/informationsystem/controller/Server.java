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
         * <p>Метод, пишущий в выходной поток массив всех отделов компании
         * Массив объектов класса Department получаем, вызывая метод getAllDepartments() из класса Controller</p>
         */
        private void getAllDepartments() throws IOException {
            System.out.println("Started method: getAllDepartments");
            Department[] deps = controller.getAllDepartments();
            out.writeObject(deps);
        }

        /**
         * <p>Метод, пишущий в выходной поток отдел, выбранный по его id;
         * id считывается из входного потока
         * Объект класса Department получаем, вызывая метод getDepartment() из класса Controller, параметр - id</p>
         */
        private void getDepI() throws IOException, ClassNotFoundException {
            System.out.println("Started method: getDepI");
            long departmentId = (long)in.readObject();
            Department dep = controller.getDepartment(departmentId);
            out.writeObject(dep);
        }

        /**
         * <p>Метод, пишущий в выходной количество отделов
         * Количество отделов получаем, вызывая метод departmentCount из класса Controller</p>
         */
        private void departmentCount() throws IOException {
            System.out.println("Started method: departmentCount");
            out.writeObject(controller.departmentCount());
        }

        /**
         * <p>Метод, пишущий в выходной поток название компании
         * Название компании получаем, вызывая метод getCompanyName из класса Controller</p>
         */
        private void getCompanyName() throws IOException {
            System.out.println("Started method: getCompanyName");
            String companyName = controller.getCompanyName();
            out.writeObject(companyName);
        }

        /**
         * <p>Метод, пишущий в выходной поток отдел, выбранный по его названию
         * Объект класса Department получаем, вызывая метод getDepartment из класса Controller, параметр - название отдела</p>
         */
        private void getDepS() throws IOException, ClassNotFoundException {
            System.out.println("Started method: getDepS");
            String departmentName = (String)in.readObject();
            Department dep = controller.getDepartment(departmentName);
            out.writeObject(dep);
        }

        /**
         * <p>Метод, создающий компанию по ее названию
         * Название считывается из входящего потока
         * Вызывает метод createCompany из класса Controller, в качестве параметра передается название компании</p>
         */
        private void createCS() throws IOException, ClassNotFoundException {
            System.out.println("Started method: createCS");
            String companyName = (String)in.readObject();
            controller.createCompany(companyName);
            //out.writeObject("complete");
        }

        /**
         * <p>Метод, создающий компанию из файла
         * Путь к файлу считывается из входного потока
         * Вызывает метод скуфеуCompanyFromXML из класса Controller, в качестве параметра передается путь к файлу</p>
         */
        private void createCF() throws IOException, ClassNotFoundException {
            System.out.println("Started method: createCF");
            String fileName = (String)in.readObject();
            controller.createCompanyFromXML(fileName);
            //out.writeObject("complete");
        }

        /**
         * <p>Метод, сохраняющий компанию в файл
         * Пусть к файлу считывается из входного потока
         * Вызывает метод saveCompanyToXML из класса Controller, в качестве параметра передается путь к файлу</p>
         */
        private void save() throws IOException, ClassNotFoundException {
            System.out.println("Started method: save");
            String fileName = (String)in.readObject();
            controller.saveCompanyToXML(fileName);
        }

        /**
         * <p>Метод, добавляющий отдел
         * Название отдела считывается из входящего потока
         * Вызывает метод addDepartment из класса Controller, в качестве параметра передается название компании</p>
         */
        private void addDep() throws IOException, ClassNotFoundException {
            System.out.println("Started method: addDep");
            String depName = (String)in.readObject();
            controller.addDepartment(depName);
            System.out.println("Added " + depName);
            //out.writeObject("complete");
        }

        /**
         * <p>Метод, удаляющий отдел, выбранный по его id
         * id считывается из входного потока
         * Вызывает метод deleteDepartment из класса Controller, в качестве параметра передается id компании</p>
         */
        private void delDepI() throws IOException, ClassNotFoundException {
            System.out.println("Started method: delDepI");
            long id = (long)in.readObject();
            controller.deleteDepartment(id);
            //out.writeObject("complete");
        }

        /**
         * <p>Метод, удаляющий отдел, выбранный по его названию
         * Название считывается из входного потока
         * Вызывает метод deleteDepartment из класса Controller, в качестве параметра передается название компании</p>
         */
        private void delDepS() throws IOException, ClassNotFoundException {
            System.out.println("Started method: delDepS");
            String depName = (String)in.readObject();
            controller.deleteDepartment(depName);
            //out.writeObject("complete");
        }

        /**
         * <p>Метод, пищущий в выходной поток сотрудника, выбранного по его id
         * id считывается из входного потока
         * Объект класса Employee получаем, вызывая метод getEmployee из класса Controller</p>
         */
        private void getEmployee() throws IOException, ClassNotFoundException {
            System.out.println("Started method: getEmployee");
            long id = (long)in.readObject();
            Employee emp = controller.getEmployee(id);
            out.writeObject(emp);
        }

        /**
         * <p>Метод, пищущий в выходной массив всех сотрудников
         * Массив получаем, вызывая метод getAllEmployees из класса Controller</p>
         */
        private void getAllEmployees() throws IOException, ClassNotFoundException {
            System.out.println("Started method: getAllEmployees");
            Employee[] emps = controller.getAllEmployees(); // пїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ, пїЅпїЅпїЅ пїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ getAllDepartments
            out.writeObject(emps);
        }

        /**
         * <p>Метод, пищущий в выходной массив сотрудников отдела, выбранного по его названию
         * Название считывается из входного потока
         * Массив получаем, вызывая метод getEmployeesOfDepartment из класса Controller</p>
         */
        private void getEmployeesOfDepartmentByName() throws IOException, ClassNotFoundException {
            System.out.println("Started method: getEmployeesOfDepartmentByName");
            String name = (String)in.readObject();
            Employee[] depEmps = controller.getEmployeesOfDepartment(name); // пїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
            out.writeObject(depEmps);
        }

        /**
         * <p>Метод, пищущий в выходной массив сотрудников отдела, выбранного по его id
         * id считывается из входного потока
         * Массив получаем, вызывая метод getEmployeesOfDepartment из класса Controller</p>
         */
        private void getEmployeesOfDepartmentById() throws IOException, ClassNotFoundException {
            System.out.println("Started method: getEmployeesOfDepartmentById");
            long id = (long)in.readObject();
            Employee[] depEmps = controller.getEmployeesOfDepartment(id); // пїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ пїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅпїЅ
            out.writeObject(depEmps);
        }

        /**
         * <p>Метод, добавляющий сотрудника
         * Из входного потока считываются необходимые параметры:
         * depName - имя отдела, в который добавляется сотрудник (тип String);
         * firstName - имя сотрудника (тип String);
         * secondName - фамилия (тип String);
         * function - должность сотрудника (тип String);
         * salary - зарплата сотрудника (тип int);
         * Вызывает метод addEmployee из класса Controller</p>
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
         * <p>Метод, удаляющий сотрудника по его id
         * id считывается из входного потока;
         * Вызывает метод deleteEmployee из класса Controller</p>
         */
        private void delEmployee() throws IOException, ClassNotFoundException {
            System.out.println("Started method: delEmployee");
            long employeeId = (long)in.readObject();
            controller.deleteEmployee(employeeId);
            //out.writeObject("complete");
        }
    }
}
