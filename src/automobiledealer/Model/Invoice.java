/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.Model;

import automobiledealer.Model.Vehicle.Vehicle;
import java.util.Date;

/**
 *
 * @author AkbarFauzy
 */
public class Invoice {
    private String invoiceID;
    private Customer customer;
    private Employee employee;
    private Vehicle vehicle;
    private Payments paymentType;
    private String description;
    private Date date;
    
    public Invoice(String _invoiceID, Customer _customer, Employee _employee, Vehicle _vehicle, Payments _payment, String _description){
        this.invoiceID = _invoiceID;
        this.customer = _customer;
        this.employee = _employee;
        this.vehicle = this.vehicle;
        this.paymentType = _payment;
        this.description = _description;
        this.date = new Date();
    }
    
    
    
    
    
}
