package org.example.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import okhttp3.*;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Mailer {
    public static void sendMail(String to, String subject, String body) {
        try {
            List<String> toList = new ArrayList<>();
            toList.add(to);

            String jsonString = new JSONObject()
                    .put("to", toList)
                    .put("from", "virtual-school@namani.co")
                    .put("subject", subject)
                    .put("plain_body", body)
                    .toString();

            RequestBody jsonBody = RequestBody.create(
                    MediaType.parse("application/json"), jsonString);

            Request request = new Request.Builder()
                    .url("https://postal.namani.co/api/v1/send/message")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("X-Server-API-Key", "")
                    .post(jsonBody)
                    .build();

            Call call = new OkHttpClient().newCall(request);
            Response response = call.execute();

            System.out.println(response.body().string());

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static String templater() {
        return "";
    }

}

