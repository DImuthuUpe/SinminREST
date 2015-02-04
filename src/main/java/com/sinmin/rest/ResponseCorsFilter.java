package com.sinmin.rest;

import com.sinmin.rest.auth.BasicAuth;
import com.sinmin.rest.auth.LDAPAuthenticator;
import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerResponse;
import com.sun.jersey.spi.container.ContainerResponseFilter;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Created by dimuthuupeksha on 12/13/14.
 */
public class ResponseCorsFilter implements ContainerResponseFilter {
    @Override
    public ContainerResponse filter(ContainerRequest req, ContainerResponse contResp) {

        String auth = req.getHeaderValue("authorization");
        if(auth==null){
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        String[] lap = BasicAuth.decode(auth);

        //If login or password fail
        if(lap == null || lap.length != 2){
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

        System.out.println(lap[0]+" : "+lap[1]);
        LDAPAuthenticator authenticator = new LDAPAuthenticator();

        boolean authenticated = authenticator.authenticate(lap[0],lap[1]);

        if(!authenticated){
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }

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
