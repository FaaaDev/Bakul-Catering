package com.blogcorel.bakulcatering.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.model.UserModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {
    private String TAG = SplashActivity.class.getSimpleName();
    UserModel um;
    FirebaseAuth mAuth;
    FirebaseUser user;
    String email, url;
    ConfigServices cs = new ConfigServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        um = new UserModel();
        session();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View main = findViewById(R.id.layout_splash);
            int flags = main.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            main.setSystemUiVisibility(flags);
            this.getWindow().setStatusBarColor(Color.WHITE);
            this.getWindow().setNavigationBarColor(Color.WHITE);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void session(){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if(user!=null){
            um.setUser(user.getEmail());
            new getUserData().execute();
        }
        else{
            Handler hh = new Handler();
            hh.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);
                    onBackPressed();
                }
            },1000);
        }
    }

    private class getUserData extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            email = um.getUser();

            url = cs.getUrl()+"getuserdata.php?email="+email;

        }
        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    JSONArray user = jsonObj.getJSONArray("result");

                    for (int i = 0; i < user.length(); i++) {
                        JSONObject c = user.getJSONObject(i);

                        String username = c.getString("username");
                        int level = c.getInt("level");

                        um.setUsername(username);
                        um.setLevel(level);

                    }

                } catch (final JSONException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
            onBackPressed();
        }
    }
}
