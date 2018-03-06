package com.example.pranavsrivastava.fma1;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.pranavsrivastava.fma1.R.id.map;

/**
 * Created by Pranav Srivastava on 03-Mar-18.
 */

public class Detail extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private ClipData.Item i;
    private EditText et1,et2;
    private Button b1,b2;
    private DatabaseReference databaseReference;
    private Button map;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // Restore instance state
        if (savedInstanceState != null) {
            onRestoreInstanceState(savedInstanceState);
        }
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        // Setting toolbar as the ActionBar with setSupportActionBar() call
        setSupportActionBar(toolbar);
        databaseReference= FirebaseDatabase.getInstance().getReference("Details");
        et1= (EditText) findViewById(R.id.et1);
        et2=(EditText) findViewById(R.id.et2);
        b1= (Button) findViewById(R.id.b1);
        b1.setOnClickListener(this);
		 map= (Button) findViewById(R.id.map);
		 map.setOnClickListener(this);

    }
	
	@Override
    protected void onResume() {
        super.onResume();
        if(et2!=null)
        {
            et2.setText(MapsActivity.locality+MapsActivity.country);
        }

    }
    private void saveinfo()
    {
        mAuth = FirebaseAuth.getInstance();
        String s1=et1.getText().toString();
        String s2=et2.getText().toString();
        Data dat=new Data(s1,s2);
        FirebaseUser user=mAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(dat);
        Toast.makeText(this,"Successfuly Added",Toast.LENGTH_LONG).show();
    }
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.b1:
                saveinfo();
                finish();
                break;
			case R.id.map:
                Intent intent =new Intent(Detail.this,MapsActivity.class);
                startActivity(intent);
        }

    }
}



