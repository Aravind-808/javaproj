package com.corent.ticketbooking;

import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ManageTickets {
    
    SessionFactory factory = new Configuration().configure().buildSessionFactory();
    private static final Logger logger = LogManager.getLogger(ManageTickets.class);
    List<Ticket> tickets = new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    utils dbutils = new utils();

    void bookTicket() {
        try {
            Session session = factory.getCurrentSession();
            System.out.println("Enter the passenger count");
            int pcount = sc.nextInt();
            sc.nextLine();

            if (pcount <= 0) {
                logger.warn("Passenger count must be greater than zero.");
                System.out.println("Passenger count must be greater than zero.");
                return;
            }

            logger.info("Booking tickets. Passenger count: " + pcount);
            System.out.println("Enter Starting Point");
            String startpoint = sc.nextLine();

            System.out.println("Enter Ending Point");
            String endpoint = sc.nextLine();
            int pID = 1;
            for (int i = 0; i < pcount; i++) {
                try {
                    System.out.println("Enter the passenger name");
                    String name = sc.nextLine();

                    System.out.println("Enter the passenger age");
                    int age = sc.nextInt();
                    sc.nextLine();  

                    if (age <= 0) {
                        logger.warn("Invalid age entered: " + age);
                        System.out.println("Age must be greater than zero.");
                        i--;
                        continue;
                    }
                    String status = "Booked";
                    Ticket ticket = new Ticket(pID, name, age, startpoint, endpoint, status);
                    Transaction transaction = session.beginTransaction();
                    session.save(ticket);
                    transaction.commit();
                    
                    tickets.add(ticket);
                    pID++;

                    dbutils.insert(ticket);
                    logger.info("Ticket booked successfully for passenger: " + name + " with ID: " + pID);
                    
                } catch (InputMismatchException ime) {
                    logger.error("Invalid input for age. Please enter a valid integer.", ime);
                    System.out.println("Invalid input for age. Please enter a valid integer.");
                    sc.nextLine();
                } catch (Exception e) {
                    logger.error("An error occurred while creating the ticket: ", e);
                    System.out.println("An error occurred while creating the ticket: " + e.getMessage());
                }
            }
        } catch (InputMismatchException ime) {
            logger.error("Invalid input for passenger count.", ime);
            System.out.println("Invalid input for passenger count. Please enter a valid integer.");
            sc.nextLine();
        } catch (Exception e) {
            logger.error("An unexpected error occurred during ticket booking.", e);
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    void listTickets() {
    try (Session session = factory.getCurrentSession()) {
        Transaction transaction = session.beginTransaction();

        List<Ticket> ticketList = session.createQuery("FROM Ticket", Ticket.class).getResultList();
        transaction.commit();
        logger.info("Listing all booked tickets.");
        if (ticketList.isEmpty()) {
            System.out.println("No tickets found.");
        }
        else {
            for (Ticket ticket : ticketList) {
                System.out.println(ticket);
            }
        }
    }catch (Exception e){
        logger.error("An error occurred while listing tickets: ", e);
        System.out.println("An error occurred while listing tickets: " + e.getMessage());
        }
    }

    void deleteTicket() {
        System.out.println("Enter PassengerID of the ticket you want to close.");
        int pID = sc.nextInt();

        try (Session session = factory.getCurrentSession()) {
            Transaction transaction = session.beginTransaction();
            
            Ticket ticket = session.get(Ticket.class, pID);

            if (ticket != null) {
                session.delete(ticket);
                transaction.commit();
                logger.info("Ticket with PassengerID " + pID + " has been deleted.");
                System.out.println("Ticket with PassengerID " + pID + " has been deleted.");
            } else {
                System.out.println("No Ticket found with PassengerID " + pID);
                logger.error("No Ticket Found with pID"+pID);
            }
        } catch (InputMismatchException e) {
            logger.error("Invalid input for PassengerID during deletion.", e);
            System.out.println("Enter the correct passenger ID");
        }
    }

    public void closeTicket() {
    int pID;
    boolean found = false;

    System.out.println("Enter PassengerID of the ticket you want to close:");

    try (Session session = factory.getCurrentSession()) {
        pID = sc.nextInt();
        Transaction transaction = session.beginTransaction();

        // Retrieve the ticket from the database by PassengerID using session.get()
        Ticket ticket = session.get(Ticket.class, pID);

        if (ticket != null) {
            logger.info("Ticket found for closing. PassengerID: " + pID);
            System.out.println("Ticket Details: " + ticket);
            System.out.println("Are you sure you want to close this ticket? (y/n)");

            String conf = sc.next();
            if (conf.equalsIgnoreCase("y")) {
                dbutils.update(pID); // Assuming this updates the database in some way

                ticket.setStatus("Closed");  // Update the status to 'Closed'
                session.merge(ticket); // Merge the ticket changes into the session
                transaction.commit();
                logger.info("Ticket with PassengerID " + pID + " has been successfully closed.");
                System.out.println("Ticket has been closed successfully.");
            } else {
                logger.info("Ticket closing canceled for PassengerID " + pID);
                System.out.println("Closing of the ticket has been canceled.");
            }

            found = true;
        } else {
            logger.warn("No Ticket found with the given PassengerID: " + pID);
            System.out.println("No Ticket found with the given PassengerID. Please verify the ID.");
        }
    } catch (Exception e) {
        logger.error("Error occurred while closing the ticket", e);
        e.printStackTrace();
        }
    }

}
