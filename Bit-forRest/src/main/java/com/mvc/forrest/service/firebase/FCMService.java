package com.mvc.forrest.service.firebase;


import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.google.firebase.messaging.WebpushConfig;
import com.google.firebase.messaging.WebpushFcmOptions;
import com.google.firebase.messaging.WebpushNotification;
import com.mvc.forrest.dao.firebase.FCMTokenDAO;
import com.mvc.forrest.service.domain.User;

import lombok.RequiredArgsConstructor;

import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FCMService implements MessageService {
	
	@Autowired
	private final FCMTokenDAO fcmTokenDAO;

    public void sendMessage(String email) throws InterruptedException, ExecutionException {

    	String token = getToken(email);
    	Notification notification = Notification.builder().setTitle("title").setBody("body").setImage("Image").build();
    	
        Message message = Message.builder()
        		//push 설정부
        		 .setWebpushConfig(WebpushConfig.builder().setNotification(new WebpushNotification(
                         "$GOOG up 1.43% on the day",
                         "$GOOG gained 11.80 points to close at 835.67, up 1.43% on the day.",
                         "https://my-server/icon.png"))
                     .setFcmOptions(WebpushFcmOptions.withLink("https://my-server/page-to-open-on-click"))
                     .build())
            .putData("title", "푸시메세지 테스트")
            .putData("content", "푸시메세지의 내용을 마음대로 설정 할 수 있습니다.")
            .setToken(token)
            .setNotification(notification)
            .setWebpushConfig(null)
            .build();
        
        
        send(message);
    }
	
    public Message webpushMessage() {
        
        Message message = Message.builder()
            .setWebpushConfig(WebpushConfig.builder()
                .setNotification(new WebpushNotification(
                    "$GOOG up 1.43% on the day",
                    "$GOOG gained 11.80 points to close at 835.67, up 1.43% on the day.",
                    "https://my-server/icon.png"))
                .setFcmOptions(WebpushFcmOptions.withLink("https://my-server/page-to-open-on-click"))
                .build())
            .build();
       
        return message;
      }
    
    
    public void sendSaleCompletedMessage(String email) {
        if (!hasKey(email)) {
            return;
        }

        String token = getToken(email);
        Message message = Message.builder()
            .putData("title", "푸시메세지 테스트")
            .putData("content", "푸시메세지의 내용을 마음대로 설정 할 수 있습니다.")
            .setToken(token)
            .build();

        send(message);
    }

    public void sendPurchaseCompletedMessage(String email) {
        if (!hasKey(email)) {
            return;
        }

        String token = getToken(email);
        Message message = Message.builder()
            .putData("title", "구매 완료 알림")
            .putData("content", "등록하신 구매 입찰이 낙찰되었습니다.")
            .setToken(token)
            .build();
        
        
        send(message);
    }

    public void send(Message message) {
        FirebaseMessaging.getInstance().sendAsync(message);
    }

    public void saveToken(User loginRequest) {
        fcmTokenDAO.saveToken(loginRequest);
    }

    public void deleteToken(String email) {
//        fcmTokenDao.deleteToken(email);
    }

    private boolean hasKey(String email) {
        return fcmTokenDAO.hasKey(email);
    }

    private String getToken(String email) {
        return fcmTokenDAO.getToken(email);
    }
}