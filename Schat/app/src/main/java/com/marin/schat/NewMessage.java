package com.marin.schat;

import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.marin.entities.User;
import com.squareup.picasso.Picasso;

/**
 * Created by Marin Mihajlovic on 13.3.2017..
 */

public class NewMessage extends Fragment {

    public NewMessage(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_send_message,container,false);
        Bundle b = getArguments();
        User user = (User) b.getSerializable("user");
        ImageView pic = (ImageView) v.findViewById(R.id.imageView2);
        TextView name = (TextView)v.findViewById(R.id.textView);
        TextView mail = (TextView)v.findViewById(R.id.textView3);
        EditText editText = (EditText)v.findViewById(R.id.editText);

        Pocetna.fab.setVisibility(View.INVISIBLE);

        Picasso.with(v.getContext()).load(user.picUrl).into(pic);
        name.setText(user.name);
        mail.setText(user.email);
        return v;
    }

}
