package com.backendbeartistic.beartistpfsproject.services;

import com.backendbeartistic.beartistpfsproject.entities.Like;
import com.backendbeartistic.beartistpfsproject.entities.Twit;
import com.backendbeartistic.beartistpfsproject.entities.User;
import com.backendbeartistic.beartistpfsproject.exception.TwitException;
import com.backendbeartistic.beartistpfsproject.exception.UserException;
import com.backendbeartistic.beartistpfsproject.repositories.LikeRepository;
import com.backendbeartistic.beartistpfsproject.repositories.TwitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LikeServiceImplementation implements LikeService{

    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private TwitService twitService;
    @Autowired
    private TwitRepository twitRepository;



    @Override
    public Like likeTwit(Long twitId, User user) throws UserException, TwitException {
        Like isLikeExist=likeRepository.isLikeExist(user.getId(),twitId);
        if(isLikeExist!=null){
            likeRepository.deleteById(isLikeExist.getId());
            return isLikeExist;
        }
        Twit twit=twitService.findById(twitId);

        Like like=new Like();
        like.setTwit(twit);
        like.setUser(user);
        Like saveLike=likeRepository.save(like);
        twit.getLikes().add(saveLike);
        twitRepository.save(twit);
        return saveLike;
    }

    @Override
    public List<Like> getAllLikes(Long twitId) throws TwitException {
        Twit twit=twitService.findById(twitId);
        List<Like> likes=likeRepository.findByTwitId(twitId);

        return likes;
    }
}
