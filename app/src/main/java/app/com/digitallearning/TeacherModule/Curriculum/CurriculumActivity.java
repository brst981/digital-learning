package app.com.digitallearning.TeacherModule.Curriculum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.ClassActivity;
import app.com.digitallearning.Utill.GlobalClass;

/**
 * Created by ${PSR} on 2/26/16.
 */
public class CurriculumActivity extends FragmentActivity {

    int curiid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.curriculum_activity);
        curiid=getIntent().getIntExtra("curiid",curiid);
        Log.e("curiidinAct",""+curiid);


        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CurriculumFragment curriculumFragment = new CurriculumFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("curiid",curiid);
        fragmentTransaction.replace(R.id.container, curriculumFragment);
        curriculumFragment.setArguments(bundle);
        fragmentTransaction.commit();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

            CurriculumFragment.curriculumtopic =null;
            Log.e("CurriculumFragment", "" + CurriculumFragment.curriculumtopic);
            CurriculumFragment.curriculumdes=null;
        CurriculumFragment.curriculuncountry=null;
            CurriculumFragment. curriculumlib=null;
        CurriculumFragment.curriculumgradefrom=null;
        CurriculumFragment.curriculumgradeto=null;



        GlobalClass.lastValue=" ";

        GlobalClass.prefClear=true;



        Intent gotoclasss=new Intent(CurriculumActivity.this, ClassActivity.class);
        startActivity(gotoclasss);
        finish();
        /*FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ClassesDetailFragment classesDetailFragment = new ClassesDetailFragment();
        fragmentTransaction.replace(R.id.container, classesDetailFragment);
        fragmentTransaction.commit();*/

    }
}
