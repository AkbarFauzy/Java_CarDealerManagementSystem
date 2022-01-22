/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.Contoller;

import automobiledealer.DAO.CustomerDAO;
import automobiledealer.Model.Others.Customer;
import automobiledealer.UI.MainFrame;
import automobiledealer.UI.ViewCustomerDetail;
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

    // CONSTRUCTOR
    public CustomerController(JFrame view){
        cDAO = new CustomerDAO();
        listCustomer = cDAO.list();
        
        this.view = (MainFrame)view;
        this.view.getCustomer_PanelButton().addActionListener(this);
        this.view.getCustomerForm_Button_add().addActionListener(this);
        this.view.getSearchCustomerButton().addActionListener(this);
        this.view.getEditCustomerButton().addActionListener(this);
        this.view.getDeleteCustomerButton().addActionListener(this);
        this.view.getCustomer_Table().addMouseListener(this);
    }

    // ActionListener Implementation
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.getCustomer_PanelButton()){ // Jika Sidebar Customer Ditekan
            // Pindah halaman dan merubah Visualisasi View
            view.getCardLayout().show(view.getContentPanel(), "CustomerPageContentPanel");
            view.prevMenuButton.setBackground(new Color(255,255,255));
            view.prevMenuButton.setForeground(Color.BLACK);
            view.getCustomer_PanelButton().setBackground(new Color(0,90,192));
            view.getCustomer_PanelButton().setForeground(Color.WHITE);
            view.prevMenuButton = view.getCustomer_PanelButton();
            //Update Models
            RefreshModel();
            //Isit tabel Customer
            CustomerList(view.getCustomer_Table());
            view.getEditCustomerButton().setEnabled(false);
            view.getDeleteCustomerButton().setEnabled(false);
            
        }else if(e.getSource() == view.getSearchCustomerButton()){
            listCustomer = cDAO.SearchCustomerByName(view.getCustomer_TextIInput_search().getText());
            CustomerList(view.getCustomer_Table());
        }else if(e.getSource() == view.getCustomerForm_Button_add()){ //Ketika button add pada Form perform
            //Jika Customer Form nama kosong tampilkan dialog
            if(view.getCustomerForm_TextInput_name().getText().trim().isEmpty()){ 
                JOptionPane.showMessageDialog(view, "Nama Tidak Boleh Kosong", "Dialog", JOptionPane.ERROR_MESSAGE);
            // Jika Customer Form address Kosong tampilkan dialog
            }else if(view.getCustomerForm_TextInput_address().getText().trim().isEmpty()){ 
                JOptionPane.showMessageDialog(view, "Address Tidak Boleh Kosong", "Dialog", JOptionPane.ERROR_MESSAGE);
            }else{
                //Ketika button add pada Form perform menggunakan text add maka insert ke DB, jika menggunakan text update maka update DB
                if("Add".equals(view.getCustomerForm_Button_add().getText())){
                    //Mengecek Apakah Customer dengan nomor telepon sudah ada pada database
                    RefreshModel();
                    int option = JOptionPane.OK_OPTION;
                    Customer customer = ((Customer)listCustomer.stream().filter(c -> view.getCustomerForm_TextInput_phoneNumber().getText().equals(c.getPhoneNumber())).findAny().orElse(null));
                    // Jika customer sudah terdaftar maka tampilkan peringatan bahwa data sudah ada pada database dan akan menggunakan data pada database tersebut
                    if(customer != null){
                        String msg = "Customer Sudah Ada di Database atas Nama : " + customer.getName() +", Customer Akan diganti dengan data yang ada pada database, Apakah anda ingin tetap melanjutkan?";
                        Object[] options ={"Yes", "No"};
                        option = JOptionPane.showOptionDialog(null, msg, "",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
                    }
                    //Jika user menekan tombol yess pada peringatan maka pindah halaman ke Form invoice
                    if(option == JOptionPane.OK_OPTION){
                        view.getCardLayout().show(view.getContentPanel(), "InvoiceFormPanel");                
                    }
                }else if("Update".equals(view.getCustomerForm_Button_add().getText())){ //Ketika button add pada Form perform
                    EditCustomer();
                    view.getCardLayout().show(view.getContentPanel(), "CustomerPageContentPanel");
                }
                //UpdateModel
                RefreshModel();
                //perbaharui Table
                CustomerList(view.getCustomer_Table());
            }
        }else if(e.getSource() == view.getEditCustomerButton()){ //Ketika Edit Customer Button perform
            //Ambil data pada listCustomer sesuai dengan table customer
            selectedCustomer = (Customer)listCustomer.get(view.getCustomer_Table().convertRowIndexToModel(view.getCustomer_Table().getSelectedRow()));
            //Pindah Halamn ke Form Customer
            view.getCardLayout().show(view.getContentPanel(), "CustomerFormPanel");
            //Isi Form sesuai dengan Customer yang telah dipilih
            FillForm();
            //Mengubah text button form menjadi update
            view.getCustomerForm_Button_add().setText("Update");
        }else if(e.getSource() == view.getDeleteCustomerButton()){ //Delete Customer Button
            DeleteCustomer();
            RefreshModel();
            CustomerList(view.getCustomer_Table());
        }
    }

    //Fungsi untuk merefresh Model
    public void RefreshModel(){
        this.listCustomer = cDAO.list();
    }
    
    //Fungsi untuk me-reset Form pada Customer Form
    public void ResetForm(){
        view.getCustomerForm_TextInput_name().setText("");
        view.getCustomerForm_TextInput_address().setText("");
        view.getCustomerForm_TextInput_phoneNumber().setText("");
        view.getCustomerGender().setSelected(view.getCustomerForm_RB_Male().getModel(), true);
    }
    
    //Fungsi untuk Mengisi Form pada Customer Form
    public void FillForm(){
        view.getCustomerForm_TextInput_name().setText(selectedCustomer.getName());
        view.getCustomerForm_TextInput_address().setText(selectedCustomer.getAddress());
        view.getCustomerForm_TextInput_phoneNumber().setText(selectedCustomer.getPhoneNumber());
        if("Female".equals(selectedCustomer.getGender())){
            view.getCustomerGender().setSelected(view.getCustomerForm_RB_Female().getModel(), true);
        }else{
            view.getCustomerGender().setSelected(view.getCustomerForm_RB_Male().getModel(), true);
        }
    }
    
    //Fungsi untuk mengisi Table Customer
    public void CustomerList(JTable table){
        tb = (DefaultTableModel)table.getModel();
        tb.setRowCount(0);  

        for(int i=0;i<listCustomer.size();i++){
            Object[] data = {listCustomer.get(i).getName(),listCustomer.get(i).getAddress(),listCustomer.get(i).getPhoneNumber(), listCustomer.get(i).getGender()};
            tb.addRow(data);
        }
        table.setModel(tb);
    }
   
    //Fungsi untuk melakukan Insert Pada database
    public void InsertCustomer(){
        cDAO.addCustomer(new Customer(0,view.getCustomerForm_TextInput_name().getText(),
                                           view.getCustomerForm_TextInput_address().getText(),
                                           view.getCustomerForm_TextInput_phoneNumber().getText(),
                                           view.getCustomerGender().getSelection().getActionCommand()
        ));
    }
    //Fungsi untuk melakukan Update pada database
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
    //Fungsi untuk melakukan penghapusan pada database
    public void DeleteCustomer(){
        String msg = "Apakah anda yakin ingin menghapus " + listCustomer.get(view.getCustomer_Table().getSelectedRow()).getName()+
                    " dengan ID : "+ listCustomer.get(view.getCustomer_Table().getSelectedRow()).getId()+" ?";
            Object[] options ={"Yes", "Cancel"};
            int option = JOptionPane.showOptionDialog(null, msg, "",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
            if(option == JOptionPane.OK_OPTION){     
                cDAO.deleteCustomer(listCustomer.get(view.getCustomer_Table().getSelectedRow()));    
        }
    }
    
    //Implementasi MouseListener
    @Override
    public void mouseClicked(MouseEvent e) {
        //Jika melakukan klik 2 kali pada row table customer maka tampilkan detail Custommer
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
    public void mousePressed(MouseEvent e){
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
