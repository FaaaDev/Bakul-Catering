package com.blogcorel.bakulcatering.ui.order;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.adapter.user_order.UserOrder;
import com.blogcorel.bakulcatering.main.ConfigServices;
import com.blogcorel.bakulcatering.main.HttpHandler;
import com.blogcorel.bakulcatering.main.LoginActivity;
import com.blogcorel.bakulcatering.model.OrderData;
import com.blogcorel.bakulcatering.model.UserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

    private OrderViewModel orderViewModel;
    UserModel um = new UserModel();
    String user = um.getUser();
    RelativeLayout order_warning;
    LinearLayout order_content, load, done, empty;
    Button order_login;
    RecyclerView orderlist;
    UserOrder userOrder;
    ConfigServices cs = new ConfigServices();
    List<OrderData> order = new ArrayList<>();
    OrderData orderData;
    LinearLayoutManager llm;
    String url;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        orderViewModel =
                ViewModelProviders.of(this).get(OrderViewModel.class);
        View root = inflater.inflate(R.layout.fragment_order, container, false);

        order_content = root.findViewById(R.id.order_content);
        order_warning = root.findViewById(R.id.order_warning);
        order_login = root.findViewById(R.id.order_login);
        orderlist = root.findViewById(R.id.orderlist);
        load = root.findViewById(R.id.load);
        done = root.findViewById(R.id.done);
        empty =  root.findViewById(R.id.empty);


        order_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        order_content.setVisibility(View.GONE);
        empty.setVisibility(View.GONE);
        done.setVisibility(View.GONE);


        if(!TextUtils.isEmpty(user)){
            order_content.setVisibility(View.VISIBLE);
            order_warning.setVisibility(View.GONE);
        }
        else {
            order_content.setVisibility(View.GONE);
            order_warning.setVisibility(View.VISIBLE);
        }

        userOrder = new UserOrder(getContext(), order);
        llm = new LinearLayoutManager(getContext());

        new getOrder().execute();

        return root;
    }

    private class getOrder extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            url = cs.getUrl()+"getorder.php?email="+user;

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
                        String catname = c.getString("catname");
                        String menu = c.getString("menu");
                        String username = c.getString("username");
                        String alamat = c.getString("alamat");
                        String hp = c.getString("hp");
                        String tpesan = c.getString("tpesan");
                        String tdipesan = c.getString("tdipesan");
                        String status = c.getString("status");
                        String id = c.getString("id");
                        int total = c.getInt("total");

                        orderData = new OrderData(id, catname, menu, username, alamat, hp, tpesan, tdipesan, status, "", total);

                        order.add(orderData);

                    }

                } catch (final JSONException e) {

                }
            } else {
                Log.e("error", "Couldn't get json from server.");

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            orderlist.setLayoutManager(llm);
            orderlist.setAdapter(userOrder);
            if (userOrder.getItemCount()==0){
                empty.setVisibility(View.VISIBLE);
                load.setVisibility(View.GONE);
            }else {
                done.setVisibility(View.VISIBLE);
                load.setVisibility(View.GONE);
            }

        }
    }
}