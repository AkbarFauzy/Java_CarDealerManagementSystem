/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.Model;

import automobiledealer.Model.Vehicle.Vehicle;

/**
 *
 * @author AkbarFauzy
 */
public class ServiceInvoice extends Invoice{
    
    public ServiceInvoice(String _invoiceID, Customer _customer, Employee _employee, Vehicle _vehicle, Payments _payment, String _description) {
        super(_invoiceID, _customer, _employee, _vehicle, _payment, _description);
    }
    
}
