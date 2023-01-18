package com.example.project1.home;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.project1.Contacts_RecyclerViewAdapter;
import com.example.project1.R;
import com.example.project1.homeActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SeeContactDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SeeContactDetailsFragment extends Fragment {

    public final static String TAG = "seeContactDetails";
    String contactId;
    TextView txtdeneme,txtNameLastname,txtAge,txtMail,txtCountry,txtSex;
    ImageView imageView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SeeContactDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SeeContactDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SeeContactDetailsFragment newInstance(String param1, String param2) {
        SeeContactDetailsFragment fragment = new SeeContactDetailsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_see_contact_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txtdeneme = view.findViewById(R.id.txtUserIdDetail);
        txtNameLastname  = view.findViewById(R.id.txtNameSurnameDetail);
        txtAge = view.findViewById(R.id.txtAgeDetail);
        txtMail = view.findViewById(R.id.txtMailDetail);
        txtCountry = view.findViewById(R.id.txtCountryDetail);
        txtSex = view.findViewById(R.id.txtSexDetail);
        imageView = view.findViewById(R.id.imageDetail);

       contactId = Contacts_RecyclerViewAdapter.itemId;
       txtdeneme.setText(contactId);

       getData();




    }


    private void getData(){

        int arrayNumber = Integer.parseInt(contactId) - 1 ;
        homeActivity activity = (homeActivity) getActivity();


        txtNameLastname.setText(activity.contacts.get(arrayNumber).getName()+" "+activity.contacts.get(arrayNumber).getLastName());
        txtAge.setText("Age: "+String.valueOf(activity.contacts.get(arrayNumber).getAge()));
        txtMail.setText("Mail: "+activity.contacts.get(arrayNumber).getMail());
        txtSex.setText("Gender: "+activity.contacts.get(arrayNumber).getGender() );
        txtCountry.setText("Country: "+activity.contacts.get(arrayNumber).getCountry());

//        Glide.with(getView())
//                .asBitmap()
//                .load(activity.contacts.get(arrayNumber).getUrlImage()).into(imageView);

        StorageReference storageReference;

        storageReference = FirebaseStorage.getInstance().getReference();



        storageReference.child("uploads/"+activity.contacts.get(arrayNumber).getIdAsString()+".jpeg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(getView()).asBitmap().load(uri).centerCrop().into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "onFailure: couldnt load image " + e.getMessage());
                Log.d("TAG", "onFailure: " +"uploads/"+activity.contacts.get(arrayNumber).getIdAsString());
            }
        });

    }
}