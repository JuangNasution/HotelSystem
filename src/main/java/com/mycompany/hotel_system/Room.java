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
public class Room {
    public boolean addRoom (int number, String type, String phone, String reserved){
        PreparedStatement ps;
        ResultSet rs;
        String query = "INSERT INTO `room`( `number`, `type`, `phone`, `reserved`) VALUES (?,?,?,?)";
        try {
            ps = My_connect.getConnection().prepareStatement(query);
            ps.setInt(1, number);
            ps.setString(2, type);
            ps.setString(3, phone);
            ps.setString(4, reserved);
            if(ps.executeUpdate()>0)
                return true;
            else
                return false;
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE,null,ex);
            return false;
        }
    }
    public boolean editRoom (int number,String type, String phone, String reserved){
        PreparedStatement ps;
        String query = "UPDATE `room` SET `type`=?, `phone`=?, `reserved`=? WHERE `number`=?";
        try {
            ps = My_connect.getConnection().prepareStatement(query);
            ps.setString(1, type);
            ps.setString(2, phone);
            ps.setString(3, reserved);
            ps.setInt(4, number);
            return ps.executeUpdate()>0;
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE,null,ex);
            return false;
        }
    }
    public boolean removeRoom (int number){
        PreparedStatement ps;
        String query = "DELETE FROM `room` WHERE `number`=?";
        try {
            ps = My_connect.getConnection().prepareStatement(query);
            ps.setInt(1, number);
            return ps.executeUpdate()>0;
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE,null,ex);
            return false;
        }
    }
    public void fillClientTable(JTable table){
        PreparedStatement ps;
        ResultSet rs;
        String query ="SELECT * FROM `room`";
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
    public boolean setRoomToReserved(int number, String isReserved){
        PreparedStatement ps;
        String query = "UPDATE `room` SET `reserved`=? WHERE `number`=?";
        try {
            ps = My_connect.getConnection().prepareStatement(query);
            ps.setString(1, isReserved);
            ps.setInt(2, number);
            return ps.executeUpdate()>0;
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE,null,ex);
            return false;
        }
    }
    
    public String IsRoomReserved(int number){
        PreparedStatement ps;
        ResultSet rs;
        String query = "SELECT `reserved` FROM `room` WHERE `number`=?";
        try {
            ps = My_connect.getConnection().prepareStatement(query);
            ps.setInt(1,number);
            rs=ps.executeQuery();
            if(rs.next()){
                return rs.getString(1);
            }
            else
                return "";
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE,null,ex);
            return "";
        }
    }
}
