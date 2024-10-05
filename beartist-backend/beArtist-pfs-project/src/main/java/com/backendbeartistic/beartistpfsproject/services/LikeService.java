package com.backendbeartistic.beartistpfsproject.services;

import com.backendbeartistic.beartistpfsproject.entities.Like;
import com.backendbeartistic.beartistpfsproject.entities.User;
import com.backendbeartistic.beartistpfsproject.exception.TwitException;
import com.backendbeartistic.beartistpfsproject.exception.UserException;

import java.util.List;

public interface LikeService {

    public Like likeTwit(Long twitId, User user)throws UserException, TwitException;
    public List<Like> getAllLikes(Long twitId)throws TwitException;
}
