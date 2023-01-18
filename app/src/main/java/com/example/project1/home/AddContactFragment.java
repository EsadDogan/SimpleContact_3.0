package com.example.project1.home;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project1.R;
import com.example.project1.homeActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;


public class AddContactFragment extends Fragment {

    public final static String TAG = "addContact";
    private static final int RESULT_OK = -1 ;
    public final static String KEY_NAME = "firstname";
    public final static String KEY_LASTNAME ="lastname";
    public final static String KEY_IMAGE ="urlImage";
    public final static String KEY_MAIL ="mail";
    public final static String KEY_GENDER ="gender";
    public final static String KEY_COUNTRY ="country";
    public final static String KEY_AGE ="age";
    public final static String KEY_ID ="id";



    private Button btnImage , btnUploadImage ,  btnRegister;
    private EditText lblName, lblSurname , lblEmail, lblAge;
    private TextView CorrectChbRequest,correctAgeRequest,CorrectEmailRequest,CorrectSurNameRequest,CorrectNameRequest,txtImageUploaded;
    private RadioGroup radioGroup;
    private Spinner spinner;
    private CheckBox checkBox;
    private ConstraintLayout parent;
    private RadioButton radioMale , radioFemale , radioOther;
    private ScrollView scrollView;
    private ImageView imageView;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private ProgressBar progressBar;
    String urlImage;
    private boolean isImageUploaded = false;

    private StorageReference storageReference;
    //private DatabaseReference

    private DocumentReference mDocRef;

    public AddContactFragment() {
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
        return inflater.inflate(R.layout.fragment_add_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnImage = view.findViewById(R.id.btnPickImage);
        btnRegister = view.findViewById(R.id.btnRegister);
        lblName = view.findViewById(R.id.lblName);
        lblEmail = view.findViewById(R.id.lblEmaıl);
        lblSurname = view.findViewById(R.id.lblSurname);
        lblAge = view.findViewById(R.id.lblAge);
        CorrectChbRequest = view.findViewById(R.id.CorrectChbRequest);
        correctAgeRequest = view.findViewById(R.id.correctAgeRequest);
        CorrectEmailRequest = view.findViewById(R.id.CorrectEmailRequest);
        CorrectSurNameRequest = view.findViewById(R.id.CorrectSurNameRequest);
        CorrectNameRequest = view.findViewById(R.id.CorrectNameRequest);
        parent = view.findViewById(R.id.parent);
        checkBox = view.findViewById(R.id.checkboxAgree);
        radioMale = view.findViewById(R.id.radioMale);
        radioFemale = view.findViewById(R.id.radioFemale);
        radioOther = view.findViewById(R.id.radioOther);
        scrollView = view.findViewById(R.id.screen);
        imageView = view.findViewById(R.id.imageViewAddContact);
        progressBar = view.findViewById(R.id.progressBar);
        btnUploadImage = view.findViewById(R.id.btnUploadImage);
        txtImageUploaded = view.findViewById(R.id.txtImageUploaded);
        spinner = view.findViewById(R.id.spinner);




        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Toast.makeText(view.getContext(), "How you dare to change Master Yoda's picture!! Go away!", Toast.LENGTH_SHORT).show();

                openFileChooser();
            }
        });


        btnUploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (null !=mImageUri){
                    try {
                        uploadImage();
                        Toast.makeText(view.getContext(),"Image uploaded.",Toast.LENGTH_SHORT).show();
                        txtImageUploaded.setVisibility(View.VISIBLE);

                    }catch (Exception e){
                        Log.d(TAG, "onClick: error with image" + e.getMessage());
                        Toast.makeText(view.getContext(),"Something went wrong please try again later.",Toast.LENGTH_SHORT).show();
                    }

                }else {
                    Toast.makeText(view.getContext(), "Please choose a image first", Toast.LENGTH_SHORT).show();
                }


            }
        });


        labelChangeListeners();
        storageReference = FirebaseStorage.getInstance().getReference("uploads");

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isImageUploaded){

                    if (correction()) {

                        if(checkBox.isChecked())
                        {
                                uploadPerson();

                        }

                        else{

                            Toast.makeText(view.getContext(), "You have to check the agreement to proceed.", Toast.LENGTH_SHORT).show();


                        }


                    }
                }else {
                Toast.makeText(view.getContext(), "Please upload image to proceed",Toast.LENGTH_LONG).show();
            }


            }
        });

    }


    private void showSnackBar()
    {
        CorrectNameRequest.setVisibility(View.GONE);
        CorrectSurNameRequest.setVisibility(View.GONE);
        correctAgeRequest.setVisibility(View.GONE);
        CorrectEmailRequest.setVisibility(View.GONE);
        CorrectChbRequest.setVisibility(View.GONE);


        Snackbar.make(getActivity().findViewById(android.R.id.content), "New Person Added", Snackbar.LENGTH_LONG).setAction("close", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lblName.setText("");
                lblSurname.setText("");
                lblAge.setText("");
                lblEmail.setText("");
                checkBox.setChecked(false);
                radioMale.setChecked(true);
                radioFemale.setChecked(false);
                radioOther.setChecked(false);
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        }).show();


        //// this Snackbars below only for activities. you have to make the snackbar like top for fragments.


