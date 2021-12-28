/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.object;

/**
 *
 * @author AkbarFauzy
 */
public class Customer {
    private String customerID;
    private String name;
    private String address;
    private String phoneNumber;
    private String gender;
    
    private Customer(String _customerID, String _name, String _address, String _phoneNumber, String _gender){
        this.customerID = _customerID;
        this.name = _name;
        this.address = _address;
        this.phoneNumber = _phoneNumber;
        this.gender = _gender;
    }
    
}
