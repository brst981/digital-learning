package app.com.digitallearning.TeacherModule.Grade;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Model.Archieve_Student;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 1/28/16.
 */
public class GradeFragment extends Fragment {
    View rootview;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView headerTitle;
    String textHeader;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private ListView mListView;
   // ListViewAdapter mAdapter;
    ProgressDialog dlg;
    String  cla_classid, userid;
    SharedPreferences preferences;
    ArrayList<Archieve_Student> archivelist = new ArrayList<Archieve_Student>();

    public static GradeFragment newInstance() {
        GradeFragment mFragment = new GradeFragment();
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_grade, container, false);


        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.myrecycler);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Student");

        initData();
        dlg=new ProgressDialog(getActivity());
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
                    .inflate(R.layout.list_grade_lesson, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);


            return viewHolder;
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Log.e("position1", "" + position);

            holder.lessonname.setText(archivelist.get(position).getStudent_Name()+" "+archivelist.get(position).getStudent_LastName());



        }

        @Override
        public int getItemCount() {
            return archivelist.size();
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
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        Grade_Lesson grade_lesson = new Grade_Lesson();
                        fragmentTransaction.replace(R.id.container, grade_lesson).addToBackStack(null);
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








    public class Get_enroll_Student extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Get_enroll_Student(params[0],params[1]);

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
            Log.e("StudentListingradesec", "" + result);
            if (result.contains("true")) {

                updateGet_enroll_Student(result);


            } else if (result.contains("false")) {
                Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();

            }
        }
        private void updateGet_enroll_Student(String success) {


            archivelist.clear();
            try {
                JSONObject jsonObject = new JSONObject(success);
                JSONArray arr = jsonObject.getJSONArray("data");
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Archieve_Student archieve_student = new Archieve_Student();
                    archieve_student.setStudent_Name(obj.optString("Name"));
                    archieve_student.setStudent_Memid(obj.getString("Mem_id"));
                    archieve_student.setStudent_Mem_Userid(obj.getString("Mem_Userid"));
                    archieve_student.setStudent_Password(obj.getString("Password"));
                    archieve_student.setStudent_Mem_Type(obj.getString("Mem_Type"));
                    archieve_student.setStudent_gradebookview(obj.getString("gradebookview"));
                    archieve_student.setStudent_LastName(obj.getString("LastName"));
                    archieve_student.setStudent_Email_id(obj.getString("Email_id"));
                    archieve_student.setStudent_Mem_Online(obj.getString("Mem_Online"));

                    archivelist.add(archieve_student);

                }
                mRecyclerView.setAdapter(mAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userid = preferences.getString("Sch_Mem_id", "");
        Log.e("userid", "" + userid);

        cla_classid = preferences.getString("cla_classid", "");
        Log.e("cla_classid", "" + cla_classid);
        new Get_enroll_Student().execute(userid,cla_classid);
    }
}


