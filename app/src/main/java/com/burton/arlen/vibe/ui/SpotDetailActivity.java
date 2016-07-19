package com.burton.arlen.vibe.ui;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.burton.arlen.vibe.R;
import com.burton.arlen.vibe.adapt.SpotPagerAdapter;
import com.burton.arlen.vibe.model.Spot;

import org.parceler.Parcels;
import java.util.ArrayList;
import butterknife.Bind;
import butterknife.ButterKnife;


public class SpotDetailActivity extends AppCompatActivity {
    @Bind(R.id.viewPager) ViewPager mViewPager;
    private SpotPagerAdapter adapterViewPager;
    ArrayList<Spot> mSpots = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_detail);
        ButterKnife.bind(this);

        mSpots = Parcels.unwrap(getIntent().getParcelableExtra("spots"));
        int startingPosition = Integer.parseInt(getIntent().getStringExtra("position"));

        adapterViewPager = new SpotPagerAdapter(getSupportFragmentManager(), mSpots);
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(startingPosition);

    }
}
