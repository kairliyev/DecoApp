package nextstep.kz.deco.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import nextstep.kz.deco.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllPointsListFragment extends Fragment {

    private RecyclerView shops_list;

    public AllPointsListFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_all_points_list, container, false);
        shops_list = view.findViewById(R.id.shops_list);

        return view;
    }

}
