package com.controller;

import com.model.Notification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsumerController {

    private RabbitTemplate rabbitTemplate;

    public ConsumerController(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/message")
    public String receiveMessage(){
        final Object message = rabbitTemplate.receiveAndConvert("test");

        if (message != null) {

            return "Udalo się pobrać wiadomość :) " + message;
        }
        return "Brak wiadomosci w kolejce";
    }

    @GetMapping("/note")
    public ResponseEntity<Notification> receiveNotification(){
        final Notification notification = rabbitTemplate.receiveAndConvert("test", ParameterizedTypeReference.forType(Notification.class));
        if (notification != null) {
            return ResponseEntity.ok(notification);
        }
        return ResponseEntity.noContent().build();
    }

    @RabbitListener(queues = "test")
    public void listenerNotification(Notification notification){
        System.out.println(notification.getEmail() + " " + notification.getTitle() + " " + notification.getBody());
    }
}
