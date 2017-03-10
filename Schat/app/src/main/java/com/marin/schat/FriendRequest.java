package com.marin.schat;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marin.entities.ListOfFriend;
import com.marin.entities.User;
import com.marin.webservice.SendDataAndProcessResponseTask;
import com.marin.webservice.ServiceGenerator;
import com.marin.webservice.WebService;

import java.util.ArrayList;

/**
 * Created by Marin Mihajlovic on 10.3.2017..
 */

public class FriendRequest extends Fragment {

    public static String email;
    View rootView;

    public FriendRequest() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_friend_request,container,false);

        email = PreferenceManager.getDefaultSharedPreferences(rootView.getContext()).getString("email","");

        new SendDataAndProcessResponseTask(
                ServiceGenerator.createService(WebService.class).findRequest(
                        "get_friends_request",
                        email),
                new SendDataAndProcessResponseTask.PostActions(){
                    @Override
                    public void onSuccess(Object response) {

                        ListOfFriend friends = (ListOfFriend)response;
                        display(friends);
                    }

                    @Override
                    public void onFailure() {

                    }
                }
        );


        return  rootView;
    }

    public void display(ListOfFriend x){
        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.rv_recycler_view2);
        recyclerView.setHasFixedSize(true);
        RequestAdapter adapter = new RequestAdapter(x.friends);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
    }
}
