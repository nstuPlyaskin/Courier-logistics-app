package com.example.courier.message;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Message {
    public String username;
    public String textMessage;
    private String messageTime;


    public Message(){}

    public Message(String username, String textMessage){
        this.username = username;
        this.textMessage = textMessage;

        this.messageTime = new SimpleDateFormat("HH:mm").format(Calendar.getInstance().getTime());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public String getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
}
