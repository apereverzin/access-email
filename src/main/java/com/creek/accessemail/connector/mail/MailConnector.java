package com.creek.accessemail.connector.mail;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.Flags.Flag;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.pop3.POP3SSLStore;

import static com.creek.accessemail.connector.mail.MailPropertiesStorage.MAIL_SMTP_HOST_PROPERTY;
import static com.creek.accessemail.connector.mail.MailPropertiesStorage.MAIL_SMTP_PORT_PROPERTY;

/**
 * 
 * @author Andrey Pereverzin
 */
public class MailConnector {
    public static final String INBOX_FOLDER_NAME = "Inbox";

    private MailPropertiesStorage propertiesStorage;

    public MailConnector(Properties mailProps) {
        propertiesStorage = new MailPropertiesStorage(mailProps);
    }

    public void checkSMTPConnection() throws ConnectorException {
        try {
            Session session = propertiesStorage.getSMTPSession();
            Transport transport = session.getTransport("smtp");
            connectTransport(transport);
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        }
    }

    public Set<Message> receiveMessages(String subject) throws ConnectorException {
        try {
            Folder inboxFolder = getFolder(INBOX_FOLDER_NAME);
            inboxFolder.open(Folder.READ_WRITE);
            Set<Message> messages = new HashSet<Message>();

            Message[] msgs = inboxFolder.search(new SubjectSearchTerm(subject));
            for (Message msg : msgs) {
                messages.add(msg);
                msg.setFlag(Flag.DELETED, true);
            }
            
            //inboxFolder.expunge();
            inboxFolder.close(true);
            return messages;
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        }
    }
    
    public void sendMessage(String subject, String from, String text, String... emailsTo) throws ConnectorException {
        Session session = propertiesStorage.getSMTPSession();
        try {
            sendToAddresses(subject, from, text, session, emailsTo);
        } catch (MessagingException e) {
            throw new ConnectorException(e);
        }
    }

    public boolean removeMessage(String folderName, String subject) {
        return true;
    }

    public Folder getFolder(String folderName) throws ConnectorException {
        try {
            Folder rootFolder = getPop3Store().getDefaultFolder();
            Folder[] folders = rootFolder.list();
            for (Folder folder : folders) {
                if (folderName.equalsIgnoreCase(folder.getName())) {
                    return folder;
                }
            }
            return null;
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        }
    }

    private Message getMailMessageBySubject(Folder folder, String subject) throws ConnectorException {
        try {
            Message[] msgs = folder.search(new SubjectSearchTerm(subject));
            if (msgs.length > 0) {
                return msgs[0];
            }
            return null;
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        }
    }

    private void sendToAddresses(String subject, String from, String text, Session session, String... emailAddresses) throws MessagingException {
        Message mailMessage = new MimeMessage(session);
        mailMessage.setFrom(new InternetAddress(from));
        StringBuilder sb = new StringBuilder();
        if (emailAddresses.length > 0) {
            for (String emailAddress : emailAddresses) {
                sb.append(emailAddress).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        mailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(sb.toString()));
        mailMessage.setSubject(subject);
        mailMessage.setText(text);

        Transport transport = session.getTransport("smtp");
        connectTransport(transport);

        Transport.send(mailMessage);
    }
    
    public void connectTransport(Transport transport) throws MessagingException {
        transport.connect(propertiesStorage.getSmtpProperties().getProperty(MAIL_SMTP_HOST_PROPERTY), 
                Integer.parseInt(propertiesStorage.getSmtpProperties().getProperty(MAIL_SMTP_PORT_PROPERTY)), 
                propertiesStorage.getUsername(), 
                propertiesStorage.getPassword());
    }

    private Session getSMTPSession() {
        return Session.getInstance(propertiesStorage.getSmtpProperties(),
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(propertiesStorage.getUsername(), propertiesStorage.getPassword());
                    }
                });
    }
    
    private Store getPop3Store() throws MessagingException {
        URLName url = propertiesStorage.getPop3URLName();
        
        Session session = Session.getInstance(propertiesStorage.getPop3Properties(), null);
        Store store = new POP3SSLStore(session, url);
        store.connect();
        
        return store;
    }
}
