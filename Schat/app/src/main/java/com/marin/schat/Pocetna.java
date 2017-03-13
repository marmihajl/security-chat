package com.marin.schat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.marin.entities.User;
import com.marin.webservice.SendDataAndProcessResponseTask;
import com.marin.webservice.ServiceGenerator;
import com.marin.webservice.WebService;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class Pocetna extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    User user;
    public static FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pocetna);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Friends();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_pocetna,fragment).commit();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        Intent intent = getIntent();
        user = (User) intent.getExtras().getSerializable("user");

        View header=navigationView.getHeaderView(0);

        final ImageView userPicture = (ImageView)header.findViewById(R.id.imageView);
        TextView userName = (TextView)header.findViewById(R.id.userName);
        TextView userId = (TextView)header.findViewById(R.id.userId);

        userName.setText(user.name);
        userId.setText(user.email);

        Bundle params = new Bundle();
        params.putString("fields", "id,picture.type(small)");
        new GraphRequest(MainActivity.at, "me", params, HttpMethod.GET,
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        if (response != null) {
                            try {
                                JSONObject data = response.getJSONObject();
                                if (data.has("picture")) {
                                    JSONObject jobj = null;
                                    try {
                                        jobj = new JSONObject(response.getRawResponse());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    JSONObject jobj2 = null;

                                    jobj2 = jobj.getJSONObject("picture");

                                    JSONObject d = jobj2.getJSONObject("data");
                                    String url = d.getString("url");
                                    user.picUrl = url;

                                    Picasso.with(navigationView.getContext()).load(url).into(userPicture);

                                    new SendDataAndProcessResponseTask(
                                            ServiceGenerator.createService(WebService.class).registerUser(
                                                    "register_user",
                                                    user.id,
                                                    user.name,
                                                    user.link,
                                                    user.gender,
                                                    user.email,
                                                    user.picUrl),
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
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).executeAsync();
        Fragment fragment = new Message();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_pocetna,fragment).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pocetna, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Fragment fragment = new Message();
        int id = item.getItemId();

        if (id == R.id.nav_message) {
            // Handle the camera action
            fragment = new Message();
        } else if (id == R.id.nav_friends) {
            fragment = new Friends();

        }else if (id == R.id.nav_friends_request){
            fragment = new FriendRequest();
        }
        else if (id == R.id.nav_log_out) {

            finish();
            User.loggedIn = false;
            System.exit(0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_pocetna,fragment).commit();
        return true;
    }

}
