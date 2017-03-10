package com.marin.schat;

import android.preference.PreferenceManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.marin.entities.ListOfFriend;
import com.marin.entities.User;
import com.marin.webservice.SendDataAndProcessResponseTask;
import com.marin.webservice.ServiceGenerator;
import com.marin.webservice.WebService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Marin Mihajlovic on 8.3.2017..
 */

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.FriendsViewHolder> {
    private ArrayList<User> friends;

    public static class FriendsViewHolder extends RecyclerView.ViewHolder{
        public CardView mCardView;
        public ImageView friendImage;
        public TextView friendName;
        public TextView friendEmail;
        public Button friendButton;

        public FriendsViewHolder(View v){
            super(v);

            mCardView = (CardView)v.findViewById(R.id.card_view);
            friendImage = (ImageView)v.findViewById(R.id.iv_image);
            friendName = (TextView)v.findViewById(R.id.tv_text);
            friendEmail = (TextView)v.findViewById(R.id.tv_blah);
            friendButton = (Button)v.findViewById(R.id.button2);
        }
    }

    public RequestAdapter(ArrayList<User> users){
        friends = users;
    }

    @Override
    public FriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false);
        FriendsViewHolder vh = new FriendsViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final FriendsViewHolder holder, int position) {
        holder.friendName.setText(friends.get(position).name);
        holder.friendEmail.setText(friends.get(position).email);
        Picasso.with(holder.mCardView.getContext()).load(friends.get(position).picUrl).into(holder.friendImage);
        holder.friendButton.setVisibility(View.VISIBLE);
        holder.friendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SendDataAndProcessResponseTask(
                        ServiceGenerator.createService(WebService.class).saveRequest(
                                "saveFriend",
                                FriendRequest.email,
                                holder.friendName.getText().toString()),
                        new SendDataAndProcessResponseTask.PostActions(){
                            @Override
                            public void onSuccess(Object response) {
                            }

                            @Override
                            public void onFailure() {

                            }
                        }
                );

            }
        });
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
}
