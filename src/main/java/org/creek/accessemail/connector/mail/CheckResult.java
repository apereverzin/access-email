package org.creek.accessemail.connector.mail;

/**
 * 
 * @author Andrey Pereverzin
 */
public enum CheckResult {
    UNKNOWN("unknown"), SUCCESS("success"), FAILURE("failure");
    
    private final String val;
    
    private CheckResult(String _val) {
        this.val = _val;
    }

    public String getVal() {
        return val;
    }
    
    public static CheckResult byVal(String val) {
        for (CheckResult res: values()) {
            if (res.getVal().equals(val)) {
                return res;
            }
        }
        
        return UNKNOWN;
    }

    public boolean isUnknown() {
        return this == UNKNOWN;
    }
    
    public boolean isSuccess() {
        return this == SUCCESS;
    }
    
    public boolean isFailure() {
        return this == FAILURE;
    }
}
