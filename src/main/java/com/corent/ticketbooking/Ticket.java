/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.corent.ticketbooking;

/**
 *
 * @author shrisaiaravind
 */
public class Ticket {

    private String name, startingPoint, endingPoint, status;
    private int pID, age;
    public Ticket() {
        // for hibernate to instantiate the object
    }


    public Ticket(int pID,String name, int age, String startingPoint, String endingPoint, String status) {
        this.pID = pID;
        this.name = name;
        this.age = age;
        this.startingPoint = startingPoint;
        this.endingPoint = endingPoint;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
    
    public String getStatus() {
        return status;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public String getEndingPoint() {
        return endingPoint;
    }
    
    public int getpID(){
        return pID;                                                                                                                                                 
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public void setEndingPoint(String endingPoint) {
        this.endingPoint = endingPoint;
    }
    
    public void setpID(int pID){
        this.pID = pID;
    }
    
    @Override
    public String toString() {
        return "Passenger ID: " + pID + ", Name: " + name + ", Age: " + age + ", From: " + startingPoint + " To: " + endingPoint;
    }

}
