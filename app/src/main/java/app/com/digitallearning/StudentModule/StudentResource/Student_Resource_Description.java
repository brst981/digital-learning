package app.com.digitallearning.StudentModule.StudentResource;

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
 * Created by ${PSR} on 1/29/16.
 */
public class Student_Resource_Description extends Fragment {
    View rootview;
    TextView headerTitle,description,textView;
    String textHeader,studentdescription,studenttitle;
    ImageButton back;
    RelativeLayout desres;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.student_description, container, false);
        description=(TextView)rootview.findViewById(R.id.description);
        textView=(TextView)rootview.findViewById(R.id.textView);
        desres=(RelativeLayout)rootview.findViewById(R.id.desres);
        desres.setClickable(true);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Students");
        initData();
        back=(ImageButton)rootview.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        studenttitle=getArguments().getString("studenttitle");
        Log.e("studenttitleres",""+studenttitle);
        studentdescription=getArguments().getString("studentdescription");
        description.setText(studentdescription);
        textView.setText(studenttitle);

        return rootview;
    }

    private void initData() {
        textHeader ="sdhfygsjdgf";}

}
