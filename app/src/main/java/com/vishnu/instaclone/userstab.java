package com.vishnu.instaclone;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class userstab extends Fragment implements AdapterView.OnItemClickListener {

    private ListView lview;
    private ArrayList<String> arrayList;
    private ArrayAdapter arrayAdapter;
    public userstab() {
        // Required empty public constructor
    }
private TextView s;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View user= inflater.inflate(R.layout.fragment_userstab, container, false);
        lview=user.findViewById(R.id.lview);
        s=user.findViewById(R.id.textView);
        arrayList=new ArrayList();
        arrayAdapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1,arrayList);
        lview.setOnItemClickListener(userstab.this);
        ParseQuery<ParseUser> parseQuery=ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username",ParseUser.getCurrentUser().getUsername());
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null){
                    if(objects.size()>0){
                        for(ParseUser obj:objects){
                            arrayList.add(obj.getUsername());
                        }
                        lview.setAdapter(arrayAdapter);
                        //lview.;
                        s.animate().alpha(0).setDuration(1000);
                        lview.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
        return user;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getContext(),userposts.class);
        intent.putExtra("username",arrayList.get(position));
        startActivity(intent);
    }
}
