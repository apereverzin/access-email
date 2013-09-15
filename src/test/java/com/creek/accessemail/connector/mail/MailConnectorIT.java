package com.creek.accessemail.connector.mail;

import java.util.Properties;
import java.util.Set;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.creek.accessemail.connector.mail.ConnectorException;
import com.creek.accessemail.connector.mail.MailConnector;
import com.creek.accessemail.connector.mail.MailPropertiesStorage;
import com.creek.accessemail.connector.mail.PredefinedMailProperties;

/**
 * 
 * @author Andrey Pereverzin
 */
public class MailConnectorIT {
    private MailConnector mailMessageConnector;
    private static final String GMAIL = "gmail";
    private static final String AOL = "aol";
    private static final String YAHOOCOM = "yahoocom";
    private static final String HOTMAIL = "hotmail";
    private static final String SUBJECT = "WhereAreYou";
    private static final String GMAIL_ADDRESS = "andrey.pereverzin@gmail.com";
    private static final String USERNAME = "andrey.pereverzin";
    private static final String YAHOO_USERNAME = "andrey.pereverzin@yahoo.com";
    private static final String HOTMAIL_USERNAME = "andrey.pereverzin@hotmail.com";
    private static final String PASSWORD = "bertoluCCi";
    
    @Before
    public void setUp() {
        //
    }

    @Test
    @Ignore
    public void shouldReceiveMessagesFromGMail() throws ConnectorException {
        runReceiveMessagesTest(GMAIL, USERNAME);
    }

    @Test
    @Ignore
    public void shouldSendMessagesToGMail() throws ConnectorException {
        runSendMessagesTest(GMAIL, USERNAME);
    }

    @Test
    @Ignore
    public void shouldCheckSmtpConnectionToGMail() throws ConnectorException {
        runCheckSmtpConnectionTest(GMAIL, USERNAME);
    }

    @Test
    @Ignore
    public void shouldReceiveMessagesFromAol() throws ConnectorException {
        runReceiveMessagesTest(AOL, USERNAME);
    }

    @Test
    @Ignore
    public void shouldSendMessagesToAol() throws ConnectorException {
        runSendMessagesTest(AOL, USERNAME);
    }

    @Test
    @Ignore
    public void shouldCheckSmtpConnectionToAol() throws ConnectorException {
        runCheckSmtpConnectionTest(AOL, USERNAME);
    }

    @Test
    @Ignore
    public void shouldReceiveMessagesFromYahooCom() throws ConnectorException {
        runReceiveMessagesTest(YAHOOCOM, YAHOO_USERNAME);
    }
    
    @Test
    @Ignore
    public void shouldSendMessagesToYahooCom() throws ConnectorException {
        runSendMessagesTest(YAHOOCOM, YAHOO_USERNAME);
    }

    @Test
    @Ignore
    public void shouldCheckSmtpConnectionToYahooCom() throws ConnectorException {
        runCheckSmtpConnectionTest(YAHOOCOM, YAHOO_USERNAME);
    }

    @Test
    @Ignore
    public void shouldReceiveMessagesFromHotmail() throws ConnectorException {
        runReceiveMessagesTest(HOTMAIL, HOTMAIL_USERNAME);
    }

    @Test
    @Ignore
    public void shouldSendMessagesToHotmail() throws ConnectorException {
        runSendMessagesTest(HOTMAIL, HOTMAIL_USERNAME);
    }

    @Test
    @Ignore
    public void shouldCheckSmtpConnectionToHotmail() throws ConnectorException {
        runCheckSmtpConnectionTest(HOTMAIL, HOTMAIL_USERNAME);
    }

    private void runSendMessagesTest(String server, String username) throws ConnectorException {
        Properties props = PredefinedMailProperties.getPredefinedPropertiesForServer(server);
        props.setProperty(MailPropertiesStorage.MAIL_USERNAME_PROPERTY, username);
        props.setProperty(MailPropertiesStorage.MAIL_PASSWORD_PROPERTY, PASSWORD);
        mailMessageConnector = new MailConnector(props);
        mailMessageConnector.sendMessage(SUBJECT, GMAIL_ADDRESS, "smth", new String[]{GMAIL_ADDRESS});
    }
    
    private void runReceiveMessagesTest(String server, String username) throws ConnectorException {
        Properties props = PredefinedMailProperties.getPredefinedPropertiesForServer(server);
        props.setProperty(MailPropertiesStorage.MAIL_USERNAME_PROPERTY, username);
        props.setProperty(MailPropertiesStorage.MAIL_PASSWORD_PROPERTY, PASSWORD);
        mailMessageConnector = new MailConnector(props);
        Set<Object> messages = mailMessageConnector.receiveMessages(SUBJECT);
        System.out.println(messages.size());
    }

    private void runCheckSmtpConnectionTest(String server, String username) throws ConnectorException {
        Properties props = PredefinedMailProperties.getPredefinedPropertiesForServer(server);
        props.setProperty(MailPropertiesStorage.MAIL_USERNAME_PROPERTY, username);
        props.setProperty(MailPropertiesStorage.MAIL_PASSWORD_PROPERTY, PASSWORD);
        mailMessageConnector = new MailConnector(props);
        mailMessageConnector.checkSMTPConnection();
    }
}
