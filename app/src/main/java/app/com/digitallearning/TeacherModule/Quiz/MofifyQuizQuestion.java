package app.com.digitallearning.TeacherModule.Quiz;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by ${PSR} on 2/9/16.
 */
public class MofifyQuizQuestion extends Fragment {
    View rootview;
    TextView headerTitle,question;
    String textHeader ,quizid , userid,cla_classid;
    SharedPreferences preferences;;
    ProgressDialog dlg;
    ArrayList<View_Quiz_Listing> quizlisting = new ArrayList<View_Quiz_Listing>();
    ArrayList<View_Quiz_Listing_Questions> quizlisting1 = new ArrayList<View_Quiz_Listing_Questions>();
    ArrayList<View_Quiz_Name> quiznamelist = new ArrayList<View_Quiz_Name>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.modify_quiz_question, container, false);
        dlg=new ProgressDialog(getActivity());
        quizid=getArguments().getString("quizid");
        Log.e("QuizId",""+quizid);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userid = preferences.getString("Sch_Mem_id", "");
        cla_classid = preferences.getString("cla_classid", "");
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("");
        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        headerTitle.setText("Quiz Question");
        initData();
        question=(TextView)rootview.findViewById(R.id.question);
        return rootview;

    }

    private void initData() {
        textHeader ="sdhfygsjdgf";




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
            Log.e("ModifyQuiz", "" + result);
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


                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new Quiz_view().execute(cla_classid,userid,quizid);
    }
}
