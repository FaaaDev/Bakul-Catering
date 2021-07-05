package com.blogcorel.bakulcatering.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.adapter.most_adapter.CardPagerAdapter;
import com.blogcorel.bakulcatering.adapter.most_adapter.MostFragmentPageAdapter;
import com.blogcorel.bakulcatering.adapter.most_adapter.MostTransformer;
import com.blogcorel.bakulcatering.adapter.new_catering.NewCatering;
import com.blogcorel.bakulcatering.adapter.popular_catering.PopularCatering;
import com.blogcorel.bakulcatering.adapter.popular_adapter.PopularAdapter;
import com.blogcorel.bakulcatering.main.ConfigServices;
import com.blogcorel.bakulcatering.main.HttpHandler;
import com.blogcorel.bakulcatering.main.MainActivity;
import com.blogcorel.bakulcatering.model.CateringModel;
import com.blogcorel.bakulcatering.model.FavoriteModel;
import com.blogcorel.bakulcatering.model.MenuModel;
import com.blogcorel.bakulcatering.model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ViewPager mViewPager;

    private CardPagerAdapter mCardAdapter;
    private MostTransformer mCardShadowTransformer;
    private MostFragmentPageAdapter mFragmentCardAdapter;
    private MostTransformer mFragmentCardShadowTransformer;

    MenuModel menuModel;
    CateringModel cm;
    RecyclerView pop_menu, pop_cat, new_cat;
    private List<MenuModel> model;
    private List<CateringModel> camod, camod1;
    private List<FavoriteModel> flist = new ArrayList<>();
    FavoriteModel fm;
    PopularAdapter la;
    PopularCatering pc;
    NewCatering nc;
    ConfigServices cs = new ConfigServices();
    UserModel um = new UserModel();
    LinearLayoutManager llm, llm2, llm3;

    ConstraintLayout home;
    LinearLayout pd1, pd2, pd3, pd4, box1, box2, box3, box4;

    String url;
    private String TAG = MainActivity.class.getSimpleName();

    @SuppressLint("WrongConstant")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        mViewPager = root.findViewById(R.id.mostVP);
        pop_menu = root.findViewById(R.id.pop_menu);
        pop_cat = root.findViewById(R.id.pop_cat);
        new_cat = root.findViewById(R.id.new_cat);
        pd1 = root.findViewById(R.id.pd1);
        pd2 = root.findViewById(R.id.pd2);
        pd3 = root.findViewById(R.id.pd3);
        pd4 = root.findViewById(R.id.pd4);
        box1 = root.findViewById(R.id.box1);
        box2 = root.findViewById(R.id.box2);
        box3 = root.findViewById(R.id.box3);
        box4 = root.findViewById(R.id.box4);
        home = root.findViewById(R.id.container_home);

        home.setVisibility(View.VISIBLE);
        pd1.setVisibility(View.VISIBLE);
        pd2.setVisibility(View.VISIBLE);
        pd3.setVisibility(View.VISIBLE);
        pd4.setVisibility(View.VISIBLE);
        box1.setVisibility(View.GONE);
        box2.setVisibility(View.GONE);
        box3.setVisibility(View.GONE);
        box4.setVisibility(View.GONE);

        menuModel = new MenuModel();
        cm = new CateringModel();
        model = new ArrayList<>();
        camod = new ArrayList<>();
        camod1 = new ArrayList<>();

        la = new PopularAdapter(getContext(), model);
        pc = new PopularCatering(getContext(), camod);
        nc = new NewCatering(getContext(), camod1);
        llm = new LinearLayoutManager(getContext());
        llm2 = new LinearLayoutManager(getContext(), LinearLayout.HORIZONTAL, false);
        llm3 = new LinearLayoutManager(getContext());

        mCardAdapter = new CardPagerAdapter();

        new getFavMenu().execute();

        return root;
    }

    @Override
    public void onDestroyView() {
        home.setVisibility(View.GONE);
        super.onDestroyView();
    }

    public static float dpToPixels(int dp, Context context) {
        return dp * (context.getResources().getDisplayMetrics().density);
    }

    private class getFavMenu extends AsyncTask<Void, Void, Void> {

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
            new getNewMenu().execute();
        }

    }

    private class getNewMenu extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            url = cs.getUrl()+"getmostmenu.php";

        }

        @SuppressLint("WrongThread")
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

                        String owner = c.getString("owner");
                        String menu = c.getString("menu");
                        int harga = c.getInt("harga");
                        int porsi = c.getInt("porsi");
                        int pajak = c.getInt("pajak");
                        int jum_order = c.getInt("jum_order");
                        String link1 = c.getString("link1");
                        String link2 = c.getString("link2");
                        String link3 = c.getString("link3");
                        int id = c.getInt("id");
                        boolean fav=false;
                        for (int o = 0; o < flist.size(); o++) {
                            if (flist.get(o).getId_menu() == id) {
                                fav = true;
                            }
                        }


                        mCardAdapter.addCardItem(new MenuModel(owner, menu, link1, link2, link3, harga, porsi, pajak, jum_order, id, fav));

                    }

                } catch (final JSONException e) {

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //tambahin validasi u/ check data kosong atau tidak
            mFragmentCardAdapter = new MostFragmentPageAdapter(getFragmentManager(),
                    dpToPixels(2, getContext()));
            mCardShadowTransformer = new MostTransformer(mViewPager, mCardAdapter);
            mFragmentCardShadowTransformer = new MostTransformer(mViewPager, mFragmentCardAdapter);
            mCardShadowTransformer.enableScaling(true);

            mViewPager.setAdapter(mCardAdapter);
            mViewPager.setPageTransformer(false, mCardShadowTransformer);
            mViewPager.setOffscreenPageLimit(3);
            new getPopMenu().execute();
            pd1.setVisibility(View.GONE);
            box1.setVisibility(View.VISIBLE);
        }
    }

    private class getPopMenu extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            url = cs.getUrl()+"getpopmenu.php";

        }

        @SuppressLint("WrongThread")
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

                        String owner = c.getString("owner");
                        String menu = c.getString("menu");
                        int harga = c.getInt("harga");
                        int porsi = c.getInt("porsi");
                        int pajak = c.getInt("pajak");
                        int jum_order = c.getInt("jum_order");
                        String link1 = c.getString("link1");
                        String link2 = c.getString("link2");
                        String link3 = c.getString("link3");
                        int id = c.getInt("id");
                        boolean fav=false;
                        for (int o = 0; o < flist.size(); o++) {
                            if (flist.get(o).getId_menu() == id) {
                                fav = true;
                            }
                        }
                        menuModel = new MenuModel(owner, menu, link1, link2, link3, harga, porsi, pajak, jum_order, id, fav);

                        model.add(menuModel);

                        la.notifyDataSetChanged();
                    }

                } catch (final JSONException e) {

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pop_menu.setLayoutManager(llm);
            pop_menu.setAdapter(la);
            new getPopCat().execute();
            pd2.setVisibility(View.GONE);
            box2.setVisibility(View.VISIBLE);
        }
    }

    private class getPopCat extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            url = cs.getUrl()+"getpopcat.php";

        }

        @SuppressLint("WrongThread")
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
                        String owner = c.getString("owner");
                        String catname = c.getString("catname");
                        String link = c.getString("link");
                        String address = c.getString("address");
                        String city = c.getString("city");
                        String desc = c.getString("desc");
                        int order = c.getInt("total");

                        cm = new CateringModel(owner, catname, link, address, city, desc, order);
                        camod.add(cm);

                        pc.notifyDataSetChanged();
                    }

                } catch (final JSONException e) {

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            pop_cat.setLayoutManager(llm2);
            pop_cat.setAdapter(pc);
            new getNewCat().execute();
            pd3.setVisibility(View.GONE);
            box3.setVisibility(View.VISIBLE);
        }
    }

    private class getNewCat extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            url = cs.getUrl()+"getnewcat.php";

        }

        @SuppressLint("WrongThread")
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
                        String owner = c.getString("owner");
                        String catname = c.getString("catname");
                        String link = c.getString("link");
                        String address = c.getString("address");
                        String city = c.getString("city");
                        String desc = c.getString("desc");
                        int order = c.getInt("total");

                        cm = new CateringModel(owner, catname, link, address, city, desc, order);
                        camod1.add(cm);

                        nc.notifyDataSetChanged();
                    }

                } catch (final JSONException e) {

                }
            } else {
                Log.e(TAG, "Couldn't get json from server.");

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            new_cat.setLayoutManager(llm3);
            new_cat.setAdapter(nc);
            pd4.setVisibility(View.GONE);
            box4.setVisibility(View.VISIBLE);
        }
    }
}