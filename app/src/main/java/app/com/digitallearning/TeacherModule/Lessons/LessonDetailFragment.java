package app.com.digitallearning.TeacherModule.Lessons;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Model.Data;
import app.com.digitallearning.TeacherModule.Model.QuizData;
import app.com.digitallearning.Utill.GlobalClass;

/**
 * Created by ${ShalviSharma} on 12/28/15.
 */
public class LessonDetailFragment extends Fragment implements ViewPager.OnPageChangeListener {

    int size;

    View rootview;
    ViewPager _mViewPager;
    SectionsPagerAdapter mPagerAdapter;
    TextView headerTitle;
    String textHeader, sizeget;
    int positionValue, pos1;
    SharedPreferences preferences;
    String Sch_Mem_id, cla_classid, lessonname, lessonid;
    int position;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    ProgressDialog dlg;
    ArrayList<Data> dataList = new ArrayList<Data>();
    ArrayList<QuizData> quizDatalist = new ArrayList<QuizData>();
    public static int currentPage_postion = 0;
    //CareerAdapter career;
    private int currentPage;
    private View view;
    LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_lesson_detail, container, false);
        _mViewPager = (ViewPager) rootview.findViewById(R.id.viewPager);
        imageButtonZoomIn = (ImageButton) rootview.findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) rootview.findViewById(R.id.img_zoom_out);
        dlg = new ProgressDialog(getActivity());


        if (getArguments() != null) {
            Bundle bundle = this.getArguments();
            textHeader = bundle.getString("HEADER_TEXT");
            positionValue = bundle.getInt("POSITION");
            dataList = (ArrayList<Data>) bundle.getSerializable("ArrayList");

            Log.e("dataListArrayList",""+dataList);
        }

        lessonname = getArguments().getString("positioninLesson");
        lessonid = getArguments().getString("lessonid");
        position = getArguments().getInt("position");
        sizeget = getArguments().getString("sizeget");
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Sch_Mem_id = preferences.getString("Sch_Mem_id", "");
        Log.e("Sch_Mem_id", "" + Sch_Mem_id);

        cla_classid = preferences.getString("cla_classid", "");
        Log.e("cla_classid", "" + cla_classid);
        // new Get_Lesson().execute(cla_classid, Sch_Mem_id);
        Log.e("txtHeader", "" + textHeader);
        Log.e("positionValue", "" + position);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        initData();
        size = getArguments().getInt(LessonFragment.LESSON_SIZE);

        mPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());
        _mViewPager.setAdapter(mPagerAdapter);
        _mViewPager.setOnPageChangeListener(this);
        // currentPage_postion = _mViewPager.getCurrentItem();
        Log.e("currentPage_postionSer", "" + currentPage_postion);
       /* _mViewPager.setCurrentItem(currentPage_postion);
          //  career=new CareerAdapter(getActivity(),quizDatalist);
        quizlist.setAdapter(career);
*/


        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        // headerTitle.setText(lessonname);
        _mViewPager.setCurrentItem(position);
        // headerTitle.setText(lessonname);


//        imageButtonZoomIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                zoom(1.5f, 1.5f, new PointF(0, 0));
//            }
//        });
//
//        imageButtonZoomOut.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                zoom(1f, 1f, new PointF(0, 0));
//            }
//        });

        return rootview;
    }

    private void initData() {
        textHeader = "sdhfygsjdgf";
    }


    @Override
    public void onPageSelected(int pos) {
        //  Toast.makeText(getActivity(),
        //         "Selected page position: " + pos, Toast.LENGTH_SHORT).show();
        position = pos;

        Log.e("pageselected", "jkj" + position + "");
        // headerTitle.setText(GlobalClass.lessonsize[pos]);

       headerTitle.setText(dataList.get(pos).getLessonName());
        //   _mViewPager.setCurrentItem(position);
//        headerTitle.setText(dataList.get(position).getLessonName());
        mPagerAdapter.notifyDataSetChanged();


    }

    // This method will be invoked when the current page is scrolled
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // Code goes here
        // Toast.makeText(getActivity(),
        //   "Selected page positionagain: " + position, Toast.LENGTH_SHORT).show();
        Log.e("pagescroll11", "pageselector" + position);
        GlobalClass.finalValue = position;

        Log.e("pagescroll11", GlobalClass.finalValue + "");


    }

    // Called when the scroll state changes:
    // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
    @Override
    public void onPageScrollStateChanged(int state) {
        // Code goes here
        Log.e("pagescroll", "pageselector");

    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Log.e("pageselector", "pageselector");
            FullDetailFragment fullDeatilFragment = new FullDetailFragment();
            Bundle arg = new Bundle();
            arg.putInt("position", position);
            arg.putString("lessonId", lessonid);
            arg.putString("sizeget", sizeget);
            arg.putString("lessonname", lessonname);
            fullDeatilFragment.setArguments(arg);
            return fullDeatilFragment;
        }

        @Override
        public int getCount() {
            return size;
        }


    }


}
