package com.blogcorel.bakulcatering.ui.menu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.adapter.dc_menu.MenuAdapter;
import com.blogcorel.bakulcatering.adapter.menu_manage.MenuManage;
import com.blogcorel.bakulcatering.main.ConfigServices;
import com.blogcorel.bakulcatering.main.HttpHandler;
import com.blogcorel.bakulcatering.main.OrderActivity;
import com.blogcorel.bakulcatering.model.DetailCateringModel;
import com.blogcorel.bakulcatering.model.FavoriteModel;
import com.blogcorel.bakulcatering.model.MenuModel;
import com.blogcorel.bakulcatering.model.OrderModel;
import com.blogcorel.bakulcatering.model.ProcessModel;
import com.blogcorel.bakulcatering.model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    RecyclerView dc_list_menu;
    LinearLayout pbm;
    private List<MenuModel> model;
    private List<OrderModel> item = new ArrayList<>();
    private List<FavoriteModel> flist = new ArrayList<>();
    OrderModel om;
    MenuAdapter ma;
    FavoriteModel fm;
    LinearLayoutManager llm;
    MenuModel menuModel;
    ProcessModel pm = new ProcessModel();
    UserModel um = new UserModel();
    DetailCateringModel dcm;
    String url;
    Button order;
    ConfigServices cs = new ConfigServices();

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.menu_fragment, container, false);

        dc_list_menu = root.findViewById(R.id.dc_list_menu);
        pbm = root.findViewById(R.id.pbm);
        order = root.findViewById(R.id.order);

        dc_list_menu.setVisibility(View.GONE);
        pbm.setVisibility(View.VISIBLE);

        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pm.getHarga() == 0) {
                    Toast.makeText(getContext(), "Pilih Menu Terlebih Dahulu", Toast.LENGTH_LONG).show();
                } else {
                    pm.setOwner(dcm.getOwner());
                    pm.setEmail(um.getUser());
                    pm.setNama(um.getUsername());
                    startActivity(new Intent(getContext(), OrderActivity.class));
                }
            }
        });

        model = new ArrayList<>();

        ma = new MenuAdapter(getContext(), model, item);
        llm = new LinearLayoutManager(getContext());

        new getFav().execute();

        return root;
    }

    private class getFav extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            url = cs.getUrl() + "getfavmenu.php?email=" + um.getUser();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("result");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        fm = new FavoriteModel();
                        JSONObject c = contacts.getJSONObject(i);
                        fm.setId(c.getInt("id"));
                        fm.setEmail(c.getString("email"));
                        fm.setId_menu(c.getInt("id_menu"));

                        flist.add(fm);

                    }
                } catch (final JSONException e) {

                }
            } else {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            new getMenu().execute();
        }

    }

    private class getMenu extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            url = cs.getUrl() + "getmenu.php?owner=" + dcm.getOwner();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);


            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("result");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        menuModel = new MenuModel();
                        JSONObject c = contacts.getJSONObject(i);
                        menuModel.setM_name(c.getString("menu"));
                        menuModel.setM_price(c.getInt("harga"));
                        menuModel.setM_portion(c.getInt("porsi"));
                        menuModel.setM_fee(c.getInt("pajak"));
                        menuModel.setM_order(c.getInt("jum_order"));
                        menuModel.setM_link1(c.getString("link1"));
                        menuModel.setM_link2(c.getString("link2"));
                        menuModel.setM_link3(c.getString("link3"));
                        menuModel.setId(c.getInt("id"));
                        menuModel.setCheck(false);
                        System.out.println("id = "+c.getInt("id"));
                        System.out.println("state : "+menuModel.notFav());

                        om = new OrderModel();
                        om.setMenu(c.getString("menu"));
                        om.setHarga(c.getInt("harga"));
                        om.setPorsi(c.getInt("porsi"));
                        om.setPajak(c.getInt("pajak"));
                        om.setTotal(0);

                        model.add(menuModel);
                        item.add(om);

                        ma.notifyDataSetChanged();

                    }
                } catch (final JSONException e) {

                }
            } else {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            for (int i = 0; i<model.size(); i++){
                for (int o = 0; o < flist.size(); o++) {
                    if (flist.get(o).getId_menu() == model.get(i).getId()) {
                        model.get(i).setFav(true);
                    }
                }
            }
            dc_list_menu.setLayoutManager(llm);
            dc_list_menu.setAdapter(ma);
            dc_list_menu.setVisibility(View.VISIBLE);
            pbm.setVisibility(View.GONE);
        }

    }

}
