package br.com.fiap.tech.challenge.erp_restaurant.dto;

import br.com.fiap.tech.challenge.erp_restaurant.model.User;
import java.util.List;

public class UserRequestDTO {
    private String name;
    private String email;
    private String login;
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