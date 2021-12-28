/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.UI;

import automobiledealer.object.Customer;
import automobiledealer.object.Employee;
import automobiledealer.object.Manager;
import automobiledealer.object.Technician;
import automobiledealer.object.Vehicle.Vehicle;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author AkbarFauzy
 */
public class Driver {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/cardealer";
    static final String USER = "root";
    static final String PASS = "";
    
    static Connection conn;
    static PreparedStatement stmt;
    static ResultSet rs; 
    
    static Employee loggedUser;
    static ArrayList<Employee> employees;
    static ArrayList<Customer> customers;
    static ArrayList<Vehicle> Vehicles;
    
    public static void main(String args[]) {
        new Login().setVisible(true);
        try{  
            Class.forName(JDBC_DRIVER);  
            conn = DriverManager.getConnection(DB_URL, USER,PASS); 
        }catch(Exception e){ 
            System.out.println(e);
        } 
  
    } 
}
