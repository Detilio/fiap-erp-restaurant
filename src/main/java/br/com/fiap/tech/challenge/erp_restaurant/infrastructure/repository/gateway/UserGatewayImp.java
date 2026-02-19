package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.repository.gateway;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import br.com.fiap.tech.challenge.erp_restaurant.application.gateway.UserGateway;
import br.com.fiap.tech.challenge.erp_restaurant.domain.user.User;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.persistence.UserEntity;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.repository.UserRepository;
import br.com.fiap.tech.challenge.erp_restaurant.mapper.UserMapper;

@Component
public class UserGatewayImp implements UserGateway {

    private final UserRepository userRepository;

    public UserGatewayImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllUsers(int page, int size) {
        Page<UserEntity> all = userRepository.findAll(PageRequest.of(size, page));
        return all.stream().map(UserMapper.INSTANCE::toDomain).toList();
    }

    public Optional<User> findById(Long id) {
        Optional<UserEntity> u = this.userRepository.findById(id);
        return u.isPresent() ? Optional.of(UserMapper.INSTANCE.toDomain(u.get())) : Optional.empty();
    }

    public User save(User u) {
        UserEntity save = userRepository.save(UserMapper.INSTANCE.toEntity(u));
        return UserMapper.INSTANCE.toDomain(save);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByLogin(String login) {
        return userRepository.existsByLogin(login);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Long id) {
        return false;
    }

    @Override
    public User findByEmailOrLogin(String email, String login) {
        UserEntity entity = userRepository.findByEmailOrLogin(email, login);
        return entity != null ? UserMapper.INSTANCE.toDomain(entity) : null;
    }


}
