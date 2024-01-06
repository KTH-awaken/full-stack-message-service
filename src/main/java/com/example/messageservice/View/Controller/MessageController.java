package com.example.messageservice.View.Controller;
import com.example.messageservice.Core.MessageService;
import com.example.messageservice.Core.Security.TokenDecoder;
import com.example.messageservice.View.ViewModels.ChatVm;
import com.example.messageservice.View.ViewModels.MessageVm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@RequestMapping
public class MessageController {

    private final MessageService messageService;


    @PostMapping("/message")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<MessageVm> sendMessage(@RequestHeader("Authorization") String authHeader,@RequestBody MessageVm messageVm){
        System.out.println(messageVm);
        return ResponseEntity.ok(messageService.sendMessage(messageVm,authHeader));
    }



    @GetMapping("/message/chats")
    public ResponseEntity<List<ChatVm>> getChats(@RequestHeader("Authorization") String authHeader){
        String email = TokenDecoder.getEmailFromToken(authHeader);
        return ResponseEntity.ok(messageService.getChats(email, authHeader));
    }

    @GetMapping("/message/chat/{participantId}")
    public ResponseEntity<List<MessageVm>> getChatByParticipantId(@PathVariable String participantId, @RequestHeader("Authorization") String authHeader){
        String email = TokenDecoder.getEmailFromToken(authHeader);
        return ResponseEntity.ok(messageService.getChatByParticipantId(email,participantId,authHeader));
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck(){
        return ResponseEntity.ok("ok");
    }


}
