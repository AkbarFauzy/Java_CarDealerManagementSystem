/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.Contoller;

import automobiledealer.Model.Manager;
import automobiledealer.Model.Sales;
import automobiledealer.Model.Technician;
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
        }else{
            this.IC = new InvoiceController(this.view, this.CC, this.EC, this.PC, this.VC);
        }
        
        this.view.getHome_PanelButton().addActionListener(this);
        RefreshDashboard();
    }
    
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
            view.getHome_Label_name3().setText("Vehicle Count");
            view.getHome_Label_name4().setText("Spare Part Count");
            
        }else if(this.view.loggedUser instanceof Sales){
        
        }else if(this.view.loggedUser instanceof Technician){
        
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
