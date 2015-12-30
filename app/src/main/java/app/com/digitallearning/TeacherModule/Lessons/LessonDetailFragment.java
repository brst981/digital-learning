package app.com.digitallearning.TeacherModule.Lessons;

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
 * Created by ${ShalviSharma} on 12/28/15.
 */
public class LessonDetailFragment extends Fragment {
    View rootview;
    ViewPager _mViewPager;
    CustomPagerAdapter mPagerAdapter;
    TextView headerTitle;
    String txtHeader;
    int positionValue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_lesson_detail, container, false);
        _mViewPager = (ViewPager)rootview. findViewById(R.id.viewPager);
        mPagerAdapter = new CustomPagerAdapter(getActivity());

        _mViewPager.setAdapter(mPagerAdapter);

        if (getArguments() != null) {
            Bundle bundle = this.getArguments();
            txtHeader = bundle.getString("HEADER_TEXT");
            positionValue = bundle.getInt("POSITION");
        }


        Log.e("txtHeader",""+txtHeader);
        Log.e("positionValue",""+positionValue);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText(txtHeader);

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
            View itemView = mLayoutInflater.inflate(R.layout.layout_lesson_items, container,
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

            textView.setText("Lesson"+" "+ positionValue++ +" "+"of"+" "+"5");


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
