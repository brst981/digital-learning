package app.com.digitallearning.StudentModule.StudentQuiz;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.daimajia.swipe.util.Attributes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Model.Quiz_Listing;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 1/29/16.
 */
public class StudentQuizFragment  extends Fragment {
    View rootview;
    TextView headerTitle;
    String textHeader,cid , userid;
    private ListView mListView;
    ListViewAdapter mAdapter;
    ProgressDialog dlg;
    ArrayList<Quiz_Listing> quizlisting = new ArrayList<Quiz_Listing>();
    SharedPreferences preferences;
    int checkId=10;
    public static StudentQuizFragment newInstance() {
        StudentQuizFragment mFragment = new StudentQuizFragment();
        /*Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);*/
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.student_quiz, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Choose Quiz");


        initData();
        dlg=new ProgressDialog(getActivity());



        mListView = (ListView)rootview.findViewById(R.id.listview_archieved);
        mAdapter = new ListViewAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mAdapter.setMode(Attributes.Mode.Single);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((SwipeLayout)(mListView.getChildAt(position - mListView.getFirstVisiblePosition()))).open(true);
            }
        });
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("ListView", "OnTouch");
                return false;
            }
        });
     /*   mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "OnItemLongClickListener", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(),"clicked"+" "+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                startActivity(intent);
                return true;
            }
        });*/
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               /* Toast.makeText(getActivity(), "OnItemLongClickListener", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(),"clicked"+" "+position,Toast.LENGTH_SHORT).show();*/
                FragmentManager fragmentManager = getFragmentManager();
                Bundle bundle=new Bundle();
                bundle.putInt("positioninLesson",position);
                bundle.putString("quiz_id",quizlisting.get(position).getQuiz_id());
                bundle.putString("dashboard_id",quizlisting.get(position).getDashboard_id());
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Student_Quiz_View student_item_lesson = new Student_Quiz_View();
                fragmentTransaction.replace(R.id.container, student_item_lesson).addToBackStack(null);
                student_item_lesson.setArguments(bundle);
                fragmentTransaction.commit();
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {


                Log.e("ListView", "onScrollStateChanged");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                Log.e("ListView", "onItemSelected:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("ListView", "onNothingSelected:");
            }
        });
        initData();

        preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
        cid=preferences.getString("class_id","");
        userid=preferences.getString("Sch_Mem_id","");
        new Quiz_listing().execute(cid,userid);
        return rootview;
    }

    private void initData() {
        textHeader ="sdhfygsjdgf";


    }

    class ListViewAdapter extends BaseSwipeAdapter {
        private Context mContext;

        public ListViewAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe;
        }



        @Override
        public View generateView(final int position, ViewGroup parent) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.quiz_list, null);
            SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
            swipeLayout.addSwipeListener(new SimpleSwipeListener() {
                @Override
                public void onOpen(SwipeLayout layout) {

                }
            });
            swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
                @Override
                public void onDoubleClick(SwipeLayout layout, boolean surface) {
                    Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
                }
            });
            v.findViewById(R.id.archive).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Result_Student_Quiz result_student_quiz = new Result_Student_Quiz();
                    Bundle bundle=new Bundle();
                    bundle.putInt("checkId",checkId);
                    bundle.putString("masterid",quizlisting.get(position).getQuiz_id());
                    bundle.putString("dashbordid",quizlisting.get(position).getDashboard_id());
                    fragmentTransaction.replace(R.id.container, result_student_quiz).addToBackStack(null);
                    result_student_quiz.setArguments(bundle);
                    fragmentTransaction.commit();

                }
            });
            return v;
        }

        @Override
        public void fillValues(int position, View convertView) {
             TextView text_quizname = (TextView)convertView.findViewById(R.id.text_quizname);
              text_quizname.setText(quizlisting.get(position).getQuiz_name());
        }

        @Override
        public int getCount() {
            return quizlisting.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }




    class Quiz_listing extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Stuent_Quiz_listing(params[0], params[1]);

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
            Log.e("StudentQuizlisting", "" + result);
            if (result.contains("true")) {

                updateTeacherLogIn(result);


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
                    data.setQuiz_id(obj.getString("quiz_id"));
                    data.setAss_less_id(obj.getString("lesson_id"));
                    data.setQuiz_cid(obj.getString("quiz_cid"));
                    data.setQuiz_name(obj.getString("quiz_name"));
                    data.setQuiz_desc(obj.getString("quiz_desc"));
                    data.setApi_quiz_id(obj.getString("quiz_status"));
                    data.setDashboard_id(obj.getString("dashboard_id"));
                    quizlisting.add(data);




                }

                mListView.setAdapter(mAdapter);
                mAdapter.setMode(Attributes.Mode.Single);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
}
