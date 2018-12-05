package com.brightmd.db.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@Getter
@Setter
@ToString
public final class User {

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String zipcode;
    private final String emailaddress;

    public User() {
        this.id = 0;
        this.firstName = null;
        this.lastName = null;
        this.zipcode = null;
        this.emailaddress = null;
    }

    public User(int id, String firstName, String lastName, String zipcode, String emailaddress) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.zipcode = zipcode;
        this.emailaddress = emailaddress;
    }

}
