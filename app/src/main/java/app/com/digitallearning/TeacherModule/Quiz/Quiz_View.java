package app.com.digitallearning.TeacherModule.Quiz;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 2/3/16.
 */
public class Quiz_View extends Fragment {
    View rootview;
    TextView headerTitle;
    String textHeader;
    ViewPager _mViewPager;
    CustomPagerAdapter mPagerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.view_quiz, container, false);
        _mViewPager = (ViewPager)rootview. findViewById(R.id.viewPager);
        mPagerAdapter = new CustomPagerAdapter(getActivity());

        _mViewPager.setAdapter(mPagerAdapter);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        //   getActivity().getActionBar().setHomeButtonEnabled(false);

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("View Quiz");
        initData();
        return rootview;

    }

    private void initData() {
        textHeader ="sdhfygsjdgf";




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
            View itemView = mLayoutInflater.inflate(R.layout.list_quiz_view, container,
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


            textView.setText("Quiz"+" "+ pos +" "+"of"+" "+"5");


            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // Remove viewpager_item.xml from ViewPager
            ((ViewPager) container).removeView((RelativeLayout) object);

        }
    }
}


