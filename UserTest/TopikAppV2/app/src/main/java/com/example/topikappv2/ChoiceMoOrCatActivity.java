package com.example.topikappv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.common.internal.BaseGmsClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ChoiceMoOrCatActivity extends AppCompatActivity{

    private GoogleApiClient mGoogleApiClient;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    private String mUsername;
    private String mPhotoUrl;
    private String mUserID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_mo_or_cate);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if(mFirebaseUser != null){
            mUserID = mFirebaseUser.getEmail();
        }

        //로그인 다시 하려면 이거 꼭 풀 것.
        if (mFirebaseUser == null) {
            startActivity(new Intent(this, SignInActivity.class));
            finish();
            return;
        } else {
            mUsername = mFirebaseUser.getDisplayName();
            if (mFirebaseUser.getPhotoUrl() != null) {
                mPhotoUrl = mFirebaseUser.getPhotoUrl().toString();
            }
        }
    }

    public void select_mo(View view) {
        Intent intent = new Intent(this, ChoiceMoActivity.class);
        startActivity(intent);
    }

    public void select_cat(View view) {
        Intent intent = new Intent(this, ChoiceCatActivity.class);
        startActivity(intent);
    }

    //로그아웃

    public void log_out(View view){
        mFirebaseAuth.signOut();
        //Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        mUsername = "";
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }
}