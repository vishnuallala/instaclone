package com.vishnu.instaclone;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

public class socialmedia extends AppCompatActivity {
    private android.support.v7.widget.Toolbar toolbar;
    private TabLayout tabLayout;
   private ViewPager viewPager;
   private TabAdapter tabAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socialmedia);
    toolbar=findViewById(R.id.my_toolbar);
    setSupportActionBar(toolbar);
    viewPager=findViewById(R.id.viewPager);
    tabAdapter=new TabAdapter(getSupportFragmentManager());
    viewPager.setAdapter(tabAdapter);
    tabLayout=findViewById(R.id.tabLayout);
    tabLayout.setupWithViewPager(viewPager,true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.my_menu,menu);
        //menuInflater.inflate(R.menu.my_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.postImageItem) {
            if(Build.VERSION.SDK_INT>=23 && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},3000);
            }
            else{
                captureImage();
            }
        }
        else if(item.getItemId()==R.id.logoutUserItem){
            ParseUser.getCurrentUser().logOut();
            finish();
            Intent intent=new Intent(this,signup.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==3000){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                captureImage();
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void captureImage() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,4000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==4000 && resultCode==RESULT_OK && data!=null){
            try{
                Uri capturedimage=data.getData();
                Bitmap bitmap=MediaStore.Images.Media.getBitmap(this.getContentResolver(),capturedimage);
                ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                byte[] bytes=byteArrayOutputStream.toByteArray();

                ParseFile parseFile=new ParseFile("img.png",bytes);
                ParseObject parseObject=new ParseObject("photo");
                parseObject.put("picture",parseFile);
                parseObject.put("user", ParseUser.getCurrentUser().getUsername());
               // parseObject.put("description",etext.getText().toString());
                final ProgressDialog obj=new ProgressDialog(this);
                obj.setMessage("loading...");
                obj.show();
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e==null){
                            obj.dismiss();
                            Toast.makeText(socialmedia.this,"sucess",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
