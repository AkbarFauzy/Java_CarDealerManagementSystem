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
public class CarEngine extends Parts{
    private int capacity;
    private int numCylinder;
    
    public CarEngine(String _partsNumber, String _name, String _brand, int _price, int _capacity, int _numCylinder){
        super(_partsNumber, _name, _brand, _price);
        this.capacity = _capacity;
        this.numCylinder = _numCylinder;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getNumCylinder() {
        return numCylinder;
    }

    public void setNumCylinder(int numCylinder) {
        this.numCylinder = numCylinder;
    }
    
    

}
