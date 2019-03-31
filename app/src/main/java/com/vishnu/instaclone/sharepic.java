package com.vishnu.instaclone;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class sharepic extends Fragment implements View.OnClickListener {
    private ImageView imgview;
    private EditText etext;
    private Button button;
    private Bitmap receivedImageBitMap;
    public sharepic() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_sharepic, container, false);
        imgview=view.findViewById(R.id.imageView3);
        etext=view.findViewById(R.id.editText);
        button=view.findViewById(R.id.button);
        imgview.setOnClickListener(sharepic.this);
        button.setOnClickListener(sharepic.this);
        return  view;
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.imageView3:
                if(Build.VERSION.SDK_INT>=23 && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1000);
                }
                else{
                    getimage();
                }
                break;
            case R.id.button:
                if(receivedImageBitMap!=null){
                    //Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
                    if(etext.getText().toString().equals("")){
                        Toast.makeText(getContext(),"enter description",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
                        receivedImageBitMap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        byte[] bytes=byteArrayOutputStream.toByteArray();
                        ParseFile parseFile=new ParseFile("img.png",bytes);
                        ParseObject parseObject=new ParseObject("photo");
                        parseObject.put("picture",parseFile);
                        parseObject.put("user", ParseUser.getCurrentUser().getUsername());
                        parseObject.put("description",etext.getText().toString());
                        final ProgressDialog obj=new ProgressDialog(getContext());
                        obj.setMessage("loading...");
                        obj.show();
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                if(e==null){
                                    obj.dismiss();
                                    Toast.makeText(getContext(),"sucess",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }else{
                    Toast.makeText(getContext(),"error",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void getimage() {
        Intent intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode==1000){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                getimage();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2000){
            if(resultCode== Activity.RESULT_OK){
                try{
                    Uri selectedImage=data.getData();
                    String[] filePathColumn={MediaStore.Images.Media.DATA};
                    Cursor cursor=getActivity().getContentResolver().query(selectedImage,filePathColumn,null,null,null);
                    cursor.moveToFirst();
                    int columnIndex=cursor.getColumnIndex(filePathColumn[0]);
                    String picturePath=cursor.getString(columnIndex);
                    cursor.close();
                    receivedImageBitMap= BitmapFactory.decodeFile(picturePath);
                    imgview.setImageBitmap(receivedImageBitMap);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
