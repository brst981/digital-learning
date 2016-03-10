package app.com.digitallearning.TeacherModule.Lessons;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Model.Data;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 2/3/16.
 */
public class Lesson_Edit extends Fragment {
    View rootview;
    TextView headerTitle, edt_title_lesson, txt_date_lesson, edt_videoUrl_lesson, edt_description_lesson;
    String textHeader, lessonId, userid;
    ProgressDialog dlg;
    SharedPreferences preferences;
    RippleView ripple_save_lesson;
    String Sch_Mem_id, cla_classid, title, videolink, currentdate, description;
    ArrayList<Data> dataList = new ArrayList<Data>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.lesson_edit, container, false);
        dlg = new ProgressDialog(getActivity());

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        //   getActivity().getActionBar().setHomeButtonEnabled(false);

        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        ripple_save_lesson = (RippleView) rootview.findViewById(R.id.ripple_save_lesson);
        txt_date_lesson = (TextView) rootview.findViewById(R.id.txt_date_lesson);
        edt_title_lesson = (TextView) rootview.findViewById(R.id.edt_title_lesson);
        edt_videoUrl_lesson = (TextView) rootview.findViewById(R.id.edt_videoUrl_lesson);
        txt_date_lesson = (TextView) rootview.findViewById(R.id.txt_date_lesson);
        edt_description_lesson = (TextView) rootview.findViewById(R.id.edt_description_lesson);
        lessonId = getArguments().getString("lessonId");
        headerTitle.setText("Modify Lesson");
        initData();
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        cla_classid = preferences.getString("cla_classid", "");
        Log.e("cla_classid", "" + cla_classid);
        userid = preferences.getString("Sch_Mem_id", "");
        Log.e("userid", "" + userid);

        /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        txtDate.setText(dateFormat.format(date));*/
        new Before_Lesson().execute(userid, lessonId);

        ripple_save_lesson.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                title = edt_title_lesson.getText().toString();
                videolink = edt_videoUrl_lesson.getText().toString();
                description = edt_description_lesson.getText().toString();
                currentdate = txt_date_lesson.getText().toString();
//userid  cid title desc  les_date les_id   video_url
                new Update_Lesson().execute(cla_classid, userid, title, description, currentdate, lessonId, videolink);
            }
        });
        return rootview;

    }

    private void initData() {
        textHeader = "sdhfygsjdgf";
    }


    class Before_Lesson extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Before_Lesson_Edit(params[0], params[1]);

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
            Log.e("REsulTinAddSchedule", "" + result);
            if (result.contains("true")) {

                updateTeacherLogIn(result);


            } else if (result.contains("false")) {
                Toast.makeText(getActivity(), "Wrong User", Toast.LENGTH_SHORT).show();

            }
        }


        private void updateTeacherLogIn(String success) {
            try {

                JSONObject jsonObject = new JSONObject(success);

                JSONArray arr = jsonObject.optJSONArray("data");
                Log.e("arr", " " + arr);


                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    String lesson_name = obj.getString("lesson_name");
                    Log.e("lesson_name", "" + lesson_name);
                    String les_date = obj.getString("les_date");
                    Log.e("les_date", "" + les_date);
                    String desc = obj.getString("desc");
                    Log.e("desc", "" + desc);
                    String video_url = obj.getString("video_url");
                    Log.e("video_url", "" + video_url);
                    String video_thumb = obj.getString("video_thumb");
                    Log.e("video_thumb", "" + video_thumb);

                    edt_title_lesson.setText(lesson_name);
                    txt_date_lesson.setText(les_date);
                    edt_videoUrl_lesson.setText(video_url);
                    edt_description_lesson.setText(desc);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


    class Update_Lesson extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Edit_Lesson(params[0], params[1], params[2], params[3], params[4], params[5], params[6]);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dlg.setMessage("Loading.....");
            dlg.setCancelable(false);
            dlg.show();


        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dlg.dismiss();
            Log.e("Get_LessonAPI", "" + result);

            if (result.contains("false")) {
                Toast.makeText(getActivity(), "Wrong user", Toast.LENGTH_SHORT).show();

            } else if (result.contains("true")) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Lesson updated").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                                getFragmentManager().popBackStack();
//                                FragmentManager fragmentManager = getFragmentManager();
//                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                                LessonFragment lessonFragment = new LessonFragment();
//                                fragmentTransaction.replace(android.R.id.content, lessonFragment).addToBackStack(null);
//                                fragmentTransaction.commit();
                               /* Intent gotoclass=new Intent(getActivity(), ClassActivity.class);
                                startActivity(gotoclass);
                                getActivity().finish();*/

                            }
                        });

                AlertDialog dialog = alertDialog.create();
                dialog.show();
                TextView messageText = (TextView) dialog
                        .findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER);


            }
        }
    }

}