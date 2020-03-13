package com.leonard.notetakingapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    static ArrayList<String>notes=new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    static Set<String>set;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ListView listView=findViewById(R.id.listView);


//        Saving data permamently

        SharedPreferences sharedPreferences=this.getSharedPreferences("com.leonard.notetakingapp",Context.MODE_PRIVATE);
        set=sharedPreferences.getStringSet("notes",null);
        notes.clear();

        if(set !=null){
            notes.addAll(set);
        }else
        {
            notes.add("Example note");
            set=new HashSet<String>();
            set.addAll(notes);
            sharedPreferences.edit().remove("notes").apply();
            sharedPreferences.edit().putStringSet("notes",set).apply();
        }




        notes.add("Example note");
         arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,notes);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),EditNote.class);
                intent.putExtra("noteId",position);
                startActivity(intent);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_delete)
                        .setTitle("Delete")
                        .setMessage("Do you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                notes.remove(position);
                                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.leonard.notetakingapp", Context.MODE_PRIVATE);

                                if (set==null){
                                    set=new HashSet<String>();
                                }else
                                {
                                    set.clear();
                                }

                                set.addAll(notes);

//                Testing ...not necessary.

                                sharedPreferences.edit().remove("notes").apply();
                                sharedPreferences.edit().putStringSet("notes",set).apply();
                                arrayAdapter.notifyDataSetChanged();


                            }
                        })
                        .setNegativeButton("No",null)
                        .show();
            }
        });



        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notes.add("");
                SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences("com.leonard.notetakingapp", Context.MODE_PRIVATE);

                if (set==null){
                    set=new HashSet<String>();
                }else
                {
                    set.clear();
                }

                set.addAll(notes);

//                Testing ...not necessary.

                sharedPreferences.edit().remove("notes").apply();
                sharedPreferences.edit().putStringSet("notes",set).apply();
                arrayAdapter.notifyDataSetChanged();




                Intent intent=new Intent(getApplicationContext(),EditNote.class);

                intent.putExtra("noteId",notes.size()-1);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
