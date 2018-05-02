package com.example.pranavsrivastava.fma1;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.tag;

/**
 * Created by Pranav Srivastava on 03-Mar-18.
 */

public class List1 extends AppCompatActivity implements View.OnClickListener
    {
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private ClipData.Item i;
        private TextView textView1,textView2;
        private DatabaseReference ref;
        private FirebaseDatabase database;
        private ListView listView;
        List<Data> dl;
        private CoordinatorLayout coordinatorLayout;
        private static final String STR="STR";


    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Restore instance state
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(toolbar);
        FloatingActionButton fab=(FloatingActionButton)findViewById(R.id.fab);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
        FirebaseMessaging.getInstance().subscribeToTopic("all");
        listView=(ListView) findViewById(R.id.listView);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Details");
        dl=new ArrayList<>();

            if (!isNetworkConnected()) {
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                snackbar.show();
            }
        fab.setOnClickListener(this);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser!=null)
        {
            getMenuInflater().inflate(R.menu.menu_main, menu);

        }
        else
            getMenuInflater().inflate(R.menu.content_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (!isNetworkConnected()) {
            Snackbar snackbar = Snackbar
                    .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    });
            snackbar.show();
        }
        else {
            int id = item.getItemId();
            if (id == R.id.action_user) {
                Intent intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                finish();
                return true;
            } else if (id == R.id.action_signout) {
                mAuth.signOut();
                Intent intent2 = new Intent(this, List1.class);
                startActivity(intent2);
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }
        public void onClick(View view)
        {
            switch (view.getId()) {
                case R.id.fab:
                    if (!isNetworkConnected()) {
                        Snackbar snackbar = Snackbar
                                .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                                .setAction("RETRY", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                });
                        snackbar.show();
                    }
                    else{
                        if(FirebaseAuth.getInstance().getCurrentUser()!=null)
                        {
                            Intent it1=new Intent(List1.this,Detail.class);
                            startActivity(it1);
                        }
                        else
                        {
                            AlertDialog.Builder builder;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Light_Dialog);
                            } else {
                                builder = new AlertDialog.Builder(this);
                            }
                            builder.setTitle("Sign In")
                                    .setMessage("Please Sign in to Upload Information")
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent2 = new Intent(List1.this, MainActivity.class);
                                            startActivity(intent2);
                                            finish();
                                        }
                                    })
                                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // do nothing
                                        }
                                    })
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .show();
                            //Toast.makeText(this, "Not Logged In", Toast.LENGTH_LONG).show();
                        }
                    }
            }

        }
        protected void onStart() {
            super.onStart();
            if (!isNetworkConnected()) {
                Snackbar snackbar = Snackbar
                        .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_LONG)
                        .setAction("RETRY", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        });
                snackbar.show();
            } else {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        dl.clear();
                        for (DataSnapshot snp : dataSnapshot.getChildren()) {

                            Data dat = snp.getValue(Data.class);
                            dl.add(dat);
                        }
                        Detail_list adapter = new Detail_list(List1.this, dl);
                        listView.setAdapter(adapter);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
        private boolean isNetworkConnected() {
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

            return cm.getActiveNetworkInfo() != null;
        }

    }
