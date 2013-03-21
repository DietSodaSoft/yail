package com.dietsodasoftware.yail.xmlrpc.service.data;

import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftResponseParsingException;
import com.dietsodasoftware.yail.xmlrpc.service.InfusionsoftXmlRpcServiceOperation;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;

import java.io.IOException;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 2/24/13
 * Time: 4:31 PM
 */
public class DataServiceGetAppointmentCalOperation extends InfusionsoftXmlRpcServiceOperation<String> {
    private static final String RPC_NAME = "DataService.getAppointmentICal";

    private final int appointmentId;

    public DataServiceGetAppointmentCalOperation(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    @Override
    protected List<?> getOperationParameters() {
        final List params = new LinkedList();
        params.add(appointmentId);

        return params;
    }

    @Override
    public String getRpcName() {
        return RPC_NAME;
    }

    public static final Calendar asIcal4jCalendar(final String rawResponse) throws InfusionsoftResponseParsingException {
        final String responseString = (String)rawResponse;
        final StringReader reader = new StringReader(responseString);
        final CalendarBuilder builder = new CalendarBuilder();
        try {
            return builder.build(reader);
        } catch (IOException e) {
            // unreachable
            throw new InfusionsoftResponseParsingException("Unable to fetch calendar. ", e);
        } catch (ParserException e) {
            throw new InfusionsoftResponseParsingException("Unable to parse calendar. ", e);
        } catch (Exception e) {
            throw new InfusionsoftResponseParsingException("Unexpected error handling calendar response: ", e);
        }

    }

    @Override
    public String parseResult(Object rawResponse) {
        return (String)rawResponse;
    }
}
