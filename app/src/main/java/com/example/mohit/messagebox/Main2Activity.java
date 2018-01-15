package com.example.mohit.messagebox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{

    private TextView textviewemailid ;
    private Button logoutbutton , savebutton , authenticationUsers;
    EditText editname , editadress ;
    private FirebaseAuth firebaseAuth ;

    DatabaseReference databasereference ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser fireBaseUser = firebaseAuth.getCurrentUser();
        textviewemailid = (TextView)findViewById(R.id.textView3);
        logoutbutton = (Button) findViewById(R.id.button3);
        textviewemailid.setText( "Welcome " + fireBaseUser.getEmail());
        savebutton = (Button)findViewById(R.id.savebutton);
        editname = (EditText)findViewById(R.id.editname);
        authenticationUsers =(Button)findViewById(R.id.button4);
        editadress = (EditText)findViewById(R.id.editaddress);
        databasereference = FirebaseDatabase.getInstance().getReference("College");
        logoutbutton.setOnClickListener(this);
        savebutton.setOnClickListener(this);
        authenticationUsers.setOnClickListener(this);
    }

    private  void saveData()
    {
        String name = editname.getText().toString().trim();
        String address = editadress.getText().toString().trim();
        UserInformation userInformation = new UserInformation(name,address);
        String id = databasereference.push().getKey();
        //FirebaseUser firebaseuser = firebaseAuth.getCurrentUser();
        databasereference.child(id).setValue(userInformation);
        Toast.makeText(getApplicationContext(),"Data Saved...",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onClick(View view) {
        if(view == logoutbutton)
        {
            firebaseAuth.signOut();
            finish();
            startActivity(new Intent(this , MainActivity.class));
        }
        else if(view == savebutton)
        {
            saveData();
            //Saving the data

        }
        else if(view == authenticationUsers)
        {
            //startActivity(new Intent(Main2Activity.this , MessageActivity.class));
            Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
            startActivity(intent);
        }
    }
}
