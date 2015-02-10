/*
* Licensed to the Apache Software Foundation (ASF) under one
* or more contributor license agreements.  See the NOTICE file
* distributed with this work for additional information
* regarding copyright ownership.  The ASF licenses this file
* to you under the Apache License, Version 2.0 (the
* "License"); you may not use this file except in compliance
* with the License.  You may obtain a copy of the License at
*
*   http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

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
            ctx = new InitialDirContext(env); // create new LDAP directory context for given credentials
            return true;
        } catch (NamingException e) {
            return false;
        }
    }
}
