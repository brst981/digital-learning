package app.com.digitallearning.StudentModule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import app.com.digitallearning.MainActivity;
import app.com.digitallearning.R;

;

/**
 * Created by ${ShalviSharma} on 12/18/15.
 */
public class StudentLoginActivity extends FragmentActivity {
    String schoolID,schoolName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitystudent);

        schoolID=getIntent().getStringExtra("SchoolID");
        Log.e("schoolID",""+schoolID);
        schoolName=getIntent().getStringExtra("SchoolName");
        Log.e("schoolName",""+schoolName);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Bundle args = new Bundle();
        args.putString("SchoolID",schoolID);
        args.putString("SchoolName",schoolName);
        StudentLoginFragment studentLoginFragment = new StudentLoginFragment();
        fragmentTransaction.replace(R.id.container, studentLoginFragment);
        studentLoginFragment.setArguments(args);
        fragmentTransaction.commit();

        /*FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        StudentClass studentClass = new StudentClass();
        fragmentTransaction.replace(R.id.container, studentClass);
        fragmentTransaction.commit();*/
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent gotomain=new Intent(StudentLoginActivity.this, MainActivity.class);
        startActivity(gotomain);
        finish();
    }
}