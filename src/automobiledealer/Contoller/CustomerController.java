/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.Contoller;

import automobiledealer.DAO.CustomerDAO;
import automobiledealer.UI.MainFrame;
import automobiledealer.UI.ViewCustomerDetail;
import automobiledealer.Model.Customer;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AkbarFauzy
 */
public class CustomerController implements ActionListener, MouseListener{
    DefaultTableModel tb = new DefaultTableModel();
    CustomerDAO cDAO;
    List<Customer> listCustomer;
    
    MainFrame view;
    Customer selectedCustomer;

    public CustomerController(JFrame view){
        cDAO = new CustomerDAO();
        listCustomer = cDAO.list();
        
        this.view = (MainFrame)view;
        this.view.getCustomer_PanelButton().addActionListener(this);
        this.view.getAddCustomerButton().addActionListener(this);
        this.view.getCustomerForm_Button_add().addActionListener(this);
        this.view.getEditCustomerButton().addActionListener(this);
        this.view.getDeleteCustomerButton().addActionListener(this);
        this.view.getCustomer_Table().addMouseListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.getCustomer_PanelButton()){
            view.getCardLayout().show(view.getContentPanel(), "CustomerPageContentPanel");
            view.prevMenuButton.setBackground(new Color(255,255,255));
            view.prevMenuButton.setForeground(Color.BLACK);
            view.getCustomer_PanelButton().setBackground(new Color(0,90,192));
            view.getCustomer_PanelButton().setForeground(Color.WHITE);
            view.prevMenuButton = view.getCustomer_PanelButton();
            RefreshModel();
            CustomerList(view.getCustomer_Table());
            view.getEditCustomerButton().setEnabled(false);
            view.getDeleteCustomerButton().setEnabled(false);
            
        }else if(e.getSource() == view.getAddCustomerButton()){
            view.getCardLayout().show(view.getContentPanel(), "CustomerFormPanel");
            ResetForm();
            view.getCustomerForm_Button_add().setText("Add"); 
        }else if(e.getSource() == view.getCustomerForm_Button_add()){   
            if("Add".equals(view.getCustomerForm_Button_add().getText())){
                if(view.prevMenuButton == view.getInvoices_PanelButton()){
                    view.cardLayout.show(view.getContentPanel(), "InvoiceFormPanel");
                }else{
                    InsertCustomer();
                    view.cardLayout.show(view.getContentPanel(), "CustomerPageContentPanel");
                }
            }else if("Update".equals(view.getCustomerForm_Button_add().getText())){
                EditCustomer();
            }
            RefreshModel();
            CustomerList(view.getCustomer_Table());
        }else if(e.getSource() == view.getEditCustomerButton()){ //Edit Customer Button
            selectedCustomer = (Customer)listCustomer.get(view.getCustomer_Table().convertRowIndexToModel(view.getCustomer_Table().getSelectedRow()));
            view.getCardLayout().show(view.getContentPanel(), "CustomerFormPanel");
            FillForm();
            view.getCustomerForm_Button_add().setText("Update");
        }else if(e.getSource() == view.getDeleteCustomerButton()){ //Delete Customer Button
            DeleteCustomer();
            RefreshModel();
            CustomerList(view.getCustomer_Table());
        }
    }


    public void RefreshModel(){
        this.listCustomer = cDAO.list();
    }
    
    public void ResetForm(){
        view.getCustomerForm_TextInput_name().setText("");
        view.getCustomerForm_TextInput_address().setText("");
        view.getCustomerForm_TextInput_phoneNumber().setText("");
        view.getCustomerGender().setSelected(view.getCustomerForm_RB_Male().getModel(), true);
    }
    
    public void FillForm(){
        view.getCustomerForm_TextInput_name().setText(selectedCustomer.getName());
        view.getCustomerForm_TextInput_address().setText(selectedCustomer.getAddress());
        view.getCustomerForm_TextInput_phoneNumber().setText(selectedCustomer.getPhoneNumber());
        if("Female".equals(selectedCustomer.getGender())){
            view.getCustomerGender().setSelected(view.getCustomerForm_RB_Female().getModel(), true);
        }else{
            view.getCustomerGender().setSelected(view.getCustomerForm_RB_Male().getModel(), true);
        }
        view.getCustomerGender().setSelected(view.getCustomerForm_RB_Male().getModel(), true);
    }
        
    public void CustomerList(JTable table){
        tb = (DefaultTableModel)table.getModel();
        tb.setRowCount(0);  

        for(int i=0;i<listCustomer.size();i++){
            Object[] data = {listCustomer.get(i).getName(),listCustomer.get(i).getAddress(),listCustomer.get(i).getPhoneNumber(), listCustomer.get(i).getGender()};
            tb.addRow(data);
        }
        table.setModel(tb);
    }
   
    public void InsertCustomer(){
        if(view.getCustomerForm_TextInput_name().getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(view, "Nama Tidak Boleh Kosong", "Dialog", JOptionPane.ERROR_MESSAGE);
        }else if(view.getCustomerForm_TextInput_address().getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(view, "Address Tidak Boleh Kosong", "Dialog", JOptionPane.ERROR_MESSAGE);
        }else{
           System.out.print(view.getCustomerForm_RB_Male().getActionCommand());
           cDAO.addCustomer(new Customer("",view.getCustomerForm_TextInput_name().getText(),
                                           view.getCustomerForm_TextInput_address().getText(),
                                           view.getCustomerForm_TextInput_phoneNumber().getText(),
                                           view.getCustomerGender().getSelection().getActionCommand()
           ));
        }
    }
    
    public void EditCustomer(){
        if(view.getCustomerForm_TextInput_name().getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(view, "Nama Tidak Boleh Kosong", "Dialog", JOptionPane.ERROR_MESSAGE);
        }else if(view.getCustomerForm_TextInput_address().getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(view, "Address Tidak Boleh Kosong", "Dialog", JOptionPane.ERROR_MESSAGE);
        }else{
            selectedCustomer.setName(view.getCustomerForm_TextInput_name().getText());
            selectedCustomer.setAddress(view.getCustomerForm_TextInput_address().getText());
            selectedCustomer.setPhoneNumber(view.getCustomerForm_TextInput_phoneNumber().getText());
            selectedCustomer.setGender(view.getCustomerGender().getSelection().getActionCommand());
            cDAO.editCustomer(selectedCustomer);
        }
    }
    
    public void DeleteCustomer(){
        String msg = "Are you sure want to Delete " + listCustomer.get(view.getCustomer_Table().getSelectedRow()).getName()+
                    " with NIK : "+ listCustomer.get(view.getCustomer_Table().getSelectedRow()).getNIK()+" ?";
            Object[] options ={"Yes", "Cancel"};
            int option = JOptionPane.showOptionDialog(null, msg, "",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
            if(option == JOptionPane.OK_OPTION){     
                cDAO.deleteCustomer(listCustomer.get(view.getCustomer_Table().getSelectedRow()));    
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 && e.getSource() == view.getCustomer_Table()){
            ViewCustomerDetail F = new ViewCustomerDetail(view, true, listCustomer.get(view.getCustomer_Table().getSelectedRow()));
            F.addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosing(java.awt.event.WindowEvent e){
                F.dispose();
            }
            
            });
            F.setVisible(true);
        }
    }
    

    @Override
    public void mousePressed(MouseEvent e){}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}    
}
