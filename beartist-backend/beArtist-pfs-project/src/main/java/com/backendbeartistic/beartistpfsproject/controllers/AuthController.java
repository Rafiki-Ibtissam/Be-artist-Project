package com.backendbeartistic.beartistpfsproject.controllers;


import com.backendbeartistic.beartistpfsproject.config.JwtProvider;
import com.backendbeartistic.beartistpfsproject.entities.User;
import com.backendbeartistic.beartistpfsproject.entities.Verification;
import com.backendbeartistic.beartistpfsproject.exception.UserException;
import com.backendbeartistic.beartistpfsproject.repositories.UserRepository;
import com.backendbeartistic.beartistpfsproject.response.AuthResponse;
import com.backendbeartistic.beartistpfsproject.services.CustomUserDetailsServiceImplementation;

import com.backendbeartistic.beartistpfsproject.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
   @Autowired
   private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private CustomUserDetailsServiceImplementation customUserDetails;

    @Autowired
    private EmailService emailService;


    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user)throws UserException {
        System.out.println("user "+user);
        String email=user.getEmail();
        String password=user.getPassword();
        String fullName=user.getFullName();
        String birthDay=user.getBirthDay();

        User isEmailExist=userRepository.findByEmail(email);
        if(isEmailExist!=null){
            throw new UserException("Email is already user with another account");
        }
        User createdUser =new User();
        createdUser.setEmail(email);
        createdUser.setFullName(fullName);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setBirthDay(birthDay);
        createdUser.setVerification(new Verification());

        User saveUser=userRepository.save(createdUser);

        // Send verification email after user registration
        String to = user.getEmail();
        String subject = "Welcome to Our Application";
        String body = "<h3>Dear <span style=\"color: blue;\">" + user.getFullName() + "</span></h3>,<br><br>"
                + "<h3>Thank you for registering with us. Your account has been successfully created.</h3>"
                + "<br><br><h5>Best regards,<br>The Platform Team : Ibtissam Meryem Mohammed</h5>";
        emailService.sendEmail(null, to, new String[0], subject, body); // Assuming no attachments



        Authentication authentication=new UsernamePasswordAuthenticationToken(email,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);



        String token =jwtProvider.generateToken(authentication);
        AuthResponse res=new AuthResponse(token,true);

        return new ResponseEntity<AuthResponse>(res, HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody User user){
        String username=user.getEmail();
        String password=user.getPassword();

        Authentication authentication=authenticate(username,password);
        String token =jwtProvider.generateToken(authentication);
        AuthResponse res=new AuthResponse(token,true);

        return new ResponseEntity<AuthResponse>(res, HttpStatus.ACCEPTED);
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails=customUserDetails.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException("Invalid username");
        }
        if(!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
    }
}

