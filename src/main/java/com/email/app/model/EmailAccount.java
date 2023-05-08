package com.email.app.model;
import javafx.application.Platform;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import java.util.Properties;

public class EmailAccount {
    private String address;
    private String password;
    private Properties properties; //holds email configuration
    private Store store; // will be using to retrieve and send mail
    private Session session;

    public Session getSession() {
        return session;
    }
    public void setSession(Session session) {
        this.session = session;
    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return address;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public EmailAccount(String address, String password) {
        this.address = address;
        this.password = password;
        properties = new Properties();

        properties.put("incomingHost", "imap.gmail.com");
        properties.put("mail.store.protocol", "imaps"); // protocol for sending the emails
        properties.put("mail.smtp.port", "587");
        properties.put("mail.transport.protocol", "smtps"); // for retrieving the protocol we stmp protocol
        properties.put("mail.smtps.host", "smtp.gmail.com");
        //properties.put("mail.debug", "true");
        properties.put("mail.smtps.auth", "true");
        properties.put("outgoingHost", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable", "true");
    }
}
