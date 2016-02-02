package app.com.digitallearning.TeacherModule;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import app.com.digitallearning.R;

/**
 * Created by ${ShalviSharma} on 12/18/15.
 */
public class TeacherLoginActivity extends FragmentActivity {
        String schoolID,schoolName;
        ProgressDialog dlg;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        dlg=new ProgressDialog(TeacherLoginActivity.this);
        schoolID=getIntent().getStringExtra("SchoolID");
        Log.e("schoolID",""+schoolID);
        schoolName=getIntent().getStringExtra("SchoolName");
        Log.e("schoolName",""+schoolName);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();


                Bundle args = new Bundle();
                args.putString("SchoolID",schoolID);
                args.putString("SchoolName",schoolName);

                TeacherLoginFragment teacherLoginFragment = new TeacherLoginFragment();
                fragmentTransaction.replace(R.id.container, teacherLoginFragment);
                teacherLoginFragment.setArguments(args);
                fragmentTransaction.commit();

        }
        }
