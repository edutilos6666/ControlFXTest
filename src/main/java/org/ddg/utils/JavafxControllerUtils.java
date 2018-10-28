package org.ddg.utils;

import javafx.stage.Stage;
import org.ddg.model.Worker;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by edutilos on 26.10.18.
 */
public class JavafxControllerUtils {
    public static Date convertLocalDateToUtilDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }
    public static java.sql.Date convertLocalDateToSqlDate(LocalDate localDate) {
        return java.sql.Date.valueOf(localDate);
    }

    public static LocalDate convertUtilDateToLocalDate(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDate convertSqlDateToLocalDate(java.sql.Date date) {
        return date.toLocalDate();
    }

}
