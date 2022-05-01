package com.lats.service;


import com.lats.repository.MessageRepository;
import com.lats.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;


    //create getMessages method
    public Map<Integer, Map<String, Object>> getMessages(Integer[] messageCodeList) {

        //calling and returning getMessages method from hostBankProfileRepo
        return messageRepository.getMessages(messageCodeList);
    }
}
