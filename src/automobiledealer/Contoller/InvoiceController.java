/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.Contoller;

import automobiledealer.DAO.InvoiceDAO;
import automobiledealer.Model.Others.Customer;
import automobiledealer.Model.Invoice.Invoice;
import automobiledealer.Model.Parts.Parts;
import automobiledealer.Model.Others.Payments;
import automobiledealer.Model.Employee.Sales;
import automobiledealer.Model.Invoice.SalesInvoice;
import automobiledealer.Model.Invoice.ServiceInvoice;
import automobiledealer.Model.Employee.Manager;
import automobiledealer.Model.Parts.CarEngine;
import automobiledealer.Model.Parts.RearviewMirror;
import automobiledealer.Model.Parts.Rims;
import automobiledealer.Model.Parts.Tire;
import automobiledealer.Model.Employee.Technician;
import automobiledealer.Model.Vehicle.Vehicle;
import automobiledealer.UI.ItemDialog;
import automobiledealer.UI.MainFrame;
import automobiledealer.UI.ViewPartDetailEngine;
import automobiledealer.UI.ViewPartDetailMirror;
import automobiledealer.UI.ViewPartDetailRims;
import automobiledealer.UI.ViewPartDetailTire;
import automobiledealer.UI.ViewVehicleDetail;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AkbarFauzy
 */
public class InvoiceController implements ActionListener, MouseListener{

    DefaultTableModel tb = new DefaultTableModel();
    InvoiceDAO iDAO;
    List<Invoice> listInvoice;
    
    MainFrame view;
    
    CustomerController C;
    EmployeeController E;
    PartController P;
    VehicleController V;
    List<Object> listItem = new ArrayList<>();
    Invoice selectedInvoice;
    
    //CONSTRUCTOR
    public InvoiceController(JFrame view, CustomerController _C, EmployeeController _E, PartController _P, VehicleController _V){
        this.C = _C;
        this.E = _E;
        this.P = _P;
        this.V = _V;
        
        iDAO = new InvoiceDAO();
        this.view = (MainFrame)view;
        
        if(this.view.loggedUser instanceof Sales){
            listInvoice = iDAO.list(C.listCustomer, E.listEmployee, V.listVehicle, this.view.loggedUser);
        }else if(this.view.loggedUser instanceof Technician){
            listInvoice = iDAO.list(C.listCustomer, E.listEmployee, P.listPart, this.view.loggedUser);
        }else if(this.view.loggedUser instanceof Manager){
            listInvoice = iDAO.list(C.listCustomer, E.listEmployee, V.listVehicle, P.listPart, this.view.loggedUser);
        }
        
        this.view.getInvoices_PanelButton().addActionListener(this);
        this.view.getAddInvoiceButton().addActionListener(this);
        this.view.getInvoiceForm_Button_addItem().addActionListener(this);
        this.view.getInvoiceForm_Button_deleteItem().addActionListener(this);
        this.view.getInvoiceForm_Button_add().addActionListener(this);
        
        this.view.getInvoice_Table().addMouseListener(this);
        this.view.getInvoiceDetail_Table_item().addMouseListener(this);
    }
    
