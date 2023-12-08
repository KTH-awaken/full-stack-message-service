package com.example.messageservice.Core.Security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;

public class TokenDecoder {


    public static String decodeJwtToken(String authHeader) {
        String token = authHeader.substring(7);

        String[] splitToken = token.split("\\.");
        if (splitToken.length != 3) {
            throw new IllegalArgumentException("Invalid JWT token");
        }

        String base64EncodedBody = splitToken[1];
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String body = new String(decoder.decode(base64EncodedBody), StandardCharsets.UTF_8);

        return body;
    }


    public static String getRoleFromToken(String authHeader) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {

            JsonNode rootNode = objectMapper.readTree(TokenDecoder.decodeJwtToken(authHeader));

            JsonNode realmAccess = rootNode.path("realm_access");
            if (!realmAccess.isMissingNode() && realmAccess.has("roles")) {
                List<String> roles = objectMapper.convertValue(realmAccess.get("roles"), new TypeReference<List<String>>() {});
                for(String role:roles){
                    if(role.equals("DOCTOR") ||role.equals("PATIENT") || role.equals("EMPLOYEE")){
                        return role;
                    }
                }
                return "empty";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "err";
        }
        return "empty";
    }

    public static String getEmailFromToken(String authHeader) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(TokenDecoder.decodeJwtToken(authHeader));
            if (rootNode.has("email")) {
                return rootNode.get("email").asText();
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
