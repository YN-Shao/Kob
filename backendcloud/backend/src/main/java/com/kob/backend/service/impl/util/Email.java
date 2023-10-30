package com.kob.backend.service.impl.util;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Email {
    public static void send(String email, String username) {
        String apiKey = "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";
        apiKey = "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";
        String apiUrl = "https://api.brevo.com/v3/smtp/email";

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("accept", "application/json");
            connection.setRequestProperty("api-key", apiKey);
            connection.setRequestProperty("content-type", "application/json");
            connection.setDoOutput(true);

            String jsonData = String.format("{\n" +
                    "   \"sender\":{\n" +
                    "      \"name\":\"Eric Cao\",\n" +
                    "      \"email\":\"c1794641427@gmail.com\"\n" +
                    "   },\n" +
                    "   \"to\":[\n" +
                    "      {\n" +
                    "         \"email\":\"%s\",\n" +
                    "         \"name\":\"%s\"\n" +
                    "      }\n" +
                    "   ],\n" +
                    "   \"subject\":\"Hello %s\",\n" +
                    "   \"htmlContent\":\"<html><head></head><body><p>Hello,</p>Welcome in joining KOB, me and the developer teams hopes you enjoy our platform.</p></body></html>\"\n" +
                    "}", email, username, username);

            try (DataOutputStream writer = new DataOutputStream(connection.getOutputStream())) {
                writer.write(jsonData.getBytes("UTF-8"));
            }

            int responseCode = connection.getResponseCode();
            System.out.println(connection.getContent());
            System.out.println(responseCode);

            if (responseCode == 201) {
                System.out.println("Request sent successfully.");
            } else {
                System.out.println("Request failed. Response code: " + responseCode);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
