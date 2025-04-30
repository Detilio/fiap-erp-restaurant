package br.com.fiap.tech.challenge.erp_restaurant.dto;

import br.com.fiap.tech.challenge.erp_restaurant.model.User;
import java.util.Date;
import java.util.List;

public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String login;
    private Date dateChange;
    private Date dateGeneration;
    private User.UserType type;
    private List<AddressDTO> addresses;

    public UserResponseDTO() {
    }

    public UserResponseDTO(Long id, String name, String email, String login, Date dateChange, Date dateGeneration, User.UserType type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.dateChange = dateChange;
        this.dateGeneration = dateGeneration;
        this.type = type;
    }

    public UserResponseDTO(Long id, String name, String email, String login, Date dateChange, Date dateGeneration, User.UserType type, List<AddressDTO> addresses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.dateChange = dateChange;
        this.dateGeneration = dateGeneration;
        this.type = type;
        this.addresses = addresses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Date getDateChange() {
        return dateChange;
    }

    public void setDateChange(Date dateChange) {
        this.dateChange = dateChange;
    }

    public Date getDateGeneration() {
        return dateGeneration;
    }

    public void setDateGeneration(Date dateGeneration) {
        this.dateGeneration = dateGeneration;
    }

    public User.UserType getType() {
        return type;
    }

    public void setType(User.UserType type) {
        this.type = type;
    }

    // Getter e Setter para a lista de endereços
    public List<AddressDTO> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<AddressDTO> addresses) {
        this.addresses = addresses;
    }
}