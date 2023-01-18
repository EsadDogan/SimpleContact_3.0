package com.example.project1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class seeContactsActivity extends AppCompatActivity {


    private BottomNavigationView bottomNavigationView;
    private RequestQueue mQueue;
    ArrayList<Contacts> contacts = new ArrayList<>();
    private final static String TAG = "seeContactActivity";
    Contacts_RecyclerViewAdapter adapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_contacts);
        mQueue = Volley.newRequestQueue(this);


        //RecyclerView recyclerView = findViewById(R.id.recyclerView);

        jsonParse();


         adapter = new Contacts_RecyclerViewAdapter(this,contacts);


        //recyclerView.setAdapter(adapter);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));


        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.contacts);



        bottomNavigationView.setOnItemSelectedListener(item -> {

            Intent intent;

            switch (item.getItemId()){

                case R.id.home:
                    intent = new Intent(seeContactsActivity.this, homeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name","sadad");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;

                    case R.id.add:

                        intent = new Intent(seeContactsActivity.this, addContactsActivity.class);
                        startActivity(intent);
                        break;

                default: break;

            }

            return true;
        });



    }



    public void jsonParse(){

        //contacts.add(new Contacts("ESAD","DOGAN",23,"ARE@GMAIL.COM"));
        //contacts.add(new Contacts("ESAD","DOGAN",23,"ARE@GMAIL.COM"));

        String url = "https://raw.githubusercontent.com/EsadDogan/projectJson/main/db.json";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("employees");



                    for (int i = 0; i < jsonArray.length(); i++ ){
                        JSONObject employee = jsonArray.getJSONObject(i);


                        String firstName = employee.getString("firstname");
                        String lastname = employee.getString("lastname");
                        int age = employee.getInt("age");
                        String mail = employee.getString("mail");

                        contacts.add(new Contacts(firstName,lastname,age,mail));

                    }
                  adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    Log.d(TAG, "onResponse: json hata");
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
            }
        });{

        }

        mQueue.add(request);

    }
}