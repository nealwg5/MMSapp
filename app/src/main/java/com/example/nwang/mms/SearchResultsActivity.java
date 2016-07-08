package com.example.nwang.mms;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class SearchResultsActivity extends AppCompatActivity implements TaskListener {


    private JSONTask asyncTask = new JSONTask();
    private JSONTaskJW asyncTaskJW = new JSONTaskJW();

    private RecyclerView nejmresults;
    private View noresult;
    private CustomAdapter mAdapter;

    private int spinnerValue;
    private String sdate, edate;

    private String publication;


    private SpinnerQuery newSpinnerQuery = new SpinnerQuery();
    private String amazonURl = "https://ilerxy5ujh.execute-api.us-east-1.amazonaws.com/nejm_stage/search?query=";
    private String amazonURLJW = "https://avelqu6i45.execute-api.us-east-1.amazonaws.com/jwatch_stage/search?query=";
    private String amazonURLFW = "https://avelqu6i45.execute-api.us-east-1.amazonaws.com/jwatch_stage/search?articleClass=Medical%20News";

    private String startQuery, endQuery;
    private String spec;
    private String maxendDate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        asyncTask.delegate = this;
       asyncTaskJW.delegate= this;

        setContentView(R.layout.activity_search_results);



        Toolbar my_toolbar = (Toolbar) findViewById(R.id.search_results_toolbar);
        setSupportActionBar(my_toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SharedPreferences sharedPref = getSharedPreferences("FileName", MODE_PRIVATE);
        spinnerValue = sharedPref.getInt("userChoiceSpinner", -1);


        SharedPreferences startPref = getSharedPreferences("startDate", MODE_PRIVATE);
        SharedPreferences.Editor sEditor = startPref.edit();
        sdate = startPref.getString("sDate", "DEFAULT");
        sEditor.remove("sDate");
        sEditor.apply();


        SharedPreferences endPref = getSharedPreferences("endDate", MODE_PRIVATE);
        edate = endPref.getString("eDate", "DEFAULT");

        SharedPreferences pubPref = getSharedPreferences("publications", MODE_PRIVATE);
        publication = pubPref.getString("pubs", "DEFAULT");



        noresult = findViewById(R.id.noResult_view);
        noresult.setVisibility(View.GONE);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        maxendDate = df.format(c.getTime());



        Intent searchIntent = getIntent();
        if (Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {
            String query = searchIntent.getStringExtra(SearchManager.QUERY);

            SearchRecentSuggestions suggestions =
                    new SearchRecentSuggestions(this,
                            RecentSuggestionsProvider.AUTHORITY,
                            RecentSuggestionsProvider.MODE);
            suggestions.saveRecentQuery(query, null);

            startQuery = getStartDate();
            endQuery = getEndDate();

            if(publication.equals("NEJM"))
            {
                spec = newSpinnerQuery.SpinnerNEJM(spinnerValue);
            }
            else
            {
                spec = newSpinnerQuery.SpinnerJW(spinnerValue);
            }
            if (query != null) {
                getSupportActionBar().setTitle(query);
                String searchquery = Uri.encode(query, "utf-8");
                String specquery = Uri.encode(spec, "utf-8");
                if (spec != null) {
                    if (publication.equals("NEJM")) {
                       asyncTask.execute(amazonURl + "has_oa_conclusions:true" + "%20AND%20" + specquery + "%20AND%20fullText:" + searchquery + startQuery + endQuery);
//                        asyncTask.executeOnExecutor(asyncTask.THREAD_POOL_EXECUTOR,amazonURl + "has_oa_conclusions:true" + "%20AND%20" + specquery + "%20AND%20fullText:" + searchquery + startQuery + endQuery);
                    }
                    if (publication.equals("JW")) {
                        asyncTaskJW.execute(amazonURLJW + searchquery + "%20AND%20" + specquery + startQuery + endQuery);
                    }
                } else {
                    if (publication.equals("NEJM")) {

                        asyncTask.execute(amazonURl + "has_oa_conclusions:true" +"%20AND%20fullText:" + searchquery + startQuery + endQuery);
                    }
                    if (publication.equals("JW")) {
                        asyncTaskJW.execute(amazonURLJW + "%20AND%20" + searchquery + startQuery + endQuery);
                    }
                    if (publication.equals("FW")) {
                        asyncTaskJW.execute(amazonURLFW + "&query=" + searchquery + startQuery + endQuery);
//                        asyncTaskJW.executeOnExecutor(asyncTaskJW.THREAD_POOL_EXECUTOR,amazonURLFW + "&query=" + searchquery + startQuery + endQuery);
//                        asyncTask.executeOnExecutor(asyncTask.THREAD_POOL_EXECUTOR,amazonURl + "has_oa_conclusions:true" +"%20AND%20fullText:" + searchquery + startQuery + endQuery);
                    }

                }
                Toast.makeText(SearchResultsActivity.this, query, Toast.LENGTH_SHORT).show();
            }
        }
    }


    private String getStartDate() {
        String sQuery;
        if (sdate.equals("DEFAULT")) {
            sQuery = "";
        } else {
            sQuery = "&startDate=" + sdate;
        }
        System.out.println(sQuery);
        return sQuery;
    }

    private String getEndDate() {
        String eQuery;
        if (edate.equals("DEFAULT")) {
            eQuery = "&endDate=" + maxendDate;
        } else {
            eQuery = "&endDate=" + edate;
        }
        System.out.println(eQuery);
        return eQuery;

    }


    @Override
    public void onFinished(List result) {
        System.out.println(result);
        nejmresults = (RecyclerView) findViewById(R.id.recycler_view);
        nejmresults.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));
        mAdapter = new CustomAdapter(SearchResultsActivity.this, result);
        nejmresults.setLayoutManager(new LinearLayoutManager(SearchResultsActivity.this));
        nejmresults.setAdapter(mAdapter);
        if (mAdapter.getItemCount() == 0) {
            noresult.setVisibility(View.VISIBLE);
        }
    }


}





