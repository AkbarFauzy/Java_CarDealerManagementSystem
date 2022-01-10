/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author AkbarFauzy
 */
public class Connection_to_db {
    static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/cardealer";
    static final String USER = "root";
    static final String PASS = "";
    
    static java.sql.Connection conn;
    static PreparedStatement stmt;
    static ResultSet rs; 
    
    public static java.sql.Connection connection(){
        if(conn==null){
            try{ 
                conn = DriverManager.getConnection(DB_URL, USER,PASS); 
            }catch(Exception e){ 
                System.out.println(e);
            }
        }
        return conn;
    } 
}
