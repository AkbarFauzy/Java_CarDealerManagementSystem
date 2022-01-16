/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.DAO;

import automobiledealer.Model.Vehicle.Car;
import automobiledealer.Model.Vehicle.Truck;
import java.util.List;

/**
 *
 * @author AkbarFauzy
 */
public interface ManageVehicle {
    public List list();
    
    public void addVehicle(Car V);
    public void editVehicle(Car V);
    public void addVehicle(Truck V);
    public void editVehicle(Truck V);
    public void deleteVehicle(String index);
    
}
