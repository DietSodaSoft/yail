package com.dietsodasoftware.yail.xmlrpc.model;

import java.util.Collection;

public interface NamedField {
    public enum Access {
        Read, Add, Update, Delete;
    }

	public String name();
	
	public Class<?> typeClass();

    public Collection<Access> getAccess();

    public boolean hasAccess(Access access);
	
}

