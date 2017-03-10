package com.marin.entities;

import com.google.gson.annotations.SerializedName;
import com.marin.entities.User;

import java.util.ArrayList;

/**
 * Created by Marin Mihajlovic on 9.3.2017..
 */

public class ListOfFriend {

    @SerializedName("friends")
    public ArrayList<User> friends;
}
