package de.relluem94.vulcan.toolbox.generators;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date {

    public static String getDate(String dateformat) {
        String DATE_FORMAT = dateformat;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(c.getTime());
    }

    public static String getDate() {
        return getDate("yyyy-MM-dd_HH.mm.ss.SSS");
    }
}
