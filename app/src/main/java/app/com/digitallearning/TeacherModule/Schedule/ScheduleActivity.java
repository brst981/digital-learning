package app.com.digitallearning.TeacherModule.Schedule;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import app.com.digitallearning.R;

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
}