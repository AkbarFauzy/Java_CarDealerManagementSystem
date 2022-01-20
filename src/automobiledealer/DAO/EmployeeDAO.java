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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author AkbarFauzy
 */
public class EmployeeDAO {
    Connection conn;
    
    public EmployeeDAO(){
        conn = connection();
    }
    
    public List list(){
        List<Employee> employee = new ArrayList<>() ;
        PreparedStatement stmt = null;
        try{
            stmt = conn.prepareStatement("SELECT * FROM employee");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Employee E = null;
                switch(rs.getString("position")){
                    case "Manager":
                        E = new Manager(rs.getInt("id"),rs.getString("username"),rs.getString("password"), rs.getString("name"), rs.getDate("birth_date"), rs.getString("gender")); 
                        break;
                    case "Sales":
                        E = new Sales(rs.getInt("id"),rs.getString("username"), rs.getString("password"), rs.getString("name"), rs.getDate("birth_date"), rs.getString("gender"));
                        break;
                    case "Technician":
                        E = new Technician(rs.getInt("id"),rs.getString("username"), rs.getString("password"), rs.getString("name"), rs.getDate("birth_date"), rs.getString("gender"));
                        break;
                    case "Admin":
                        E = new Employee(rs.getInt("id"),rs.getString("username"),rs.getString("password"), rs.getString("name"), rs.getDate("birth_date"), rs.getString("gender"));
                        break;
                }
                employee.add(E);
            }
            stmt.close();
            rs.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
        
        return employee;
    }
    
    public int salesCount(){
        int count = 0;
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT count(*) AS total FROM employee WHERE employee.position='Sales'");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                count = rs.getInt("total");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }finally{
            return count;
        }
    }
    
    public int technicianCount(){
        int count = 0;
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT count(*) AS total FROM employee WHERE employee.position='Technician'");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                count = rs.getInt("total");
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }finally{
            return count;
        }
    }
    
}
