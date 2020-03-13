package com.leonard.notetakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.HashSet;

import static com.leonard.notetakingapp.MainActivity.notes;
import static com.leonard.notetakingapp.MainActivity.set;

public class EditNote extends AppCompatActivity implements TextWatcher {

    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//Editing an editText
        EditText editText=findViewById(R.id.editText);
        Intent i=getIntent();

        noteId=i.getIntExtra("noteId",-1);

        if (noteId !=-1){
            String fillerText= notes.get(noteId);
            editText.setText(fillerText);
        }

//        Automatically saving the note
        editText.addTextChangedListener(this);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);

                startActivity(intent);

            }
        });

//        BACK ARROW
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
        notes.set(noteId,String.valueOf(charSequence));
        MainActivity.arrayAdapter.notifyDataSetChanged();

        SharedPreferences sharedPreferences=this.getSharedPreferences("com.leonard.notetakingapp", Context.MODE_PRIVATE);

        if (set==null){
            set=new HashSet<String>();
        }else
        {
            set.clear();
        }

        set.clear();
        set.addAll(notes);
        sharedPreferences.edit().remove("notes").apply();
        sharedPreferences.edit().putStringSet("notes",set).apply();


//        to save data permanently create shared preferences in the main activity

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
