package com.c1se62.clinic_booking.controller;

import com.c1se62.clinic_booking.entity.User;
import com.c1se62.clinic_booking.service.AuthenticationServices.AuthenticationServices;
import com.c1se62.clinic_booking.service.UserServices.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserServices userServices;

    @Autowired
    private AuthenticationServices authenticationService;

    @GetMapping("/listSearchName")
    public ResponseEntity<List<User>> findAll(@RequestParam String name){
        List<User> userList = userServices.findUserByNameContaining(name);
        if(userList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
    @GetMapping("/list")
    public ResponseEntity<List<User>> findAll(){
        List<User> userList = userServices.findAll();
        if(userList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(userList,HttpStatus.OK);
    }
    @PostMapping("/create")
    public ResponseEntity<User> create(@RequestBody User user){
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        User user1 = new User();
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setEmail(user.getEmail());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setRole(user.getRole());
        userServices.save(user1);
        return new ResponseEntity<>(user1,HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<User> detail(@PathVariable String name){
        User user = userServices.findByUsername(name);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @PutMapping("/{name}")
    public ResponseEntity<User> update(@PathVariable String name, @RequestBody User user){
        User user1 = userServices.findByUsername(name);
        if(user1 == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user1.setUsername(user.getUsername());
        user1.setPassword(user.getPassword());
        user1.setFirstName(user.getFirstName());
        user1.setLastName(user.getLastName());
        user1.setEmail(user.getEmail());
        user1.setPhoneNumber(user.getPhoneNumber());
        user1.setRole(user.getRole());
        userServices.save(user1);
        return new ResponseEntity<>(user1,HttpStatus.OK);
    }
    @DeleteMapping("/{name}")
    public ResponseEntity<User> delete(@PathVariable String name){
        User user1 = userServices.findByUsername(name);
        if(user1 == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userServices.delete(name);
        return new ResponseEntity<>(user1,HttpStatus.NO_CONTENT);
    }
}
