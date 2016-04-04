package app.com.digitallearning.StudentModule;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 4/2/16.
 */
public class StudentClassActivity extends FragmentActivity {
    String schoolID, schoolName;
    ProgressDialog dlg;
    int upated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        StudentClass studentClass = new StudentClass();
        fragmentTransaction.replace(R.id.container, studentClass);
        fragmentTransaction.commit();

    }
}