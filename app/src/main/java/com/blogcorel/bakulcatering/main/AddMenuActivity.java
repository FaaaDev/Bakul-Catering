package com.blogcorel.bakulcatering.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.model.CateringModel;
import com.blogcorel.bakulcatering.model.UserModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

public class AddMenuActivity extends AppCompatActivity {
    private String TAG = AddMenuActivity.class.getSimpleName();

    ImageButton back_add,  caim1, caim2, caim3, add1, add2, add3;
    ImageView img1, img2, img3;
    ConstraintLayout add_menu_container;
    Button add_menu;

    Uri uri1, uri2, uri3;
    String link1, link2, link3;
    String c1, c2, c3;
    String email, url, value;
    EditText e_menu, e_price, e_portion, e_fee;
    String menu, catname;
    int harga, porsi, pajak;

    StorageReference sf;
    CateringModel cm;
    UserModel um;
    ConfigServices cs = new ConfigServices();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View main = findViewById(R.id.add_menu_container);
            int flags = main.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            main.setSystemUiVisibility(flags);
            this.getWindow().setStatusBarColor(Color.WHITE);
            this.getWindow().setNavigationBarColor(Color.WHITE);
        }

        cm = new CateringModel();
        um = new UserModel();

        catname = cm.getCatname();
        email = um.getUser();

        back_add = findViewById(R.id.back_add);
        img1 = findViewById(R.id.img1);
        img2 = findViewById(R.id.img2);
        img3 = findViewById(R.id.img3);
        caim1 = findViewById(R.id.caim1);
        caim2 = findViewById(R.id.caim2);
        caim3 = findViewById(R.id.caim3);
        add1 = findViewById(R.id.add1);
        add2 = findViewById(R.id.add2);
        add3 = findViewById(R.id.add3);
        e_menu = findViewById(R.id.e_menu);
        e_price = findViewById(R.id.e_price);
        e_portion = findViewById(R.id.e_portion);
        e_fee = findViewById(R.id.e_fee);
        add_menu_container = findViewById(R.id.add_menu_container);
        add_menu = findViewById(R.id.add_menu);

        caim1.setVisibility(View.GONE);
        caim2.setVisibility(View.GONE);
        caim3.setVisibility(View.GONE);

        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                AddMenuActivity.this.startActivityForResult(Intent.createChooser(intent, "Select Image"), 1);
            }
        });

        add2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                AddMenuActivity.this.startActivityForResult(Intent.createChooser(intent, "Select Image"), 2);
            }
        });

        add3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, false);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                AddMenuActivity.this.startActivityForResult(Intent.createChooser(intent, "Select Image"), 3);
            }
        });

        caim1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img1.setImageURI(null);
                uri1 = null;
                caim1.setVisibility(View.GONE);
                add1.setVisibility(View.VISIBLE);
                c1 = "";
            }
        });
        caim2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img2.setImageURI(null);
                uri2 = null;
                caim2.setVisibility(View.GONE);
                add2.setVisibility(View.VISIBLE);
                c2 = "";
            }
        });
        caim3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img3.setImageURI(null);
                uri3 = null;
                caim3.setVisibility(View.GONE);
                add3.setVisibility(View.VISIBLE);
                c3 = "";
            }
        });

        back_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        add_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addMenu();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK){
            uri1 = data.getData();
            img1.setImageURI(uri1);
            caim1.setVisibility(View.VISIBLE);
            add1.setVisibility(View.GONE);
            c1 = "ok";

        } else if (requestCode == 2 && resultCode == RESULT_OK){
            uri2 = data.getData();
            img2.setImageURI(uri2);
            caim2.setVisibility(View.VISIBLE);
            add2.setVisibility(View.GONE);
            c2 = "ok";

        } if (requestCode == 3 && resultCode == RESULT_OK){
            uri3 = data.getData();
            img3.setImageURI(uri3);
            caim3.setVisibility(View.VISIBLE);
            add3.setVisibility(View.GONE);
            c3 = "ok";

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(AddMenuActivity.this, ManageActivity.class);
        startActivity(i);
        finish();


    }

    private void addMenu(){
        if(TextUtils.isEmpty(e_menu.getText()) || TextUtils.isEmpty(e_price.getText())
                ||TextUtils.isEmpty(e_portion.getText()) || TextUtils.isEmpty(e_fee.getText())){

            if(TextUtils.isEmpty(e_menu.getText())){
                e_menu.setError("Nama menu harus diisi");
            }
            if(TextUtils.isEmpty(e_price.getText())){
                e_price.setError("Silahkan isi harga");
            }
            if(TextUtils.isEmpty(e_portion.getText())){
                e_portion.setError("Porsi harus diisi");
            }
            if(TextUtils.isEmpty(e_fee.getText())){
                e_fee.setError("Biaya penanganan harus diisi");
            }

        } else {
            menu = e_menu.getText().toString();
            harga = Integer.parseInt(e_price.getText().toString());
            porsi = Integer.parseInt(e_portion.getText().toString());
            pajak = Integer.parseInt(e_fee.getText().toString());
            upload1();
            add_menu.setEnabled(false);
            back_add.setEnabled(false);
            caim1.setEnabled(false);
            caim2.setEnabled(false);
            caim3.setEnabled(false);
        }

    }

    private void upload1(){
        if (uri1!=null){
            Snackbar snackbar = Snackbar
                    .make(add_menu_container, "Mengupload gambar 1", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
            sf = FirebaseStorage.getInstance().getReference().child("Menu").child(catname+uri1.getLastPathSegment());
            sf.putFile(uri1)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            sf.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    link1 = uri.toString();
                                    upload2();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            add_menu.setEnabled(true);
                            back_add.setEnabled(true);
                            caim1.setEnabled(true);
                            caim2.setEnabled(true);
                            caim3.setEnabled(true);
                            Toast.makeText(AddMenuActivity.this, exception.getCause().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
        } else{
            link1 = "";
            upload2();
        }
    }

    private void upload2(){
        if (uri2!=null){
            Snackbar snackbar = Snackbar
                    .make(add_menu_container, "Mengupload gambar 2", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
            sf = FirebaseStorage.getInstance().getReference().child("Menu").child(catname+uri2.getLastPathSegment());
            sf.putFile(uri2)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            sf.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    link2 = uri.toString();
                                    upload3();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            add_menu.setEnabled(true);
                            back_add.setEnabled(true);
                            caim1.setEnabled(true);
                            caim2.setEnabled(true);
                            caim3.setEnabled(true);
                            Toast.makeText(AddMenuActivity.this, exception.getCause().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
        }else{
            link2 = "";
            upload3();
        }
    }

    private void upload3(){
        if (uri3!=null){
            Snackbar snackbar = Snackbar
                    .make(add_menu_container, "Mengupload gambar 3", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
            sf = FirebaseStorage.getInstance().getReference().child("Menu").child(catname+uri3.getLastPathSegment());
            sf.putFile(uri3)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            sf.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    link3 = uri.toString();
                                    new addMenu().execute();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            add_menu.setEnabled(true);
                            back_add.setEnabled(true);
                            caim1.setEnabled(true);
                            caim2.setEnabled(true);
                            caim3.setEnabled(true);
                            Toast.makeText(AddMenuActivity.this, exception.getCause().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
        } else {
            link3 = "";
            new addMenu().execute();
        }
    }

    private class addMenu extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Snackbar snackbar = Snackbar
                    .make(add_menu_container, "Menyimpan Menu", Snackbar.LENGTH_LONG);
            snackbar.show();

            email = um.getUser();

            url = cs.getUrl()+"postmenu.php";
        }
        @Override
        protected Void doInBackground(Void... voids) {
            PostHandler sh = new PostHandler();

            JSONObject data = new JSONObject();
            try {
                data.put("owner", email);
                data.put("menu", menu);
                data.put("harga", harga);
                data.put("porsi", porsi);
                data.put("pajak", pajak);
                data.put("link1", link1);
                data.put("link2", link2);
                data.put("link3", link3);
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
                Snackbar snackbar = Snackbar
                        .make(add_menu_container, "Berhasil Menambahkan Menu", Snackbar.LENGTH_LONG);
                snackbar.show();
                Handler hh = new Handler();
                hh.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                },500);
            }else {
                add_menu.setEnabled(true);
                back_add.setEnabled(true);
                caim1.setEnabled(true);
                caim2.setEnabled(true);
                caim3.setEnabled(true);
                Snackbar snackbar = Snackbar
                        .make(add_menu_container, "Gagal menambahkan, coba lagi nanti", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }
}
