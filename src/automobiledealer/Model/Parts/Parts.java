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
public class Parts {
    private String partsNumber;
    private String name;
    private String brand;
    private int price;
    
    public Parts(String _partsNumber, String _name, String _brand, int _price){
        this.partsNumber = _partsNumber;
        this.name = _name;
        this.brand = _brand;
        this.price = _price;
    }
    
    public void setPartsNumber(String partsNumber){
        this.partsNumber = partsNumber;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setBrand(String brand){
        this.brand = brand;
    }
    
    public void setPrice(int price){
        this.price = price;
    }
    
    public String getPartsNumber(){
        return this.partsNumber;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getBrand(){
        return this.brand;
    }  
    
    public int getPrice(){
        return this.price;
    }
}
