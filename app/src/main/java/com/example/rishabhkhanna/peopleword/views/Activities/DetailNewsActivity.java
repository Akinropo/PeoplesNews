package com.example.rishabhkhanna.peopleword.views.Activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.rishabhkhanna.peopleword.R;

import com.example.rishabhkhanna.peopleword.model.NewsJson;
import com.example.rishabhkhanna.peopleword.model.PhotoStory;
import com.example.rishabhkhanna.peopleword.utils.Constants;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class DetailNewsActivity extends AppCompatActivity {

    public static final String TAG = "DetailNew Activity";
    TextView headTv;
    TextView detailTV;
    ImageView imageViewNews;
    BottomNavigationView navigationView;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        headTv = (TextView) findViewById(R.id.news_headline_full);
        detailTV = (TextView) findViewById(R.id.news_deatil_full);
        imageViewNews = (ImageView) findViewById(R.id.detail_image);
        navigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        Intent i = getIntent();
        final Gson gson = new Gson();
        final NewsJson thisNews = gson.fromJson(i.getStringExtra(Constants.DETAIL_NEWS_KEY), NewsJson.class);
        headTv.setText(thisNews.getHl());
        detailTV.setText(thisNews.getSyn());

        Picasso.with(DetailNewsActivity.this)
                .load("http://timesofindia.indiatimes.com/thumb.cms?photoid=" + thisNews.getImageid() + "&width=1500&height=1440&resizemode=1")
                .noFade()
                .fit()
                .into(imageViewNews, new Callback() {
                    @Override
                    public void onSuccess() {
                        try {
                            int cx = imageViewNews.getWidth() / 2;
                            int cy = imageViewNews.getHeight() / 2;
                            int finalRadius = imageViewNews.getHeight();
                            Animator anim = ViewAnimationUtils.createCircularReveal(imageViewNews, cx, cy, 0, finalRadius);
                            anim.setDuration(500);
                            anim.start();
                            Log.d(TAG, "onSuccess: ");
                        } catch (IllegalStateException e) {
                            Log.d(TAG, "onSuccess:  " + e.getMessage());

                        }
                    }

                    @Override
                    public void onError() {

                    }
                });


        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_article:
                        if (thisNews.getTn().equals("photostory")) {
                            Intent i = new Intent(DetailNewsActivity.this, PhotoStoryActivity.class);
                            i.putExtra(Constants.FULL_STORY_KEY, gson.toJson(thisNews.getPhotoStory()));

                            startActivity(i);
                        } else {
                            Intent i = new Intent(DetailNewsActivity.this, FullStoryActivity.class);
                            i.putExtra(Constants.FULL_STORY_KEY, gson.toJson(thisNews));

                            startActivity(i);
                        }
                        break;
                    case R.id.action_share:
                        break;
                    case R.id.action_chat:
                        break;
                }
                return true;
            }
        });

    }
}
