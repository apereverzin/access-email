package com.creek.accessemail.connector.mail;

import java.util.StringTokenizer;

/**
 * 
 * @author Andrey Pereverzin
 */
public final class MailUtil {
    private static final String AT = "@";

    public static String extractUsernameFromEmailAddress(String emailAddress) {
        StringTokenizer st = new StringTokenizer(emailAddress, AT);
        if (st.countTokens() == 2) {
            return st.nextToken();
        }
        return emailAddress;
    }
    
    public static String extractHostnameFromEmailAddress(String emailAddress) {
        StringTokenizer st = new StringTokenizer(emailAddress, AT);
        if (st.countTokens() == 2) {
            st.nextToken();
            return st.nextToken();
        }
        return null;
    }
}
