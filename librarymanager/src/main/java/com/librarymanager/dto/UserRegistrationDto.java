package com.librarymanager.dto;

import com.librarymanager.misc.FieldMatch;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@FieldMatch.List({
        @FieldMatch(first = "password", second = "confirmPassword", message = "Campurile pentru parola nu se potrivesc"),
        @FieldMatch(first = "email", second = "confirmEmail", message = "Campurile de email nu se potrivesc")
})
public class UserDto {
    private Long id;

    @NotEmpty(message = "Campul nu poate fi gol")
    private String firstName;

    @NotEmpty(message = "Campul nu poate fi gol")
    private String lastName;

    @NotEmpty(message = "Campul nu poate fi gol")
    private String password;

    @NotEmpty(message = "Campul nu poate fi gol")
    private String confirmPassword;

    @Email(message = "Email invalid")
    @NotEmpty(message = "Campul nu poate fi gol")
    private String email;

    @Email(message = "Email invalid")
    @NotEmpty(message = "Campul nu poate fi gol")
    private String confirmEmail;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
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
}
