package app.com.digitallearning.TeacherModule.Quiz;

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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
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
import app.com.digitallearning.TeacherModule.Model.Quiz_Listing;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 1/28/16.
 */
public class QuizFragment extends Fragment {
    View rootview;
    SwipeRefreshLayout swipeRefreshLayout;
    RippleView rippleViewCreate;
    TextView headerTitle;
    private ListView mListView;
    ListViewAdapter mAdapter;
    ProgressDialog dlg;
    int onitemid=10;
    String textHeader, cla_classid, userid,delquizid;
    SharedPreferences preferences;
    ArrayList<Quiz_Listing> quizlisting = new ArrayList<Quiz_Listing>();
    public static QuizFragment newInstance() {
        QuizFragment mFragment = new QuizFragment();
        /*Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);*/
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_quiz, container, false);

        rippleViewCreate = (RippleView) rootview.findViewById(R.id.ripple_create_resource);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Class Quiz");

        initData();
        dlg=new ProgressDialog(getActivity());
        swipeRefreshLayout = (SwipeRefreshLayout)rootview.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorlima);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userid = preferences.getString("Sch_Mem_id", "");
        Log.e("userid", "" + userid);

        cla_classid = preferences.getString("cla_classid", "");
        Log.e("cla_classid", "" + cla_classid);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
        mListView = (ListView)rootview.findViewById(R.id.listview_archieved);
        mAdapter = new ListViewAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mAdapter.setMode(Attributes.Mode.Single);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Quiz_View quiz_view = new Quiz_View();
                Bundle bundle=new Bundle();
                bundle.putString("quizid",quizlisting.get(position).getQuiz_id());
                bundle.putInt("onitemid",onitemid);
                fragmentTransaction.replace(R.id.container, quiz_view).addToBackStack(null);
                quiz_view.setArguments(bundle);
                fragmentTransaction.commit();
               // ((SwipeLayout)(mListView.getChildAt(position - mListView.getFirstVisiblePosition()))).open(true);
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


               /* Toast.makeText(getActivity(), "OnItemLongClickListener", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(),"clicked"+" "+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                startActivity(intent);*/
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
        return rootview;
    }

    private void initData() {
        textHeader ="sdhfygsjdgf";

        rippleViewCreate.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AddQuiz classFragment = new AddQuiz();
                fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


    }
    private void refreshItems() {
        // Load items
        // ...

        // Load complete
        onItemsLoadComplete();
    }

    private void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        swipeRefreshLayout.setRefreshing(false);
    }

    class ListViewAdapter extends BaseSwipeAdapter {
        private Context mContext;
        int pos;

        public ListViewAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe;
        }



        @Override
        public View generateView(int position, ViewGroup parent) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.quiz_item, null);
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
           /* v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "click delete", Toast.LENGTH_SHORT).show();
                }
            });*/
           /* v.findViewById(R.id.archive).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e("pos",""+pos);

                }
            });*/
            return v;
        }

        @Override
        public void fillValues(final int position, View convertView) {
            pos=position;
            Log.e("pos1",""+pos);
             TextView quizname = (TextView)convertView.findViewById(R.id.quizname);
             quizname.setText(quizlisting.get(position).getQuiz_name());

            TextView lastmodify = (TextView)convertView.findViewById(R.id.lastmodify);
            lastmodify.setText(quizlisting.get(position).getLast_modify());
            TextView archive=(TextView)convertView.findViewById(R.id.archive);
            archive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Quiz_Edit classFragment = new Quiz_Edit();
                    Bundle bundle=new Bundle();
                    bundle.putString("quizid",quizlisting.get(position).getQuiz_id());
                   // bundle.putInt("pos",position);
                    //Log.e("goingpos",""+pos);
                    fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                    classFragment.setArguments(bundle);
                    fragmentTransaction.commit();
                }
            });

            TextView delete =(TextView)convertView.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   delquizid=quizlisting.get(position).getQuiz_id();

                   new Delete_Quiz(position).execute(cla_classid,delquizid);
                }
            });
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

            return WSConnector.Quiz_listing(params[0], params[1]);

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
            Log.e("Quizlisting", "" + result);
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
                    data.setQuiz_id(obj.getString("quizid"));
                    data.setAss_less_id(obj.getString("ass_less_id"));
                    data.setQuiz_cid(obj.getString("quiz_cid"));
                    data.setApi_quiz_id(obj.getString("api_quizid"));
                    data.setQuiz_desc(obj.getString("quiz_desc"));
                    data.setQuiz_user_id(obj.getString("user_id"));
                    data.setQuiz_name(obj.getString("quiz_name"));
                    quizlisting.add(data);




                }

                mListView.setAdapter(mAdapter);
                mAdapter.setMode(Attributes.Mode.Single);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }





    class Delete_Quiz extends AsyncTask<String, Integer, String> {
        private int pos;
        public Delete_Quiz(int pos) {
            this.pos = pos;

        }
        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Delete_quiz(params[0], params[1]);

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
            if (dlg != null)
                dlg.dismiss();
            Log.e("Delete_Quiz", "" + result);

            if (result.contains("true")) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Quiz deleted").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                                quizlisting.remove(pos);
                                mAdapter = new ListViewAdapter(getActivity());

                                mListView.setAdapter(mAdapter);
                            }
                        });

                AlertDialog dialog = alertDialog.create();
                dialog.show();
                TextView messageText = (TextView) dialog
                        .findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER);


            } else if (result.contains("false")) {
                //   updateGet_Lesson(result);


            }
        }
    }













    @Override
    public void onResume() {
        super.onResume();
        new Quiz_listing().execute(cla_classid,userid);
    }
}

