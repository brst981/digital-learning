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
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 1/28/16.
 */
public class AddQuiz extends Fragment{
    View rootview;
    SharedPreferences preferences;
    String Sch_Mem_id, cla_classid,Mem_Sch_Id,arrlessonid,selectedlesonid,quiztitle,modifydate;
    TextView headerTitle,lessonname,edtquizdescription,quizquestionname;
    RippleView rippleViewPreview,ripple_lesson,ripple_quizquestion,ripple_quizdec,ripple_save;
    ArrayList<Data> dataList = new ArrayList<Data>();
    ProgressDialog dlg;
    String[] lesson;
    EditText title;
    public static String name,quizdescription,dataquiz,strquizquestionname;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_add_quiz, container, false);
        rippleViewPreview=(RippleView)rootview.findViewById(R.id.ripple_preview) ;
        title=(EditText)rootview.findViewById(R.id.title);
        edtquizdescription=(TextView) rootview.findViewById(R.id.quizdescription);
        dlg=new ProgressDialog(getActivity());
        lessonname=(TextView)rootview.findViewById(R.id.lessonname);
        quizquestionname=(TextView)rootview.findViewById(R.id.quizquestionname);
        ripple_lesson=(RippleView)rootview.findViewById(R.id.ripple_lesson);
        ripple_quizquestion=(RippleView)rootview.findViewById(R.id.ripple_quizquestion);
        ripple_quizdec=(RippleView)rootview.findViewById(R.id.ripple_quizdec);
        ripple_save=(RippleView)rootview.findViewById(R.id.ripple_save);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Sch_Mem_id = preferences.getString("Sch_Mem_id", "");
        Log.e("Sch_Mem_id", "" + Sch_Mem_id);
        cla_classid = preferences.getString("cla_classid", "");
        Log.e("cla_classid", "" + cla_classid);
        Mem_Sch_Id=preferences.getString("Mem_Sch_Id", "");
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Add Quiz");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        final Date date = new Date();
        modifydate=(dateFormat.format(date));
        Log.e("modifydate",""+modifydate);


        ripple_lesson.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Make your selection");
                Log.e("sizearray",""+dataList.size());
                lesson=new String[dataList.size()];
                for (int i1=0;i1< dataList.size();i1++ ) {
                    lesson[i1] = dataList.get(i1).getLessonName();
                    arrlessonid = dataList.get(i1).getLessonId();
                    Log.e("arrlessonid",""+arrlessonid);
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
//classid  user_type  userid  schid  lessonid  quiz_title  quiz_description  qzdata  last_modified


        ripple_save.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                quiztitle=title.getText().toString();
                Log.e("cla_classid",""+cla_classid);
                Log.e("Sch_Mem_id",""+Sch_Mem_id);
                Log.e("Mem_Sch_Id",""+Mem_Sch_Id);
                Log.e("selectedlesonid",""+selectedlesonid);
                Log.e("quiztitle",""+quiztitle);
                Log.e("quizdescription",""+quizdescription);
                Log.e("dataquiz",""+dataquiz);
                Log.e("modifydate",""+modifydate);
                new Add_Quiz().execute(cla_classid, Sch_Mem_id,Mem_Sch_Id,selectedlesonid,quiztitle,quizdescription,dataquiz,modifydate);
            }
        });
        return rootview;
    }


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
                    dataList.add(data);
                }

                Log.e("sizeofarray",""+dataList.size());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    class Add_Quiz extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Add_Quiz(params[0],params[1],params[2],params[3],params[4],params[5],params[6],params[7]);

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

            if (result.contains("true")) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Quiz inserted").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub

                                getFragmentManager().popBackStack();
                                dialog.dismiss();



                            }
                        });

                AlertDialog dialog = alertDialog.create();
                dialog.show();
                TextView messageText = (TextView) dialog
                        .findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER);


            } else if (result.contains("false")) {
                Toast.makeText(getActivity(),"Wrong user",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        try {
            edtquizdescription.setText(quizdescription);
            quizquestionname.setText(strquizquestionname);
        }
        catch (Exception e){}
        try{
            if (name!=null){
                lessonname.setText(name);
            }        }
        catch (Exception e){}
        new Get_Lesson().execute(cla_classid, Sch_Mem_id);

    }
}