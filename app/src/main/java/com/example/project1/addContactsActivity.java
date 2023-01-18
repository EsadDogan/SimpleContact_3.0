package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

public class addContactsActivity extends AppCompatActivity {


    private Button btnImage , btnRegister;
    private EditText lblName, lblSurname , lblEmail, lblPassword, lblPasswordAgain;
    private TextView CorrectChbRequest,CorrectPasswordAgainRequest,CorrectPasswordRequest,CorrectEmailRequest,CorrectSurNameRequest,CorrectNameRequest;
    private RadioGroup radioGroup;
    private Spinner spinner;
    private CheckBox checkBox;
    private ConstraintLayout parent;
    private RadioButton radioMale , radioFemale , radioOther;
    private ScrollView screen;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contacts);








       bottomNavigationView.setOnItemSelectedListener(item -> {

           Intent intent;
           switch (item.getItemId()){
               case R.id.contacts:
                   intent = new Intent(addContactsActivity.this, seeContactsActivity.class);
                   startActivity(intent);
                   break;

               case  R.id.home:
                   intent = new Intent(addContactsActivity.this, homeActivity.class);
                   startActivity(intent);
                   break;

               default: break;


           }

           return true;
       } );

    }


    private void showSnackBar()
    {
        CorrectNameRequest.setVisibility(View.GONE);
        CorrectSurNameRequest.setVisibility(View.GONE);
        CorrectPasswordRequest.setVisibility(View.GONE);
        CorrectPasswordAgainRequest.setVisibility(View.GONE);
        CorrectEmailRequest.setVisibility(View.GONE);
        CorrectChbRequest.setVisibility(View.GONE);

        Snackbar.make(parent, "New person added", Snackbar.LENGTH_INDEFINITE).setAction("Close", new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lblName.setText("");
                lblSurname.setText("");
                lblPassword.setText("");
                lblEmail.setText("");
                lblPasswordAgain.setText("");
                checkBox.setChecked(false);
                radioMale.setChecked(true);
                radioFemale.setChecked(false);
                radioOther.setChecked(false);
               // screen.arrowScroll(0);




            }
        }).show();
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




         if (lblPassword.getText().toString().equals("")) {

             CorrectPasswordRequest.setVisibility(View.VISIBLE);
             return false;
         }


         if (lblPasswordAgain.getText().toString().equals("")) {

             CorrectPasswordAgainRequest.setVisibility(View.VISIBLE);
             return false;
         }

         if (!lblPasswordAgain.getText().toString().equals(lblPassword.getText().toString())) {

             CorrectPasswordAgainRequest.setText("Passwords have to be same.");
             CorrectPasswordAgainRequest.setVisibility(View.VISIBLE);
             return false;
         }

         else
         {
             return true;
         }
    }


}