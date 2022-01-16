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
public class SalesInvoice extends Invoice{
    private Sales employee;

    public SalesInvoice(int _invoiceID, Customer _customer, Vehicle _vehicle, Payments _payment, String _description, Date _date, Sales _employee) {
        super(_invoiceID, _customer, _vehicle, _payment, _description, _date);
        this.employee = _employee;
    }
    

    
}
