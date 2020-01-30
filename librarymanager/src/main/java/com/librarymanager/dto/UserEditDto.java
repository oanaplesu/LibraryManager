package com.librarymanager.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserEditDto {
    private Long id;

    @NotEmpty(message = "Campul nu poate fi gol")
    private String firstName;

    @NotEmpty(message = "Campul nu poate fi gol")
    private String lastName;

    @Email(message = "Email invalid")
    @NotEmpty(message = "Campul nu poate fi gol")
    private String email;

    @NotEmpty(message = "Campul nu poate fi gol")
    private String phone;

    @NotEmpty(message = "Campul nu poate fi gol")
    private String address;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
