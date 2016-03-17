package app.com.digitallearning.StudentModule.StudentLesson;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 2/4/16.
 */
public class Student_Item_Lesson extends Fragment {
    View rootview;
    ViewPager _mViewPager;
    CustomPagerAdapter mPagerAdapter;
    TextView headerTitle;
    String txtHeader;
    int positionValue,pos1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.student_item_lesson, container, false);
        _mViewPager = (ViewPager)rootview. findViewById(R.id.viewPager);
        mPagerAdapter = new CustomPagerAdapter(getActivity());

        _mViewPager.setAdapter(mPagerAdapter);

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

        return rootview;
    }

    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;



        public CustomPagerAdapter(Context mContext) {
            this.mContext = mContext;

        }

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            // Declare Variables


            mLayoutInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = mLayoutInflater.inflate(R.layout.lesson_item_student, container,
                    false);

            TextView textView = (TextView)itemView.findViewById(R.id.textView_lesson_count);
            ImageView imageViewLeft =(ImageView)itemView.findViewById(R.id.img_left_icon);
            ImageView imageViewRight =(ImageView)itemView.findViewById(R.id.img_right_icon);
         /*  if (position==4){
               imageViewRight.setVisibility(View.GONE);
           }*/

            if (position==0){
                imageViewLeft.setVisibility(View.GONE);
            }
            int pos=position+1;


            textView.setText("Lesson"+" "+ pos +" "+"of"+" "+"5");


            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            ((ViewPager) container).removeView((RelativeLayout) object);

        }
    }
}
