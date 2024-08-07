package com.example.cst338_tracktournament.Database.viewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.cst338_tracktournament.Database.entities.UserTrainingLog;

public class TrackTournamentAdapter  extends ListAdapter<UserTrainingLog, TrackTournamentViewHolder> {

    public TrackTournamentAdapter(@NonNull DiffUtil.ItemCallback<UserTrainingLog> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public TrackTournamentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return TrackTournamentViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull TrackTournamentViewHolder holder, int position) {
        UserTrainingLog current = getItem(position);
        holder.bind(current.toString());
    }

    public static class TrainingLogDiff extends DiffUtil.ItemCallback<UserTrainingLog> {
        @Override
        public boolean areItemsTheSame(@NonNull UserTrainingLog oldItem, @NonNull UserTrainingLog newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull UserTrainingLog oldItem, @NonNull UserTrainingLog newItem) {
            return oldItem.equals(newItem);
        }
    }

}
