package app.com.digitallearning.TeacherModule.Grade;

/**
 * Created by ${PSR} on 2/3/16.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import java.util.List;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Model.Grade_Info;
import app.com.digitallearning.TeacherModule.Model.Lesson_Info;
import app.com.digitallearning.TeacherModule.Model.Quiz_Info;
import app.com.digitallearning.TeacherModule.Model.Quiz_Total_Score;
import app.com.digitallearning.WebServices.WSConnector;

public class Grade_Lesson extends  Fragment {
    View rootview;
    TextView headerTitle;
    String textHeader;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    ProgressDialog dlg;
    SharedPreferences preferences;
    String Sch_Mem_id, cla_classid,studentid,total_grade_for_class;
    ArrayList<String> arrLessonName;

    String jsonResult;

    ArrayList<Lesson_Info> lesson_infolist=new ArrayList<Lesson_Info>();
    ArrayList<Quiz_Total_Score> quiztotalscorelist=new ArrayList<Quiz_Total_Score>();
    ArrayList<Quiz_Info> quizinfolist=new ArrayList<Quiz_Info>();
    ArrayList<Grade_Info> gradeinfolist=new ArrayList<Grade_Info>();


 //   ArrayList<Quiz_Listing> quizlisting = new ArrayList<Quiz_Listing>();
    //ArrayList<Data> dataList = new ArrayList<Data>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.grade_lesson, container, false);
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.myrecycler);
        dlg=new ProgressDialog(getActivity());
        arrLessonName=new ArrayList<>();
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Lesson");

        initData();
        studentid=getArguments().getString("studentid");
        Log.e("studentidres",""+studentid);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
        });
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyRecyclerViewAdapter();

        return rootview;
    }

    private void initData() {
        textHeader ="sdhfygsjdgf";


            }



    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private Context mContext;
        private LayoutInflater inflater;
        List<String> myList;
        List<String> myList1;

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.lessondetail, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);


            return viewHolder;
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Log.e("position1", "" + position);
            holder.lessonname.setText(lesson_infolist.get(position).getLesson_name().toString());




        }

        @Override
        public int getItemCount() {


            return lesson_infolist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


            RippleView lessonnameripple;
            TextView lessonname;




            public ViewHolder(View itemView) {
                super(itemView);

                lessonname=(TextView)itemView.findViewById(R.id.lessonname);
                lessonnameripple = (RippleView) itemView.findViewById(R.id.lessonnameripple);
                // a3ripple.setOnClickListener(this);

                lessonnameripple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        int position = getLayoutPosition(); // gets item position



                        FragmentManager fragmentManager=getFragmentManager();
                        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                        Grade_Details grade_details=new Grade_Details();
                        Bundle bundle=new Bundle();
                        //for(int i=0;i<quizinfolist.size();i++) {
                           // Log.e("totalname", "" + quizinfolist.get(i).getQuiz_name());
                          //  Log.e("totalname1", "" + quizinfolist.get(i).getAss_less_id());

                            bundle.putString("studentid", studentid);
                            bundle.putString("jsonResult",jsonResult);
                            bundle.putString("position",""+position);
                        bundle.putString("total_grade_for_class",total_grade_for_class);

                        Log.e("bbbbbb",""+total_grade_for_class);
                          //  Log.e("QUIZNAMES", "" + quizinfolist.get(i).getQuiz_name());
                      //  }
                        fragmentTransaction.replace(R.id.container,grade_details).addToBackStack(null);
                        grade_details.setArguments(bundle);
                        fragmentTransaction.commit();

                    }
                });
            }

            @Override
            public void onClick(View v) {
                int position = getLayoutPosition();
                Log.e("positionGetLay", "" + position);
                int id = v.getId();
                switch (id) {
                    case R.id.a3ripple:
                        if (position == 0) {


                            break;
                        }

                }
            }
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Sch_Mem_id = preferences.getString("Sch_Mem_id", "");
        Log.e("Sch_Mem_id", "" + Sch_Mem_id);

        cla_classid = preferences.getString("cla_classid", "");
        Log.e("cla_classid", "" + cla_classid);
       // new Get_Lesson().execute(cla_classid, Sch_Mem_id);
        new Grade_lessondetail().execute(cla_classid,studentid);
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
            jsonResult = result;
            if (result.contains("true")) {

                updateGrade_lessondetail(result);


            } else if (result.contains("false")) {
                Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();

            }
        }


        private void updateGrade_lessondetail(String success) {
            lesson_infolist.clear();
            try {

                JSONObject jsonObject = new JSONObject(success);

                JSONObject jsonObject1 = jsonObject.getJSONObject("data");




                JSONArray quiz_total_score=jsonObject1.getJSONArray("quiz_total_score");
                Log.e("quiz_total_score",""+quiz_total_score);

                for(int j=0;j<quiz_total_score.length();j++){
                    JSONObject obj1 = quiz_total_score.getJSONObject(j);
                    Quiz_Total_Score quiztotalscore=new Quiz_Total_Score();
                    quiztotalscore.setTotal(obj1.optString("total"));
                    quiztotalscore.setLesson_id(obj1.optString("lesson_id"));
                    quiztotalscorelist.add(quiztotalscore);
                    Log.e("quiztotalscorelist",""+quiztotalscorelist);



                JSONArray arr = jsonObject1.getJSONArray("lesson_info");
                Log.e("arr", " " + arr);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Log.e("obj", "" + obj);

                    Lesson_Info lesson_info = new Lesson_Info();
                    lesson_info.setLes_id(obj.optString("les_id"));
                    lesson_info.setLesson_name(obj.optString("lesson_name"));
                    Log.e("value", "" + obj.optString("lesson_name"));
                    lesson_infolist.add(lesson_info);
                    Log.e("lesson_infolist", "" + lesson_infolist.get(i).getLesson_name().toString());
                    mRecyclerView.setAdapter(mAdapter);
                }

                    JSONArray quiz_info=jsonObject1.getJSONArray("quiz_info");
                    for(int k=0;k<quiz_info.length();k++){
                        JSONObject obj2 = quiz_info.getJSONObject(k);

                        Quiz_Info quizinfo=new Quiz_Info();
                        quizinfo.setQuiz_id(obj2.getString("quiz_id"));
                        quizinfo.setQuiz_name(obj2.getString("quiz_name"));
                        quizinfo.setAss_less_id(obj2.getString("ass_less_id"));
                        quizinfo.setGet_point(obj2.getString("get_point"));
                        quizinfo.setTotal_point(obj2.getString("total_point"));
                        quizinfolist.add(quizinfo);
                        Log.e("quizinfolist",""+quizinfolist.get(k).getAss_less_id().toString());
                    }


                JSONObject Total_number=jsonObject1.getJSONObject("Total_number");
                Log.e("Total_number",""+Total_number);
                String Total=Total_number.getString("Total");
                JSONArray grade_info=jsonObject1.getJSONArray("grade_info");
                for(int l=0;l<grade_info.length();l++){
                    JSONObject obj3 = grade_info.getJSONObject(l);

                    Grade_Info gradeinfo=new Grade_Info();
                    gradeinfo.setLesson_id(obj3.getString("lesson_id").toString());
                    gradeinfo.setGrade(obj3.getString("grade").toString());
                    gradeinfolist.add(gradeinfo);
                    Log.e("gradeinfolist",""+gradeinfolist);
                }
                JSONObject total_grade=jsonObject1.getJSONObject("total_grade");
                Log.e("total_grade",""+total_grade);
                 total_grade_for_class=Total_number.getString("total_grade_for_class");
                Log.e("total_grade_for_class",""+total_grade_for_class);
                //lesson_infolist  quiztotalscorelist  quizinfolist gradeinfolist
                    Log.e("sizeget",""+lesson_infolist.size());
                    Log.e("jsonobject",""+jsonObject);
                    Log.e("jsonobject1",""+jsonObject1);
                }








            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}