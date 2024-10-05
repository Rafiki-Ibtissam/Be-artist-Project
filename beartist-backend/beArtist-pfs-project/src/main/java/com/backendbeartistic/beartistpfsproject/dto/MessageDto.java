package com.backendbeartistic.beartistpfsproject.dto;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDto {
    private Long id;
    private Long senderId;
    private Long receiverId;
    private String content;
    private LocalDateTime timestamp;
}