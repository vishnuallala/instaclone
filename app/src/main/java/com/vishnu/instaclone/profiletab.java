package com.vishnu.instaclone;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class profiletab extends Fragment {
    private EditText pname,bio,profession,hobbies,fsport;
    private Button update;


    public profiletab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_profiletab, container, false);
        pname=view.findViewById(R.id.pname);
        bio=view.findViewById(R.id.bio);
        profession=view.findViewById(R.id.profession);
        hobbies=view.findViewById(R.id.hobbies);
        fsport=view.findViewById(R.id.fsport);
        update=view.findViewById(R.id.update);

       final  ParseUser profile=ParseUser.getCurrentUser();
       if(profile.get("pname")==null) {
           pname.setHint("enter profilename");
       }else {
           pname.setText(profile.get("pname").toString());
       }
       if(profile.get("bio")==null){
           bio.setHint("bio");
       }
       else {
                      bio.setText(profile.get("bio").toString());
       }
       if(profile.get("profession")==null){
           profession.setHint("profession");
       }
       else {
           profession.setText(profile.get("profession").toString());
       }
       if(profile.get("hobbies")==null)
       {
           hobbies.setHint("Hobbies");
      }
       else {
           hobbies.setText(profile.get("hobbies").toString());
       }
       if(profile.get("fsport")==null)
       {
           fsport.setHint("favourite sprot");
       }
       else {
           fsport.setText(profile.get("fsport").toString());
       }
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.put("pname", pname.getText().toString());
                profile.put("bio", bio.getText().toString());
                profile.put("profession", profession.getText().toString());
                profile.put("hobbies", hobbies.getText().toString());
                profile.put("fsport", fsport.getText().toString());

                profile.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Toast.makeText(getContext(), "updated successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        return view;
    }

}
