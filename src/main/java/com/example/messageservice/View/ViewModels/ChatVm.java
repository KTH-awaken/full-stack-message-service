package com.example.messageservice.View.ViewModels;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatVm {
    String id;
    String otherParticipantId;
    String otherParticipantName;
    String lastMessage;
    private Date date;



}
