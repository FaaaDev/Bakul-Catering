package com.blogcorel.bakulcatering.main;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.blogcorel.bakulcatering.adapter.menu_manage.MenuManage;

import org.json.JSONException;
import org.json.JSONObject;

public class DeleteMenu {

    ProgressDialog dialog;
    String url, value;
    int id;
    ConfigServices cs = new ConfigServices();

    public DeleteMenu(Context context, int id) {
        this.id = id;
        dialog = new ProgressDialog(context);
        dialog.setMessage("Memproses Permintaan");
        dialog.setCancelable(false);
        new deleteMenu().execute();
    }

    private class deleteMenu extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
            url = cs.getUrl()+"deletemenu.php";
        }

        @Override
        protected Void doInBackground(Void... voids) {
            PostHandler sh = new PostHandler();

            JSONObject data = new JSONObject();
            try {
                data.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String jsonStr = sh.makeServiceCall(url, data);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    value = jsonObj.getString("value");

                } catch (final JSONException e) {


                }
            } else {
                Log.e("Error", "Couldn't get json from server.");


            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
        }
    }
}
