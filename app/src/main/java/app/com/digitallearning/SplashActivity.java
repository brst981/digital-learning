package app.com.digitallearning;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;

import app.com.digitallearning.StudentModule.StudentClassActivity;
import app.com.digitallearning.TeacherModule.ClassActivity;

/**
 * Created by ${ShalviSharma} on 12/17/15.
 */
public class SplashActivity extends Activity {
    private int SPLASH_TIME_OUT = 5000;
    SharedPreferences preferences;
    int remembermechecked,remembermecheckedstu,val;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                preferences= PreferenceManager.getDefaultSharedPreferences(SplashActivity.this);
                remembermechecked=preferences.getInt("rememberMe",0);
                remembermecheckedstu=preferences.getInt("remembermecheckedstu",0);
                Log.e("remembermecheckedres",""+remembermechecked);

                if(val==12){
                    Log.e("valres",""+val);
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);

                    startActivity(mainIntent);
                    finish();
                }
                else if(remembermechecked==1){
                    Intent toclass=new Intent(SplashActivity.this, ClassActivity.class);
                    startActivity(toclass);
                    finish();
                }
                else  if(remembermechecked==0){
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);

                    startActivity(mainIntent);
                    finish();
                }

                if(remembermecheckedstu==11){
                    Intent toclass=new Intent(SplashActivity.this, StudentClassActivity.class);
                    startActivity(toclass);
                    finish();
                }
                else if(remembermecheckedstu==10){
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);

                    startActivity(mainIntent);
                    finish();
                }

                /* Create an Intent that will start the Menu-Activity. */

                /*if(GlobalClass.rememberMe==true){
                    Intent toclass=new Intent(SplashActivity.this, ClassActivity.class);
                    startActivity(toclass);
                    finish();
                }
                else if(GlobalClass.rememberMe==false) {
                    Intent mainIntent = new Intent(SplashActivity.this, MainActivity.class);

                    startActivity(mainIntent);
                    finish();
                }*/
            }
        }, SPLASH_TIME_OUT);

    }
}
