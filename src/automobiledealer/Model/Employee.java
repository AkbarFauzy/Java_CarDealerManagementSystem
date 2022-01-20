/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.Model;

import java.util.Date;

/**
 *
 * @author AkbarFauzy
 */
public class Employee{
    private int employeeID;
    private String username;
    private String password;
    private String name;
    private Date dateOfBirth;
    private String gender;
    
    public Employee(int _ID, String _username, String _password,String _name, Date _dateOfBirth, String _gender){
        this.employeeID = _ID;
        this.username = _username;
        this.password = _password;
        this.name = _name;
        this.dateOfBirth = _dateOfBirth;
        this.gender = _gender;
    }
    
    public void setUsername(String username){
        this.username = username;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public void setDateOfBirth(Date date){
        this.dateOfBirth = date;
    }
    
    public void setGender(String gender){
        this.gender = gender;
    }
    
    public int getEmployeeID(){
        return this.employeeID;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public Date dateOfBirth(){
        return this.dateOfBirth;
    }
    
    public String getGender(){
        return this.gender;
    }
    
}
