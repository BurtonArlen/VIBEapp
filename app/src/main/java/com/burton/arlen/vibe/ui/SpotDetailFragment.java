package com.burton.arlen.vibe.ui;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.burton.arlen.vibe.Constants;
import com.burton.arlen.vibe.R;
import com.burton.arlen.vibe.model.Spot;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;


public class SpotDetailFragment extends Fragment implements View.OnClickListener {
    private static final int MAX_WIDTH = 400;
    private static final int MAX_HEIGHT = 300;
    @Bind(R.id.spotImageView) ImageView mImageLabel;
    @Bind(R.id.spotNameTextView) TextView mNameLabel;
    @Bind(R.id.cuisineTextView) TextView mCategoriesLabel;
    @Bind(R.id.ratingTextView) TextView mRatingLabel;
    @Bind(R.id.websiteTextView) TextView mWebsiteLabel;
    @Bind(R.id.phoneTextView) TextView mPhoneLabel;
    @Bind(R.id.addressTextView) TextView mAddressLabel;
    @Bind(R.id.saveSpotButton) Button mSaveSpotButton;

    private Spot mSpot;

    public static SpotDetailFragment newInstance(Spot spot) {
        SpotDetailFragment spotDetailFragment = new SpotDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("spot", Parcels.wrap(spot));
        spotDetailFragment.setArguments(args);
        return spotDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSpot = Parcels.unwrap(getArguments().getParcelable("spot"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spot_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext())
                .load(mSpot.getImageUrl())
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mImageLabel);

        mNameLabel.setText(mSpot.getName());
        mCategoriesLabel.setText(android.text.TextUtils.join(", ", mSpot.getCategories()));
        mRatingLabel.setText(Double.toString(mSpot.getRating()) + "/5");
        mPhoneLabel.setText(mSpot.getPhone());
        mAddressLabel.setText(android.text.TextUtils.join(", ", mSpot.getAddress()));

        mWebsiteLabel.setOnClickListener(this);
        mPhoneLabel.setOnClickListener(this);
        mAddressLabel.setOnClickListener(this);

        mSaveSpotButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        if (view == mWebsiteLabel) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(mSpot.getWebsite()));
            startActivity(webIntent);
        }
        if (view == mPhoneLabel) {
            Intent phoneIntent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:" + mSpot.getPhone()));
            startActivity(phoneIntent);
        }
        if (view == mAddressLabel) {
            Intent mapIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:" + mSpot.getLatitude()
                            + "," + mSpot.getLongitude()
                            + "?q=(" + mSpot.getName() + ")"));
            startActivity(mapIntent);
        }
        if (view == mSaveSpotButton) {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String uid = user.getUid();
            DatabaseReference spotRef = FirebaseDatabase
                    .getInstance()
                    .getReference(Constants.FIREBASE_CHILD_RESTAURANTS)
                    .child(uid);
            DatabaseReference pushRef = spotRef.push();
            String pushId = pushRef.getKey();
            mSpot.setPushId(pushId);
            pushRef.setValue(mSpot);
            Toast.makeText(getContext(), "Saved", Toast.LENGTH_SHORT).show();
        }
    }
}
