package com.dietsodasoftware.yail.oauth2.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by wendel.schultz on 2/4/16.
 */
public class Lists {

    public static <T> ArrayList<T> newArrayList(T... objects) {
        ArrayList<T> al = new ArrayList<T>();
        Collections.addAll(al, objects);

        return al;
    }

    public static <T> LinkedList<T> newLinkedList(T... objects) {
        LinkedList<T> ll = new LinkedList<T>();
        Collections.addAll(ll, objects);

        return ll;
    }
}
