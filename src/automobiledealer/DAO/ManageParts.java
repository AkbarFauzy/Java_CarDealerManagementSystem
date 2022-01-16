/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package automobiledealer.DAO;

import automobiledealer.Model.Parts.Parts;
import java.util.List;

/**
 *
 * @author AkbarFauzy
 */
public interface ManageParts {
    public List list();
    public void addPart(Parts P);
    public void editPart(Parts P);
    public void deletePart(String partNumber);
    
}
