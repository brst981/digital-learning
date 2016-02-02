package app.com.digitallearning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by ${ShalviSharma} on 12/17/15.
 */
public class SplashActivity extends Activity {
    private int SPLASH_TIME_OUT = 5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */

                Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);

                startActivity(mainIntent);
                finish();

            }
        }, SPLASH_TIME_OUT);

    }
}
