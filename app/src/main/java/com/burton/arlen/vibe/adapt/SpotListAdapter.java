package com.burton.arlen.vibe.adapt;

/**
 * Created by arlen on 7/15/16.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.burton.arlen.vibe.R;
import com.burton.arlen.vibe.model.Spot;
import com.burton.arlen.vibe.ui.SpotDetailActivity;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SpotListAdapter extends RecyclerView.Adapter<SpotListAdapter.SpotViewHolder> {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    private ArrayList<Spot> mSpots = new ArrayList<>();
    private Context mContext;

    public SpotListAdapter(Context context, ArrayList<Spot> spots) {
        mContext = context;
        mSpots = spots;
    }

    @Override
    public SpotListAdapter.SpotViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spot_list_item, parent, false);
        SpotViewHolder viewHolder = new SpotViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SpotListAdapter.SpotViewHolder holder, int position) {
        holder.bindSpot(mSpots.get(position));
    }

    @Override
    public int getItemCount() {
        return mSpots.size();
    }

    public class SpotViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.spotImageView) ImageView mSpotImageView;
        @Bind(R.id.spotNameTextView) TextView mNameTextView;
        @Bind(R.id.categoryTextView) TextView mCategoryTextView;
        @Bind(R.id.ratingTextView) TextView mRatingTextView;

        private Context mContext;

        public SpotViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            mContext = itemView.getContext();
            itemView.setOnClickListener(this);
        }

        public void bindSpot(Spot spot) {

            Picasso.with(mContext)
                    .load(spot.getImageUrl())
                    .resize(MAX_WIDTH, MAX_HEIGHT)
                    .centerCrop()
                    .into(mSpotImageView);

            mNameTextView.setText(spot.getName());
            mCategoryTextView.setText(spot.getCategories().get(0));
            mRatingTextView.setText("Rating: " + spot.getRating() + "/5");
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getLayoutPosition();

            Intent intent = new Intent(mContext, SpotDetailActivity.class);
            intent.putExtra("position", itemPosition + "");
            intent.putExtra("spots", Parcels.wrap(mSpots));

            mContext.startActivity(intent);
        }
    }
}
