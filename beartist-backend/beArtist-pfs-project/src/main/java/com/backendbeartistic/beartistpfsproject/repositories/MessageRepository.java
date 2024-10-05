package com.backendbeartistic.beartistpfsproject.repositories;

import com.backendbeartistic.beartistpfsproject.entities.Message;
import com.backendbeartistic.beartistpfsproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByReceiver(User receiver);
}