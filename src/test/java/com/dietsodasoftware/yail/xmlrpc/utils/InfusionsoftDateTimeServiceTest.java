package com.dietsodasoftware.yail.xmlrpc.utils;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created with IntelliJ IDEA.
 * User: wendelschultz
 * Date: 3/12/13
 * Time: 7:32 PM
 * To change this template use File | Settings | File Templates.
 */
public class InfusionsoftDateTimeServiceTest {

    private InfusionsoftDateTimeService timeService = new InfusionsoftDateTimeService();

    final TimeZone mst = TimeZone.getTimeZone("MST");
    final TimeZone est = TimeZone.getTimeZone("EST");

    @Test
    public void testServerTimeNow(){
        if(true) return;

        final Date now = Calendar.getInstance(TimeZone.getTimeZone("MST")).getTime();
        final Date server = timeService.getInfusionsoftNow();


        System.out.println("Date now time:    " + new Date().getTime());
        System.out.println("AZ Cal now time:  " + Calendar.getInstance(mst).getTime().getTime());
        System.out.println("NY Cal now time:  " + Calendar.getInstance(est).getTime().getTime());

        System.out.println("Cal types: " + Calendar.getInstance().getClass());
        System.out.println("AZ: " + now);
        System.out.println("NY: " + server);

        double difference = (server.getTime() - now.getTime()) / 1000;
        System.out.println("Difference: " + difference);
    }

    @Test
    public void testNormalizeDateFromDifferentTimeZones(){
        final String plus11id = "GMT+11";
        final String plus6id = "GMT+6";
        final String minus4id = "GMT-4";
        final String minus9id = "GMT-9";

        final TimeZone minus4 = TimeZone.getTimeZone(minus4id);
        final TimeZone minus9 = TimeZone.getTimeZone(minus9id);
        final TimeZone plus11 = TimeZone.getTimeZone(plus11id);
        final TimeZone plus6 =  TimeZone.getTimeZone(plus6id);

        final TimeZone pst = TimeZone.getTimeZone("PST");
        final TimeZone mst = TimeZone.getTimeZone("MST");
        final TimeZone cst = TimeZone.getTimeZone("CST");
        final TimeZone est = TimeZone.getTimeZone("EST");

        final Date now = new Date();// new Date(111, 07, 13, 22, 37, 23);
        now.setHours(22);
        now.setMinutes(15);
        now.setSeconds(0);


        final Date minus4date = timeService.normalizeDateToInfusionsoftTime(now, minus4);
        final Date minus9date = timeService.normalizeDateToInfusionsoftTime(now, minus9);
        final Date plus11date = timeService.normalizeDateToInfusionsoftTime(now, plus11);
        final Date plus6date = timeService.normalizeDateToInfusionsoftTime(now, plus6);
        final Date pstdate = timeService.normalizeDateToInfusionsoftTime(now, pst);
        final Date mstdate = timeService.normalizeDateToInfusionsoftTime(now, mst);
        final Date cstdate = timeService.normalizeDateToInfusionsoftTime(now, cst);
        final Date estdate = timeService.normalizeDateToInfusionsoftTime(now, est);

        final Date server = timeService.getInfusionsoftNow();

        System.out.println("Ser: " + server);
        System.out.println("Now: " + now);
        System.out.println("PST: " + pstdate);
        System.out.println("MST: " + mstdate);
        System.out.println("CST: " + cstdate);
        System.out.println("EST: " + estdate);

        org.junit.Assert.assertEquals(60 * 60 * 1000, (mstdate.getTime() - pstdate.getTime()));
    }
}
