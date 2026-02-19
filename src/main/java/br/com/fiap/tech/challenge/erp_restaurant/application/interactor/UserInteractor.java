package br.com.fiap.tech.challenge.erp_restaurant.application.interactor;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.fiap.tech.challenge.erp_restaurant.application.exception.NotFoundException;
import br.com.fiap.tech.challenge.erp_restaurant.application.exception.NotUniqueException;
import br.com.fiap.tech.challenge.erp_restaurant.application.gateway.UserGateway;
import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.address.AddressUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.user.UserUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.domain.address.Address;
import br.com.fiap.tech.challenge.erp_restaurant.domain.user.User;
import br.com.fiap.tech.challenge.erp_restaurant.shared.Role;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserInteractor implements UserUseCase {

    private final AddressUseCase addressUseCase;

    private final UserGateway userGateway;

    public UserInteractor(UserGateway userGateway, AddressUseCase addressUseCase) {
		this.userGateway = userGateway;
        this.addressUseCase = addressUseCase;
    }

    public List<User> findAllUsers(int page, int size) {
        return userGateway.findAllUsers(size, page);
    }

    public User findUserById(Long id) {
        return userGateway.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public User save(User userToSave) {
        boolean emailAlreadyInUse = userGateway.existsByEmail(userToSave.getEmail());
        if (emailAlreadyInUse) {
            throw new NotUniqueException("This email is already been used by another user");
        }

        boolean loginAlreadyInUse = userGateway.existsByLogin(userToSave.getLogin());
        if (loginAlreadyInUse) {
            throw new NotUniqueException("This login is already been used by another user");
        }

        log.info("saving user {}", userToSave.getLogin());
        User savedUser = userGateway.save(new User(null, userToSave.getName(), userToSave.getEmail(), userToSave.getLogin(), userToSave.getPassword(), userToSave.getRole()));
        return User.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .login(savedUser.getLogin())
                .role(savedUser.getRole())
                .updatedAt(savedUser.getUpdatedAt())
                .createdAt(savedUser.getCreatedAt())
                .build();
    }

    public User update(User userToUpdate) {
        userGateway.findById(userToUpdate.getId())
        		.orElseThrow( () -> new NotFoundException("User not found"));

        log.info("updating user {}", userToUpdate.getLogin());

        User savedUser = userGateway.save(new User(userToUpdate.getId(), userToUpdate.getName(), userToUpdate.getEmail(), userToUpdate.getLogin(), userToUpdate.getPassword(), userToUpdate.getRole()));
        return User.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .login(savedUser.getLogin())
                .role(savedUser.getRole())
                .updatedAt(savedUser.getUpdatedAt())
                .createdAt(savedUser.getCreatedAt())
                .build();
    }
    
	@Override
	public User updateAddress(User u) {
		User user = userGateway.findById(u.getId())
        		.orElseThrow(() -> new NotFoundException("User not found"));
	
		if(u.getAddress() == null) {
			throw new IllegalArgumentException("must hava an adress");
		}
		
		if(user.getAddress() != null) {
			Address address = addressUseCase.update(user.getAddress().getId(), u.getAddress());
			user.setAddress(address);
		}else {
			user.setAddress(addressUseCase.create(u.getAddress())); 
		}
		
		userGateway.save(user);
		return User.builder()
                .id(user.getId())
                .name(user.getName())
                .updatedAt(user.getUpdatedAt())
                .address(user.getAddress())
                .build();
	}

   
    public User updateRole(User userToUpdate) {
        User user = userGateway.findById(userToUpdate.getId())
        		.orElseThrow(() -> new NotFoundException("User not found"));
        
        if(user.getRole().equals(Role.OWNER)) {
//        	if(user.getRestaurant() != null)
//        		throw new IllegalStateException("User is a OWNER and have a Restaurant can't be a CUSTOMER");
        }
        
        log.info("updating userId {} role {}", userToUpdate.getId(), userToUpdate.getRole());
        user.setRole(userToUpdate.getRole());
        User savedUser = userGateway.save(user);
        return User.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .role(savedUser.getRole())
                .updatedAt(savedUser.getUpdatedAt())
                .build();
    }


    public void delete(Long id) {
        log.info("deleting user id {}", id);

        Optional<User> user = userGateway.findById(id);
        if (user.isEmpty())
            throw new NotFoundException("User not found");
       
        userGateway.delete(id);
    }



}
