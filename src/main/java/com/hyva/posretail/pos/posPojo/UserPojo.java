package com.hyva.posretail.pos.posPojo;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.NotNull;

/**
 * Created by tnataraj on 1/15/18.
 */
public class UserPojo {

    @NotNull
    private String userName;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String companyName;

    /**
     * Gets email.
     *
     * @return Value of email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets password.
     *
     * @return Value of password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets userName.
     *
     * @return Value of userName.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets new email.
     *
     * @param email New value of email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets new companyName.
     *
     * @param companyName New value of companyName.
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Sets new password.
     *
     * @param password New value of password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets companyName.
     *
     * @return Value of companyName.
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets new userName.
     *
     * @param userName New value of userName.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
}
