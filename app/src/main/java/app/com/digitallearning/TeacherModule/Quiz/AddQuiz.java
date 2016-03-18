package app.com.digitallearning.TeacherModule.Quiz;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andexert.library.RippleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Model.Data;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 1/28/16.
 */
public class AddQuiz extends Fragment{
    View rootview;
    SharedPreferences preferences;
    String Sch_Mem_id, cla_classid;
    TextView headerTitle,lessonname,title;
    RippleView rippleViewPreview,ripple_lesson,ripple_quizquestion,ripple_quizdec,ripple_save;
    ArrayList<Data> dataList = new ArrayList<Data>();
    ProgressDialog dlg;
    String[] lesson;
    static String name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_add_quiz, container, false);
        rippleViewPreview=(RippleView)rootview.findViewById(R.id.ripple_preview) ;
        title=(TextView)rootview.findViewById(R.id.title);
        dlg=new ProgressDialog(getActivity());
        lessonname=(TextView)rootview.findViewById(R.id.lessonname);
        ripple_lesson=(RippleView)rootview.findViewById(R.id.ripple_lesson);
        ripple_quizquestion=(RippleView)rootview.findViewById(R.id.ripple_quizquestion);
        ripple_quizdec=(RippleView)rootview.findViewById(R.id.ripple_quizdec);
        ripple_save=(RippleView)rootview.findViewById(R.id.ripple_save);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Sch_Mem_id = preferences.getString("Sch_Mem_id", "");
        Log.e("Sch_Mem_id", "" + Sch_Mem_id);

        cla_classid = preferences.getString("cla_classid", "");
        Log.e("cla_classid", "" + cla_classid);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Add Quiz");



        ripple_lesson.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Make your selection");
                Log.e("sizearray",""+dataList.size());
                lesson=new String[dataList.size()];
                for (int i1=0;i1< dataList.size();i1++ ) {
                    lesson[i1] = dataList.get(i1).getLessonName();
                }
                builder.setItems(lesson, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Log.e("items[item]",""+lesson[item]);
                         name=lesson[item];
                        lessonname.setText(name);
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
                Quiz_Question_Fragment quiz_question_fragment = new Quiz_Question_Fragment();
                fragmentTransaction.replace(R.id.container, quiz_question_fragment).addToBackStack(null);
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
//classid , user_type , userid , schid , lessonid , quiz_ans_1 , quiz_ans_2 , quiz_ans_3 , quiz_ans_4 , correct_ans ,
// quiz_title , quiz_description , quiz_question , last_modified

    class Get_Lesson extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Get_Lesson(params[0], params[1]);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dlg = new ProgressDialog(getActivity());
            dlg.setMessage("Loading.....");
            dlg.setCancelable(false);
            dlg.show();


        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

//            if (dlg != null)
                dlg.dismiss();
            Log.e("GetLesson", "" + result);

            if (result.contains("false")) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("No data").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();


                            }
                        });

                AlertDialog dialog = alertDialog.create();
                dialog.show();
                TextView messageText = (TextView) dialog
                        .findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER);


            } else if (result.contains("true")) {
                updateGet_Lesson(result);


            }
        }

        private void updateGet_Lesson(String success) {

            try {

                JSONObject jsonObject = new JSONObject(success);

                JSONArray arr = jsonObject.optJSONArray("data");

                Log.e("arr", " " + arr);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Data data = new Data();
                    data.setLessonId(obj.optString("les_id"));
                    data.setLessonClassId(obj.optString("Les_Cls_Id"));
                    data.setLessonName(obj.getString("lesson_name"));
                    data.setLessonDate(obj.getString("les_date"));

                    data.setDescription(obj.getString("desc"));
                    data.setLessonImage(obj.getString("les_image"));
                    data.setImageThumbnail(obj.getString("img_thumb"));
                    data.setVideoUrl(obj.getString("video_url"));
                    data.setVideoThumbnail(obj.getString("video_thumb"));
                    dataList.add(data);
                }

                Log.e("sizeofarray",""+dataList.size());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        try{
            if (name!=null){
                lessonname.setText(name);
            }        }
        catch (Exception e){}
        new Get_Lesson().execute(cla_classid, Sch_Mem_id);

    }
}