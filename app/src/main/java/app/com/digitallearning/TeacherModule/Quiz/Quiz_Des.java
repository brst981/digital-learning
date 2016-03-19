package app.com.digitallearning.TeacherModule.Quiz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 2/6/16.
 */
public class Quiz_Des extends Fragment {
    View rootview;
    TextView headerTitle;
    String textHeader;
    Button done;
    EditText description;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.quiz_des, container, false);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("");
        //   getActivity().getActionBar().setHomeButtonEnabled(false);
        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        headerTitle.setText("Description");
        initData();
        description=(EditText)rootview.findViewById(R.id.description);
        done=(Button)rootview.findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddQuiz.quizdescription=description.getText().toString();
                getFragmentManager().popBackStack();
            }
        });
        return rootview;

    }

    private void initData() {
        textHeader ="sdhfygsjdgf";




    }

}
