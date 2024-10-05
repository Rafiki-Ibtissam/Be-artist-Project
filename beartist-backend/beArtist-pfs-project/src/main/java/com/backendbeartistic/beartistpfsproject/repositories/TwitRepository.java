package com.backendbeartistic.beartistpfsproject.repositories;

import com.backendbeartistic.beartistpfsproject.entities.Twit;
import com.backendbeartistic.beartistpfsproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TwitRepository extends JpaRepository<Twit, Long> {

    List<Twit> findAllByIsTwitTrueOrderByCreatedAtDesc();


    List<Twit> findByRetwitUserContainsOrUser_idAndIsTwitTrueOrderByCreatedAtDesc(User user, Long userId);

    List<Twit> findByLikesContainingOrderByCreatedAtDesc(User user);

    @Query("SELECT t FROM Twit t JOIN t.likes l WHERE l.user.id=:userId")
    List<Twit> findByLikesUser_id(@Param("userId") Long userId);

    List<Twit> findAllTwitByIsReplyIsFalse();
}
