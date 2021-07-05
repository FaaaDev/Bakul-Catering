package com.blogcorel.bakulcatering.main;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class AddFavorite {

    String email, table, url, value;
    int id;
    Context context;
    ConfigServices cs = new ConfigServices();

    public AddFavorite(String email, int id, String table, Context context) {
        this.email = email;
        this.id = id;
        this.table = table;
        this.context = context;
        new addFavorite().execute();
    }

    private class addFavorite extends AsyncTask<Void, Void, Void>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            url =cs.getUrl()+"addfavorite.php";
        }

        @Override
        protected Void doInBackground(Void... voids) {
            PostHandler sh = new PostHandler();

            JSONObject data = new JSONObject();
            try {
                data.put("table", table);
                data.put("id_menu", id);
                data.put("email", email);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String jsonStr = sh.makeServiceCall(url, data);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    value = jsonObj.getString("value");

                } catch (final JSONException e) {
                    Log.e("Error", e.toString());
                }
            } else {
                Log.e("Error", "Couldn't get json from server.");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            if ("S1".equals(value)){
                Toast.makeText(context, "Berhasil Menambahkan Favorit", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "Gagal Menambahkan Favorit", Toast.LENGTH_LONG).show();
            }
        }
    }
}
