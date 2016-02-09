package app.com.digitallearning.StudentModule.StudentLesson;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 2/9/16.
 */
public class Student_Lesson_Profile extends Fragment {
    View rootview;
    TextView headerTitle,txtDate;
    String textHeader;
    ImageButton imageButtonZoomIn, imageButtonZoomOut,back;
    RelativeLayout rel;
    public static Student_Lesson_Profile newInstance() {
        Student_Lesson_Profile mFragment = new Student_Lesson_Profile();
        /*Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);*/
        return mFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.student_lesson_profile, container, false);

        imageButtonZoomIn = (ImageButton)rootview. findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton)rootview. findViewById(R.id.img_zoom_out);
        rel=(RelativeLayout)rootview.findViewById(R.id.rel);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        //   getActivity().getActionBar().setHomeButtonEnabled(false);

        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        txtDate=(TextView)rootview.findViewById(R.id.txt_date_lesson) ;
        headerTitle.setText("Profile");
        initData();
        back=(ImageButton)rootview.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });
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
        return rootview;

    }

    private void initData() {
        textHeader ="Profile";




    }
    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        rel.setPivotX(pivot.x);
        rel.setPivotY(pivot.y);
        rel.setScaleX(scaleX);
        rel.setScaleY(scaleY);
    }

}