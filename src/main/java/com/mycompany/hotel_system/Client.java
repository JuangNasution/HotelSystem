/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hotel_system;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author XYZ
 */
public class Client {

    public boolean addClient (int id,String Fname, String LName, String Phone, String Email){
        PreparedStatement ps;
        ResultSet rs;
        String query = "INSERT INTO `client`( `id`,`fname`, `lname`, `phone`, `email`) VALUES (?,?,?,?,?)";
        try {
            ps = My_connect.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            ps.setString(2, Fname);
            ps.setString(3, LName);
            ps.setString(4, Phone);
            ps.setString(5, Email);
            if(ps.executeUpdate()>0)
                return true;
            else
                return false;
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE,null,ex);
            return false;
        }
    }
    public boolean editClient (int id,String Fname, String LName, String Phone, String Email){
        PreparedStatement ps;
        String query = "UPDATE `client` SET `fname`=?, `lname`=?, `phone`=?, `email`=? WHERE `id`=?";
        try {
            ps = My_connect.getConnection().prepareStatement(query);
            ps.setString(1, Fname);
            ps.setString(2, LName);
            ps.setString(3, Phone);
            ps.setString(4, Email);
            ps.setInt(5, id);
            return ps.executeUpdate()>0;
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE,null,ex);
            return false;
        }
    }
    public boolean removeClient (int id){
        PreparedStatement ps;
        String query = "DELETE FROM `client` WHERE `id`=?";
        try {
            ps = My_connect.getConnection().prepareStatement(query);
            ps.setInt(1, id);
            return ps.executeUpdate()>0;
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE,null,ex);
            return false;
        }
    }
    public void fillClientTable(JTable table){
        PreparedStatement ps;
        ResultSet rs;
        String query ="SELECT * FROM `client`";
        try {
            ps = My_connect.getConnection().prepareStatement(query);
            rs = ps.executeQuery();
            DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
            Object[] row;
            while(rs.next()){
                row = new Object[5];
                row[0] = rs.getInt(1);
                row[1] = rs.getString(2);
                row[2] = rs.getString(3);
                row[3] = rs.getString(4);
                row[4] = rs.getString(5);
                tableModel.addRow(row);
                
            }
            
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    public void clearTable(JTable table){
        DefaultTableModel dm = (DefaultTableModel)table.getModel();
        while(dm.getRowCount() > 0)
        {
            dm.removeRow(0);
        }
    }
    
}
    

