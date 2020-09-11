package com.mpa.leaderboard;

import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class IqLeaderListActivity extends AppCompatActivity  {
    private ProgressBar mLoadingProgress;
    private RecyclerView rvIqLeaders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.iq_leader_list);
        rvIqLeaders = (RecyclerView) findViewById(R.id.rv_iq_leader);
        mLoadingProgress = (ProgressBar) findViewById(R.id.pb_loading_iq);
        try {
            URL iqLeadersUrl = ApiHandler.constructUrl("iq");
            new IqLeadersQueryTask().execute(iqLeadersUrl);

        }
        catch (Exception e) {
            Log.d("error", e.getMessage());
        }

        //create the layoutManager for the books (linear in this case, scrolling vertically
        LinearLayoutManager iqLeaderLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvIqLeaders.setLayoutManager(iqLeaderLayoutManager);


    }


    public class IqLeadersQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL = urls[0];
            String result = null;
            try {
                result = ApiHandler.collectJson(searchURL);
            }
            catch (IOException e) {
                Log.e("Error", e.getMessage());
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            TextView iqLeaderError = (TextView) findViewById(R.id.iq_error);
            mLoadingProgress.setVisibility(View.INVISIBLE);
            if (result == null) {
                rvIqLeaders.setVisibility(View.INVISIBLE);
                iqLeaderError.setVisibility(View.VISIBLE);
            }
            else {
                rvIqLeaders.setVisibility(View.VISIBLE);
                iqLeaderError.setVisibility(View.INVISIBLE);
            }
            ArrayList<IqLeader> books = ApiHandler.returnIqLeadersFromJson(result);

            IqLeaderAdapter adapter = new IqLeaderAdapter(books);
            rvIqLeaders.setAdapter(adapter);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgress.setVisibility(View.VISIBLE);
        }
    }
}
