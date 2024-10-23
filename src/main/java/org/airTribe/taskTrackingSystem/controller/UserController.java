package org.airTribe.taskTrackingSystem.controller;


import jakarta.validation.Valid;
import org.airTribe.taskTrackingSystem.dto.UpdateUserDto;
import org.airTribe.taskTrackingSystem.entity.User;
import org.airTribe.taskTrackingSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService _userService;


    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable long id)
    {
        User user = _userService.getUserById(id);
        String authEmail = _userService.getAutheticateUser();
        Map<String, String> response = new HashMap<>();
        if(authEmail.equals(user.getEmail())) {
            response.put("Status","200");
            response.put("userId", user.getUserId().toString());
            response.put("firstName", user.getFirstName());
            response.put("lastName", user.getLastName());
            response.put("email", user.getEmail());
        }
        else {
            response.put("Status","206");
            response.put("firstName", user.getFirstName());
            response.put("email", user.getEmail());
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/findIdByEmail/{email}")
    public ResponseEntity<Object> findIdByEmail(@PathVariable String email)
    {
        User user = _userService.getIdByEmail(email);
        String authEmail = _userService.getAutheticateUser();
        Map<String, String> response = new HashMap<>();
        if(authEmail.equals(user.getEmail())) {
            response.put("Status","200");
            response.put("userId", user.getUserId().toString());
            response.put("firstName", user.getFirstName());
            response.put("lastName", user.getLastName());
            response.put("email", user.getEmail());
        }
        else {
            response.put("Status","206");
            response.put("firstName", user.getFirstName());
            response.put("email", user.getEmail());
        }
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable long id,@RequestBody @Valid UpdateUserDto updateUserDto)
    {
        User user = _userService.getUserById(id);
        String authEmail = _userService.getAutheticateUser();
        Map<String, String> response = new HashMap<>();
        if(authEmail.equals(user.getEmail())) {
            try {
                user.setFirstName(updateUserDto.getFirstName());
                user.setLastName(updateUserDto.getLastName());
                _userService.saveUser(user);
                response.put("Status","200");
                response.put("userId", user.getUserId().toString());
                response.put("firstName", user.getFirstName());
                response.put("lastName", user.getLastName());
                response.put("email", user.getEmail());
            }catch (Exception e)
            {
                response.put("Status","400");
                response.put("message","error will updating record");
                return ResponseEntity.internalServerError().body(response);
            }
        }
        else {
            response.put("Status","400");
            response.put("message","not allowed to update other user!!!");
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable long id) {

        User user = _userService.getUserById(id);
        String authEmail = _userService.getAutheticateUser();
        Map<String, String> response = new HashMap<>();
        if(authEmail.equals(user.getEmail())) {
            try{
                _userService.deleteUser(id);
                response.put("Status","200");
                response.put("message","Sucessfuly Deleted the User Record");
            }catch (Exception e)
            {
                response.put("Status","400");
                response.put("message","error will deleting record");
                return ResponseEntity.internalServerError().body(response);
            }
        }
        else {
            response.put("Status","400");
            response.put("message","not allowed to delete other user!!!!");
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }
}