    //IMPLMENTASI ACTIONLISTENER
    @Override
    public void actionPerformed(ActionEvent e) {
        //Ketika keluar dari invoice Form
        if(e.getSource() != view.getInvoiceForm_Button_add() && e.getSource() != view.getInvoiceForm_Button_addItem() && e.getSource() != view.getInvoiceForm_Button_deleteItem()){
            for(int i=0;i<listItem.size();i++){
                DeleteItemFromForm(i);
            }      
        }
        
        if(e.getSource() == view.getInvoices_PanelButton()){ //Ketika tombol invoice pada sidebar ditekan
            view.getCardLayout().show(view.getContentPanel(), "InvoicePageContentPanel");
            view.prevMenuButton.setBackground(new Color(255,255,255));
            view.prevMenuButton.setForeground(Color.BLACK);
            view.getInvoices_PanelButton().setBackground(new Color(0,90,192));
            view.getInvoices_PanelButton().setForeground(Color.WHITE);
            view.prevMenuButton = view.getInvoices_PanelButton();
            RefreshModel();
            InvoiceList(view.getInvoice_Table());
            
        }else if(e.getSource() == view.getAddInvoiceButton()){ //Ketika tombol add invoice pada halaman invoice ditekan
            view.getCardLayout().show(view.getContentPanel(), "CustomerFormPanel");
            C.ResetForm();
            ResetForm();
        }else if(e.getSource() == view.getInvoiceForm_Button_add()){ // Ketika tombol add pada invoice form ditekan
            if(listItem.size()==0){
                JOptionPane.showMessageDialog(view, "Item Tidak Boleh Kosong", "Dialog", JOptionPane.ERROR_MESSAGE);
            }else{
                InsertInvoice();
                view.getCardLayout().show(view.getContentPanel(), "InvoicePageContentPanel");
                RefreshModel();
                InvoiceList(view.getInvoice_Table());
                ResetForm();
            }
            
        }else if(e.getSource() == view.getInvoiceForm_Button_addItem()){ // Ketika Tombol add item pada invoice form ditekan
            ItemDialog F = new ItemDialog(view, true);
            tb = (DefaultTableModel)F.getItem_Table().getModel();
            tb.setRowCount(0);
            
            if(view.loggedUser instanceof Sales){
                F.setVehicleController(V);
                V.RefreshModel();
                V.EligibleVehicle();
                for(int i=0;i < V.listVehicle.size();i++){
                    Object[] data = {((Vehicle)V.listVehicle.get(i)).getRegistrationNumber(), ((Vehicle)V.listVehicle.get(i)).getName(), ((Vehicle)V.listVehicle.get(i)).getPrice()};
                    tb.addRow(data);
                }         
            }else if(view.loggedUser instanceof Technician){
                F.setPartController(P);
                P.RefreshModel();
                P.EligibleParts();
                for(int i=0;i < P.listPart.size();i++){
                    Object[] data = {((Parts)P.listPart.get(i)).getPartsNumber(), ((Parts)P.listPart.get(i)).getName(), ((Parts)P.listPart.get(i)).getPrice()};
                    tb.addRow(data);
                }
            }
            F.getItem_Table().setModel(tb);
            
            F.addWindowListener(new java.awt.event.WindowAdapter(){
                @Override
                public void windowClosing(java.awt.event.WindowEvent e){   
                    F.dispose();
                }
            });
            
            F.setVisible(true);
            if(F.getSelectedItem() != -1){
                AddItemtoForm(F.getSelectedItem());
            }
        }else if(e.getSource() == view.getInvoiceForm_Button_deleteItem()){ //Ketika tombol delete Item pada invoice form ditekan
            DeleteItemFromForm(view.getInvoiceForm_Table_Item().getSelectedRow());
        }
    }
    
    //Fungsi untuk Mengisi tabel dengan Invoice
    public void InvoiceList(JTable table){
        tb = (DefaultTableModel)table.getModel();
        tb.setRowCount(0);  
        for(int i=0;i<listInvoice.size();i++){
            Object[] data = {listInvoice.get(i).getInvoiceID(), listInvoice.get(i).getCustomer().getName(), listInvoice.get(i).getDescription(), listInvoice.get(i).getDate()};
            tb.addRow(data);
        }
        table.setModel(tb);     
    }
    
    //Fungsi untuk me-refresh Model
    public void RefreshModel(){
        C.RefreshModel();
        E.RefreshModel();  
        if(this.view.loggedUser instanceof Sales){
            V.RefreshModel();
            listInvoice = iDAO.list(C.listCustomer, E.listEmployee, V.listVehicle, this.view.loggedUser);
        }else if(this.view.loggedUser instanceof Technician){
            P.RefreshModel();
            listInvoice = iDAO.list(C.listCustomer, E.listEmployee, P.listPart, this.view.loggedUser);
        }    
    }
    
