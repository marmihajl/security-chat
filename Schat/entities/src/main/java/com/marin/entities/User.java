package com.marin.entities;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Marin Mihajlovic on 27.2.2017..
 */

public class User implements Serializable{
    @SerializedName("id")
    public String id;
    @SerializedName("name")
    public String name;
    @SerializedName("link")
    public String link;
    @SerializedName("gender")
    public String gender;
    @SerializedName("email")
    public String email;
    @SerializedName("picUrl")
    public String picUrl;
    public static boolean loggedIn = false;

    public User(String id, String name, String link, String gender, String email) {
        this.id = id;

        this.name = name;
        this.link = link;
        this.gender = gender;
        this.email = email;
    }

    public User() {
    }
}
