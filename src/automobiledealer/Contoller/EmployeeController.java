/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.Contoller;

import automobiledealer.DAO.EmployeeDAO;
import automobiledealer.DAO.ManagerDAO;
import automobiledealer.UI.MainFrame;
import automobiledealer.UI.ViewEmployeeDetail;
import automobiledealer.Model.Employee.Employee;
import automobiledealer.Model.Employee.Manager;
import automobiledealer.Model.Employee.Sales;
import automobiledealer.Model.Employee.Technician;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AkbarFauzy
 */
public class EmployeeController implements ActionListener, MouseListener{
    Employee m;
    EmployeeDAO employeeDAO;
    ManagerDAO mDAO;
    
    List<Employee> listEmployee;
    MainFrame view;
    DefaultTableModel tb;
    
    Employee selectedEmployee;
    
    //CONSTRUCTOR
    public EmployeeController(JFrame view){
        mDAO = new ManagerDAO();
        employeeDAO = new EmployeeDAO();
        listEmployee = employeeDAO.list();
        
        this.view = (MainFrame)view;
        this.view.getEmployee_PanelButton().addActionListener(this);
        this.view.getAddEmployeeButton().addActionListener(this);
        this.view.getSearchEmployeeButton().addActionListener(this);
        this.view.getEmployeeForm_Button_add().addActionListener(this);
        this.view.getDeleteEmployeeButton().addActionListener(this);
        this.view.getEditEmployeeButton().addActionListener(this);
        this.view.getEmployee_Table().addMouseListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {  
        if(e.getSource()==view.getEmployee_PanelButton()){ //Ketika tiombol employee pada sidebarr di tekan
            view.getCardLayout().show(view.getContentPanel(), "EmployeePageContentPanel");
            view.prevMenuButton.setBackground(new Color(255,255,255));
            view.prevMenuButton.setForeground(Color.BLACK);    
            view.getEmployee_PanelButton().setBackground(new Color(0,90,192));
            view.getEmployee_PanelButton().setForeground(Color.WHITE);           
            view.prevMenuButton = view.getEmployee_PanelButton();
            RefreshModel();
            EmployeeList(view.getEmployee_Table());
            view.getEditEmployeeButton().setEnabled(false);
            view.getDeleteEmployeeButton().setEnabled(false);
        
        }else if(e.getSource() == view.getSearchEmployeeButton()){ //Ketika tombol search pada halaman Employee ditekan
            listEmployee = employeeDAO.SearchEmployeeByName(view.getEmployee_TextIInput_search().getText());
            EmployeeList(view.getEmployee_Table());
        }else if(e.getSource() == view.getEmployeeForm_Button_add()){ // Ketika tomboll add pada Form Employee ditekan
            //Memvalidasi input
            if(view.getEmployeeForm_TextInput_username().getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(view, "Username Tidak Boleh Kosong", "Dialog", JOptionPane.ERROR_MESSAGE);
            }else if(view.getEmployeeForm_TextInput_password().getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(view, "Password Tidak Boleh Kosong", "Dialog", JOptionPane.ERROR_MESSAGE);
            }else if(view.getEmployeeForm_TextInput_name().getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(view, "Nama Tidak Boleh Kosong", "Dialog", JOptionPane.ERROR_MESSAGE);
            }else if(!view.getEmployeeForm_TextInput_password().getText().equals(view.getEmployeeForm_TextInput_confirmPassword().getText())){
                JOptionPane.showMessageDialog(view, "Password tidak sama", "Dialog", JOptionPane.ERROR_MESSAGE);
            }else{
                try{
                    if("Add".equals(view.getEmployeeForm_Button_add().getText())){ //Ketika Form pada mode insert
                        if(!employeeDAO.SearchEmployeeByUsername(view.getEmployeeForm_TextInput_username().getText()).isEmpty()){
                            JOptionPane.showMessageDialog(view, "Username Sudah diambil", "Dialog", JOptionPane.ERROR_MESSAGE);
                        }else{
                            InsertEmployee();
                        }
                    }else if("Update".equals(view.getEmployeeForm_Button_add().getText())){ //Ketika Form pada mode edit
                         if(!employeeDAO.SearchEmployeeByUsername(view.getEmployeeForm_TextInput_username().getText(), selectedEmployee.getUsername()).isEmpty()){
                            JOptionPane.showMessageDialog(view, "Username Sudah diambil", "Dialog", JOptionPane.ERROR_MESSAGE);
                        }else{
                            EditEmployee();
                        }
                    }                     
                    view.getCardLayout().show(view.getContentPanel(), "EmployeePageContentPanel");                 
                    }catch(ClassCastException ex){
                        JOptionPane.showMessageDialog(view, "Restricted Action", "Dialog", JOptionPane.ERROR_MESSAGE);
                    }catch(HeadlessException | ParseException ex){
                        JOptionPane.showMessageDialog(view, ex, "Dialog", JOptionPane.ERROR_MESSAGE);
                    }finally{
                        RefreshModel();
                        EmployeeList(view.getEmployee_Table());
                    }           
            }
        }else if(e.getSource()==view.getAddEmployeeButton()){ //Ketika Tombol add employee pada halaman employee ditekan maka pindah halaman ke form mode insert
            //Pindah halaman ke employee form
            view.getCardLayout().show(view.getContentPanel(), "EmployeeFormPanel");
            //Reset Form
            resetForm();
            //Ubah text pada tombol form menjadi add
            view.getEmployeeForm_Button_add().setText("Add"); 
    
        }else if(e.getSource() == view.getEditEmployeeButton()){ //Ketika Tombol edit pada halaman employee ditekan maka pindah halaman ke form mode edit
           //Mendapatkan objek Employee 
           selectedEmployee = (Employee) listEmployee.get(view.getEmployee_Table().convertRowIndexToModel(view.getEmployee_Table().getSelectedRow()));
           //Pindah halaman ke employee form
           view.getCardLayout().show(view.getContentPanel(), "EmployeeFormPanel");   
           //Mengisi form dengan objek employee yang telah dipilih
           fillForm();
           //Ubah text pada tombol form menjadi update
           view.getEmployeeForm_Button_add().setText("Update");
        }else if(e.getSource() == view.getDeleteEmployeeButton()){//Ketika Tombol delete pada halaman employee ditekan
            //Melakukan Konfirmasi penghapusan data
            String msg = "Apakah anda yakin ingin menghapus Employee " + listEmployee.get(view.getEmployee_Table().getSelectedRow()).getName()+
                    " dengan User ID : "+ listEmployee.get(view.getEmployee_Table().getSelectedRow()).getEmployeeID()+" ?";
            Object[] options ={"Yes", "Cancel"};
            int option = JOptionPane.showOptionDialog(null, msg, "",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
            if(option == JOptionPane.OK_OPTION){     
                mDAO.deleteEmployee(listEmployee.get(view.getEmployee_Table().getSelectedRow()));    
                RefreshModel();
                EmployeeList(view.getEmployee_Table());
            }
        }
    }
   
    //Implementasi Mouse Listener
    @Override
    public void mouseClicked(MouseEvent e) { //Ketika melakukan Klik
        // Jika melakukan Klik 2 kalai menggunakan key M1 pada table employee
        if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 && e.getSource() == view.getEmployee_Table()){ 
            ViewEmployeeDetail F = new ViewEmployeeDetail(view, true, listEmployee.get(view.getEmployee_Table().getSelectedRow()));
            F.addWindowListener(new java.awt.event.WindowAdapter(){
            @Override
            public void windowClosing(java.awt.event.WindowEvent e){
                F.dispose();
            }
            
            });
            F.setVisible(true);
        }
    }

    //Fungsi untuk mengisi table dengan employee
    public void EmployeeList(JTable table){
        tb = (DefaultTableModel)table.getModel();
        tb.setRowCount(0);  
        Object[]object = new Object[4];
        for(int i=0;i<listEmployee.size();i++){
            object[0] = listEmployee.get(i).getName();
            object[1] = listEmployee.get(i).getClass().getSimpleName();
            object[2] = listEmployee.get(i).dateOfBirth();
            object[3] = listEmployee.get(i).getGender();
            tb.addRow(object);
        }
        table.setModel(tb);
    }
    
    //Fungsi untuk memasukkan employee ke database
    public void InsertEmployee() throws ParseException{
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(view.getEmployeeForm_ComboBox_dd().getSelectedItem().toString()+"/"+
                                                            (view.getEmployeeForm_ComboBox_mm().getSelectedIndex()+1) +"/"+
                                                            view.getEmployeeForm_ComboBox_yyyy().getSelectedItem().toString());

        switch(view.getEmployeePosition().getSelection().getActionCommand()){
            case "Manager":
                mDAO.addEmployee(new Manager(0, view.getEmployeeForm_TextInput_username().getText(),
                                                                   view.getEmployeeForm_TextInput_password().getText(),
                                                                   view.getEmployeeForm_TextInput_name().getText(),
                                                                   date,
                                                                   view.getEmployeeGender().getSelection().getActionCommand()));
                break;
            case "Sales":
                mDAO.addEmployee(new Sales(0, view.getEmployeeForm_TextInput_username().getText(),
                                                                   view.getEmployeeForm_TextInput_password().getText(),
                                                                   view.getEmployeeForm_TextInput_name().getText(),
                                                                   date,
                                                                   view.getEmployeeGender().getSelection().getActionCommand()));
                break;
            case "Technician":
                mDAO.addEmployee(new Technician(0, view.getEmployeeForm_TextInput_username().getText(),
                                                                   view.getEmployeeForm_TextInput_password().getText(),
                                                                   view.getEmployeeForm_TextInput_name().getText(),
                                                                   date,
                                                                   view.getEmployeeGender().getSelection().getActionCommand()));
           break;
        }
    }
    
    //Fungsi untuk mengupdate employee ke database
    public void EditEmployee() throws ParseException{
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse(view.getEmployeeForm_ComboBox_dd().getSelectedItem().toString()+"/"+
                                                            (view.getEmployeeForm_ComboBox_mm().getSelectedIndex()+1) +"/"+
                                                            view.getEmployeeForm_ComboBox_yyyy().getSelectedItem().toString());
        
        selectedEmployee.setUsername(view.getEmployeeForm_TextInput_username().getText());
        selectedEmployee.setPassword(view.getEmployeeForm_TextInput_password().getText());
        selectedEmployee.setName(view.getEmployeeForm_TextInput_name().getText());
        selectedEmployee.setDateOfBirth(date);
        selectedEmployee.setGender(view.getEmployeeGender().getSelection().getActionCommand());
        
        switch(view.getEmployeePosition().getSelection().getActionCommand()){
            case "Manager":
                mDAO.editEmployee(new Manager(selectedEmployee.getEmployeeID(), 
                                            view.getEmployeeForm_TextInput_username().getText(),
                                            view.getEmployeeForm_TextInput_password().getText(),
                                            view.getEmployeeForm_TextInput_name().getText(),
                                           date,
                                           view.getEmployeeGender().getSelection().getActionCommand()));
                break;
            case "Sales":
                mDAO.editEmployee(new Sales(selectedEmployee.getEmployeeID(),
                                           view.getEmployeeForm_TextInput_username().getText(),
                                           view.getEmployeeForm_TextInput_password().getText(),
                                           view.getEmployeeForm_TextInput_name().getText(),
                                           date,
                                           view.getEmployeeGender().getSelection().getActionCommand()));
                break;
            case "Technician":
                mDAO.editEmployee(new Technician(selectedEmployee.getEmployeeID(), 
                                               view.getEmployeeForm_TextInput_username().getText(),
                                               view.getEmployeeForm_TextInput_password().getText(),
                                               view.getEmployeeForm_TextInput_name().getText(),
                                               date,
                                               view.getEmployeeGender().getSelection().getActionCommand()));
           break;
       }
    }
    
    //Fungsi untuk me-reset form
    public void resetForm(){
        view.getEmployeeForm_TextInput_username().setText("");
        view.getEmployeeForm_TextInput_name().setText("");
        view.getEmployeeForm_TextInput_password().setText("");
        view.getEmployeeForm_TextInput_confirmPassword().setText("");
        view.getEmployeePosition().setSelected(view.getEmployeeForm_RB_Manager().getModel(), true);
        view.getEmployeeGender().setSelected(view.getEmployeeForm_RB_Male().getModel(), true);
        view.getEmployeeForm_ComboBox_dd().setSelectedIndex(0);
        view.getEmployeeForm_ComboBox_mm().setSelectedIndex(0);
        view.getEmployeeForm_ComboBox_yyyy().setSelectedIndex(0);
    }
    
    //Fungsi untuk mengisi form
    public void fillForm(){
        if(selectedEmployee instanceof Manager){
            view.getEmployeePosition().setSelected(view.getEmployeeForm_RB_Manager().getModel(), true);
        }else if (selectedEmployee instanceof Sales){
            view.getEmployeePosition().setSelected(view.getEmployeeForm_RB_Sales().getModel(), true);
        }else if(selectedEmployee instanceof Technician){
            view.getEmployeePosition().setSelected(view.getEmployeeForm_RB_Technician().getModel(), true);
        }
           
       if("Female".equals(selectedEmployee.getGender())){
            view.getEmployeeGender().setSelected(view.getEmployeeForm_RB_Female().getModel(), true);
        }else {
            view.getEmployeeGender().setSelected(view.getEmployeeForm_RB_Male().getModel(), true);
        }
           
        view.getEmployeeForm_TextInput_username().setText(selectedEmployee.getUsername());
        view.getEmployeeForm_TextInput_name().setText(selectedEmployee.getName());
        view.getEmployeeForm_TextInput_password().setText(selectedEmployee.getPassword());
        view.getEmployeeForm_TextInput_confirmPassword().setText("");
           
        Calendar cal = Calendar.getInstance();
        cal.setTime(selectedEmployee.dateOfBirth());
        view.getEmployeeForm_ComboBox_yyyy().setSelectedItem(cal.get(Calendar.YEAR)+"");
        view.getEmployeeForm_ComboBox_mm().setSelectedIndex(cal.get(Calendar.MONTH));
        view.getEmployeeForm_ComboBox_dd().setSelectedIndex(cal.get(Calendar.DAY_OF_MONTH - 1));
    }
    
    //Fungsi untuk merefresh model
    public void RefreshModel(){
        this.listEmployee = employeeDAO.list();
    }
    
    //Fungsi untuk Mendapatkan jumlah Employee
    // jika x == 0 maka tampilkan jumlah sales
    // jika x == 1 maka tampilkan jumlah technician
    public int getEmployeeCount(int x){
        if(x == 0){
            return employeeDAO.salesCount();
        }else if(x == 1){
            return employeeDAO.technicianCount();
        }
        return 0;
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
    
    @Override
    public void mousePressed(MouseEvent e) {
      //DO NOTHING
    }
    

    
}
