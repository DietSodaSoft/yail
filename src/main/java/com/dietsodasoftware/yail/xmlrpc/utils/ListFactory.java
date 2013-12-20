package com.dietsodasoftware.yail.xmlrpc.utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: wendel.schultz
 * Date: 4/19/13
 * Time: 10:39 AM
 */
public class ListFactory {

    public static <T> List<T> quickLinkedList(T... items){
        final List<T> list = new LinkedList<T>();
        for(T t: items){
            list.add(t);
        }
        return list;
    }

    public static <T> List<T> quickUnmodifiableLinkedList(T... items){
        return Collections.unmodifiableList(quickLinkedList(items));
    }

}
