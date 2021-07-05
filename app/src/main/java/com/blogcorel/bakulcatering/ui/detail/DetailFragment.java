package com.blogcorel.bakulcatering.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.blogcorel.bakulcatering.R;
import com.blogcorel.bakulcatering.model.DetailCateringModel;

public class DetailFragment extends Fragment {

    DetailCateringModel dcm = new DetailCateringModel();
    TextView dc_desc, dc_city, dc_address;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.detail_fragment, container, false);

        dc_desc = root.findViewById(R.id.dc_desc);
        dc_city = root.findViewById(R.id.dc_city);
        dc_address = root.findViewById(R.id.dc_address);

        dc_desc.setText(dcm.getDesc());
        dc_city.setText(dcm.getCity());
        dc_address.setText(dcm.getAddress());

        return root;
    }


}
