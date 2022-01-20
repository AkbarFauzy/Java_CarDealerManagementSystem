/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.Model.Invoice;

import automobiledealer.Model.Customer;
import automobiledealer.Model.Parts.Parts;
import automobiledealer.Model.Payments;
import automobiledealer.Model.Technician;
import java.util.Date;
import java.util.List;

/**
 *
 * @author AkbarFauzy
 */
public class ServiceInvoice extends Invoice{
    
    private Technician employee;
    private List<Parts> parts;

    public ServiceInvoice(int _invoiceID, Customer _customer, Payments _payment, String _description, Date _date, Technician _employee, List<Parts> _parts) {
        super(_invoiceID, _customer, _payment, _description, _date);
        this.employee = _employee;
        this.parts = _parts;
    }

    public Technician getEmployee() {
        return employee;
    }

    public void setEmployee(Technician employee) {
        this.employee = employee;
    }

    public List<Parts> getParts() {
        return parts;
    }

    public void setParts(List<Parts> parts) {
        this.parts = parts;
    }
    
}
