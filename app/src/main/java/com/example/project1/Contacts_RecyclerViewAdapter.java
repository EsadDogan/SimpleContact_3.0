package com.example.project1;



import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project1.home.ContactsFragment;
import com.example.project1.home.SeeContactDetailsFragment;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class Contacts_RecyclerViewAdapter extends RecyclerView.Adapter<Contacts_RecyclerViewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Contacts> contacts;
    public static String itemId;

    /**
     * Constructor which holds the data from activity
     * @param context
     * @param contacts arraylist of objects
     */
    public Contacts_RecyclerViewAdapter(Context context, ArrayList<Contacts> contacts){

        this.contacts = contacts;
        this.context = context;
    }

    @NonNull
    @Override
    public Contacts_RecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // this is where you inflate your layout (giving a look to our rows)

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recyclerview,parent, false);

        return new Contacts_RecyclerViewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Contacts_RecyclerViewAdapter.MyViewHolder holder, int position) {

        // assigning values to the views we created in the recylerview.xml
        // based on the position of the recycler view

        String nameLastname = contacts.get(holder.getAdapterPosition()).getName() +" "+ contacts.get(position).getLastName();
        holder.txtNameSurname.setText(nameLastname);
        holder.txtMail.setText(contacts.get(position).getMail());
        holder.txtUserId.setText(contacts.get(position).getIdAsString());

        contacts.get(position).loadImage(context,holder.imageView);

//        Glide.with(context)
//                .asBitmap()
//                .load(contacts.get(position).getUrlImage()).into(holder.imageView);


        holder.itemContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

                    itemId = holder.txtUserId.getText().toString();
                    AppCompatActivity activity = (AppCompatActivity) view.getContext();


                    FragmentManager manager2 = activity.getSupportFragmentManager();
                    manager2.beginTransaction()
                            .replace(R.id.myFrameLay, new SeeContactDetailsFragment(), SeeContactDetailsFragment.TAG)
                            .addToBackStack(null)
                            .commit();

                }catch (Exception e){
                    Log.d("tag", "onClick: "+e.getMessage());
                }



            }
        });
    }

    @Override
    public int getItemCount() {
        // Recyclerview just wants to know the number of items you want
        return contacts.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        // grabbing the views from our recyclerview.xml file
        // kinda like in the onCreate method

        ImageView imageView;
        TextView txtNameSurname, txtMail,txtUserId;
        CardView itemContact;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
            txtNameSurname = itemView.findViewById(R.id.txtNameSurname);
            txtMail = itemView.findViewById(R.id.txtEmail);
            txtUserId = itemView.findViewById(R.id.txtUserId);
            itemContact = itemView.findViewById(R.id.itemContact);

        }
    }
}
