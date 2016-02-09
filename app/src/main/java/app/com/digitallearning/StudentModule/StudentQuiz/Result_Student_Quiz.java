package app.com.digitallearning.StudentModule.StudentQuiz;

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
 * Created by ${PSR} on 2/4/16.
 */
public class Result_Student_Quiz extends Fragment {
    View rootview;
    RelativeLayout rel;
    ImageButton back;
    TextView headerTitle;
    String textHeader;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.result_quiz_view, container, false);
        back=(ImageButton)rootview.findViewById(R.id.back);
        rel=(RelativeLayout)rootview.findViewById(R.id.rel) ;
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
