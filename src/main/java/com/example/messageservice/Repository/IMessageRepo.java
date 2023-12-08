package com.example.messageservice.Repository;

import com.example.messageservice.Entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface IMessageRepo extends JpaRepository<Message,Integer> {
    List<Message> findBySenderEmailOrReceiverEmailOrderByDateDesc(String sender, String receiver);

    @Query("SELECT m FROM Message m WHERE (m.senderEmail = :myAccountId AND m.receiverEmail = :participantId) OR (m.senderEmail = :participantId AND m.receiverEmail = :myAccountId) ORDER BY m.date ASC")
    List<Message> findMessagesBetweenTwoPeople(String myAccountId, String participantId);


}
