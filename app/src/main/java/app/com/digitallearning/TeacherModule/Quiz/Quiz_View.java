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
import app.com.digitallearning.TeacherModule.Model.View_Quiz_Listing;
import app.com.digitallearning.TeacherModule.Model.View_Quiz_Listing_Questions;
import app.com.digitallearning.TeacherModule.Model.View_Quiz_Name;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 2/3/16.
 */
public class Quiz_View extends Fragment {
    View rootview;
    TextView headerTitle;
    String textHeader,quizid , userid,cla_classid,truevalue,truevalue1,correctans,question_id, option_id ,quizzes_id;
    String question,ans1,ans2,ans3,ans4;
    SharedPreferences preferences;
    ViewPager _mViewPager;
    CustomPagerAdapter mPagerAdapter;
    ProgressDialog dlg;
    int onitemid,idvalue,viewpagerpos;
    RelativeLayout relquiz;
    ArrayList<View_Quiz_Listing> quizlisting = new ArrayList<View_Quiz_Listing>();
    ArrayList<View_Quiz_Listing_Questions> quizlisting1 = new ArrayList<View_Quiz_Listing_Questions>();
    ArrayList<View_Quiz_Name> quiznamelist = new ArrayList<View_Quiz_Name>();
    String quizdata;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.view_quiz, container, false);
        _mViewPager = (ViewPager)rootview. findViewById(R.id.viewPager);
        relquiz=(RelativeLayout)rootview.findViewById(R.id.relquiz);
        dlg=new ProgressDialog(getActivity());
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("");
        //   getActivity().getActionBar().setHomeButtonEnabled(false);

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        initData();
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userid = preferences.getString("Sch_Mem_id", "");
        cla_classid = preferences.getString("cla_classid", "");
    //    try {
            quizid=getArguments().getString("quizid");
        idvalue=getArguments().getInt("idvalue");
       // }catch (Exception e){}

        onitemid=getArguments().getInt("onitemid");

        question_id=getArguments().getString("question_id");
        option_id=getArguments().getString("option_id");
        quizzes_id=getArguments().getString("quizzes_id");


        new Quiz_view().execute(cla_classid,userid,quizid);
        mPagerAdapter = new CustomPagerAdapter(getActivity());
        _mViewPager.setAdapter(mPagerAdapter);

        _mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {}
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                viewpagerpos=position;
                Log.e("viewpagerposition",""+position);
            }

            public void onPageSelected(int position) {
                // Check if this is the page you want.
            }
        });
        return rootview;
    }

    private void initData() {
        textHeader ="sdhfygsjdgf";
    }

    class CustomPagerAdapter extends PagerAdapter {
        Context mContext;
        LayoutInflater mLayoutInflater;

        public CustomPagerAdapter(Context mContext) {
            this.mContext = mContext;
        }
        @Override
        public int getCount() {
            Log.e("quizlisting",""+quizlisting.size());
            Log.e("quizlisting1",""+quizlisting1.size());
            Log.e("quiznamelist",""+quiznamelist.size());
            return quizlisting1.size();
        }
        @Override
        public int getItemPosition(Object object) {
            Log.e("object",""+object);
            Log.e("POSITION_NONE",""+POSITION_NONE);
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
            TextView textquiz=(TextView)itemView.findViewById(R.id.textquiz);
        //    Log.e("nameofquiz",""+quiznamelist.get(position).getQuiz_name());

            textquiz.setText(quiznamelist.get(0).getQuiz_name());
            TextView description=(TextView)itemView.findViewById(R.id.description);
            description.setText(quiznamelist.get(0).getQuiz_description());
            TextView textView = (TextView)itemView.findViewById(R.id.textView_lesson_count);

            ImageView imageViewLeft =(ImageView)itemView.findViewById(R.id.img_left_icon);
            ImageView imageViewRight =(ImageView)itemView.findViewById(R.id.img_right_icon);
            final EditText questionname=(EditText) itemView.findViewById(R.id.questionname);
            questionname.setText(quizlisting1.get(position).getQuestion_nameString());

            Log.e("viewpagerpos",""+position);
               final EditText textView_lesson_title = (EditText) itemView.findViewById(R.id.textView_lesson_title);

                final EditText textView_lesson_description = (EditText) itemView.findViewById(R.id.textView_lesson_description);

                final EditText textView_lesson_description_ = (EditText) itemView.findViewById(R.id.textView_lesson_description_);

                final EditText textView_lesson_description1 = (EditText) itemView.findViewById(R.id.textView_lesson_description1);
            textView_lesson_title.setText(quizlisting.get(0).getQuiz_option());
            textView_lesson_description.setText(quizlisting.get(1).getQuiz_option());
            textView_lesson_description_.setText(quizlisting.get(2).getQuiz_option());
            textView_lesson_description1.setText(quizlisting.get(3).getQuiz_option());

          /*  for(int x=0;x<quizlisting.size();x++){
                Log.e("textname",""+quizlisting.get(x).getQuiz_option());
                textView_lesson_title.setText(quizlisting.get(0).getQuiz_option());
                textView_lesson_description.setText(quizlisting.get(1).getQuiz_option());
                textView_lesson_description_.setText(quizlisting.get(2).getQuiz_option());
                textView_lesson_description1.setText(quizlisting.get(3).getQuiz_option());
            }*/
          //  Log.e("textname",""+quizlisting.get(0).getQuiz_option());

            final CheckBox check1=(CheckBox)itemView.findViewById(R.id.check1);
            check1.setEnabled(false);
            final CheckBox check2=(CheckBox)itemView.findViewById(R.id.check2);
            check2.setEnabled(false);
            final CheckBox check3=(CheckBox)itemView.findViewById(R.id.check3);
            check3.setEnabled(false);
            final CheckBox check4=(CheckBox)itemView.findViewById(R.id.check4);
            check4.setEnabled(false);

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
            if(onitemid==10){
                Log.e("onitemidreceived",""+onitemid);
                textView_lesson_title.setEnabled(false);
                textView_lesson_description.setEnabled(false);
                textView_lesson_description_.setEnabled(false);
                textView_lesson_description1.setEnabled(false);
                questionname.setEnabled(false);
                headerTitle.setText("View Quiz");
                int pos=position+1;
                textView.setText("Quiz"+" "+ pos +" "+"of"+" "+quizlisting1.size());
            }

           Log.e("Swipe","asd");
            for(int k=0;k<4;k++){
                Log.e("correct",""+ quizlisting.get(k).getCorrect_answer());
                if((quizlisting.get(k).getCorrect_answer().equals("1")==true))
                {
                    Log.e("posk",""+k);
                    if(k==0){
                        check1.setChecked(true);
                    }
                    else if(k==1){
                        check2.setChecked(true);
                    }
                    else if(k==2){
                        check3.setChecked(true);
                    }
                    else if(k==3){
                        check4.setChecked(true);
                    }
                }
                 truevalue= String.valueOf(quizlisting.get(k).getCorrect_answer().equals("1"));
                truevalue1= String.valueOf(quizlisting.get(position).getCorrect_answer().contains("1"));


                Log.e("checkedval",""+truevalue);
                Log.e("checkedval1",""+truevalue1);

              //  Log.e("truevalue",quizlisting.get(k).getCorrect_answer().c)
            }






         /*  if (position==4){
               imageViewRight.setVisibility(View.GONE);
           }*/
            Log.e("POsition",""+position);
            if (position==0){
                imageViewLeft.setVisibility(View.GONE);
            }

            container.addView(itemView);
            if(idvalue==30){
                check1.setEnabled(true);
                check2.setEnabled(true);
                check3.setEnabled(true);
                check4.setEnabled(true);

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
//                relquiz.setVisibility(View.GONE);
                textquiz.setVisibility(View.GONE);
                description.setVisibility(View.GONE);
                Log.e("idvalue",""+idvalue);
                textView.setText("Save");
                textView_lesson_title.setEnabled(true);
                textView_lesson_description.setEnabled(true);
                textView_lesson_description_.setEnabled(true);
                textView_lesson_description1.setEnabled(true );
                headerTitle.setText("Quiz Question");
                imageViewRight.setVisibility(View.GONE);

                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String checked = null;
                        int flag=0;
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

                        Log.e("checked",""+checked);
                        JSONObject object=new JSONObject();
                        JSONArray arr=new JSONArray();

                           JSONObject start=new JSONObject();
                           JSONArray firstarr=new JSONArray();
                        for (int i=0;i<quizlisting.size();i++) {
                           JSONObject object1=new JSONObject();
                           question = questionname.getText().toString();
                           try {
                               object1.put(("question_id"), question_id);
                               object1.put(("question_name"), question);
                               JSONObject jsn = new JSONObject();
                               JSONArray jsnarr = new JSONArray();

                                for(int k=0;k<4;k++){
                                    ans1 = textView_lesson_title.getText().toString();
                                    ans2 = textView_lesson_description.getText().toString();
                                    ans3 = textView_lesson_description_.getText().toString();
                                    ans4 = textView_lesson_description1.getText().toString();

                           }
                               ans1 = textView_lesson_title.getText().toString();
                               ans2 = textView_lesson_description.getText().toString();
                               ans3 = textView_lesson_description_.getText().toString();
                               ans4 = textView_lesson_description1.getText().toString();
                               Log.e("answer1",""+ans1);

                                   JSONObject optionobj=new JSONObject();
                                   optionobj.put(("option_id"),quizlisting.get(i).getOption_id());
                                   optionobj.put(("quizzes_id"),quizzes_id);
                               if(i==0)
                                   optionobj.put(("quiz_option"),ans1);
                               if(i==1)
                                   optionobj.put(("quiz_option"),ans2);
                               if(i==2)
                                   optionobj.put(("quiz_option"),ans3);
                               if (i==3)
                                   optionobj.put(("quiz_option"),ans4);


                               if(check1.isChecked()&&i==0)

                                   optionobj.put(("correct_answer"),"1");
                               else if(i==0)
                                   optionobj.put(("correct_answer"),"0");
                               if(check2.isChecked()&&i==1)

                                   optionobj.put(("correct_answer"),"1");
                               else if(i==1)
                                   optionobj.put(("correct_answer"),"0");
                               if(check3.isChecked()&&i==2)

                                   optionobj.put(("correct_answer"),"1");
                               else if(i==2)
                                   optionobj.put(("correct_answer"),"0");
                               if(check4.isChecked()&&i==3)
                                   optionobj.put(("correct_answer"),"1");
                               else if(i==3)
                                   optionobj.put(("correct_answer"),"0");

                                   Log.e("optionobj", "" + optionobj);
                                   jsnarr.put(optionobj);
                                  jsn.put("options",jsnarr);

                                   object1.put("quiz_options",jsn);
                                   Log.e("object1",""+object1);
                                   firstarr.put(object1);
                                   object.put("quiz_question",firstarr);


                                   Log.e("object",""+object);
                               quizdata=object.toString();
                               Quiz_Edit.question_name=questionname.getText().toString();
                               Log.e("isdone",""+Quiz_Edit.question_name);

                               Quiz_Edit.editdataquiz=quizdata;


                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                           Log.e("object1", "" + object1);
                       }
                               //Quiz_Edit.dataquiz=quizdata;

                        getFragmentManager().popBackStack();
                        Log.e("quizdata", "" + quizdata);
                      //  Log.e("quizdatainglob", "" + Quiz_Edit.dataquiz);

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
            return WSConnector.Quiz_view(params[0], params[1],params[2]);
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
          quizlisting.clear();
            try {

                JSONObject jsonObject = new JSONObject(success);
                Log.e("jsonObject", "" + jsonObject);

                JSONObject data = jsonObject.getJSONObject("data");
                JSONObject quiz_info=data.getJSONObject("quiz_info");

                View_Quiz_Name view_quiz_name=new View_Quiz_Name();
                view_quiz_name.setQuiz_name(quiz_info.getString("quiz_name"));
                view_quiz_name.setQuiz_description(quiz_info.getString("quiz_desc"));
                view_quiz_name.setLesson_name(quiz_info.getString("lesson_name"));
                view_quiz_name.setAss_less_id(quiz_info.getString("ass_less_id"));
                quiznamelist.add(view_quiz_name);

                String quizid=quiz_info.getString("quizid");
            //    String ass_less_id=quiz_info.getString("ass_less_id");
           //     String lesson_name=quiz_info.getString("lesson_name");
                String api_quizid=quiz_info.getString("api_quizid");
              //  String quiz_name=quiz_info.getString("quiz_name");
             //   String quiz_desc=quiz_info.getString("quiz_desc");
                String user_id=quiz_info.getString("user_id");
                String quiz_cid=quiz_info.getString("quiz_cid");
                String last_modified=quiz_info.getString("last_modified");

                JSONArray quiz_question=data.getJSONArray("quiz_question");
                Log.e("quiz_question",""+quiz_question);

                for(int i=0;i<quiz_question.length();i++) {
                    JSONObject obj1 = quiz_question.getJSONObject(i);
                    View_Quiz_Listing_Questions view_quiz_listing_questions=new View_Quiz_Listing_Questions();
                    view_quiz_listing_questions.setQuestion_id(obj1.getString("question_id"));
                    view_quiz_listing_questions.setQuestion_nameString(obj1.getString("question_name"));
                    quizlisting1.add(view_quiz_listing_questions);

                    Log.e("quizlisting1",""+quizlisting1);
                   /* String question_id = obj1.getString("question_id");
                    Log.e("question_id", "" + question_id);
                    String question_name = obj1.getString("question_name");
                    Log.e("question_name", "" + question_name);*/

                    JSONObject quiz_options = obj1.getJSONObject("quiz_options");
                    Log.e("quiz_options", "" + quiz_options);

                    JSONArray options = quiz_options.getJSONArray("options");
                    Log.e("options", "" + options);
                    for(int j=0;j<options.length();j++){

                        JSONObject object=options.getJSONObject(j);
                        View_Quiz_Listing view_quiz_listing=new View_Quiz_Listing();
                        view_quiz_listing.setOption_id(object.optString("option_id"));
                        view_quiz_listing.setQuizzes_id(object.optString("quizzes_id"));
                        view_quiz_listing.setQuiz_option(object.optString("quiz_option"));
                        view_quiz_listing.setCorrect_answer(object.optString("correct_answer"));
                        quizlisting.add(view_quiz_listing);

                       /* String option_id=object.getString("option_id");
                        Log.e("option_id",""+option_id);
                        String quizzes_id=object.getString("quizzes_id");
                        Log.e("quizzes_id",""+quizzes_id);
                        String quiz_option=object.getString("quiz_option");
                        Log.e("quiz_option",""+quiz_option);
                        String correct_answer=object.getString("correct_answer");
                        Log.e("correct_answer",""+correct_answer);*/
                    }
                }
                    _mViewPager.setAdapter(mPagerAdapter);
           //   }


         //      mAdapter.setMode(Attributes.Mode.Single);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}


