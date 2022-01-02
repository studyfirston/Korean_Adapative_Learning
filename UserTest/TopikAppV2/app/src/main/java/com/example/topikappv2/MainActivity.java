package com.example.topikappv2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    private FirebaseUser mFirebaseUser;
    private FirebaseAuth mFirebaseAuth;
    private String mUsername;
    private String mPhotoUrl;
    private String mUserID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //로그인
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API)
                .build();
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



    @Override
    public void onConnectionFailed (@NonNull ConnectionResult connectionResult){
        Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show();
    }
    //로그아웃
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (@NonNull MenuItem item){
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                mFirebaseAuth.signOut();
                Auth.GoogleSignInApi.signOut(mGoogleApiClient);
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void go_solve_re(View view) {
        Intent intent = new Intent(this, ChoiceReActivity.class);
        intent.putExtra("UserID", mUserID);
        startActivity(intent);
    }

    public void select_mo(View view) {
        Intent intent = new Intent(this, ChoiceMoActivity.class);
        intent.putExtra("UserID", mUserID);
        startActivity(intent);

    }

    public void select_cat(View view) {

        Intent intent = new Intent(this, ChoiceCatActivity.class);
        startActivity(intent);

    }

    //로그아웃

    public void log_out(View view){
        mFirebaseAuth.signOut();
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        startActivity(new Intent(this, SignInActivity.class));
        finish();
    }
}
