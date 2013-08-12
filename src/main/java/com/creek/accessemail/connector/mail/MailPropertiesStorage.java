package com.creek.accessemail.connector.mail;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.URLName;

/**
 * 
 * @author Andrey Pereverzin
 */
public class MailPropertiesStorage {
    private final String username;
    private final String password;
    private final Properties imapProps = new Properties();
    private final Properties pop3Props = new Properties();
    private final Properties smtpProps = new Properties();

    public static final String MAIL_USERNAME_PROPERTY = "mail.username";
    public static final String MAIL_PASSWORD_PROPERTY = "mail.password";
    public static final String MAIL_STORE_PROTOCOL_PROPERTY = "mail.store.protocol";

    public static final String MAIL_SMTP_HOST_PROPERTY = "mail.smtp.host";
    public static final String MAIL_SMTP_PORT_PROPERTY = "mail.smtp.port";
    public static final String MAIL_SMTP_TRANSPORT_PROTOCOL_PROPERTY = "mail.smtp.transport.protocol";
    public static final String MAIL_SMTP_AUTH_PROPERTY = "mail.smtp.auth";
    public static final String MAIL_SMTP_STARTTLS_ENABLE_PROPERTY = "mail.smtp.starttls.enable";
    public static final String MAIL_SMTP_SOCKET_FACTORY_CLASS_PROPERTY = "mail.smtp.socketFactory.class";
    public static final String MAIL_SMTP_SOCKET_FACTORY_PORT_PROPERTY = "mail.smtp.socketFactory.port";
    
    public static final String MAIL_POP3_HOST_PROPERTY = "mail.pop3.host";
    public static final String MAIL_POP3_PORT_PROPERTY = "mail.pop3.port";
    public static final String MAIL_POP3_SOCKET_FACTORY_PORT_PROPERTY = "mail.pop3.socketFactory.port";
    public static final String MAIL_POP3_SOCKET_FACTORY_CLASS_PROPERTY = "mail.pop3.socketFactory.class";
    public static final String MAIL_POP3_SOCKET_FACTORY_FALLBACK_PROPERTY = "mail.pop3.socketFactory.fallback";
    
    public static final String MAIL_POP3S_PORT_PROPERTY = "mail.pop3s.port";
    public static final String MAIL_POP3S_SOCKET_FACTORY_PORT_PROPERTY = "mail.pop3s.socketFactory.port";
    public static final String MAIL_POP3S_SOCKET_FACTORY_CLASS_PROPERTY = "mail.pop3s.socketFactory.class";
    public static final String MAIL_POP3S_SOCKET_FACTORY_FALLBACK_PROPERTY = "mail.pop3s.socketFactory.fallback";
    public static final String MAIL_POP3S_SSL_ENABLE_PROPERTY = "mail.pop3.ssl.enable";

    public static final String MAIL_IMAP_HOST_PROPERTY = "mail.imap.host";
    public static final String MAIL_IMAP_PORT_PROPERTY = "mail.imap.port";

    public MailPropertiesStorage(Properties mailProps) {
        username = mailProps.getProperty(MAIL_USERNAME_PROPERTY);
        password = mailProps.getProperty(MAIL_PASSWORD_PROPERTY);
        
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_HOST_PROPERTY);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_PORT_PROPERTY);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_TRANSPORT_PROTOCOL_PROPERTY);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_AUTH_PROPERTY);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_STARTTLS_ENABLE_PROPERTY);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_SOCKET_FACTORY_PORT_PROPERTY);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_SOCKET_FACTORY_CLASS_PROPERTY);
        
        setPropertyIfNotNull(mailProps, pop3Props, MAIL_POP3_HOST_PROPERTY);
        setPropertyIfNotNull(mailProps, pop3Props, MAIL_POP3_PORT_PROPERTY);
        setPropertyIfNotNull(mailProps, pop3Props, MAIL_POP3_SOCKET_FACTORY_PORT_PROPERTY);
        setPropertyIfNotNull(mailProps, pop3Props, MAIL_POP3_SOCKET_FACTORY_CLASS_PROPERTY);
        setPropertyIfNotNull(mailProps, pop3Props, MAIL_POP3_SOCKET_FACTORY_FALLBACK_PROPERTY);
        setPropertyIfNotNull(mailProps, pop3Props, MAIL_POP3S_PORT_PROPERTY);
        setPropertyIfNotNull(mailProps, pop3Props, MAIL_POP3S_SOCKET_FACTORY_PORT_PROPERTY);
        setPropertyIfNotNull(mailProps, pop3Props, MAIL_POP3S_SOCKET_FACTORY_CLASS_PROPERTY);
        setPropertyIfNotNull(mailProps, pop3Props, MAIL_POP3S_SOCKET_FACTORY_FALLBACK_PROPERTY);
        setPropertyIfNotNull(mailProps, pop3Props, MAIL_POP3S_SSL_ENABLE_PROPERTY);

        setPropertyIfNotNull(mailProps, imapProps, MAIL_STORE_PROTOCOL_PROPERTY);
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
        URLName url = new URLName("pop3", pop3Props.getProperty(MAIL_POP3_HOST_PROPERTY), 
                new Integer(pop3Props.getProperty(MAIL_POP3_SOCKET_FACTORY_PORT_PROPERTY)), "", username, password);
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
