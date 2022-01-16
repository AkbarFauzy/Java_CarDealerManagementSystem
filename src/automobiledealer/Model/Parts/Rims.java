/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.Model.Parts;

/**
 *
 * @author AkbarFauzy
 */
public class Rims extends Parts{
    private int diameter;

    public Rims(String _partsNumber, String _name, String _brand, int _price, String _status, int _diameter) {
        super(_partsNumber, _name, _brand, _price, _status);
        this.diameter = _diameter;
    }
    
    public void setDiameter(int diameter){
        this.diameter = diameter;
    }
    
    public int getDiameter(){
        return this.diameter;
    }
    
}
