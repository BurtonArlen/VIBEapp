package com.burton.arlen.vibe.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.burton.arlen.vibe.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class SpotDetailFragment extends Fragment {


    public SpotDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_spot_detail, container, false);
    }

}
