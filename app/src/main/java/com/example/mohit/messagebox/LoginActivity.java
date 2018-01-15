package com.example.mohit.messagebox;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private Button buttonlogin;
    private EditText editTextemail ;
    private EditText editTextpass ;
    private TextView signuplink;
    private ProgressDialog progressDialog ;
    private FirebaseAuth firebaseAuth ;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null)
        {
            finish();
            startActivity(new Intent(this,Main2Activity.class));
            //loginactivity
        }
        progressDialog = new ProgressDialog(this );
        buttonlogin = (Button) findViewById(R.id.button2);
        editTextemail= (EditText) findViewById(R.id.edittextemail);
        editTextpass= (EditText) findViewById(R.id.edittextpass);
        signuplink = (TextView) findViewById(R.id.textview2);
        buttonlogin.setOnClickListener(this);
        signuplink.setOnClickListener(this);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    private void userlogin()
    {
        String email = editTextemail.getText().toString().trim();
        String  pass = editTextpass.getText().toString().trim();
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
        progressDialog.setMessage("Logging User ...");
        progressDialog.show();
        firebaseAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful())
                        {

                            checkuserexist();
                        }
                        else {

                            Toast.makeText(getApplicationContext(),"Failed To Sign",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
    public void checkuserexist()
    {
        final String user_id = firebaseAuth.getCurrentUser().getUid();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(user_id))
                {
                    startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                    Toast.makeText(getApplicationContext(),"You are Signed In",Toast.LENGTH_LONG).show();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    @Override
    public void onClick(View view) {
        if(view == buttonlogin )
        {
            userlogin();
            return;
        }
        if (view == signuplink)
        {
            finish();
            startActivity(new Intent(this,MainActivity.class));
            return;
        }
    }
}
