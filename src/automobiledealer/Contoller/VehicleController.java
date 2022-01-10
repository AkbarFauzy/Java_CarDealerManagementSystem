/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.Contoller;

import automobiledealer.DAO.VehicleDAO;
import automobiledealer.Model.Vehicle.Car;
import automobiledealer.Model.Vehicle.CarModel;
import automobiledealer.Model.Vehicle.Truck;
import automobiledealer.Model.Vehicle.Vehicle;
import automobiledealer.UI.MainFrame;
import automobiledealer.UI.ViewVehicleDetail;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
public class VehicleController implements ActionListener, MouseListener{
    DefaultTableModel tb = new DefaultTableModel();
    
    VehicleDAO vDao;
    MainFrame view;
    List<Vehicle> listVehicle;
    
    Vehicle selectedVehicle;
    
    public VehicleController(JFrame _view){
        vDao = new VehicleDAO();
        listVehicle = vDao.list();
        
        this.view = (MainFrame) _view;
        view.getVehicle_PanelButton().addActionListener(this);
        view.getAddVehicleButton().addActionListener(this);
        view.getEditVehicleButton().addActionListener(this);
        view.getDeleteVehicleButton().addActionListener(this);
        view.getVehicleForm_Button_add().addActionListener(this);
        view.getVehicle_Table().addMouseListener(this);
        
        view.getVehicleForm_ComboBox_carmodel().setModel(new DefaultComboBoxModel<>(CarModel.values()));
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.getVehicle_PanelButton()){
            view.getCardLayout().show(view.getContentPanel(), "VehiclePageContentPanel");
            //        Vehicle_PanelButton1.setBackground(new Color(0,90,192));
            //        Vehicle_PanelButton1.getComponent(0).setForeground(Color.WHITE);
            //        prevMenuButton.setBackground(new Color(255,255,255));
            //        prevMenuButton.getComponent(0).setForeground(Color.BLACK);
            view.prevMenuButton = view.getVehicle_PanelButton();
            RefreshModel();
            VehicleList(view.getVehicle_Table());
        }else if(e.getSource() == view.getAddVehicleButton()){
            String msg = "";
            Object[] options ={"Car", "Truck"};
            int option = JOptionPane.showOptionDialog(null, msg, "",JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
            
            if(option == 0){ 
                view.getVehicleForm_ComboBox_carmodel().setEnabled(true);
                view.getVehicleForm_Spinner_truckloadcapacity().setEnabled(false);
                view.getCardLayout().show(view.getContentPanel(), "VehicleFormPanel");
                ResetForm();
                view.getVehicleForm_Button_add().setText("Add");
            
            }else if(option == 1){
                view.getVehicleForm_ComboBox_carmodel().setEnabled(false);
                view.getVehicleForm_Spinner_truckloadcapacity().setEnabled(true);
                view.getCardLayout().show(view.getContentPanel(), "VehicleFormPanel");
                ResetForm();
                view.getVehicleForm_Button_add().setText("Add");
            
            }
            
        
             
        }else if(e.getSource() == view.getVehicleForm_Button_add()){
            if(view.getVehicleForm_TextInput_registerNumber().getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(view, "Register Number Tidak Boleh Kosong", "Dialog", JOptionPane.ERROR_MESSAGE);
            }else if(view.getVehicleForm_TextInput_name().getText().trim().isEmpty()){
                JOptionPane.showMessageDialog(view, "Nama Kendaraan Tidak Boleh Kosong", "Dialog", JOptionPane.ERROR_MESSAGE);
            }else{
                if("Add".equals(view.getVehicleForm_Button_add().getText())){
                    InsertVehicle();
                    view.getCardLayout().show(view.getContentPanel(), "VehiclePageContentPanel");
                }else if("Update".equals(view.getVehicleForm_Button_add().getText())){
                    EditVehicle();
                    view.getCardLayout().show(view.getContentPanel(), "VehiclePageContentPanel");
                }
            }
        }else if(e.getSource() == view.getEditVehicleButton()){
            selectedVehicle = listVehicle.get(view.getVehicle_Table().getSelectedRow());
            if( selectedVehicle instanceof Car){
            
            }else if(selectedVehicle instanceof Truck){
            
            }
        }else if(e.getSource() == view.getDeleteVehicleButton()){
            DeleteVehicle();
        }
    }
    
    public void VehicleList(JTable table){
        tb = (DefaultTableModel)table.getModel();
        tb.setRowCount(0);  

        for(int i=0;i<listVehicle.size();i++){
            Object[] data = {listVehicle.get(i).getName(),listVehicle.get(i).getBrand(),listVehicle.get(i).getColor(), listVehicle.get(i).getPrice()};
            tb.addRow(data);
        }
        table.setModel(tb);
    }
    
    public void RefreshModel(){
        this.listVehicle = vDao.list();
    }
    
