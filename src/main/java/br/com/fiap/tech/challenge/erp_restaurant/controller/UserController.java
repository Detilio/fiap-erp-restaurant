package br.com.fiap.tech.challenge.erp_restaurant.controller;

import br.com.fiap.tech.challenge.erp_restaurant.model.User;
import br.com.fiap.tech.challenge.erp_restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers(){
        List<User> users = userService.listUsers();
        return ResponseEntity.ok(users);
    }


}
