package org.airTribe.taskTrackingSystem.controller;


import org.airTribe.taskTrackingSystem.entity.User;
import org.airTribe.taskTrackingSystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        User user = _userService.getUserById(id);

        Map<String, String> response = new HashMap<>();
        response.put("userId",user.getUserId().toString());
        response.put("firstName",user.getFirstName());
        response.put("lastName",user.getLastName());
        response.put("email",user.getEmail());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/findIdByEmail/{email}")
    public ResponseEntity<Object> findIdByEmail(@PathVariable String email)
    {
        User user = _userService.getIdByEmail(email);
        Map<String, String> response = new HashMap<>();
        response.put("userId",user.getUserId().toString());
        response.put("firstName",user.getFirstName());
        response.put("lastName",user.getLastName());
        response.put("email",user.getEmail());

        return ResponseEntity.ok(response);
    }
}
