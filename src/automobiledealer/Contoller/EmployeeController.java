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
import automobiledealer.Model.Employee;
import automobiledealer.Model.Manager;
import automobiledealer.Model.Sales;
import automobiledealer.Model.Technician;
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
    
    public EmployeeController(JFrame view){
        mDAO = new ManagerDAO();
        employeeDAO = new EmployeeDAO();
        listEmployee = employeeDAO.list();
        
        this.view = (MainFrame)view;
        this.view.getEmployee_PanelButton().addActionListener(this);
        this.view.getAddEmployeeButton().addActionListener(this);
        this.view.getEmployeeForm_Button_add().addActionListener(this);
        this.view.getDeleteEmployeeButton().addActionListener(this);
        this.view.getEditEmployeeButton().addActionListener(this);
        this.view.getEmployee_Table().addMouseListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {  
        if(e.getSource()==view.getEmployee_PanelButton()){
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
        
        }else if(e.getSource() == view.getEmployeeForm_Button_add()){
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
                    if("Add".equals(view.getEmployeeForm_Button_add().getText())){
                        InsertEmployee();
                    }else if("Update".equals(view.getEmployeeForm_Button_add().getText())){
                        EditEmployee();
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
        }else if(e.getSource()==view.getAddEmployeeButton()){
            view.getCardLayout().show(view.getContentPanel(), "EmployeeFormPanel");
            resetForm();
            view.getEmployeeForm_Button_add().setText("Add"); 
    
        }else if(e.getSource() == view.getEditEmployeeButton()){
           selectedEmployee = (Employee) listEmployee.get(view.getEmployee_Table().convertRowIndexToModel(view.getEmployee_Table().getSelectedRow()));
           view.getCardLayout().show(view.getContentPanel(), "EmployeeFormPanel");
           
           fillForm();
           view.getEmployeeForm_Button_add().setText("Update");
        }else if(e.getSource() == view.getDeleteEmployeeButton()){
            String msg = "Are you sure want to Delete " + listEmployee.get(view.getEmployee_Table().getSelectedRow()).getName()+
                    " with User ID : "+ listEmployee.get(view.getEmployee_Table().getSelectedRow()).getEmployeeID()+" ?";
            Object[] options ={"Yes", "Cancel"};
            int option = JOptionPane.showOptionDialog(null, msg, "",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
            if(option == JOptionPane.OK_OPTION){     
                mDAO.deleteEmployee(listEmployee.get(view.getEmployee_Table().getSelectedRow()));    
                RefreshModel();
                EmployeeList(view.getEmployee_Table());
            }
        }
    }
   
    @Override
    public void mouseClicked(MouseEvent e) {
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
                mDAO.editEmployee((Manager)selectedEmployee);
                break;
            case "Sales":
                mDAO.editEmployee((Sales)selectedEmployee);
                break;
            case "Technician":
                mDAO.editEmployee((Technician)selectedEmployee);
                break;
       }
    }
    
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
    
    
    public void RefreshModel(){
        this.listEmployee = employeeDAO.list();
    }
    
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
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
       
    }
    
        @Override
    public void mousePressed(MouseEvent e) {
      
    }
    

    
}
