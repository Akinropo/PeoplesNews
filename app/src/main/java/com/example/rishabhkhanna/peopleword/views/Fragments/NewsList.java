package com.example.rishabhkhanna.peopleword.views.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rishabhkhanna.peopleword.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsList extends Fragment {


    public NewsList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_news_list, container, false);
        return root;
    }

}
