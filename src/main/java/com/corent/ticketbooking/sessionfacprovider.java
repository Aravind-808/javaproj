/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.corent.ticketbooking;

/**
 *
 * @author shrisaiaravind
 */

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class sessionfacprovider {
    public static SessionFactory provideSessionFactory()
    {
        Configuration config = new Configuration();
        config.configure("resources/hibernate.cfg.xml");
        return config.buildSessionFactory();
    }
}