/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.Contoller;

import automobiledealer.Model.Parts.Parts;
import automobiledealer.Model.Vehicle.Vehicle;
import automobiledealer.UI.ItemDialog;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author AkbarFauzy
 */
public class ItemController implements ActionListener {
    
    ItemDialog view;
    
    //CONSTRUCTOR
    public ItemController(Dialog _view){
        this.view = (ItemDialog) _view;
        view.getItemDialog_Button_add().addActionListener(this);
        view.getItemDialog_Button_search().addActionListener(this);
    }
    
    //IMPLEMENTASI ACTIONLISTENER
    @Override
    public void actionPerformed(ActionEvent e) { 
        if(e.getSource() == view.getItemDialog_Button_add()){ //Ketika tombol add pada ItemDialog ditekan  
            view.setSelectedItem(view.getItem_Table().getSelectedRow());
            view.dispose();
        }else if(e.getSource() == view.getItemDialog_Button_search()){ //Ketika tombol search pada ItemDialog ditekan
            DefaultTableModel tb = (DefaultTableModel)view.getItem_Table().getModel();
            tb.setRowCount(0);

            if(view.getVC() != null){ //Jika ItemDialog memiliki Vehicle Controller Tampilkan kendaraan yang berstatus "Ready"
                if(view.getItemDialog_TextInput_search().getText().trim().isEmpty()){
                    view.getVC().EligibleVehicle();
                }else {
                     view.getVC().SearchVehicle(view.getItemDialog_TextInput_search().getText());
                }
                for(int i=0;i < view.getVC().listVehicle.size();i++){
                    Object[] data = {((Vehicle)view.getVC().listVehicle.get(i)).getRegistrationNumber(), ((Vehicle)view.getVC().listVehicle.get(i)).getName(), ((Vehicle)view.getVC().listVehicle.get(i)).getPrice()};
                    tb.addRow(data);
                }
       
            }else if(view.getPC() != null){ // Jika ItemDialog memiliki Part Controller Tampilkan spare part yang berstatus "Ready"
                if(view.getItemDialog_TextInput_search().getText().trim().isEmpty()){
                    view.getPC().EligibleParts();
                }else{
                    view.getPC().SearchPart(view.getItemDialog_TextInput_search().getText());
                }
                for(int i=0;i < view.getPC().listPart.size();i++){
                    Object[] data = {((Parts)view.getPC().listPart.get(i)).getPartsNumber(), ((Parts)view.getPC().listPart.get(i)).getName(), ((Parts)view.getPC().listPart.get(i)).getPrice()};
                    tb.addRow(data);
                }
            }
            view.getItem_Table().setModel(tb);
        }
    }
}
