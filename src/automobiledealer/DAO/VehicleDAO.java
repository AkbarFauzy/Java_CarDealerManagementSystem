/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.DAO;

import static Connection.Connection_to_db.connection;
import automobiledealer.Model.Vehicle.Car;
import automobiledealer.Model.Vehicle.CarModel;
import automobiledealer.Model.Vehicle.Truck;
import automobiledealer.Model.Vehicle.Vehicle;
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
public class VehicleDAO implements ManageVehicle{
    Connection conn;
    
    public VehicleDAO(){
        conn = connection();
    }
    
    @Override
    public List list(){
        List<Vehicle> vehicle = new ArrayList<>();
        try{
            PreparedStatement stmt  = conn.prepareStatement("SELECT * FROM vehicle");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Vehicle V = null;
                if(rs.getObject("load_capacity", int.class) == null){
                    V = new Car(rs.getString("register_number"),
                            rs.getString("name"), 
                            rs.getString("brand"), 
                            rs.getString("color"),
                            rs.getInt("number_wheel"),
                            rs.getDouble("weight"),
                            rs.getInt("number_doors"),
                            rs.getString("transmission"),
                            rs.getInt("price"),
                            rs.getString("fuel_type"),
                            rs.getInt("horse_power"),
                            rs.getString("status"),
                            CarModel.valueOf(rs.getString("model"))
                    );
                }
                else{
                    V = new Truck(rs.getString("register_number"),
                            rs.getString("name"), 
                            rs.getString("brand"), 
                            rs.getString("color"),
                            rs.getInt("number_wheel"),
                            rs.getDouble("weight"),
                            rs.getInt("number_doors"),
                            rs.getString("transmission"),
                            rs.getInt("price"),
                            rs.getString("fuel_type"),
                            rs.getInt("horse_power"),
                            rs.getString("status"),
                            rs.getInt("load_capacity")
                    );
                }
                vehicle.add(V);
            }
            stmt.close();
            rs.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
        
        return vehicle;
    }
    
