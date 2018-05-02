package com.example.pranavsrivastava.fma1;

import android.app.DownloadManager;
import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.pranavsrivastava.fma1.R.id.map;

/**
 * Created by Pranav Srivastava on 03-Mar-18.
 */

public class Detail extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private ClipData.Item i;
    private EditText et1,et2;
    private Button b1;
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
       // Log.d("Test","Worked");
        mAuth = FirebaseAuth.getInstance();
        String s1=et1.getText().toString();
        String s2=et2.getText().toString();
        Data dat=new Data(s2,s1);
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
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        sendnoti();
                    }
                }).start();
                finish();
                break;
			case R.id.map:
                Intent intent =new Intent(Detail.this,MapsActivity.class);
                startActivity(intent);
        }

    }

    private void sendnoti() {

        Log.d("Test","Worked");
        OkHttpClient client=new OkHttpClient();
        MediaType Json=MediaType.parse("application/json");

        JSONObject actualdata=new JSONObject();
        //actualdata.put("\"to\": \"/topics/all\",\"notification\" : { \"body\" : \"Tap to know more...\",\"content_available\" : true,\"priority\" : \"high\",\"title\" : \"(FMA1)New Food Detail\"}");
        RequestBody body=RequestBody.create(Json,"\"to\": \"/topics/all\",\"notification\" : { \"body\" : \"Tap to know more...\",\"content_available\" : true,\"priority\" : \"high\",\"title\" : \"(FMA1)New Food Detail\"}");
        Request newr=new Request.Builder().url(" https://fcm.googleapis.com/fcm/send").post(body).build();
        try {
            Log.d("Test","Worked");
            Response response=client.newCall(newr).execute();
        } catch (IOException e) {
            Log.d("Test","LOLWorked");
            e.printStackTrace();
        }
    }
}



