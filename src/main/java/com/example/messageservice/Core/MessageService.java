package com.example.messageservice.Core;

import com.example.messageservice.Entities.Message;
import com.example.messageservice.Repository.IMessageRepo;
import com.example.messageservice.View.ViewModels.AccountVm;
import com.example.messageservice.View.ViewModels.ChatVm;
import com.example.messageservice.View.ViewModels.MessageVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
@RequiredArgsConstructor
public class MessageService {

    private final IMessageRepo messageRepo;
    private final WebClientService webClientService;


    public MessageVm sendMessage(MessageVm messageVm, String authHeader){

        AccountVm receiver = getUserByEmail(messageVm.getReceiverEmail(),authHeader);
        AccountVm sender = getUserByEmail(messageVm.getSenderEmail(),authHeader);

        if(sender == null ||receiver == null){
            throw  new IllegalArgumentException("Sender or receiver does not exist");
        }

        Message message = ConverterUtil.convertFromMessageVmToMessage(messageVm);
        Message msg = messageRepo.save(message);
        MessageVm msgVm = ConverterUtil.convertFromMessageToMessageVm(msg);
        msgVm.setSenderFirstName(sender.getFirstName());
        msgVm.setSenderLastName(sender.getLastName());
        msgVm.setReceiverFirstName(receiver.getFirstName());
        msgVm.setReceiverLastName(receiver.getLastName());
        return msgVm;
    }


    public List<ChatVm> getChats(String email,String authHeader) {
        List<Message> messages = messageRepo.findBySenderEmailOrReceiverEmailOrderByDateDesc(email, email);

        Map<String, Message> latestMessages = new HashMap<>();
        Map<String, String> participantNames = new HashMap<>();
        List<ChatVm> chatVms = new ArrayList<>();

        for (Message message : messages) {
            String otherParticipantId = (message.getSenderEmail().equals(email)) ? message.getReceiverEmail() : message.getSenderEmail();
            AccountVm other = getUserByEmail(otherParticipantId,authHeader);
            if (!participantNames.containsKey(otherParticipantId)) {
                String otherParticipantName = other.getFirstName();
                otherParticipantName.concat(" "+other.getLastName());
                participantNames.put(otherParticipantId, otherParticipantName);
            }

            if (!latestMessages.containsKey(otherParticipantId) || message.getDate().after(latestMessages.get(otherParticipantId).getDate())) {
                latestMessages.put(otherParticipantId, message);
            }
        }

        for (Map.Entry<String, Message> entry : latestMessages.entrySet()) {
            String otherParticipantId = entry.getKey();
            Message latestMessage = entry.getValue();
            String otherParticipantName = participantNames.get(otherParticipantId);
            String lastMessage = latestMessage.getText();
            Date date = latestMessage.getDate();

            ChatVm chatVm = new ChatVm(email,otherParticipantId, otherParticipantName, lastMessage, date);
            chatVms.add(chatVm);
        }

        return chatVms;
    }

    public List<MessageVm> getChatByParticipantId(String myAccountId, String participantId, String authHeader) {
        List<Message> messages = messageRepo.findMessagesBetweenTwoPeople(myAccountId, participantId);
        List<MessageVm> messageVms = ConverterUtil.convertFromMessageToMessageVmList(messages);

        for (MessageVm messageVm : messageVms) {
            AccountVm sender = getUserByEmail(messageVm.getSenderEmail(),authHeader);
            AccountVm receiver = getUserByEmail(messageVm.getReceiverEmail(),authHeader);
            messageVm.setSenderFirstName(sender.getFirstName());
            messageVm.setSenderLastName(sender.getLastName());
            messageVm.setReceiverFirstName(receiver.getFirstName());
            messageVm.setReceiverLastName(receiver.getLastName());
        }

        return messageVms;
    }
    private AccountVm getUserByEmail(String email, String authHeader){
        AccountVm user = webClientService.fetchUserFromUserService("https://health-user-service.app.cloud.cbh.kth.se/user/" + email, authHeader).block();
        return user;
    }



}
