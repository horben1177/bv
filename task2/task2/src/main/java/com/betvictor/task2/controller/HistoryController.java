package com.betvictor.task2.controller;

import com.betvictor.task2.entity.Message;
import com.betvictor.task2.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/betvictor")
public class HistoryController {

    private MessageRepository messageRepository;

    @Autowired
    public void setMessageRepository(MessageRepository messageRepository){
        this.messageRepository = messageRepository;
    }

    @GetMapping(value="/history", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Message> show10LastMessage(){
        return messageRepository.findLast10Messages();
    }

}
