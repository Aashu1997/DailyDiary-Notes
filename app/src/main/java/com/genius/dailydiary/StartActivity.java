package com.genius.dailydiary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.genius.dailydiary.user_sign.LoginActivity;
import com.genius.dailydiary.user_sign.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;

public class StartActivity extends AppCompatActivity {

    private Button btnReg, btnLog;

    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnLog=findViewById(R.id.button);
        btnReg=findViewById(R.id.button2);

        fAuth=FirebaseAuth.getInstance();

        updateUI();

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
               login();
            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                register();
            }
        });

    }

    private void register()
    {
       Intent regIntent = new Intent(StartActivity.this, RegisterActivity.class);
       startActivity(regIntent);
       finish();
    }

    private void login()
    {
        Intent logIntent = new Intent(StartActivity.this, LoginActivity.class);
        startActivity(logIntent);
        finish();
    }

    private void updateUI()
    {
        if(fAuth.getCurrentUser() !=null)
        {
            Log.i("StartActivity","fAuth !=null");
            Intent startIntent = new Intent(StartActivity.this,MainActivity.class);
            startActivity(startIntent);
            finish();

        }
        else
        {

            Log.i("StartActivity","fAuth==null");
        }
    }
}
