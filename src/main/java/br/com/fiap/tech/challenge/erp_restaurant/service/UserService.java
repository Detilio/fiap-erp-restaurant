package br.com.fiap.tech.challenge.erp_restaurant.service;

import br.com.fiap.tech.challenge.erp_restaurant.model.User;
import br.com.fiap.tech.challenge.erp_restaurant.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private Optional<User> userFound;
    private LocalDate dataAtual = LocalDate.now();
    Date date = Date.from(dataAtual.atStartOfDay(ZoneId.systemDefault()).toInstant());

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public Optional<User> userSearch (String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    public void userValidation(String email) {
        userFound = userSearch(email);
    }

    public User registerUser(String name, String login, String password, String email) {
        userValidation(email);
        if(userFound.isPresent()){
            throw new IllegalArgumentException("Usuário já cadastrado para este e-mail!");
        } else {
            User user = new User();
            user.setName(name);
            user.setLogin(login);
            user.setPassword(password);
            user.setEmail(email);
            user.setDateGeneration(date);
            return userRepository.save(user);
        }
    }

    public User updateUser(String name, String login, String password, String email) {
        userValidation(email);
        if(userFound.isPresent()){
            User user = userFound.get();
            user.setName(name);
            user.setLogin(login);
            user.setPassword(password);
            user.setDateChange(date);
            return userRepository.save(user);
        } else {
            throw new IllegalArgumentException("Não existe usuário cadastrado para este e-mail!");
        }
    }

    public String deleteUser(String email){
        userValidation(email);
        if(userFound.isPresent()){
            userRepository.delete(userFound.get());
            return "Usuário excluído com sucesso!";
        } else {
            throw new IllegalArgumentException("Não existe usuário cadastrado para este e-mail!");
        }
    }

}
