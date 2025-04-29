package br.com.fiap.tech.challenge.erp_restaurant.model;

import java.util.Date;
import java.util.List;

public class User {
    private Long id;
    private String name;
    private String email;
    private String login;
    private Date dateChange;
    private Date dateGeneration;
    private String password;
    private String token;
    private UserType type;
    private List<Address> deliveryAddresses;

    public List<Address> getDeliveryAddresses() {
        return deliveryAddresses;
    }

    public void setDeliveryAddresses(List<Address> deliveryAddresses) {
        this.deliveryAddresses = deliveryAddresses;
    }

    public User(Long id, String name, String email, String login, Date dateChange, Date dateGeneration, String password, UserType type, List<Address> deliveryAddresses) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.login = login;
        this.dateChange = dateChange;
        this.dateGeneration = dateGeneration;
        this.password = password;
        this.type = type;
        this.deliveryAddresses = deliveryAddresses;
    }

    public Long getId() {
        return id;
    }

    //  Enum para representar os tipos de usuário
    public enum UserType {
        MASTER,
        NORMAL
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public Date getDateChange() {
        return dateChange;
    }

    public Date getDateGeneration() {
        return dateGeneration;
    }

    public String getPassword() {
        return password;
    }

    public UserType getType() {
        return type;
    }

    public User(){
    }
}
