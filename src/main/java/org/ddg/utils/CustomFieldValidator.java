package org.ddg.utils;

/**
 * Created by edutilos on 26.10.18.
 */
public class CustomFieldValidator {
    public static String validateInteger(String txt) {
        try {
            Integer.parseInt(txt);
            return "";
        } catch(Exception ex) {
            return "Please enter valid Integer.";
        }
    }

    public static String validateLong(String txt) {
        try {
            Long.parseLong(txt);
            return "";
        } catch(Exception ex) {
            return "Please enter valid Long.";
        }
    }

    public static String validateString(String txt) {
        if(txt.length() == 0) {
            return "Please enter non-empty String.";
        } else {
            return "";
        }
    }
    public static String validateBoolean(String txt) {
        if(txt.toLowerCase().equals("true") ||
                txt.toLowerCase().equals("false")) {
            return "";
        } else {
            return "Please enter valid Boolean value.";
        }
    }

    public static String validateDouble(String txt) {
        try {
            Double.parseDouble(txt);
            return "";
        } catch(Exception ex) {
            return "Please enter valid Double.";
        }
    }
}
