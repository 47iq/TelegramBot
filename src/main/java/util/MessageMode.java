package util;

public enum MessageMode {
    ITMO,
    SCH9;

    public static MessageMode getValue(String s) {
        if(s.toUpperCase().equals("SCH9"))
            return SCH9;
        else
            return ITMO;
    }
}
