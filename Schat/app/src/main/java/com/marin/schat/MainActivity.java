package com.marin.schat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.marin.entities.User;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    CallbackManager callbackManager;

    JSONObject o;
    GraphResponse r;

    public static AccessToken at;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        callbackManager = CallbackManager.Factory.create();

        if(!User.loggedIn){
            LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);
            loginButton.setReadPermissions("email");

            LoginManager.getInstance().registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            at = loginResult.getAccessToken();
                            final GraphRequest request = GraphRequest.newMeRequest(
                                    loginResult.getAccessToken(),
                                    new GraphRequest.GraphJSONObjectCallback() {
                                        @Override
                                        public void onCompleted(
                                                JSONObject object,
                                                GraphResponse response) {
                                            JSONObject jobj = null;
                                            try {
                                                jobj = new JSONObject(response.getRawResponse());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            try {
                                                login(jobj.getString("id"),jobj.getString("name"),jobj.getString("link"),jobj.getString("gender"),jobj.getString("email"));
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                            Bundle parameters = new Bundle();
                            parameters.putString("fields", "id,name,link,gender,email");
                            request.setParameters(parameters);
                            request.executeAsync();

                            LoginManager.getInstance().logOut();

                        }

                        @Override
                        public void onCancel() {
                            // App code
                        }

                        @Override
                        public void onError(FacebookException exception) {
                            // App code
                        }
                    });
        }else {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            String id = PreferenceManager.getDefaultSharedPreferences(this).getString("id","");
            String name = PreferenceManager.getDefaultSharedPreferences(this).getString("name","");
            String link = PreferenceManager.getDefaultSharedPreferences(this).getString("link","");
            String gender = PreferenceManager.getDefaultSharedPreferences(this).getString("gender","");
            String email = PreferenceManager.getDefaultSharedPreferences(this).getString("email","");
            User u = new User(id,name,link,gender,email);
            Intent i = new Intent(MainActivity.this, Pocetna.class);
            i.putExtra("user",u);
            startActivity(i);
            finish();
        }


    }

    public void login(String id, String name, String link, String gender, String email){
        User u = new User(id,name,link,gender,email);
        User.loggedIn = true;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        preferences.edit().putString("id",id).apply();
        preferences.edit().putString("name",name).apply();
        preferences.edit().putString("link",link).apply();
        preferences.edit().putString("gender",gender).apply();
        preferences.edit().putString("email",email).apply();
        Intent i = new Intent(MainActivity.this, Pocetna.class);
        i.putExtra("user",u);
        startActivity(i);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
