package com.sinmin.rest.auth;

import javax.xml.bind.DatatypeConverter;

/**
 * Created by dimuthuupeksha on 2/4/15.
 */
public class BasicAuth {
    public static String[] decode(String auth) {
        //Replacing "Basic THE_BASE_64" to "THE_BASE_64" directly
        auth = auth.replaceFirst("[B|b]asic ", "");

        //Decode the Base64 into byte[]
        byte[] decodedBytes = DatatypeConverter.parseBase64Binary(auth);

        //If the decode fails in any case
        if(decodedBytes == null || decodedBytes.length == 0){
            return null;
        }

        return new String(decodedBytes).split(":", 2);
    }
}
