package com.example.nwang.mms;

import android.app.DatePickerDialog;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * The default activity that is shown when the app starts
 */
public class MainActivity extends AppCompatActivity {

    private EditText fromDate;
    private EditText toDate;
    private Calendar c = Calendar.getInstance();
    private Spinner dropdown;
    private SpinnerQuery spinnerItems = new SpinnerQuery();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar my_toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(my_toolbar);

        getSupportActionBar().setTitle(R.string.my_toolbar);
        getSupportActionBar().setSubtitle(R.string.my_tb_subtitle);
        getSupportActionBar().setIcon(R.drawable.ic_mms);


        fromDate = (EditText) findViewById(R.id.fromDate);
        fromDate.setInputType(InputType.TYPE_NULL);                         //Make the date uneditable and have to click the datepicker
        fromDate.requestFocus();
        toDate = (EditText) findViewById(R.id.toDate);
        toDate.setInputType(InputType.TYPE_NULL);
        toDate.requestFocus();

        dropdown = (Spinner) findViewById(R.id.spinnerTopic);
        dropdown.setVisibility(View.VISIBLE);

        setSpinner();
        setstartDatePicker();
        setendDatePicker();

        cleanPref();
    }


    /**
     * When radiobutton is clicked, set the spinner option and save the journal preference
     */
    public void onRadioButtonClicked(View view) {
        String pub = "NEJM";
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioNejm:
                if (checked)
                    dropdown.setVisibility(View.VISIBLE);
                    pub = "NEJM";
                setSpinner();
                break;
            case R.id.radioJW:
                if (checked) pub = "JW";
                dropdown.setVisibility(View.VISIBLE);
                setSpinnerJW();
                break;
            case R.id.radioFW:
                if (checked) pub = "FW";
                dropdown.setVisibility(View.GONE);
                break;
        }
        SharedPreferences pref = getApplicationContext().getSharedPreferences("publications", MODE_PRIVATE);
        SharedPreferences.Editor pubEditor = pref.edit();
        pubEditor.putString("pubs",pub);
        pubEditor.apply();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_1:
                Toast.makeText(MainActivity.this, "Refresh", Toast.LENGTH_SHORT).show();
                finish();
                startActivity(getIntent());
                break;

            case R.id.menu_2:
                Toast.makeText(MainActivity.this, "Clearing Suggestions", Toast.LENGTH_SHORT).show();
                SearchRecentSuggestions suggestions =
                        new SearchRecentSuggestions(this,
                                RecentSuggestionsProvider.AUTHORITY,
                                RecentSuggestionsProvider.MODE);
                suggestions.clearHistory();
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    private void setSpinner() {
        String[] items = spinnerItems.NEJMitem();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);


        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                int usersChoice = dropdown.getSelectedItemPosition();
                SharedPreferences sharedPref = getSharedPreferences("FileName", 0);
                SharedPreferences.Editor prefEditor = sharedPref.edit();
                prefEditor.putInt("userChoiceSpinner", usersChoice);
                prefEditor.apply();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing.
            }
        });
    }


    private void setSpinnerJW() {
        String[] items = spinnerItems.JWitem();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);


        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            public void onItemSelected(AdapterView<?> parent,
                                       View view, int position, long id) {
                int usersChoice = dropdown.getSelectedItemPosition();
                SharedPreferences sharedPref = getSharedPreferences("FileName", 0);
                SharedPreferences.Editor prefEditor = sharedPref.edit();
                prefEditor.putInt("userChoiceSpinner", usersChoice);
                prefEditor.apply();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing.
            }
        });
    }


    private void setstartDatePicker() {
        fromDate.setOnClickListener(new View.OnClickListener() {

            int sYear = c.get(Calendar.YEAR);
            int sMonth = c.get(Calendar.MONTH);
            int sDay = c.get(Calendar.DAY_OF_MONTH);

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                DatePickerDialog.OnDateSetListener s = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        sDay = view.getDayOfMonth();
                        sMonth = view.getMonth();
                        sYear = view.getYear();


                        c.set(Calendar.YEAR, sYear);
                        c.set(Calendar.MONTH, sMonth);
                        c.set(Calendar.DAY_OF_MONTH, sDay);
                        Date sDate = c.getTime();

                        final String FORMAT = "yyyy-MM-dd";
                        String sData = new SimpleDateFormat(FORMAT).format(sDate);
                        fromDate.setText(sData);
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("startDate", MODE_PRIVATE);
                        SharedPreferences.Editor sEditor = pref.edit();
                        sEditor.putString("sDate", sData);
                        sEditor.apply();
                    }

                };

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, s,
                        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                        .get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.show();



            }
        });


    }


    private void setendDatePicker() {
        toDate.setOnClickListener(new View.OnClickListener() {

            int eYear = c.get(Calendar.YEAR);
            int eMonth = c.get(Calendar.MONTH);
            int eDay = c.get(Calendar.DAY_OF_MONTH);

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                DatePickerDialog.OnDateSetListener e = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {

                        eDay = view.getDayOfMonth();
                        eMonth = view.getMonth();

                        eYear = view.getYear();
                        c.set(Calendar.YEAR, eYear);
                        c.set(Calendar.MONTH, eMonth);
                        c.set(Calendar.DAY_OF_MONTH, eDay);
                        Date eDate = c.getTime();

                        final String FORMAT = "yyyy-MM-dd";
                        String eData = new SimpleDateFormat(FORMAT).format(eDate);
                        toDate.setText(eData);
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("endDate", MODE_PRIVATE);
                        SharedPreferences.Editor eEditor = pref.edit();
                        eEditor.putString("eDate", eData);
                        eEditor.apply();

                    }
                };

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, e,
                        c.get(Calendar.YEAR), c.get(Calendar.MONTH), c
                        .get(Calendar.DAY_OF_MONTH));
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.show();

            }
        });

    }


    public void onAllButtonClicked(View view) {
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, BrowseActivity.class);
        startActivity(intent);
    }


    /**
     * Set preference back to default
     */
    private void cleanPref()
    {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("publications", MODE_PRIVATE);
        SharedPreferences.Editor pubEditor = pref.edit();
        pubEditor.putString("pubs", "NEJM");
        pubEditor.apply();

        SharedPreferences cleanPref2 = getSharedPreferences("endDate", MODE_PRIVATE);
        SharedPreferences.Editor clean2 = cleanPref2.edit();
        clean2.remove("eDate");
        clean2.apply();

        SharedPreferences cleanPref1 = getSharedPreferences("startDate", MODE_PRIVATE);
        SharedPreferences.Editor clean1 = cleanPref1.edit();
        clean1.remove("sDate");
        clean1.apply();
    }


}
