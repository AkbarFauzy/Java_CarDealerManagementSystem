
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.DAO;

import static Connection.Connection_to_db.connection;
import automobiledealer.Model.Others.Customer;
import automobiledealer.Model.Employee.Employee;
import automobiledealer.Model.Invoice.Invoice;
import automobiledealer.Model.Parts.Parts;
import automobiledealer.Model.Others.Payments;
import automobiledealer.Model.Employee.Sales;
import automobiledealer.Model.Invoice.SalesInvoice;
import automobiledealer.Model.Invoice.ServiceInvoice;
import automobiledealer.Model.Employee.Manager;
import automobiledealer.Model.Employee.Technician;
import automobiledealer.Model.Vehicle.Vehicle;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    
    public List list(List<Customer> C, List<Employee> E, List Items, Employee loggedUser){
        List<Invoice> invoice = new ArrayList<>();
        try{
            ResultSet rs;
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM invoice")) {
                rs = stmt.executeQuery();
                while(rs.next()){
                    Customer customer = getCustomer(C, rs.getInt("customer_id"));
                    Employee employee = getEmployee(E, rs.getInt("employee_id"));
                    Invoice I = null;
                    if(employee instanceof Sales && loggedUser instanceof Sales){
                        I = new SalesInvoice(rs.getInt("id"), customer, Payments.valueOf(rs.getString("payment_type")), rs.getString("description"),rs.getDate("created_at"), (Sales)employee, getVehicle(Items, rs.getInt("id")));
                        invoice.add(I);
                    }else if(employee instanceof Technician && loggedUser instanceof Technician){
                        I = new ServiceInvoice(rs.getInt("id"), customer, Payments.valueOf(rs.getString("payment_type")), rs.getString("description"),rs.getDate("created_at"), (Technician)employee, getParts(Items, rs.getInt("id")));
                        invoice.add(I);
                    }
                }
            }
            rs.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
        return invoice;
    }
    
      public List list(List<Customer> C, List<Employee> E, List<Vehicle> V, List<Parts> P, Employee loggedUser){
        List<Invoice> invoice = new ArrayList<>();
        try{
            ResultSet rs;
            try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM invoice")) {
                rs = stmt.executeQuery();
                while(rs.next()){
                    Customer customer = getCustomer(C, rs.getInt("customer_id"));
                    Employee employee = getEmployee(E, rs.getInt("employee_id"));
                    Invoice I = null;
                    if(employee instanceof Sales && (loggedUser instanceof Sales || loggedUser instanceof Manager)){
                        I = new SalesInvoice(rs.getInt("id"), customer, Payments.valueOf(rs.getString("payment_type")), rs.getString("description"),rs.getDate("created_at"), (Sales)employee, getVehicle(V, rs.getInt("id")));
                        invoice.add(I);
                    }else if(employee instanceof Technician && (loggedUser instanceof Technician || loggedUser instanceof Manager)){
                        I = new ServiceInvoice(rs.getInt("id"), customer, Payments.valueOf(rs.getString("payment_type")), rs.getString("description"),rs.getDate("created_at"), (Technician)employee, getParts(P, rs.getInt("id")));
                        invoice.add(I);
                    }
                }
            }
            rs.close();
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
        
        return invoice;
    }
    
    public void addInvoice(Invoice I){
        try{
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO invoice(customer_id, employee_id, payment_type, description, created_at) VALUE(?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, I.getCustomer().getId());
            stmt.setString(3, I.getPaymentType().toString());
            stmt.setString(4,I.getDescription());
            stmt.setDate(5, Date.valueOf(LocalDate.now()));
            
            if(I instanceof SalesInvoice){
                stmt.setInt(2, Integer.valueOf(((SalesInvoice)I).getEmployee().getEmployeeID()));
                stmt.executeUpdate();
                ResultSet generatedId = stmt.getGeneratedKeys();
                if(generatedId.next()){
                    for(Vehicle vehicle : ((SalesInvoice)I).getVehicles()){
                        try(PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO invoice_sales(FK_invoice_id, vehicle_register_number) VALUES(?,?)")){
                            stmt2.setInt(1, generatedId.getInt(1));
                            stmt2.setString(2, vehicle.getRegistrationNumber());
                            stmt2.executeUpdate();
                        }catch(SQLException e){
                            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                
            }else if(I instanceof ServiceInvoice){
                stmt.setInt(2, Integer.valueOf(((ServiceInvoice)I).getEmployee().getEmployeeID()));
                stmt.executeUpdate();
                ResultSet generatedId = stmt.getGeneratedKeys();
                if (generatedId.next()) {
                    for(Parts part : ((ServiceInvoice)I).getParts()){
                        try(PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO invoice_service(FK_invoice_id, part_id) VALUES(?,?)")){
                            stmt2.setInt(1, generatedId.getInt(1));
                            stmt2.setString(2, part.getPartsNumber());
                            stmt2.executeUpdate();
                        }catch(SQLException e){
                            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
           
            }
            JOptionPane.showMessageDialog(null, "Invoice Berhasil di Tambahkan");
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);  
        }
    }
    
        
    public List<Vehicle> getVehicle(List<Vehicle> V, int invoice_ID){
        List<Vehicle> result = new ArrayList<>(); 
        try{
            PreparedStatement stmt = conn.prepareStatement("SELECT vehicle_register_number FROM invoice LEFT JOIN invoice_sales ON invoice.id = invoice_sales.FK_invoice_id WHERE invoice.id=?");
            stmt.setInt(1, invoice_ID);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                String register_number = rs.getString("vehicle_register_number");
                result.add(V.stream().filter(vehicle ->register_number.equals(vehicle.getRegistrationNumber())).findAny().orElse(null));
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);  
        }
        return result;
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
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e, "Dialog", JOptionPane.ERROR_MESSAGE);
        }
        return result;
    }
    
    public Employee getEmployee(List<Employee> E, int id){
        Employee result =  E.stream().filter(employee -> id == employee.getEmployeeID()).findAny().orElse(null);
        return result;
    }
    
    public Customer getCustomer(List<Customer> C, int id){
        Customer result = C.stream().filter(customer -> id == customer.getId()).findAny().orElse(null);
        return result;
    }
    
//    public Invoice searchInvoiceByID(List<Invoice> I, String id){
//        Invoice result = I.stream().filter(invoice -> id.equals(invoice.getInvoiceID())).findAny().orElse(null);
//        return result;
//    }
}
