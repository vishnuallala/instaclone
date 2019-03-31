package com.vishnu.instaclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class login extends AppCompatActivity {

    private EditText emaillogin,passlogin;
    private Button signuplogin,loginlogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("Login");
        if(ParseUser.getCurrentUser()!=null){
            Intent social=new Intent(login.this,socialmedia.class);
            startActivity(social);
            finish();
        }
        emaillogin=findViewById(R.id.emaillogin);
        passlogin=findViewById(R.id.passlogin);
        signuplogin=findViewById(R.id.signuplogin);
        loginlogin=findViewById(R.id.loginlogin);
        signuplogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(login.this,signup.class);
                startActivity(in);
                finish();
            }
        });
        loginlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog=new ProgressDialog(login.this);
                progressDialog.setMessage("Logging up");
                progressDialog.show();

                ParseUser.logInInBackground(emaillogin.getText().toString(), passlogin.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user!=null && e==null) {
                            progressDialog.dismiss();
                            Intent social=new Intent(login.this,socialmedia.class);
                            startActivity(social);
                            finish();
                            Toast.makeText(login.this, "logged in", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            progressDialog.dismiss();
                            Toast.makeText(login.this, "error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
