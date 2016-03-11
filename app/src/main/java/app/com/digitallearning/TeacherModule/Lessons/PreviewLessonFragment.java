package app.com.digitallearning.TeacherModule.Lessons;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.com.digitallearning.R;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${ShalviSharma} on 12/29/15.
 */
public class PreviewLessonFragment extends Fragment {
    View rootview;
    String textHeader, lessonId, userid,video_url;
    ProgressDialog dlg;
    SharedPreferences preferences;
    ImageView thumbnail;
    TextView desciption, headerTitle;;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_preview_lesson, container, false);
        dlg=new ProgressDialog(getActivity());
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        //headerTitle.setText("Choose Lesson");


        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        thumbnail = (ImageView) rootview.findViewById(R.id.thumbnail);

        desciption = (TextView) rootview.findViewById(R.id.desciption);
        userid = preferences.getString("Sch_Mem_id", "");
        Log.e("userid", "" + userid);
        lessonId = getArguments().getString("lessonId");
        new Before_Lesson().execute(userid, lessonId);

        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(video_url));
                startActivity(browserIntent);
            }
        });
        return rootview;
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
                     video_url = obj.getString("video_url");
                    Log.e("video_url", "" + video_url);
                    String video_thumb = obj.getString("video_thumb");
                    Log.e("video_thumb", "" + video_thumb);

                    Picasso.with(getActivity()).load(video_thumb).into(thumbnail);
                    desciption.setText(desc);
                    headerTitle.setText(lesson_name);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
