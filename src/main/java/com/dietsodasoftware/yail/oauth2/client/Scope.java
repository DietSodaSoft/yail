package com.dietsodasoftware.yail.oauth2.client;

/**
 * User: wendel.schultz
 * Date: 4/12/14
 */
public enum Scope {

    Read("read"),
    Write("write"),
    Execute("execute"),

    Full("full");

    final String scope;

    private Scope(String scope) {
        this.scope = scope;
    }

    public String getScopeName(){
        return scope;
    }

    public static Scope fromScopeName(String name){
        for(Scope scope: values()){
            if(scope.scope.equals(name)){
                return scope;
            }
        }

        return null;
    }
}
