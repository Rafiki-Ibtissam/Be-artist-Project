package com.backendbeartistic.beartistpfsproject.controllers;


import com.backendbeartistic.beartistpfsproject.dto.LikeDto;
import com.backendbeartistic.beartistpfsproject.dto.mapper.LikeDtoMapper;
import com.backendbeartistic.beartistpfsproject.entities.Like;
import com.backendbeartistic.beartistpfsproject.entities.User;
import com.backendbeartistic.beartistpfsproject.exception.TwitException;
import com.backendbeartistic.beartistpfsproject.exception.UserException;
import com.backendbeartistic.beartistpfsproject.services.LikeService;
import com.backendbeartistic.beartistpfsproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.ClientInfoStatus;
import java.util.List;

@RestController
@RequestMapping("/api")
public class LikeContoller {
    @Autowired
    private UserService userService;
    @Autowired
    private LikeService likeService;


    @PostMapping("/{twitId}")
    public ResponseEntity<LikeDto> likeTwit(@PathVariable Long twitId,
    @RequestHeader("Authorization")String jwt)throws UserException, TwitException {

        User user=userService.findUserProfileByJwt(jwt);
        Like like=likeService.likeTwit(twitId,user);

        LikeDto likeDto= LikeDtoMapper.toLikeDto(like,user);
        return new ResponseEntity<LikeDto>(likeDto, HttpStatus.CREATED);


    }

    @PostMapping("/twit/{twitId}")
    public ResponseEntity<List<LikeDto>> getAllLikes(@PathVariable Long twitId,
                                            @RequestHeader("Authorization")String jwt)throws UserException, TwitException {

        User user=userService.findUserProfileByJwt(jwt);
        List<Like> like=likeService.getAllLikes(twitId);

        List<LikeDto> likeDtos= LikeDtoMapper.toLikeDtos(like,user);
        return new ResponseEntity<>(likeDtos, HttpStatus.CREATED);


    }
}
