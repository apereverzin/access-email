package com.creek.accessemail.connector.mail;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class PredefinedMailProperties {
    private static Map<String, Properties> mailProperties = new HashMap<String, Properties>();
    
    private static Properties yahoocomProps = new Properties();
    private static Properties gmailProps = new Properties();
    private static Properties googlemailProps = new Properties();
    private static Properties aolProps = new Properties();
    private static Properties hotmailProps = new Properties();
    
    static {
        yahoocomProps.setProperty(MailPropertiesStorage.MAIL_STORE_PROTOCOL, "imap");
        yahoocomProps.setProperty(MailPropertiesStorage.MAIL_SMTP_HOST, "smtp.mail.yahoo.com");
        yahoocomProps.setProperty(MailPropertiesStorage.MAIL_SMTP_PORT, "587");
        yahoocomProps.setProperty(MailPropertiesStorage.MAIL_SMTP_AUTH, "true");
        yahoocomProps.setProperty(MailPropertiesStorage.MAIL_SMTP_STARTTLS_ENABLE, "true");
        yahoocomProps.setProperty(MailPropertiesStorage.MAIL_POP3_SOCKET_FACTORY_CLASS, "javax.net.ssl.SSLSocketFactory");
        yahoocomProps.setProperty(MailPropertiesStorage.MAIL_POP3_SOCKET_FACTORY_FALLBACK, "false");
        yahoocomProps.setProperty(MailPropertiesStorage.MAIL_POP3_HOST,  "pop.mail.yahoo.com");
        yahoocomProps.setProperty(MailPropertiesStorage.MAIL_POP3_PORT,  "995");
        yahoocomProps.setProperty(MailPropertiesStorage.MAIL_POP3_SOCKET_FACTORY_PORT, "995");
        
        //gmailProps.setProperty(MailSessionKeeper.MAIL_STORE_PROTOCOL, "imap");
        gmailProps.setProperty(MailPropertiesStorage.MAIL_SMTP_HOST, "smtp.gmail.com");
        gmailProps.setProperty(MailPropertiesStorage.MAIL_SMTP_PORT, "465");
        gmailProps.setProperty(MailPropertiesStorage.MAIL_SMTP_AUTH, "true");
        gmailProps.setProperty(MailPropertiesStorage.MAIL_SMTP_STARTTLS_ENABLE, "true");
        gmailProps.setProperty(MailPropertiesStorage.MAIL_SMTP_SOCKET_FACTORY_PORT, "465");
        gmailProps.setProperty(MailPropertiesStorage.MAIL_SMTP_SOCKET_FACTORY_CLASS, "javax.net.ssl.SSLSocketFactory");
        gmailProps.setProperty(MailPropertiesStorage.MAIL_POP3_SOCKET_FACTORY_CLASS, "javax.net.ssl.SSLSocketFactory");
        gmailProps.setProperty(MailPropertiesStorage.MAIL_POP3_SOCKET_FACTORY_FALLBACK, "false");
        gmailProps.setProperty(MailPropertiesStorage.MAIL_POP3_HOST,  "pop.gmail.com");
        gmailProps.setProperty(MailPropertiesStorage.MAIL_POP3_PORT,  "995");
        gmailProps.setProperty(MailPropertiesStorage.MAIL_POP3_SOCKET_FACTORY_PORT, "995");
        
        //googlemailProps.setProperty(MailSessionKeeper.MAIL_STORE_PROTOCOL, "imap");
        googlemailProps.setProperty(MailPropertiesStorage.MAIL_SMTP_HOST, "smtp.gmail.com");
        googlemailProps.setProperty(MailPropertiesStorage.MAIL_SMTP_PORT, "465");
        googlemailProps.setProperty(MailPropertiesStorage.MAIL_SMTP_AUTH, "true");
        googlemailProps.setProperty(MailPropertiesStorage.MAIL_SMTP_STARTTLS_ENABLE, "true");
        googlemailProps.setProperty(MailPropertiesStorage.MAIL_SMTP_SOCKET_FACTORY_PORT, "465");
        googlemailProps.setProperty(MailPropertiesStorage.MAIL_SMTP_SOCKET_FACTORY_CLASS, "javax.net.ssl.SSLSocketFactory");
        googlemailProps.setProperty(MailPropertiesStorage.MAIL_POP3_SOCKET_FACTORY_CLASS, "javax.net.ssl.SSLSocketFactory");
        googlemailProps.setProperty(MailPropertiesStorage.MAIL_POP3_SOCKET_FACTORY_FALLBACK, "false");
        googlemailProps.setProperty(MailPropertiesStorage.MAIL_POP3_HOST,  "pop.gmail.com");
        googlemailProps.setProperty(MailPropertiesStorage.MAIL_POP3_PORT,  "995");
        googlemailProps.setProperty(MailPropertiesStorage.MAIL_POP3_SOCKET_FACTORY_PORT, "995");
        
        aolProps.setProperty(MailPropertiesStorage.MAIL_STORE_PROTOCOL, "imap");
        aolProps.setProperty(MailPropertiesStorage.MAIL_SMTP_HOST, "smtp.aol.com");
        aolProps.setProperty(MailPropertiesStorage.MAIL_SMTP_PORT, "587");
        aolProps.setProperty(MailPropertiesStorage.MAIL_SMTP_AUTH, "true");
        aolProps.setProperty(MailPropertiesStorage.MAIL_SMTP_STARTTLS_ENABLE, "true");
        aolProps.setProperty(MailPropertiesStorage.MAIL_POP3_SOCKET_FACTORY_CLASS, "javax.net.ssl.SSLSocketFactory");
        aolProps.setProperty(MailPropertiesStorage.MAIL_POP3_SOCKET_FACTORY_FALLBACK, "false");
        aolProps.setProperty(MailPropertiesStorage.MAIL_POP3_HOST,  "pop.aol.com");
        aolProps.setProperty(MailPropertiesStorage.MAIL_POP3_PORT,  "995");
        aolProps.setProperty(MailPropertiesStorage.MAIL_POP3_SOCKET_FACTORY_PORT, "995");
        
        hotmailProps.setProperty(MailPropertiesStorage.MAIL_SMTP_HOST, "smtp.live.com");
        hotmailProps.setProperty(MailPropertiesStorage.MAIL_SMTP_PORT, "465");
        hotmailProps.setProperty(MailPropertiesStorage.MAIL_SMTP_AUTH, "true");
        hotmailProps.setProperty(MailPropertiesStorage.MAIL_SMTP_STARTTLS_ENABLE, "true");
        hotmailProps.setProperty(MailPropertiesStorage.MAIL_SMTP_SOCKET_FACTORY_PORT, "465");
        hotmailProps.setProperty(MailPropertiesStorage.MAIL_SMTP_SOCKET_FACTORY_CLASS, "javax.net.ssl.SSLSocketFactory");
        hotmailProps.setProperty("mail.pop3s.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        hotmailProps.setProperty("mail.pop3s.socketFactory.fallback", "false");
        hotmailProps.setProperty("mail.pop3.ssl.enable", "true");
        hotmailProps.setProperty(MailPropertiesStorage.MAIL_POP3_HOST,  "pop3.live.com");
        hotmailProps.setProperty("mail.pop3s.port",  "995");
        hotmailProps.setProperty("mail.pop3s.socketFactory.port", "995");
//        Session session = Session.getInstance(pop3Props, null);
//        Store store = session.getStore("pop3s");
//        store.connect(host, 995, username, password);

        mailProperties.put("yahoocom", yahoocomProps);
        mailProperties.put("gmail", gmailProps);
        mailProperties.put("googlemailProps", aolProps);
        mailProperties.put("aol", aolProps);
        mailProperties.put("hotmail", hotmailProps);
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
