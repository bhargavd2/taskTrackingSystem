package org.airTribe.taskTrackingSystem.controller;


import jakarta.validation.Valid;
import org.airTribe.taskTrackingSystem.dto.UpdateUserDto;
import org.airTribe.taskTrackingSystem.entity.User;
import org.airTribe.taskTrackingSystem.exception.UserNotFoundException;
import org.airTribe.taskTrackingSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService _userService;


    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable long id)
    {
        Map<String, Object> response = new HashMap<>();
        try {
            User user = _userService.getUserById(id);
            String authEmail = _userService.getAutheticateUser();

            if (authEmail.equals(user.getEmail())) {
                response.put("Status", "200");
                response.put("userId", user.getUserId().toString());
                response.put("firstName", user.getFirstName());
                response.put("lastName", user.getLastName());
                response.put("email", user.getEmail());
                return ResponseEntity.ok(response);
            } else {
                response.put("Status", "206");
                response.put("firstName", user.getFirstName());
                response.put("email", user.getEmail());
                return new ResponseEntity<>(response, HttpStatus.PARTIAL_CONTENT);
            }
        }catch (UserNotFoundException e)
        {
            response.put("Status",400);
            response.put("message","UserNotFoundException");
            return ResponseEntity.badRequest().body(response);
        }catch (Exception e)
        {
            response.put("Status",500);
            response.put("message","error will getting User");
            return ResponseEntity.internalServerError().body(response);
        }

    }

    @GetMapping("/findIdByEmail/{email}")
    public ResponseEntity<Object> findIdByEmail(@PathVariable String email)
    {
        Map<String, Object> response = new HashMap<>();
        try {
            User user = _userService.getUserByEmail(email);
            String authEmail = _userService.getAutheticateUser();

            if (authEmail.equals(user.getEmail())) {
                response.put("Status", 200);
                response.put("userId", user.getUserId().toString());
                response.put("firstName", user.getFirstName());
                response.put("lastName", user.getLastName());
                response.put("email", user.getEmail());
                return ResponseEntity.ok(response);
            } else {
                response.put("Status", 206);
                response.put("firstName", user.getFirstName());
                response.put("email", user.getEmail());
                return new ResponseEntity<>(response, HttpStatus.PARTIAL_CONTENT);
            }
        }catch (UserNotFoundException e)
        {
            response.put("Status",400);
            response.put("message","UserNotFoundException");
            return ResponseEntity.badRequest().body(response);
        }catch (Exception e)
        {
            response.put("Status",500);
            response.put("message","error will getting User");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable long id,@RequestBody @Valid UpdateUserDto updateUserDto)
    {

        Map<String, Object> response = new HashMap<>();
        try{
            User user = _userService.getUserById(id);
            String authEmail = _userService.getAutheticateUser();

            if(authEmail.equals(user.getEmail())) {
                user.setFirstName(updateUserDto.getFirstName());
                user.setLastName(updateUserDto.getLastName());
                _userService.saveUser(user);
                response.put("Status",200);
                response.put("userId", user.getUserId().toString());
                response.put("firstName", user.getFirstName());
                response.put("lastName", user.getLastName());
                response.put("email", user.getEmail());
                return ResponseEntity.ok(response);
            }
            else {
                response.put("Status",400);
                response.put("message","not allowed to update other user!!!");
                return ResponseEntity.badRequest().body(response);
            }
        }catch (UserNotFoundException e)
        {
            response.put("Status",400);
            response.put("message","UserNotFoundException");
            return ResponseEntity.badRequest().body(response);
        }
        catch (Exception e)
        {
            response.put("Status",500);
            response.put("message","error will updating record");
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable long id) {

        Map<String, Object> response = new HashMap<>();
        try {
            User user = _userService.getUserById(id);
            String authEmail = _userService.getAutheticateUser();

            if (authEmail.equals(user.getEmail())) {

                _userService.deleteUser(id);
                response.put("Status", 200);
                response.put("message", "Successfully Deleted the User Record");
                return ResponseEntity.ok(response);
            } else {
                response.put("Status", 400);
                response.put("message", "not allowed to delete other user!!!!");
                return ResponseEntity.badRequest().body(response);
            }
        }catch (UserNotFoundException e)
        {
            response.put("Status",400);
            response.put("message","UserNotFoundException");
            return ResponseEntity.badRequest().body(response);
        }
        catch (Exception e)
        {
            response.put("Status",500);
            response.put("message","error will deleting record");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
