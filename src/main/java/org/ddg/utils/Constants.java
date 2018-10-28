package org.ddg.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Constants {
    public static String DELIMITER = " = ";
    public static String SEPARATOR = System.getProperty("line.separator");
    public static double PREF_WIDTH = 800;
    public static double PREF_HEIGHT = 650;
    public static double MIN_TABLE_COLUMN_WIDTH = 200;
    public static DateFormat DATE_FORMAT_1 = new SimpleDateFormat("dd/MM/yyyy");
    public static DateFormat TIME_FORMAT_1 = new SimpleDateFormat("hh:mm:ss");
}
