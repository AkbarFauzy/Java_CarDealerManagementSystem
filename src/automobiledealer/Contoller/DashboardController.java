/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.Contoller;

import automobiledealer.Model.Employee.Manager;
import automobiledealer.Model.Employee.Sales;
import automobiledealer.Model.Employee.Technician;
import automobiledealer.UI.MainFrame;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 *
 * @author AkbarFauzy
 */
public class DashboardController implements ActionListener {
    MainFrame view;
    EmployeeController EC;
    CustomerController CC;
    VehicleController VC;
    PartController PC;
    InvoiceController IC;
    
    //CONSTRUCTOR
    public DashboardController(JFrame view){
        this.view = (MainFrame) view;
        CreateMenu();
        this.EC = new EmployeeController(this.view);
        this.CC = new CustomerController(this.view);
        this.VC = new VehicleController(this.view);
        this.PC = new PartController(this.view);
        
        if(this.view.loggedUser instanceof Sales){
            this.IC = new InvoiceController(this.view, this.CC, this.EC, null, this.VC);
        }else if(this.view.loggedUser instanceof Technician){
            this.IC = new InvoiceController(this.view, this.CC, this.EC, this.PC, null);
        }else if(this.view.loggedUser instanceof Manager){
            this.IC = new InvoiceController(this.view, this.CC, this.EC, this.PC, this.VC);
        }
        
        this.view.getHome_PanelButton().addActionListener(this);
        RefreshDashboard();
    }
    
    //Fungsi untuk membuat menu berdasarkan Role
    private void CreateMenu(){
        view.getSidebar().removeAll();
        view.getSidebar().add(view.getHome_PanelButton());
        if(view.loggedUser instanceof Sales){
            view.getSidebar().add(view.getCustomer_PanelButton());
            view.getSidebar().add(view.getVehicle_PanelButton());
        }else if(view.loggedUser instanceof Technician){
            view.getSidebar().add(view.getCustomer_PanelButton());
            view.getSidebar().add(view.getParts_PanelButton());
        }else if(view.loggedUser instanceof Manager){
            view.getSidebar().add(view.getEmployee_PanelButton());
            view.getSidebar().add(view.getCustomer_PanelButton());
        }
        view.getSidebar().add(view.getInvoices_PanelButton());
    } 
    
    //Fungsi untuk me-refresh informasi pada halaman home sesuai dengan role
    public void RefreshDashboard(){
        EC.RefreshModel();
        VC.RefreshModel();
        PC.RefreshModel();
        
        if(this.view.loggedUser instanceof Manager){
            view.getHome_Label_count1().setText(EC.getEmployeeCount(0)+"");
            view.getHome_Label_count2().setText(EC.getEmployeeCount(1)+"");
            view.getHome_Label_count3().setText(VC.getCount(0)+"");
            view.getHome_Label_count4().setText(PC.getCount(0)+"");
            
            view.getHome_Label_name1().setText("Sales Employee");
            view.getHome_Label_name2().setText("Technician Employee");
            view.getHome_Label_name3().setText("Available Vehicle");
            view.getHome_Label_name4().setText("Available Spare Part Count");
            view.getAddInvoiceButton().setEnabled(false);
        }else if(this.view.loggedUser instanceof Sales){
            view.getHome_Label_count1().setText(VC.getCount(2)+"");
            view.getHome_Label_count2().setText(VC.getCount(5)+"");
            view.getHome_Label_count3().setText(VC.getCount(3)+"");
            view.getHome_Label_count4().setText(VC.getCount(6)+"");
            
            view.getHome_Label_name1().setText("Available Car");
            view.getHome_Label_name2().setText("Available Truck");
            view.getHome_Label_name3().setText("Sold Car");
            view.getHome_Label_name4().setText("Sold Truck");
            
        }else if(this.view.loggedUser instanceof Technician){
            view.getHome_Label_count1().setText(PC.getCount(1)+"");
            view.getHome_Label_count2().setText(PC.getCount(2)+"");
            view.getHome_Label_count3().setText(PC.getCount(3)+"");
            view.getHome_Label_count4().setText(PC.getCount(4)+"");
            
            view.getHome_Label_name1().setText("Available Rims");
            view.getHome_Label_name2().setText("Available Tire");
            view.getHome_Label_name3().setText("Available Engine");
            view.getHome_Label_name4().setText("Available Mirror");

        }
    
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.getHome_PanelButton()){
            view.getCardLayout().show(view.getContentPanel(), "HomePageContentPanel");
            view.prevMenuButton.setBackground(new Color(255,255,255));
            view.prevMenuButton.setForeground(Color.BLACK);
            view.getHome_PanelButton().setBackground(new Color(0,90,192));
            view.getHome_PanelButton().setForeground(Color.WHITE);
            view.prevMenuButton = view.getHome_PanelButton();
            view.getEditCustomerButton().setEnabled(false);
            view.getDeleteCustomerButton().setEnabled(false);
            RefreshDashboard();
        }
    }
    
}
