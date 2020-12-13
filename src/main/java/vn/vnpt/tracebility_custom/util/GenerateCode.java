package vn.vnpt.tracebility_custom.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class GenerateCode {

    public static String generateOTP() {
        return RandomStringUtils.randomNumeric(6);
    }

    public static Integer getRadomImageLength() {
        Random rd = new Random();
        int high = 30;
        int low = 1;
        return rd.nextInt(high - low) + low;
    }

    public static String genarateUploadKey() {

        int length = getRadomImageLength();

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);

        return year + month + day
                + RandomStringUtils.random(length, true, true)
                + RandomStringUtils.random(10, true, false)
                + RandomStringUtils.random(5, false, true);
    }
}
