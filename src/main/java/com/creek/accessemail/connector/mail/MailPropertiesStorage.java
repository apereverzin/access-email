package com.creek.accessemail.connector.mail;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.URLName;

import com.creek.accessemail.connector.mail.CheckResult;
import com.creek.accessemail.connector.mail.TrueFalse;
import static com.creek.accessemail.connector.mail.CheckResult.UNKNOWN;
import static com.creek.accessemail.connector.mail.MailUtil.extractUsernameFromEmailAddress;
import static com.creek.accessemail.connector.mail.TrueFalse.UNDEFINED;

/**
 * 
 * @author Andrey Pereverzin
 */
public class MailPropertiesStorage {
    private String username;
    private String emailAddress;
    private final String password;
    
    private TrueFalse useFullEmailAddress;
    private CheckResult smtpCheckResult;
    private CheckResult pop3CheckResult;
    private CheckResult imapCheckResult;
    
    private final Properties mailProps;
    private final Properties smtpProps = new Properties();
    private final Properties pop3Props = new Properties();
    private final Properties imapProps = new Properties();

    public static final String MAIL_USERNAME_PROPERTY = "mail.username";
    public static final String EMAIL_ADDRESS_PROPERTY = "email.address";
    public static final String MAIL_PASSWORD_PROPERTY = "mail.password";

    public static final String USE_FULL_EMAIL_ADDRESS_PROPERTY = "use.full.email.address";
    public static final String SMTP_CHECK_RESULT_PROPERTY = "smtp.result";
    public static final String POP3_CHECK_RESULT_PROPERTY = "pop3.result";
    public static final String IMAP_CHECK_RESULT_PROPERTY = "imap.result";

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
    
    public MailPropertiesStorage(Properties _mailProps) {
        this.mailProps = _mailProps;
        setCommonProperties();
        password = mailProps.getProperty(MAIL_PASSWORD_PROPERTY);
        
        setCheckResultProperties(mailProps);
        
        setSmtpProperties();
        
        setPop3Properties();

        setImapProperties();
    }

