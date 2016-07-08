package com.example.nwang.mms;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


public class MyHolder extends RecyclerView.ViewHolder {

    protected TextView title, date, doi, oa;

    // create constructor to get widget reference
    public MyHolder(View itemView) {
        super(itemView);
        title = (TextView) itemView.findViewById(R.id.pubTitle);
        date = (TextView) itemView.findViewById(R.id.pubDate);
        doi = (TextView) itemView.findViewById(R.id.pubDoi);
        oa = (TextView) itemView.findViewById(R.id.pubOA);

    }


}
