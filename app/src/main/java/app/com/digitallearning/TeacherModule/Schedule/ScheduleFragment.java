package app.com.digitallearning.TeacherModule.Schedule;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import app.com.digitallearning.TeacherModule.Classes.ClassesDetailFragment;
import app.com.digitallearning.TeacherModule.Lessons.Lesson_Edit;
import app.com.digitallearning.Utill.OnReceiveListner;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 2/4/16.
 */
public class ScheduleFragment extends FragmentActivity {

    public static final String RLISTNER = "r_listner";

    View rootview;
    RippleView rippleViewCreate;
    TextView headerTitle;
    String textHeader, cla_classid, userid;
    ProgressDialog dlg;
    private ListView mListView;
    ListViewAdapter mAdapter;
    LinearLayout bottom_wrapper;
    SharedPreferences preferences;
    int pos;
    int shouldempty = 2;
    RelativeLayout relschedule;
    ImageButton imageButtonZoomIn, imageButtonZoomOut, back;
    ArrayList<String> arrTimeId, arrdescription, arrEn_min, arrStr_min, arrEn_Hour, arrStr_Hour, arrDay, arrLocation;
    OnReceiveListner mListner;


//    @Override
//    public void onResume() {
//        super.onResume();
//        new Schedule_listing().execute(userid, cla_classid);
//    }

    /*@Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new Schedule_listing().execute(userid, cla_classid);
    }
*/

    @Override
    protected void onResume() {
        super.onResume();
        new Schedule_listing().execute(userid, cla_classid);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_schedule);


        arrTimeId = new ArrayList<>();
        arrdescription = new ArrayList<>();
        arrEn_min = new ArrayList<>();
        arrStr_min = new ArrayList<>();
        arrEn_Hour = new ArrayList<>();
        arrStr_Hour = new ArrayList<>();
        arrDay = new ArrayList<>();
        arrLocation = new ArrayList<>();


        dlg = new ProgressDialog(ScheduleFragment.this);
        preferences = PreferenceManager.getDefaultSharedPreferences(ScheduleFragment.this);
        relschedule = (RelativeLayout) findViewById(R.id.relschedule);
        imageButtonZoomIn = (ImageButton) findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) findViewById(R.id.img_zoom_out);
        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ClassesDetailFragment classesDetailFragment = new ClassesDetailFragment();
                fragmentTransaction.replace(R.id.container, classesDetailFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        userid = preferences.getString("Sch_Mem_id", "");
        Log.e("userid", "" + userid);

        cla_classid = preferences.getString("cla_classid", "");
        Log.e("cla_classid", "" + cla_classid);

        rippleViewCreate = (RippleView) findViewById(R.id.ripple_create_lesson);

        /*AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        activity.getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Schedule");*/
        mListView = (ListView) findViewById(R.id.listview_archieved);
        mAdapter = new ListViewAdapter(ScheduleFragment.this);
        //  mListView.setAdapter(mAdapter);
        //  mAdapter.notifyDataSetChanged();
        mAdapter.setMode(Attributes.Mode.Single);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent inte = new Intent(ScheduleFragment.this, ViewSchedule.class);
                inte.putExtra("timeID", arrTimeId.get(position));
                startActivity(inte);

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

                // Log.e("ListView", "onItemSelected:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("ListView", "onNothingSelected:");
            }
        });
        initData();
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

    }

    private void initData() {
        textHeader = "sdhfygsjdgf";

        rippleViewCreate.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

                // AddSchedule addSchedule = new AddSchedule(mListner);
                Intent inty = new Intent(ScheduleFragment.this, AddSchedule.class);
                inty.putExtra("shouldempty", shouldempty);
                startActivity(inty);
                finish();
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
            View v = LayoutInflater.from(mContext).inflate(R.layout.list_schedule, null);
            SwipeLayout swipeLayout = (SwipeLayout) v.findViewById(getSwipeLayoutResourceId(position));
            swipeLayout.addSwipeListener(new SimpleSwipeListener() {
                @Override
                public void onOpen(SwipeLayout layout) {
                    // Toast.makeText(mContext, "Swipe Open", Toast.LENGTH_SHORT).show();
                }
            });
            swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
                @Override
                public void onDoubleClick(SwipeLayout layout, boolean surface) {
                    // Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
                }
            });
            v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //  Toast.makeText(mContext, "click delete", Toast.LENGTH_SHORT).show();
                }
            });

            v.findViewById(R.id.archive).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Lesson_Edit classFragment = new Lesson_Edit();
                    fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
            return v;
        }

        @Override
        public void fillValues(final int position, View convertView) {
            TextView delete = (TextView) convertView.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("arrTimeId.get(position)", "" + arrTimeId.get(position));
                    new Delete_schedule(position).execute(arrTimeId.get(position));
                }
            });
            TextView day = (TextView) convertView.findViewById(R.id.day);
            //  day.setText(arrDay.get(position));

            if (arrDay.get(position).contains("1")) {
                day.setText("Monday");
            } else if (arrDay.get(position).contains("2")) {
                day.setText("Tuesday");
            } else if (arrDay.get(position).contains("3")) {
                day.setText("Wednesday");
            } else if (arrDay.get(position).contains("4")) {
                day.setText("Thrusday");
            } else if (arrDay.get(position).contains("5")) {
                day.setText("Friday");
            } else if (arrDay.get(position).contains("6")) {
                day.setText("Saturday");
            } else if (arrDay.get(position).contains("7")) {
                day.setText("Sunday");
            } else if (arrDay.get(position).contains("8")) {
                day.setText("Every Day");
            } else if (arrDay.get(position).contains("9")) {
                day.setText("Every Weekday");
            }
            TextView location = (TextView) convertView.findViewById(R.id.location);
            location.setText(arrLocation.get(position));
            // TextView t = (TextView)convertView.findViewById(R.id.position);
            // t.setText((position + 1) + ".");
        }

        @Override
        public int getCount() {
            return arrDay.size();
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


    class Schedule_listing extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Schedule_listing(params[0], params[1]);

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
            Log.e("REsulTinSchedule", "" + result);
            if (result.contains("true")) {

                updateTeacherLogIn(result);


            } else if (result.contains("false")) {
                Toast.makeText(ScheduleFragment.this, "No data", Toast.LENGTH_SHORT).show();

            }
        }
