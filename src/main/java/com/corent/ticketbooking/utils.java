/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.corent.ticketbooking;

/**
 *
 * @author shrisaiaravind
 */
import java.sql.*;
import java.io.*;
import java.util.*;

public class utils {

    public Connection connect() {
        Properties properties = new Properties();
        
        try(InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")){
            if(input==null){
                System.out.println("Couldnt fetch properties");
                return null;
            }
            properties.load(input);
        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
        
        
        String ur = properties.getProperty("db.url");
        String uname = properties.getProperty("db.username");
        String pw = properties.getProperty("db.password");
        
        if (ur == null || uname == null || pw == null) {
        System.err.println("Database connection details are missing in the properties file.");
        return null;
        }
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(ur, uname, pw);
        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found: "
                    + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            System.err.println("SQL Error: "
                    + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    void insert(Ticket ticket) {
        System.out.println("ticket = " + ticket);
        String query = "INSERT INTO `passengers` (`pID`, `name`, `age`, `from`, `to`,`status`) VALUES (?,?,?,?,?,?);";
        try(Connection conn = connect();  PreparedStatement st = conn.prepareStatement(query)) {
            st.setInt(1, ticket.getpID());
            st.setString(2, ticket.getName());
            st.setInt(3, ticket.getAge());
            st.setString(4, ticket.getStartingPoint());
            st.setString(5, ticket.getEndingPoint());
            st.setString(6, ticket.getStatus());
            st.execute();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    void delete(int pID) {
        String sql = "DELETE FROM `passengers` WHERE pID = ?";

        try ( Connection conn = connect();  PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pID);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Ticket deleted successfully from the database.");
            } else {
                System.out.println("Ticket ID not found.");
            }
        } catch (SQLException e) {
            System.err.println("SQL Error during ticket deletion: " + e.getMessage());
        }
    }
    
    void update(int pID) {
    String sql = "UPDATE passengers SET status = 'Closed' WHERE pID = ?";

    try (Connection conn = connect(); PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, pID);

        int rowsAffected = stmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Ticket successfully closed.");
        } else {
            System.out.println("Ticket ID not found.");
        }
    } catch (SQLException e) {
        System.err.println("SQL Error during ticket update: " + e.getMessage());
    }
}

    void display() {
        String sql = "SELECT * FROM passengers";

        try ( Connection conn = connect();  Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int pID = rs.getInt("pID");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String startingPoint = rs.getString("from");
                String endingPoint = rs.getString("to");
                String status = rs.getString("status");
                System.out.println("----------------------------------------------"
                        + "\nPassengerID: " + pID + "\nName: " + name + "\nAge: " + age
                        + "\nFrom: " + startingPoint + "\nTo: " + endingPoint
                        + "\nStatus: "+status);
            }
        } catch (SQLException e) {
            System.err.println("SQL Error during ticket retrieval: " + e.getMessage());
        }
    }
}
