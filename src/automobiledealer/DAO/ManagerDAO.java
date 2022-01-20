/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.DAO;

import static Connection.Connection_to_db.connection;
import automobiledealer.Model.Employee;
import automobiledealer.Model.Manager;
import automobiledealer.Model.Sales;
import automobiledealer.Model.Technician;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author AkbarFauzy
 */
public class ManagerDAO implements ManageEmployee{
    Connection conn;
    
    public ManagerDAO(){
        this.conn = connection();
    }
 
    @Override
    public void addEmployee(Employee e) {
        PreparedStatement stmt = null;
        try{
            java.sql.Date sqlDate = new java.sql.Date(e.dateOfBirth().getTime());
            stmt = conn.prepareStatement("INSERT INTO employee(username, password, name, position, birth_date, gender) VALUES(?,?,?,?,?,?)");
            stmt.setString(1, e.getUsername());
            stmt.setString(2, e.getPassword());
            stmt.setString(3, e.getName());
            if(e instanceof Manager){
                stmt.setString(4, "Manager");
            }else if(e instanceof Sales){
                stmt.setString(4, "Sales");
            }else if(e instanceof Technician){
                stmt.setString(4, "Technician");
            }
            stmt.setDate(5, sqlDate);
            stmt.setString(6, e.getGender());
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Employee Berhasil Ditambahkan", "Dialog", JOptionPane.INFORMATION_MESSAGE);
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void editEmployee(Employee e) {
        PreparedStatement stmt = null;
        try{
            java.sql.Date sqlDate = new java.sql.Date(e.dateOfBirth().getTime());
            stmt = conn.prepareStatement("UPDATE employee SET username=?, password=?, name=?, position=?, birth_date=?, gender=? WHERE employee.id=?");
            stmt.setString(1, e.getUsername());
            stmt.setString(2, e.getPassword());
            stmt.setString(3, e.getName());
            if(e instanceof Manager){
                stmt.setString(4, "Manager");
            }else if(e instanceof Sales){
                stmt.setString(4, "Sales");
            }else if(e instanceof Technician){
                stmt.setString(4, "Technician");
            };
            stmt.setDate(5, sqlDate);
            stmt.setString(6, e.getGender());
            stmt.setInt(7, e.getEmployeeID());
            stmt.execute();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Employee Berhasil Diupdate", "Dialog", JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, ex,"Dialog", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void deleteEmployee(Employee e) {
        PreparedStatement stmt = null;
        String query = "DELETE FROM employee WHERE employee.id=?";
        try{
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, e.getEmployeeID());
            stmt.execute();              
            stmt.close();
        }catch(SQLException ex){
            System.err.println("Got an exception!");
            System.err.println(ex.getMessage()); 
        }
    }
    
    
}