    //Fungsi untuk MeresetForm
    public void ResetForm(){
        DefaultComboBoxModel CB = (DefaultComboBoxModel) view.getInvoiceForm_ComboBox_paymentType().getModel();
        CB.removeAllElements();
        for(Payments payment: Payments.values()){
            CB.addElement(payment);
        }
        view.getInvoiceForm_ComboBox_paymentType().setSelectedIndex(0);
        view.getInvoiceForm_TextArea_description().setText("");
        view.getCustomerForm_Button_add().setText("Add");
        
        DefaultTableModel items = (DefaultTableModel) view.getInvoiceForm_Table_Item().getModel();
        items.setRowCount(0);
    }
    
    //Fungsi untuk menambahkan item ke form
    public void AddItemtoForm(int selectedItem){
        DefaultTableModel IM = (DefaultTableModel) view.getInvoiceForm_Table_Item().getModel();
        if(view.loggedUser instanceof Sales){
            Object[] item = {V.listVehicle.get(selectedItem).getRegistrationNumber(), V.listVehicle.get(selectedItem).getName(), V.listVehicle.get(selectedItem).getPrice()};
            IM.addRow(item);
            V.listVehicle.get(selectedItem).setStatus("Pending");
            V.EditStatus(V.listVehicle.get(selectedItem));
            listItem.add(V.listVehicle.get(selectedItem));
        }else if(view.loggedUser instanceof Technician){
            Object[] item = {P.listPart.get(selectedItem).getPartsNumber(), P.listPart.get(selectedItem).getName(), P.listPart.get(selectedItem).getPrice()};
            IM.addRow(item);
            P.listPart.get(selectedItem).setStatus("Pending");
            P.EditStatus(P.listPart.get(selectedItem));
            listItem.add(P.listPart.get(selectedItem));
        }
        view.getInvoiceForm_Table_Item().setModel(IM);
        int total = 0;
        
        for(Object obj: listItem){
           if(obj instanceof Parts){
               total += ((Parts)obj).getPrice();
           }else if(obj instanceof Vehicle){
               total += ((Vehicle)obj).getPrice();
           }
        }
        view.getInvoiceForm_Label_total().setText(total+"");
    }
    
    //Fungsi untuk menghapus Item pada Form
    public void DeleteItemFromForm(int selectedItem){
        ((DefaultTableModel) view.getInvoiceForm_Table_Item().getModel()).removeRow(selectedItem);
        if(view.loggedUser instanceof Sales){
            V.RefreshModel();
            for(Vehicle v : V.listVehicle){
                if(v.getRegistrationNumber().equals(((Vehicle)listItem.get(selectedItem)).getRegistrationNumber())){
                    v.setStatus("Ready");
                    V.EditStatus(v);
                    break;
                }
            }
            view.getInvoiceForm_Label_total().setText((Integer.parseInt(view.getInvoiceForm_Label_total().getText()) - ((Vehicle)listItem.get(selectedItem)).getPrice())+"");
            listItem.remove(listItem.get(selectedItem));
        }else if(view.loggedUser instanceof Technician){
            P.RefreshModel();
            for(Parts p : P.listPart){
                if(p.getPartsNumber().equals(((Parts)listItem.get(selectedItem)).getPartsNumber())){
                    p.setStatus("Ready");
                    P.EditStatus(p);
                    break;
                }
            }
            view.getInvoiceForm_Label_total().setText((Integer.parseInt(view.getInvoiceForm_Label_total().getText()) - ((Parts)listItem.get(selectedItem)).getPrice())+"");
            listItem.remove(listItem.get(selectedItem));
        }
    }
    