//        Snackbar.make(parent,"new person added",Snackbar.LENGTH_INDEFINITE).setAction("close", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        }).show();

//        Snackbar.make(parent, "New person added", Snackbar.LENGTH_INDEFINITE).setAction("Close", new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                // screen.arrowScroll(0);
//
//
//
//
//            }
//        }).show();
    }


    private void labelChangeListeners(){


        lblName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                CorrectNameRequest.setVisibility(View.GONE);
            }
        });


        lblSurname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                CorrectSurNameRequest.setVisibility(View.GONE);
            }
        });

        lblEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                CorrectEmailRequest.setVisibility(View.GONE);
            }
        });

        lblAge.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                correctAgeRequest.setVisibility(View.GONE);
            }
        });


    }

    private boolean correction()
    {
        if (lblName.getText().toString().equals("")) {

            CorrectNameRequest.setVisibility(View.VISIBLE);

            return false;
        }


        if (lblSurname.getText().toString().equals("")) {

            CorrectSurNameRequest.setVisibility(View.VISIBLE);

            return false;
        }


        if (lblEmail.getText().toString().equals("")) {

            CorrectEmailRequest.setVisibility(View.VISIBLE);

            return false;
        }




        if (lblAge.getText().toString().equals("")) {

            correctAgeRequest.setVisibility(View.VISIBLE);

            return false;
        }



        else
        {
            return true;
        }
    }


    private void openFileChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
        && data != null && data.getData() != null){

            mImageUri = data.getData();
            imageView.setImageURI(mImageUri);

        }
    }



   private void uploadImage(){


       homeActivity activity = (homeActivity) getActivity();


        String fileName = String.valueOf(activity.contacts.size() + 1 +".jpeg" ) ;

        StorageReference fileReference = storageReference.child(fileName);

       StorageMetadata metadata = new StorageMetadata.Builder()
               .setContentType("image/jpeg")
               .build();

        fileReference.putFile(mImageUri,metadata).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setProgress(0);
                    }
                },500);

                Log.d(TAG, "onSuccess: image uploaded "+urlImage);


                progressBar.setVisibility(View.GONE);
                isImageUploaded = true;
                btnImage.setEnabled(false);
                btnUploadImage.setEnabled(false);



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.d(TAG, "onFailure: error with picture" + e.getMessage());
                progressBar.setVisibility(View.GONE);

            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                progressBar.setVisibility(View.VISIBLE);
            }
        });

   }


        private  void uploadPerson(){



           homeActivity activity = (homeActivity) getActivity();

            String name = String.valueOf(lblName.getText());
            String lastname = String.valueOf(lblSurname.getText());
            String urlImageContact = "";
            String mail = String.valueOf(lblEmail.getText());
            String gender = "";
            String country = spinner.getSelectedItem().toString();
            int age = Integer.parseInt(String.valueOf(lblAge.getText()));
            int id = activity.contacts.size()+1;

            if (radioMale.isChecked()){
                gender = (String) radioMale.getText();
            }

            else if (radioFemale.isChecked()){
                gender = (String) radioFemale.getText();
            }

            else if (radioOther.isChecked()){
                 gender = (String) radioOther.getText();
            }




            Map<String , Object> dataToSave = new HashMap<String,Object>();
            dataToSave.put(KEY_NAME, name);
            dataToSave.put(KEY_LASTNAME, lastname);
            dataToSave.put(KEY_IMAGE, urlImageContact);
            dataToSave.put(KEY_MAIL, mail);
            dataToSave.put(KEY_GENDER, gender);
            dataToSave.put(KEY_COUNTRY,country);
            dataToSave.put(KEY_AGE,age);
            dataToSave.put(KEY_ID, id);

            mDocRef = FirebaseFirestore.getInstance().document("contactsDatas/"+id);
            mDocRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d(TAG, "onSuccess: everything is great");
                    showSnackBar();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "onFailure: error"+e.getMessage());
                }
            });

            ContactsFragment.adapter.notifyDataSetChanged();

        }



    //    private void postRequest(){
//
//
//
//        String url = "https://raw.githubusercontent.com/EsadDogan/projectJson/main/db.json";
//
//        // Request a string response from the provided URL.
//        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
//                url, null,
//                new Response.Listener<JSONObject>() {
//
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("JSONPost", response.toString());
//                        //pDialog.hide();
//                    }
//                }, new Response.ErrorListener() {
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                VolleyLog.d("JSONPost", "Error: " + error.getMessage());
//                //pDialog.hide();
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("name=someVal&comment=someOtherVal");
//                //params.put("comment", "someOtherVal");
//                return params;
//            }
//        };
//
//    }

}