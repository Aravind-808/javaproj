/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package com.corent.ticketbooking;

import java.util.Scanner;

/**
 *
 * @author shrisaiaravind
 */
public class TicketBooking {

    public static void main(String[] args) {
        ManageTickets mg = new ManageTickets();
        Scanner sc = new Scanner(System.in);
        OUTER:
        while (true) {
            System.out.format("Welcome to Ticket Booking System\n\n"
                            + "1) Book Ticket\n"
                            + "2) Display Ticket\n"
                            + "3) Delete Ticket\n"
                            + "4) Close Ticket\n"
                            + "5) Exit\n" 
                            + "Choose an option : ");
            int ch = sc.nextInt();
            sc.nextLine();
            switch (ch) {
                case 1:
                    mg.bookTicket();
                    break;  
                            
                case 2:
                    mg.listTickets();
                    break;
                case 3:
                    mg.deleteTicket();
                    break;
                case 4:
                    mg.closeTicket();
                    break;
                case 5:
                    System.out.println("Bye Bye !");
                    break OUTER;
                default:
                    System.out.println("Invalid Input.");
                    break;
            }
        }
        sc.close();
    }
}
