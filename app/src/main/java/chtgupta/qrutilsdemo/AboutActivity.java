package chtgupta.qrutilsdemo;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;

import static chtgupta.qrutilsdemo.MainActivity.VIEW_ELEVATION;

public class AboutActivity extends AppCompatActivity {

    final String repositoryURL = "https://github.com/chtgupta/QRUtils-Android/";
    final String profileURL = "https://chtgupta.github.io/";
    final String githubURL = "https://github.com/chtgupta/";
    final String instagramURL = "https://instagram.com/chtgupta/";
    final String facebookURL = "https://facebook.com/chtgupta/";
    final String twitterURL = "https://twitter.com/i_m_cht/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        ViewCompat.setElevation(toolbar, VIEW_ELEVATION);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    // ______ handling clicks ______

    public void viewRepository(View view) {
        startActivity(
                new Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse(repositoryURL))
        );
    }

    public void viewProfile(View view) {
        startActivity(
                new Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse(profileURL))
        );
    }

    public void viewGithub(View view) {
        startActivity(
                new Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse(githubURL))
        );
    }

    public void viewInstagram(View view) {
        startActivity(
                new Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse(instagramURL))
        );
    }

    public void viewFacebook(View view) {
        startActivity(
                new Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse(facebookURL))
        );
    }

    public void viewTwitter(View view) {
        startActivity(
                new Intent(Intent.ACTION_VIEW)
                        .setData(Uri.parse(twitterURL))
        );
    }

}
