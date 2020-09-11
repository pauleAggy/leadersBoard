package com.mpa.leaderboard;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class SkillFragment extends Fragment {
    private ProgressBar mLoadingProgress;
    private RecyclerView rvIqLeaders;
    private View mView;


    public SkillFragment() {
        // Required empty public constructor
    }


    /**
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */
    @androidx.annotation.RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.iq_leader_list, container, false);
        mView=v;
        rvIqLeaders = (RecyclerView) v.findViewById(R.id.rv_iq_leader);
        mLoadingProgress = (ProgressBar) v.findViewById(R.id.pb_loading_iq);
        try {
            URL iqLeaderUrl = ApiHandler.constructUrl("iq");
            new IqLeadersQueryTask().execute(iqLeaderUrl);

        }
        catch (Exception e) {
            Log.d("error", e.getMessage());
        }

        //create the layoutManager for the books (linear in this case, scrolling vertically
        LinearLayoutManager iqLeadersLayoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvIqLeaders.setLayoutManager(iqLeadersLayoutManager);
        return v;
    }


    @androidx.annotation.RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
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

            TextView  iqError = (TextView) mView.findViewById(R.id.iq_error);
            mLoadingProgress.setVisibility(View.INVISIBLE);
            if (result == null) {
                rvIqLeaders.setVisibility(View.INVISIBLE);
                iqError.setVisibility(View.VISIBLE);
            }
            else {
                rvIqLeaders.setVisibility(View.VISIBLE);
                iqError.setVisibility(View.INVISIBLE);
            }
            ArrayList<IqLeader> iqLeaders = ApiHandler.returnIqLeadersFromJson(result);

            IqLeaderAdapter adapter = new IqLeaderAdapter(iqLeaders);
            rvIqLeaders.setAdapter(adapter);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgress.setVisibility(View.VISIBLE);
        }
    }
}
