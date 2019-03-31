package com.vishnu.instaclone;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class userposts extends AppCompatActivity {

    private LinearLayout linearLayout;
    String receivedUserName;
 //   ParseQuery<ParseObject> parseQuery=new ParseQuery.getQuery("photo");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userposts);
        linearLayout=findViewById(R.id.li);
        Intent receivedIntentObject = getIntent();
        receivedUserName = receivedIntentObject.getStringExtra("username");
//        Toast.makeText(this,receivedUserName,Toast.LENGTH_SHORT).show();
        setTitle(receivedUserName + "posts");
        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("photo");
//        ParseQuery<ParseObject> parseQuery=new ParseQuery.getQuery("photo");
        parseQuery.whereEqualTo("user", receivedUserName);
        parseQuery.orderByAscending("createdAt");

        final ProgressDialog progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("fetching");
        progressDialog.show();
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
               // Toast.makeText(userposts.this,objects.size()+"qwee",Toast.LENGTH_SHORT).show();

                if(e==null && objects.size()>0){
//               Toast.makeText(userposts.this,receivedUserName+"qwee",Toast.LENGTH_SHORT).show();
               for(ParseObject post:objects){
               //    Toast.makeText(userposts.this,receivedUserName+"qwee",Toast.LENGTH_SHORT).show();
                   final TextView desc=new TextView(userposts.this);
                   desc.setText(post.get("description")+"");
                   ParseFile postPicture =(ParseFile) post.get("picture");
                   postPicture.getDataInBackground(new GetDataCallback() {
                       @Override
                       public void done(byte[] data, ParseException e) {
                           if(e==null && data!=null){
                               Bitmap bitmap= BitmapFactory.decodeByteArray(data,0,data.length);
                               ImageView postImageView=new ImageView(userposts.this);
                               LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                               params.setMargins(5,5,5,5);
                               postImageView.setLayoutParams(params);
                               postImageView.setScaleType(ImageView.ScaleType.FIT_START);
                               postImageView.setImageBitmap(bitmap);
                 //              Toast.makeText(userposts.this,receivedUserName,Toast.LENGTH_SHORT).show();

                               LinearLayout.LayoutParams param=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                               param.setMargins(5,5,5,15);
                               desc.setLayoutParams(param);
                               desc.setGravity(Gravity.CENTER);
                               desc.setBackgroundColor(Color.RED);
                               desc.setTextColor(Color.WHITE);
                               desc.setTextSize(30f);

                               linearLayout.addView(postImageView);
                               linearLayout.addView(desc);
                               progressDialog.dismiss();
                           }
                           else{
                               Toast.makeText(userposts.this,receivedUserName,Toast.LENGTH_SHORT).show();
                           }
                       }
                   });

               }
           }else{
               Toast.makeText(userposts.this,receivedUserName+ "sds",Toast.LENGTH_SHORT).show();

           }
           }
        });

    }
}
