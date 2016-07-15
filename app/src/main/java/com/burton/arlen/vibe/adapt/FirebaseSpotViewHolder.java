package com.burton.arlen.vibe.adapt;

/**
 * Created by arlen on 7/15/16.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.burton.arlen.vibe.Constants;
import com.burton.arlen.vibe.R;
import com.burton.arlen.vibe.model.Spot;
import com.burton.arlen.vibe.ui.SpotDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

public class FirebaseSpotViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    View mView;
    Context mContext;

    public FirebaseSpotViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindSpot(Spot spot) {
        ImageView spotImageView = (ImageView) mView.findViewById(R.id.spotImageView);
        TextView nameTextView = (TextView) mView.findViewById(R.id.spotNameTextView);
        TextView categoryTextView = (TextView) mView.findViewById(R.id.categoryTextView);
        TextView ratingTextView = (TextView) mView.findViewById(R.id.ratingTextView);

        Picasso.with(mContext)
                .load(spot.getImageUrl())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(spotImageView);

        nameTextView.setText(spot.getName());
        categoryTextView.setText(spot.getCategories().get(0));
        ratingTextView.setText("Rating: " + spot.getRating() + "/5");
    }

    @Override
    public void onClick(View view) {
        final ArrayList<Spot> spots = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_RESTAURANTS);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    spots.add(snapshot.getValue(Spot.class));
                }

                int itemPosition = getLayoutPosition();

                Intent intent = new Intent(mContext, SpotDetailActivity.class);
                intent.putExtra("position", itemPosition + "");
                intent.putExtra("spots", Parcels.wrap(spots));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
