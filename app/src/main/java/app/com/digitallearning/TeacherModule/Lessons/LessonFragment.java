package app.com.digitallearning.TeacherModule.Lessons;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
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

import com.andexert.library.RippleView;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.daimajia.swipe.util.Attributes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Model.Data;
import app.com.digitallearning.TeacherModule.Model.QuizData;
import app.com.digitallearning.TeacherModule.NavigationDrawerFragment;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${ShalviSharma} on 12/23/15.
 */
public class LessonFragment extends Fragment {
    View rootview;
    RippleView rippleViewCreate;
    TextView headerTitle;
    String textHeader;
    private ListView mListView;
    ListViewAdapter mAdapter;
    SharedPreferences preferences;
    String Sch_Mem_id,cla_classid;
    LinearLayout bottom_wrapper;
    ProgressDialog dlg;

    ArrayList<Data> dataList = new ArrayList<Data>();
    ArrayList<QuizData> quizDatalist = new ArrayList<QuizData>();



    public static LessonFragment newInstance() {
        LessonFragment mFragment = new LessonFragment();
        /*Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);*/
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_lesson, container, false);
        dlg=new ProgressDialog(getActivity());
        rippleViewCreate = (RippleView) rootview.findViewById(R.id.ripple_create_lesson);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
//        NavigationActivity.mToolbar.setNavigationIcon(R.drawable.drawericon);
//        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Sch_Mem_id=preferences.getString("Sch_Mem_id","");
        Log.e("Sch_Mem_id",""+Sch_Mem_id);

        cla_classid=preferences.getString("cla_classid","");
        Log.e("cla_classid",""+cla_classid);



        new Get_Lesson().execute(cla_classid, Sch_Mem_id);
        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Choose Lesson");
        mListView = (ListView)rootview.findViewById(R.id.listview_archieved);
        mAdapter = new ListViewAdapter(getActivity());
       /* mListView.setAdapter(mAdapter);
        mAdapter.setMode(Attributes.Mode.Single);*/

        NavigationDrawerFragment.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mFragment = LessonProfile.newInstance();
               FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LessonProfile lesson = new LessonProfile();
                fragmentTransaction.replace(android.R.id.content, lesson).addToBackStack(null);

                fragmentTransaction.commit();
               // Toast.makeText(getActivity(),"Clmfkff",Toast.LENGTH_SHORT).show();

            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //((SwipeLayout)(mListView.getChildAt(position - mListView.getFirstVisiblePosition()))).open(true);

                Log.e("positioninLesson",""+position);

