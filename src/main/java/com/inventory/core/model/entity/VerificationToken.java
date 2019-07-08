package com.inventory.core.model.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by dhiraj on 2/6/18.
 */
@Entity
@Table(name = "verification_token_table")
public class VerificationToken extends AbstractEntity<Long>{

    private String token;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
