package app.com.digitallearning.TeacherModule.Grade;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Model.Data_Grade;
import app.com.digitallearning.TeacherModule.Model.Quiz_Listing;

/**
 * Created by ${PSR} on 2/3/16.
 */
public class Grade_Details  extends Fragment {
    View rootview;
    LayoutInflater inflater;
    ListView list;
    TextView headerTitle,getgrade;
    EditText edittotal;
    String textHeader,studentid,cla_classid, userid,jsonResult,quizTotalScore;
    SharedPreferences preferences;
    ArrayList<String> categoryName;
    int selected_position;
    ProgressDialog dlg;
    ArrayList<Quiz_Listing> quizlisting = new ArrayList<Quiz_Listing>();
    ArrayList<Data_Grade> datagradelist;
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

        list=(ListView)rootview.findViewById(R.id.list);
        edittotal=(EditText)rootview.findViewById(R.id.edittotal);
        getgrade=(TextView)rootview.findViewById(R.id.getgrade);
        studentid=getArguments().getString("studentid");
        Log.e("studentidneed",""+studentid);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userid = preferences.getString("Sch_Mem_id", "");
        Log.e("userid", "" + userid);

        cla_classid = preferences.getString("cla_classid", "");
        Log.e("cla_classid", "" + cla_classid);
        datagradelist=new ArrayList<Data_Grade>();
        jsonResult=getArguments().getString("jsonResult");
        selected_position=Integer.parseInt(getArguments().getString("position"));
        Log.e("jsonres",""+jsonResult);
        Log.e("position",""+selected_position);
        parseJsonData(jsonResult, selected_position);


        edittotal.setInputType(InputType.TYPE_NULL);

        edittotal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edittotal.setInputType(InputType.TYPE_CLASS_TEXT);
                return false;
            }
        });




        return rootview;
    }
    private void initData() {
        textHeader ="Grade Details";


    }
    class careerAdapter extends BaseAdapter {
        Context context;
        ArrayList<Data_Grade> datagradelist;



        public careerAdapter(Context context, ArrayList<Data_Grade> datagradelist) {
          this.datagradelist = datagradelist;
            this.context = context;

        }


        @Override
        public int getCount() {

            return datagradelist.size();
        }

        @Override
        public Object getItem(int position) {
            return datagradelist.get(position);
        }


        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder view;  //=new ViewHolder();
           // if (inflater == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.listingdata, null);
                view = new ViewHolder(convertView);
                view.digit.setText(datagradelist.get(position).getQuiz_name());
                view.getpoint.setText(datagradelist.get(position).getGet_point());
                Log.e("SHow",""+datagradelist.get(position).getQuiz_name());
           // }


            /*if (convertView == null) {
                convertView = inflater.inflate(R.layout.listingdata, null);
                view = new ViewHolder(convertView);
                view.digit.setText(datagradelist.get(position).getQuiz_name());
                Log.e("SHow",""+datagradelist.get(position).getQuiz_name());


            }*/

            return convertView;
        }


    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView digit,getpoint;
        RadioButton radiobutton;
        RadioGroup mainradiobutton;

        public ViewHolder(View itemView) {
            super(itemView);
            digit = (TextView) itemView.findViewById(R.id.digit);
            getpoint=(TextView)itemView.findViewById(R.id.getpoint);

        }
    }



    public void parseJsonData(String jsonResult, int position){

        try {
            JSONObject jsonObject = new JSONObject(jsonResult);

            JSONObject jsonObject_1 = jsonObject.getJSONObject("data");
            Log.e("jsonObject_1",""+jsonObject_1);


            /**
             *  Lesson Id array ....
             */
            JSONArray lessonJsonArray = jsonObject_1.getJSONArray("lesson_info");
            Log.e("lessonJsonArray",""+lessonJsonArray);
            JSONObject getLessonJsonObject = lessonJsonArray.getJSONObject(position);
            Log.e("getLessonJsonObject",""+getLessonJsonObject);
            String lesson_id = getLessonJsonObject.getString("les_id");
            Log.e("lesson_id",""+lesson_id);
            String lesson_name = getLessonJsonObject.getString("lesson_name");
            Log.e("lesson_name",""+lesson_name);

            /**
             *  Total SCore array ....
             */
            JSONArray totalJsonArray = jsonObject_1.getJSONArray("quiz_total_score");
            Log.e("totalJsonArray",""+totalJsonArray);
            JSONObject totalJsonObject = totalJsonArray.getJSONObject(position);
            Log.e("totalJsonObject",""+totalJsonObject);
             quizTotalScore = totalJsonObject.getString("total");
            Log.e("quizTotalScore",""+quizTotalScore);
            edittotal.setText(quizTotalScore);

            /**
             * Quiz Info Array ...
             */
            JSONArray quizInfoJsonArray = jsonObject_1.getJSONArray("quiz_info");
            Log.e("quizInfoJsonArray",""+quizInfoJsonArray);
            for(int i=0; i<quizInfoJsonArray.length(); i++){
                JSONObject jsnObject = quizInfoJsonArray.getJSONObject(i);

                Data_Grade dataGrade=new Data_Grade();
                String less_id = jsnObject.getString("ass_less_id");
                Log.e("less_id",""+less_id);
                if(less_id.equals(lesson_id)){




                    String quizId = jsnObject.getString("quiz_id");
                    String quizName=jsnObject.getString("quiz_name");
                    String get_point=jsnObject.getString("get_point");
                    String total_point=jsnObject.getString("total_point");

                    dataGrade.setQuiz_id(quizId);
                    dataGrade.setQuiz_name(quizName);
                    dataGrade.setGet_point(get_point);
                    dataGrade.setTotal_point(total_point);
                    datagradelist.add(dataGrade);


                    Log.e("datagradelist",""+datagradelist);
                    Log.e("quizName",""+quizName);
                    Log.e("get_point",""+get_point);
                    Log.e("total_point",""+total_point);
                }


            }
            list.setAdapter(new careerAdapter(getActivity(), datagradelist));
            /**
             *  get Total Number ...
             */
            JSONObject jsObject = jsonObject_1.getJSONObject("Total_number");
            String total = jsObject.getString("Total");

            Log.e("jsObject",""+jsObject);
            Log.e("total",""+total);


            JSONArray gradeInfojsonArray=jsonObject_1.getJSONArray("grade_info");
            for(int j=0;j<gradeInfojsonArray.length();j++){
                JSONObject jsnObject1=gradeInfojsonArray.getJSONObject(j);
                String less_id = jsnObject1.getString("lesson_id");


                Log.e("jsnObject1",""+jsnObject1);
                Log.e("less_id",""+less_id);
               if(less_id.equals(lesson_id)) {

                    String grade = jsnObject1.getString("grade");
                    Log.e("grade",""+grade);

                   getgrade.setText(grade);
                }

            }


            JSONObject totalGrade = jsonObject_1.getJSONObject("total_grade");
            String forclass = totalGrade.getString("total_grade_for_class");





            Log.e("totalGrade",""+totalGrade);
            Log.e("forclass",""+forclass);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Exxxxxxxxxxxxxxxxxxxxxx",""+e);
        }
    }

}

