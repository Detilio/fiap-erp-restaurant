package br.com.fiap.tech.challenge.erp_restaurant.repository;

import br.com.fiap.tech.challenge.erp_restaurant.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByLoginIgnoreCase(String login);

    Optional<UserEntity> findByLoginAndPasswordIgnoreCase(@Param("login") String login, @Param("password") String password);
}
