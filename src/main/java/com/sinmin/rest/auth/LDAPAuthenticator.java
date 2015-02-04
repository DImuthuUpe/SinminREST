package com.sinmin.rest.auth;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

/**
 * Created by dimuthuupeksha on 2/4/15.
 */
public class LDAPAuthenticator {
    public boolean authenticate(String userId, String password){
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://192.248.15.239:389");
        env.put(Context.SECURITY_PRINCIPAL, "cn="+userId+",ou=users,dc=sinmin,dc=org");
        env.put(Context.SECURITY_CREDENTIALS, password);

        DirContext ctx = null;
        try {
            ctx = new InitialDirContext(env);
            return true;
        } catch (NamingException e) {
            return false;
        }
    }
}
