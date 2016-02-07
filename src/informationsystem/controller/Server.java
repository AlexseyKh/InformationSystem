package informationsystem.controller;



import informationsystem.model.dataClasses.Employee;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;



public class Server {
    public static final int PORT = 7777;
    private static Controller controller = new Controller();

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(PORT);
        try {
            while(true) {
                Socket s = ss.accept();
                try{
                    new ServerThread(s);
                }
                catch (IOException e) {
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
                while(true){
                    str = (String)in.readObject();
                    if(str.equals("createCS"))
                        createCS();
                    if(str.equals("createCF"))
                        createCF();
                    if(str.equals("save"))
                        save();
                    if(str.equals("addDep"))
                        addDep();
                    if(str.equals("delDepI"))
                        delDepI();
                    if(str.equals("delDepS"))
                        delDepS();
                    if(str.equals("getEmployee"))
                        getEmployee();
                    if(str.equals("getAllEmployees"))
                        getAllEmployees();
                    if(str.equals("getEmployeesOfDepartmentByName"))
                        getEmployeesOfDepartmentByName();
                    if(str.equals("getEmployeesOfDepartmentByName"))
                        getEmployeesOfDepartmentById();
                    if(str.equals("addEmployee"))
                        addEmployee();
                    if(str.equals("deleteEmployee"))
                        delEmployee();
                    if(str.equals("end")) return;

                }
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            catch (IOException e) {
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

        private void createCS() throws IOException, ClassNotFoundException {
            String companyName = (String)in.readObject();
            controller.createCompany(companyName);
            out.writeObject("complete");
        }

        private void createCF() throws IOException, ClassNotFoundException {
            String fileName = (String) in.readObject();
            controller.createCompany(fileName);
            out.writeObject("complete");
        }

        private void save() throws IOException, ClassNotFoundException {
            String fileName = (String) in.readObject();
            controller.saveCompanyToXML(fileName);
            out.writeObject("complete");
        }

        private void addDep() throws IOException, ClassNotFoundException {
            String depName = (String) in.readObject();
            controller.addDepartment(depName);
            out.writeObject("complete");
        }

        private void delDepI() throws IOException, ClassNotFoundException {
            int id = Integer.parseInt((String)in.readObject());
            controller.deleteDepartment(id);
            out.writeObject("complete");
        }

        private void delDepS() throws IOException, ClassNotFoundException {
            String depName = (String) in.readObject();
            controller.deleteDepartment(depName);
            out.writeObject("complete");
        }

        private void getEmployee() throws IOException, ClassNotFoundException {

            int id = Integer.parseInt((String)in.readObject());
            Employee emp = controller.getEmployee(id);
            out.writeObject(emp);
        }

        private void getAllEmployees() throws IOException, ClassNotFoundException {
            Employee[] emps = controller.getAllEmployees();
            out.writeObject(emps);
        }
        private void getEmployeesOfDepartmentByName() throws IOException, ClassNotFoundException {
            String name = (String) in.readObject();
            Employee[] depEmps = controller.getEmployeesOfDepartment(name);
            out.writeObject(depEmps);
        }

        private void getEmployeesOfDepartmentById() throws IOException, ClassNotFoundException {
            int id = Integer.parseInt((String)in.readObject());
            Employee[] depEmps = controller.getEmployeesOfDepartment(id);
            out.writeObject(depEmps);
        }
        private void addEmployee() throws IOException, ClassNotFoundException {
            String depName = (String)in.readObject();
            String firstName = (String)in.readObject();
            String secondName = (String)in.readObject();
            String function = (String)in.readObject();
            int salary = Integer.parseInt((String) in.readObject());
            controller.addEmployee(depName, firstName, secondName, function, salary);
            out.writeObject("complete");
        }
        private void delEmployee() throws IOException, ClassNotFoundException {
            int employeeId = Integer.parseInt((String)in.readObject());
            controller.deleteEmployee(employeeId);
            out.writeObject("complete");
        }
    }
}
