package com.example.mohit.messagebox;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;

public class Main3Activity extends AppCompatActivity {

    ListView authenticationListview ;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference ;
    List<String> userEmaillist ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        authenticationListview = (ListView)findViewById(R.id.listview1);
        userEmaillist = new ArrayList<String>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userEmaillist.clear();
                for(DataSnapshot userSnapShot : dataSnapshot.getChildren())
                {
                    //String id = databaseReference.push().getKey();
                   String userEmail = (String) userSnapShot.getValue();
                    userEmaillist.add(userEmail);
                }
                ArrayAdapter<String> adapter= new ArrayAdapter<>(Main3Activity.this,R.layout.listlayout,R.id.textview5,userEmaillist);
                authenticationListview.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    authenticationListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(getApplicationContext(), MessageActivity.class);
            startActivity(intent);
            //intent.putExtra("user", )
        }
    });
    }


}
