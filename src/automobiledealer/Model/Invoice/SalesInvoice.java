/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.Model.Invoice;

import automobiledealer.Model.Customer;
import automobiledealer.Model.Payments;
import automobiledealer.Model.Sales;
import automobiledealer.Model.Vehicle.Vehicle;
import java.util.Date;
import java.util.List;

/**
 *
 * @author AkbarFauzy
 */
public class SalesInvoice extends Invoice{
    private Sales employee;
    private List<Vehicle> vehicles;
    
    public SalesInvoice(int _invoiceID, Customer _customer, Payments _payment, String _description, Date _date, Sales _employee, List<Vehicle> _vehicles) {
        super(_invoiceID, _customer, _payment, _description, _date);
        this.employee = _employee;
        this.vehicles = _vehicles;
    }
  
    public Sales getEmployee() {
        return employee;
    }

    public void setEmployee(Sales employee) {
        this.employee = employee;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicle) {
        this.vehicles = vehicle;
    }
    
    
}
