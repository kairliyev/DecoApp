package nextstep.kz.deco.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import nextstep.kz.deco.Activities.EventItemActivity;
import nextstep.kz.deco.Data.Events;
import nextstep.kz.deco.R;


public class TrainingFragment extends Fragment {


    public TrainingFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_training, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rv = view.findViewById(R.id.fragment_train_main_rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);


//        initializeData(events);
//        EventsFragment.RVAdapter rvAdapter = new EventsFragment.RVAdapter(events);
        // rv.setAdapter(rvAdapter);

    }

    public static class RVAdapter extends RecyclerView.Adapter<TrainingFragment.RVAdapter.TrainViewHolder> {


        @NonNull
        @Override
        public TrainingFragment.RVAdapter.TrainViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.train_people_list_item, viewGroup, false);
            TrainingFragment.RVAdapter.TrainViewHolder pvh = new TrainingFragment.RVAdapter.TrainViewHolder(v);
            return pvh;
        }

        @Override
        public void onBindViewHolder(@NonNull TrainingFragment.RVAdapter.TrainViewHolder personViewHolder, final int i) {

        }

        @Override
        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public int getItemCount() {
            return 0;
        }


        RVAdapter() {

        }

        public static class TrainViewHolder extends RecyclerView.ViewHolder {
            CardView cv;
            TextView eventName;
            TextView eventDate;
            ImageView personPhoto;

            TrainViewHolder(View itemView) {
                super(itemView);

            }
        }
    }
}
