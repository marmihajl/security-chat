package com.marin.entities;

import android.net.Uri;

import java.io.Serializable;

/**
 * Created by Marin Mihajlovic on 27.2.2017..
 */

public class User implements Serializable{
    public String id;
    public String name;
    public String link;
    public String gender;
    public String email;
    public String picUrl;
    public static boolean loggedIn = false;

    public User(String id, String name, String link, String gender, String email) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.gender = gender;
        this.email = email;
    }
}
