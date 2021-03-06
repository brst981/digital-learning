package app.com.digitallearning.TeacherModule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import app.com.digitallearning.MainActivity;
import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Classes.DescriptionFragment;
import app.com.digitallearning.TeacherModule.Classes.EditClassFragment;

/**
 * Created by ${ShalviSharma} on 12/18/15.
 */
public class TeacherLoginActivity extends FragmentActivity {
        String schoolID,schoolName;
        ProgressDialog dlg;
        int upated,val;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        dlg=new ProgressDialog(TeacherLoginActivity.this);
        schoolID=getIntent().getStringExtra("SchoolID");
        Log.e("schoolID",""+schoolID);
        schoolName=getIntent().getStringExtra("SchoolName");
                val=getIntent().getIntExtra("val",0);
        Log.e("schoolName",""+schoolName);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();




                TeacherLoginFragment teacherLoginFragment = new TeacherLoginFragment();
                fragmentTransaction.replace(R.id.container, teacherLoginFragment);
                fragmentTransaction.commit();

        }

        @Override
        public void onBackPressed() {
                super.onBackPressed();
                EditClassFragment.style=" ";
                EditClassFragment.topic1=" ";
                DescriptionFragment.description=" ";
              //  getFragmentManager().popBackStackImmediate();
            //  finish();
                if (val==12){
                        Log.e("val",""+val);
                        finish();
                }



                Intent gotomain=new Intent(TeacherLoginActivity.this, MainActivity.class);
                startActivity(gotomain);
                finish();
        }
}
