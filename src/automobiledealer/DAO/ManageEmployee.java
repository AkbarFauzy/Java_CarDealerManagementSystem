/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.DAO;

import automobiledealer.Model.Employee.Employee;

/**
 *
 * @author AkbarFauzy
 */
public interface ManageEmployee {
    public void addEmployee(Employee e);
    public void editEmployee(Employee e);
    public void deleteEmployee(Employee e);
}
