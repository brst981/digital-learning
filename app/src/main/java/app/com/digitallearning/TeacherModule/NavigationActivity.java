package app.com.digitallearning.TeacherModule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import app.com.digitallearning.NavigationDrawerCallbacks;
import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Classes.ClassesDetailFragment;
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
    int val=0;
    boolean classID=false;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
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
                    //popBackStack();
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

               Intent gototeacher=new Intent(NavigationActivity.this,TeacherLoginActivity.class);

                preferences = PreferenceManager.getDefaultSharedPreferences(NavigationActivity.this);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("rememberMe", val);
                Log.e("val",""+val);
                editor.commit();
                startActivity(gototeacher);
                finishAffinity();



                break;
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

    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        frame.setPivotX(pivot.x);
        frame.setPivotY(pivot.y);
        frame.setScaleX(scaleX);
        frame.setScaleY(scaleY);
    }

}