    @Override
    public void addVehicle(Car V){
        try{
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO "
                    + "vehicle(register_number,name, brand, color, number_wheel, weight, number_doors, transmission, price, fuel_type, horse_power, model) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, V.getRegistrationNumber());
            stmt.setString(2, V.getName());
            stmt.setString(3, V.getBrand());
            stmt.setString(4, V.getColor());
            stmt.setInt(5, V.getNumWheel());
            stmt.setDouble(6,V.getWeight());
            stmt.setInt(7, V.getNumDoors());
            stmt.setString(8, V.getTransmission());
            stmt.setInt(9, V.getPrice());
            stmt.setString(10, V.getFuelType());
            stmt.setInt(11, V.getHorsePower());
            stmt.setString(12, V.getCarModel().toString());
            stmt.executeUpdate();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Vehicle Berhasil di Tambahkan");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void addVehicle(Truck V){
        try{
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO "
                    + "vehicle(register_number,name, brand, color, number_wheel, weight,number_doors, transmission, price, fuel_type, horse_power, load_capacity) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, V.getRegistrationNumber());
            stmt.setString(2, V.getName());
            stmt.setString(3, V.getBrand());
            stmt.setString(4, V.getColor());
            stmt.setInt(5, V.getNumWheel());
            stmt.setDouble(6, V.getWeight());
            stmt.setInt(7, V.getNumDoors());
            stmt.setString(8, V.getTransmission());
            stmt.setInt(9, V.getPrice());
            stmt.setString(10, V.getFuelType());
            stmt.setInt(11, V.getHorsePower());
            stmt.setDouble(12, V.getLoadCapacity());
            stmt.executeUpdate();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Vehicle Berhasil di Tambahkan");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void editVehicle(Car V){
        try{
            PreparedStatement stmt = conn.prepareStatement("UPDATE vehicle SET "
                    + "register_number=?, name=?, brand=?, color=?, number_wheel=?, weight=?,number_doors=?, transmission=?, price=?, fuel_type=?, horse_power=?,status=?, model=? "
                    + "WHERE vehicle.register_number=?");
            stmt.setString(1, V.getRegistrationNumber());
            stmt.setString(2, V.getName());
            stmt.setString(3, V.getBrand());
            stmt.setString(4, V.getColor());
            stmt.setInt(5, V.getNumWheel());
            stmt.setDouble(6, V.getWeight());
            stmt.setInt(7, V.getNumDoors());
            stmt.setString(8, V.getTransmission());
            stmt.setInt(9, V.getPrice());
            stmt.setString(10, V.getFuelType());
            stmt.setInt(11, V.getHorsePower());
            stmt.setString(12,V.getStatus());
            stmt.setString(13, V.getCarModel().toString());
            stmt.setString(14, V.getRegistrationNumber());
            stmt.executeUpdate();
            stmt.close();
            System.out.print(V.getRegistrationNumber());
            JOptionPane.showMessageDialog(null, "Vehicle Berhasil di Update");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @Override
    public void editVehicle(Truck V){
        try{
            PreparedStatement stmt = conn.prepareStatement("UPDATE vehicle SET "
                    + "register_number =?, name=?, brand=?, color=?, number_wheel=?, weight=?,number_doors=?, transmission=?, price=?, fuel_type=?, horse_power=?,status=?, load_capacity=? "
                    + "WHERE vehicle.register_number=?");
            stmt.setString(1, V.getRegistrationNumber());
            stmt.setString(2, V.getName());
            stmt.setString(3, V.getBrand());
            stmt.setString(4, V.getColor());
            stmt.setInt(5, V.getNumWheel());
            stmt.setDouble(6, V.getWeight());
            stmt.setInt(7, V.getNumDoors());
            stmt.setString(8, V.getTransmission());
            stmt.setInt(9, V.getPrice());
            stmt.setString(10, V.getFuelType());
            stmt.setInt(11, V.getHorsePower());
            stmt.setString(12, V.getStatus());
            stmt.setDouble(13, V.getLoadCapacity());
            stmt.setString(14, V.getRegistrationNumber());
            stmt.executeUpdate();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Vehicle Berhasil di Update");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    
    @Override
    public void deleteVehicle(String index){
        try{
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM vehicle WHERE vehicle.register_number=?");
            stmt.setString(1, index);
            stmt.executeUpdate();              
            stmt.close();
            JOptionPane.showMessageDialog(null, "Vehicle Berhasil di Hapus");
        }catch(SQLException ex){
            System.err.println("Got an exception!");
            System.err.println(ex.getMessage()); 
        }    
    }
    
    public void editStatus(Vehicle V){
        try{
            PreparedStatement stmt = conn.prepareStatement("UPDATE vehicle SET status=? WHERE vehicle.register_number=?");
            stmt.setString(1, V.getStatus());
            stmt.setString(2, V.getRegistrationNumber());
            stmt.executeUpdate();
            stmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public List<Vehicle> searchByName(String name){
        List<Vehicle> vehicle = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM vehicle WHERE vehicle.name LIKE '%" + name + "%'");
            ResultSet rs= stmt.executeQuery();
            while(rs.next()){
                Vehicle V = null;
                if(rs.getObject("load_capacity", int.class) == null){
                    V = new Car(rs.getString("register_number"),
                            rs.getString("name"), 
                            rs.getString("brand"), 
                            rs.getString("color"),
                            rs.getInt("number_wheel"),
                            rs.getDouble("weight"),
                            rs.getInt("number_doors"),
                            rs.getString("transmission"),
                            rs.getInt("price"),
                            rs.getString("fuel_type"),
                            rs.getInt("horse_power"),
                            rs.getString("status"),
                            CarModel.valueOf(rs.getString("model"))
                    );
                }
                else{
                    V = new Truck(rs.getString("register_number"),
                            rs.getString("name"), 
                            rs.getString("brand"), 
                            rs.getString("color"),
                            rs.getInt("number_wheel"),
                            rs.getDouble("weight"),
                            rs.getInt("number_doors"),
                            rs.getString("transmission"),
                            rs.getInt("price"),
                            rs.getString("fuel_type"),
                            rs.getInt("horse_power"),
                            rs.getString("status"),
                            rs.getInt("load_capacity")
                    );
                }
                vehicle.add(V);
            }
            stmt.close();
            rs.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
        
        return vehicle;
    }
    
    public List<Vehicle> searchByRegisterNumber(String registerNumber){
        List<Vehicle> vehicle = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM vehicle WHERE vehicle.register_number LIKE '%" + registerNumber + "%'");
            ResultSet rs= stmt.executeQuery();
            while(rs.next()){
                Vehicle V = null;
                if(rs.getObject("load_capacity", int.class) == null){
                    V = new Car(rs.getString("register_number"),
                            rs.getString("name"), 
                            rs.getString("brand"), 
                            rs.getString("color"),
                            rs.getInt("number_wheel"),
                            rs.getDouble("weight"),
                            rs.getInt("number_doors"),
                            rs.getString("transmission"),
                            rs.getInt("price"),
                            rs.getString("fuel_type"),
                            rs.getInt("horse_power"),
                            rs.getString("status"),
                            CarModel.valueOf(rs.getString("model"))
                    );
                }
                else{
                    V = new Truck(rs.getString("register_number"),
                            rs.getString("name"), 
                            rs.getString("brand"), 
                            rs.getString("color"),
                            rs.getInt("number_wheel"),
                            rs.getDouble("weight"),
                            rs.getInt("number_doors"),
                            rs.getString("transmission"),
                            rs.getInt("price"),
                            rs.getString("fuel_type"),
                            rs.getInt("horse_power"),
                            rs.getString("status"),
                            rs.getInt("load_capacity")
                    );
                }
                vehicle.add(V);
            }
            stmt.close();
            rs.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
        
        return vehicle;
    }
    
    public int countAllVehicle(){
        int count = 0;
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT count(*) AS total FROM vehicle");
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
    
    public int countCar(){
        int count = 0;
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT count(*) AS total FROM vehicle WHERE load_capacity IS NULL");
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
    
    public int countCarWithStatus(String status){
        int count = 0;
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT count(*) AS total FROM vehicle WHERE load_capacity IS NULL AND status='"+status+"'");
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
    
    public int countTruck(){
        int count = 0;
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT count(*) AS total FROM vehicle WHERE load_capacity IS NOT NULL");
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
    
    public int countTruckWithStatus(String status){
        int count = 0;
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT count(*) AS total FROM vehicle WHERE load_capacity IS NOT    NULL AND status='"+status+"'");
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
