package com.dietsodasoftware.yail.xmlrpc.service.contact;

import com.dietsodasoftware.yail.xmlrpc.utils.ListFactory;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 5/22/13
 * Time: 9:31 PM
 */
public class LegacyActionSetResponse {
    private final List<LegacyActionSetResult> results;

    public LegacyActionSetResponse(Object rawResponse){
        final Object[] structArray = (Object[]) rawResponse;

        results = ListFactory.quickLinkedList();
        for(Object o: structArray){
            final Map<String, Object> struct = (Map<String, Object>) o;
            results.add(new LegacyActionSetResult(struct));
        }
    }

    public List<LegacyActionSetResult> getResults(){
        return results;
    }
}
