package com.example.nwang.mms;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Similiar to SearchResultActivity, browse all through the different publications.
 */
public class BrowseActivity extends AppCompatActivity implements TaskListener {


    private JSONTask asyncTask = new JSONTask();
    private JSONTaskJW asyncTaskJW = new JSONTaskJW();

    private RecyclerView nejmresults;
    private View noresult;
    private CustomAdapter mAdapter;

    private int spinnerValue;
    private String sdate, edate;

    private String publication;
    private String maxendDate;


    private SpinnerQuery newSpinnerQuery = new SpinnerQuery();
    private String amazonURl = "https://ilerxy5ujh.execute-api.us-east-1.amazonaws.com/nejm_stage/search?query=";
    private String amazonURLJW = "https://avelqu6i45.execute-api.us-east-1.amazonaws.com/jwatch_stage/search?query=";
    private String amazonURLFW = "https://avelqu6i45.execute-api.us-east-1.amazonaws.com/jwatch_stage/search?articleClass=Medical%20News";
   // private String amazonURLFW = "https://avelqu6i45.execute-api.us-east-1.amazonaws.com/jwatch_stage/search?query=speciality:pfw";


    private String startQuery, endQuery;
    private String page = "&pageLength=50";
    private String spec;
    private String specquery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        asyncTask.delegate = this;
        asyncTaskJW.delegate = this;

        setContentView(R.layout.activity_search_results);

        Toolbar my_toolbar = (Toolbar) findViewById(R.id.search_results_toolbar);   //set toolbar
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPref = getSharedPreferences("FileName", MODE_PRIVATE);      //set up sharedpreference for drop down spinner
        spinnerValue = sharedPref.getInt("userChoiceSpinner", -1);


        SharedPreferences startPref = getSharedPreferences("startDate", MODE_PRIVATE);  //set up sharepreference for dates
        SharedPreferences.Editor sEditor = startPref.edit();
        sdate = startPref.getString("sDate", "DEFAULT");
        sEditor.remove("sDate");
        sEditor.apply();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        maxendDate = df.format(c.getTime());

        SharedPreferences endPref = getSharedPreferences("endDate", MODE_PRIVATE);
        edate = endPref.getString("eDate", "DEFAULT");

        SharedPreferences pubPref = getSharedPreferences("publications", MODE_PRIVATE);
        publication = pubPref.getString("pubs", "DEFAULT");

        if (publication.equals("NEJM")) {                           //Set different spinners between JW and NEJM
            spec = newSpinnerQuery.SpinnerNEJM(spinnerValue);
            specquery = Uri.encode(spec, "utf-8");
        } else {
            spec = newSpinnerQuery.SpinnerJW(spinnerValue);
            specquery = spec;
        }

        noresult = findViewById(R.id.noResult_view);
        noresult.setVisibility(View.GONE);

        startQuery = getStartDate();
        endQuery = getEndDate();

        //if there specializations are chosen, use spec query
        if (spec != null) {
            if (publication.equals("NEJM")) {
                asyncTask.execute(amazonURl + "has_oa_conclusions:true" + "%20AND%20" + specquery + startQuery + endQuery + page);      //pull JSON data as array from asynctask
            }
            if (publication.equals("JW")) {
                asyncTaskJW.execute(amazonURLJW + specquery + startQuery + endQuery + page);
            }
        } else {
            if (publication.equals("NEJM")) {

                asyncTask.execute(amazonURl + "has_oa_conclusions:true" + "%20AND%20" + startQuery + endQuery + page);
            }
            if (publication.equals("JW")) {
                asyncTaskJW.execute(amazonURLJW + startQuery + endQuery + page);
            }
            if (publication.equals("FW")) {
                asyncTaskJW.execute(amazonURLFW + startQuery + endQuery + page);
            }
        }
        Toast.makeText(BrowseActivity.this, "Searching", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onFinished(List result) {
        nejmresults = (RecyclerView) findViewById(R.id.recycler_view);
        nejmresults.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));    //add divider
        mAdapter = new CustomAdapter(BrowseActivity.    this, result);          //insert results to the recylerview ui through an adapter
        nejmresults.setLayoutManager(new LinearLayoutManager(BrowseActivity.this));
        nejmresults.setAdapter(mAdapter);
        if (mAdapter.getItemCount() == 0) {                     //if there are no result, change view to a no result listview
            noresult.setVisibility(View.VISIBLE);
        }

    }

    private String getStartDate() {
        String sQuery;
        if (sdate.equals("DEFAULT")) {
            sQuery = "";
        } else {
            sQuery = "&startDate=" + sdate;
        }
        return sQuery;
    }

    private String getEndDate() {
        String eQuery;
        if (edate.equals("DEFAULT")) {
            eQuery = "&endDate=" + maxendDate;
        } else {
            eQuery = "&endDate=" + edate;
        }
        return eQuery;

    }


}
