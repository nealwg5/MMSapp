package com.example.nwang.mms;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * asynctask
 */

public class JSONTask extends AsyncTask<String, String, List> {

    List<DataNejm> nejmlist = new ArrayList<>();
    public TaskListener delegate = null;


    @Override
    protected List doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();

            int status = connection.getResponseCode();
            connection.setRequestMethod("GET");         //open connection as a get
            connection.connect();
            InputStream stream = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(stream));
            StringBuffer buffer = new StringBuffer();

            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }

            String finalJson = buffer.toString();                           //get the JSON into a readable format

            JSONObject parentObject = new JSONObject(finalJson);            //get the JSON results
            JSONObject jObject = parentObject.getJSONObject("json");
            JSONObject resultObject = jObject.getJSONObject("results");
            JSONArray resultArray = resultObject.getJSONArray("result");
            for (int i = 0; i < resultArray.length(); i++) {
                DataNejm nejm1 = new DataNejm();
                JSONObject finalObject = resultArray.getJSONObject(i);      //set the results to an array list
                nejm1.pubtitle = finalObject.getString("title");
                nejm1.pubdate = finalObject.getString("pubdate");
                nejm1.pubdoi = finalObject.getString("doi");
                nejm1.pubOA = finalObject.getString("oa_conclusion");
                nejmlist.add(nejm1);
            }

            connection.disconnect();
            return nejmlist;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * After executing, use delegate to call onFinished for activity
     * @param result Parsed JSOn data in an arraylist
     */
    @Override
    protected void onPostExecute(List result) {
        super.onPostExecute(result);
        delegate.onFinished(result);


    }


}
