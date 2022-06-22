package com.mvc.forrest.service.firebase;


import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.mvc.forrest.dao.firebase.FCMTokenDAO;
import com.mvc.forrest.service.domain.User;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FCMService implements MessageService {
	
	@Autowired
	private final FCMTokenDAO fcmTokenDAO;

    public void sendMessage(String email, String title, String body) throws InterruptedException, ExecutionException {
    	//누구에게, 제목, 메세지
        if (!hasKey(email)) {
            return;
        }
    	String token = getToken(email);
    	Notification notification = Notification.builder().setTitle(title).setBody(body).setImage("Image").build();
    	
        Message message = Message.builder()
            .setToken(token)
            .setNotification(notification)
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
//        fcmTokenDao.deleteToken(email);//로그아웃 시에 사용할 Method 알람을 받겠다면 로그아웃시 실행하지 않고 안받는다면 실행해줘야 한다
    }

    private boolean hasKey(String email) {
        return fcmTokenDAO.hasKey(email);
    }

    private String getToken(String email) {
        return fcmTokenDAO.getToken(email);
    }


	@Override
	public void sendSaleCompletedMessage(String token) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendPurchaseCompletedMessage(String token) {
		// TODO Auto-generated method stub
		
	}
}