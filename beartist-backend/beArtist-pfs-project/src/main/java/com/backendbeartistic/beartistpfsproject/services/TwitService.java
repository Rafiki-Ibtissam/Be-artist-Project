package com.backendbeartistic.beartistpfsproject.services;

import com.backendbeartistic.beartistpfsproject.entities.Twit;
import com.backendbeartistic.beartistpfsproject.entities.User;
import com.backendbeartistic.beartistpfsproject.exception.UserException;
import com.backendbeartistic.beartistpfsproject.exception.TwitException;
import com.backendbeartistic.beartistpfsproject.request.TwitReplyRequest;

import java.util.List;


public interface TwitService {
    public Twit createTwit(Twit req, User user)throws UserException;
    public List<Twit> findAllTwit();
    public Twit retwit(Long twitId,User user)throws UserException, TwitException;
    public Twit findById(Long twitId)throws TwitException;
    public void deleteTwitById(Long twitId,Long userId)throws TwitException,UserException;
    public Twit removeFromRetwit(Long twitId,User user)throws UserException,TwitException;
    public Twit createReply(TwitReplyRequest req, User user)throws TwitException;
    public List<Twit> getUserTwit(User user);
    public List<Twit> findByLikesContainsUser(User user);

    public List<Twit> findAllTwitSansAuth();


}
