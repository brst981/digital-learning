package app.com.digitallearning.StudentModule.StudentQuiz;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import app.com.digitallearning.R;
import app.com.digitallearning.StudentModule.Model.Student_Quiz_Info;
import app.com.digitallearning.StudentModule.Model.Student_Quiz_Option;
import app.com.digitallearning.StudentModule.Model.Student_Quiz_View_Data;
import app.com.digitallearning.Utill.NonSwipeableViewPager;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 2/4/16.
 */
public class Student_Quiz_View extends Fragment {
    View rootview;
    TextView headerTitle;
    String textHeader, quiz_id, cls_clsid;
    NonSwipeableViewPager _mViewPager;
    CustomPagerAdapter mPagerAdapter;
    SharedPreferences preferences;
    ProgressDialog dlg;
    private Student_Quiz_View_Data dataViewQuiz;


    private long startTime = 0L;
    private Handler customHandler = new Handler() {
        /**
         * Closes this handler. A flush operation will be performed and all the
         * associated resources will be freed. Client applications should not use
         * this handler after closing it.
         */
        @Override
        public void close() {

        }

        /**
         * Flushes any buffered output.
         */
        @Override
        public void flush() {

        }

        /**
         * Accepts a logging request and sends it to the the target.
         *
         * @param record the log record to be logged; {@code null} records are ignored.
         */
        @Override
        public void publish(LogRecord record) {

        }
    };
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.view_quiz_student, container, false);
        _mViewPager = (NonSwipeableViewPager) rootview.findViewById(R.id.viewPager);

        _mViewPager.setAdapter(mPagerAdapter);
        dlg = new ProgressDialog(getActivity());
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("");
        //   getActivity().getActionBar().setHomeButtonEnabled(false);
        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        headerTitle.setText("View Quiz");
        quiz_id = getArguments().getString("quiz_id");
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        cls_clsid = preferences.getString("cls_clsid", "");





      /*_mViewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;

            }
        });*/

        new Quiz_view().execute(cls_clsid, quiz_id);
        return rootview;

    }





    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;
        Student_Quiz_View_Data mDataViewQuiz;
        ArrayList<Student_Quiz_Info> mQuizOpt;


        public CustomPagerAdapter(Context mContext, Student_Quiz_View_Data mDataViewQuiz) {
            this.mContext = mContext;
            this.mDataViewQuiz = mDataViewQuiz;
            mQuizOpt = mDataViewQuiz.getQuiz_info();
        }

        @Override
        public int getCount() {
            return mQuizOpt.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            // Declare Variables


            mLayoutInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = mLayoutInflater.inflate(R.layout.list_quiz_view_student, container,
                    false);

            TextView textquiz=(TextView)itemView.findViewById(R.id.textquiz);
            textquiz.setText(mQuizOpt.get(position).getQuiz_name());
            TextView textView = (TextView) itemView.findViewById(R.id.txt);
            Log.e("questionname",""+mQuizOpt.get(position).getQuiz_question());
            textView.setText(mQuizOpt.get(position).getQuiz_question());
           TextView txtdes=(TextView)itemView.findViewById(R.id.txtdes);
            txtdes.setText(mQuizOpt.get(position).getQuiz_desc());
            TextView option1=(TextView)itemView.findViewById(R.id.option1);
            option1.setText(mQuizOpt.get(position).getStudent_quiz_options().get(0).getQuiz_option());
            TextView option2=(TextView)itemView.findViewById(R.id.option2);
            option2.setText(mQuizOpt.get(position).getStudent_quiz_options().get(1).getQuiz_option());
            TextView option3=(TextView)itemView.findViewById(R.id.option3);
            option3.setText(mQuizOpt.get(position).getStudent_quiz_options().get(2).getQuiz_option());

           final  CheckBox check1 = (CheckBox) itemView.findViewById(R.id.check1);
            final   CheckBox check2 = (CheckBox) itemView.findViewById(R.id.check2);
            final CheckBox check3 = (CheckBox) itemView.findViewById(R.id.check3);
            final CheckBox check4 = (CheckBox) itemView.findViewById(R.id.check4);



            TextView option4=(TextView)itemView.findViewById(R.id.option4);
            option4.setText(mQuizOpt.get(position).getStudent_quiz_options().get(3).getQuiz_option());

             TextView timer=(TextView)itemView.findViewById(R.id.timer);

            check1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    startTime = SystemClock.uptimeMillis();
                 //  customHandler.postDelayed(updateTimerThread, 0);


                    if (isChecked == true) {
                        check2.setChecked(false);
                        check3.setChecked(false);
                        check4.setChecked(false);
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

                    }
                }
            });


            Button next=(Button)itemView.findViewById(R.id.button_create);


            next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  _mViewPager.setCurrentItem(_mViewPager.getCurrentItem()+1);
                    Toast.makeText(getActivity(),"Next",Toast.LENGTH_SHORT).show();
                }
            });




        //    int pos = position + 1;


           // textView.setText("Lesson" + " " + pos + " " + "of" + " " + "5");


            container.addView(itemView);

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
            return WSConnector.Student_Quiz_view(params[0], params[1]);
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
            Log.e("StudentQuizview", "" + result);
            if (result.contains("true")) {

                updateTeacherLogIn(result);


            } else if (result.contains("false")) {
                Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();

            }
        }


        private void updateTeacherLogIn(String success) {
            try {
                ArrayList<Student_Quiz_Info> mStuList = new ArrayList<Student_Quiz_Info>();
                dataViewQuiz = new Student_Quiz_View_Data();
                JSONObject jsonObject = new JSONObject(success);
                Log.e("jsonObject", "" + jsonObject);

                JSONObject data = jsonObject.optJSONObject("data");
                Log.e("data", "" + data);
                JSONArray quizInfoArray = data.getJSONArray("quiz_info");
                Log.e("quizInfoArray", "" + quizInfoArray);
                //JSONObject quiz_info = data.getJSONObject("quiz_info");
                for (int j = 0; j < quizInfoArray.length(); j++) {
                    JSONObject objquiz = quizInfoArray.getJSONObject(j);
                    Log.e("objquiz", "" + objquiz);

                    Student_Quiz_Info data_quiz_info = new Student_Quiz_Info();
                    data_quiz_info.setMaster_id(objquiz.optString("master_id"));
                    data_quiz_info.setQuiz_name(objquiz.optString("quiz_name"));
                    data_quiz_info.setQuiz_desc(objquiz.optString("quiz_desc"));
                    data_quiz_info.setQuizzes_id(objquiz.optString("quizzes_id"));
                    data_quiz_info.setQuiz_question(objquiz.optString("quiz_question"));

                    Log.e("data_quiz_info", "" + data_quiz_info);


                    JSONArray quiz_question = objquiz.getJSONArray("quiz_option_data");
                    Log.e("quiz_question", "" + quiz_question);
                    ArrayList<Student_Quiz_Option> mQuizOpt = new ArrayList<Student_Quiz_Option>();

                    for (int i = 0; i < quiz_question.length(); i++) {
                        JSONObject obj1 = quiz_question.getJSONObject(i);

                        Student_Quiz_Option dataQuizQuestion = new Student_Quiz_Option();
                        dataQuizQuestion.setAns_id(obj1.optString("ans_id"));
                        dataQuizQuestion.setElement_id(obj1.optString("element_id"));
                        dataQuizQuestion.setQuizzes_id(obj1.optString("quizzes_id"));
                        dataQuizQuestion.setQuiz_option(obj1.optString("quiz_option"));
                        dataQuizQuestion.setCorrect_answer(obj1.optString("correct_answer"));


                        Log.e("dataQuizQuestion", "" + dataQuizQuestion);
                        mQuizOpt.add(dataQuizQuestion);
                        Log.e("mQuizOpt", "" + mQuizOpt);

                    }
                    data_quiz_info.setStudent_quiz_options(mQuizOpt);
                    mStuList.add(data_quiz_info);

                }
                dataViewQuiz.setQuiz_info(mStuList);

                // dataViewQuiz.setQuiz_info(data_quiz_info);

                //  dataViewQuiz.setmDataQuizInfo(data_quiz_info);
               /* dataViewQuiz.setmQuizQuestions(mQuizOpt);
               // mPagerAdapter = new CustomPagerAdapter(getActivity(), dataViewQuiz);
                _mViewPager.setAdapter(mPagerAdapter);*/
                //   }


                //      mAdapter.setMode(Attributes.Mode.Single);

            //    mPagerAdapter = new CustomPagerAdapter(getActivity(), dataViewQuiz);
                mPagerAdapter = new CustomPagerAdapter(getActivity(), dataViewQuiz);
                _mViewPager.setAdapter(mPagerAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }




    private  Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);

            int mins = secs / 60;
            secs = secs % 60;

            int milliseconds = (int) (updatedTime % 1000);

            //timer.setText("" + mins + ":"

             //       + String.format("%02d", secs) + ":"

             //      + String.format("%03d", milliseconds));

        //    customHandler.postDelayed(this, 0);

        }

    };



}


