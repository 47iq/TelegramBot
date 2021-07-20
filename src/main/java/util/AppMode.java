package util;

public enum AppMode {
    TEST,
    RELEASE;

    public static AppMode getValue(String s) {
        if(s.toUpperCase().equals("RELEASE"))
            return RELEASE;
        else
            return TEST;
    }
}
