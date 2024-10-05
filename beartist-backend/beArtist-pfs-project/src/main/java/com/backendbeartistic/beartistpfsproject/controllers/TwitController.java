package com.backendbeartistic.beartistpfsproject.controllers;


import com.backendbeartistic.beartistpfsproject.dto.TwitDto;
import com.backendbeartistic.beartistpfsproject.dto.mapper.TwitDtoMapper;
import com.backendbeartistic.beartistpfsproject.entities.Twit;
import com.backendbeartistic.beartistpfsproject.entities.User;
import com.backendbeartistic.beartistpfsproject.exception.TwitException;
import com.backendbeartistic.beartistpfsproject.exception.UserException;
import com.backendbeartistic.beartistpfsproject.request.TwitReplyRequest;
import com.backendbeartistic.beartistpfsproject.response.ApiResponse;
import com.backendbeartistic.beartistpfsproject.services.TwitService;
import com.backendbeartistic.beartistpfsproject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/twits")
public class TwitController {
    @Autowired
    private TwitService twitService;
    @Autowired
    private UserService userService;


    @PostMapping("/create")
    public ResponseEntity<TwitDto> createTwit(@RequestBody Twit req,
      @RequestHeader("Authorization") String jwt)throws UserException, TwitException {
        User user=userService.findUserProfileByJwt(jwt);
        Twit twit=twitService.createTwit(req,user);
        TwitDto twitDto= TwitDtoMapper.toTwitDto(twit,user);

        return new ResponseEntity<>(twitDto, HttpStatus.CREATED);
    }

    @PostMapping("/reply")
    public ResponseEntity<TwitDto> relpyTwit(@RequestBody TwitReplyRequest req,
                                             @RequestHeader("Authorization") String jwt)throws UserException, TwitException {
        User user=userService.findUserProfileByJwt(jwt);
        Twit twit=twitService.createReply(req,user);
        TwitDto twitDto= TwitDtoMapper.toTwitDto(twit,user);

        return new ResponseEntity<>(twitDto, HttpStatus.CREATED);
    }


    @PutMapping("/{twitId}/retwit")
    public ResponseEntity<TwitDto> retwit(@PathVariable Long twitId,
                                             @RequestHeader("Authorization") String jwt)throws UserException, TwitException {
        User user=userService.findUserProfileByJwt(jwt);
        Twit twit=twitService.retwit(twitId,user);
        TwitDto twitDto= TwitDtoMapper.toTwitDto(twit,user);

        return new ResponseEntity<>(twitDto, HttpStatus.OK);
    }

    @GetMapping("/{twitId}")
    public ResponseEntity<TwitDto> findTwitById(@PathVariable Long twitId,
                                          @RequestHeader("Authorization") String jwt)throws UserException, TwitException {
        User user=userService.findUserProfileByJwt(jwt);
        Twit twit=twitService.findById(twitId);
        TwitDto twitDto= TwitDtoMapper.toTwitDto(twit,user);

        return new ResponseEntity<>(twitDto, HttpStatus.OK);
    }

    @DeleteMapping ("/{twitId}")
    public ResponseEntity<ApiResponse> deleteTwit(@PathVariable Long twitId,
                                          @RequestHeader("Authorization") String jwt)throws UserException, TwitException {
        User user=userService.findUserProfileByJwt(jwt);
        twitService.deleteTwitById(twitId,user.getId());

        ApiResponse res=new ApiResponse("Twit deleted succussfuly",true);

        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<TwitDto>> getAllTwits(@RequestHeader("Authorization") String jwt)throws UserException, TwitException {

        User user=userService.findUserProfileByJwt(jwt);
        List<Twit> twits=twitService.findAllTwit();

        List<TwitDto> twitDtos= TwitDtoMapper.toTwitDtos(twits,user);
        System.out.println("bonjour" + twitDtos + jwt);
        return new ResponseEntity<>(twitDtos, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TwitDto>> getUsersAllTwits(@PathVariable Long userId,@RequestHeader("Authorization") String jwt)throws UserException, TwitException {

        User user=userService.findUserProfileByJwt(jwt);
        List<Twit> twits=twitService.getUserTwit(user);

        List<TwitDto> twitDtos= TwitDtoMapper.toTwitDtos(twits,user);

        return new ResponseEntity<>(twitDtos, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/likes")
    public ResponseEntity<List<TwitDto>> findTwitByLikesContainsUser(@PathVariable Long userId,@RequestHeader("Authorization") String jwt)throws UserException, TwitException {

        User user=userService.findUserProfileByJwt(jwt);
        List<Twit> twits=twitService.findByLikesContainsUser(user);

        List<TwitDto> twitDtos= TwitDtoMapper.toTwitDtos(twits,user);

        return new ResponseEntity<>(twitDtos, HttpStatus.OK);
    }

}
