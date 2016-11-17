package com.example.travissimple;

import com.hannesdorfmann.sqlbrite.objectmapper.annotation.Column;
import com.hannesdorfmann.sqlbrite.objectmapper.annotation.ObjectMappable;

/**
 * Created by raul on 16/11/2016.
 */
@ObjectMappable
public class Users {
    //
    // Define Table name and column names as constants
    //
    public static final String TABLE_NAME = "users";
    public static final String COL_ID = "_id";
    public static final String COL_USER_NAME = "userName";
    public static final String COL_USER_AGE  = "age";

    //
    // Fields mapped to database columns
    //
    @Column(COL_ID)
    int id;

    @Column(COL_USER_NAME)
    String userName;

    @Column(COL_USER_AGE)
    int age;

    public Users() {}

    public Users(int id, String userName, int age) {
        this.id = id;
        this.userName = userName;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public int getAge() {
        return age;
    }
}
