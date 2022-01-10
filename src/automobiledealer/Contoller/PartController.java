/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.Contoller;

import automobiledealer.DAO.PartDAO;
import automobiledealer.Model.Parts.CarEngine;
import automobiledealer.Model.Parts.Parts;
import automobiledealer.Model.Parts.RearviewMirror;
import automobiledealer.Model.Parts.Rims;
import automobiledealer.Model.Parts.Tire;
import automobiledealer.UI.MainFrame;
import automobiledealer.UI.ViewPartDetail;
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
public class PartController implements ActionListener, MouseListener{
    Parts p;
    PartDAO partDAO;
    
    List<Parts> listPart;
    MainFrame view;
    DefaultTableModel tb;
    
    Parts selectedPart; 
    
    public PartController(JFrame view){
        partDAO = new PartDAO();
        listPart = partDAO.list();
        
        this.view = (MainFrame)view;
        this.view.getParts_PanelButton().addActionListener(this);
        this.view.getAddPartButton().addActionListener(this);
        this.view.getEditPartButton().addActionListener(this);
        this.view.getDeletePartButton().addActionListener(this);
        this.view.getPartForm_Button_add().addActionListener(this);
        
        this.view.getPart_Table().addMouseListener(this);
    
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.getParts_PanelButton()){
           view.getCardLayout().show(view.getContentPanel(), "PartPageContentPanel");
           view.prevMenuButton = view.getParts_PanelButton();
           RefreshModel();
           PartList(view.getCustomer_Table());
        }else if(e.getSource() == view.getPartForm_Button_add()){
            if(view.getPartForm_TextInput_partNumber().getText().trim().isEmpty()){
                
            }else{
                if("Add".equals(view.getPartForm_Button_add().getText())){
                    InsertPart();
                }else if("Update".equals(view.getPartForm_Button_add().getText())){
                    EditPart();
                }
            }
        }else if(e.getSource() == view.getAddPartButton()){
           view.getCardLayout().show(view.getContentPanel(), "PartFormPanel");
           ResetForm();
           view.getPartForm_Button_add().setText("Add");
        }else if(e.getSource() == view.getEditPartButton()){
           selectedPart = (Parts)listPart.get(view.getPart_Table().convertRowIndexToModel(view.getPart_Table().getSelectedRow()));
           view.getCardLayout().show(view.getContentPanel(),"PartFormPanel");
           FillForm();
           view.getPartForm_Button_add().setText("Update");
        }else if(e.getSource() == view.getDeletePartButton()){
           DeletePart();
        }
      
    }
    
   
    public void RefreshModel(){
        this.listPart = partDAO.list();
    }
    
    public void PartList(JTable table){
        tb = (DefaultTableModel)table.getModel();
        tb.setRowCount(0);  

        for(int i=0;i<listPart.size();i++){
            Object[] data = {listPart.get(i).getPartsNumber(),listPart.get(i).getName(),listPart.get(i).getBrand(), listPart.get(i).getPrice()};
            tb.addRow(data);
        }
        table.setModel(tb);
        
    }
    
    public void ResetForm(){
        view.getPartForm_ComboBox_partType().setEnabled(true);
        view.getPartForm_ComboBox_partType().setSelectedIndex(0);
        view.getPartForm_TextInput_partNumber().setText("");
        view.getPartForm_TextInput_name().setText("");
        view.getPartForm_TextInput_brand().setText("");
        view.getPartForm_Spinner_Price().setValue(0);
        view.getPartForm_Spinner_diameter().setValue(0);
        view.getPartForm_Spinner_width().setValue(0);
        view.getPartForm_Spinner_capacity().setValue(0);
        view.getPartForm_Spinner_cylinder().setValue(0);
        view.getPartForm_TextInput_type().setText("");
        
    }
    
    public void FillForm(){
        view.getPartForm_ComboBox_partType().setSelectedIndex(0);
        view.getPartForm_TextInput_partNumber().setText(selectedPart.getPartsNumber());
        view.getPartForm_TextInput_name().setText(selectedPart.getName());
        view.getPartForm_TextInput_brand().setText(selectedPart.getBrand());
        view.getPartForm_Spinner_Price().setValue(selectedPart.getPrice());
       
        if(selectedPart instanceof Rims){        
            view.getPartForm_Spinner_diameter().setValue(((Rims)selectedPart).getDiameter());
        }else if(selectedPart instanceof RearviewMirror){   
            view.getPartForm_TextInput_type().setText(((RearviewMirror)selectedPart).getType());
        }else if(selectedPart instanceof CarEngine){
            view.getPartForm_Spinner_capacity().setValue(((CarEngine)selectedPart).getCapacity());
            view.getPartForm_Spinner_cylinder().setValue(((CarEngine)selectedPart).getNumCylinder());            
        }else if(selectedPart instanceof Tire){
            view.getPartForm_Spinner_diameter().setValue(((Tire)selectedPart).getDiameter());
            view.getPartForm_Spinner_width().setValue(((Tire)selectedPart).getWidth());
            view.getPartForm_TextInput_type().setText(((Tire)selectedPart).getType());
        }
    }
    
    public void InsertPart(){
        switch(view.getPartForm_ComboBox_partType().getSelectedItem().toString()){
            case "Rims":
                partDAO.addPart(new Rims(view.getPartForm_TextInput_partNumber().getText(),
                                        view.getPartForm_TextInput_name().getText(),
                                        view.getPartForm_TextInput_brand().getText(),
                                        (Integer)view.getPartForm_Spinner_Price().getValue(),
                                        (Integer)view.getPartForm_Spinner_diameter().getValue()
                                ));
                break;
            case "Rearview Mirror":
                partDAO.addPart(new RearviewMirror(view.getPartForm_TextInput_partNumber().getText(),
                                                    view.getPartForm_TextInput_name().getText(),
                                                    view.getPartForm_TextInput_brand().getText(),
                                                   (Integer)view.getPartForm_Spinner_Price().getValue(),
                                                    view.getPartForm_TextInput_type().getText()
                                ));
                break;
            case "Engine":
                partDAO.addPart(new CarEngine(view.getPartForm_TextInput_partNumber().getText(),
                                            view.getPartForm_TextInput_name().getText(),
                                            view.getPartForm_TextInput_brand().getText(),
                                            (Integer)view.getPartForm_Spinner_Price().getValue(),
                                            (Integer)view.getPartForm_Spinner_capacity().getValue(),
                                            (Integer)view.getPartForm_Spinner_cylinder().getValue()     
                                ));
                break;
            case "Tire":
                partDAO.addPart(new Tire(view.getPartForm_TextInput_partNumber().getText(),
                                        view.getPartForm_TextInput_name().getText(),
                                        view.getPartForm_TextInput_brand().getText(),
                                        (Integer)view.getPartForm_Spinner_Price().getValue(),
                                        (Integer)view.getPartForm_Spinner_diameter().getValue(),
                                        (Integer)view.getPartForm_Spinner_width().getValue(),
                                        view.getPartForm_TextInput_type().getText()
                                ));
                break;
        
        }
    }
    
    public void EditPart(){
        view.getPartForm_ComboBox_partType().setEnabled(false);
        selectedPart.setPartsNumber(view.getPartForm_TextInput_partNumber().getText());
        selectedPart.setName(view.getPartForm_TextInput_name().getText());
        selectedPart.setBrand(view.getPartForm_TextInput_brand().getText());
        selectedPart.setPrice((Integer)view.getPartForm_Spinner_Price().getValue());
        
        if(selectedPart instanceof Rims){        
            ((Rims)selectedPart).setDiameter((Integer)view.getPartForm_Spinner_diameter().getValue());
        }else if(selectedPart instanceof RearviewMirror){
            ((RearviewMirror)selectedPart).setType(view.getPartForm_TextInput_type().getText());
        }else if(selectedPart instanceof CarEngine){
            ((CarEngine)selectedPart).setCapacity((Integer)view.getPartForm_Spinner_capacity().getValue());
            ((CarEngine)selectedPart).setNumCylinder((Integer)view.getPartForm_Spinner_cylinder().getValue());           
        }else if(selectedPart instanceof Tire){
            ((Tire)selectedPart).setDiameter((Integer)view.getPartForm_Spinner_diameter().getValue());
            ((Tire)selectedPart).setWidth((Integer)view.getPartForm_Spinner_width().getValue());
            ((Tire)selectedPart).setType( view.getPartForm_TextInput_type().getText());
        }   
    }
    
    public void DeletePart(){
        String msg = "Are you sure want to Delete " + listPart.get(view.getPart_Table().getSelectedRow()).getName()+
                    " with NIK : "+ listPart.get(view.getCustomer_Table().getSelectedRow()).getPartsNumber()+" ?";
            Object[] options ={"Yes", "Cancel"};
            int option = JOptionPane.showOptionDialog(null, msg, "",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,null,options,options[0]);
            if(option == JOptionPane.OK_OPTION){     
                partDAO.deletePart(listPart.get(view.getPart_Table().getSelectedRow()).getPartsNumber());    
        }
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 && e.getSource() == view.getPart_Table()){
            ViewPartDetail F = new ViewPartDetail(view, true, listPart.get(view.getPart_Table().getSelectedRow()));
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
