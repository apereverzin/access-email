package com.creek.accessemail.connector.mail;

import java.util.Properties;
import java.util.Set;

import javax.mail.Message;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.creek.accessemail.connector.mail.ConnectorException;
import com.creek.accessemail.connector.mail.MailConnector;
import com.creek.accessemail.connector.mail.MailPropertiesStorage;
import com.creek.accessemail.connector.mail.PredefinedMailProperties;

/**
 * 
 * @author andreypereverzin
 */
public class MailConnectorIT {
    private MailConnector mailMessageConnector;
    private static final String GMAIL = "gmail";
    private static final String AOL = "aol";
    private static final String YAHOOCOM = "yahoocom";
    private static final String SUBJECT = "IAmHere";
    private static final String GMAIL_ADDRESS = "andrey.pereverzin@gmail.com";
    private static final String USERNAME = "andrey.pereverzin";
    private static final String PASSWORD = "bertoluCCi";
    
    @Before
    public void setUp() {
        //
    }

    @Test
    @Ignore
    public void shouldReceiveMessagesFromGMail() throws ConnectorException {
        Properties props = PredefinedMailProperties.getPredefinedPropertiesForServer(GMAIL);
        props.setProperty(MailPropertiesStorage.MAIL_USERNAME, USERNAME);
        props.setProperty(MailPropertiesStorage.MAIL_PASSWORD, PASSWORD);
        mailMessageConnector = new MailConnector(props);
        Set<Message> messages = mailMessageConnector.receiveMessages(SUBJECT);
        System.out.println(messages.size());
    }

    @Test
    @Ignore
    public void shouldSendMessagesFromGMail() throws ConnectorException {
        Properties props = PredefinedMailProperties.getPredefinedPropertiesForServer(GMAIL);
        props.setProperty(MailPropertiesStorage.MAIL_USERNAME, USERNAME);
        props.setProperty(MailPropertiesStorage.MAIL_PASSWORD, PASSWORD);
        mailMessageConnector = new MailConnector(props);
        mailMessageConnector.sendMessage(SUBJECT, GMAIL_ADDRESS, "smth", new String[]{GMAIL_ADDRESS});
    }

    @Test
    @Ignore
    public void shouldReceiveMessagesFromAol() throws ConnectorException {
        Properties props = PredefinedMailProperties.getPredefinedPropertiesForServer(AOL);
        props.setProperty(MailPropertiesStorage.MAIL_USERNAME, USERNAME);
        props.setProperty(MailPropertiesStorage.MAIL_PASSWORD, PASSWORD);
        mailMessageConnector = new MailConnector(props);
        Set<Message> messages = mailMessageConnector.receiveMessages(SUBJECT);
        System.out.println(messages.size());
    }

    @Test
    @Ignore
    public void shouldReceiveMessagesFromYahooCom() throws ConnectorException {
        Properties props = PredefinedMailProperties.getPredefinedPropertiesForServer(YAHOOCOM);
        props.setProperty(MailPropertiesStorage.MAIL_USERNAME, "andrey.pereverzin@yahoo.com");
        props.setProperty(MailPropertiesStorage.MAIL_PASSWORD, PASSWORD);
        mailMessageConnector = new MailConnector(props);
        Set<Message> messages = mailMessageConnector.receiveMessages(SUBJECT);
        System.out.println(messages.size());
    }
}