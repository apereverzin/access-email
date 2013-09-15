package com.creek.accessemail.connector.mail;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import static com.creek.accessemail.connector.mail.MailPropertiesStorage.USE_FULL_EMAIL_ADDRESS_PROPERTY;
import static com.creek.accessemail.connector.mail.MailPropertiesStorage.MAIL_STORE_PROTOCOL_PROPERTY;
import static com.creek.accessemail.connector.mail.MailPropertiesStorage.MAIL_SMTP_HOST_PROPERTY;
import static com.creek.accessemail.connector.mail.MailPropertiesStorage.MAIL_SMTP_PORT_PROPERTY;
import static com.creek.accessemail.connector.mail.MailPropertiesStorage.MAIL_SMTP_AUTH_PROPERTY;
import static com.creek.accessemail.connector.mail.MailPropertiesStorage.MAIL_SMTP_SOCKET_FACTORY_PORT_PROPERTY;
import static com.creek.accessemail.connector.mail.MailPropertiesStorage.MAIL_SMTP_SOCKET_FACTORY_CLASS_PROPERTY;
import static com.creek.accessemail.connector.mail.MailPropertiesStorage.MAIL_SMTP_STARTTLS_ENABLE_PROPERTY;
import static com.creek.accessemail.connector.mail.MailPropertiesStorage.MAIL_POP3_HOST_PROPERTY;
import static com.creek.accessemail.connector.mail.MailPropertiesStorage.MAIL_POP3_PORT_PROPERTY;
import static com.creek.accessemail.connector.mail.MailPropertiesStorage.MAIL_POP3_SOCKET_FACTORY_PORT_PROPERTY;
import static com.creek.accessemail.connector.mail.MailPropertiesStorage.MAIL_POP3_SOCKET_FACTORY_CLASS_PROPERTY;
import static com.creek.accessemail.connector.mail.MailPropertiesStorage.MAIL_POP3_SOCKET_FACTORY_FALLBACK_PROPERTY;
import static com.creek.accessemail.connector.mail.MailPropertiesStorage.MAIL_POP3S_PORT_PROPERTY;
import static com.creek.accessemail.connector.mail.MailPropertiesStorage.MAIL_POP3S_SOCKET_FACTORY_PORT_PROPERTY;
import static com.creek.accessemail.connector.mail.MailPropertiesStorage.MAIL_POP3S_SOCKET_FACTORY_CLASS_PROPERTY;
import static com.creek.accessemail.connector.mail.MailPropertiesStorage.MAIL_POP3S_SOCKET_FACTORY_FALLBACK_PROPERTY;
import static com.creek.accessemail.connector.mail.MailPropertiesStorage.MAIL_POP3S_SSL_ENABLE_PROPERTY;
import static com.creek.accessemail.connector.mail.MailPropertiesStorage.MAIL_IMAP_HOST_PROPERTY;
import static com.creek.accessemail.connector.mail.MailPropertiesStorage.MAIL_IMAP_PORT_PROPERTY;

import static com.creek.accessemail.connector.mail.TrueFalse.TRUE;
import static com.creek.accessemail.connector.mail.TrueFalse.FALSE;

/**
 * 
 * @author Andrey Pereverzin
 */
public class PredefinedMailProperties {
    private static Map<String, Properties> mailProperties = new HashMap<String, Properties>();
    
    private static Properties yahoocomProps = new Properties();
    private static Properties gmailProps = new Properties();
    private static Properties googlemailProps = new Properties();
    private static Properties aolProps = new Properties();
    private static Properties hotmailProps = new Properties();
    
    private static final String YAHOOCOM = "yahoo";
    private static final String GMAIL = "gmail";
    private static final String GOOGLEMAIL = "googlemail";
    private static final String AOL = "aol";
    private static final String HOTMAIL = "hotmail";
    
