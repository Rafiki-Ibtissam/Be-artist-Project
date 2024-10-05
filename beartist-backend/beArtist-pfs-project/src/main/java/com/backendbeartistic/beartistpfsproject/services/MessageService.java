package com.backendbeartistic.beartistpfsproject.services;


import com.backendbeartistic.beartistpfsproject.entities.Message;
import com.backendbeartistic.beartistpfsproject.exception.UserException;

import java.util.List;

public interface MessageService {
    Message sendMessage(Long senderId, Long receiverId, String content) throws UserException;
    List<Message> getMessages(Long userId) throws UserException;
}
