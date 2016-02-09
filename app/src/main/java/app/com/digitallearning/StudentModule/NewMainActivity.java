package app.com.digitallearning.StudentModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import app.com.digitallearning.R;
import app.com.digitallearning.StudentModule.StudentLesson.StudentLessonFragment;
import app.com.digitallearning.StudentModule.StudentQuiz.StudentQuizFragment;
import app.com.digitallearning.StudentModule.StudentResource.StudentResourceFragment;

/**
 * Created by ${PSR} on 2/8/16.
 */
public class NewMainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
   int pos;
    Fragment mFragment;
    boolean fromClass = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_main_activity);
        mToolbar = (Toolbar)findViewById(R.id.tool);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setSupportActionBar(mToolbar);
        pos=getIntent().getIntExtra("positioin",0);
        Log.e("pos",""+pos);

        FragmentManager mFragmentManager = getSupportFragmentManager();



        switch (pos) {

            case 0:

                if(fromClass){
//                    isFromDrawer
//                mFragment = ClassesDetailFragment.newInstance();

                    finish();
//                   ClassFragment.relative_header.setVisibility(View.GONE);
                }else {
                    mFragment = StudentLessonFragment.newInstance();

                }



                break;
            case 1:

                mFragment = StudentLessonFragment.newInstance();


                break;

            case 2:

                mFragment = StudentResourceFragment.newInstance();


                break;

            case 3:

                mFragment = StudentQuizFragment.newInstance();


                break;

            case 4:

                mFragment = StudentEnroll.newInstance();


                break;

            case 5:

                finish();


                break;

            /*case 1:
                headerTitle.setText("Search");
                mFragment = AdvanceSearchFragment.newInstance();

                break;
            case 2:
                headerTitle.setText("Spare Parts");
                mFragment = SparePartsFragment.newInstance("");
                break;

            case 3:
                headerTitle.setText("Add your Car");
                mFragment = AddCarDetailsFragment.newInstance();
                break;

            case 4:
                headerTitle.setText("Add Spare Part");
                mFragment = SpareDetails.newInstance();
                break;

            case 5:
                headerTitle.setText("Settings");
                mFragment = SettingFragment.newInstance();
                break;
            case 6:


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

                // Setting Dialog Title
                alertDialog.setTitle("Logged out");

                // Setting Dialog Message
                alertDialog.setMessage("Do you really want to logout?");

                // Setting Icon to Dialog


                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        clearPrefernces();
                        // Write your code here to invoke YES event
                        Intent i =  new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_left,
                                R.anim.slide_out_right);
                        finish();
                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event

                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();

                break;
            default:
                break;
*/
        }


        if (mFragment != null) {
            mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
        }



    }


        /*FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ClassesDetailFragment classesDetailFragment = new ClassesDetailFragment();
        fragmentTransaction.replace(R.id.container, classesDetailFragment);
        fragmentTransaction.commit();*/
       /* switch (position) {

            case 0:


            case 1:
                headerTitle.setText("Search");
                mFragment = AdvanceSearchFragment.newInstance();

                break;
            case 2:
                headerTitle.setText("Spare Parts");
                mFragment = SparePartsFragment.newInstance("");
                break;

            case 3:
                headerTitle.setText("Add your Car");
                mFragment = AddCarDetailsFragment.newInstance();
                break;

            case 4:
                headerTitle.setText("Add Spare Part");
                mFragment = SpareDetails.newInstance();
                break;

            case 5:
                headerTitle.setText("Settings");
                mFragment = SettingFragment.newInstance();
                break;
            case 6:


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

                // Setting Dialog Title
                alertDialog.setTitle("Logged out");

                // Setting Dialog Message
                alertDialog.setMessage("Do you really want to logout?");

                // Setting Icon to Dialog


                // Setting Positive "Yes" Button
                alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        clearPrefernces();
                        // Write your code here to invoke YES event
                        Intent i =  new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_left,
                                R.anim.slide_out_right);
                        finish();
                    }
                });

                // Setting Negative "NO" Button
                alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to invoke NO event

                        dialog.cancel();
                    }
                });

                // Showing Alert Message
                alertDialog.show();

                break;
            default:
                break;
 }


        if (mFragment != null) {
            mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
        }*/


}
