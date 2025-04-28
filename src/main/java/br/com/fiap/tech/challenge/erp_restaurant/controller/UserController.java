package br.com.fiap.tech.challenge.erp_restaurant.controller;

import br.com.fiap.tech.challenge.erp_restaurant.model.User;
import br.com.fiap.tech.challenge.erp_restaurant.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try {
            User newUser = userService.registerUser(user.getName(), user.getLogin(), user.getPassword(), user.getEmail());
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (IllegalArgumentException e) {
            Map<String, String> resposta = new HashMap<>();
            resposta.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(resposta);
        }
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user){
        try {
            User updateUser = userService.updateUser(user.getName(), user.getLogin(), user.getPassword(), user.getEmail());
            return ResponseEntity.ok(updateUser);
        } catch (IllegalArgumentException e) {
            Map<String, String> resposta = new HashMap<>();
            resposta.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(resposta);
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUser(@RequestBody User user){
        try {
            String email = userService.deleteUser(user.getEmail());
            return ResponseEntity.ok(email);
        } catch (IllegalArgumentException e) {
            Map<String, String> resposta = new HashMap<>();
            resposta.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(resposta);
        }
    }
}
