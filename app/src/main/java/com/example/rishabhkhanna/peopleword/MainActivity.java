package com.example.rishabhkhanna.peopleword;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import static com.android.volley.Request.Method.GET;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "fetchNews";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestQueue queue;
        queue = Volley.newRequestQueue(MainActivity.this);

        String url = "http://timesofindia.indiatimes.com" +
                "/feeds/newslistingfeed/tag-alrt,msid-48986328,feedtype-sjson,type-brief.cms?andver=" +
                "417&platform=android&adreqfrm=sec";

        StringRequest stringRequest = new StringRequest(GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG , response);



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(stringRequest);

    }
}



