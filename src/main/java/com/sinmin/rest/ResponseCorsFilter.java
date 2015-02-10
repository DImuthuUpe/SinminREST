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
package com.sinmin.rest;

import com.sinmin.rest.auth.BasicAuth;
import com.sinmin.rest.auth.LDAPAuthenticator;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;
import org.apache.log4j.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ResponseCorsFilter implements ContainerResponseFilter { //authentication and Access-Control-Allow-Origin response header managing
    final static Logger logger = Logger.getLogger(ResponseCorsFilter.class);

    @Override
    public ContainerResponse filter(ContainerRequest req, ContainerResponse contResp) {

        logger.info("Authorizing.....");
        String auth = req.getHeaderValue("authorization");
        if(auth==null){
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        String[] lap = BasicAuth.decode(auth);

        //If login or password fail
        if(lap == null || lap.length != 2){
            logger.info("Authorizing failed : invalid input");
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        LDAPAuthenticator authenticator = new LDAPAuthenticator();

        boolean authenticated = authenticator.authenticate(lap[0],lap[1]);

        if(!authenticated){
            logger.info("Authorizing failed : invalid credentials");
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        logger.info("Authorization Succeeded");

        Response.ResponseBuilder resp = Response.fromResponse(contResp.getResponse());
        resp.header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS");

        String reqHead = req.getHeaderValue("Access-Control-Request-Headers");

        if(null != reqHead && !reqHead.equals("")){
            resp.header("Access-Control-Allow-Headers", reqHead);
        }

        contResp.setResponse(resp.build());
        return contResp;
    }
}