              FragmentManager fragmentManager = getFragmentManager();
                Bundle bundle=new Bundle();
                bundle.putInt("positioninLesson",position);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LessonDetailFragment lessonDetailFragment = new LessonDetailFragment();
                fragmentTransaction.replace(R.id.container, lessonDetailFragment).addToBackStack(null);
                lessonDetailFragment.setArguments(bundle);
                fragmentTransaction.commit();


            }
        });
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("ListView", "OnTouch");
                return false;
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(getActivity(), "OnItemLongClickListener", Toast.LENGTH_SHORT).show();
              //  Toast.makeText(getActivity(),"clicked"+" "+position,Toast.LENGTH_SHORT).show();
                return true;
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
        textHeader ="sdhfygsjdgf";

        rippleViewCreate.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AddLessonFragment classFragment = new AddLessonFragment();
                fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


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
            View v = LayoutInflater.from(mContext).inflate(R.layout.lesson_item_list, null);
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
            v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "click delete", Toast.LENGTH_SHORT).show();
                }
            });

            v.findViewById(R.id.archive).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Lesson_Edit classFragment = new Lesson_Edit();
                    fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
            return v;
        }

        @Override
        public void fillValues(int position, View convertView) {
            // TextView t = (TextView)convertView.findViewById(R.id.position);
            // t.setText((position + 1) + ".");
            TextView text_view=(TextView)convertView.findViewById(R.id.text_view);
            String title=dataList.get(position).getLessonName();
            text_view.setText(title);
        /*   TextView text_view1=(TextView)convertView.findViewById(R.id.text_view1);
            text_view1.setText("Social Media:Embed");*/
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

    class Get_Lesson extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Get_Lesson(params[0],params[1]);

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
//{"success":false,"data":null}
             /*   LogMessage.showDialog(getActivity(), null,
                        "Class successfully deleted", "OK");*/

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("No data").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                               /* Intent deletetoclass=new Intent(getActivity(),ClassActivity.class);
                                startActivity(deletetoclass);
                                getActivity().finish();*/

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

            try {

                JSONObject jsonObject = new JSONObject(success);
//{"success":true,"data":[{"les_id":"4114","Les_Cls_Id":"1808","lesson_name":"androidTest","les_date":"2016-03-05",
// "desc":"Description","les_image":"","img_thumb":"","video_url":"","video_thumb":"",
// "quiz_data":[{"quiz_master_id":"","quiz_name":"","quiz_desc":""}]},{"les_id":"4115","Les_Cls_Id":"1808","lesson_name":"try",
// "les_date":"2016-03-08","desc":"Ref hi","les_image":"","img_thumb":"","video_url":"","video_thumb":"",
// "quiz_data":[{"quiz_master_id":"","quiz_name":"","quiz_desc":""}]},
                JSONArray arr = jsonObject.optJSONArray("data");
                Log.e("arr", " " + arr);


                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    Data data = new Data();
                    data.setLessonId(obj.optString("les_id"));
                    data.setLessonClassId(obj.getString("Les_Cls_Id"));
                    data.setLessonName(obj.getString("lesson_name"));
                    data.setLessonDate(obj.getString("les_date"));

                    data.setDescription(obj.getString("desc"));
                    data.setLessonImage(obj.getString("les_image"));
                    data.setImageThumbnail(obj.getString("img_thumb"));
                    data.setVideoUrl(obj.getString("video_url"));
                    data.setVideoThumbnail(obj.getString("video_thumb"));
                    dataList.add(data);

                    /*String les_id = obj.optString("les_id");
                    Log.e("les_id", "" + les_id);
                    String Les_Cls_Id = obj.getString("Les_Cls_Id");
                    Log.e("Les_Cls_Id", "" + Les_Cls_Id);
                    String lesson_name = obj.getString("lesson_name");
                    Log.e("lesson_name", "" + lesson_name);
                    String les_date = obj.getString("les_date");
                    Log.e("les_date", "" + les_date);
                    String desc = obj.getString("desc");
                    Log.e("desc", "" + desc);
                    String les_image = obj.getString("les_image");
                    Log.e("les_image", "" + les_image);
                    String img_thumb = obj.getString("img_thumb");
                    Log.e("img_thumb", "" + img_thumb);
                    String video_url = obj.getString("video_url");
                    Log.e("video_url", "" + video_url);
                    String video_thumb = obj.getString("video_thumb");
                    Log.e("video_thumb", "" + video_thumb);*/

                    JSONArray arr1 = obj.getJSONArray("quiz_data");
                    Log.e("arr1", "" + arr1);

                    for (int j = 0; j < arr1.length(); j++) {
                        JSONObject obj1 = arr1.getJSONObject(j);

                        QuizData quizData = new QuizData();
                        quizData.setQuizMasterId(obj1.getString("quiz_master_id"));
                        quizData.setQuizName(obj1.getString("quiz_name"));
                        quizData.setQuizDescription(obj1.getString("quiz_desc"));
                        quizDatalist.add(quizData);

                        /*String quiz_master_id = obj1.getString("quiz_master_id");
                        Log.e("quiz_master_id", "" + quiz_master_id);
                        String quiz_name = obj1.getString("quiz_name");
                        Log.e("quiz_name", "" + quiz_name);
                        String quiz_desc = obj1.getString("quiz_desc");
                        Log.e("quiz_desc", "" + quiz_desc);*/

                    }
                    mListView.setAdapter(mAdapter);
                    mAdapter.setMode(Attributes.Mode.Single);
                }

            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
