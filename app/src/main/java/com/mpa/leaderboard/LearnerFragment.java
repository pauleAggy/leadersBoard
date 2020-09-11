package com.mpa.leaderboard;

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

public class LearnerFragment extends Fragment {
    private ProgressBar mLoadingProgress;
    private RecyclerView rvLearnerLeaders;
    private View mView;


    public LearnerFragment() {
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
        View v = inflater.inflate(R.layout.learner_leader_list, container, false);
        mView=v;
        rvLearnerLeaders = (RecyclerView) v.findViewById(R.id.rv_learner_leader);
        mLoadingProgress = (ProgressBar) v.findViewById(R.id.pb_loading_learner);
        try {
            URL learnerLeaderUrl = ApiHandler.constructUrl("learner");
            new LearnerLeadersQueryTask().execute(learnerLeaderUrl);

        }
        catch (Exception e) {
            Log.d("error", e.getMessage());
        }

        //create the layoutManager for the books (linear in this case, scrolling vertically
        LinearLayoutManager learnerLeadersLayoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvLearnerLeaders.setLayoutManager(learnerLeadersLayoutManager);
        return v;
    }


    @androidx.annotation.RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    public class LearnerLeadersQueryTask extends AsyncTask<URL, Void, String> {

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

            TextView  learnerError = (TextView) mView.findViewById(R.id.learner_error);
            mLoadingProgress.setVisibility(View.INVISIBLE);
            if (result == null) {
                rvLearnerLeaders.setVisibility(View.INVISIBLE);
                learnerError.setVisibility(View.VISIBLE);
            }
            else {
                rvLearnerLeaders.setVisibility(View.VISIBLE);
                learnerError.setVisibility(View.INVISIBLE);
            }
            ArrayList<LearnerLeader> learnerLeaders = ApiHandler.returnLearnerLeadersFromJson(result);

            LearnerLeaderAdapter adapter = new LearnerLeaderAdapter(learnerLeaders);
            rvLearnerLeaders.setAdapter(adapter);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgress.setVisibility(View.VISIBLE);
        }
    }
}
