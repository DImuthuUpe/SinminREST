package com.sinmin.rest.auth;

import com.sinmin.rest.ConfigManager;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;


public class LDAPAuthenticator {
    public boolean authenticate(String userId, String password){
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY, ConfigManager.getProperty(ConfigManager.LDAP_CONTEXT_FACTORY));
        env.put(Context.PROVIDER_URL, ConfigManager.getProperty(ConfigManager.LDAP_PROVIDER_URL));
        env.put(Context.SECURITY_PRINCIPAL, ConfigManager.getProperty(ConfigManager.LDAP_SECURITY_PRINCIPAL).replace("?",userId));
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
