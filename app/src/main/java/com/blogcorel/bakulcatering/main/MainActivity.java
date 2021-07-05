package com.blogcorel.bakulcatering.main;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.model.UserModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    Toast backToast;
    private long backPressedTime;
    UserModel um;
    Button menu_login, btn_catering, menu_logout;
    RelativeLayout menu_before, menu_after;
    TextView menu_username;
    String value, url, email, token;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String TEXT = "text";
    ProgressDialog dialog;
    ConfigServices cs = new ConfigServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_order, R.id.navigation_favorit)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View main = findViewById(R.id.container);
            int flags = main.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            main.setSystemUiVisibility(flags);
            this.getWindow().setStatusBarColor(Color.WHITE);
            this.getWindow().setNavigationBarColor(Color.WHITE);
        }

        um = new UserModel();

        btn_catering = findViewById(R.id.btn_catering);

        if(um.getLevel() == 1){
            btn_catering.setText("Buka Catering");
        }else if (um.getLevel() == 2){
            btn_catering.setText("Catering Saya");
        }else{
            btn_catering.setText("Buka Catering");
        }

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView nav_view = findViewById(R.id.nav_drawer);
        final View nav_header = nav_view.getHeaderView(0);

        menu_login = nav_header.findViewById(R.id.menu_login);
        menu_after = nav_header.findViewById(R.id.menu_after);
        menu_before = nav_header.findViewById(R.id.menu_before);
        menu_username = nav_header.findViewById(R.id.menu_username);
        menu_logout = nav_header.findViewById(R.id.menu_logout);

        menu_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.LEFT);
                Handler hh = new Handler();
                hh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                    }
                },200);
            }
        });
        menu_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(Gravity.LEFT);
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Logout")
                        .setMessage("Anda yakin ingin logout ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                new deleteToken().execute();
                            }})
                        .setNegativeButton("No", null).show();
            }
        });

        ImageButton menu = findViewById(R.id.menu_btn);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler hh = new Handler();
                hh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(TextUtils.isEmpty(um.getUser())){
                            menu_before.setVisibility(View.VISIBLE);
                            menu_after.setVisibility(View.GONE);
                            drawer.openDrawer(Gravity.LEFT);
                        } else {
                            menu_before.setVisibility(View.GONE);
                            menu_after.setVisibility(View.VISIBLE);
                            drawer.openDrawer(Gravity.LEFT);
                            menu_username.setText(um.getUsername());
                        }
                    }
                },200);
            }
        });

        btn_catering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(um.getLevel() == 1){
                    Intent i = new Intent(MainActivity.this, BukaActivity.class);
                    startActivity(i);
                    finish();
                }else if(um.getLevel() == 2){
                    Intent i = new Intent(MainActivity.this, ManageActivity.class);
                    startActivity(i);
                    finish();
                }else{
                    backToast = Toast.makeText(MainActivity.this,"Silahkan Login Terlebih Dahulu", Toast.LENGTH_SHORT);
                    backToast.show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 >System.currentTimeMillis()){
            backToast.cancel();
            this.finish();
            return;
        }else{
            backToast = Toast.makeText(this,"Press back again to exit", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime = System.currentTimeMillis();
    }

    private void logout(){
        FirebaseAuth.getInstance().signOut();
        um.setUser("");
        um.setUsername("");
        um.setLevel(0);
        dialog.dismiss();
        startActivity(getIntent());
        finish();
    }
    private class deleteToken extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("Memproses Permintaan");
            dialog.setCancelable(false);
            dialog.show();

            email = um.getUser();
            SharedPreferences sp = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
            token = sp.getString(TEXT, "");
            System.out.println(token);

            url = cs.getUrl()+"deletedevice.php";
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
                Log.e("Error", "Couldn't get json from server.");
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
                logout();
            }
        }
    }
}
