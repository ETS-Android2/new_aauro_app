package com.auro.scholr.home.presentation.view.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.auro.scholr.R;
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

import java.util.Arrays;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ScholarShipFacebookIn#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ScholarShipFacebookIn extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    CallbackManager callbackManager;
    private static final String EMAIL = "email";
    LoginButton loginButton;
    // Your Facebook APP ID
    AccessTokenTracker accessTokenTracker;
    ProfileTracker profileTracker;
    TextView mFirstName,LastName,txtToken;


    public ScholarShipFacebookIn() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ScholarShipFacebookIn.
     */
    // TODO: Rename and change types and number of parameters
    public static ScholarShipFacebookIn newInstance(String param1, String param2) {
        ScholarShipFacebookIn fragment = new ScholarShipFacebookIn();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View mview = inflater.inflate(R.layout.fragment_scholar_ship_facebook_in, container, false);
        loginButton = mview.findViewById(R.id.login_button);
        mFirstName = mview.findViewById(R.id.txtFirstName);
        LastName = mview.findViewById(R.id.txtLastName);
        txtToken = mview.findViewById(R.id.txtToken);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        callbackManager = CallbackManager.Factory.create();

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();



        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code

            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
        loginButton.setFragment(this);
        loginButton.setReadPermissions(Arrays.asList("user_status"));
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email"));
        LoginManager.getInstance().logInWithPublishPermissions(this, Arrays.asList("publish_actions"));




        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
                AccessToken  accessToken  =  AccessToken.getCurrentAccessToken();
                if(newToken == null){
                    Toast.makeText(getContext(),"Successfull",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext(),"UnSuccessfull",Toast.LENGTH_LONG).show();
                }
                // Log.e("Acess Token","Access token Facebook"+accessToken);
                //Toast.makeText(getContext(),accessToken + " Access Token "+AccessToken.getAccessToken().getToken(),Toast.LENGTH_LONG).show();
            }
        };


        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                nextActivity(newProfile);
            }
        };
        // If the access token is available already assign it.
        AccessToken accessToken  = AccessToken.getCurrentAccessToken();


        AccessToken token = AccessToken.getCurrentAccessToken();
        if (token != null) {
            Log.e("Acess Token","Access token Facebook"+accessToken);
            Log.e("access only Token is", String.valueOf(token.getToken()));
            Toast.makeText(getContext(),"Token Acces "+ token, Toast.LENGTH_LONG).show();
            String facebook_id_token = String.valueOf(token.getToken());
            txtToken.setText(facebook_id_token);
        }
        accessTokenTracker.startTracking();
        profileTracker.startTracking();


        return mview;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Facebook login
        Profile profile = Profile.getCurrentProfile();
        AccessToken accessToken  = AccessToken.getCurrentAccessToken();
        Log.e("Acess Token","Access token Facebook"+accessToken);

        nextActivity(profile);
    }

    private void nextActivity(Profile profile){
        if(profile != null){
            mFirstName.setText( profile.getFirstName());
            LastName.setText(profile.getLastName());
           /* Intent main = new Intent(LoginActivity.this, MainActivity.class);
            main.putExtra("name", profile.getFirstName());
            main.putExtra("surname", profile.getLastName());
            main.putExtra("imageUrl", profile.getProfilePictureUri(200,200).toString());
            startActivity(main);*/
        }
    }

    @Override
    public void onPause() {

        super.onPause();
    }

    public void onStop() {
        super.onStop();
        //Facebook login
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    /*   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }*/
}