    //Fungsi untuk melakukan insert ke database
    public void InsertInvoice(){
        C.RefreshModel();
        Customer customer = ((Customer)C.listCustomer.stream().filter(c -> view.getCustomerForm_TextInput_phoneNumber().getText().equals(c.getPhoneNumber())).findAny().orElse(null));
        if(customer == null){
            C.InsertCustomer();
            C.RefreshModel();
            customer = ((Customer)C.listCustomer.stream().filter(c -> view.getCustomerForm_TextInput_phoneNumber().getText().equals(c.getPhoneNumber())).findAny().orElse(null));
        }
        if(view.loggedUser instanceof Sales){
            iDAO.addInvoice(new SalesInvoice(0, 
                                            customer, 
                                            Payments.valueOf(view.getInvoiceForm_ComboBox_paymentType().getSelectedItem().toString()),
                                            view.getInvoiceForm_TextArea_description().getText(),
                                            new Date(), (Sales)view.loggedUser,
                                            (List<Vehicle>)(List<?>)listItem
                            ));
            listItem.stream().map(obj -> (Vehicle)obj).map(vehicle -> {
                vehicle.setStatus("Sold");
                return vehicle;
            }).forEachOrdered(vehicle -> {
                V.EditStatus(vehicle);
            });
        }else if(view.loggedUser instanceof Technician){
            iDAO.addInvoice(new ServiceInvoice(0, 
                                               customer, 
                                               Payments.valueOf(view.getInvoiceForm_ComboBox_paymentType().getSelectedItem().toString()), 
                                               view.getInvoiceForm_TextArea_description().getText(), 
                                               new Date(), 
                                               (Technician)view.loggedUser, 
                                               (List<Parts>)(List<?>)listItem
                            ));
            listItem.stream().map(obj -> (Parts)obj).map(part -> {
                part.setStatus("Sold");
                return part;
            }).forEachOrdered(part -> {
                P.EditStatus(part);
            });
        }
        listItem.clear();
    }
    
