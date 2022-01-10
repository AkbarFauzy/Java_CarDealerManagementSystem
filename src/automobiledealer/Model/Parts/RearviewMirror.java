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
public class RearviewMirror extends Parts{
    private String type;

    public RearviewMirror(String _partsNumber, String _name, String _brand, int _price, String _type) {
        super(_partsNumber, _name, _brand, _price);
        this.type = _type;
    }
    
    public void setType(String type){
        this.type = type;
    }
    
    public String getType(){
        return this.type;
    }
    
}
