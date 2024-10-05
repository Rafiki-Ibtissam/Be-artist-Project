package com.backendbeartistic.beartistpfsproject.util;

import com.backendbeartistic.beartistpfsproject.entities.Like;
import com.backendbeartistic.beartistpfsproject.entities.Twit;
import com.backendbeartistic.beartistpfsproject.entities.User;

public class TwitUtil{

 public final static boolean isLikedByReqUser(User reqUser, Twit twit){
     for(Like like:twit.getLikes()){
         if(like.getUser().getId().equals(reqUser.getId())){
             return true;
         }
     }
     return false;

 }

 public final static boolean isRetwitedByReqUser(User reqUser,Twit twit){
     for(User user: twit.getRetwitUser()){
       if(user.getId().equals(reqUser.getId())){
           return true;
       }
     }

     return false;
 }
}
