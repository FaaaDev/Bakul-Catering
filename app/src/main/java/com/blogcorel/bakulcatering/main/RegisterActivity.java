package com.blogcorel.bakulcatering.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
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
import android.widget.TextView;
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

public class RegisterActivity extends AppCompatActivity {

    private String TAG = RegisterActivity.class.getSimpleName();
    private static String url ="";
    private String value;
    FirebaseAuth mAuth;
    String email,username,password,token;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";

    Button start_login, regist;
    ConstraintLayout registerfragment;
    EditText reg_email, reg_username, reg_password, reg_repass;

    UserModel um;
    FirebaseUser user;
    ConfigServices cs = new ConfigServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        um = new UserModel();

        setContentView(R.layout.activity_register);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View main = findViewById(R.id.registerfragment);
            int flags = main.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            main.setSystemUiVisibility(flags);
            this.getWindow().setStatusBarColor(Color.WHITE);
            this.getWindow().setNavigationBarColor(Color.WHITE);
        }

        start_login = findViewById(R.id.start_login);
        registerfragment = findViewById(R.id.registerfragment);
        regist = findViewById(R.id.regist);
        reg_email = findViewById(R.id.reg_email);
        reg_username = findViewById(R.id.reg_username);
        reg_password = findViewById(R.id.reg_pass);
        reg_repass = findViewById(R.id.reg_repass);


        start_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(reg_email.getText()) ||
                        TextUtils.isEmpty(reg_username.getText()) ||
                        TextUtils.isEmpty(reg_password.getText()) ||
                        TextUtils.isEmpty(reg_repass.getText())){

                    if(TextUtils.isEmpty(reg_email.getText())){
                        reg_email.setError("Email tidak boleh kosong");
                    }

                    if(TextUtils.isEmpty(reg_username.getText())){
                        reg_username.setError("Username tidak boleh kosong");
                    }

                    if(TextUtils.isEmpty(reg_password.getText())){
                        reg_password.setError("Password tidak boleh kosong");
                    }

                    if(TextUtils.isEmpty(reg_repass.getText())){
                        reg_repass.setError("Ketik ulang password");
                    }
                } else if (reg_password.length()<8){
                    reg_password.setError("Password minimal 8 karakter");
                    reg_password.setText("");
                    reg_repass.setText("");
                    reg_password.requestFocus();
                } else if (!reg_password.getText().toString().equals(reg_repass.getText().toString())){
                    reg_repass.setError("Password tidak sama");
                    reg_repass.setText("");
                    reg_repass.requestFocus();
                } else {
                    new startRegist().execute();
                }
            }
        });

    }


    private class startRegist extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Snackbar snackbar = Snackbar
                    .make(registerfragment, "Sedang Mendaftar...", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();


            mAuth = FirebaseAuth.getInstance();

            email = reg_email.getText().toString();
            username = reg_username.getText().toString();
            password = reg_password.getText().toString();

            url = cs.getUrl()+"regist.php?email="+email+"&username="+username+"&password="+password+"&level=1";

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);


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
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(("E1").equals(value)){
                value = "";
                reg_email.setError("Username Sudah Digunakan");
                reg_email.setText("");
                reg_email.requestFocus();
                Snackbar snackbar = Snackbar
                        .make(registerfragment, "Username Sudah Digunakan", Snackbar.LENGTH_LONG);
                snackbar.show();
            } else if(("E2").equals((value))){
                Snackbar snackbar = Snackbar
                        .make(registerfragment, "Gagal Melakukan Pendaftaran", Snackbar.LENGTH_LONG);
                snackbar.show();
            } else if (("S1").equals(value)){
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    user = mAuth.getCurrentUser();
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
                                                    session();
                                                }
                                            });
                                }
                            }
                        });
            }
        }

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(i);
        finish();
    }

    private void session(){
        Snackbar snackbar = Snackbar
                .make(registerfragment, "Menyiapkan Data", Snackbar.LENGTH_LONG);
        snackbar.show();

        new registToken().execute();
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
            Snackbar snackbar = Snackbar
                    .make(registerfragment, "Berhasil Melakukan Penfadtaran", Snackbar.LENGTH_LONG);
            snackbar.show();
            Handler hh = new Handler();
            hh.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            },500);
        }
    }

    private class registToken extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {


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
}