//{"success":true,"user_type":"Teacher","Sch_Mem_id":"2155","Mem_Sch_Id":"487","Mem_Type":"1, 4","Mem_Name":"Ashish",
// "Mem_Emailid":"brstdev@gmail.com","class_data":[{"class_id":"183599","cls_createdby":"2155","cls_name":"1",
// "Cls_desc":"Test","subject":"Training ","cla_classid":"1800","students":"0","cls_image":"","orderid":"649",
// "new_orderid":"125"},

        private void updateTeacherLogIn(String success) {

            try {

                JSONObject jsonObject = new JSONObject(success);
                Log.e("jsonObject", "" + jsonObject);



                arrTimeId.clear();
                arrdescription.clear();
                arrEn_min.clear();
                arrStr_min.clear();
                arrEn_Hour.clear();
                arrStr_Hour.clear();
                arrDay.clear();
                arrLocation.clear();
                JSONArray arr = jsonObject.getJSONArray("data");
                Log.e("arr", " " + arr);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Log.e("obj", "" + obj);

                    String timeid = obj.getString("timeid");
                    Log.e("timeid", "" + timeid);

                    String Description = obj.getString("Description");
                    Log.e("Description", "" + Description);

                    String En_Min = obj.getString("En_Min");
                    Log.e("En_Min", "" + En_Min);

                    String Str_Min = obj.getString("Str_Min");
                    Log.e("Str_Min", "" + Str_Min);

                    String En_Hour = obj.getString("En_Hour");
                    Log.e("En_Hour", "" + En_Hour);

                    String Str_Hour = obj.getString("Str_Hour");
                    Log.e("Str_Hour", "" + Str_Hour);

                    String day = obj.getString("day");
                    Log.e("day", "" + day);

                    String Cls_Location = obj.getString("Cls_Location");
                    Log.e("Cls_Location", "" + Cls_Location);

                    String cls_id = obj.getString("cls_id");
                    Log.e("cls_id", "" + cls_id);


                    arrTimeId.add(timeid);
                    arrdescription.add(Description);
                    arrEn_min.add(En_Min);
                    arrStr_min.add(Str_Min);
                    arrEn_Hour.add(En_Hour);
                    arrStr_Hour.add(Str_Hour);
                    arrDay.add(day);
                    Log.e("ArrayDay", "" + arrDay);
                    arrLocation.add(Cls_Location);

                }

                mListView.setAdapter(mAdapter);
                mAdapter.setMode(Attributes.Mode.Single);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    class Delete_schedule extends AsyncTask<String, Integer, String> {
        private int pos;

        public Delete_schedule(int pos) {
            this.pos = pos;

        }

        @Override
        protected String doInBackground(String... params) {

            return WSConnector.delete_Schedule(params[0]);

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
            Log.e("REsulTinSchedule", "" + result);
            if (result.contains("true")) {
                //  ((BaseSwipeAdapter) mListView.getAdapter()).notifyDataSetChanged();
                //    mAdapter.notifyDataSetChanged();

                //  Toast.makeText(getActivity(), "Schedule deleted", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(ScheduleFragment.this);
                alertDialog.setMessage("Schedule successfully deleted").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub

                                Log.e("sizeafterdelete", "" + arrDay.size());


                                // mListView.setAdapter(mAdapter);
                                Log.e("posdelete", "" + arrDay.get(pos));
                                arrDay.remove(pos);
                                mAdapter = new ListViewAdapter(ScheduleFragment.this);

                                mListView.setAdapter(mAdapter);
                                //  mAdapter.notifyDataSetChanged();
                                Log.e("sizeafterdelete", "" + arrDay.size());
                                Log.e("sizeafterdelete", "" + arrDay.size());
                            }
                        });

                AlertDialog dialog = alertDialog.create();
                dialog.show();
                TextView messageText = (TextView) dialog
                        .findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER);


            } else if (result.contains("false")) {
                Toast.makeText(ScheduleFragment.this, "Wrong User", Toast.LENGTH_SHORT).show();

            }
        }

    }

    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        relschedule.setPivotX(pivot.x);
        relschedule.setPivotY(pivot.y);
        relschedule.setScaleX(scaleX);
        relschedule.setScaleY(scaleY);
    }


}
