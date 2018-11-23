package com.example.rodrigosoares.appconsultas;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodrigosoares.appconsultas.interfaces.DownloadCallback;

public class LoginActivity extends AppCompatActivity implements DownloadCallback<String> {
    // Keep a reference to the NetworkFragment, which owns the AsyncTask object
    // that is used to execute network ops.
    private NetworkFragment mNetworkFragment;

    // Boolean telling us whether a download is in progress, so we don't trigger overlapping
    // downloads with consecutive button clicks.
    private boolean mDownloading = false;

    private boolean successfulLogin = true;
    private Button mBtnSignIn;
    private ProgressBar mProgressBar;
    private TextView mTxtLogin;
    private TextView mTxtPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mBtnSignIn = findViewById(R.id.btn_login);
        mTxtLogin = findViewById(R.id.txt_login);
        mTxtPassword = findViewById(R.id.txt_password);
        mBtnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startDownload();
            }
        });

        mProgressBar = findViewById(R.id.progress_bar);
    }

    private void startDownload() {
        String url = "http://192.168.1.43:8080/idosos?login="+mTxtLogin.getText().toString()+"&password="+mTxtPassword.getText().toString();
        mNetworkFragment = NetworkFragment.getInstance(getSupportFragmentManager(), url);
        mNetworkFragment.mUrlString = url;
        mNetworkFragment.mCallback = this;

        if (!mDownloading && mNetworkFragment != null) {
            // Execute the async download.
            mNetworkFragment.startDownload();
            mDownloading = true;
            mBtnSignIn.setVisibility(View.GONE);
            mProgressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void updateFromDownload(String result) {
        if(successfulLogin){
            Log.d("RDEBUG", "Successful Login");
        }
    }

    @Override
    public NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo;
    }

    @Override
    public void onProgressUpdate(int progressCode, int percentComplete) {
        switch(progressCode) {
            case Progress.ERROR:
                successfulLogin = false;
                Toast.makeText(this, "Senha ou usuário incorretos", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void finishDownloading() {
        mDownloading = false;
        if (mNetworkFragment != null) {
            mNetworkFragment.cancelDownload();
        }
        mProgressBar.setVisibility(View.GONE);
        mBtnSignIn.setVisibility(View.VISIBLE);
    }
}
