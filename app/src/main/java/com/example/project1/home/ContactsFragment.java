package com.example.project1.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.project1.Contacts;
import com.example.project1.Contacts_RecyclerViewAdapter;
import com.example.project1.R;
import com.example.project1.addContactsActivity;
import com.example.project1.homeActivity;
import com.example.project1.seeContactsActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class ContactsFragment extends Fragment {

    private RecyclerView recyclerView;


    public final static String TAG = "seeContacts";
   public static Contacts_RecyclerViewAdapter adapter;
    int userId;



    public ContactsFragment() {
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
        return inflater.inflate(R.layout.fragment_contacts, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        homeActivity activity = (homeActivity) getActivity();
        adapter = new Contacts_RecyclerViewAdapter(getContext(),activity.contacts);


        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        if (!activity.contactsIsDownloaded){

            Log.d(TAG, "onViewCreated: veri indir");
            activity.getDataFromFireBase();
            adapter.notifyDataSetChanged();
        }



    }









    //from json
//
//    private void veriGeldi(JSONObject response){
//        try {
//            JSONArray jsonArray = response.getJSONArray("employees");
//            homeActivity activity = (homeActivity) getActivity();
//            activity.contacts.clear();
//
//            for (int i = 0; i < jsonArray.length(); i++ ){
//                JSONObject employee = jsonArray.getJSONObject(i);
//
//
//                String firstName = employee.getString("firstname");
//                String lastname = employee.getString("lastname");
//                int age = employee.getInt("age");
//                int id = employee.getInt("id");
//                String mail = employee.getString("mail");
//                String urlImage = employee.getString("urlimage");
//
//                activity.contacts.add(new Contacts(firstName,lastname,urlImage,age,id,mail,"A","b"));
//
//            }
//            adapter.notifyDataSetChanged();
//            activity.contactsIsDownloaded = true;
//
//
//        } catch (JSONException e) {
//            Log.d(TAG, "onResponse: json hata");
//            e.printStackTrace();
//        }
//    }
//
//
//    public void jsonParse(){
//
//        //contacts.add(new Contacts("ESAD","DOGAN",23,"ARE@GMAIL.COM"));
//        //contacts.add(new Contacts("ESAD","DOGAN",23,"ARE@GMAIL.COM"));
//
//        String url = "https://raw.githubusercontent.com/EsadDogan/projectJson/main/db.json";
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//
//               // ContactsFragment fragment = (ContactsFragment) getSupportFragmentManager().findFragmentByTag(ContactsFragment.TAG);
////
////                if (fragment != null)
////                {
////                    fragment.veriGeldi(response);
////                }
//
//                veriGeldi(response);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                error.printStackTrace();
//            }
//        });{
//
//        }


//        homeActivity activity = (homeActivity) getActivity();
//
//        if (activity != null){
//
//            activity.mQueue.add(request);
//
//        }
//
//
//
//
//    }


//    public void callSeeContactsDetail(){
//
//        homeActivity activity = (homeActivity) getActivity();
//        FragmentManager manager2 = activity.getSupportFragmentManager();
//        manager2.beginTransaction()
//                .replace(R.id.myFrameLay, new SeeContactDetailsFragment(), SeeContactDetailsFragment.TAG)
//                .addToBackStack(null)
//                .commit();
//
//    }

}