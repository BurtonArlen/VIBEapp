package com.burton.arlen.vibe.ui;

/**
 * Created by arlen on 7/15/16.
 */
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.burton.arlen.vibe.Constants;
import com.burton.arlen.vibe.R;
import com.burton.arlen.vibe.adapt.FirebaseSpotViewHolder;
import com.burton.arlen.vibe.model.Spot;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SavedSpotListActivity extends AppCompatActivity {
    private DatabaseReference mSpotReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Bind(R.id.recyclerView) RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_spots);
        ButterKnife.bind(this);

        setUpFirebaseAdapter();
    }

    private void setUpFirebaseAdapter() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        Query query = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_SPOTS).child(uid);

        mFirebaseAdapter = new FirebaseRecyclerAdapter<Spot, FirebaseSpotViewHolder>
                (Spot.class, R.layout.spot_list_item, FirebaseSpotViewHolder.class,
                        mSpotReference) {

            @Override
            protected void populateViewHolder(FirebaseSpotViewHolder viewHolder, Spot model, int position) {
                viewHolder.bindSpot(model);
            }
        };
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}
