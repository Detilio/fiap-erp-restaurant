package br.com.fiap.tech.challenge.erp_restaurant.application.gateway;

import java.util.List;
import java.util.Optional;

import br.com.fiap.tech.challenge.erp_restaurant.domain.user.User;

public interface UserGateway {


    List<User> findAllUsers(int page, int size);

    Optional<User> findById(Long id);

    User save(User u);

    boolean existsById(Long id);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);

    void delete(Long id);

    User findByEmailOrLogin(String email, String login);
}