    public Session getSMTPSession() {
        if (!isUseFullEmailAddressDefined()) {
            return null;
        }
        
        final String authName = getAuthName();
        return Session.getInstance(smtpProps,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(authName, password);
                    }
                });
    }

    public Session getSMTPSessionUsingUsername() {
        return Session.getInstance(smtpProps,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }
    
    public Session getSMTPSessionUsingEmailAddress() {
        return Session.getInstance(smtpProps,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(emailAddress, password);
                    }
                });
    }
    
    public URLName getPop3URLName() {
        if (!isUseFullEmailAddressDefined()) {
            return null;
        }
        
        final String authName = getAuthName();
        URLName url = new URLName("pop3", pop3Props.getProperty(MAIL_POP3_HOST_PROPERTY), 
                new Integer(pop3Props.getProperty(MAIL_POP3_SOCKET_FACTORY_PORT_PROPERTY)), "", authName, password);
        return url;
    }
    
    public URLName getPop3URLNameUsingUsername() {
        URLName url = new URLName("pop3", pop3Props.getProperty(MAIL_POP3_HOST_PROPERTY), 
                new Integer(pop3Props.getProperty(MAIL_POP3_SOCKET_FACTORY_PORT_PROPERTY)), "", username, password);
        return url;
    }
    
    public URLName getPop3URLNameUsingEmailAddress() {
        URLName url = new URLName("pop3", pop3Props.getProperty(MAIL_POP3_HOST_PROPERTY), 
                new Integer(pop3Props.getProperty(MAIL_POP3_SOCKET_FACTORY_PORT_PROPERTY)), "", emailAddress, password);
        return url;
    }
    
    public Properties getSmtpProperties() {
        return smtpProps;
    }

    public Properties getPop3Properties() {
        return pop3Props;
    }

    public Properties getImapProperties() {
        return imapProps;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
    public boolean isUseFullEmailAddressDefined() {
        return !useFullEmailAddress.isUndefined();
    }
    
    public boolean isUseFullEmailAddressTrue() {
        return useFullEmailAddress.isTrue();
    }
    
    public boolean isUseFullEmailAddressFalse() {
        return useFullEmailAddress.isFalse();
    }
    
    public void setUseFullEmailAddress(TrueFalse val) {
        useFullEmailAddress = val;
    }
    
    public CheckResult getSmtpCheckResult() {
        return getCheckResult(mailProps, SMTP_CHECK_RESULT_PROPERTY);
    }
    
    public boolean isSmtpCheckResultKnown() {
        return !smtpCheckResult.isUnknown();
    }
    
    public boolean isSmtpCheckResultSuccess() {
        return smtpCheckResult.isSuccess();
    }
    
    public boolean isSmtpCheckResultFailure() {
        return smtpCheckResult.isFailure();
    }
    
    public void setSmtpCheckResult(CheckResult res) {
        smtpCheckResult = res;
        setCheckResult(SMTP_CHECK_RESULT_PROPERTY, res);
    }
    
    public CheckResult getPop3CheckResult(Properties mailProps) {
        return getCheckResult(mailProps, POP3_CHECK_RESULT_PROPERTY);
    }

    public boolean isPop3CheckResultKnown() {
        return !pop3CheckResult.isUnknown();
    }
    
    public boolean isPop3CheckResultSuccess() {
        return pop3CheckResult.isSuccess();
    }
    
    public boolean isPop3CheckResultFailure() {
        return pop3CheckResult.isFailure();
    }
    
    public void setPop3CheckResult(CheckResult res) {
        pop3CheckResult = res;
        setCheckResult(POP3_CHECK_RESULT_PROPERTY, res);
    }
    
    public CheckResult getImapCheckResult(Properties mailProps) {
        return getCheckResult(mailProps, IMAP_CHECK_RESULT_PROPERTY);
    }

    public boolean isImapCheckResultKnown() {
        return !imapCheckResult.isUnknown();
    }
    
    public boolean isImapCheckpResultSuccess() {
        return imapCheckResult.isSuccess();
    }
    
    public boolean isImapCheckpResultFailure() {
        return imapCheckResult.isFailure();
    }
    
    public void setImapCheckResult(CheckResult res) {
        imapCheckResult = res;
        setCheckResult(IMAP_CHECK_RESULT_PROPERTY, res);
    }
    
    private String getAuthName() {
        final String authName;
        if (isUseFullEmailAddressTrue()) {
            authName = emailAddress;
        } else {
            authName = username;
        }
        return authName;
    }
    
    private void setSmtpProperties() {
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_HOST_PROPERTY);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_PORT_PROPERTY);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_TRANSPORT_PROTOCOL_PROPERTY);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_AUTH_PROPERTY);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_STARTTLS_ENABLE_PROPERTY);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_SOCKET_FACTORY_PORT_PROPERTY);
        setPropertyIfNotNull(mailProps, smtpProps, MAIL_SMTP_SOCKET_FACTORY_CLASS_PROPERTY);
    }
    
    private void setPop3Properties() {
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
    }

    private void setImapProperties() {
        setPropertyIfNotNull(mailProps, imapProps, MAIL_STORE_PROTOCOL_PROPERTY);
    }

    private void setPropertyIfNotNull(Properties propsFrom, Properties propsTo, String propName) {
        if (propsFrom.getProperty(propName) != null) {
            propsTo.setProperty(propName, propsFrom.getProperty(propName));
        }
    }
    
    private CheckResult getCheckResult(Properties mailProps, String propName) {
        String val = mailProps.getProperty(propName);
        return CheckResult.valueOf(val);
    }
    
    private void setCheckResult(String propName, CheckResult res) {
        mailProps.setProperty(propName, res.getVal());
    }
    
    private void setCommonProperties() {
        emailAddress = mailProps.getProperty(EMAIL_ADDRESS_PROPERTY);
        username = mailProps.getProperty(MAIL_USERNAME_PROPERTY);
        if (username == null) {
            username = extractUsernameFromEmailAddress(emailAddress);
        }

        String useFullEmailAddressProp = mailProps.getProperty(USE_FULL_EMAIL_ADDRESS_PROPERTY);
        if (useFullEmailAddressProp == null) {
            useFullEmailAddress = UNDEFINED;
        } else {
            useFullEmailAddress = TrueFalse.byVal(useFullEmailAddressProp);
        }
        smtpCheckResult = getCheckResultFromProps(SMTP_CHECK_RESULT_PROPERTY);
        pop3CheckResult = getCheckResultFromProps(POP3_CHECK_RESULT_PROPERTY);
        imapCheckResult = getCheckResultFromProps(IMAP_CHECK_RESULT_PROPERTY);
    }
    
    private CheckResult getCheckResultFromProps(String propName) {
        String prop = mailProps.getProperty(propName);
        if (prop == null) {
            return UNKNOWN;
        }
        return CheckResult.byVal(prop);
    }
    
    private void setCheckResultProperties(Properties mailProps) {
        String useFullEmailAddressStr = mailProps.getProperty(USE_FULL_EMAIL_ADDRESS_PROPERTY);
        if (useFullEmailAddressStr == null) {
            mailProps.setProperty(USE_FULL_EMAIL_ADDRESS_PROPERTY, UNDEFINED.getVal());
        } else {
            setUseFullEmailAddress(TrueFalse.byVal(useFullEmailAddressStr));
        }
        setUnknown(mailProps, SMTP_CHECK_RESULT_PROPERTY);
        setUnknown(mailProps, POP3_CHECK_RESULT_PROPERTY);
        setUnknown(mailProps, IMAP_CHECK_RESULT_PROPERTY);
    }

    private void setUnknown(Properties mailProps, String propName) {
        if (mailProps.getProperty(propName) == null) {
            mailProps.setProperty(propName, UNKNOWN.getVal());
        }
    }
}
