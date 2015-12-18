package app.com.digitallearning.TeacherModule;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.WindowManager;

import app.com.digitallearning.NavigationDrawerCallbacks;
import app.com.digitallearning.R;

/**
 * Created by ${ShalviSharma} on 12/18/15.
 */
public class NavigationActivity extends AppCompatActivity implements NavigationDrawerCallbacks {
    private Toolbar mToolbar;
    private NavigationDrawerFragment mNavigationDrawerFragment;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_navigation);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //headerTitle.setText("Car List");

        getSupportActionBar().setTitle("");

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
        //Toast.makeText(this, "Menu item selected -> " + position, Toast.LENGTH_SHORT).show();


      /*  switch (position) {
            case 0:

                mFragment = HomeFragment.newInstance("");


                break;
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


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(NavigationActivity.this);

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

/*

        if (mFragment != null) {
            mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
        }
*/







    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();


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
