package com.androidexample.moviemanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class ImageDisplayActivity extends AppCompatActivity {

    ImageView mFullImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("image_url");

        mFullImage = (ImageView)findViewById(R.id.full_image);

        ImageLoader loader = new ImageLoader(this);
        mFullImage.setTag(imageUrl);
        loader.displayImage(imageUrl, this, mFullImage);
    }
}
