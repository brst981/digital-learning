package app.com.digitallearning.TeacherModule.Quiz;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Model.Data_Option;
import app.com.digitallearning.TeacherModule.Model.Data_Quiz_Info;
import app.com.digitallearning.TeacherModule.Model.Data_Quiz_Option;
import app.com.digitallearning.TeacherModule.Model.Data_Quiz_Question;
import app.com.digitallearning.TeacherModule.Model.Data_View_Quiz;
import app.com.digitallearning.TeacherModule.Model.View_Quiz_Listing;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 2/3/16.
 */
public class Quiz_View extends Fragment {
    View rootview;
    TextView headerTitle;
    String textHeader, quizid, userid, cla_classid, truevalue, truevalue1, correctans, question_id, option_id, quizzes_id;
    String question, ans1, ans2, ans3, ans4;
    SharedPreferences preferences;
    ViewPager _mViewPager;
    CustomPagerAdapter mPagerAdapter;
    ProgressDialog dlg;
    int onitemid, idvalue, viewpagerpos;
    RelativeLayout relquiz;
    private Data_View_Quiz dataViewQuiz;
    ArrayList<Data_Quiz_Question> mQuizOpt;
    ;
    ArrayList<View_Quiz_Listing> quizlisting = new ArrayList<View_Quiz_Listing>();
    /* ArrayList<View_Quiz_Listing_Questions> quizlisting1 = new ArrayList<View_Quiz_Listing_Questions>();
     ArrayList<View_Quiz_Name> quiznamelist = new ArrayList<View_Quiz_Name>();*/
    String quizdata;
    JSONObject jobject;
    JSONArray firstarr;
    int currentCount = -1;
    int pagerChildCount = -1;
    int pos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.view_quiz, container, false);
        _mViewPager = (ViewPager) rootview.findViewById(R.id.viewPager);
        relquiz = (RelativeLayout) rootview.findViewById(R.id.relquiz);
        dlg = new ProgressDialog(getActivity());
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("");
        //   getActivity().getActionBar().setHomeButtonEnabled(false);

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        initData();
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userid = preferences.getString("Sch_Mem_id", "");
        cla_classid = preferences.getString("cla_classid", "");
        //    try {
        quizid = getArguments().getString("quizid");
        idvalue = getArguments().getInt("idvalue");
        // }catch (Exception e){}

        onitemid = getArguments().getInt("onitemid");

        question_id = getArguments().getString("question_id");
        option_id = getArguments().getString("option_id");
        quizzes_id = getArguments().getString("quizzes_id");

        new Quiz_view().execute(cla_classid, userid, quizid);


