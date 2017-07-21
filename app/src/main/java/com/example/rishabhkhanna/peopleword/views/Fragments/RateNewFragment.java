package com.example.rishabhkhanna.peopleword.views.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.daprlabs.cardstack.SwipeDeck;
import com.example.rishabhkhanna.peopleword.Adapters.RateNewsAdapter;
import com.example.rishabhkhanna.peopleword.Interfaces.onJsonRecieved;
import com.example.rishabhkhanna.peopleword.Network.API;
import com.example.rishabhkhanna.peopleword.Network.NewsAPI;
import com.example.rishabhkhanna.peopleword.Network.interfaces.getAuth;
import com.example.rishabhkhanna.peopleword.Network.interfaces.getNews;
import com.example.rishabhkhanna.peopleword.Network.interfaces.rateNews;
import com.example.rishabhkhanna.peopleword.R;
import com.example.rishabhkhanna.peopleword.model.AuthResponse;
import com.example.rishabhkhanna.peopleword.model.NewsJson;
import com.example.rishabhkhanna.peopleword.model.User;
import com.example.rishabhkhanna.peopleword.utils.Constants;
import com.example.rishabhkhanna.peopleword.utils.FetchNews;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RateNewFragment extends Fragment {

    private SwipeDeck swipeDeck;
    public RateNewsAdapter.SwipeCardAdapter swipeCardAdapter;
    Button dislikeBtn, likeBtn;
    ArrayList<NewsJson> newsArrayList = new ArrayList<>();
    CallbackManager callbackManager;
    SharedPreferences sharedPreferences;
    public static final String TAG = "RateNewsActivity";
    AccessTokenTracker accessTokenTracker;

    public RateNewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sharedPreferences = getActivity().getSharedPreferences("newsapp", Context.MODE_PRIVATE);
        if (Profile.getCurrentProfile() != null) {
            Log.d(TAG, "onCreateView: " + Profile.getCurrentProfile().getName());
            Log.d(TAG, "onCreateView: " + AccessToken.getCurrentAccessToken().getToken());

        }
        if ((sharedPreferences.getString(Constants.LOGIN_TOKEN, "null")).equals("null")) {
            callbackManager = CallbackManager.Factory.create();
            return getSignupPage(inflater, container);
        }

        View root = inflater.inflate(R.layout.fragment_rate_new, container, false);
        swipeDeck = (SwipeDeck) root.findViewById(R.id.swipe_deck);
        likeBtn = (Button) root.findViewById(R.id.like_btn);
        dislikeBtn = (Button) root.findViewById(R.id.dislike_btn);

        swipeCardAdapter = RateNewsAdapter.getSwipeCardAdapter(newsArrayList, getContext());
        swipeDeck.setAdapter(swipeCardAdapter);

        //event CallBack
        swipeDeck.setEventCallback(new SwipeDeck.SwipeEventCallback() {
            @Override
            public void cardSwipedLeft(int position) {
                Log.d(TAG, "cardSwipedLeft: ");
                NewsJson swipedNews = newsArrayList.get(position);
                String token = AccessToken.getCurrentAccessToken().getToken();
                String userID = AccessToken.getCurrentAccessToken().getUserId();
                API.getInstance()
                        .retrofit
                        .create(rateNews.class)
                        .dislikeNews(
                                token,
                                userID,
                                swipedNews.getMsid(),
                                String.valueOf(swipedNews.getId())
                        ).enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        Log.d(TAG, "onResponse: Cool Your Rating has been recorded");
                        Log.d(TAG, "onResponse: " + call.request());
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + call.request());
                    }
                });
            }

            @Override
            public void cardSwipedRight(int position) {
                Log.d(TAG, "cardSwipedRight: ");
                NewsJson swipedNews = newsArrayList.get(position);
                String token = AccessToken.getCurrentAccessToken().getToken();
                String userID = AccessToken.getCurrentAccessToken().getUserId();
                API.getInstance()
                        .retrofit
                        .create(rateNews.class)
                        .likeNews(
                                token,
                                userID,
                                swipedNews.getMsid(),
                                String.valueOf(swipedNews.getId())
                        ).enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                        Log.d(TAG, "onResponse: Cool Your Rating has been recorded");
                        Log.d(TAG, "onResponse: " + call.request());
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        Log.d(TAG, "onFailure: " + call.request());
                    }
                });

            }

            @Override
            public void cardsDepleted() {

            }

            @Override
            public void cardActionDown() {

            }

            @Override
            public void cardActionUp() {

            }
        });


        //Interface callback to show data after download
//        onJsonRecieved onJsonRecieved = new onJsonRecieved() {
//            @Override
//            public void onSuccess(ArrayList<NewsJson> fetchedNewsList) {
//                newsArrayList.addAll(fetchedNewsList);
//                swipeCardAdapter.notifyDataSetChanged();
//            }

//            @Override
//            public void onError(VolleyError error) {
//                Log.d(TAG, "onError: " + error);
//                Toast.makeText(getActivity(), "Sorry could not fetch news at this moment", Toast.LENGTH_SHORT).show();
//            }
//        };

        //get Toi data
//        FetchNews.getNewsJson(getActivity(), onJsonRecieved);


