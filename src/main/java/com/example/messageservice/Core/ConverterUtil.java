package com.example.messageservice.Core;



import com.example.messageservice.Entities.Message;
import com.example.messageservice.View.ViewModels.MessageVm;

import java.util.ArrayList;
import java.util.List;

public class ConverterUtil {

    public static Message convertFromMessageVmToMessage(MessageVm messageVm){
        return new Message(messageVm.getText(),messageVm.getSenderEmail(),messageVm.getReceiverEmail());
    }
    public static MessageVm convertFromMessageToMessageVm(Message message){
        return  new MessageVm(message.getId(), message.getText(), message.getDate(),message.getSenderEmail(), message.getReceiverEmail());
    }
    public static List<MessageVm> convertFromMessageToMessageVmList(List<Message> messages){
        List<MessageVm> list = new ArrayList<>();
        for (Message m:messages) {
            list.add(convertFromMessageToMessageVm(m));
        }
        return list;
    }
}
