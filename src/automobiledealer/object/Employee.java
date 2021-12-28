/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.object;

import java.util.Date;

/**
 *
 * @author AkbarFauzy
 */
public class Employee {
    private String employeeID;
    private String name;
    private Date dateOfBirth;
    private String gender;
    
    public Employee(String _ID, String _name, Date _dateOfBirth, String _gender){
        this.employeeID = _ID;
        this.name = _name;
        this.dateOfBirth = _dateOfBirth;
        this.gender = _gender;
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
    
    public String getEmployeeID(){
        return this.employeeID;
    }
    
    public String getName(){
        return this.name;
    }
    
    public Date dateOfBirth(){
        return this.dateOfBirth;
    }
    
    public String getGender(){
        return this.gender;
    }
    
    
}
