package app.com.digitallearning.TeacherModule.Lessons;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 2/3/16.
 */
public class Lesson_Edit extends Fragment {
    View rootview;
    TextView headerTitle,txtDate;
    String textHeader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.lesson_edit, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        //   getActivity().getActionBar().setHomeButtonEnabled(false);

        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        txtDate=(TextView)rootview.findViewById(R.id.txt_date_lesson) ;
        headerTitle.setText("Modify Lesson");
        initData();

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        txtDate.setText(dateFormat.format(date));
        return rootview;

    }

    private void initData() {
        textHeader ="sdhfygsjdgf";




    }

}