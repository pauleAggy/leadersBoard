package com.mpa.leaderboard;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class IqLeaderAdapter extends RecyclerView.Adapter<IqLeaderAdapter.IqLeaderViewHolder>  {

    ArrayList<IqLeader> iqLeaders;
    public IqLeaderAdapter(ArrayList<IqLeader> iqLeaders) {
        this.iqLeaders = iqLeaders;
    }
    @Override
    public IqLeaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.item_skill_leader, parent, false);


        return new IqLeaderViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(IqLeaderViewHolder holder, int position) {
        IqLeader iqLeader = iqLeaders.get(position);
        holder.bind(iqLeader);

    }

    @Override
    public int getItemCount() {
        return iqLeaders.size();
    }


    public class IqLeaderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView iqLeaderName;
        TextView iqLeaderResult;


        public IqLeaderViewHolder(View itemView) {
            super(itemView);
            iqLeaderName = (TextView) itemView.findViewById(R.id.iq_leader_name);
            iqLeaderResult= (TextView) itemView.findViewById(R.id.iq_leader_result);
            itemView.setOnClickListener(this);

        }
        public void bind (IqLeader iqLeader) {
            iqLeaderName.setText(iqLeader.name.get());
            iqLeaderResult.setText(iqLeader.score.get()+" Score,"+iqLeader.country.get());
        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.d("Click", String.valueOf(position));
            IqLeader selectedIqLeader = iqLeaders.get(position);
            Snackbar.make(view,"Setected "+selectedIqLeader.name+" ,"+selectedIqLeader.score+" Score,"+selectedIqLeader.country,3000);

        }
    }
}
