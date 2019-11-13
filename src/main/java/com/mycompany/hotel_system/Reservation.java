/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.hotel_system;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author XYZ
 */
public class Reservation {
    Room room = new Room();
    public boolean addReserv (int client_id, int room_number, String dateIn, String dateOut){
        PreparedStatement ps;
        ResultSet rs;
        String query = "INSERT INTO `reservation`( `client_id`, `room_number`, `date_in`, `date_out`) VALUES (?,?,?,?)";
        try {
            ps = My_connect.getConnection().prepareStatement(query);
            ps.setInt(1, client_id);
            ps.setInt(2, room_number);
            ps.setString(3, dateIn);
            ps.setString(4, dateOut);
            if(room.IsRoomReserved(room_number).equals("NO")){
                if(ps.executeUpdate()>0){
                room.setRoomToReserved(room_number, "YES");
                return true;
                }
                else
                    return false;
                }
            else{
                JOptionPane.showMessageDialog(null, "This room is already reserved");
                return false;
            }
            
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE,null,ex);
            return false;
        }
    }
    public boolean editReserv (int reservID, int client_id, int room_number, String dateIn, String dateOut){
        PreparedStatement ps;
        String query = "UPDATE `reservation` SET `client_id`=?, `room_number`=?, `date_in`=?,`date_out`=? WHERE `id`=?";
        try {
            ps = My_connect.getConnection().prepareStatement(query);
            ps.setInt(1, client_id);
            ps.setInt(2, room_number);
            ps.setString(3, dateIn);
            ps.setString(4, dateOut);
            ps.setInt(5, reservID);
            return ps.executeUpdate()>0;
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE,null,ex);
            return false;
        }
    }
    public boolean removeRes (int reservid){
        PreparedStatement ps;
        String query = "DELETE FROM `reservation` WHERE `id`=?";
        try {
            ps = My_connect.getConnection().prepareStatement(query);
            ps.setInt(1, reservid);
            int room_number = getRoomNumber(reservid);
            room.setRoomToReserved(room_number, "NO");
            return ps.executeUpdate()>0;
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE,null,ex);
            return false;
        }
    }
    public boolean removeReserv (int reservID){
        PreparedStatement ps;
        String query = "DELETE FROM `reservation` WHERE `id`=?";
        try {
            ps = My_connect.getConnection().prepareStatement(query);
            ps.setInt(1, reservID);
            int room_number = getRoomNumber(reservID);
            System.out.println(ps.executeUpdate());
            if(ps.executeUpdate()>0){
                System.out.println("YES");
                room.setRoomToReserved(room_number, "NO");
                return true;
            }
            else
                return false;
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE,null,ex);
            return false;
        }
    }
    public void fillReservTable(JTable table){
        PreparedStatement ps;
        ResultSet rs;
        String query ="SELECT * FROM `reservation`";
        try {
            ps = My_connect.getConnection().prepareStatement(query);
            rs = ps.executeQuery();
            DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
            Object[] row;
            while(rs.next()){
                row = new Object[5];
                row[0] = rs.getInt(1);
                row[1] = rs.getInt(2);
                row[2] = rs.getInt(3);
                row[3] = rs.getDate(4);
                row[4] = rs.getDate(5);
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
    public int getRoomNumber (int reservID){
        PreparedStatement ps;
        ResultSet rs;
        String query ="SELECT `room_number` FROM `reservation` WHERE `id`=?";
        try {
            ps = My_connect.getConnection().prepareStatement(query);
            ps.setInt(1, reservID);
            rs = ps.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
            else
                return 0;
        } catch (Exception ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE,null,ex);
            return 0;
        }
    }
}
