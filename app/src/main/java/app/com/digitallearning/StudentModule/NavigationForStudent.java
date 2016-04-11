package app.com.digitallearning.StudentModule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.os.AsyncTask;
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

import java.text.SimpleDateFormat;
import java.util.Date;

import app.com.digitallearning.NavigationDrawerCallbacks;
import app.com.digitallearning.R;
import app.com.digitallearning.StudentModule.StudentLesson.StudentLessonFragment;
import app.com.digitallearning.StudentModule.StudentQuiz.StudentQuizFragment;
import app.com.digitallearning.StudentModule.StudentResource.StudentResourceFragment;
import app.com.digitallearning.WebServices.WSConnector;

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
    int val=0;
    SharedPreferences preferences;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    ProgressDialog dlg;
    String curdate,Sch_Mem_id;
    boolean lessonselected;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_student);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        imageButtonZoomIn=(ImageButton) mToolbar.findViewById(R.id.img_zoom_in);
        imageButtonZoomOut=(ImageButton) mToolbar.findViewById(R.id.img_zoom_out);
        fromClass = getIntent().getBooleanExtra("fromClass", false);
        lessonselected=getIntent().getBooleanExtra("lessonselected",false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        preferences = PreferenceManager.getDefaultSharedPreferences(NavigationForStudent.this);
        Sch_Mem_id = preferences.getString("Sch_Mem_id", "");
     //   this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //headerTitle.setText("Car List");
        frame=(FrameLayout)findViewById(R.id.container);
        getSupportActionBar().setTitle("");
        dlg=new ProgressDialog(NavigationForStudent.this);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
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

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        final Date date = new Date();
        curdate= String.valueOf(dateFormat.format(date));

        mNavigationDrawerFragment = (NavigationDrawerStudent) getFragmentManager().findFragmentById(R.id.fragment_drawer);
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);

        mNavigationDrawerFragment.closeDrawer();

        //Student_Logout student_logout=new Student_Logout();
       // student_logout.execute();

        Log.e("curdate",""+curdate);
        Log.e("Sch_Mem_id",""+Sch_Mem_id);




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
//                   ClassFragment.relative_header.setVisibility(View.GONE);
                }else {
                    NavigationDrawerStudent.imageView.setVisibility(View.GONE);
                    mFragment = StudentLessonFragment.newInstance();

                }
               /* if(lessonselected==true){
                    Log.e("lessonselected","lessonselected"+lessonselected);
                    NavigationDrawerStudent.imageView.setVisibility(View.VISIBLE);
                    mFragment = StudentLessonFragment.newInstance();
                }*/



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

                new Student_Logout().execute(Sch_Mem_id,curdate);

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
   class Student_Logout extends AsyncTask<String, Integer, String> {



       @Override
       protected String doInBackground(String... params) {

           return WSConnector.student_logout(params[0], params[1]);
       }

       @Override
       protected void onPreExecute() {
           super.onPreExecute();
           dlg.setMessage("Loading.....");
           dlg.setCancelable(false);
           dlg.show();

       }
       @Override
       protected void onPostExecute(String result) {
           super.onPostExecute(result);
           dlg.dismiss();
           Log.e("Student_Logout", "" + result);

           if (result.contains("true")) {


               Intent gototeacher=new Intent(NavigationForStudent.this,StudentLoginActivity.class);
               preferences = PreferenceManager.getDefaultSharedPreferences(NavigationForStudent.this);
               SharedPreferences.Editor editor = preferences.edit();
               editor.putInt("remembermecheckedstu", val);
               Log.e("val",""+val);
               editor.commit();
               startActivity(gototeacher);
               finishAffinity();

           } else if (result.contains("false")) {
                            /*AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                            alertDialog.setMessage("No data").setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // TODO Auto-generated method stub
                                            dialog.dismiss();
                               *//* Intent deletetoclass=new Intent(getActivity(),ClassActivity.class);
                                startActivity(deletetoclass);
                                getActivity().finish();*//*

                                        }
                                    });

                            AlertDialog dialog = alertDialog.create();
                            dialog.show();
                            TextView messageText = (TextView) dialog
                                    .findViewById(android.R.id.message);
                            messageText.setGravity(Gravity.CENTER);*/
           }
       }

   }


}
