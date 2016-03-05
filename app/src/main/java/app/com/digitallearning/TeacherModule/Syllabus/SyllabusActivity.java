package app.com.digitallearning.TeacherModule.Syllabus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.ClassActivity;

/**
 * Created by ${PSR} on 2/26/16.
 */
public class SyllabusActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.syllabus_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SyllabusFragment syllabusFragment = new SyllabusFragment();
        fragmentTransaction.replace(R.id.container, syllabusFragment);
        fragmentTransaction.commit();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent gotoclasss=new Intent(SyllabusActivity.this, ClassActivity.class);
        startActivity(gotoclasss);
        finish();
    }
}