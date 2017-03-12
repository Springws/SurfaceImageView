package com.wusen.surfaceimageview;

import android.graphics.Movie;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private SurfaceGifView surfaceGifView;
    private Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        Display display = this.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        surfaceGifView = (SurfaceGifView) findViewById(R.id.surface_image_view);
//        surfaceGifView.setmScaleX(2);
//        surfaceGifView.setmScaleY(2);
        try {
            InputStream stream = getAssets().open("coffe.gif");
            movie = Movie.decodeStream(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        surfaceGifView.setMovie(movie);
        surfaceGifView.setmMeasureHeight(height);
        surfaceGifView.setmMeasureWidth(width);
    }
}
