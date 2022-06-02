package com.mvc.forrest.service.firebase;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.auth.oauth2.AccessToken;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PushNotificationService {
	
    @Value("${fcm.certification}")
    private String googleApplicationCredentials;
	
    private static final String PROJECT_ID = "bit-project-runrunfunfun";
    private static final String BASE_URL = "https://fcm.googleapis.com";
    private static final String FCM_SEND_ENDPOINT = "/v1/projects/" + PROJECT_ID + "/messages:send";
    
    private static final String MESSAGING_SCOPE = "https://www.googleapis.com/auth/firebase.messaging";
    private static final String[] SCOPES = { MESSAGING_SCOPE };
    
    private static final String TITLE = "FCM Notification";
    private static final String BODY = "Notification from FCM";
    public static final String MESSAGE_KEY = "message";
    
    
    /**
    firebase 서버와 통신하기 위해 connection 열어 줌
     */
    private static HttpURLConnection getConnection() throws IOException {
        // [START use_access_token]
        URL url = new URL(BASE_URL + FCM_SEND_ENDPOINT);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestProperty("Authorization", "Bearer " + getAccessToken());
        httpURLConnection.setRequestProperty("Content-Type", "application/json; UTF-8");
        return httpURLConnection;
        // [END use_access_token]
      }

    
    /**
    토큰 값을 받아오는 method
     */
    private static String getAccessToken() throws IOException {
    	  GoogleCredentials googleCredentials = GoogleCredentials
    	          .fromStream(new FileInputStream("service-account.json"))
    	          .createScoped(Arrays.asList(SCOPES));
    	  googleCredentials.refreshAccessToken();
    	  return googleCredentials.getAccessToken().getTokenValue();
    	}

    
    /**
     firebase와 통신하고, return값을 받는 메소드
     */
    private static void sendMessage(JsonObject fcmMessage) throws IOException {
        HttpURLConnection connection = getConnection();
        connection.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
        writer.write(fcmMessage.toString());
        writer.flush();
        writer.close();

        int responseCode = connection.getResponseCode();
        if (responseCode == 200) {
          String response = inputstreamToString(connection.getInputStream());
          System.out.println("Message sent to Firebase for delivery, response:");
          System.out.println(response);
        } else {
          System.out.println("Unable to send message to Firebase:");
          String response = inputstreamToString(connection.getErrorStream());
          System.out.println(response);
        }
      }

      /**
       누구에게 무엇을 보낼것인지 구성
       */
      private static JsonObject buildNotificationMessage() {
        JsonObject jNotification = new JsonObject();
        jNotification.addProperty("title", TITLE);
        jNotification.addProperty("body", BODY);

        JsonObject jMessage = new JsonObject();
        jMessage.add("notification", jNotification);
        jMessage.addProperty("topic", "news");
        //jMessage.addProperty("token", /* your test device token */); //토큰으로 보내려면 이거 사용
        
        JsonObject jFcm = new JsonObject();
        jFcm.add(MESSAGE_KEY, jMessage);

        return jFcm;
      }

      /**
      return 값을 connection에서 읽어오기 위해서 필요함
       */
      private static String inputstreamToString(InputStream inputStream) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
          stringBuilder.append(scanner.nextLine());
        }
        return stringBuilder.toString();
      }

      /**
       로그용 함수
       */
      private static void prettyPrint(JsonObject jsonObject) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(jsonObject) + "\n");
      }

	    /**
	    전체 공지
	    */
        public static void sendCommonMessage() throws IOException {
          JsonObject notificationMessage = buildNotificationMessage();
          System.out.println("FCM request body for message using common notification object:");
          prettyPrint(notificationMessage);
          sendMessage(notificationMessage);
        }
}
