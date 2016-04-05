package app.com.digitallearning.StudentModule.StudentQuiz;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.com.digitallearning.R;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 2/4/16.
 */
public class Result_Student_Quiz extends Fragment {
    View rootview;
    RelativeLayout rel;
    ImageButton back;
    TextView headerTitle,txtusername,userscore,userans,userpercantage,userresult,usertimetaken;
    String textHeader,username,scoring ,correct,min_score,result_grade,total_time,name,masterid,dashbordid;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    ProgressDialog dlg;
    SharedPreferences preferences;
    int checkId,resultid;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.result_quiz_view, container, false);
        txtusername=(TextView)rootview.findViewById(R.id.txtusername);
        userscore=(TextView)rootview.findViewById(R.id.userscore);
        userans=(TextView)rootview.findViewById(R.id.userans);
        userpercantage=(TextView)rootview.findViewById(R.id.userpercantage);
        userresult=(TextView)rootview.findViewById(R.id.userresult);
        usertimetaken=(TextView)rootview.findViewById(R.id.usertimetaken);
        back=(ImageButton)rootview.findViewById(R.id.back);
        rel=(RelativeLayout)rootview.findViewById(R.id.rel) ;
        dlg=new ProgressDialog(getActivity());
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        name=preferences.getString("name","");

        try {
            checkId = getArguments().getInt("checkId", 0);
            Log.e("checkId",""+checkId);
            resultid = getArguments().getInt("resultid", 0);
        }catch (Exception e){}

        if(checkId==10){
            masterid=getArguments().getString("masterid");
            dashbordid=getArguments().getString("dashbordid");
            Log.e("masterid",""+masterid);
            Log.e("dashbordid",""+dashbordid);
            new Student_result().execute(masterid,dashbordid,name);



        }
        if(resultid==12){
            username= getArguments().getString("username");
            scoring=getArguments().getString("scoring");
            correct=getArguments().getString("correct");
            min_score=getArguments().getString("min_score");
            result_grade=getArguments().getString("result_grade");
            total_time=getArguments().getString("total_time");
            txtusername.setText(username);
            userscore.setText(scoring);
            userans.setText(correct);
            userpercantage.setText(min_score +"%");
            userresult.setText(result_grade);
            usertimetaken.setText(total_time);
        }








        imageButtonZoomIn = (ImageButton)rootview. findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) rootview.findViewById(R.id.img_zoom_out);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });
        imageButtonZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoom(1.5f, 1.5f, new PointF(0, 0));
            }
        });

        imageButtonZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoom(1f, 1f, new PointF(0, 0));
            }
        });

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        //   getActivity().getActionBar().setHomeButtonEnabled(false);

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Result");
        initData();


        return rootview;

    }

    private void initData() {
        textHeader ="sdhfygsjdgf";

    }

    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        rel.setPivotX(pivot.x);
        rel.setPivotY(pivot.y);
        rel.setScaleX(scaleX);
        rel.setScaleY(scaleY);
    }

    class Student_result extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return WSConnector.student_result(params[0], params[1],params[2]);
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
            Log.e("Student_result", "" + result);
            if (result.contains("true")) {
                updateGet_Lesson(result);




            } else if (result.contains("false")) {
                Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();

            }
        }
//{"success":true,"data":[{"username":"Komal","scoring":"50 out of 100 ( 50 % )","correct":"1 out of 2","min_score":"60","result_grade":"FAIL","total_time":"01 SECS "}]}
// {"success":true,"data":[{"username":"stutech1","scoring":"100 out of 100 ( 100 % )","correct":"1 out of 1","min_score":"60","result_grade":"Pass","total_time":"03 secs "}]}
        private void updateGet_Lesson(String success) {

            try {

                JSONObject jsonObject = new JSONObject(success);

                JSONArray arr = jsonObject.optJSONArray("data");

                Log.e("arr", " " + arr);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    String username=obj.getString("username");
                    Log.e("username",""+username);

                    String scoring=obj.getString("scoring");
                    Log.e("scoring",""+scoring);

                    String correct=obj.getString("correct");
                    Log.e("correct",""+correct);

                    String min_score=obj.getString("min_score");
                    Log.e("min_score",""+min_score);

                    String result_grade=obj.getString("result_grade");
                    Log.e("result_grade",""+result_grade);

                    String total_time=obj.getString("total_time");
                    Log.e("total_time",""+total_time);



                    txtusername.setText(username);
                    userscore.setText(scoring);
                    userans.setText(correct);
                    userpercantage.setText(min_score +"%");
                    userresult.setText(result_grade);
                    usertimetaken.setText(total_time);


                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

}
