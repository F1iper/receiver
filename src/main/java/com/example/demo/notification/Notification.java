package com.example.demo.notification;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class Notification implements Serializable {

    private String email;

    private String title;

    private String body;
}
