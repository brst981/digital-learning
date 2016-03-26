package app.com.digitallearning.TeacherModule.Grade;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Model.Quiz_Listing;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 2/3/16.
 */
public class Grade_Details  extends Fragment {
    View rootview;
    LayoutInflater inflater;
    ListView list;
    TextView headerTitle;
    EditText edittotal;
    String textHeader,studentid,cla_classid, userid,jsonResult;
    SharedPreferences preferences;
    ArrayList<String> categoryName;
    int position;
    ProgressDialog dlg;
    ArrayList<Quiz_Listing> quizlisting = new ArrayList<Quiz_Listing>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.grade_details, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        dlg=new ProgressDialog(getActivity());
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("");
        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        headerTitle.setText("Grade Details");
        initData();
        studentid=getArguments().getString("studentid");
        Log.e("studentidneed",""+studentid);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userid = preferences.getString("Sch_Mem_id", "");
        Log.e("userid", "" + userid);

        cla_classid = preferences.getString("cla_classid", "");
        Log.e("cla_classid", "" + cla_classid);

        jsonResult=getArguments().getString("jsonResult");
        position=Integer.parseInt(getArguments().getString("position"));
        Log.e("jsonres",""+jsonResult);
        Log.e("position",""+position);


        list=(ListView)rootview.findViewById(R.id.list);
        edittotal=(EditText)rootview.findViewById(R.id.edittotal);
        edittotal.setInputType(InputType.TYPE_NULL);

        edittotal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edittotal.setInputType(InputType.TYPE_CLASS_TEXT);
                return false;
            }
        });


        new Grade_lessondetail().execute(cla_classid,studentid);


        return rootview;
    }
    private void initData() {
        textHeader ="Grade Details";


    }
    class careerAdapter extends BaseAdapter {
        Context context;
        ArrayList<String> quizlisting;



        public careerAdapter(Context context, ArrayList<Quiz_Listing> quizlisting) {
          //  this.quizlisting = quizlisting;
            this.context = context;

        }


        @Override
        public int getCount() {

            return quizlisting.size();
        }

        @Override
        public Object getItem(int position) {
            return categoryName.get(position);
        }


        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder view;  //=new ViewHolder();
            if (inflater == null) {
                inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            }


            if (convertView == null) {
                convertView = inflater.inflate(R.layout.listingdata, null);
                view = new ViewHolder();
             //   view.category = (TextView) convertView.findViewById(R.id.category);



                /*view.mainradiobutton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int isChecked) {

                    }
                });*/


               // view.category.setText(categoryName.get(position));
               // Log.e("TEXT", "" + categoryName.get(position));

            /* inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             convertView=inflater.inflate(R.layout.newcategory,parent,false);
             view = new ViewHolder();
             view.category=(TextView)convertView.findViewById(R.id.category);
             view.check=(CheckBox)convertView.findViewById(R.id.check);
             view.category.setText(categoryName.get(position));
             Log.e("TEXT",""+categoryName.get(position));*/

            }
            return convertView;
        }


    }

    static class ViewHolder {
        TextView category;
        RadioButton radiobutton;
        RadioGroup mainradiobutton;

    }

    class Grade_lessondetail extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Grade_lesson(params[0], params[1]);

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
            Log.e("Grade_lessondetail", "" + result);
            if (result.contains("true")) {

                //updateTeacherLogIn(result);


            } else if (result.contains("false")) {
                Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();

            }
        }
//{"success":true,"user_type":"Teacher","Sch_Mem_id":"2155","Mem_Sch_Id":"487","Mem_Type":"1, 4","Mem_Name":"Ashish",
// "Mem_Emailid":"brstdev@gmail.com","class_data":[{"class_id":"183599","cls_createdby":"2155","cls_name":"1",
// "Cls_desc":"Test","subject":"Training ","cla_classid":"1800","students":"0","cls_image":"","orderid":"649",
// "new_orderid":"125"},

        private void updateTeacherLogIn(String success) {
            quizlisting.clear();
            try {

                JSONObject jsonObject = new JSONObject(success);
                Log.e("jsonObject", "" + jsonObject);




                JSONArray arr = jsonObject.getJSONArray("data");
                Log.e("arr", " " + arr);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Log.e("obj", "" + obj);

                    Quiz_Listing data = new Quiz_Listing();
                    data.setLast_modify(obj.optString("last_modified"));
                    data.setQuiz_id(obj.getString("quizid"));
                    data.setAss_less_id(obj.getString("ass_less_id"));
                    data.setQuiz_cid(obj.getString("quiz_cid"));
                    data.setApi_quiz_id(obj.getString("api_quizid"));
                    data.setQuiz_desc(obj.getString("quiz_desc"));
                    data.setQuiz_user_id(obj.getString("user_id"));
                    data.setQuiz_name(obj.getString("quiz_name"));
                    quizlisting.add(data);

                 list.setAdapter(new careerAdapter(getActivity(), quizlisting));


                }



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


}