//        _mViewPager.setAdapter(mPagerAdapter);

        _mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {
            }

            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                viewpagerpos = position;
                Log.e("viewpagerposition", "" + position);
            }

            public void onPageSelected(int position) {
                // Check if this is the page you want.
            }
        });


        return rootview;
    }

    private void initData() {
        textHeader = "sdhfygsjdgf";
    }

    class CustomPagerAdapter extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;
        Data_View_Quiz mDataViewQuiz;
        ArrayList<Data_Quiz_Question> mQuizOpt;

        public CustomPagerAdapter(Context mContext, Data_View_Quiz mDataViewQuiz) {
            this.mContext = mContext;
            this.mDataViewQuiz = mDataViewQuiz;
            mQuizOpt = mDataViewQuiz.getmQuizQuestions();
        }

        @Override
        public int getCount() {

            return mQuizOpt.size();
        }

        @Override
        public int getItemPosition(Object object) {
            Log.e("object", "" + object);
            Log.e("POSITION_NONE", "" + POSITION_NONE);
            return POSITION_NONE;

        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = mLayoutInflater.inflate(R.layout.list_quiz_view, container,
                    false);

            RelativeLayout relativeLayout1 = (RelativeLayout) itemView.findViewById(R.id.relative1);
            RelativeLayout relativeLayout2 = (RelativeLayout) itemView.findViewById(R.id.relative2);
            RelativeLayout relativeLayout3 = (RelativeLayout) itemView.findViewById(R.id.relative3);
            RelativeLayout relativeLayout4 = (RelativeLayout)itemView.findViewById(R.id.relative4);
            Data_Quiz_Info mInfo = mDataViewQuiz.getmDataQuizInfo();

            Data_Quiz_Option mOption = mQuizOpt.get(position).getmQuiz_option();
            final ArrayList<Data_Option> mData_options = mOption.getOption();


            TextView textquiz = (TextView) itemView.findViewById(R.id.textquiz);
            //    Log.e("nameofquiz",""+quiznamelist.get(position).getQuiz_name());

            textquiz.setText(mInfo.getQuiz_name());
            TextView description = (TextView) itemView.findViewById(R.id.description);
            description.setText(mInfo.getQuiz_desc());
            TextView textView = (TextView) itemView.findViewById(R.id.textView_lesson_count);

            ImageView imageViewLeft = (ImageView) itemView.findViewById(R.id.img_left_icon);
            ImageView imageViewRight = (ImageView) itemView.findViewById(R.id.img_right_icon);
            final EditText questionname = (EditText) itemView.findViewById(R.id.questionname);
            questionname.setText(mQuizOpt.get(position).getQuestion_name());

            Log.e("mQuizOpt", "" + mQuizOpt);
            Log.e("mData_options", "" + mData_options);
            https:
//youtu.be/42ebdXqke34
            Log.e("viewpagerpos", "" + position);
//            final EditText[] mEditDat = new EditText[4];
//
//            mEditDat[0] = (EditText) itemView.findViewById(R.id.textView_lesson_title);
//
//            mEditDat[1] = (EditText) itemView.findViewById(R.id.textView_lesson_description);
//
//            mEditDat[2] = (EditText) itemView.findViewById(R.id.textView_lesson_description_);
//
//            mEditDat[3] = (EditText) itemView.findViewById(R.id.textView_lesson_description1);


            final EditText textView_lesson_title = (EditText) itemView.findViewById(R.id.textView_lesson_title);

            final EditText textView_lesson_description = (EditText) itemView.findViewById(R.id.textView_lesson_description);

            final EditText textView_lesson_description_ = (EditText) itemView.findViewById(R.id.textView_lesson_description_);

            final EditText textView_lesson_description1 = (EditText) itemView.findViewById(R.id.textView_lesson_description1);

            for (int i = 0; i < mData_options.size(); i++) {
                Log.e("mData_options", "mData_options" + mData_options.size());


                if (mQuizOpt.get(position).getQuestion_id().equals(mData_options.get(i).getQuizzes_id())) {

                    if(mData_options.size()==1){

                        textView_lesson_title.setText(mData_options.get(0).getQuiz_option());
                        relativeLayout2.setVisibility(View.GONE);
                        relativeLayout3.setVisibility(View.GONE);
                        relativeLayout4.setVisibility(View.GONE);
                    }
                    else if(mData_options.size()==2){
                        relativeLayout3.setVisibility(View.GONE);
                        relativeLayout4.setVisibility(View.GONE);
                        textView_lesson_title.setText(mData_options.get(0).getQuiz_option());
                        textView_lesson_description.setText(mData_options.get(1).getQuiz_option());
                    }
                    else if(mData_options.size()==3){
                        relativeLayout4.setVisibility(View.GONE);
                        textView_lesson_title.setText(mData_options.get(0).getQuiz_option());
                        textView_lesson_description.setText(mData_options.get(1).getQuiz_option());
                        textView_lesson_description_.setText(mData_options.get(2).getQuiz_option());
                    }

                   else{
                        textView_lesson_title.setText(mData_options.get(0).getQuiz_option());
                        textView_lesson_description.setText(mData_options.get(1).getQuiz_option());
                        textView_lesson_description_.setText(mData_options.get(2).getQuiz_option());
                        textView_lesson_description1.setText(mData_options.get(3).getQuiz_option());
                    }

                }

            }


                 /*textView_lesson_description.setText(quizlisting.get(1).getQuiz_option());
               textView_lesson_description_.setText(quizlisting.get(2).getQuiz_option());
              textView_lesson_description1.setText(quizlisting.get(3).getQuiz_option());*/

          for(int x=0;x<quizlisting.size();x++){
                Log.e("textname",""+quizlisting.get(x).getQuiz_option());
                textView_lesson_title.setText(quizlisting.get(0).getQuiz_option());
                textView_lesson_description.setText(quizlisting.get(1).getQuiz_option());
                textView_lesson_description_.setText(quizlisting.get(2).getQuiz_option());
                textView_lesson_description1.setText(quizlisting.get(3).getQuiz_option());
            }
            //  Log.e("textname",""+quizlisting.get(0).getQuiz_option());

            final CheckBox check1 = (CheckBox) itemView.findViewById(R.id.check1);
            check1.setEnabled(false);
            final CheckBox check2 = (CheckBox) itemView.findViewById(R.id.check2);
            check2.setEnabled(false);
            final CheckBox check3 = (CheckBox) itemView.findViewById(R.id.check3);
            check3.setEnabled(false);
            final CheckBox check4 = (CheckBox) itemView.findViewById(R.id.check4);
            check4.setEnabled(false);

            for (int j = 0; j < mData_options.size(); j++) {
                Log.e("Visit", "visited");
                if (mQuizOpt.get(position).getQuestion_id().equals(mData_options.get(j).getQuizzes_id())) {
                    if (mData_options.get(0).getCorrect_answer().equals("0")) {

                        check1.setChecked(false);
                    } else if (mData_options.get(0).getCorrect_answer().equals("1")) {
                        check1.setChecked(true);
                    }
                    if (mData_options.get(1).getCorrect_answer().equals("0")) {
                        check2.setChecked(false);
                    } else if (mData_options.get(1).getCorrect_answer().equals("1")) {
                        check2.setChecked(true);
                    }
                    /*if (mData_options.get(2).getCorrect_answer().equals("0")) {
                        check3.setChecked(false);
                    } else if (mData_options.get(2).getCorrect_answer().equals("1")) {
                        check3.setChecked(true);
                    }
                    if (mData_options.get(3).getCorrect_answer().equals("0")) {
                        check4.setChecked(false);
                    } else if (mData_options.get(3).getCorrect_answer().equals("1")) {
                        check4.setChecked(true);
                        Log.e("check", "" + mData_options.get(3).getCorrect_answer().equals("1"));
                    }*/
                }

            }
           /* if(position==0)
            {
              //View_Quiz_Listing data=quizlisting.get(0);
               // Log.e("data",""+data.toString());
                for(int i=0;i<quizlisting.size();i++)
                {
                    Log.e("quizlistingsize",""+quizlisting.get(i).getCorrect_answer());
                 //   quizlisting

                   *//* textView_lesson_title.setText(quizlisting.get(0).getQuiz_option());
                    textView_lesson_description.setText(quizlisting.get(1).getQuiz_option());
                    textView_lesson_description_.setText(quizlisting.get(2).getQuiz_option());
                    textView_lesson_description1.setText(quizlisting.get(3).getQuiz_option());*//*
                }

            }
*/
            if (onitemid == 10) {
                Log.e("onitemidreceived", "" + onitemid);
                textView_lesson_title.setEnabled(false);
                textView_lesson_description.setEnabled(false);
                textView_lesson_description_.setEnabled(false);
                textView_lesson_description1.setEnabled(false);
                questionname.setEnabled(false);
                headerTitle.setText("View Quiz");
                pos = position + 1;
                textView.setText("Quiz" + " " + pos + " " + "of" + " " + mQuizOpt.size());


            }

            Log.e("Swipe", "asd");
            /*for (int k = 0; k < 4; k++) {
            //    Log.e("correct", "" + quizlisting.get(k).getCorrect_answer());
                if ((quizlisting.get(k).getCorrect_answer().equals("1") == true)) {
                    Log.e("posk", "" + k);
                    if (k == 0) {
                        check1.setChecked(true);
                    } else if (k == 1) {
                        check2.setChecked(true);
                    } else if (k == 2) {
                        check3.setChecked(true);
                    } else if (k == 3) {
                        check4.setChecked(true);
                    }
                }
                truevalue = String.valueOf(quizlisting.get(k).getCorrect_answer().equals("1"));
                truevalue1 = String.valueOf(quizlisting.get(position).getCorrect_answer().contains("1"));


                Log.e("checkedval", "" + truevalue);
                Log.e("checkedval1", "" + truevalue1);

                //  Log.e("truevalue",quizlisting.get(k).getCorrect_answer().c)
            }*/






         /*  if (position==4){
               imageViewRight.setVisibility(View.GONE);
           }*/
            Log.e("POsition", "" + position);
            if (position == 0) {
                imageViewLeft.setVisibility(View.GONE);
            }

            container.addView(itemView);
            if (idvalue == 30) {
                check1.setEnabled(true);
                check2.setEnabled(true);
                check3.setEnabled(true);
                check4.setEnabled(true);

                check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked == true) {
                            check2.setChecked(false);
                            check3.setChecked(false);
                            check4.setChecked(false);
                            correctans = String.valueOf(1);
                        }
                    }
                });

                check2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked == true) {
                            check1.setChecked(false);
                            check3.setChecked(false);
                            check4.setChecked(false);
                            correctans = String.valueOf(2);
                        }
                    }
                });

                check3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked == true) {
                            check1.setChecked(false);
                            check2.setChecked(false);
                            check4.setChecked(false);
                            correctans = String.valueOf(3);
                        }
                    }
                });


                check4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked == true) {
                            check1.setChecked(false);
                            check2.setChecked(false);
                            check3.setChecked(false);
                            correctans = String.valueOf(4);

                        }
                    }
                });
