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
public class PartDAO implements ManageParts{
    
    Connection conn;
    
    public PartDAO(){
        conn=connection();
    }
    
    public List list(){
        List<Parts> part = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM `spare_part` main "
                    + "LEFT JOIN `rims` r on r.FK_partNumber = main.part_number "
                    + "LEFT JOIN `rearview_mirror` m on m.FK_partNumber = main.part_number "
                    + "LEFT JOIN `engine` e on e.FK_partNumber= main.part_number "
                    + "LEFT JOIN `tire` t on t.FK_partNumber = main.part_number;");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){  
                Parts P = null;
                if(rs.getString("rims_diameter")!=null){
                    P = new Rims(rs.getString("part_number"),rs.getString("name"),rs.getString("brand"),rs.getInt("price"),rs.getString("status"),rs.getInt("rims_diameter"));
                }else if(rs.getString("mirror_type")!=null){
                    P = new RearviewMirror(rs.getString("part_number"),rs.getString("name"),rs.getString("brand"),rs.getInt("price"), rs.getString("status"),rs.getString("mirror_type"));                    
                }else if(rs.getString("capacity")!=null){
                    P = new CarEngine(rs.getString("part_number"),rs.getString("name"),rs.getString("brand"),rs.getInt("price"), rs.getString("status"), rs.getInt("capacity"),rs.getInt("number_cylinder"));              
                }else if(rs.getString("tire_diameter")!=null){
                    P = new Tire(rs.getString("part_number"),rs.getString("name"),rs.getString("brand"),rs.getInt("price"), rs.getString("status"), rs.getInt("tire_diameter"),rs.getInt("width"),rs.getString("tire_type"));              
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
            try (PreparedStatement stmt = conn.prepareStatement("INSERT INTO spare_part(part_number, name, brand, price) VALUES(?,?,?,?)")) {
                stmt.setString(1, P.getPartsNumber());
                stmt.setString(2, P.getName());
                stmt.setString(3, P.getBrand());
                stmt.setInt(4, P.getPrice());
                
                stmt.executeUpdate();
                stmt.close();
            }
            
            if(P instanceof Rims){
                try (PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO rims(FK_partNumber,rims_diameter) VALUES(?,?)")) {
                    stmt2.setString(1,P.getPartsNumber());
                    stmt2.setInt(2, ((Rims)P).getDiameter());
                    
                    stmt2.executeUpdate();
                    stmt2.close();
                }
            }else if(P instanceof RearviewMirror){
                try (PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO rearview_mirror(FK_partNumber,mirror_type) VALUES(?,?)")) {
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
            JOptionPane.showMessageDialog(null, "Parts Berhasil Ditambahkan", "Dialog", JOptionPane.INFORMATION_MESSAGE);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void editPart(Parts P){
        try{
            try (PreparedStatement stmt = conn.prepareStatement("UPDATE spare_part SET part_number=?, name=?, brand=?, price=? WHERE part_number=?")) {
                stmt.setString(1, P.getPartsNumber());
                stmt.setString(2, P.getName());
                stmt.setString(3, P.getBrand());
                stmt.setInt(4, P.getPrice());
                stmt.setString(5, P.getPartsNumber());
                
                stmt.executeUpdate();
                stmt.close();
            }
            
            if(P instanceof Rims){
                try (PreparedStatement stmt2 = conn.prepareStatement("UPDATE rims SET FK_partNumber=?, rims_diameter=? WHERE FK_partNumber=?")) {
                    stmt2.setString(1,P.getPartsNumber());
                    stmt2.setInt(2, ((Rims)P).getDiameter());
                    stmt2.setString(3, P.getPartsNumber());
                    
                    stmt2.executeUpdate();
                }
            }else if(P instanceof RearviewMirror){
                try (PreparedStatement stmt2 = conn.prepareStatement("UPDATE rearview_mirror SET FK_partNumber=?, type=? WHERE FK_partNumber=?")) {
                    stmt2.setString(1,P.getPartsNumber());
                    stmt2.setString(2, ((RearviewMirror)P).getType());
                    stmt2.setString(3, P.getPartsNumber());
                    
                    stmt2.executeUpdate();
                }
            }else if(P instanceof CarEngine){
                try (PreparedStatement stmt2 = conn.prepareStatement("UPDATE engine SET FK_partNumber=?, capacity=?, number_cylinder=? WHERE FK_partNumber=?")) {
                    stmt2.setString(1,P.getPartsNumber());
                    stmt2.setInt(2, ((CarEngine)P).getCapacity());
                    stmt2.setInt(3, ((CarEngine)P).getNumCylinder());
                    stmt2.setString(4, P.getPartsNumber());
                    
                    stmt2.executeUpdate();
                }
            }else if(P instanceof Tire){
                try (PreparedStatement stmt2 = conn.prepareStatement("UPDATE tire SET FK_partNumber=?, tire_diameter=?,width=?, tire_type=? WHERE FK_partNumber=?")) {
                    stmt2.setString(1,P.getPartsNumber());
                    stmt2.setInt(2, ((Tire)P).getDiameter());
                    stmt2.setInt(3, ((Tire)P).getWidth());
                    stmt2.setString(4, ((Tire)P).getType());
                    stmt2.setString(5, P.getPartsNumber());
                    
                    
                    stmt2.executeUpdate();
                }
            }
            JOptionPane.showMessageDialog(null, "Parts Berhasil Diupdate", "Dialog", JOptionPane.INFORMATION_MESSAGE);
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public void deletePart(String partNumber){
        try{
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM spare_part WHERE spare_part.part_number=?");
            stmt.setString(1, partNumber);
            stmt.executeUpdate();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void editStatus(Parts P){
        try{
            PreparedStatement stmt = conn.prepareStatement("UPDATE spare_part SET status=? WHERE spare_part.part_number=?");
            stmt.setString(1, P.getStatus());
            stmt.setString(2, P.getPartsNumber());
            stmt.executeUpdate();
            stmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public List<Parts> searchPartByName(String name){
        List<Parts> part = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM `spare_part` main "
                    + "LEFT JOIN `rims` r on r.FK_partNumber = main.part_number "
                    + "LEFT JOIN `rearview_mirror` m on m.FK_partNumber = main.part_number "
                    + "LEFT JOIN `engine` e on e.FK_partNumber= main.part_number "
                    + "LEFT JOIN `tire` t on t.FK_partNumber = main.part_number WHERE main.name LIKE '%" + name + "%'");
            ResultSet rs= stmt.executeQuery();
            while(rs.next()){
                Parts P = null;
                if(rs.getString("rims_diameter")!=null){
                    P = new Rims(rs.getString("part_number"),rs.getString("name"),rs.getString("brand"),rs.getInt("price"),rs.getString("status"),rs.getInt("rims_diameter"));
                }else if(rs.getString("mirror_type")!=null){
                    P = new RearviewMirror(rs.getString("part_number"),rs.getString("name"),rs.getString("brand"),rs.getInt("price"), rs.getString("status"),rs.getString("mirror_type"));                    
                }else if(rs.getString("capacity")!=null){
                    P = new CarEngine(rs.getString("part_number"),rs.getString("name"),rs.getString("brand"),rs.getInt("price"), rs.getString("status"), rs.getInt("capacity"),rs.getInt("number_cylinder"));              
                }else if(rs.getString("tire_diameter")!=null){
                    P = new Tire(rs.getString("part_number"),rs.getString("name"),rs.getString("brand"),rs.getInt("price"), rs.getString("status"), rs.getInt("tire_diameter"),rs.getInt("width"),rs.getString("tire_type"));              
                }
                part.add(P);
            }
        }catch(SQLException e){
             JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
        return part;
    }
    
    public int countAllPart(){
        int count = 0;
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT count(*) AS total FROM spare_part WHERE spare_part.status = 'Ready'");
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
    
    public int countTire(){
        int count = 0;
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT count(*) AS total FROM tire "
                    + "LEFT JOIN spare_part on tire.FK_partNumber = spare_part.part_number WHERE spare_part.status = 'Ready'");
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
    
    public int countEngine(){
        int count = 0;
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT count(*) AS total FROM engine e "
                    + "LEFT JOIN spare_part on e.FK_partNumber = spare_part.part_number WHERE spare_part.status = 'Ready'");
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
    
     public int countRims(){
        int count = 0;
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT count(*) AS total FROM rims "
                    + "LEFT JOIN spare_part on rims.FK_partNumber = spare_part.part_number  WHERE spare_part.status = 'Ready'");
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
    
    public int countMirror(){
        int count = 0;
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT count(*) AS total FROM rearview_mirror "
                    + "LEFT JOIN spare_part on rearview_mirror.FK_partNumber = spare_part.part_number  WHERE spare_part.status = 'Ready'");
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
