package com.example.virtualschools.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.virtualschools.R;
import com.google.android.material.navigation.NavigationBarView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.back) ImageView back;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.all_courses) TextView courses;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.all_bookmarks) TextView bookmarks;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.grades) TextView grades;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.attendance) TextView attendance;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spinner) Spinner menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);

        back.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this,MainActivity.class)));

        courses.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this,MainActivity.class)));

        bookmarks.setOnClickListener(v -> startActivity(new Intent(ProfileActivity.this,BookMarkActivity.class)));

        //spinner
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.profile_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        menu.setAdapter(adapter);

        menu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                String selectedItem = parent.getItemAtPosition(position).toString();
                if(selectedItem.equals("Log Out"))
                {
                    startActivity(new Intent(ProfileActivity.this,LogInActivity.class));
                }
            }

            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }
}