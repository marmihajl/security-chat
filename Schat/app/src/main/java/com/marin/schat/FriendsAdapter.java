package com.marin.schat;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.marin.entities.User;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marin Mihajlovic on 8.3.2017..
 */

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.FriendsViewHolder> {
    private ArrayList<User> friends;

    public static class FriendsViewHolder extends RecyclerView.ViewHolder{
        public CardView mCardView;
        public ImageView friendImage;
        public TextView friendName;
        public TextView friendEmail;
        public Button friendButton;
        public Button friendButton2;

        public FriendsViewHolder(View v){
            super(v);

            mCardView = (CardView)v.findViewById(R.id.card_view);
            friendImage = (ImageView)v.findViewById(R.id.iv_image);
            friendName = (TextView)v.findViewById(R.id.tv_text);
            friendEmail = (TextView)v.findViewById(R.id.tv_blah);
            friendButton = (Button)v.findViewById(R.id.button2);
            friendButton2 = (Button)v.findViewById(R.id.button3);
        }
    }

    public FriendsAdapter(ArrayList<User> users){
        friends = users;
    }

    @Override
    public FriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_item, parent, false);
        FriendsViewHolder vh = new FriendsViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(FriendsViewHolder holder, int position) {
        holder.friendName.setText(friends.get(position).name);
        holder.friendEmail.setText(friends.get(position).email);
        Picasso.with(holder.mCardView.getContext()).load(friends.get(position).picUrl).into(holder.friendImage);
        holder.friendButton.setVisibility(View.INVISIBLE);
        holder.friendButton2.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return friends.size();
    }
}
