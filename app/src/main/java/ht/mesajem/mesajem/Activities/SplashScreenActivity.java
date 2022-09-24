package ht.mesajem.mesajem.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import ht.mesajem.mesajem.R;



public class SplashScreenActivity extends AppCompatActivity {

    ImageView splach_im2;
    ImageView splach_im1;
    TextView tv_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splach_im1 = findViewById(R.id.splach_im1);
        //splach_im2 = findViewById(R.id.splach_im2);

        new Handler()
                .postDelayed( new Runnable() {
                    @Override
                    public void run () {
                        startActivity( new Intent(SplashScreenActivity.this, LoginActivity.class )) ;
                        finish() ;
                    }
                } , 1500 ) ;
    }
}