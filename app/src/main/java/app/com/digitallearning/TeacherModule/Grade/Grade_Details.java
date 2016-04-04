package app.com.digitallearning.TeacherModule.Grade;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.util.Log;
import android.view.Gravity;
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

import com.andexert.library.RippleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Model.Data_Grade;
import app.com.digitallearning.TeacherModule.Model.Quiz_Listing;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 2/3/16.
 */
public class Grade_Details  extends Fragment {
    View rootview;
    LayoutInflater inflater;
    ListView list;
    TextView headerTitle,getgrade;
    EditText edittotal,txtgivengrade;
    String textHeader,studentid,cla_classid, userid,jsonResult,quizTotalScore,srlessonid,srgrade,srgivengrade,totalquizid,totalgetpoints,srtotal,total_grade_for_class;
    SharedPreferences preferences;
    ArrayList<String> categoryName;
    int selected_position;
    RippleView ripple_edit_update;
    ProgressDialog dlg;
    ArrayList<Quiz_Listing> quizlisting = new ArrayList<Quiz_Listing>();
    ArrayList<Data_Grade> datagradelist;
    ArrayList<String> quizids,getquizpoints;
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

        quizids=new ArrayList<>();
        getquizpoints=new ArrayList<>();
        list=(ListView)rootview.findViewById(R.id.list);
        ripple_edit_update=(RippleView)rootview.findViewById(R.id.ripple_edit_update);
        edittotal=(EditText)rootview.findViewById(R.id.edittotal);
        getgrade=(TextView)rootview.findViewById(R.id.getgrade);
        txtgivengrade=(EditText)rootview.findViewById(R.id.txtgivengrade);
        studentid=getArguments().getString("studentid");
        /*try{
        total_grade_for_class=getArguments().getString(total_grade_for_class);
        edittotal.setText(total_grade_for_class);}
        catch (Exception e){}*/
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

////cid  stud_id  master_id hiddenquizscore gotit_point  total_point  gradeadd  totgrade  lesson_id
        edittotal.setInputType(InputType.TYPE_NULL);

