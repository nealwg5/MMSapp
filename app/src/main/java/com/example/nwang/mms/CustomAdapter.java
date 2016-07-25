package com.example.nwang.mms;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/*
Adapter to set JSON data to the appropriate field
 */
public class CustomAdapter extends RecyclerView.Adapter<MyHolder> {

    private Context context;
    private LayoutInflater inflater;
    private String urlNEJM="http://www.nejm.org/doi/full/";
    private String urlFW="http://www.jwatch.org/";
    private String publink;
    private String pubs;
    private String fullJWlink = "10.1056/nejm-jw.";
    List<DataNejm> data= Collections.emptyList();
    DataNejm current;
    DataNejm test;
    int currentPos=0;


    public CustomAdapter(Context context, List<DataNejm> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.custom_row, parent,false);
        MyHolder holder=new MyHolder(view);
        SharedPreferences pubPref = context.getSharedPreferences("publications", context.MODE_PRIVATE);
        pubs = pubPref.getString("pubs", "DEFAULT");
        return holder;
    }


    /*
    Really quick and dirty method I used here. The recyclerview will call this function depending on how many results is displayed on the screen.
    It will continuously call as the user scroll down or up as a new result is shown. This lead to the problem of setting clickable links to the correct spot as position contiguously change.
    I have hard setted the uri as text to the button then hid the uri with another button in the UI.
     */
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final MyHolder myHolder= holder;
        current=data.get(position);
        myHolder.title.setText(current.pubtitle);
        myHolder.date.setText("Publication Date:" + current.pubdate);
        myHolder.doi.setText(current.pubdoi);

        if (pubs.equals("NEJM")) {
            myHolder.oa.setText("Conclusion:" + current.pubOA);
        }
        if (pubs.equals("JW"))
        {

            myHolder.oa.setText("Abstract:" + current.pubOA);
        }
        if (pubs.equals("FW"))
        {
            myHolder.oa.setText("Abstract:" + current.pubOA);
        }

        myHolder.doi.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                 String test = myHolder.doi.getText().toString();
                if (pubs.equals("NEJM"))
                {
                   publink = urlNEJM + test;
                }
                else
                {

                    String newcopy = test.replaceAll(fullJWlink,"");
                    publink = urlFW + newcopy;
                }

                Uri uri =Uri.parse(publink);
               Intent intent = new Intent(Intent.ACTION_VIEW, uri);
               context.startActivity(intent);
            }
        });




    }


    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        }
        return 0;
    }






}
