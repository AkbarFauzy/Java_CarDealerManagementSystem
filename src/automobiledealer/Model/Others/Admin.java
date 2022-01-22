/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.Model.Others;

import automobiledealer.Model.Employee.Employee;
import java.util.Date;


/**
 *
 * @author AkbarFauzy
 */
public class Admin extends Employee{
    
    public Admin(int _ID, String _username, String _password, String _name, Date _dateOfBirth, String _gender) {
        super(_ID, _username, _password, _name, _dateOfBirth, _gender);
    }
   
}
