package br.com.fiap.tech.challenge.erp_restaurant.dto;

import br.com.fiap.tech.challenge.erp_restaurant.model.User;
import java.util.Date;

public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String login;
    private Date dateChange;
    private Date dateGeneration;
    private String adress;
    private User.UserType type;

    public UserResponseDTO() {
    }

    public UserResponseDTO(Long id, String name, String email, String login, Date dateChange, Date dateGeneration, String adress, User.UserType type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.dateChange = dateChange;
        this.dateGeneration = dateGeneration;
        this.adress = adress;
        this.type = type;
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public User.UserType getType() {
        return type;
    }

    public void setType(User.UserType type) {
        this.type = type;
    }
}
