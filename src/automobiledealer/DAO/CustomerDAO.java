/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.DAO;

import static Connection.Connection_to_db.connection;
import automobiledealer.Model.Customer;
import java.awt.HeadlessException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author AkbarFauzy
 */
public class CustomerDAO {
   Connection conn;
    
   public CustomerDAO(){
       conn=connection();
   }
   
    public List list(){
        List<Customer> customer = new ArrayList<>();
        PreparedStatement stmt = null;
        try{
            stmt = conn.prepareStatement("SELECT * FROM customer");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Customer C = new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("address"), rs.getString("phone_number"), rs.getString("Gender"));
                customer.add(C);
            }
            
            stmt.close();
            rs.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
        return customer;
    }
    
    public void addCustomer(Customer _customer){
        try{
            System.out.print(_customer.getName());
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO customer(name,address,phone_number,gender) VALUES(?,?,?,?)");
            stmt.setString(1, _customer.getName());
            stmt.setString(2, _customer.getAddress());
            stmt.setString(3, _customer.getPhoneNumber());
            stmt.setString(4, _customer.getGender());
            stmt.executeUpdate();
            stmt.close();

            JOptionPane.showMessageDialog(null, "Customer Berhasil di Tambahkan");
            }catch(Exception e){
                JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
            }
    }
    
    public void editCustomer(Customer _customer){      
        PreparedStatement stmt;
        
        try{
            stmt = conn.prepareStatement("UPDATE customer SET name=?, address=?,phone_number=?,gender=? WHERE customer.id=?");
            stmt.setString(1, _customer.getName());
            stmt.setString(2, _customer.getAddress());
            stmt.setString(3, _customer.getPhoneNumber());
            stmt.setString(4, _customer.getGender());
            stmt.setInt(5, _customer.getId());
            stmt.executeUpdate();
            stmt.close();
            
            JOptionPane.showMessageDialog(null, "Customer Berhasil di Update");
            
        }catch(HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void deleteCustomer(Customer C){
        try{
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM customer WHERE customer.id=?");
            stmt.setInt(1, C.getId());
            stmt.executeUpdate();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
