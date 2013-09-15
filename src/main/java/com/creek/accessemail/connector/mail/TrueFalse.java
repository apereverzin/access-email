package com.creek.accessemail.connector.mail;

/**
 * 
 * @author Andrey Pereverzin
 */
public enum TrueFalse {
    UNDEFINED("unknown"), TRUE("true"), FALSE("false");
    
    private final String val;
    
    private TrueFalse(String _val) {
        this.val = _val;
    }

    public String getVal() {
        return val;
    }
    
    public static TrueFalse byVal(String val) {
        for (TrueFalse res: values()) {
            if (res.getVal().equals(val)) {
                return res;
            }
        }
        
        return null;
    }

    public boolean isUndefined() {
        return this == UNDEFINED;
    }

    public boolean isTrue() {
        return this == TRUE;
    }

    public boolean isFalse() {
        return this == FALSE;
    }
}
