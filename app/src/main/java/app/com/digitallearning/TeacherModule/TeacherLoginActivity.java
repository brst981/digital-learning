package app.com.digitallearning.TeacherModule;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import app.com.digitallearning.R;

/**
 * Created by ${ShalviSharma} on 12/18/15.
 */
public class TeacherLoginActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        TeacherLoginFragment teacherLoginFragment = new TeacherLoginFragment();
        fragmentTransaction.replace(R.id.container, teacherLoginFragment);

        fragmentTransaction.commit();
    }
}
