package app.com.digitallearning.TeacherModule.Quiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andexert.library.RippleView;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 2/3/16.
 */
public class Quiz_Edit extends Fragment {
    View rootview;
    TextView headerTitle;
    String textHeader;
    RippleView rippleViewPreview,ripple_lesson,ripple_quizquestion,ripple_quizdec;
    final CharSequence[] lesson = {
            "Lesson 1", "Lesson 2", };
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.quiz_edit, container, false);
        ripple_lesson=(RippleView)rootview.findViewById(R.id.ripple_lesson);
        ripple_quizquestion=(RippleView)rootview.findViewById(R.id.ripple_quizquestion);
        ripple_quizdec=(RippleView)rootview.findViewById(R.id.ripple_quizdec);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        //   getActivity().getActionBar().setHomeButtonEnabled(false);

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Modify Quiz");
        initData();

        ripple_lesson.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
               /* FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Lesson lesson = new Lesson();
                fragmentTransaction.replace(R.id.container, lesson).addToBackStack(null);
                fragmentTransaction.commit();*/


                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Make your selection");
                builder.setItems(lesson, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        //  mDoneButton.setText(items[item]);
                        Log.e("items[item]",""+lesson[item]);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        ripple_quizquestion.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                MofifyQuizQuestion mofifyQuizQuestion = new MofifyQuizQuestion();
                fragmentTransaction.replace(R.id.container, mofifyQuizQuestion).addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        ripple_quizdec.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Quiz_Des quiz_des = new Quiz_Des();
                fragmentTransaction.replace(R.id.container, quiz_des).addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        return rootview;

    }

    private void initData() {
        textHeader ="sdhfygsjdgf";




    }

}
