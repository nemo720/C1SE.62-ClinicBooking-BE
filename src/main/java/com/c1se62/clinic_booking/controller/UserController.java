package com.c1se62.clinic_booking.controller;

import com.c1se62.clinic_booking.entity.User;
import com.c1se62.clinic_booking.service.AuthenticationServices.AuthenticationServices;
import com.c1se62.clinic_booking.service.UserServices.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
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

//    @GetMapping("/listSearchName")
////    public ResponseEntity<List<User>> findAll(@RequestParam String name){
////
////    }
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
        User foundUser = new User();
        foundUser.setUsername(user.getUsername());
        foundUser.setPassword(user.getPassword());
        foundUser.setFirstName(user.getFirstName());
        foundUser.setLastName(user.getLastName());
        foundUser.setEmail(user.getEmail());
        foundUser.setPhoneNumber(user.getPhoneNumber());
        foundUser.setRole(user.getRole());
        userServices.save(foundUser);
        return new ResponseEntity<>(foundUser,HttpStatus.OK);
    }

    @GetMapping("/{name}")
    public ResponseEntity<User> getUserByName(@PathVariable String name){
        User user = userServices.findByUsername(name);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
    @PutMapping("/{name}")
    public ResponseEntity<User> update(@PathVariable String name, @RequestBody User user){
        User foundUser = userServices.findByUsername(name);
        if(foundUser == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        foundUser.setFirstName(user.getFirstName());
        foundUser.setLastName(user.getLastName());
        foundUser.setEmail(user.getEmail());
        foundUser.setPhoneNumber(user.getPhoneNumber());
        userServices.save(foundUser);
        return new ResponseEntity<>(foundUser,HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePerson(@RequestParam("personId") Long personId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(userServices.deleteUser(personId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
