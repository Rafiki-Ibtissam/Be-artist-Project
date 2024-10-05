package com.backendbeartistic.beartistpfsproject.services;

import com.backendbeartistic.beartistpfsproject.entities.User;
import com.backendbeartistic.beartistpfsproject.exception.UserException;

import java.util.List;

public interface UserService {
public User findUserById(Long userId)throws UserException;

public User findUserProfileByJwt(String jwt)throws UserException;

public User updateUser(Long userId,User user)throws UserException;

public User followUser(Long userId,User user)throws UserException;

public List<User> searchUser(String query);
    public List<User> getAllUsers() ;


}
