package com.example.demo;

import com.example.demo.notification.Notification;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
        final Object notification = rabbitTemplate.receiveAndConvert("test");
        if (notification instanceof Notification) {
            return ResponseEntity.ok((Notification) notification);
        }
        return ResponseEntity.noContent().build();
    }

    @RabbitListener(queues = "test")
    public void listenerNotification(Notification notification){
        System.out.println(notification.getEmail() + " " + notification.getTitle() + " " + notification.getBody());
    }
}
