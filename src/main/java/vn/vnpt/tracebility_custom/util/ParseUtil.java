package vn.vnpt.tracebility_custom.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ParseUtil {

    public static String parseToString(Object obj) {

        return obj == null ? " " : obj.toString().trim();
    }

    public static String toString(Date date, String pattern) {
        if (date == null) {
            return null;
        } else {
            SimpleDateFormat formater = new SimpleDateFormat(pattern);
            return formater.format(date);
        }
    }

    public static Date stringToDate(String date, String pattern) throws ParseException {
        if (date == null || date.equals("")) return null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(date);
    }
}
