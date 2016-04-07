package app.com.digitallearning.TeacherModule.Students;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;

import app.com.digitallearning.R;
import app.com.digitallearning.SlidingLayout.SlidingTabLayout;

/**
 * Created by ${ShalviSharma} on 1/1/16.
 */
public class StudentFragment extends Fragment {
    View rootview;
    CustomViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Archived Student(s)","Enrolled Student(s)"};
    String back ="0";
    int Numboftabs =2;
    TextView headerTitle;
    RippleView rippleViewAddStudent;
    ProgressDialog dlg;
    Button archivebutton,enrollbutton;
    public static int TabPos;
    LinearLayout frame;

    public static StudentFragment newInstance() {
        StudentFragment mFragment = new StudentFragment();
        /*Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);*/
        return mFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_student, container, false);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("");


        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Students");
      //  adapter =  new ViewPagerAdapter(getActivity().getSupportFragmentManager(),Titles,Numboftabs);
        archivebutton=(Button)rootview.findViewById(R.id.archivebutton) ;
        enrollbutton=(Button)rootview.findViewById(R.id.enrollbutton) ;
        frame=(LinearLayout)rootview.findViewById(R.id.frame);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ArchivedFragment archivedFragment = new ArchivedFragment();
        fragmentTransaction.replace(R.id.frame, archivedFragment).addToBackStack(null);
        fragmentTransaction.commit();
        archivebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                archivebutton.setBackgroundColor(Color.parseColor("#ffffff"));
                archivebutton.setTextColor(Color.parseColor("#89C139"));

                enrollbutton.setBackgroundColor(Color.parseColor("#89C139"));
                enrollbutton.setTextColor(Color.parseColor("#ffffff"));
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ArchivedFragment archivedFragment = new ArchivedFragment();
                fragmentTransaction.replace(R.id.frame, archivedFragment);
                fragmentTransaction.commit();
            }
        });


        enrollbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enrollbutton.setBackgroundColor(Color.parseColor("#ffffff"));
                enrollbutton.setTextColor(Color.parseColor("#89C139"));

                archivebutton.setBackgroundColor(Color.parseColor("#89C139"));
                archivebutton.setTextColor(Color.parseColor("#ffffff"));
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                EnrolledFragment enrolledFragment = new EnrolledFragment();
                fragmentTransaction.replace(R.id.frame, enrolledFragment);
                fragmentTransaction.commit();
            }
        });

        /*pager = (CustomViewPager)rootview.findViewById(R.id.pager);
        pager.setPagingEnabled(false);
        pager.setAdapter(adapter);*/


        /*tabs = (SlidingTabLayout)rootview.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);


        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.color_white);
            }
        });
        tabs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = (String) tabs.getTag();
                Log.e("TabName","tt"+TabPos);
            }
        });

        tabs.setViewPager(pager);*/

        rippleViewAddStudent=(RippleView)rootview.findViewById(R.id.ripple_add_student);

        rippleViewAddStudent.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AddStudentFragment classFragment = new AddStudentFragment();
                fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        return rootview;
    }
}
