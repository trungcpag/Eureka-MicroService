package com.app.photoappapiusers.ui.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CreateUserRequestModel {

    @NotNull(message = "FistName can not be null")
    @Size(min = 2, message = "FistName must not be less than two")
    private String firstName;

    @NotNull(message = "LastName can not be null")
    @Size(min = 2, message = "LastName must not be less than two")
    private String lastName;

    @NotNull(message = "Password can not be null")
    @Size(min = 8, max = 16,message = "Password must be equal or grater than 16")
    private String password;

    @NotNull(message = "email can not be null")
    @Email
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
