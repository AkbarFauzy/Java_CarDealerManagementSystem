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
public class CarEngine extends Parts{
    private String capacity;
    private int numCylinder;
    
    public CarEngine(String _partsNumber, String _name, String _brand, int _price, String _capacity, int _numCylinder){
        super(_partsNumber, _name, _brand, _price);
        this.capacity = _capacity;
        this.numCylinder = _numCylinder;
    }
    
    

}
