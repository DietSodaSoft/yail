package com.dietsodasoftware.yail.xmlrpc.utils;

/**
 * Created with IntelliJ IDEA.
 * User: wendelschultz
 * Date: 3/12/13
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalDateTime;

import java.util.Date;
import java.util.TimeZone;

public class InfusionsoftDateTimeService {
    private static final DateTimeZone est = DateTimeZone.forOffsetHours(-5);

    public Date normalizeDateToInfusionsoftTime(Date date, TimeZone originTimeZone){
        final DateTimeZone otz = DateTimeZone.forTimeZone(originTimeZone);
        final LocalDateTime local = new LocalDateTime(date.getTime(), otz);

        return local.toDateTime(est).toDate();
    }

    public Date getInfusionsoftNow(){
        final DateTime now = new DateTime();
        final LocalDateTime serverNow = now.withZone(est).toLocalDateTime();


        return serverNow.toDateTime().toDate();
    }

}
