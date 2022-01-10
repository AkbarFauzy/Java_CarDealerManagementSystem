/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.Model;

/**
 *
 * @author AkbarFauzy
 */
public class Customer {
    private String NIK;
    private String name;
    private String address;
    private String phoneNumber;
    private String gender;
    
    public Customer(String _NIK, String _name, String _address, String _phoneNumber, String _gender){
        this.NIK = _NIK;
        this.name = _name;
        this.address = _address;
        this.phoneNumber = _phoneNumber;
        this.gender = _gender;
    }

    public void setNIK(String NIK) {
        this.NIK = NIK;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNIK() {
        return NIK;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }
    
    
    
}
