package com.backendbeartistic.beartistpfsproject.dto;

import lombok.Data;

@Data
public class LikeDto {
  private Long id;
  private UserDto user;
  private TwitDto twit;

}
