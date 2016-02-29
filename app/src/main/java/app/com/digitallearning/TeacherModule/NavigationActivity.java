package app.com.digitallearning.TeacherModule;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import app.com.digitallearning.NavigationDrawerCallbacks;
import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Classes.ClassesDetailFragment;
import app.com.digitallearning.TeacherModule.Curriculum.CurriculumFragment;
import app.com.digitallearning.TeacherModule.Grade.GradeFragment;
import app.com.digitallearning.TeacherModule.Lessons.LessonFragment;
import app.com.digitallearning.TeacherModule.Lessons.LessonProfile;
import app.com.digitallearning.TeacherModule.Quiz.QuizFragment;
import app.com.digitallearning.TeacherModule.Resource.ResourceFragment;
import app.com.digitallearning.TeacherModule.Students.StudentFragment;

/**
 * Created by ${ShalviSharma} on 12/18/15.
 */
public class NavigationActivity extends AppCompatActivity implements NavigationDrawerCallbacks {
    public static Toolbar mToolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    Fragment mFragment;
    boolean fromClass = false;
    public static boolean isFromDrawer = false;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    FrameLayout frame;
    boolean classID=false;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_navigation);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        imageButtonZoomIn=(ImageButton) mToolbar.findViewById(R.id.img_zoom_in);
        imageButtonZoomOut=(ImageButton) mToolbar.findViewById(R.id.img_zoom_out);
         fromClass = getIntent().getBooleanExtra("fromClass", false);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
       // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //headerTitle.setText("Car List");
        frame=(FrameLayout)findViewById(R.id.container);
        getSupportActionBar().setTitle("");

        imageButtonZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoom(1.5f, 1.5f, new PointF(0, 0));
            }
        });

        imageButtonZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoom(1f, 1f, new PointF(0, 0));
            }
        });


        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);

        mNavigationDrawerFragment.closeDrawer();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mNavigationDrawerFragment.closeDrawer();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {

        //  headerTitle = (TextView) mToolbar.findViewById(R.id.mytext);
        FragmentManager mFragmentManager = getSupportFragmentManager();
        Toast.makeText(this, "Menu item selected -> " + position, Toast.LENGTH_SHORT).show();


        switch (position) {
            case 0:

                if(fromClass){
//                    isFromDrawer
//                mFragment = ClassesDetailFragment.newInstance();

                    finish();
                    Intent toclass=new Intent(NavigationActivity.this,ClassActivity.class);
                    startActivity(toclass);
//                   ClassFragment.relative_header.setVisibility(View.GONE);
                }
                else if(classID=false){
                    mFragment = ClassFragment.newInstance();
                }else {
                    mFragment = ClassesDetailFragment.newInstance();
                    NavigationDrawerFragment.imageView.setVisibility(View.GONE);
                }




                break;
            case 1:
                NavigationDrawerFragment.imageView.setVisibility(View.VISIBLE);
                NavigationDrawerFragment.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFragment = LessonProfile.newInstance();
                    }
                });

                mFragment = LessonFragment.newInstance();


                break;

            case 2:
                NavigationDrawerFragment.imageView.setVisibility(View.GONE);
                mFragment = ResourceFragment.newInstance();


                break;

            case 3:
                NavigationDrawerFragment.imageView.setVisibility(View.GONE);
                mFragment = QuizFragment.newInstance();


                break;

            case 4:
                NavigationDrawerFragment.imageView.setVisibility(View.GONE);
                mFragment = StudentFragment.newInstance();


                break;

            case 5:
                NavigationDrawerFragment.imageView.setVisibility(View.GONE);
                mFragment = GradeFragment.newInstance();


                break;

            case 6:


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







    @Override
    public void onBackPressed() {


        if (mNavigationDrawerFragment.isDrawerOpen()){
            mNavigationDrawerFragment.closeDrawer();
        }

        else {

            super.onBackPressed();

        }

    }

   /* public void clearPrefernces() {
        editor = sharedPreferences.edit();
        editor.remove(email);
        editor.remove(uid);
        editor.remove(pwd);
        editor.clear();
        editor.commit();


    }*/

    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        frame.setPivotX(pivot.x);
        frame.setPivotY(pivot.y);
        frame.setScaleX(scaleX);
        frame.setScaleY(scaleY);
    }
}
