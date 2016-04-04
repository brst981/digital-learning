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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Model.Data;
import app.com.digitallearning.TeacherModule.Model.View_Quiz_Listing;
import app.com.digitallearning.TeacherModule.Model.View_Quiz_Listing_Questions;
import app.com.digitallearning.TeacherModule.Model.View_Quiz_Name;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 2/3/16.
 */
public class Quiz_Edit extends Fragment {
    View rootview;
    TextView headerTitle,lessonname,quizquestionname,quizdecdata;
    String textHeader,quizid,selectedlesonid,finalselectedlessonid,Mem_Sch_Id,quiztitle,quizdescription,modifydate,quizlessonid,serquizdescripion;
    EditText title;
    RippleView rippleViewPreview,ripple_lesson,ripple_quizquestion,ripple_quizdec,rippleupdatequiz;
    ProgressDialog dlg;
    SharedPreferences preferences;
    String Sch_Mem_id, cla_classid,question_id, option_id,quizzes_id ,newquizdata,serquizdata,finalquizdata;
    ArrayList<Data> dataList = new ArrayList<Data>();
    String[] lesson;
    int pos;
    int idvalue=30;
    static String name;
    ArrayList<View_Quiz_Listing> quizlisting = new ArrayList<View_Quiz_Listing>();
    ArrayList<View_Quiz_Listing_Questions> quizlisting1 = new ArrayList<View_Quiz_Listing_Questions>();
    ArrayList<View_Quiz_Name> quiznamelist = new ArrayList<View_Quiz_Name>();
    public static String editdataquiz,updatedescription,question_name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.quiz_edit, container, false);
        ripple_lesson=(RippleView)rootview.findViewById(R.id.ripple_lesson);
        ripple_quizquestion=(RippleView)rootview.findViewById(R.id.ripple_quizquestion);
        ripple_quizdec=(RippleView)rootview.findViewById(R.id.ripple_quizdec);
        title=(EditText)rootview.findViewById(R.id.title);
        lessonname=(TextView)rootview.findViewById(R.id.lessonname) ;
        quizquestionname=(TextView)rootview.findViewById(R.id.quizquestionname);
        quizdecdata=(TextView)rootview.findViewById(R.id.quizdecdata);
        rippleupdatequiz=(RippleView)rootview.findViewById(R.id.rippleupdatequiz);
        dlg=new ProgressDialog(getActivity());
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("");
        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        headerTitle.setText("Modify Quiz");
        initData();
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Sch_Mem_id = preferences.getString("Sch_Mem_id", "");
        Mem_Sch_Id= preferences.getString("Mem_Sch_Id", "");
        cla_classid = preferences.getString("cla_classid", "");
        ripple_lesson.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Make your selection");
                Log.e("sizearray",""+dataList.size());
                lesson=new String[dataList.size()];
                for (int i1=0;i1< dataList.size();i1++ ) {
                    lesson[i1] = dataList.get(i1).getLessonName();
                    // arrlessonid = dataList.get(i1).getLessonId();
                    //   Log.e("arrlessonid",""+arrlessonid);
                    Log.e("selectedlesson",""+dataList.get(i1).getLessonId());
                }
                builder.setItems(lesson, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Log.e("items[item]",""+dataList.get(item).getLessonId());
                        //  Log.e("selectedlessonid",""+arrlessonid[item]);
                        name=lesson[item];
                        lessonname.setText(name);
                        selectedlesonid=dataList.get(item).getLessonId();
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
                Bundle bundle=new Bundle();
                bundle.putString("quizid",quizid);
                bundle.putInt("idvalue",idvalue);
                bundle.putString("question_id",question_id);
                bundle.putString("option_id",option_id);
                bundle.putString("quizzes_id",quizzes_id);
                Log.e("question_id",""+question_id);
                Quiz_View quiz_view = new Quiz_View();
                fragmentTransaction.replace(R.id.container, quiz_view).addToBackStack(null);
                quiz_view.setArguments(bundle);
                fragmentTransaction.commit();

            }
        });

        ripple_quizdec.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Quiz_Des quiz_des = new Quiz_Des();
                Bundle bundle=new Bundle();
                bundle.putString("descriptn",quizdecdata.getText().toString());
                fragmentTransaction.replace(R.id.container, quiz_des).addToBackStack(null);
                quiz_des.setArguments(bundle);
                fragmentTransaction.commit();

            }
        });
        rippleupdatequiz.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                quiztitle=title.getText().toString();
                updatedescription =quizdecdata.getText().toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
              /* // newquizdata=editdataquiz;
                Log.e("shouldnull",""+newquizdata);*/
                final Date date = new Date();
                modifydate=(dateFormat.format(date));
                Log.e("modifydate",""+modifydate);

                if(selectedlesonid==null){
                    finalselectedlessonid=quizlessonid;
                    Log.e("servicelessonid",""+finalselectedlessonid);
                }
                else if(selectedlesonid!=null){
                    finalselectedlessonid=selectedlesonid;
                    Log.e("selectedlesonidfinal",""+finalselectedlessonid);
                }
                if(newquizdata==null){
                    finalquizdata=serquizdata;
                    Log.e("serfinalquizdata",""+finalquizdata);

                }
                else  if(newquizdata.equals(" ")){
                    finalquizdata = serquizdata;
                    Log.e("workquizdata", "errr" + finalquizdata);
                }
                else if(newquizdata!=null){
                    try{
                        newquizdata= editdataquiz.replace("\"","\'");}
                    catch (Exception e){}

                    Log.e("DATA",""+editdataquiz);

                    Log.e("newquizdata",""+newquizdata);

                    finalquizdata=newquizdata;
                    Log.e("finalquizdata",""+finalquizdata);
                }



                new Quiz_Update().execute(cla_classid, Sch_Mem_id,Mem_Sch_Id,finalselectedlessonid,quiztitle,updatedescription,finalquizdata,modifydate,quizid);
            }
        });
        return rootview;

    }

    private void initData() {
        textHeader ="sdhfygsjdgf";
    }
    class Get_Lesson extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Get_Lesson(params[0], params[1]);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           /* dlg = new ProgressDialog(getActivity());
            dlg.setMessage("Loading.....");
            dlg.setCancelable(false);
            dlg.show();*/


        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