//        NewsAPI.getInstance().getNews.getBriefs("0").enqueue(new Callback<ArrayList<NewsJson>>() {
//            @Override
//            public void onResponse(Call<ArrayList<NewsJson>> call, Response<ArrayList<NewsJson>> response) {
//                newsArrayList.addAll(response.body());
//                swipeCardAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onFailure(Call<ArrayList<NewsJson>> call, Throwable t) {
//
//            }
//        });


        API.getInstance()
                .retrofit
                .create(getNews.class)
                .getBriefs("0").enqueue(new Callback<ArrayList<NewsJson>>() {
            @Override
            public void onResponse(Call<ArrayList<NewsJson>> call, Response<ArrayList<NewsJson>> response) {
                newsArrayList.addAll(response.body());
                swipeCardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ArrayList<NewsJson>> call, Throwable t) {

            }
        });

        //button click listner
        likeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeDeck.swipeTopCardRight(1000);
            }
        });

        dislikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                swipeDeck.swipeTopCardLeft(1000);
            }
        });


        // set images on of like and dislike on swipe
        swipeDeck.setLeftImage(R.id.nope_card_image);
        swipeDeck.setRightImage(R.id.like_card_image);


        return root;
    }

    private View getSignupPage(LayoutInflater inflater, ViewGroup container) {
        FacebookSdk.sdkInitialize(getContext());
        View signup_root = inflater.inflate(R.layout.signup_login_layout, container, false);
        final ProgressBar progressBar = (ProgressBar) signup_root.findViewById(R.id.marker_progress);
        final LoginButton loginButton = (LoginButton) signup_root.findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.setFragment(this);

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                progressBar.setVisibility(View.VISIBLE);
                loginButton.setVisibility(View.GONE);
                Log.d(TAG, "onSuccess: accessToken" + loginResult.getAccessToken());
                Log.d(TAG, "onSuccess: permissions" + loginResult.getRecentlyGrantedPermissions());
                Log.d(TAG, "onSuccess: token" + loginResult.getAccessToken().getToken());
                Log.d(TAG, "onSuccess: token" + loginResult.getAccessToken().getExpires().toString());
                Log.d(TAG, "onSuccess: application Id" + loginResult.getAccessToken().getApplicationId());
                Log.d(TAG, "onSuccess: user_id" + loginResult.getAccessToken().getUserId());
                Log.d(TAG, "onSuccess: isExpired" + loginResult.getAccessToken().isExpired());
                if (Profile.getCurrentProfile() == null) {
                    ProfileTracker profileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            Profile.setCurrentProfile(currentProfile);
                            this.stopTracking();
                        }
                    };
                } else {
                    Log.d(TAG, "onSuccess: FirstName" + Profile.getCurrentProfile().getFirstName());
                }


                API.getInstance()
                        .retrofit
                        .create(getAuth.class)
                        .facebookUserAuth(
                                loginResult.getAccessToken().getToken(),
                                loginResult.getAccessToken().getUserId()
                        ).enqueue(new Callback<AuthResponse>() {
                    @Override
                    public void onResponse(retrofit2.Call<AuthResponse> call, Response<AuthResponse> response) {

                        Log.d(TAG, "onResponse: " + response);
                        Log.d(TAG, "onResponse: " + response.body().getUser().getName());
                        Log.d(TAG, "onResponse: " + response.body().getUser().getEmail());

                        Log.d(TAG, "onResponse: " + call.request());
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(Constants.LOGIN_TOKEN, loginResult.getAccessToken().getToken());
                        editor.putString(Constants.LOGIN_USER_ID, loginResult.getAccessToken().getUserId());
                        editor.apply();
                        progressBar.setVisibility(View.GONE);
                        getActivity().recreate();
                    }

                    @Override
                    public void onFailure(Call<AuthResponse> call, Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        Log.d(TAG, "onFailure: " + call.request());
                        Log.d(TAG, "onFailure: " + t.getMessage());
                        loginButton.setVisibility(View.VISIBLE);
                        LoginManager.getInstance().logOut();
                        Toast.makeText(getActivity(), "Cannot Login Right Now", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onCancel() {
                Log.d(TAG, "onCancel: Cancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "onError: " + error.getMessage());
            }
        });
//


        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (currentAccessToken != null) {
                    AccessToken.setCurrentAccessToken(currentAccessToken);
                    API.getInstance().retrofit
                            .create(getAuth.class)
                            .facebookTokenUpdate(currentAccessToken.getToken(), currentAccessToken.getUserId())
                            .enqueue(new Callback<AuthResponse>() {
                                @Override
                                public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                                    Log.d(TAG, "onResponse: " + response.body());
                                    Log.d(TAG, "onResponse: " + response.body().getUser().getName());
                                }

                                @Override
                                public void onFailure(Call<AuthResponse> call, Throwable t) {

                                }
                            });
                }
            }
        };


//        AccessToken.refreshCurrentAccessTokenAsync(new AccessToken.AccessTokenRefreshCallback() {
//            @Override
//            public void OnTokenRefreshed(AccessToken accessToken) {
//
//            }
//
//            @Override
//            public void OnTokenRefreshFailed(FacebookException exception) {
//
//            }
//        });
        return signup_root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: request code" + requestCode);
        Log.d(TAG, "onActivityResult: result code" + resultCode);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onDestroy() {
        if (accessTokenTracker != null) {
            accessTokenTracker.stopTracking();
        }
        super.onDestroy();
    }
}
