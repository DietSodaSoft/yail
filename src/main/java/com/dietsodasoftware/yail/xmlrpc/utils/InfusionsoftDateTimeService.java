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
    private static final DateTimeZone edt = DateTimeZone.forID("EDT");

    public enum DateTimeBinding {
        Date("yyyyMMdd"),
        Time("HH:mm:ss"),
        DateTime("yyyyMMdd HH:mm:ss")
        ;

        private final String serviceFormatMask;
        private DateTimeBinding(String serviceFormatMask) {
            this.serviceFormatMask = serviceFormatMask;
        }
    }

    public Date normalizeDateToInfusionsoftDate(Date date, TimeZone originTimeZone){
        final DateTimeZone otz = forTimeZone(originTimeZone);
        final LocalDateTime local = new LocalDateTime(date.getTime(), otz);

        return local.toDateTime(est).toDate();
    }

    public Date normalizeDateToInfusionsoftDate(DateTime date){
        return date.toDateTime(est).toDate();
    }

    public Date getInfusionsoftNow(){
        final DateTime now = new DateTime();
        final LocalDateTime serverNow = now.withZone(est).toLocalDateTime();

        return serverNow.toDateTime().toDate();
    }

    public Date getLocalNow(TimeZone zone){
        final LocalDateTime now = new LocalDateTime(forTimeZone(zone));

        return now.toDate();
    }

    public Date getBeginningOfLocalDay(TimeZone zone){
        final LocalDateTime now = new LocalDateTime(forTimeZone(zone));

        return now.toDateTime().toDateMidnight().toDate();
    }

    public Date getBeginningOfLocalDay(Date date, TimeZone zone){
        final LocalDateTime now = new LocalDateTime(date.getTime(), forTimeZone(zone));

        return now.toDateTime().toDateMidnight().toDate();
    }

    public Date getEndOfLocalDay(TimeZone zone){
        final LocalDateTime now = new LocalDateTime(forTimeZone(zone));

        return now.plusDays(1).toDateTime().toDateMidnight().toDateTime().minusSeconds(1).toDate();
    }

    public Date getEndOfLocalDay(Date date, TimeZone zone){
        final LocalDateTime now = new LocalDateTime(date.getTime(), forTimeZone(zone));

        return now.plusDays(1).toDateTime().toDateMidnight().toDateTime().minusSeconds(1).toDate();
    }


    /**
     * A few service methods to format correct binding strings for service calls.
     */
    public String dateAsServiceBindingValue(Date date, DateTimeBinding binding){
        return BindingUtils.dateAsBinding(date, binding.serviceFormatMask);
    }

    public String dateAsServiceBindingValue(DateTime date, DateTimeBinding binding){
        return BindingUtils.dateAsBinding(date.toDate(), binding.serviceFormatMask);
    }

    public String todayAsBindingValue(TimeZone originTimeZone, DateTimeBinding binding){
        final DateTimeZone zone = DateTimeZone.forTimeZone(originTimeZone);
        final LocalDateTime today = new LocalDateTime(originTimeZone);

        return BindingUtils.dateAsBinding(today.toDate(), binding.serviceFormatMask);
    }

    private static DateTimeZone forTimeZone(TimeZone zone){
        return DateTimeZone.forTimeZone(zone);
    }

}