//            if (dlg != null)
            //  dlg.dismiss();
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
            dataList.clear();
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

                    quizlessonid=obj.getString("les_id");

                    dataList.add(data);
                }

                Log.e("sizeofarray",""+dataList.size());

            } catch (JSONException e) {
                e.printStackTrace();
            }
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

                serquizdescripion=quiz_info.optString("quiz_desc");
                // for(int q=0;q<quiznamelist.size();q++){


                Log.e("wrong",""+quiznamelist.get(pos).getQuiz_name());
                title.setText(quiznamelist.get(pos).getQuiz_name());

                if(name==null) {
                    lessonname.setText(quiznamelist.get(pos).getLesson_name());
                }
                else if(name!=null){
                    lessonname.setText(name);
                }
//quiztitle,updatedescription,finalquizdata,

                if(updatedescription==null||updatedescription==" ") {
                    quizdecdata.setText(quiznamelist.get(pos).getQuiz_description());
                }
                else if(updatedescription!=null){
                    quizdecdata.setText(updatedescription);
                }
                //  }



                String quizid=quiz_info.getString("quizid");
                //   String ass_less_id=quiz_info.getString("ass_less_id");
                // String lesson_name=quiz_info.getString("lesson_name");
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

                    if(question_name==null) {
                        quizquestionname.setText(quizlisting1.get(pos).getQuestion_nameString());

                    }
                    else if(question_name.equals(" ")){
                        quizquestionname.setText(quizlisting1.get(pos).getQuestion_nameString());
                    }

                    else if(question_name!=null){
                        Log.e("qusenamenotnull",""+question_name);
                        quizquestionname.setText(question_name);
                    }
                    Log.e("quizlisting1",""+quizlisting1);
                    question_id = obj1.getString("question_id");
                    Log.e("question_id", "" + question_id);
                    String question_name = obj1.getString("question_name");


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

                        option_id =object.getString("option_id");
                        quizzes_id=object.getString("quizzes_id");




                    }
                    JSONObject jsonObject1=new JSONObject();
                    serquizdata= String.valueOf(jsonObject1.put("quiz_question",quiz_question));
                    Log.e("serquizdata",""+serquizdata);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }}

    class Quiz_Update extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return WSConnector.Update_Quiz(params[0], params[1], params[2],params[3],params[4],params[5],params[6],params[7],params[8]);
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
            Log.e("ResultUpdateQuiz",""+result);




            /*name=" ";
            question_name=" ";
            updatedescription=" ";*/


            if (result.contains("true")) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Quiz updated").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                                quiztitle=" ";
                                updatedescription=" ";
                                finalquizdata=" ";
                                editdataquiz=" ";
                                newquizdata=" ";
                                question_name=" ";
                                Log.e("cleareditdataquiz",""+editdataquiz);
                                Log.e("cleareditdataquiznew",""+newquizdata);
                                getFragmentManager().popBackStack();


                            }
                        });

                AlertDialog dialog = alertDialog.create();
                dialog.show();
                TextView messageText = (TextView) dialog
                        .findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER);



            } else if (result.contains("false")) {
                Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();

            }
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        quizid=getArguments().getString("quizid");
        //    pos=getArguments().getInt("pos");
        Log.e("quizedit",""+quizid);
        //  Log.e("pos",""+pos);
        new Get_Lesson().execute(cla_classid, Sch_Mem_id);
        new Quiz_view().execute(cla_classid,Sch_Mem_id,quizid);
    }


}
