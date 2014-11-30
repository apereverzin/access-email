package org.creek.accessemail.connector.mail;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.Flags.Flag;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.pop3.POP3SSLStore;

import static org.creek.accessemail.connector.mail.CheckResult.FAILURE;
import static org.creek.accessemail.connector.mail.CheckResult.SUCCESS;
import static org.creek.accessemail.connector.mail.MailPropertiesStorage.MAIL_SMTP_HOST_PROPERTY;
import static org.creek.accessemail.connector.mail.MailPropertiesStorage.MAIL_SMTP_PORT_PROPERTY;
import static org.creek.accessemail.connector.mail.TrueFalse.FALSE;
import static org.creek.accessemail.connector.mail.TrueFalse.TRUE;

/**
 * 
 * @author Andrey Pereverzin
 */
public class MailConnector {
    public static final String INBOX_FOLDER_NAME = "Inbox";
    private static final String SMTP = "smtp";

    private final MailPropertiesStorage propertiesStorage;

    public MailConnector(Properties mailProps) {
        propertiesStorage = new MailPropertiesStorage(mailProps);
    }
    
    public void checkSMTPConnection() throws ConnectorException {
        if (propertiesStorage.isUseFullEmailAddressTrue()) {
            Session session = propertiesStorage.getSMTPSessionUsingEmailAddress();
            checkSmtpConnection(session);
        } else if (propertiesStorage.isUseFullEmailAddressFalse()) {
            Session session = propertiesStorage.getSMTPSessionUsingUsername();
            checkSmtpConnection(session);
        } else { // UNDEFINED
            try {
                Session session = propertiesStorage.getSMTPSessionUsingUsername();
                Transport transport = session.getTransport(SMTP);
                connectTransport(transport);
                propertiesStorage.setUseFullEmailAddress(FALSE);
                propertiesStorage.setSmtpCheckResult(SUCCESS);
            } catch (MessagingException ex) {
                Session session = propertiesStorage.getSMTPSessionUsingEmailAddress();
                checkSmtpConnection(session);
                propertiesStorage.setUseFullEmailAddress(TRUE);
            }
        }
    }

    public void checkPOP3Connection() throws ConnectorException {
        if (propertiesStorage.isUseFullEmailAddressTrue()) {
            URLName url = propertiesStorage.getPop3URLNameUsingEmailAddress();
            checkPop3Connection(url);
        } else if (propertiesStorage.isUseFullEmailAddressFalse()) {
            URLName url = propertiesStorage.getPop3URLNameUsingUsername();
            checkPop3Connection(url);
        } else { // UNDEFINED
            try {
                URLName url = propertiesStorage.getPop3URLNameUsingUsername();
                Session session = Session.getInstance(propertiesStorage.getPop3Properties(), null);
                POP3SSLStore store = new POP3SSLStore(session, url);
                store.connect();
                propertiesStorage.setUseFullEmailAddress(FALSE);
                propertiesStorage.setPop3CheckResult(SUCCESS);
            } catch (MessagingException ex) {
                URLName url = propertiesStorage.getPop3URLNameUsingEmailAddress();
                checkPop3Connection(url);
                propertiesStorage.setUseFullEmailAddress(TRUE);
            }
            
        }
    }

    // TODO implement this
    public void checkIMAPConnection() throws ConnectorException {
        // try {
        // sessionKeeper.getIMAPStore().getDefaultFolder();
        // } catch (MessagingException ex) {
        // throw new ConnectorException(ex);
        // }
    }

    public Set<Object> receiveMessages(String subject) throws ConnectorException {
        try {
            Folder inboxFolder = getFolder(INBOX_FOLDER_NAME);
            inboxFolder.open(Folder.READ_WRITE);
            Set<Object> messages = new HashSet<Object>();

            Message[] msgs = inboxFolder.search(new SubjectSearchTerm(subject));
            for (Message msg : msgs) {
                Multipart mp = (Multipart) msg.getContent();
                BodyPart bp = mp.getBodyPart(0);
                Object content = bp.getContent();
                messages.add(content);
                msg.setFlag(Flag.DELETED, true);
            }

            // inboxFolder.expunge();
            inboxFolder.close(true);
            return messages;
        } catch (MessagingException ex) {
            throw new ConnectorException(ex);
        } catch (IOException ex) {
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

    private void checkSmtpConnection(Session session) throws ConnectorException {
        try {
            Transport transport = session.getTransport(SMTP);
            connectTransport(transport);
            propertiesStorage.setSmtpCheckResult(SUCCESS);
        } catch (MessagingException ex) {
            propertiesStorage.setSmtpCheckResult(FAILURE);
            throw new ConnectorException(ex);
        }
    }

    private void checkPop3Connection(URLName url) throws ConnectorException {
        Session session = Session.getInstance(propertiesStorage.getPop3Properties(), null);
        POP3SSLStore store = new POP3SSLStore(session, url);
        try {
            store.connect();
            propertiesStorage.setPop3CheckResult(SUCCESS);
        } catch (MessagingException ex) {
            propertiesStorage.setPop3CheckResult(FAILURE);
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

        Transport transport = session.getTransport(SMTP);
        connectTransport(transport);

        Transport.send(mailMessage);
    }

    private void connectTransport(Transport transport) throws MessagingException {
        String host = propertiesStorage.getSmtpProperties().getProperty(MAIL_SMTP_HOST_PROPERTY);
        int port = Integer.parseInt(propertiesStorage.getSmtpProperties().getProperty(MAIL_SMTP_PORT_PROPERTY));
        transport.connect(host, port, propertiesStorage.getUsername(), propertiesStorage.getPassword());
    }

    private Store getPop3Store() throws MessagingException {
        URLName url = propertiesStorage.getPop3URLName();

        Session session = Session.getInstance(propertiesStorage.getPop3Properties(), null);
        Store store = new POP3SSLStore(session, url);
        store.connect();

        return store;
    }
}
