/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.UI;

import automobiledealer.Model.Employee;
import javax.swing.JPanel;

/**
 *
 * @author AkbarFauzy
 */
public class EmployeeForm extends JPanel{
    Employee E;

    public EmployeeForm(Employee _E) {
        this.E = _E;
    }
    
    public void setEmployee(Employee _e){
        this.E = _e;
    }
    
    public Employee getEmployee(){
        return this.E;
    }
    
    
}
