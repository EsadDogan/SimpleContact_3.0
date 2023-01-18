package com.example.project1;



import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Locale;

public class Contacts {

    private String name,lastName, urlImage,mail,gender,country;
    private int age,id;

    public Contacts(){}

    public Contacts(String name, String lastName, String urlImage, int age,int id,String mail,String gender,String country) {
        this.name = name;
        this.lastName = lastName;
        this.urlImage = urlImage;
        this.age = age;
        this.id = id;
        this.mail = mail;
        this.gender = gender;
        this.country = country;
    }



    public Contacts(String name, String lastName, int age, String mail) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
        this.mail = mail;
    }


    public String getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }

    public int getId() {
        return id;
    }

    public String getIdAsString(){
        return String.valueOf(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void loadImage(Context context , ImageView imageView){

        StorageReference storageReference;

        storageReference = FirebaseStorage.getInstance().getReference();



        storageReference.child("uploads/"+getIdAsString()+".jpeg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(context).load(uri).centerCrop().into(imageView);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "onFailure: couldnt load image " + e.getMessage());
                Log.d("TAG", "onFailure: " +"uploads/"+getIdAsString());
            }
        });
    }



    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
