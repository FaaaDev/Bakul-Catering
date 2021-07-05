package com.blogcorel.bakulcatering.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    ConstraintLayout loginfragment;
    Button login, start_regist;
    EditText log_email, log_pass;

    String email, url, token, value, password;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";

    UserModel um;
    ConfigServices cs = new ConfigServices();

    private String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View main = findViewById(R.id.loginfragment);
            int flags = main.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            main.setSystemUiVisibility(flags);
            this.getWindow().setStatusBarColor(Color.WHITE);
            this.getWindow().setNavigationBarColor(Color.WHITE);
        }

        mAuth = FirebaseAuth.getInstance();
        um = new UserModel();

        loginfragment = findViewById(R.id.loginfragment);
        login = findViewById(R.id.login);
        start_regist = findViewById(R.id.start_regist);
        log_email = findViewById(R.id.log_email);
        log_pass = findViewById(R.id.log_pass);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(log_email.getText()) || TextUtils.isEmpty(log_pass.getText())){
                    if(TextUtils.isEmpty(log_email.getText())){
                        log_email.setError("E-mail tidak boleh kosong");
                    }
                    if(TextUtils.isEmpty(log_pass.getText())){
                        log_pass.setError("Password tidak boleh kosong");
                    }
                } else if (log_pass.length() < 8){
                    log_pass.setError("Password minimal 8 karakter");
                    log_pass.setText("");
                    log_pass.requestFocus();
                } else{
                    login();
                }
            }
        });

        start_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    private void login(){
        email = log_email.getText().toString();
        password = log_pass.getText().toString();

        Snackbar snackbar = Snackbar
                .make(loginfragment, "Permintaan Sedang Diproses...", Snackbar.LENGTH_INDEFINITE);
        snackbar.show();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            um.setUser(user.getEmail());

                            FirebaseInstanceId.getInstance().getInstanceId()
                                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                            if (!task.isSuccessful()) {
                                                Log.w(TAG, "getInstanceId failed", task.getException());
                                                return;
                                            }

                                            token = task.getResult().getToken();
                                            SharedPreferences sp = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sp.edit();
                                            editor.putString(TEXT, token);
                                            editor.apply();
                                            new registToken().execute();
                                        }
                                    });
                        } else {
                            Snackbar snackbar = Snackbar
                                    .make(loginfragment, "Gagal Login ! Periksa Username dan Password", Snackbar.LENGTH_LONG);
                            snackbar.show();
                        }

                    }
                });
    }

    private class getUserData extends AsyncTask<Void, Void, Void> {

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
            Snackbar snackbar = Snackbar
                    .make(loginfragment, "Berhasil Melakukan Login", Snackbar.LENGTH_LONG);
            snackbar.show();
            Handler hh = new Handler();
            hh.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            },500);
        }
    }

    private class registToken extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Snackbar snackbar = Snackbar
                    .make(loginfragment, "Mendapatkan Data...", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();

            url = cs.getUrl()+"registerdevice.php";
        }
        @Override
        protected Void doInBackground(Void... voids) {
            PostHandler sh = new PostHandler();

            JSONObject data = new JSONObject();
            try {
                data.put("email", email);
                data.put("token", token);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String jsonStr = sh.makeServiceCall(url, data);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    value = jsonObj.getString("value");

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
            if(("S1").equals(value)){
                new getUserData().execute();
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

}
