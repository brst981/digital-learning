package app.com.digitallearning.TeacherModule;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Classes.DescriptionFragment;
import app.com.digitallearning.TeacherModule.Classes.EditClassFragment;
import app.com.digitallearning.TeacherModule.Classes.TopicFragment;
import app.com.digitallearning.Utill.GlobalClass;

/**
 * Created by ${PSR} on 2/18/16.
 */
public class ClassActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        GlobalClass.lastValue="";
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ClassFragment classFragment = new ClassFragment();
        fragmentTransaction.replace(R.id.container, classFragment);

        fragmentTransaction.commit();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        GlobalClass.lastValue=" ";
        EditClassFragment.style=" ";
        TopicFragment.topic=" ";
        DescriptionFragment.description=" ";
        EditClassFragment.topic1=" ";
        TopicFragment.nwstring=" ";
        TopicFragment.otherstring=" ";
        EditClassFragment.newtopicsel="";
        GlobalClass.prefClear=true;
     //   TopicFragment.artthe=" ";
      //  TopicFragment.itemart=" ";







    }
}
