package org.example.utils;

import javax.net.ssl.HttpsURLConnection;
import java.io.DataOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Mailer {
    public static void sendMail(String to, String subject, String body) {
        try {
            URL url = new URL("");
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Server-API-Key", System.getenv("MAIL_API_KEY"));

            // Request Parameters
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("from", "virtual-school@namani.co");
            params.put("to", new ArrayList<>().add(to));
            params.put("subject", subject);
            params.put("plain_body", body);

            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(ParamStringBuilder.getParamsString(params));
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            System.out.println("Sending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static String templater(){
        return "";
    }

}

