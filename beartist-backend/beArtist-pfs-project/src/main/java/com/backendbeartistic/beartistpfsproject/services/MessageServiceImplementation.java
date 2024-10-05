package com.backendbeartistic.beartistpfsproject.services;


import com.backendbeartistic.beartistpfsproject.entities.Message;
import com.backendbeartistic.beartistpfsproject.entities.User;
import com.backendbeartistic.beartistpfsproject.exception.UserException;
import com.backendbeartistic.beartistpfsproject.repositories.MessageRepository;
import com.backendbeartistic.beartistpfsproject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImplementation implements MessageService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public Message sendMessage(Long senderId, Long receiverId, String content) throws UserException {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new UserException("Sender not found"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new UserException("Receiver not found"));

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(content);

        return messageRepository.save(message);
    }

    @Override
    public List<Message> getMessages(Long userId) throws UserException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found"));
        return messageRepository.findByReceiver(user);
    }
}