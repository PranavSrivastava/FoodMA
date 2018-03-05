package com.example.pranavsrivastava.fma1;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Pranav Srivastava on 03-Mar-18.
 */

public class List extends AppCompatActivity implements View.OnClickListener
    {
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private ClipData.Item i;
        private TextView textView1,textView2;
        private DatabaseReference ref;
        private FirebaseDatabase database;

    protected void onCreate(Bundle savedInstanceState) {
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
        fab.setOnClickListener(this);
        //editText=(EditText)findViewById(R.id.editText);
        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Details");


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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_user) {
            Intent intent1=new Intent(this,MainActivity.class);
            startActivity(intent1);
            finish();
            return true;
        }
        else if (id==R.id.action_signout)
        {
            mAuth.signOut();
            Intent intent2=new Intent(this,List.class);
            startActivity(intent2);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
        public void onClick(View view)
        {
            switch (view.getId()) {
                case R.id.fab:
                    Intent it1=new Intent(List.this,Detail.class);
                    startActivity(it1);
            }

        }
        protected void onStart()
        {
            super.onStart();
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    DataSnapshot snap = (DataSnapshot) dataSnapshot.getChildren();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
}
