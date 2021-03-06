package com.marin.schat;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
 * Created by Marin Mihajlovic on 6.3.2017..
 */

public class Friends extends Fragment{
    View rootView;
    public static FriendsAdapter adapter;

    public Friends(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView =inflater.inflate(R.layout.fragment_friends,container,false);
        String email = PreferenceManager.getDefaultSharedPreferences(rootView.getContext()).getString("email","");

        Pocetna.fab.setVisibility(View.VISIBLE);
        new SendDataAndProcessResponseTask(
                ServiceGenerator.createService(WebService.class).findFriends(
                        "get_friends",
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


        return rootView;
    }

    public void display(ListOfFriend x){
        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.rv_recycler_view);
        recyclerView.setHasFixedSize(true);
        adapter = new FriendsAdapter(x.friends, this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
    }
}
