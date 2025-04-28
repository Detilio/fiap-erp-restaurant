package br.com.fiap.tech.challenge.erp_restaurant.repository;

import br.com.fiap.tech.challenge.erp_restaurant.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailIgnoreCase(String email);
}
