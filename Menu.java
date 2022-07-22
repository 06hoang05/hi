package menu;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.sql.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Menu {
    Connection connection = Mssql.getMssqlConnection();

    public Menu() throws SQLException {
    }

    public static void main(String[] args) throws SQLException, JAXBException, ParserConfigurationException, TransformerException, IOException {
        Scanner input = new Scanner(System.in);
        menu();
        while (true){
            System.out.println("#: ");
            int choice = input.nextInt();
            switch (choice){
                case 1->{
                    loadData();
                }
                case 2->{
                    jsonWriter();
                    System.out.println("jsonwrite thanh cong!");
                }
                case 3->{
                    writeXMl();
                    System.out.println("WriteXML thanh cong!");
                }
                case 4->{

                }
                case 5->{
                    return;
                }
            }
        }
    }

    public static void loadData() throws SQLException {
        Connection connection = getconnection();
        String query = "select * from product";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while (resultSet.next()){
            int id = resultSet.getInt(1);
            String name = resultSet.getString(2);
            String address = resultSet.getString(3);
            String mobile = resultSet.getString(4);
            System.out.println("id: " + id + " | Name: " + name + " | Address: " + address + " | Mobile: " + mobile);
        }
    }

    private static Connection getconnection() {
        return null;
    }
    public static void jsonWriter() throws IOException {

        JSONObject customer = new JSONObject();//{}
        customer.put("id",1);
        customer.put("name","Khoi");
        customer.put("email","khoi@gmail.com");
        customer.put("age",25);

        JSONObject address = new JSONObject();
        address.put("street","Hai Ba Trung");
        address.put("city","Ha Noi");
        address.put("state","HBT");

        customer.put("address",address);


        JSONArray pm = new JSONArray();//[]
        pm.addAll(Arrays.asList("Momo","Visa","Zalopay"));

        customer.put("paymentMethods",pm);

        JSONArray projects = new JSONArray();


        JSONObject p1 = new JSONObject();
        p1.put("title","Java with json application");
        p1.put("budget",5000);


        JSONObject p2 = new JSONObject();
        p2.put("title","Java with xml application");
        p2.put("budget",3000);


        JSONObject p3 = new JSONObject();
        p3.put("title","Java with xml application");
        p3.put("budget",3000);


        JSONObject p4 = new JSONObject();
        p4.put("title","Java with xml application");
        p4.put("budget",3000);


        JSONObject p5 = new JSONObject();
        p5.put("title","Java with xml application");
        p5.put("budget",3000);

        projects.addAll(Arrays.asList(p1,p2,p3,p4,p5));

        customer.put("projects",projects);


        FileWriter fileWriter = new FileWriter("student.json");
        fileWriter.write(customer.toJSONString());
        fileWriter.flush();
    }
    public static void writeXMl() throws JAXBException, ParserConfigurationException, TransformerException {
        DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        Document dom = builder.newDocument();

        Element root = dom.createElement("user");
        dom.appendChild(root);

        Attr attr = dom.createAttribute("id");
        attr.setValue("1");
        root.setAttributeNode(attr);

        Element name = dom.createElement("name");
        name.setTextContent("Hoang");
        Element email = dom.createElement("email");
        email.setTextContent("Hoang@gmail.com");
        Element phone = dom.createElement("phone");
        phone.setTextContent("0913764843");

        root.appendChild(name);
        root.appendChild(email);
        root.appendChild(phone);
        

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        transformer.transform(new DOMSource(dom), new StreamResult(new File("product.xml")));


    }
public static void findByName(String name) throws IOException {
        FileWriter reader = new FileWriter("student.json");
        studentList = new Gson().fromJson(reader, new TypeToken<List<Product>>(){}.getType());
        for (Product s : studentList){
            if (s.getName().equals(name)){
                System.out.println(s);
                break;
            }else {
                System.out.println("Not found");
            }
        }
}
    public static void menu(){
        System.out.println("=====Menu=====");
        System.out.println("1.Display student");
        System.out.println("2.Write data to Json");
        System.out.println("3.Write data to XML");
        System.out.println("4.Find student by name");
        System.out.println("5.Exit");
    }
}
