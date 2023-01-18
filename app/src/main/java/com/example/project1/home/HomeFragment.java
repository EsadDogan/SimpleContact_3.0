package com.example.project1.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.project1.Contacts;
import com.example.project1.R;
import com.example.project1.homeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class HomeFragment extends Fragment {

    public final static String TAG = "homeFr";
    TextView txtMesaj;


    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_asd, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        txtMesaj = view.findViewById(R.id.txtMesaj);


        homeActivity activity = (homeActivity) getActivity();

        // ilk acildigi zaman guncelliyor
        //txtMesaj.setText(activity.mesaj);


    }

    public void getData(String mesaj)
    {
        // yeni veri geldigi zaman guncelliyor
        //txtMesaj.setText(mesaj);
    }


}