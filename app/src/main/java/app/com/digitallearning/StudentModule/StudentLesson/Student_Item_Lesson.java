package app.com.digitallearning.StudentModule.StudentLesson;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Lessons.LessonFragment;

/**
 * Created by ${PSR} on 2/4/16.
 */
public class Student_Item_Lesson extends Fragment implements ViewPager.OnPageChangeListener {
    View rootview;
    ViewPager _mViewPager;
    TextView headerTitle;
    String txtHeader,lessonname,lessonid,sizeget;
    int positionValue,pos1,position,size;
    SectionsPagerAdapter mPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.student_lesson_detail, container, false);
        _mViewPager = (ViewPager)rootview. findViewById(R.id.viewPager);
        lessonname = getArguments().getString("positioninLesson");
        lessonid = getArguments().getString("lessonid");
        position = getArguments().getInt("position");
        sizeget=getArguments().getString("sizeget");
        size = getArguments().getInt(LessonFragment.LESSON_SIZE);
        if (getArguments() != null) {
            Bundle bundle = this.getArguments();
            txtHeader = bundle.getString("HEADER_TEXT");
            positionValue = bundle.getInt("POSITION");
        }
        pos1=getArguments().getInt("positioninLesson");
        Log.e("pos1",""+pos1);

        Log.e("txtHeader",""+txtHeader);
        Log.e("positionValue",""+positionValue);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("sdhfygsjdgf");
        mPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        _mViewPager.setAdapter(mPagerAdapter);
        _mViewPager.setOnPageChangeListener(this);
        return rootview;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    @Override
    public void onPageSelected(int position) {

    }


    @Override
    public void onPageScrollStateChanged(int state) {

    }




    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            StudentFullDetailFragment studentFullDetailFragment = new StudentFullDetailFragment();
            Bundle arg = new Bundle();
            arg.putInt("position", position);
            arg.putString("lessonId", lessonid);
            arg.putString("sizeget",sizeget);
            arg.putString("lessonname",lessonname);
            studentFullDetailFragment.setArguments(arg);
            return studentFullDetailFragment;
        }

        @Override
        public int getCount() {
            return size;
        }


    }
}
