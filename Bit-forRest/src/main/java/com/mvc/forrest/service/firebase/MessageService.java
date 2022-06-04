package com.mvc.forrest.service.firebase;

public interface MessageService {

    void sendSaleCompletedMessage(String token);

    void sendPurchaseCompletedMessage(String token);
}
