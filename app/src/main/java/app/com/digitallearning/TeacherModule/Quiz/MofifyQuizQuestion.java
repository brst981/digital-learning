package app.com.digitallearning.TeacherModule.Quiz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 2/9/16.
 */
public class MofifyQuizQuestion extends Fragment {
    View rootview;
    TextView headerTitle;
    String textHeader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.modify_quiz_question, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        //   getActivity().getActionBar().setHomeButtonEnabled(false);

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Quiz Question");
        initData();
        return rootview;

    }

    private void initData() {
        textHeader ="sdhfygsjdgf";




    }

}
