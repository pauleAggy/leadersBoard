package com.mpa.leaderboard;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class LearnerLeaderAdapter extends RecyclerView.Adapter<LearnerLeaderAdapter.LearnerLeaderViewHolder>  {

    ArrayList<LearnerLeader> learnerLeaders;
    public LearnerLeaderAdapter(ArrayList<LearnerLeader> learnerLeaders) {
        this.learnerLeaders = learnerLeaders;
    }
    @Override
    public LearnerLeaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_learner_leader, parent, false);


        return new LearnerLeaderViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(LearnerLeaderViewHolder holder, int position) {
        LearnerLeader learnerLeader = learnerLeaders.get(position);
        holder.bind(learnerLeader);

    }

    @Override
    public int getItemCount() {
        return learnerLeaders.size();
    }


    public class LearnerLeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView learnerLeaderName;
        TextView learnerLeaderResult;

        public LearnerLeaderViewHolder(View itemView) {
            super(itemView);
            learnerLeaderName = (TextView) itemView.findViewById(R.id.learner_leader_name);
            learnerLeaderResult= (TextView) itemView.findViewById(R.id.learner_leader_result);
            itemView.setOnClickListener(this);

        }
        public void bind (LearnerLeader learnerLeader) {
            learnerLeaderName.setText(learnerLeader.name.get());
            learnerLeaderResult.setText(learnerLeader.hours.get()+" Hours,"+learnerLeader.country.get());
        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.d("Click", String.valueOf(position));
            //gets the book from the arrayList
            LearnerLeader selectedLearnerLeader = learnerLeaders.get(position);
            Snackbar.make(view,"Selected "+selectedLearnerLeader.name+" ,"+selectedLearnerLeader.hours+" Hours,"+selectedLearnerLeader.country,3000);
        }
    }
}