    static {
        yahoocomProps.setProperty(MAIL_STORE_PROTOCOL_PROPERTY, "imap");
        yahoocomProps.setProperty(MAIL_SMTP_HOST_PROPERTY, "smtp.mail.yahoo.com");
        yahoocomProps.setProperty(MAIL_SMTP_PORT_PROPERTY, "587");
        yahoocomProps.setProperty(MAIL_SMTP_AUTH_PROPERTY, TRUE.getVal());
        yahoocomProps.setProperty(MAIL_SMTP_STARTTLS_ENABLE_PROPERTY, TRUE.getVal());
        yahoocomProps.setProperty(MAIL_POP3_HOST_PROPERTY,  "pop.mail.yahoo.com");
        yahoocomProps.setProperty(MAIL_POP3_PORT_PROPERTY,  "995");
        yahoocomProps.setProperty(MAIL_POP3_SOCKET_FACTORY_PORT_PROPERTY, "995");
        yahoocomProps.setProperty(MAIL_POP3_SOCKET_FACTORY_CLASS_PROPERTY, "javax.net.ssl.SSLSocketFactory");
        yahoocomProps.setProperty(MAIL_POP3_SOCKET_FACTORY_FALLBACK_PROPERTY, FALSE.getVal());
        yahoocomProps.setProperty(MAIL_IMAP_HOST_PROPERTY, "imap.mail.yahoo.com");
        yahoocomProps.setProperty(MAIL_IMAP_PORT_PROPERTY, "993");
        yahoocomProps.setProperty(USE_FULL_EMAIL_ADDRESS_PROPERTY, TRUE.getVal());
        
        //gmailProps.setProperty(MailSessionKeeper.MAIL_STORE_PROTOCOL, "imap");
        gmailProps.setProperty(MAIL_SMTP_HOST_PROPERTY, "smtp.gmail.com");
        gmailProps.setProperty(MAIL_SMTP_PORT_PROPERTY, "465");
        gmailProps.setProperty(MAIL_SMTP_AUTH_PROPERTY, TRUE.getVal());
        gmailProps.setProperty(MAIL_SMTP_STARTTLS_ENABLE_PROPERTY, TRUE.getVal());
        gmailProps.setProperty(MAIL_SMTP_SOCKET_FACTORY_PORT_PROPERTY, "465");
        gmailProps.setProperty(MAIL_SMTP_SOCKET_FACTORY_CLASS_PROPERTY, "javax.net.ssl.SSLSocketFactory");
        gmailProps.setProperty(MAIL_POP3_HOST_PROPERTY,  "pop.gmail.com");
        gmailProps.setProperty(MAIL_POP3_PORT_PROPERTY,  "995");
        gmailProps.setProperty(MAIL_POP3_SOCKET_FACTORY_PORT_PROPERTY, "995");
        gmailProps.setProperty(MAIL_POP3_SOCKET_FACTORY_CLASS_PROPERTY, "javax.net.ssl.SSLSocketFactory");
        gmailProps.setProperty(MAIL_POP3_SOCKET_FACTORY_FALLBACK_PROPERTY, FALSE.getVal());
        gmailProps.setProperty(MAIL_IMAP_HOST_PROPERTY, "imap.gmail.com");
        gmailProps.setProperty(USE_FULL_EMAIL_ADDRESS_PROPERTY, FALSE.getVal());
        
        //googlemailProps.setProperty(MailSessionKeeper.MAIL_STORE_PROTOCOL, "imap");
        googlemailProps.setProperty(MAIL_SMTP_HOST_PROPERTY, "smtp.gmail.com");
        googlemailProps.setProperty(MAIL_SMTP_PORT_PROPERTY, "465");
        googlemailProps.setProperty(MAIL_SMTP_AUTH_PROPERTY, TRUE.getVal());
        googlemailProps.setProperty(MAIL_SMTP_STARTTLS_ENABLE_PROPERTY, TRUE.getVal());
        googlemailProps.setProperty(MAIL_SMTP_SOCKET_FACTORY_PORT_PROPERTY, "465");
        googlemailProps.setProperty(MAIL_SMTP_SOCKET_FACTORY_CLASS_PROPERTY, "javax.net.ssl.SSLSocketFactory");
        googlemailProps.setProperty(MAIL_POP3_HOST_PROPERTY,  "pop.gmail.com");
        googlemailProps.setProperty(MAIL_POP3_PORT_PROPERTY,  "995");
        googlemailProps.setProperty(MAIL_POP3_SOCKET_FACTORY_PORT_PROPERTY, "995");
        googlemailProps.setProperty(MAIL_POP3_SOCKET_FACTORY_CLASS_PROPERTY, "javax.net.ssl.SSLSocketFactory");
        googlemailProps.setProperty(MAIL_POP3_SOCKET_FACTORY_FALLBACK_PROPERTY, FALSE.getVal());
        googlemailProps.setProperty(MAIL_IMAP_HOST_PROPERTY, "imap.gmail.com");
        googlemailProps.setProperty(USE_FULL_EMAIL_ADDRESS_PROPERTY, FALSE.getVal());
        
        aolProps.setProperty(MAIL_STORE_PROTOCOL_PROPERTY, "imap");
        aolProps.setProperty(MAIL_SMTP_HOST_PROPERTY, "smtp.aol.com");
        aolProps.setProperty(MAIL_SMTP_PORT_PROPERTY, "587");
        aolProps.setProperty(MAIL_SMTP_AUTH_PROPERTY, TRUE.getVal());
        aolProps.setProperty(MAIL_SMTP_STARTTLS_ENABLE_PROPERTY, TRUE.getVal());
        aolProps.setProperty(MAIL_POP3_HOST_PROPERTY,  "pop.aol.com");
        aolProps.setProperty(MAIL_POP3_PORT_PROPERTY,  "995");
        aolProps.setProperty(MAIL_POP3_SOCKET_FACTORY_PORT_PROPERTY, "995");
        aolProps.setProperty(MAIL_POP3_SOCKET_FACTORY_CLASS_PROPERTY, "javax.net.ssl.SSLSocketFactory");
        aolProps.setProperty(MAIL_POP3_SOCKET_FACTORY_FALLBACK_PROPERTY, FALSE.getVal());
        aolProps.setProperty(MAIL_IMAP_HOST_PROPERTY, "imap.aol.com");
        aolProps.setProperty(MAIL_IMAP_PORT_PROPERTY, "993");
        aolProps.setProperty(USE_FULL_EMAIL_ADDRESS_PROPERTY, FALSE.getVal());
        
        hotmailProps.setProperty(MAIL_SMTP_HOST_PROPERTY, "smtp.live.com");
        hotmailProps.setProperty(MAIL_SMTP_PORT_PROPERTY, "465");
        hotmailProps.setProperty(MAIL_SMTP_AUTH_PROPERTY, TRUE.getVal());
        hotmailProps.setProperty(MAIL_SMTP_SOCKET_FACTORY_PORT_PROPERTY, "465");
        hotmailProps.setProperty(MAIL_SMTP_SOCKET_FACTORY_CLASS_PROPERTY, "javax.net.ssl.SSLSocketFactory");
        hotmailProps.setProperty(MAIL_SMTP_STARTTLS_ENABLE_PROPERTY, TRUE.getVal());
        hotmailProps.setProperty(MAIL_POP3_HOST_PROPERTY,  "pop3.live.com");
        hotmailProps.setProperty(MAIL_POP3S_PORT_PROPERTY,  "995");
        hotmailProps.setProperty(MAIL_POP3S_SOCKET_FACTORY_PORT_PROPERTY, "995");
        hotmailProps.setProperty(MAIL_POP3S_SOCKET_FACTORY_CLASS_PROPERTY, "javax.net.ssl.SSLSocketFactory");
        hotmailProps.setProperty(MAIL_POP3S_SOCKET_FACTORY_FALLBACK_PROPERTY, FALSE.getVal());
        hotmailProps.setProperty(MAIL_POP3S_SSL_ENABLE_PROPERTY, TRUE.getVal());
        hotmailProps.setProperty(USE_FULL_EMAIL_ADDRESS_PROPERTY, TRUE.getVal());
//        Session session = Session.getInstance(pop3Props, null);
//        Store store = session.getStore("pop3s");
//        store.connect(host, 995, username, password);

        mailProperties.put(YAHOOCOM, yahoocomProps);
        mailProperties.put(GMAIL, gmailProps);
        mailProperties.put(GOOGLEMAIL, gmailProps);
        mailProperties.put(AOL, aolProps);
        mailProperties.put(HOTMAIL, hotmailProps);
    }
    
    public static Set<String> getPredefinedServers() {
        return mailProperties.keySet();
    }
    
    public static Properties getPredefinedPropertiesForServer(String server) {
        return mailProperties.get(server);
    }
    
    public static Properties getPredefinedProperties(String emailAddress) {
        for (String server : getPredefinedServers()) {
            if (emailAddress.contains(server)) {
                return mailProperties.get(server);
            }
        }
        return null;
    }
}
