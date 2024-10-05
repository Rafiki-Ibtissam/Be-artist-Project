package com.backendbeartistic.beartistpfsproject.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Twit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private User user;

    private String content;
    private String image;
    private String video;

    @OneToMany(mappedBy = "twit",cascade = CascadeType.ALL)
    private List<Like> likes=new ArrayList<>();
    @OneToMany
    private List<Twit> replyTwits=new ArrayList<>();

    @ManyToMany
    private List<User> retwitUser=new ArrayList<>();

    @ManyToOne
    private Twit replayFor;

    private boolean isReply;
    private boolean isTwit;

    private LocalDateTime createdAt;


}
