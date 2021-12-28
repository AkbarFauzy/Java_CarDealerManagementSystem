/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.object.Vehicle;

/**
 *
 * @author AkbarFauzy
 */
public class Vehicle {
    private String registrationNumber;
    private String name;
    private String brand;
    private String color; 
    private int numWheel;
    private double weight;
    private int numDoors;
    private String transmission;
    private int price;
    private String fuelType;
    
    public Vehicle(String _registrationNumber,String _name, String _brand, String _color, int _numWheel, double _weight, int _numDoors, String _transmission, int _price, String _fuelType){
        this.registrationNumber = _registrationNumber;
        this.name = _name;
        this.brand = _brand;
        this.color = _color;
        this.numWheel = _numWheel;
        this.weight = _weight;
        this.numDoors = _numDoors;
        this.transmission = _transmission;
        this.price = _price;
        this.fuelType = _fuelType;
    }
    
    public void setRegistrationNumber(String registrationNumber){
        this.registrationNumber = registrationNumber;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setBrand(String brand){
        this.brand = brand;
    }
    
    public void setNumWheel(int numWheel){
        this.numWheel = numWheel;
    }
    
    public void setWeight(double weight){
        this.weight = weight;
    }
    
    public void setNumDoors(int numDoors){
        this.numDoors = numDoors;
    }
    
    public void setTransmission(String transmission){
        this.transmission = transmission;
    }
    
    public void setPrice(int price){
        this.price = price;
    }
    
    public void setFuelType(String fuelType){
        this.fuelType = fuelType;
    }
    
    public String getRegistrationNumber(){
        return this.registrationNumber;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getBrand(){
        return this.brand;
    }
    
    public int getNumWheel(){
        return this.numWheel;
    }
    
    public double getWeight(){
        return this.weight;
    }
    
    public int getNumDoors(){
        return this.numDoors;
    }
    
    public String getTransmission(){
        return this.transmission;
    }
    
    public int getPrice(){
        return this.price;
    }
    
    public String getFuelType(){
        return this.fuelType;
    }
 
}
