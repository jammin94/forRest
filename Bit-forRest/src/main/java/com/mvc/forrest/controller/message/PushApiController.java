package com.mvc.forrest.controller.message;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.firebase.messaging.FirebaseMessaging;
import com.mvc.forrest.service.firebase.PushNotificationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class PushApiController {

    /**
     * 푸시 알람 요청
     * @param title
     * @param body
     * @throws IOException
     */
    @PostMapping("/fcm")
    public ResponseEntity<?> reqFcm(@RequestParam(required = true) String title,@RequestParam(required = true) String body) {

        log.info("** title : {}",title);
        log.info("** body : {}",body);
        
        

        try {

            PushNotificationService.sendCommonMessage(title, body);


        } catch(Exception e) {
            log.error(e.getMessage());

            return new ResponseEntity<>("failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok("Success");
    }

}
