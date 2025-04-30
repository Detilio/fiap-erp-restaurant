package br.com.fiap.tech.challenge.erp_restaurant.dto;

import br.com.fiap.tech.challenge.erp_restaurant.model.User;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public class UserRequestDTO {
    @NotBlank(message = "O nome é obrigatório")
    private String name;
    @NotBlank(message = "O e-mail é obrigatório")
    private String email;
    @NotBlank(message = "O login é obrigatório")
    private String login;
    @NotBlank(message = "A senha é obrigatória")
    private String password;
    private User.UserType type;
    private List<AddressDTO> addresses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User.UserType getType() {
        return type;
    }

    public void setType(User.UserType type) {
        this.type = type;
    }

    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }

    public UserRequestDTO() {
    }
}