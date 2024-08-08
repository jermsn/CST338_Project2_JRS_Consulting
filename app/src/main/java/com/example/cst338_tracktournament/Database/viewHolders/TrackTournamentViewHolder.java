package com.example.cst338_tracktournament.Database.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cst338_tracktournament.R;

public class TrackTournamentViewHolder extends RecyclerView.ViewHolder {
    // This is a reference to our recycler item
    private final TextView trainingLogViewItem;

    // Constructor
    private TrackTournamentViewHolder (View trackTournamentLogView) {
        super(trackTournamentLogView);
        trainingLogViewItem = trackTournamentLogView.findViewById(R.id.recyclerItemTextView);
    }

    public void bind(String text) { trainingLogViewItem.setText(text); }

    // This is a reference to our container text box where all the recyclers will be shown
    static TrackTournamentViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_training_recycler_item, parent, false);
        return new TrackTournamentViewHolder(view);
    }
}
