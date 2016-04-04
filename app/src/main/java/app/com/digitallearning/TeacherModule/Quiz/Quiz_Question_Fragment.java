package app.com.digitallearning.TeacherModule.Quiz;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Model.Quiz_Question;

/**
 * Created by ${PSR} on 2/6/16.quiz_question_fragment
 */
public class Quiz_Question_Fragment extends Fragment {
    View rootview;
    TextView headerTitle;
    String textHeader,strquestion,strans1,strans2,strans3,strans4,correctans,position;
    ScrollView relativescrollview;
    LinearLayout linearlayout;
    Button addmore1,save1;
    private View view;
    LayoutInflater inflater;
    int pos = 1;
    EditText question,ans1,ans2,ans3,ans4;
    CheckBox check1,check2,check3,check4;
    ArrayList<Quiz_Question> listing=new ArrayList<Quiz_Question>();
    String quizdata;

    @Override
    public View onCreateView(LayoutInflater inflater,final ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.quiz_question_fragment, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        //   getActivity().getActionBar().setHomeButtonEnabled(false);

        relativescrollview = (ScrollView) rootview.findViewById(R.id.relativescrollview);
        linearlayout = (LinearLayout) rootview.findViewById(R.id.linearlayout);
        addmore1 = (Button) rootview.findViewById(R.id.addmore1);
        save1=(Button)rootview.findViewById(R.id.save1) ;

        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.question_layout, container,false);
        question=(EditText) view.findViewById(R.id.question) ;
        ans1=(EditText)view.findViewById(R.id.ans1) ;
        ans2=(EditText)view.findViewById(R.id.ans2) ;
        ans3=(EditText)view.findViewById(R.id.ans3) ;
        ans4=(EditText)view.findViewById(R.id.ans4) ;
        check1=(CheckBox)view.findViewById(R.id.check1) ;
        check2=(CheckBox)view.findViewById(R.id.check2) ;
        check3=(CheckBox)view.findViewById(R.id.check3) ;
        check4=(CheckBox)view.findViewById(R.id.check4) ;

        check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    check2.setChecked(false);
                    check3.setChecked(false);
                    check4.setChecked(false);
                    correctans= String.valueOf(1);
                }
            }
        });

        check2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    check1.setChecked(false);
                    check3.setChecked(false);
                    check4.setChecked(false);
                    correctans= String.valueOf(2);
                }
            }
        });

        check3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    check1.setChecked(false);
                    check2.setChecked(false);
                    check4.setChecked(false);
                    correctans= String.valueOf(3);
                }
            }
        });


        check4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    check1.setChecked(false);
                    check2.setChecked(false);
                    check3.setChecked(false);
                    correctans= String.valueOf(4);

                }
            }
        });
        linearlayout.addView(view, 0);

        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        headerTitle.setText("Quiz Question");
        initData();

        addmore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // view = inflater.inflate(R.layout.question_layout, container,false);
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                view = inflater.inflate(R.layout.question_layout, container,false);

                Log.e("sizepos",""+pos);
                linearlayout.addView(view, pos);
                pos ++;

                relativescrollview.post(new Runnable() {
                    @Override
                    public void run() {

                        relativescrollview.fullScroll(View.FOCUS_DOWN);

                    }
                });


            }
        });

        save1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               position= String.valueOf(pos);
               strquestion=question.getText().toString();
                strans1=ans1.getText().toString();
                strans2=ans2.getText().toString();
                strans3=ans3.getText().toString();
                strans4=ans4.getText().toString();

                Log.e("childcount",""+linearlayout.getChildCount());
                Log.e("strquestion",""+strquestion);
                Log.e("strans1",""+strans1);
                Log.e("strans2",""+strans2);
                Log.e("strans3",""+strans3);
                Log.e("strans4",""+strans4);
                Log.e("correctans",""+correctans);
                JSONObject object=new JSONObject();
                for (int i=0;i<linearlayout.getChildCount();i++){
                    JSONObject object1=new JSONObject();
                    View view = linearlayout.getChildAt(i);

                    question=(EditText) view.findViewById(R.id.question) ;
                    ans1=(EditText)view.findViewById(R.id.ans1) ;
                    ans2=(EditText)view.findViewById(R.id.ans2) ;
                    ans3=(EditText)view.findViewById(R.id.ans3) ;
                    ans4=(EditText)view.findViewById(R.id.ans4) ;
                    check1=(CheckBox)view.findViewById(R.id.check1) ;
                    check2=(CheckBox)view.findViewById(R.id.check2) ;
                    check3=(CheckBox)view.findViewById(R.id.check3) ;
                    check4=(CheckBox)view.findViewById(R.id.check4) ;

                    position= String.valueOf(pos);
                    strquestion=question.getText().toString();
                    strans1=ans1.getText().toString();
                    strans2=ans2.getText().toString();
                    strans3=ans3.getText().toString();
                    strans4=ans4.getText().toString();

                    String checked = null;
                    if(check1.isChecked()){
                        checked= String.valueOf(1);

                    }else if(check2.isChecked()){
                        checked= String.valueOf(2);
                    }
                    else if(check3.isChecked()){
                        checked=String.valueOf(3);
                    }
                    else  if(check4.isChecked()){
                        checked=String .valueOf(4);
                    }
//{"correct_ans":"2","quiz_ans_1":"1a","quiz_ans_2":"2a","quiz_ans_3":"3a","quiz_ans_4":"4a","quiz_question":"Ques 1"}}
                    JSONObject obj = new JSONObject();
                    try {
                        obj.put("correct_ans", checked);
                        obj.put("quiz_ans_1", strans1);
                        obj.put("quiz_ans_2",strans2);
                        obj.put("quiz_ans_3", strans3);
                        obj.put("quiz_ans_4", strans4);
                        obj.put("quiz_question", strquestion);
                        Log.e("obj",""+obj);

                        object.put(String.valueOf(i+1), obj);
                        quizdata=object.toString();
                        AddQuiz.dataquiz=quizdata;
                        Quiz_Edit.editdataquiz=quizdata;
                        Log.e("updateQuiz",""+Quiz_Edit.editdataquiz);

                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();

                    }
                }


                    Log.e("FinalJson",""+object);
                Log.e("quizdata",""+quizdata);

                Log.e("staticstrquestion",""+strquestion);
                AddQuiz.strquizquestionname=strquestion;

               /* Quiz_Question quiz_question=new Quiz_Question();
                quiz_question.getQuiz_question();
                quiz_question.getAns1();
                quiz_question.getAns2();
                quiz_question.getAns3();
                quiz_question.getAns4();
                quiz_question.isCorrectans();
                quiz_question.getCount();
                listing.add(quiz_question);

                Log.e("listingsize",""+  listing.size());
                for (int i1=0;i1< listing.size();i1++ ) {

                    //Log.e("strquestion",""+listing.get(i1).getQuiz_question());
                }
*/


                getFragmentManager().popBackStack();
            }
        });
        return rootview;

    }

    private void initData() {
        textHeader = "sdhfygsjdgf";
    }

}
