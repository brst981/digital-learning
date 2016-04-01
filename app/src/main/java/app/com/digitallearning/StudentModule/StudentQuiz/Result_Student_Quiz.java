package app.com.digitallearning.StudentModule.StudentQuiz;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 2/4/16.
 */
public class Result_Student_Quiz extends Fragment {
    View rootview;
    RelativeLayout rel;
    ImageButton back;
    TextView headerTitle,txtusername,userscore,userans,userpercantage,userresult,usertimetaken;
    String textHeader,username,scoring ,correct,min_score,result_grade,total_time;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.result_quiz_view, container, false);
        back=(ImageButton)rootview.findViewById(R.id.back);
        rel=(RelativeLayout)rootview.findViewById(R.id.rel) ;
        username= getArguments().getString("username");
        scoring=getArguments().getString("scoring");
        correct=getArguments().getString("correct");
        min_score=getArguments().getString("min_score");
        result_grade=getArguments().getString("result_grade");
        total_time=getArguments().getString("total_time");

        txtusername=(TextView)rootview.findViewById(R.id.txtusername);
        userscore=(TextView)rootview.findViewById(R.id.userscore);
        userans=(TextView)rootview.findViewById(R.id.userans);
        userpercantage=(TextView)rootview.findViewById(R.id.userpercantage);
        userresult=(TextView)rootview.findViewById(R.id.userresult);
        usertimetaken=(TextView)rootview.findViewById(R.id.usertimetaken);
        Log.e("timeres",""+usertimetaken);

        txtusername.setText(username);
        userscore.setText(scoring);
        userans.setText(correct);
        userpercantage.setText(min_score);
        userresult.setText(result_grade);
        usertimetaken.setText(total_time);


        imageButtonZoomIn = (ImageButton)rootview. findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) rootview.findViewById(R.id.img_zoom_out);
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

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        //   getActivity().getActionBar().setHomeButtonEnabled(false);

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Result");
        initData();
        return rootview;

    }

    private void initData() {
        textHeader ="sdhfygsjdgf";

    }

    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        rel.setPivotX(pivot.x);
        rel.setPivotY(pivot.y);
        rel.setScaleX(scaleX);
        rel.setScaleY(scaleY);
    }



}
