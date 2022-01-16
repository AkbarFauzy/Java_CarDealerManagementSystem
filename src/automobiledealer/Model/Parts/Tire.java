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
public class Tire extends Parts{
    private int diameter;
    private int width;
    private String type;

    public Tire(String _partsNumber, String _name, String _brand, int _price, String _status, int _diameter, int _width, String _type) {
        super(_partsNumber, _name, _brand, _price, _status);
        this.diameter = _diameter;
        this.width = _width;
        this.type = _type;
    }

    public int getDiameter() {
        return diameter;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
    
}
