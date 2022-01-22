/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.Model.Invoice;

import automobiledealer.Model.Others.Customer;
import automobiledealer.Model.Others.Payments;
import java.util.Date;

/**
 *
 * @author AkbarFauzy
 */
public class Invoice {
    private int invoiceID;
    private Customer customer;
    private Payments paymentType;
    private String description;
    private Date date;
    
    public Invoice(int _invoiceID, Customer _customer, Payments _payment, String _description, Date _date){
        this.invoiceID = _invoiceID;
        this.customer = _customer;
        this.paymentType = _payment;
        this.description = _description;
        this.date = _date;
    }

    public int getInvoiceID() {
        return invoiceID;
    }

    public void setInvoiceID(int invoiceID) {
        this.invoiceID = invoiceID;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Payments getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Payments paymentType) {
        this.paymentType = paymentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
   
}
