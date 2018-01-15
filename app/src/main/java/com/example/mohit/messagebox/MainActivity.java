package com.example.mohit.messagebox;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonregister;
    private EditText editTextemail ;
    private EditText editTextpass ;
    private TextView newuserlink;
    private ProgressDialog progressdialog;
    private FirebaseAuth firebaseAuth ;
    private DatabaseReference mdata  ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth  = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(this,Main2Activity.class));
            //loginactivity
        }
        progressdialog = new ProgressDialog(this);
        buttonregister = (Button)findViewById(R.id.button1);
        editTextemail= (EditText)findViewById(R.id.edittextemail);
        editTextpass= (EditText)findViewById(R.id.edittextpass);
        newuserlink = (TextView)findViewById(R.id.textview1);
        buttonregister.setOnClickListener(this);
        newuserlink.setOnClickListener(this);
        mdata = FirebaseDatabase.getInstance().getReference().child("Users");


    }

    private void registeruser(){

        final String email = editTextemail.getText().toString().trim();
        String pass = editTextpass.getText().toString().trim();
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(getApplicationContext(),"Please Enter email ",Toast.LENGTH_LONG).show();
            return;
        }
        if(TextUtils.isEmpty(pass))
        {
            Toast.makeText(this,"Enter Your password",Toast.LENGTH_LONG).show();
            return;
        }
        progressdialog.setMessage("Regitering User ....");
        progressdialog.show();
        firebaseAuth.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressdialog.dismiss();
                        if(task.isSuccessful())
                        {

                            //mdata = FirebaseDatabase.getInstance().getReference("Users");
                            //String userid = mdata.push().getKey();
                            String userid = firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference current_database_reference = mdata.child(userid);
                            current_database_reference.child("Name").setValue(email);
                            startActivity(new Intent(MainActivity.this, LoginActivity.class));
                            Toast.makeText(getApplicationContext(),"You are Registered ",Toast.LENGTH_LONG).show();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),"Failed to register",Toast.LENGTH_LONG).show();
                        }

                    }
                });

    }
    @Override
    public void onClick(View view) {
        if(view == buttonregister){
            //for registering
            registeruser();
        }
        if(view == newuserlink){
            finish();
            startActivity(new Intent(this , LoginActivity.class));
            //for new user
        }

    }
}
