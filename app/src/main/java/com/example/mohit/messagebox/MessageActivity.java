package com.example.mohit.messagebox;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editmessage ;
    Button sendbutton ;
    DatabaseReference databaseReference , databaseUsers;
    private RecyclerView recyclerView;
    private FirebaseAuth firebaseAuth ;
    private FirebaseUser mCurrentUser ;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        firebaseAuth = FirebaseAuth.getInstance();
        editmessage = (EditText)findViewById(R.id.editmessage);
        sendbutton = (Button)findViewById(R.id.sendbutton);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Messages");
        recyclerView = (RecyclerView)findViewById(R.id.messageRec);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(MessageActivity.this,MainActivity.class));
                }
            }
        };
        sendbutton.setOnClickListener(this);
    }

    public void sendButtonclicked (View view)
    {
        mCurrentUser = firebaseAuth.getCurrentUser();
        databaseUsers = FirebaseDatabase.getInstance().getReference().child("Users").child(mCurrentUser.getUid());
        final String message = editmessage.getText().toString().trim();
        if(!TextUtils.isEmpty(message))
        {

            final DatabaseReference newPost = databaseReference.push();
            databaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    newPost.child("content").setValue(message);
                    newPost.child("username").setValue(dataSnapshot.child("Name").getValue()).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });
                }


                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount());

        }
    }
    @Override
    public void onClick(View view) {
        if(view == sendbutton)
        {
            sendButtonclicked(sendbutton);
            recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter <Message,MessageViewholder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Message, MessageViewholder>(
                Message.class,
                R.layout.messagelistlayout,
                MessageViewholder.class,
                databaseReference
        ) {
            @Override
            protected void populateViewHolder(MessageViewholder viewHolder, Message model, int position) {
                viewHolder.setContent(model.getContent());
                viewHolder.setUsername(model.getUsername());
            }

        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
        recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount());
    }

    public static class MessageViewholder extends RecyclerView.ViewHolder{

        View mView ;
        public MessageViewholder(View itemView) {

            super(itemView);
            mView = itemView;

        }
        public void setContent (String content)
        {
            TextView messageContent = (TextView)mView.findViewById(R.id.textmessage);
            messageContent.setText(content);
        }
        public  void setUsername (String username)
        {
            TextView username_text = (TextView)mView.findViewById(R.id.textviewname);
            username_text.setText(username);
        }
    }
}
