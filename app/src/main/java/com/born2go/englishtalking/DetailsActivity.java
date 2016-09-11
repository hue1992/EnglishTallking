package com.born2go.englishtalking;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.born2go.englishtalking.entities.CallCenter;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener, YouTubePlayer.OnFullscreenListener {

    private static final String API_KEY = "AIzaSyCp4x4PMorQnbJhygRArnUupo6-UDi49nE";
    private static final String TAG = DetailsActivity.class.getSimpleName();
    private static java.lang.String VIDEO_ID = "v7cvc6bD7l4";
    private CallCenter callCenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_details);

        if (getIntent().getExtras() != null) {
            callCenter = getIntent().getExtras().getParcelable("callCenter");
            if (callCenter != null) {
                loadInformation(callCenter);
                if (callCenter.getVideoId() != null) {
                    VIDEO_ID = callCenter.getVideoId();
                }
                Log.d(TAG, "VIDEO_ID:" + VIDEO_ID);
                YouTubePlayerSupportFragment frag =
                        (YouTubePlayerSupportFragment) getSupportFragmentManager().findFragmentById(R.id.youtube_fragment);
                frag.initialize(API_KEY, this);

            } else {
                Toast.makeText(this, "No load call center", Toast.LENGTH_SHORT).show();
            }

        }

        //
//        VideoFragment videoFragment =
//                (VideoFragment) getFragmentManager().findFragmentById(R.id.youtube_view);
//        videoFragment.setVideoId("v7cvc6bD7l4");

        //
//        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.list_video);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void loadInformation(CallCenter callCenter) {
        NetworkImageView imgWellcome = (NetworkImageView) findViewById(R.id.avata);
        TextView callcenterName = (TextView) findViewById(R.id.callcenterName);
        LinearLayout active = (LinearLayout) findViewById(R.id.active);
        TextView grade = (TextView) findViewById(R.id.grade);
        TextView ccId = (TextView) findViewById(R.id.ccId);

        //Load img well come
//        ESFSingleton.getInstance().getImageLoader().get(callCenter.getAvatar(), ImageLoader.getImageListener(imgWellcome, R.drawable.default_avatar_photo, R.drawable.default_avatar_photo));
//        imgWellcome.setImageUrl(callCenter.getAvatar(), ESFSingleton.getInstance().getImageLoader());
        Picasso.with(this).load(callCenter.getAvatar()).error(R.drawable.default_avatar_photo).into(imgWellcome);

        //set name and grade,id
        callcenterName.setText(callCenter.getCcName());
        grade.setText(callCenter.getGrade());
        ccId.setText(callCenter.getCc_id());

        //set active call center
        active.removeAllViews();
        if (callCenter.getActive() == 0) {
            active.addView(LayoutInflater.from(this).inflate(R.layout.view_busy, null, false));
            // active.setColorFilter(R.color.colorDeactivate);
        } else {
            // active.setColorFilter(R.color.colorActive);
            active.addView(LayoutInflater.from(this).inflate(R.layout.view_available, null, false));
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
        }

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(getApplicationContext(), "onInitializationFailure()", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onFullscreen(boolean b) {

    }


    public void call(View view) {
        if (callCenter != null)
            callNumber(callCenter.getMsisdn());
    }

    public void callNumber(String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE},
                    100);

            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
            // app-defined int constant

            return;
        }
        startActivity(intent);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 100: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! do the
                    // calendar task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

        }
    }
}
