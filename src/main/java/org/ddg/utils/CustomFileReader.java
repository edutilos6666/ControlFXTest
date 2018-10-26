package org.ddg.utils;

import java.io.*;
import java.util.stream.Collectors;

public class CustomFileReader {
    public static String readFromInputStream(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        return reader.lines().collect(Collectors.joining("\n"));
    }

    public static String readFromClassPath(String fullFileName) {
        Class clazz = CustomFileReader.class;
        return readFromInputStream(clazz.getResourceAsStream(fullFileName));
    }

    public static String readFromAbsoultePath(String fullPath) {
        try {
            return readFromInputStream(new FileInputStream(fullPath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
