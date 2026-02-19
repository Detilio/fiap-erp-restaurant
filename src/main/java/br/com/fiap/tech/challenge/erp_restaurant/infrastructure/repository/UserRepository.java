package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.repository;

import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.persistence.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByEmailOrLogin(String email, String login);

    boolean existsByEmail(String email);

    boolean existsByLogin(String login);

}