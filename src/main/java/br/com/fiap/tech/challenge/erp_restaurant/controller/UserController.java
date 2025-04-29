package br.com.fiap.tech.challenge.erp_restaurant.controller;

import br.com.fiap.tech.challenge.erp_restaurant.dto.UserLoginDTO;
import br.com.fiap.tech.challenge.erp_restaurant.dto.UserRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.dto.UserResponseDTO;
import br.com.fiap.tech.challenge.erp_restaurant.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        String token = userService.authenticate(userLoginDTO.getLogin(), userLoginDTO.getPassword());
        if (token != null) {
            Map<String, String> response = new HashMap<>();
            response.put("token", token);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        }
    }

    @GetMapping
    public ResponseEntity<?> listUsers(@RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token de autorização ausente ou inválido.");
        }
        String actualToken = token.substring(7); // Remove "Bearer " do token
        try {
            List<UserResponseDTO> users = userService.listUsers(actualToken);
            return ResponseEntity.ok(users);
        } catch (IllegalArgumentException e) {
            Map<String, String> resposta = new HashMap<>();
            resposta.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resposta); // 403 Forbidden para acesso não autorizado
        }
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> registerUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO registeredUser = userService.registerUser(userRequestDTO);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequestDTO userRequestDTO, @RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token de autorização ausente ou inválido.");
        }
        String actualToken = token.substring(7);
        try {
            UserResponseDTO updatedUser = userService.updateUser(id, userRequestDTO, actualToken);
            return ResponseEntity.ok(updatedUser);
        } catch (IllegalArgumentException e) {
            Map<String, String> resposta = new HashMap<>();
            resposta.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resposta);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        if (token == null || !token.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token de autorização ausente ou inválido.");
        }
        String actualToken = token.substring(7);
        try {
            userService.deleteUser(id, actualToken);
            return ResponseEntity.ok().body("Usuário " + id + " excluído com sucesso!");
        } catch (IllegalArgumentException e) {
            Map<String, String> resposta = new HashMap<>();
            resposta.put("mensagem", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(resposta);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado.");
        }
    }
}