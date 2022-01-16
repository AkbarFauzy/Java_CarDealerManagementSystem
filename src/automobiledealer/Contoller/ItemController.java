/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.Contoller;

import automobiledealer.UI.ItemDialog;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author AkbarFauzy
 */
public class ItemController implements ActionListener, MouseListener    {
    
    ItemDialog view;

    public ItemController(Dialog _view){
        this.view = (ItemDialog) _view;
        view.getItemDialog_Button_add().addActionListener(this);
        view.getItemDialog_Button_search().addActionListener(this);
        view.getItem_Table().addMouseListener(this);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == view.getItemDialog_Button_add()){  
            view.setSelectedItem(view.getItem_Table().getSelectedRow());
            view.dispose();
        }else if(e.getSource() == view.getItemDialog_Button_search()){
        
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1 && e.getSource() == view.getItem_Table()){
        
            
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
}
