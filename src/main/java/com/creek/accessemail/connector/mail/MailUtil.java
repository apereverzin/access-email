package com.creek.accessemail.connector.mail;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Andrey Pereverzin
 */
public final class MailUtil {
    private static final String AT = "@";
    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN);
    
    public static String extractUsernameFromEmailAddress(final String emailAddress) {
        if (emailAddress == null) {
            return "";
        }
        
        StringTokenizer st = new StringTokenizer(emailAddress, AT);
        if (st.countTokens() == 2) {
            return st.nextToken();
        }
        
        return emailAddress;
    }
    
    public static String extractHostnameFromEmailAddress(final String emailAddress) {
        if (emailAddress == null) {
            return "";
        }
        
        StringTokenizer st = new StringTokenizer(emailAddress, AT);
        if (st.countTokens() == 2) {
            st.nextToken();
            return st.nextToken();
        }

        return null;
    }
    
    public static boolean isEmailAddressValid(final String emailAddress) {
        Matcher matcher = PATTERN.matcher(emailAddress);
        return matcher.matches();    }
}
