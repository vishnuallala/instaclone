package com.vishnu.instaclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.security.Key;

public class signup extends AppCompatActivity{


    private EditText emailsign,usersign,passsign;
    private Button signsign,loginsign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("Sign Up");
        if(ParseUser.getCurrentUser()!=null){
            Intent social=new Intent(signup.this,socialmedia.class);
            startActivity(social);
            finish();
        }
        emailsign=findViewById(R.id.emailsign);
        usersign=findViewById(R.id.usersign);
        passsign=findViewById(R.id.passsign);
        signsign=findViewById(R.id.signsign);
        loginsign=findViewById(R.id.loginsign);
        passsign.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()== KeyEvent.ACTION_DOWN){
                    signsign.callOnClick();
                }
                return false;
            }
        });
        signsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ProgressDialog progressDialog=new ProgressDialog(signup.this);
//                progressDialog.setMessage("signing up");
//                progressDialog.show();
                if(emailsign.getText().toString().compareTo("")!=0 &&
                        passsign.getText().toString().compareTo("")!=0) {
//                    ProgressDialog progressDialog=new ProgressDialog(signup.this);
//                    progressDialog.setMessage("signing up");
//                    progressDialog.show();
                    ParseUser user = new ParseUser();
                    user.setEmail(emailsign.getText().toString());
                    user.setUsername(usersign.getText().toString());
                    user.setPassword(passsign.getText().toString());
                   final ProgressDialog progressDialog=new ProgressDialog(signup.this);
                    progressDialog.setMessage("signing up");
                    progressDialog.show();

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if(e==null)
                            {
                                progressDialog.dismiss();
                                Intent social=new Intent(signup.this,socialmedia.class);
                                startActivity(social);
                                finish();
                                Toast.makeText(signup.this,"User Registered sucessfully",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                progressDialog.dismiss();
                                Toast.makeText(signup.this,"error",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
              //      progressDialog.dismiss();
                }
                else{
                    Toast.makeText(signup.this,"Enter the username and password",Toast.LENGTH_SHORT).show();
                }
            }
        });
        loginsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(signup.this,login.class);
                startActivity(in);
                finish();
            }
        });
    }
}
