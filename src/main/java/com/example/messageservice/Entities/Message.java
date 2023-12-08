package com.example.messageservice.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "message")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String senderEmail;
    private String receiverEmail;
    private String text;
    private Date date = Date.from(ZonedDateTime.now(ZoneId.of("Europe/Stockholm")).toInstant());;


    public Message(String text, String senderEmail, String receiverEmail) {
        this.text = text;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", senderEmail='" + senderEmail + '\'' +
                ", receiverEmail='" + receiverEmail + '\'' +
                ", text='" + text + '\'' +
                ", date=" + date +
                '}';
    }
}
