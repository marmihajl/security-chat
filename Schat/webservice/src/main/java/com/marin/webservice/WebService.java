package com.marin.webservice;

import com.marin.entities.ListOfFriend;
import com.marin.entities.User;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by Marin Mihajlovic on 28.2.2017..
 */

public interface WebService {
    @FormUrlEncoded
    @POST("schat.php")
    Call<User> registerUser(
        @Field("request_type") String requestType,
        @Field("id") String id,
        @Field("name") String name,
        @Field("link") String link,
        @Field("gender") String gender,
        @Field("email") String email,
        @Field("picUrl") String picUrl
    );

    @FormUrlEncoded
    @POST("schat.php")
    Call<ListOfFriend> findFriends(
            @Field("request_type") String requestType,
            @Field("email") String email
    );
}
