package com.backendbeartistic.beartistpfsproject.request;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class TwitReplyRequest {
    private String content;
    private Long twitId;
    private LocalDateTime createAt;
    private String image;
}