//                relquiz.setVisibility(View.GONE);
                textquiz.setVisibility(View.GONE);
                description.setVisibility(View.GONE);
                Log.e("idvalue", "" + idvalue);
                pos = position + 1;
                textView.setText("Quiz" + " " + pos + " " + "of" + " " + mQuizOpt.size());
                String getText = textView.getText().toString();
                Log.e("getText", "daa" + getText);
                if (pos == mQuizOpt.size()) {
                    Log.e("Visit", "here");
                    textView.setText("Save");
                }
                textView_lesson_title.setEnabled(true);
                textView_lesson_description.setEnabled(true);
                textView_lesson_description_.setEnabled(true);
                textView_lesson_description1.setEnabled(true);
                headerTitle.setText("Quiz Question");
                imageViewRight.setVisibility(View.GONE);
                pagerChildCount = _mViewPager.getChildCount();

                jobject = new JSONObject();
                firstarr = new JSONArray();
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int k;
                        for (k = 0; k < _mViewPager.getChildCount(); k++) {

                            View view = _mViewPager.getChildAt(k);
                            EditText et = (EditText) view.findViewById(R.id.questionname);
                            EditText textView_lesson_title = (EditText) view.findViewById(R.id.textView_lesson_title);
                            EditText textView_lesson_description = (EditText) view.findViewById(R.id.textView_lesson_description);
                            EditText textView_lesson_description_ = (EditText) view.findViewById(R.id.textView_lesson_description_);
                            EditText textView_lesson_description1 = (EditText) view.findViewById(R.id.textView_lesson_description1);
                            CheckBox check1 = (CheckBox) view.findViewById(R.id.check1);
                            CheckBox check2 = (CheckBox) view.findViewById(R.id.check2);
                            CheckBox check3 = (CheckBox) view.findViewById(R.id.check3);
                            CheckBox check4 = (CheckBox) view.findViewById(R.id.check4);


                            Log.e("MyViewData", "" + et.getText().toString());
                            Log.e("MyViewData1", "" + textView_lesson_description1.getText().toString());
                            String checked = null;
                            int flag = 0;
                            if (check1.isChecked()) {
                                checked = String.valueOf(1);

                            } else if (check2.isChecked()) {
                                checked = String.valueOf(2);
                            } else if (check3.isChecked()) {
                                checked = String.valueOf(3);
                            } else if (check4.isChecked()) {
                                checked = String.valueOf(4);
                            }

                            Log.e("checked", "" + mQuizOpt.get(k).getQuestion_name());

                            JSONArray arr = new JSONArray();

                            JSONObject start = new JSONObject();

                            ans1 = textView_lesson_title.getText().toString();
                            ans2 = textView_lesson_description.getText().toString();
                            ans3 = textView_lesson_description_.getText().toString();
                            ans4 = textView_lesson_description1.getText().toString();
                            Log.e("answer1size", "" + mData_options.size());
                            Log.e("Ans_Type", "" + ans1 + "--" + ans2 + "--" + ans3 + "--" + ans4);

                            for (int i = 0; i < mData_options.size(); i++) {
                                JSONObject object1 = new JSONObject();
                                question = questionname.getText().toString();
                                try {
                                    object1.put(("question_id"), mQuizOpt.get(k).getQuestion_id());
                                    object1.put(("question_name"), et.getText().toString());
                                    JSONObject jsn = new JSONObject();
                                    JSONArray jsnarr = new JSONArray();

                                    Log.e("answer1", "" + ans1);

                                    JSONObject optionobj = new JSONObject();
                                    optionobj.put(("option_id"), mQuizOpt.get(k).getmQuiz_option().getOption().get(i).getOption_id());// mData_options.get(i).getOption_id());
                                    Log.e("optionidsgone", "" + mData_options.get(i).getOption_id());
                                    optionobj.put(("quizzes_id"), mQuizOpt.get(k).getQuestion_id());
                                    if (i == 0)
                                        optionobj.put(("quiz_option"), ans1);
                                    if (i == 1)
                                        optionobj.put(("quiz_option"), ans2);
                                    if (i == 2)
                                        optionobj.put(("quiz_option"), ans3);
                                    if (i == 3)
                                        optionobj.put(("quiz_option"), ans4);


                                    if (check1.isChecked() && i == 0)
                                        optionobj.put(("correct_answer"), "1");
                                    else if (i == 0)
                                        optionobj.put(("correct_answer"), "0");
                                    if (check2.isChecked() && i == 1)
                                        optionobj.put(("correct_answer"), "1");
                                    else if (i == 1)
                                        optionobj.put(("correct_answer"), "0");
                                    if (check3.isChecked() && i == 2)

                                        optionobj.put(("correct_answer"), "1");
                                    else if (i == 2)
                                        optionobj.put(("correct_answer"), "0");
                                    if (check4.isChecked() && i == 3)
                                        optionobj.put(("correct_answer"), "1");
                                    else if (i == 3)
                                        optionobj.put(("correct_answer"), "0");

                                    Log.e("optionobj", "" + optionobj);
                                    jsnarr.put(optionobj);
                                    jsn.put("options", jsnarr);

                                    object1.put("quiz_options", jsn);
                                    Log.e("object1", "" + object1);
                                    firstarr.put(object1);
                                    jobject.put("quiz_question", firstarr);


                                    Log.e("object", "" + jobject);
                                    quizdata = jobject.toString();
                                    Log.e("quizdata", "" + quizdata);
                                    Quiz_Edit.question_name = questionname.getText().toString();
                                    Log.e("isdone", "" + Quiz_Edit.question_name);

                                    Quiz_Edit.editdataquiz = quizdata;


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                Log.e("object1", "" + object1);
                            }
                            //Quiz_Edit.dataquiz=quizdata;


                            Log.e("quizdata1", "" + jobject);
                            //  Log.e("quizdatainglob", "" + Quiz_Edit.dataquiz);

//                        if(currentCount < pagerChildCount){
//                            currentCount++;
                        }
                        getFragmentManager().popBackStack();
                    }

                });
            }
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // Remove viewpager_item.xml from ViewPager
            ((ViewPager) container).removeView((RelativeLayout) object);
        }
    }

    class Quiz_view extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return WSConnector.Quiz_view(params[0], params[1], params[2]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dlg.setMessage("Loading....");
            dlg.setCancelable(false);
            dlg.show();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dlg.dismiss();
            Log.e("Quiz_view", "" + result);
            if (result.contains("true")) {

                updateTeacherLogIn(result);


            } else if (result.contains("false")) {
                Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();

            }
        }


        private void updateTeacherLogIn(String success) {
            try {
                dataViewQuiz = new Data_View_Quiz();
                JSONObject jsonObject = new JSONObject(success);
                Log.e("jsonObject", "" + jsonObject);

                JSONObject data = jsonObject.optJSONObject("data");
                JSONObject quiz_info = data.getJSONObject("quiz_info");


                Data_Quiz_Info data_quiz_info = new Data_Quiz_Info();
                data_quiz_info.setQuizid(quiz_info.optString("quizid"));
                data_quiz_info.setAss_less_id(quiz_info.optString("ass_less_id"));
                data_quiz_info.setLesson_name(quiz_info.optString("lesson_name"));
                data_quiz_info.setApi_quizid(quiz_info.optString("api_quizid"));
                data_quiz_info.setQuiz_name(quiz_info.optString("quiz_name"));
                data_quiz_info.setQuiz_desc(quiz_info.optString("quiz_desc"));
                data_quiz_info.setUser_id(quiz_info.optString("user_id"));
                data_quiz_info.setQuiz_cid(quiz_info.optString("quiz_cid"));
                data_quiz_info.setLast_modified(quiz_info.optString("last_modified"));


                JSONArray quiz_question = data.getJSONArray("quiz_question");

                mQuizOpt = new ArrayList<Data_Quiz_Question>();

                for (int i = 0; i < quiz_question.length(); i++) {
                    JSONObject obj1 = quiz_question.getJSONObject(i);

                    Data_Quiz_Question dataQuizQuestion = new Data_Quiz_Question();
                    dataQuizQuestion.setQuestion_id(obj1.optString("question_id"));
                    dataQuizQuestion.setQuestion_name(obj1.optString("question_name"));

                    JSONObject quiz_options = obj1.getJSONObject("quiz_options");
                    Log.e("quiz_options", "" + quiz_options);

                    JSONArray options = quiz_options.getJSONArray("options");
                    Log.e("options", "" + options);

                    Data_Quiz_Option mOption = new Data_Quiz_Option();


                    ArrayList<Data_Option> mDataOpt = new ArrayList<Data_Option>();

                    for (int j = 0; j < options.length(); j++) {

                        JSONObject object = options.getJSONObject(j);

                        Data_Option dataOption = new Data_Option();
                        dataOption.setOption_id(object.optString("option_id"));
                        dataOption.setQuizzes_id(object.optString("quizzes_id"));
                        dataOption.setQuiz_option(object.optString("quiz_option"));
                        dataOption.setCorrect_answer(object.optString("correct_answer"));
                        mDataOpt.add(dataOption);
                    }
                    mOption.setOption(mDataOpt);
                    dataQuizQuestion.setmQuiz_option(mOption);
                    mQuizOpt.add(dataQuizQuestion);
                }
                Log.e("quiz_optiiiiiii", "" + mQuizOpt.size());
                dataViewQuiz.setmDataQuizInfo(data_quiz_info);
                dataViewQuiz.setmQuizQuestions(mQuizOpt);
                mPagerAdapter = new CustomPagerAdapter(getActivity(), dataViewQuiz);
                _mViewPager.setAdapter(mPagerAdapter);
                //   }


                //      mAdapter.setMode(Attributes.Mode.Single);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}


