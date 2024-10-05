package com.backendbeartistic.beartistpfsproject.controllers;



import com.backendbeartistic.beartistpfsproject.dto.MessageDto;
import com.backendbeartistic.beartistpfsproject.entities.Message;
import com.backendbeartistic.beartistpfsproject.entities.User;
import com.backendbeartistic.beartistpfsproject.exception.UserException;
import com.backendbeartistic.beartistpfsproject.services.MessageService;
import com.backendbeartistic.beartistpfsproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @Autowired
    private UserService userService;

    @PostMapping("/send")
    public ResponseEntity<MessageDto> sendMessage(@RequestHeader("Authorization") String jwt,
                                                  @RequestParam Long receiverId,
                                                  @RequestBody String content) throws UserException {
        User sender = userService.findUserProfileByJwt(jwt);
        Message message = messageService.sendMessage(sender.getId(), receiverId, content);
        return new ResponseEntity<>(toMessageDto(message), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MessageDto>> getMessages(@RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);
        List<Message> messages = messageService.getMessages(user.getId());
        List<MessageDto> messageDtos = messages.stream().map(this::toMessageDto).collect(Collectors.toList());
        return new ResponseEntity<>(messageDtos, HttpStatus.OK);
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