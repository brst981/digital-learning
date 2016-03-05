package app.com.digitallearning.TeacherModule.Schedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.ClassActivity;

/**
 * Created by ${PSR} on 2/25/16.
 */
public class ScheduleActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ScheduleFragment scheduleFragment = new ScheduleFragment();
        fragmentTransaction.replace(R.id.container, scheduleFragment);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent gotoclasss=new Intent(ScheduleActivity.this, ClassActivity.class);
        startActivity(gotoclasss);
        finish();
    }
}