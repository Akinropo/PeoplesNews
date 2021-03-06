package com.example.rishabhkhanna.peopleword.views.Activities;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.NewsJson;
import com.example.rishabhkhanna.peopleword.model.ToiJson;
import com.example.rishabhkhanna.peopleword.utils.Constants;
import com.example.rishabhkhanna.peopleword.utils.UtilMethods;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import im.delight.android.webview.AdvancedWebView;


public class FullStoryActivity extends AppCompatActivity {

    ImageView ivFullStory;
    TextView tvFullStory;
    AppBarLayout appBarLayout;
    Boolean appBarExtented = true;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_story_layout);
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        ivFullStory = (ImageView) findViewById(R.id.ivFullStory);
        tvFullStory = (TextView) findViewById(R.id.tvFullStory);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.ctFullStory);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
//        collapsingToolbarLayout.home
        Gson gson = new Gson();
        NewsJson thisNews = gson.fromJson(getIntent().getStringExtra(Constants.FULL_STORY_KEY), NewsJson.class);
        Picasso.with(this)
                .load(UtilMethods.getImageurl(thisNews.getImageid()))
                .fit()
                .into(ivFullStory);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (thisNews.getStory() != null) {
                tvFullStory.setText(Html.fromHtml(thisNews.getStory(), Html.FROM_HTML_MODE_COMPACT));
            }
        } else {
            if(thisNews.getStory() != null) {
                tvFullStory.setText(Html.fromHtml(thisNews.getStory()));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
