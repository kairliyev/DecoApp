package nextstep.kz.deco.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import nextstep.kz.deco.Fragments.AllPointsListFragment;
import nextstep.kz.deco.Fragments.EventsFragment;
import nextstep.kz.deco.Fragments.MapFragment;
import nextstep.kz.deco.R;
import nextstep.kz.deco.Fragments.TrainingFragment;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton list_all;
    private ImageButton filter;
    private ImageButton again_map;

    private ViewGroup list_all_btn_layout;
    private ViewGroup again_map_btn_layout;

    private BottomNavigationView bottomNavigation;
    private Fragment mapF;
    private Fragment eventsF;
    private Fragment trainingF;
    private Fragment active;
    private Fragment allpointslistF;
    private android.support.v4.app.FragmentManager fragmentManager;
    private android.support.v4.app.FragmentTransaction transaction;


    int eF = 0;
    int tF = 0;
    int aplF = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        list_all_btn_layout = findViewById(R.id.list_all_layout);
        again_map_btn_layout = findViewById(R.id.again_map_layout);


        list_all = findViewById(R.id.list_all);
        again_map = findViewById(R.id.again_map);

        bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.inflateMenu(R.menu.navigation_menu);

        list_all_btn_layout.setVisibility(View.VISIBLE);
        again_map_btn_layout.setVisibility(View.GONE);


        fragmentManager = getSupportFragmentManager();
        mapF = new MapFragment();
        transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container, mapF).commit();
        active = mapF;
        onBottombar();


        list_all.setOnClickListener(this);
        again_map.setOnClickListener(this);

    }

    private void onBottombar() {
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                transaction = fragmentManager.beginTransaction();
                switch (id) {
                    case R.id.action_map:
                        transaction.hide(active).show(mapF).commit();
                        active = mapF;
                        break;
                    case R.id.action_events:
                        if (eF == 0) {
                            eventsF = new EventsFragment();
                            transaction.add(R.id.container, eventsF);
                            eF++;
                        }
                        transaction.hide(active).show(eventsF).commit();
                        active = eventsF;
                        break;
                    case R.id.action_training:
                        if (tF == 0) {
                            trainingF = new TrainingFragment();
                            transaction.add(R.id.container, trainingF);
                            tF++;
                        }
                        transaction.hide(active).show(trainingF).commit();
                        active = trainingF;
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.list_all:
                list_all_btn_layout.setVisibility(View.GONE);
                again_map_btn_layout.setVisibility(View.VISIBLE);
                transaction = fragmentManager.beginTransaction();
                if (aplF == 0) {
                    allpointslistF = new AllPointsListFragment();
                    transaction.add(R.id.container, allpointslistF);
                    aplF++;
                }
                transaction.hide(active).show(allpointslistF).commit();
                active = allpointslistF;
                break;
            case R.id.again_map:
                list_all_btn_layout.setVisibility(View.VISIBLE);
                again_map_btn_layout.setVisibility(View.GONE);
                transaction = fragmentManager.beginTransaction();
                transaction.hide(active).show(mapF).commit();
                active = mapF;
                break;
        }
    }
}
