package com.example.project1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.project1.home.AddContactFragment;
import com.example.project1.home.BlankFragment;
import com.example.project1.home.ContactsFragment;
import com.example.project1.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firestore.v1.Document;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class homeActivity extends AppCompatActivity {



    public BottomNavigationView bottomNavigationView;
    FrameLayout myFrame;
    public RequestQueue mQueue;
    public String mesaj;
    public int odul;
    public ArrayList<Contacts> contacts = new ArrayList<>();
    public boolean contactsIsDownloaded = false;

    public static String TAG = "home activity";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mQueue = Volley.newRequestQueue(this);

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.home);





//        Intent intent2 = getIntent();
//             try {
//                 if (intent2 != null){
//
//                     String name = intent2.getExtras().getString("name");
//                     Log.d("TAG", "onCreate: "+name+" alindi  ");
//                 }
//             }catch (Exception ex){
//                 ex.printStackTrace();
//             }




        try {
            getDataFromFireBase();
            ContactsFragment.adapter.notifyDataSetChanged();
        }catch (Exception e)
        {
            Log.d(TAG, "onCreate: error " + e.getMessage());
        }



        bottomNavigationView.setOnItemSelectedListener(item -> {

            Intent intent;

            switch (item.getItemId()){

                case R.id.home:

                    goHome();
                    break;

                case R.id.contacts:

                    FragmentManager manager = getSupportFragmentManager();
//                    manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    manager.beginTransaction()
                            .replace(R.id.myFrameLay, new ContactsFragment(), ContactsFragment.TAG)
                            .addToBackStack(null)
                            .commit();
                    //intent = new Intent(homeActivity.this , seeContactsActivity.class);
                    //startActivity(intent);
                    break;
                case R.id.add:
//                     intent = new Intent(homeActivity.this , addContactsActivity.class);
//                    startActivity(intent);

//                    FragmentManager manager2 = getSupportFragmentManager();
////                    manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                    manager2.beginTransaction()
//                            .replace(R.id.myFrameLay, BlankFragment.newInstance("esad","asd"), "tag")
//                            .addToBackStack(null)
//                            .commit();
                    AddContactFragment fragment = new AddContactFragment();
                    Bundle args = new Bundle();
                    args.putString("param1", "dogan");
                    args.putString("param2", "tt");
                    fragment.setArguments(args);
                  //  FrgmentHelper.setFragment(this,BlankFragment.newInstance("esad","asd"),"s" );
                  //  FrgmentHelper.setFragment(this,fragment,AddContactFragment.TAG );
                    // with that bottom part we call new fragment and without back button crowd
                    FragmentManager manager2 = getSupportFragmentManager();
                    manager2.beginTransaction()
                            .replace(R.id.myFrameLay, new AddContactFragment(), AddContactFragment.TAG)
                            .addToBackStack(null)
                            .commit();
                    break;

                default: break;
            }


            return true;
        });


        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.myFrameLay, new HomeFragment(), HomeFragment.TAG)
                .commit();

        json();

    }

    private void goHome(){

        FragmentManager manager = getSupportFragmentManager();
        //manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        manager.beginTransaction()
                .replace(R.id.myFrameLay, new HomeFragment(), HomeFragment.TAG)
                .addToBackStack(null)
                .commit();
    }


    /**
     * this method gets all the document from collection within realtime. it listens the if the documents are changed or not if it changed then getting all documents.
     */
    //from firebase
    public void getDataFromFireBase(){

        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("contactsDatas").orderBy("id").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null){

                    Log.d(TAG, "onEvent: " + error.getMessage());
                    return;
                }

                for (DocumentChange dc : value.getDocumentChanges()){

                 //   StorageReference storageReference;

                   // storageReference = FirebaseStorage.getInstance().getReference();

                    String firstName = dc.getDocument().getString("firstname");
                    String lastname = dc.getDocument().getString("lastname");
                    int age = dc.getDocument().getLong("age").intValue();
                    int id = dc.getDocument().getLong("id").intValue();
                    String mail = dc.getDocument().getString("mail");
                    String urlImage = dc.getDocument().getString("urlImage");
                    String gender = dc.getDocument().getString("gender");
                    String country = dc.getDocument().getString("country");
                  //  String url = String.valueOf(storageReference.child("uploads/"+String.valueOf(id)+".jpeg").getDownloadUrl())

                    ;

                    contacts.add(new Contacts(firstName,lastname,urlImage,age,id,mail,gender,country));



                }



                contactsIsDownloaded = true;


                Log.d(TAG, "onSuccess: veri geldi");


            }
        });




    }

    /**
     * this method gets all the document from collection but its not realtime that means when data changes we cant take new dataset with this method
     * @param response
     */

//    //from firebase
//    public void getDataFromFireBase(){
//
//        FirebaseFirestore db = FirebaseFirestore.getInstance();
//
//        db.collection("contactsDatas")
//                .orderBy("id")
//                //.whereEqualTo("capital", true)
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot dc : task.getResult()) {
//
//                                String firstName = dc.getString("firstname");
//                                String lastname = dc.getString("lastname");
//                                int age = dc.getLong("age").intValue();
//                                int id = dc.getLong("id").intValue();
//                                String mail = dc.getString("mail");
//                                String urlImage = dc.getString("urlImage");
//                                String gender = dc.getString("gender");
//                                String country = dc.getString("country");
//
//                                ;
//
//                                contacts.add(new Contacts(firstName,lastname,urlImage,age,id,mail,gender,country));
//                            }
//
//                            contactsIsDownloaded = true;
//
//
//                            Log.d(TAG, "onSuccess: veri geldi");
//
//                        } else {
//                            Log.d(TAG, "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
//
//
//        }







    private void veriGeldi(JSONObject response){


        try {

             mesaj = response.getString("mesaj");
             odul = response.getInt("odul");



        } catch (JSONException e) {
            Log.d("TAG", "onResponse: json hata");
            e.printStackTrace();
            return;
        }

        HomeFragment fragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag(HomeFragment.TAG);

        if (fragment != null)
        {
            fragment.getData(mesaj);
            Log.d("TAG", "veriGeldi: 1 ");
        }
        else {
            Log.d("TAG", "veriGeldi: 2 ");
        }

    }

    private void json(){



        String url = "https://raw.githubusercontent.com/EsadDogan/projectJson/main/ayar.json";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                // ContactsFragment fragment = (ContactsFragment) getSupportFragmentManager().findFragmentByTag(ContactsFragment.TAG);
//
//                if (fragment != null)
//                {
//                    fragment.veriGeldi(response);
//                }

                veriGeldi(response);
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