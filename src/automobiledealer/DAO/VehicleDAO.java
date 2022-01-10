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
public class VehicleDAO {
    Connection conn;
    
    public VehicleDAO(){
        conn = connection();
    }
    
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
    
    public void addVehicle(Car V){
        try{
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO "
                    + "vehicle(name, brand, color, number_wheel, weight, number_doors, transmission, price, fuel_type, horse_power, model) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, V.getName());
            stmt.setString(2, V.getBrand());
            stmt.setString(3, V.getColor());
            stmt.setInt(4, V.getNumWheel());
            stmt.setDouble(5,V.getWeight());
            stmt.setInt(6, V.getNumDoors());
            stmt.setString(7, V.getTransmission());
            stmt.setInt(8, V.getPrice());
            stmt.setString(9, V.getFuelType());
            stmt.setInt(10, V.getHorsePower());
            stmt.setString(11, V.getCarModel().toString());
            stmt.executeUpdate();
            stmt.close();
            JOptionPane.showMessageDialog(null, "Vehicle Berhasil di Tambahkan");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void addVehicle(Truck V){
        try{
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO "
                    + "vehicle(name, brand, color, number_wheel, weight,number_doors, transmission, price, fuel_type, horse_power, load_capacity) "
                    + "VALUES(?,?,?,?,?,?,?,?,?,?)");
            stmt.setString(1, V.getName());
            stmt.setString(2, V.getBrand());
            stmt.setString(3, V.getColor());
            stmt.setInt(4, V.getNumWheel());
            stmt.setDouble(5, V.getWeight());
            stmt.setInt(6, V.getNumDoors());
            stmt.setString(7, V.getTransmission());
            stmt.setInt(8, V.getPrice());
            stmt.setString(9, V.getFuelType());
            stmt.setInt(10, V.getHorsePower());
            stmt.setDouble(11, V.getLoadCapacity());
            stmt.executeUpdate();
            stmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void editVehicle(Car V){
        try{
            PreparedStatement stmt = conn.prepareStatement("UPDATE vehicle SET "
                    + "name=?, brand=?, color=?, number_wheel=?, weight=?,number_doors=?, transmission=?, price=?, fuel_type=?, horse_power=?, model=? "
                    + "WHERE vehicle.registered_number=?");
            stmt.setString(1, V.getName());
            stmt.setString(2, V.getBrand());
            stmt.setString(3, V.getColor());
            stmt.setInt(4, V.getNumWheel());
            stmt.setDouble(5, V.getWeight());
            stmt.setInt(6, V.getNumDoors());
            stmt.setString(7, V.getTransmission());
            stmt.setInt(8, V.getPrice());
            stmt.setString(9, V.getFuelType());
            stmt.setInt(10, V.getHorsePower());
            stmt.setString(11, V.getCarModel().toString());
            stmt.setString(12, V.getRegistrationNumber());
            stmt.executeUpdate();
            stmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void editVehicle(Truck V){
        try{
            PreparedStatement stmt = conn.prepareStatement("UPDATE vehicle SET "
                    + "name=?, brand=?, color=?, number_wheel=?, weight=?,number_doors=?, transmission=?, price=?, fuel_type=?, horse_power=?, load_capacity=? "
                    + "WHERE vehicle.registered_number=?");
            stmt.setString(1, V.getName());
            stmt.setString(2, V.getBrand());
            stmt.setString(3, V.getColor());
            stmt.setInt(4, V.getNumWheel());
            stmt.setDouble(5, V.getWeight());
            stmt.setInt(6, V.getNumDoors());
            stmt.setString(7, V.getTransmission());
            stmt.setInt(8, V.getPrice());
            stmt.setString(9, V.getFuelType());
            stmt.setInt(10, V.getHorsePower());
            stmt.setDouble(11, V.getLoadCapacity());
            stmt.setString(12, V.getRegistrationNumber());
            stmt.executeUpdate();
            stmt.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void deleteVehicle(String index){
        try{
            PreparedStatement stmt = conn.prepareStatement("DELETE FROM vehicle WHERE vehicle.register_number=?");
            stmt.setString(1, index);
            stmt.executeUpdate();              
            stmt.close();
        }catch(SQLException ex){
            System.err.println("Got an exception!");
            System.err.println(ex.getMessage()); 
        }
    
    }
    
    
}
