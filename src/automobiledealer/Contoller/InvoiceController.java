/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.Contoller;

import automobiledealer.DAO.InvoiceDAO;
import automobiledealer.Model.Invoice;
import automobiledealer.Model.Manager;
import automobiledealer.Model.Parts.Parts;
import automobiledealer.Model.Payments;
import automobiledealer.Model.ServiceInvoice;
import automobiledealer.Model.Technician;
import automobiledealer.Model.Vehicle.Vehicle;
import automobiledealer.UI.ItemDialog;
import automobiledealer.UI.MainFrame;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import static java.awt.image.ImageObserver.HEIGHT;
import static java.awt.image.ImageObserver.WIDTH;
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
    
    public InvoiceController(JFrame view, CustomerController _C, EmployeeController _E, PartController _P, VehicleController _V){
        this.C = _C;
        this.E = _E;
        this.P = _P;
        this.V = _V;
        
        iDAO = new InvoiceDAO();
        listInvoice = iDAO.list(C.listCustomer, E.listEmployee, P.listPart);
        
        this.view = (MainFrame)view;
        
        this.view.getInvoices_PanelButton().addActionListener(this);
        this.view.getAddInvoiceButton().addActionListener(this);
        this.view.getInvoiceForm_Button_addItem().addActionListener(this);
        
        this.view.getInvoice_Table().addMouseListener(this);
        
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.getInvoices_PanelButton()){
            view.getCardLayout().show(view.getContentPanel(), "InvoicePageContentPanel");
            view.prevMenuButton.setBackground(new Color(255,255,255));
            view.prevMenuButton.setForeground(Color.BLACK);
            view.getInvoices_PanelButton().setBackground(new Color(0,90,192));
            view.getInvoices_PanelButton().setForeground(Color.WHITE);
            view.prevMenuButton = view.getInvoices_PanelButton();
            RefreshModel();
            InvoiceList(view.getInvoice_Table());
            
        }else if(e.getSource() == view.getAddInvoiceButton()){
            Object[] options = {"New Customer", "Existing Customer"};
            int x = JOptionPane.showOptionDialog(null, "", "CUSTOMER", WIDTH, HEIGHT, null, options, JOptionPane.PLAIN_MESSAGE);
            if(x==0){
                view.getCardLayout().show(view.getContentPanel(), "CustomerFormPanel");
            }else if(x==1){
                view.getCardLayout().show(view.getContentPanel(), "InvoiceFormPanel");
            }
            ResetForm();
        }else if(e.getSource() == view.getInvoiceForm_Button_add()){
        
        
        }else if(e.getSource() == view.getInvoiceForm_Button_addItem()){
            ItemDialog F = new ItemDialog(view, true);
            tb = (DefaultTableModel)F.getItem_Table().getModel();
            tb.setRowCount(0);
            if(view.loggedUser instanceof Manager){
                for(int i=0;i < V.EligibleVehicle().size();i++){
                    Object[] data = {((Vehicle)V.EligibleVehicle().get(i)).getRegistrationNumber(), ((Vehicle)V.EligibleVehicle().get(i)).getName()};
                    tb.addRow(data);
                }
                F.getItem_Table().setModel(tb);
                
                
            }else if(view.loggedUser instanceof Technician){
                for(int i=0;i < P.listPart.size();i++){
                    Object[] data = {P.listPart.get(i).getPartsNumber(), P.listPart.get(i).getName()};
                    tb.addRow(data);
                }
                F.getItem_Table().setModel(tb);
            }
            
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
            System.out.print(F.getSelectedItem());
        }
    }
    
    public void InvoiceList(JTable table){
        tb = (DefaultTableModel)table.getModel();
        tb.setRowCount(0);  
        for(int i=0;i<listInvoice.size();i++){
            Object[] data = {listInvoice.get(i).getInvoiceID(), listInvoice.get(i).getCustomer().getName(), listInvoice.get(i).getDescription(), listInvoice.get(i).getDate()};
            tb.addRow(data);
        }
        table.setModel(tb);     
    }
    
    public void RefreshModel(){
        this.listInvoice = iDAO.list(C.listCustomer, E.listEmployee, P.listPart);
    }

    public void ResetForm(){
        view.getInvoiceForm_TextInput_customer().setText("");
        DefaultComboBoxModel CB = (DefaultComboBoxModel) view.getInvoiceForm_ComboBox_paymentType().getModel();
        CB.removeAllElements();
        for(Payments payment: Payments.values()){
            CB.addElement(payment);
        }
        view.getInvoiceForm_ComboBox_paymentType().setSelectedIndex(0);
        view.getInvoiceForm_TextArea_description();
        
        DefaultTableModel items = (DefaultTableModel) view.getInvoiceForm_Table_Item().getModel();
        items.setRowCount(0);
    }
    
    public void AddItemtoForm(int selectedItem){
        if(view.loggedUser instanceof Manager){
            Object[] item = {V.listVehicle.get(selectedItem).getRegistrationNumber(), V.listVehicle.get(selectedItem).getName()};
            DefaultTableModel IM = (DefaultTableModel) view.getInvoiceForm_Table_Item().getModel();
            IM.addRow(item);
            view.getInvoiceForm_Table_Item().setModel(IM);
            V.listVehicle.get(selectedItem).setStattus("Pending");
        }else if(view.loggedUser instanceof Technician){
            Object[] item = {P.listPart.get(selectedItem).getPartsNumber(), P.listPart.get(selectedItem).getName()};
            DefaultTableModel IM = (DefaultTableModel) view.getInvoiceForm_Table_Item().getModel();
            IM.addRow(item);
            view.getInvoiceForm_Table_Item().setModel(IM);
             P.listPart.get(selectedItem).setStatus("Pending");
        }
    }
    
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 && e.getSource() == view.getInvoice_Table()){
             view.getCardLayout().show(view.getContentPanel(), "InvoiceDetail");
            
             view.getInvoiceDetail_Label_id().setText(listInvoice.get(view.getInvoice_Table().getSelectedRow()).getInvoiceID()+"");
             view.getInvoiceDetail_Label_date().setText(listInvoice.get(view.getInvoice_Table().getSelectedRow()).getDate().toString());
             
            DefaultTableModel itemsTB = (DefaultTableModel) view.getInvoiceDetail_Table_item().getModel();
            itemsTB.setRowCount(0);
            
            if(listInvoice.get(view.getInvoice_Table().getSelectedRow()) instanceof ServiceInvoice){         
                List<Parts> parts = ((ServiceInvoice)listInvoice.get(view.getInvoice_Table().getSelectedRow())).getParts();
                for(Parts part : parts){
                    Object[] data = {part.getPartsNumber(), part.getName(), part.getPrice()};
                    itemsTB.addRow(data);
                }
                view.getInvoiceDetail_Table_item().setModel(itemsTB);
                
            }else{
                System.out.print("sales");
            } 
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}
