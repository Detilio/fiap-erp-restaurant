package br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.v1;

import static br.com.fiap.tech.challenge.erp_restaurant.mapper.UserMapper.INSTANCE;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.tech.challenge.erp_restaurant.application.usecase.user.UserUseCase;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.user.UserChangeRoleRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.user.UserRequestDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.user.UserResponseDTO;
import br.com.fiap.tech.challenge.erp_restaurant.infrastructure.controller.dto.user.UserUpdateAddressRequestDTO;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("v1/users")
public class UserControllerV1 {


    private final UserUseCase userUseCase;

    public UserControllerV1(UserUseCase userService) {
        this.userUseCase = userService;
    }


    @GetMapping
    public List<UserResponseDTO> getUsers(
            @RequestParam int page,
            @RequestParam int size) {
    	 log.info("find all users list page {} size {}", page , size);
        return userUseCase.findAllUsers(page, size).stream().map(INSTANCE::domainToDTO).toList();
    }

    @GetMapping("{id}")
    public UserResponseDTO getUser(@PathVariable Long id) {
        return INSTANCE.domainToDTO(userUseCase.findUserById(id));
    }

    @PostMapping
    public UserResponseDTO createUser(@RequestBody @Valid UserRequestDTO userDto) {
        log.info("receiving user to create", userDto);
        return INSTANCE.domainToDTO(userUseCase.save(INSTANCE.dtoToDomain(userDto)));
    }

    @PutMapping
    public UserResponseDTO updateUser(@RequestBody @Valid UserRequestDTO userDto) {
        log.info("receiving user to update", userDto);
        return INSTANCE.domainToDTO(userUseCase.update(INSTANCE.dtoToDomain(userDto)));
    }
    
    @PatchMapping("role")
    public UserResponseDTO updateUserRole(@RequestBody UserChangeRoleRequestDTO userDto) {
        log.info("receiving user to update role", userDto);
        return INSTANCE.domainToDTO(userUseCase.updateRole(INSTANCE.dtoToDomain(userDto)));
    }
    
    @PutMapping("address")
    public UserResponseDTO updateUserAddress (@RequestBody UserUpdateAddressRequestDTO userDto) {
        log.info("receiving user to update addres", userDto);
        return INSTANCE.domainToDTO(userUseCase.updateAddress(INSTANCE.dtoToDomain(userDto)));
    }
    

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteUser(@PathVariable Long id) {
        log.info("receiving user id {} to delete", id);
        userUseCase.delete(id);
    }

}
