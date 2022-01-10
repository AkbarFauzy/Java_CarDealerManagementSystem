/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.DAO;

import static Connection.Connection_to_db.connection;
import automobiledealer.Model.Parts.CarEngine;
import automobiledealer.Model.Parts.Parts;
import automobiledealer.Model.Parts.RearviewMirror;
import automobiledealer.Model.Parts.Rims;
import automobiledealer.Model.Parts.Tire;
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
public class PartDAO {
    
    Connection conn;
    
    public void PartDAO(){
        this.conn = connection();
    }
    
    public List list(){
        List<Parts> part = new ArrayList<>();
        try{
            PreparedStatement stmt= conn.prepareStatement("SELECT * FROM `spare_part` main "
                    + "LEFT JOIN `rims` r on r.FK_partNumber = main.partNumber "
                    + "LEFT JOIN `rearview_mirror` m on m.FK_partNumber = main.partNumber "
                    + "LEFT JOIN `engine` e on e.FK_partNumber= main.partNumber "
                    + "LEFT JOIN `tire` t on t.FK_partNumber = main.partNumber;");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Parts P = null;
                if(rs.getString("rims_id")!=null){
                    P = new Rims(rs.getString("partNumber"),rs.getString("name"),rs.getString("brand"),rs.getInt("price"),rs.getInt("rim_diameter"));
                }else if(rs.getString("rearview_mirror_id")!=null){
                    P = new RearviewMirror(rs.getString("partNumber"),rs.getString("name"),rs.getString("brand"),rs.getInt("price"),rs.getString("mirror_type"));                    
                }else if(rs.getString("engine_id")!=null){
                    P = new CarEngine(rs.getString("partNumber"),rs.getString("name"),rs.getString("brand"),rs.getInt("price"),rs.getInt("capacity"),rs.getInt("number_cylinder"));              
                }else if(rs.getString("tire_id")!=null){
                    P = new Tire(rs.getString("partNumber"),rs.getString("name"),rs.getString("brand"),rs.getInt("price"),rs.getInt("tire_diameter"),rs.getInt("width"),rs.getString("tire_type"));              
                }else{

                }
                part.add(P);
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
        return part;
    }
    
    public void addPart(Parts P){
        try{
            try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO spare_part(partNumber, name, brand, price) VALUES(?,?,?,?)")) {
                stmt.setString(1, P.getPartsNumber());
                stmt.setString(2, P.getName());
                stmt.setString(3, P.getBrand());
                stmt.setInt(4, P.getPrice());
                
                stmt.executeUpdate();
                stmt.close();
            }
            
            if(P instanceof Rims){
                try (PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO rim(FK_partNumber,rim_diameter) VALUES(?,?)")) {
                    stmt2.setString(1,P.getPartsNumber());
                    stmt2.setInt(2, ((Rims)P).getDiameter());
                    
                    stmt2.executeUpdate();
                    stmt2.close();
                }
            }else if(P instanceof RearviewMirror){
                try (PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO rearview_mirror(FK_partNumber,type) VALUES(?,?)")) {
                    stmt2.setString(1,P.getPartsNumber());
                    stmt2.setString(2, ((RearviewMirror)P).getType());
                    
                    stmt2.executeUpdate();
                    stmt2.close();
                }
            }else if(P instanceof CarEngine){
                try (PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO engine(FK_partNumber, capacity, number_cylinder) VALUES(?,?,?)")) {
                    stmt2.setString(1,P.getPartsNumber());
                    stmt2.setInt(2, ((CarEngine)P).getCapacity());
                    stmt2.setInt(3, ((CarEngine)P).getNumCylinder());
                    
                    stmt2.executeUpdate();
                    stmt2.close();
                }
            }else if(P instanceof Tire){
                try (PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO tire(FK_partNumber,tire_diameter, width,tire_type) VALUES(?,?,?,?)")) {
                    stmt2.setString(1,P.getPartsNumber());
                    stmt2.setInt(2, ((Tire)P).getDiameter());
                    stmt2.setInt(3, ((Tire)P).getWidth());
                    stmt2.setString(4, ((Tire)P).getType());
                    
                    stmt2.executeUpdate();
                    stmt2.close();
                }
            }    
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void editPart(Parts P){
        try{
            try (PreparedStatement stmt = conn.prepareStatement("UPDATE spare_part SET partNumber=?, name=?, brand=?, price=?")) {
                stmt.setString(1, P.getPartsNumber());
                stmt.setString(2, P.getName());
                stmt.setString(3, P.getBrand());
                stmt.setInt(4, P.getPrice());
                
                stmt.executeUpdate();
                stmt.close();
            }
            
            if(P instanceof Rims){
                try (PreparedStatement stmt2 = conn.prepareStatement("UPDATE rim SET FK_partNumber=?, rim_diameter=?")) {
                    stmt2.setString(1,P.getPartsNumber());
                    stmt2.setInt(2, ((Rims)P).getDiameter());
                    
                    stmt2.executeUpdate();
                }
            }else if(P instanceof RearviewMirror){
                try (PreparedStatement stmt2 = conn.prepareStatement("UPDATE rearview_mirror SET FK_partNumber=?, type=?")) {
                    stmt2.setString(1,P.getPartsNumber());
                    stmt2.setString(2, ((RearviewMirror)P).getType());
                    
                    stmt2.executeUpdate();
                }
            }else if(P instanceof CarEngine){
                try (PreparedStatement stmt2 = conn.prepareStatement("UPDATE engine SET FK_partNumber=?, capacity=?, number_cylinder=?")) {
                    stmt2.setString(1,P.getPartsNumber());
                    stmt2.setInt(2, ((CarEngine)P).getCapacity());
                    stmt2.setInt(3, ((CarEngine)P).getNumCylinder());
                    
                    stmt2.executeUpdate();
                }
            }else if(P instanceof Tire){
                try (PreparedStatement stmt2 = conn.prepareStatement("UPDATE tire SET FK_partNumber=?, tire_diameter=?,width=?, tire_type=?")) {
                    stmt2.setString(1,P.getPartsNumber());
                    stmt2.setInt(2, ((Tire)P).getDiameter());
                    stmt2.setInt(3, ((Tire)P).getWidth());
                    stmt2.setString(4, ((Tire)P).getType());
                    
                    stmt2.executeUpdate();
                }
            }
                   
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void deletePart(String partNumber){
        try{
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM spare_part WHERE spare_part.partNumber=?");
            stmt.setString(1, partNumber);
            stmt.executeUpdate();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }
    
}
