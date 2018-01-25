package com.inventory.core.model.entity;

import com.inventory.core.model.enumconstant.Status;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by dhiraj on 1/25/18.
 */
@Entity
@Table(name = "subscriber_table")
public class Subscriber extends AbstractEntity<Long>{

    private String fullName;

    private String contact;

    private String mobile;

    private String email;

    private CityInfo cityInfo;

    private String street;

    @OneToOne(fetch = FetchType.EAGER)
    private User user;

    private Status status;
}
