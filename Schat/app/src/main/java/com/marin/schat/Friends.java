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

        ArrayList<User> users = new ArrayList<User>();
        String email = PreferenceManager.getDefaultSharedPreferences(rootView.getContext()).getString("email","");

        /*SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(rootView.getContext());
        String id = PreferenceManager.getDefaultSharedPreferences(rootView.getContext()).getString("id","");
        String name = PreferenceManager.getDefaultSharedPreferences(rootView.getContext()).getString("name","");
        String link = PreferenceManager.getDefaultSharedPreferences(rootView.getContext()).getString("link","");
        String gender = PreferenceManager.getDefaultSharedPreferences(rootView.getContext()).getString("gender","");

        User u = new User(id,name,link,gender,email);
        u.picUrl = "https://scontent.xx.fbcdn.net/v/t1.0-1/p50x50/11745678_10206756231521542_4413984894819159045_n.jpg?oh=4695092350338fa41e09d62e1631a0e0&oe=5970885C";
        users.add(u);
        User u2 = new User(id,name,link,gender,email);
        u2.picUrl = "https://scontent.xx.fbcdn.net/v/t1.0-1/p50x50/11745678_10206756231521542_4413984894819159045_n.jpg?oh=4695092350338fa41e09d62e1631a0e0&oe=5970885C";
        users.add(u2);
        User u3 = new User(id,name,link,gender,email);
        u3.picUrl = "https://scontent.xx.fbcdn.net/v/t1.0-1/p50x50/11745678_10206756231521542_4413984894819159045_n.jpg?oh=4695092350338fa41e09d62e1631a0e0&oe=5970885C";
        users.add(u3);*/


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
        FriendsAdapter adapter = new FriendsAdapter(x.friends);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(llm);
    }
}
