package com.dietsodasoftware.yail.oauth2.client.annotations;

/**
 * Some API calls require OAuth.  In other words, they don't support legacy API Key/Vendor key auth methods.
 *
 * User: wendel.schultz
 * Date: 4/18/14
 */

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RequiresOAuth {
}
