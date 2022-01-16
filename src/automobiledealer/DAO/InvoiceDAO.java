
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.DAO;

import static Connection.Connection_to_db.connection;
import automobiledealer.Model.Customer;
import automobiledealer.Model.Employee;
import automobiledealer.Model.Invoice;
import automobiledealer.Model.Parts.Parts;
import automobiledealer.Model.Payments;
import automobiledealer.Model.Sales;
import automobiledealer.Model.SalesInvoice;
import automobiledealer.Model.ServiceInvoice;
import automobiledealer.Model.Technician;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author AkbarFauzy
 */
public class InvoiceDAO {
    Connection conn;
    
    public InvoiceDAO(){
        conn = connection();
    }
    
    public List list(List<Customer> C, List<Employee> E, List<Parts> P){
        List<Invoice> invoice = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Invoice");
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                Customer customer = getCustomer(C, rs.getString("customer_id"));
                Employee employee = getEmployee(E, rs.getString("employee_id"));
                Invoice I = null;
                if(employee instanceof Sales){
                    I = new SalesInvoice(rs.getInt("id"), customer, null, Payments.valueOf(rs.getString("payment_type")), rs.getString("description"),rs.getDate("created_at"), (Sales)employee);
                }else if(employee instanceof Technician){
                    I = new ServiceInvoice(rs.getInt("id"), customer, null, Payments.valueOf(rs.getString("payment_type")), rs.getString("description"),rs.getDate("created_at"), (Technician)employee, getParts(P, rs.getInt("id")));
                }
                invoice.add(I);
            }
            
            stmt.close();
            rs.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
        
        return invoice;
    }
    
    public void addInvoice(Invoice I){
        try{
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO invoice(customer_id, vehicle_register_number, spare_part_id, employee_id, payment_type_description, created_at) VALUE(?,?,?,?,?,?,?)");
            stmt.setString(1, I.getCustomer().getNIK());
            stmt.setString(2, I.getVehicle().getRegistrationNumber());
//            stmt.setString(3, I.get);
//            stmt.setString(4, I.);
            stmt.setString(5, I.getPaymentType().toString());
            stmt.setString(6,I.getDescription());
            stmt.setDate(7, Date.valueOf(LocalDate.now()));
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);  
        }
    }
    
    @SuppressWarnings("empty-statement")
    public List<Parts> getParts(List<Parts> P, int invoice_ID){
        List<Parts> result = new ArrayList<>();
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT part_id FROM invoice LEFT JOIN invoice_service ON invoice.id = invoice_service.FK_invoice_id WHERE invoice.id=?");
            stmt.setInt(1, invoice_ID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String part_id = rs.getString("part_id");
                result.add(P.stream().filter(part -> part_id.equals(part.getPartsNumber())).findAny().orElse(null));
            }
        //List<Parts> result = P.stream().filter(part -> part.getPartsNumber().equals(part_id)).collect(Collectors.toList());;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }
    
    public Employee getEmployee(List<Employee> E, String id){
        Employee result =  E.stream().filter(employee -> id.equals(employee.getEmployeeID())).findAny().orElse(null);
        return result;
    }
    
    public Customer getCustomer(List<Customer> C, String id){
        Customer result = C.stream().filter(customer -> id.equals(customer.getNIK())).findAny().orElse(null);
        return result;
    }
    
    public Invoice searchInvoiceByID(List<Invoice> I, String id){
        Invoice result = I.stream().filter(invoice -> id.equals(invoice.getInvoiceID())).findAny().orElse(null);
        return result;
    }
    
    
    
}