    public void ResetForm(){
        view.getVehicleForm_TextInput_name().setText("");
        view.getVehicleForm_TextInput_brand().setText("");
        view.getVehicleForm_TextInput_color().setText("");
        view.getVehicleForm_Spinner_numWheels().setValue(0);
        view.getVehicleForm_Spinner_numDoors().setValue(0);
        view.getVehicleForm_Spinner_weight().setValue(0);
        view.getVehicleForm_Spinner_horsePower().setValue(0);
        view.getVehicleForm_Spinner_truckloadcapacity().setValue(0);
        view.getVehicleForm_ComboBox_fueltype().setSelectedIndex(0);
        view.getVehicleForm_ComboBox_fueltype().setSelectedIndex(0);
    }
    
    public void InsertVehicle(){
        if((Integer) view.getVehicleForm_Spinner_truckloadcapacity().getValue() != 0 && !view.getVehicleForm_ComboBox_carmodel().isEnabled()){
            vDao.addVehicle(new Truck(view.getVehicleForm_TextInput_registerNumber().getText(),
                                      view.getVehicleForm_TextInput_name().getText(),
                                      view.getVehicleForm_TextInput_brand().getText(),
                                      view.getVehicleForm_TextInput_color().getText(),
                                      (Integer) view.getVehicleForm_Spinner_numWheels().getValue(),
                                      (Integer) view.getVehicleForm_Spinner_weight().getValue(),
                                      (Integer) view.getVehicleForm_Spinner_numDoors().getValue(),
                                      view.getVehicleForm_TextInput_transmition().getText(),
                                      (Integer) view.getVehicleForm_Spinner_price().getValue(),
                                      view.getVehicleForm_ComboBox_fueltype().getSelectedItem().toString(),
                                      (Integer) view.getVehicleForm_Spinner_horsePower().getValue(),
                                      (Double) view.getVehicleForm_Spinner_truckloadcapacity().getValue()
            ));
        }else{
            vDao.addVehicle(new Car(view.getVehicleForm_TextInput_registerNumber().getText(),
                                    view.getVehicleForm_TextInput_name().getText(),
                                    view.getVehicleForm_TextInput_brand().getText(),
                                    view.getVehicleForm_TextInput_color().getText(),
                                    (Integer) view.getVehicleForm_Spinner_numWheels().getValue(),
                                    (Integer) view.getVehicleForm_Spinner_weight().getValue(),
                                    (Integer) view.getVehicleForm_Spinner_numDoors().getValue(),
                                    view.getVehicleForm_TextInput_transmition().getText(),
                                    (Integer) view.getVehicleForm_Spinner_price().getValue(),
                                    view.getVehicleForm_ComboBox_fueltype().getSelectedItem().toString(),
                                    (Integer) view.getVehicleForm_Spinner_horsePower().getValue(),
                                    CarModel.valueOf(view.getVehicleForm_ComboBox_carmodel().getSelectedItem().toString())
            ));
        }
    }
    
    public void EditVehicle(){
        selectedVehicle.setRegistrationNumber(view.getVehicleForm_TextInput_registerNumber().getText());
        selectedVehicle.setName(view.getVehicleForm_TextInput_name().getText());
        selectedVehicle.setBrand(view.getVehicleForm_TextInput_brand().getText());
        selectedVehicle.setColor(view.getVehicleForm_TextInput_color().getText());
        selectedVehicle.setNumWheel((Integer)view.getVehicleForm_Spinner_numWheels().getValue());
        selectedVehicle.setWeight((Integer)view.getVehicleForm_Spinner_weight().getValue());
        selectedVehicle.setNumDoors((Integer)view.getVehicleForm_Spinner_numDoors().getValue());
        selectedVehicle.setTransmission(view.getVehicleForm_TextInput_transmition().getText());
        selectedVehicle.setPrice((Integer) view.getVehicleForm_Spinner_price().getValue());
        selectedVehicle.setFuelType(view.getVehicleForm_ComboBox_fueltype().getSelectedItem().toString());
        selectedVehicle.setHorsePower((Integer) view.getVehicleForm_Spinner_horsePower().getValue());
        
        if(selectedVehicle instanceof Car){
            ((Car)selectedVehicle).setCarModel(CarModel.valueOf(view.getVehicleForm_ComboBox_carmodel().getSelectedItem().toString()));
            vDao.editVehicle((Car)selectedVehicle);
        }else{
            ((Truck)selectedVehicle).setLoadCapacity((Double) view.getVehicleForm_Spinner_truckloadcapacity().getValue());
            vDao.editVehicle((Truck)selectedVehicle);
        }
    }
    
    public void DeleteVehicle(){
        String msg = "Are you sure want to Delete " + listVehicle.get(view.getVehicle_Table().getSelectedRow()).getName()+
                    " with NIK : "+ listVehicle.get(view.getVehicle_Table().getSelectedRow()).getRegistrationNumber()+" ?";
        Object[] options ={"Yes", "Cancel"};
        int option = JOptionPane.showOptionDialog(null, msg, "",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
        if(option == JOptionPane.OK_OPTION){     
            vDao.deleteVehicle(listVehicle.get(view.getVehicle_Table().getSelectedRow()).getRegistrationNumber());
            RefreshModel();
        }
    }   
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 && e.getSource() == view.getVehicle_Table()){
            ViewVehicleDetail F = new ViewVehicleDetail(view, true, listVehicle.get(view.getVehicle_Table().getSelectedRow()));
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