    //IMPLMENTASI MOUSELISTENER
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 && e.getSource() == view.getInvoice_Table()){
            int total = 0;
            view.getCardLayout().show(view.getContentPanel(), "InvoiceDetail");
            
            view.getInvoiceDetail_Label_id().setText(listInvoice.get(view.getInvoice_Table().getSelectedRow()).getInvoiceID()+"");
            view.getInvoiceDetail_Label_date().setText(listInvoice.get(view.getInvoice_Table().getSelectedRow()).getDate().toString());
            view.getInvoiceDetail_TextArea_description().setText(listInvoice.get(view.getInvoice_Table().getSelectedRow()).getDescription());
            
            view.getInvoiceDetail_Label_customerName().setText(listInvoice.get(view.getInvoice_Table().getSelectedRow()).getCustomer().getName());
            view.getInvoiceDetail_Label_customerTelpon().setText(listInvoice.get(view.getInvoice_Table().getSelectedRow()).getCustomer().getPhoneNumber());
            view.getInvoiceDetail_Label_customerAddress().setText(listInvoice.get(view.getInvoice_Table().getSelectedRow()).getCustomer().getAddress());
            
            DefaultTableModel itemsTB = (DefaultTableModel) view.getInvoiceDetail_Table_item().getModel();
            itemsTB.setRowCount(0);
            
            if(listInvoice.get(view.getInvoice_Table().getSelectedRow()) instanceof ServiceInvoice){
                view.getInvoiceDetail_Label_employeeName().setText(((ServiceInvoice)listInvoice.get(view.getInvoice_Table().getSelectedRow())).getEmployee().getName());
                List<Parts> parts = ((ServiceInvoice)listInvoice.get(view.getInvoice_Table().getSelectedRow())).getParts();
                for(Parts part : parts){
                    Object[] data = {part.getPartsNumber(), part.getName(), "Rp. " + NumberFormat.getIntegerInstance().format(part.getPrice())};
                    itemsTB.addRow(data);
                    total += part.getPrice();
                }
                view.getInvoiceDetail_Table_item().setModel(itemsTB);
                
            }else if(listInvoice.get(view.getInvoice_Table().getSelectedRow()) instanceof SalesInvoice){
                view.getInvoiceDetail_Label_employeeName().setText(((SalesInvoice)listInvoice.get(view.getInvoice_Table().getSelectedRow())).getEmployee().getName());
                List<Vehicle> vehicles = ((SalesInvoice)listInvoice.get(view.getInvoice_Table().getSelectedRow())).getVehicles();
                for(Vehicle vehicle : vehicles){
                    Object[] data = {vehicle.getRegistrationNumber(), vehicle.getName(), "Rp. " +  NumberFormat.getIntegerInstance().format(vehicle.getPrice())};
                    itemsTB.addRow(data);
                    total += vehicle.getPrice();
                }
                view.getInvoiceDetail_Table_item().setModel(itemsTB);
            } 
            view.getInvoiceDetail_Label_total().setText(NumberFormat.getIntegerInstance().format(total));
            selectedInvoice = listInvoice.get(view.getInvoice_Table().getSelectedRow());
        }else if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 && e.getSource() == view.getInvoiceDetail_Table_item()){
            if(view.loggedUser instanceof Sales || view.loggedUser instanceof Manager){
                List<Vehicle> temp = ((SalesInvoice)selectedInvoice).getVehicles();
                ViewVehicleDetail F = new ViewVehicleDetail(view, true,  temp.get(view.getInvoiceDetail_Table_item().getSelectedRow()));
                F.addWindowListener(new java.awt.event.WindowAdapter(){
                @Override
                public void windowClosing(java.awt.event.WindowEvent e){
                    F.dispose();
                }
                });
                F.setVisible(true);
            }else if(view.loggedUser instanceof Technician || view.loggedUser instanceof Manager){
                List<Parts> temp = ((ServiceInvoice)selectedInvoice).getParts();
                if(temp.get(view.getInvoiceDetail_Table_item().getSelectedRow()) instanceof Rims){
                    ViewPartDetailRims F = new ViewPartDetailRims(view, true, (Rims)temp.get(view.getInvoiceDetail_Table_item().getSelectedRow()));
                    F.addWindowListener(new java.awt.event.WindowAdapter(){
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e){
                            F.dispose();
                        }
                    });
                    F.setVisible(true);
                }else if(temp.get(view.getInvoiceDetail_Table_item().getSelectedRow()) instanceof Tire){
                    ViewPartDetailTire F = new ViewPartDetailTire(view, true, (Tire)temp.get(view.getInvoiceDetail_Table_item().getSelectedRow()));
                    F.addWindowListener(new java.awt.event.WindowAdapter(){
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e){
                            F.dispose();
                        }
                    });
                    F.setVisible(true);
                }else if(temp.get(view.getInvoiceDetail_Table_item().getSelectedRow()) instanceof RearviewMirror){
                    ViewPartDetailMirror F = new ViewPartDetailMirror(view, true, (RearviewMirror)temp.get(view.getInvoiceDetail_Table_item().getSelectedRow()));
                    F.addWindowListener(new java.awt.event.WindowAdapter(){
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e){
                            F.dispose();
                        }
                        });
                    F.setVisible(true);
                }else if(temp.get(view.getInvoiceDetail_Table_item().getSelectedRow()) instanceof CarEngine){
                    ViewPartDetailEngine F = new ViewPartDetailEngine(view, true, (CarEngine)temp.get(view.getInvoiceDetail_Table_item().getSelectedRow()));
                    F.addWindowListener(new java.awt.event.WindowAdapter(){
                        @Override
                        public void windowClosing(java.awt.event.WindowEvent e){
                            F.dispose();
                        }
                        });
                    F.setVisible(true);
                }
            }    
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //DO NOTHING
    }
    @Override
    public void mouseReleased(MouseEvent e) {
        //DO NOTHING
    }
    @Override
    public void mouseEntered(MouseEvent e) {
        //DO NOTHING
    }
    @Override
    public void mouseExited(MouseEvent e) {
        //DO NOTHING
    }
    
}
