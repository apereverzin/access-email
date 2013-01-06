package com.creek.accessemail.connector.mail;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.URLName;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class MailPropertiesStorage {
    private Properties mailProps;
    
    private String username;
    private String password;
    private Properties imapProps = new Properties();
    private Properties pop3Props = new Properties();
    private Properties smtpProps = new Properties();

    public static final String MAIL_USERNAME = "mail.username";
    public static final String MAIL_PASSWORD = "mail.password";
    public static final String MAIL_STORE_PROTOCOL = "mail.store.protocol";

    public static final String MAIL_SMTP_HOST = "mail.smtp.host";
    public static final String MAIL_SMTP_PORT = "mail.smtp.port";
    public static final String MAIL_SMTP_TRANSPORT_PROTOCOL = "mail.smtp.transport.protocol";
    public static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    public static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
    public static final String MAIL_SMTP_SOCKET_FACTORY_CLASS = "mail.smtp.socketFactory.class";
    public static final String MAIL_SMTP_SOCKET_FACTORY_PORT = "mail.smtp.socketFactory.port";
    public static final String MAIL_POP3_SOCKET_FACTORY_CLASS = "mail.pop3.socketFactory.class";
    public static final String MAIL_POP3_SOCKET_FACTORY_FALLBACK = "mail.pop3.socketFactory.fallback";
    public static final String MAIL_POP3_HOST = "mail.pop3.host";
    public static final String MAIL_POP3_PORT = "mail.pop3.port";
    public static final String MAIL_POP3_SOCKET_FACTORY_PORT = "mail.pop3.socketFactory.port";

    public static final String MAIL_IMAP_HOST = "mail.imap.host";
    public static final String MAIL_IMAP_PORT = "mail.imap.port";

    public MailPropertiesStorage(Properties mailProps) {
        this.mailProps = mailProps;
        username = mailProps.getProperty(MAIL_USERNAME);
        password = mailProps.getProperty(MAIL_PASSWORD);
        
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_HOST);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_PORT);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_TRANSPORT_PROTOCOL);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_AUTH);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_STARTTLS_ENABLE);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_SOCKET_FACTORY_PORT);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_SOCKET_FACTORY_CLASS);
        
        setPropertyIfNotNull(mailProps, pop3Props, MAIL_POP3_SOCKET_FACTORY_CLASS);
        setPropertyIfNotNull(mailProps, pop3Props, MAIL_POP3_SOCKET_FACTORY_FALLBACK);
        setPropertyIfNotNull(mailProps, pop3Props, MAIL_POP3_HOST);
        setPropertyIfNotNull(mailProps, pop3Props, MAIL_POP3_PORT);
        setPropertyIfNotNull(mailProps, pop3Props, MAIL_POP3_SOCKET_FACTORY_PORT);

        setPropertyIfNotNull(mailProps, imapProps, MAIL_STORE_PROTOCOL);
    }
    
    public Session getSMTPSession() {
        return Session.getInstance(smtpProps,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }
    
    public Properties getImapProperties() {
        return imapProps;
    }

    public URLName getPop3URLName() {
        URLName url = new URLName("pop3", pop3Props.getProperty(MAIL_POP3_HOST), 
                new Integer(pop3Props.getProperty(MAIL_POP3_SOCKET_FACTORY_PORT)), "", username, password);
        return url;
    }
    
    public Properties getPop3Properties() {
        return pop3Props;
    }

    public Properties getSmtpProperties() {
        return smtpProps;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    private void setPropertyIfNotNull(Properties propsFrom, Properties propsTo, String propName) {
        if (propsFrom.getProperty(propName) != null) {
            propsTo.setProperty(propName, propsFrom.getProperty(propName));
        }
    }
}
