package nextstep.kz.deco.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.Person;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerViewAccessibilityDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nextstep.kz.deco.Activities.EventItemActivity;
import nextstep.kz.deco.Data.Events;
import nextstep.kz.deco.R;


public class EventsFragment extends Fragment {


    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView rv = view.findViewById(R.id.fragment_events_main_rv);
        rv.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rv.setLayoutManager(layoutManager);
        ArrayList<Events> events = new ArrayList<>();
        initializeData(events);
        RVAdapter rvAdapter = new RVAdapter(events);
        rv.setAdapter(rvAdapter);

    }

    private void initializeData(ArrayList<Events> events) {

        events.add(new Events("Decathlon:Triathlon ", "28 июля — 28 июля", "5000 тенге","Выдача стартовых пакетов будет проходить с 6:30 до 7:30."
                ,"https://www.decathlon.ru/","Алматы","https://mhealth.ru/media/images/page/2017/12/20/4a9a8fbc4222478aa47755781a46d111.jpg", R.drawable.dec_tri));
        events.add(new Events("Decathlon: Осенний забег ", "28 июля — 28 июля", "5000 тенге","Осенний забег состоится 10 ноября на побережье озера Сайран и соберет 1000 любителей бега. " +
                "Соревнование пройдет уже в пятый раз и станет последним в этом году в цикле подготовки к «Алматы марафону». Для участников в возрасте от 10 до 14 лет " +
                "представлена дистанция в 3 км, спортсменам старше 14 предстоит преодолеть" +
                " 11 км бегом или скандинавской ходьбой","https://www.decathlon.ru/","Алматы","http://www.tcsamsterdammarathon.nl/files/2011/12/start700x300.jpg", R.drawable.dec_run));
        events.add(new Events("Decathlon:Snowboarding ", "28 июля — 28 июля", "5000 тенге","Выдача стартовых пакетов будет проходить с 6:30 до 7:30."
                ,"https://www.decathlon.ru/","Алматы","http://www.whc2002.com/wp-content/uploads/2018/07/Snowboarding-1.jpg", R.drawable.dec_snow));
    }

    public static class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {
        ArrayList<Events> events;

        @NonNull
        @Override
        public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.events_item_card_view, viewGroup, false);
            PersonViewHolder pvh = new PersonViewHolder(v);
            return pvh;
        }

        @Override
        public void onBindViewHolder(@NonNull PersonViewHolder personViewHolder, final int i) {
            personViewHolder.eventName.setText(events.get(i).getName());
            personViewHolder.eventDate.setText(events.get(i).getDate());
            Picasso.get().load(events.get(i).getImage()).into(personViewHolder.personPhoto);
            personViewHolder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent in = new Intent(v.getContext(), EventItemActivity.class);
                    Bundle extras = new Bundle();
                    extras.putString("e_name",events.get(i).getName());
                    extras.putString("e_date",events.get(i).getDate());
                    extras.putString("e_price",events.get(i).getPrice());
                    extras.putString("e_des",events.get(i).getDescription());
                    extras.putString("e_site",events.get(i).getSite());
                    extras.putString("e_city",events.get(i).getCity());
                    extras.putString("e_image",events.get(i).getImage());
                    in.putExtras(extras);
                    v.getContext().startActivity(in);
                }
            });
        }

        @Override
        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public int getItemCount() {
            return events.size();
        }



        RVAdapter(ArrayList<Events> events) {
            this.events = events;
        }

        public static class PersonViewHolder extends RecyclerView.ViewHolder {
            CardView cv;
            TextView eventName;
            TextView eventDate;
            ImageView personPhoto;

            PersonViewHolder(View itemView) {
                super(itemView);
                cv = itemView.findViewById(R.id.cv);
                eventName = itemView.findViewById(R.id.event_name);
                eventDate = itemView.findViewById(R.id.event_date);
                personPhoto = itemView.findViewById(R.id.event_photo);
            }
        }
    }
}
