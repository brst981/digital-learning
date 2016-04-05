package app.com.digitallearning.StudentModule.StudentLesson;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
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
import app.com.digitallearning.StudentModule.NavigationDrawerStudent;
import app.com.digitallearning.TeacherModule.Model.Data;
import app.com.digitallearning.TeacherModule.Model.QuizData;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 1/28/16.
 */
public class StudentLessonFragment  extends Fragment {
    public static String LESSON_SIZE = "lesson_size";
    View rootview;
    TextView headerTitle;
    String textHeader, clsid;
    private ListView mListView;
    ListViewAdapter mAdapter;
    LinearLayout bottom_wrapper;
    ProgressDialog dlg;
    SharedPreferences preferences;
    ArrayList<Data> dataList = new ArrayList<Data>();
    ArrayList<QuizData> quizDatalist = new ArrayList<QuizData>();


    public static StudentLessonFragment newInstance() {
        StudentLessonFragment mFragment = new StudentLessonFragment();
        /*Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);*/
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.student_lesson, container, false);
        dlg = new ProgressDialog(getActivity());

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Choose Lesson");


        mListView = (ListView) rootview.findViewById(R.id.listview_archieved);
        mAdapter = new ListViewAdapter(getActivity());
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        clsid = preferences.getString("class_id", "");


        NavigationDrawerStudent.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mFragment = LessonProfile.newInstance();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Student_Lesson_Profile lesson = new Student_Lesson_Profile();
                fragmentTransaction.replace(android.R.id.content, lesson).addToBackStack(null);

                fragmentTransaction.commit();
                // Toast.makeText(getActivity(),"Clmfkff",Toast.LENGTH_SHORT).show();

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((SwipeLayout) (mListView.getChildAt(position - mListView.getFirstVisiblePosition()))).open(true);
            }
        });
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("ListView", "OnTouch");
                return false;
            }
        });

        NavigationDrawerStudent.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mFragment = LessonProfile.newInstance();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Student_Lesson_Profile lesson = new Student_Lesson_Profile();
                fragmentTransaction.replace(android.R.id.content, lesson).addToBackStack(null);

                fragmentTransaction.commit();
                // Toast.makeText(getActivity(),"Clmfkff",Toast.LENGTH_SHORT).show();

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


                Bundle bundle = new Bundle();
                bundle.putString("positioninLesson", dataList.get(position).getLessonName());
                bundle.putString("lessonid", dataList.get(position).getLessonId());
                bundle.putString("sizeget", String.valueOf(dataList.size()));
                Log.e("sizeget",""+String.valueOf(dataList.size()));
                bundle.putInt("position", position);
                bundle.putInt(LESSON_SIZE, dataList.size());
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Student_Item_Lesson student_item_lesson = new Student_Item_Lesson();
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
        return rootview;
    }

    private void initData() {
        textHeader = "sdhfygsjdgf";


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
        public View generateView(int position, ViewGroup parent) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.list_student_lesson, null);
            SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));
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
                    final Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    String link="https://digitallearningtree2.com";
                    String link1="Hi, I am studying a lesson from this Website";
                  //  shareIntent.putExtra(Intent.EXTRA_TEXT, link1);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, link1+"\n"+link);
                    startActivity(Intent.createChooser(shareIntent, "Share image using"));
                    // Toast.makeText(mContext, "click delete", Toast.LENGTH_SHORT).show();
                }
            });
            return v;
        }

        @Override
        public void fillValues(int position, View convertView) {
            TextView studentlessonname = (TextView)convertView.findViewById(R.id.studentlessonname);
            studentlessonname.setText(dataList.get(position).getLessonName());

            Log.e("settext",""+dataList.get(position).getLessonName());
        }

        @Override
        public int getCount() {
            return dataList.size();
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

    class Student_Get_Lesson extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Student_Get_Lesson(params[0]);

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

        //  if (dlg != null)
           // if(dlg.isShowing()){
            dlg.dismiss();
       // }
            Log.e("StudentGetLesson", "" + result);

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
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("vist","bshjsi");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new Student_Get_Lesson().execute(clsid);

    }
}