        edittotal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edittotal.setInputType(InputType.TYPE_CLASS_TEXT);
                return false;
            }
        });

        ripple_edit_update.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

                srgrade=getgrade.getText().toString();
                srgivengrade=txtgivengrade.getText().toString();
                srtotal=edittotal.getText().toString();
                Log.e("studentid",""+studentid);
                Log.e("cla_classid",""+cla_classid);
                Log.e("totalquizid",""+totalquizid);
                Log.e("totalgetpoints",""+totalgetpoints);
                Log.e("srlessonid",""+srlessonid);
                Log.e("srgrade",""+srgrade);
                Log.e("srgivengrade",""+srgivengrade);
                Log.e("srtotal",""+srtotal);


                new Teacher_SaveGrade().execute(cla_classid,studentid,totalquizid,totalgetpoints, srgrade,srgivengrade,srlessonid,srtotal);
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

        quizids.clear();
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

            String str="";
            String str1="";
            for(int i=0; i<quizInfoJsonArray.length(); i++){
                JSONObject jsnObject = quizInfoJsonArray.getJSONObject(i);



                Data_Grade dataGrade=new Data_Grade();
                String less_id = jsnObject.getString("ass_less_id");
                Log.e("less_id",""+less_id);
                if(less_id.equals(lesson_id)){

//cid  stud_id  master_id hiddenquizscore gotit_point  total_point  gradeadd  totgrade  lesson_id



                    String quizId = jsnObject.getString("quiz_id");
                    String quizName=jsnObject.getString("quiz_name");
                    String get_point=jsnObject.getString("get_point");
                    String total_point=jsnObject.getString("total_point");

                    dataGrade.setQuiz_id(quizId);
                    dataGrade.setQuiz_name(quizName);
                    dataGrade.setGet_point(get_point);
                    dataGrade.setTotal_point(total_point);
                    datagradelist.add(dataGrade);

                    if(str.equals("")){
                        str=str+dataGrade.getQuiz_id();
                    }
                    else{
                        str=str+","+dataGrade.getQuiz_id();
                    }


                    if ((str1.equals(""))){
                        str1=str1+dataGrade.getGet_point();
                    }
                    else{
                        str1=str1+","+dataGrade.getGet_point();
                    }


                    Log.e("datagradelistsi",""+datagradelist.size());
//                    quizids.add(datagradelist.get(position).getQuiz_id());
                    Log.e("QuizIds",""+quizids);


                 //   getquizpoints.add(datagradelist.get(position).getGet_point());

                 //   Log.e("getquizpoints",""+getquizpoints);


                    /*strquizids = "";

                    for (String s : quizids)
                    {
                        strquizids += s + "\t";
                    }
                Log.e("strquizids",""+strquizids);



                    strtotalpoints = "";

                    for (String s : getquizpoints)
                    {
                        strtotalpoints += s + "\t";
                    }

                    Log.e("srlessonid",""+srlessonid);
                    Log.e("strtotalpoints",""+strtotalpoints);

*/
                    srlessonid=less_id;

                    Log.e("datagradelist",""+datagradelist);
                    Log.e("quizName",""+quizName);
                    Log.e("get_point",""+get_point);
                    Log.e("total_point",""+total_point);
                }


            }

            totalquizid=str;
            totalgetpoints=str1;
            Log.e("strquizids",""+str);
            Log.e("strgetpoints",""+str1);
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
            if(forclass=="null"){
                Log.e("forclassnull",""+forclass);
                txtgivengrade.setText(" ");
            }
            else{
                txtgivengrade.setText(forclass);
            }
            Log.e("totalGrade",""+totalGrade);
            Log.e("forclass",""+forclass);

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("Exxxxxxxxxxxxxxxxxxxxxx",""+e);
        }
    }




    class Teacher_SaveGrade extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Teacher_SaveGrade(params[0],params[1],params[2],params[3],params[4],params[5],params[6],params[7]);

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

//           if (dlg != null)
            dlg.dismiss();
            Log.e("StudentGetLesson", "" + result);

            if (result.contains("false")) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("No quiz attempted by Student").setCancelable(false)
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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Inserted").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                getFragmentManager().popBackStack();


                            }
                        });

                AlertDialog dialog = alertDialog.create();
                dialog.show();
                TextView messageText = (TextView) dialog
                        .findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER);



            }
        }

        /*private void updateGet_Lesson(String success) {
            dataList.clear();
            quizDatalist.clear();

            try {

                JSONObject jsonObject = new JSONObject(success);

                JSONArray arr = jsonObject.optJSONArray("data");
                Log.e("arr", " " + arr);


                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    Data data = new Data();
                    data.setLessonId(obj.optString("les_id"));
                    data.setLessonName(obj.getString("les_name"));
                    data.setDescription(obj.getString("les_desc"));
                    data.setVideoUrl(obj.getString("video_url"));
                    data.setVideoThumbnail(obj.getString("video_thumb"));
                    dataList.add(data);

                    Log.e("datalist", "" + dataList);

                    JSONArray arr1 = obj.getJSONArray("quiz_data");
                    Log.e("arr1", "" + arr1);

                    for (int j = 0; j < arr1.length(); j++) {
                        JSONObject obj1 = arr1.getJSONObject(j);

                        QuizData quizData = new QuizData();
                        quizData.setQuizMasterId(obj1.getString("quiz_master_id"));
                        quizData.setQuizName(obj1.getString("quiz_name"));
                        quizData.setQuizDescription(obj1.getString("quiz_desc"));
                        quizDatalist.add(quizData);

                    }
                    mListView.setAdapter(mAdapter);
                    mAdapter.setMode(Attributes.Mode.Single);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }*/
    }





}

