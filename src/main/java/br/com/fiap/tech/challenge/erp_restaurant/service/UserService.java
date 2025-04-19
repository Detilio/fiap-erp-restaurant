package br.com.fiap.tech.challenge.erp_restaurant.service;

import br.com.fiap.tech.challenge.erp_restaurant.model.User;
import br.com.fiap.tech.challenge.erp_restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

}
