/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.Model.Vehicle;

/**
 *
 * @author AkbarFauzy
 */
public class Car extends Vehicle{
    private CarModel model;

    public Car(String _registrationNumber, String _name, String _brand, String _color, int _numWheel, double _weight, int _numDoors, String _transmission, int _price, String _fuelType, int _horsePower, CarModel _model) {
        super(_registrationNumber, _name, _brand, _color, _numWheel, _weight, _numDoors, _transmission, _price, _fuelType, _horsePower);
        this.model = _model;
    }
    
    public void setCarModel(CarModel model){
        this.model = model;
    }
    
    public CarModel getCarModel(){
        return this.model;
    }
    
}
