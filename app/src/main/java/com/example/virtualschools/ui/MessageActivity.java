package com.example.virtualschools.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.virtualschools.R;
import com.example.virtualschools.adapters.MessageAdapter;
import com.example.virtualschools.models.Chat;
import com.example.virtualschools.models.ChatUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessageActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.chat_profile_image)
    CircleImageView profile_image;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.chat_name)
    TextView name;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.btn_send)
    ImageButton btn_send;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.text_send)
    EditText text_send;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    FirebaseUser firebaseUser;
    DatabaseReference reference;

    Intent intent;

    MessageAdapter messageAdapter;
    List<Chat> mChat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);

        //Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        intent =getIntent();
        String userid = intent.getStringExtra("userid");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ChatUser chatUser = snapshot.getValue(ChatUser.class);
                name.setText(Objects.requireNonNull(chatUser).getUsername());
                if(chatUser.getImageUrl().equals("default")){
                    profile_image.setImageResource(R.drawable.user);
                } else {
                    Glide.with(MessageActivity.this).load(chatUser.getImageUrl()).into(profile_image);
                }
                readMessages(firebaseUser.getUid(),userid,chatUser.getImageUrl());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //send message
        btn_send.setOnClickListener(v -> {
            String msg = text_send.getText().toString();
            if(!msg.equals("")){
                sendMessage(firebaseUser.getUid(),userid,msg);
            } else {
                Toast.makeText(this, "You can't send an empty message", Toast.LENGTH_SHORT).show();
            }

            text_send.setText("");
        });
    }

    private void sendMessage(String sender,String receiver,String message) {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);

        reference.child("Chats").push().setValue(hashMap);
    }

    private void readMessages(final String myid, final String userid, String imageUrl){
        mChat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mChat.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()) {
                    Chat chat = snapshot1.getValue(Chat.class);
                    assert chat != null;
                    if((chat.getReceiver().equals(myid) && chat.getSender().equals(userid)) ||
                            (chat.getReceiver().equals(userid) && chat.getSender().equals(myid))){
                        mChat.add(chat);
                    }
                    messageAdapter = new MessageAdapter(MessageActivity.this,mChat,imageUrl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}