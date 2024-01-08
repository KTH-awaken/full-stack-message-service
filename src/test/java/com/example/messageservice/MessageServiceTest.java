package com.example.messageservice;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.example.messageservice.Core.MessageService;
import com.example.messageservice.Core.WebClientService;
import com.example.messageservice.Entities.Message;
import com.example.messageservice.Repository.IMessageRepo;
import com.example.messageservice.View.ViewModels.AccountVm;
import com.example.messageservice.View.ViewModels.ChatVm;
import com.example.messageservice.View.ViewModels.MessageVm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

class MessageServiceTest {

    private IMessageRepo messageRepo;
    private WebClientService webClientService;
    private MessageService messageService;

    @BeforeEach
    void setUp() {
        messageRepo = mock(IMessageRepo.class);
        webClientService = mock(WebClientService.class);
        messageService = new MessageService(messageRepo, webClientService);
    }

    @Test
    void sendMessage_ShouldSendAndReturnMessage() {
        MessageVm messageVm = new MessageVm();
        AccountVm mockSender = new AccountVm();
        AccountVm mockReceiver = new AccountVm();
        Message mockMessage = new Message();

        when(webClientService.fetchUserFromUserService(anyString(), anyString()))
                .thenReturn(Mono.just(mockSender), Mono.just(mockReceiver));
        when(messageRepo.save(any(Message.class))).thenReturn(mockMessage);

        MessageVm result = messageService.sendMessage(messageVm, "authHeader");

        assertNotNull(result);
    }
    @Test
    void getChats_ShouldReturnListOfChats() {
        String email = "test@example.com";
        List<Message> mockMessages = new ArrayList<>();

        when(messageRepo.findBySenderEmailOrReceiverEmailOrderByDateDesc(email, email))
                .thenReturn(mockMessages);
        // Mock webClientService as needed

        List<ChatVm> result = messageService.getChats(email, "authHeader");

        assertNotNull(result);
        // Additional assertions as needed
    }

}
