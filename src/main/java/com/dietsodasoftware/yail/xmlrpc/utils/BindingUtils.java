package com.dietsodasoftware.yail.xmlrpc.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class BindingUtils {

    public static String dateAsBinding(Date date, String format){
        final SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return sdf.format(date);
    }

    public static String dateAsBinding(Date date){
        return dateAsBinding(date, "yyyyMMdd");
    }

}
