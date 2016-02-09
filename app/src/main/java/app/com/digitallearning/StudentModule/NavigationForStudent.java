package app.com.digitallearning.StudentModule;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import app.com.digitallearning.NavigationDrawerCallbacks;
import app.com.digitallearning.R;
import app.com.digitallearning.StudentModule.StudentLesson.StudentLessonFragment;
import app.com.digitallearning.StudentModule.StudentQuiz.StudentQuizFragment;
import app.com.digitallearning.StudentModule.StudentResource.StudentResourceFragment;

/**
 * Created by ${PSR} on 1/28/16.
 */
public class NavigationForStudent extends AppCompatActivity implements NavigationDrawerCallbacks {
    private Toolbar mToolbar;
    private NavigationDrawerStudent mNavigationDrawerFragment;
    Fragment mFragment;
    boolean fromClass = false;
    public static boolean isFromDrawer = false;
    FrameLayout frame;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_student);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        imageButtonZoomIn=(ImageButton) mToolbar.findViewById(R.id.img_zoom_in);
        imageButtonZoomOut=(ImageButton) mToolbar.findViewById(R.id.img_zoom_out);
        fromClass = getIntent().getBooleanExtra("fromClass", false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //headerTitle.setText("Car List");
        frame=(FrameLayout)findViewById(R.id.container);
        getSupportActionBar().setTitle("");

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mNavigationDrawerFragment = (NavigationDrawerStudent) getFragmentManager().findFragmentById(R.id.fragment_drawer);
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
//                   ClassFragment.relative_header.setVisibility(View.GONE);
                }else {
                    NavigationDrawerStudent.imageView.setVisibility(View.GONE);
                    mFragment = StudentLessonFragment.newInstance();

                }



                break;
            case 1:
                NavigationDrawerStudent.imageView.setVisibility(View.VISIBLE);
                mFragment = StudentLessonFragment.newInstance();


                break;

            case 2:
                NavigationDrawerStudent.imageView.setVisibility(View.GONE);
                mFragment = StudentResourceFragment.newInstance();


                break;

            case 3:
                NavigationDrawerStudent.imageView.setVisibility(View.GONE);
                mFragment = StudentQuizFragment.newInstance();


                break;

            case 4:
                NavigationDrawerStudent.imageView.setVisibility(View.GONE);
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







    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen()){
            mNavigationDrawerFragment.closeDrawer();
        }

        else{
            super.onBackPressed();}


    }
    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        frame.setPivotX(pivot.x);
        frame.setPivotY(pivot.y);
        frame.setScaleX(scaleX);
        frame.setScaleY(scaleY);
    }
   /* public void clearPrefernces() {
        editor = sharedPreferences.edit();
        editor.remove(email);
        editor.remove(uid);
        editor.remove(pwd);
        editor.clear();
        editor.commit();


    }*/
}
