package com.backendbeartistic.beartistpfsproject.controllers;



import com.backendbeartistic.beartistpfsproject.dto.MessageDto;
import com.backendbeartistic.beartistpfsproject.entities.Message;
import com.backendbeartistic.beartistpfsproject.entities.User;
import com.backendbeartistic.beartistpfsproject.exception.UserException;
import com.backendbeartistic.beartistpfsproject.services.MessageService;
import com.backendbeartistic.beartistpfsproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;


@Controller
public class WebSocketMessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public MessageDto sendMessage(MessageDto messageDto) throws UserException {
        User sender = userService.findUserById(messageDto.getSenderId());
        User receiver = userService.findUserById(messageDto.getReceiverId());
        Message message = messageService.sendMessage(sender.getId(), receiver.getId(), messageDto.getContent());
        return toMessageDto(message);
    }

    private MessageDto toMessageDto(Message message) {
        MessageDto dto = new MessageDto();
        dto.setId(message.getId());
        dto.setSenderId(message.getSender().getId());
        dto.setReceiverId(message.getReceiver().getId());
        dto.setContent(message.getContent());
        dto.setTimestamp(message.getTimestamp());
        return dto;
    }
}