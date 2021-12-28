/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.object.Parts;

/**
 *
 * @author AkbarFauzy
 */
public class Tire extends Parts{
    private int diameter;
    private int width;
    private String type;

    public Tire(String _partsNumber, String _name, String _brand, int _price, int _diameter, int _width, String _type) {
        super(_partsNumber, _name, _brand, _price);
        this.diameter = _diameter;
        this.width = _width;
        this.type = _type;
    }
    
    
    
}
