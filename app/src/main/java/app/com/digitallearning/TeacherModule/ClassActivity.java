package app.com.digitallearning.TeacherModule;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Classes.DescriptionFragment;
import app.com.digitallearning.TeacherModule.Classes.EditClassFragment;
import app.com.digitallearning.TeacherModule.Classes.TopicFragment;

/**
 * Created by ${PSR} on 2/18/16.
 */
public class ClassActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        ClassFragment classFragment = new ClassFragment();
        fragmentTransaction.replace(R.id.container, classFragment);

        fragmentTransaction.commit();


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      //  finish();
        /*CreateClassFragment.slecectedclasstype=0;
        EditClassFragment.style=" ";
        DescriptionFragment.description=" ";
*/
        EditClassFragment.style=" ";
        Log.e("EditClassFragment.style",""+EditClassFragment.style);
        TopicFragment.topic=" ";
        DescriptionFragment.description=" ";
        EditClassFragment.topic1=" ";
     //   TopicFragment.artthe=" ";
      //  TopicFragment.itemart=" ";


    }
}
