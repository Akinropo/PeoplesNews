package com.example.rishabhkhanna.peopleword.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.rishabhkhanna.peopleword.views.Fragments.AllNewsPageFragment;

import java.util.List;

/**
 * Created by rishabhkhanna on 19/06/17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    List<Fragment> pageFragmentList;

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
//        pageFragmentList = Arrays.asList(new Fragment[9]);
    }

    AllNewsPageFragment newsFragment;

    @Override
    public Fragment getItem(int position) {
        //get Item is called to instantite the fragment for the given page
        // write login for which fragment to return


        newsFragment = AllNewsPageFragment.newInstance(position);
        return newsFragment;

    }

    @Override
    public int getCount() {
        return 15;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Briefs";
            case 1:
                return "top news";
            case 2:
                return "entertainment";
            case 3:
                return "india";
            case 4:
                return "world";
            case 5:
                return "sports";
            case 6:
                return "cricket";
            case 7:
                return "business";
            case 8:
                return "education";
            case 9:
                return "TV";
            case 10:
                return "Automotive";
            case 11:
                return "LifeStyle";
            case 12:
                return "Environment";
            case 13:
                return "Good Governance";
            case 14:
                return "Events";
        }
        return super.getPageTitle(position);
    }
}
