package com.example.messageservice.View.ViewModels;

import lombok.*;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MessageVm {

    int id;
    private String text;
    private Date date;
    private String senderEmail;
    private String receiverEmail;
    private String senderFirstName;
    private String senderLastName;
    private String receiverFirstName;
    private String receiverLastName;

    public MessageVm(int id, String text, Date date, String senderEmail, String receiverEmail) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;

    }


    @Override
    public String toString() {
        return "MessageVm{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", date=" + date +
                ", senderEmail='" + senderEmail + '\'' +
                ", receiverEmail='" + receiverEmail + '\'' +
                ", senderFirstName='" + senderFirstName + '\'' +
                ", senderLastName='" + senderLastName + '\'' +
                ", receiverFirstName='" + receiverFirstName + '\'' +
                ", receiverLastName='" + receiverLastName + '\'' +
                '}';
    }
}
