package com.example.online_ethio_gebeya.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

// for both client side and response from server
public class FormErrors {
    @JsonProperty("first_name")
    private List<String> firstNameErrors;
    @JsonProperty("last_name")
    private List<String> lastNameErrors;
    @JsonProperty("email")
    private List<String> emailErrors;
    @JsonProperty("password")
    private List<String> passwordErrors;
    @JsonProperty("password_confirmation")
    private List<String> passwordConfirmationErrors;

    @JsonProperty("current_password")
    private String currentPasswordError;

    // BEGIN
    //   for client side
    public boolean isRegistrationValid() {
        return getFirstNameError() == null &&
                getLastNameError() == null &&
                getEmailError() == null &&
                getPasswordError() == null &&
                getPasswordConfirmationError() == null;
    }

    public boolean isChangePasswordFormValid() {
        return getPasswordError() == null && getPasswordConfirmationError() == null;
    }
    // END

    public String getFirstNameError() {
        return firstNameErrors == null ? null : firstNameErrors.get(0);
    }

    public void setFirstNameError(String firstNameError) {
        firstNameErrors = new ArrayList<>();
        firstNameErrors.add(firstNameError);
    }

    public String getLastNameError() {
        return lastNameErrors == null ? null : lastNameErrors.get(0);
    }

    public void setLastNameError(String lastNameError) {
        lastNameErrors = new ArrayList<>();
        lastNameErrors.add(lastNameError);
    }

    public String getEmailError() {
        return emailErrors == null ? null : emailErrors.get(0);
    }

    public void setEmailError(String emailError) {
        emailErrors = new ArrayList<>();
        emailErrors.add(emailError);
    }

    public String getPasswordError() {
        return passwordErrors == null ? null : passwordErrors.get(0);
    }

    public void setPasswordError(String passwordError) {
        passwordErrors = new ArrayList<>();
        passwordErrors.add(passwordError);
    }

    public String getPasswordConfirmationError() {
        return passwordConfirmationErrors == null ? null : passwordConfirmationErrors.get(0);
    }

    public void setPasswordConfirmationError(String passwordConfirmationError) {
        passwordConfirmationErrors = new ArrayList<>();
        passwordConfirmationErrors.add(passwordConfirmationError);
    }

    public String getCurrentPasswordError() {
        return currentPasswordError;
    }

    public void setCurrentPasswordError(String currentPasswordError) {
        this.currentPasswordError = currentPasswordError;
    }
}
