package app.com.digitallearning.StudentModule;

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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.daimajia.swipe.util.Attributes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import app.com.digitallearning.R;
import app.com.digitallearning.StudentModule.Model.Student_Login_Data;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 1/28/16.
 */
public class StudentClass extends  Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    View rootview;
    String name,password,schoolID,Sch_Mem_id,Mem_Sch_Id,curdate;
    private ListView mListView;
    ListViewAdapter mAdapter;
    public static RelativeLayout relative_header;
    TextView headerTitle;
    boolean defineClass = false;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    RelativeLayout rellogin;
    ProgressDialog dlg;
    int val=0;
    ImageView logout;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    boolean lessonselected;
    ArrayList<Student_Login_Data> stulogindata=new ArrayList<Student_Login_Data>();
    public static StudentClass newInstance() {
        StudentClass mFragment = new StudentClass();
        /*Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);*/
        return mFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragmentstudentclass, container, false);

       /* AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Choose Class");*/

        imageButtonZoomIn = (ImageButton)rootview. findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) rootview.findViewById(R.id.img_zoom_out);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        final Date date = new Date();
        curdate= String.valueOf(dateFormat.format(date));

        rellogin=(RelativeLayout)rootview.findViewById(R.id.rellogin) ;
        dlg=new ProgressDialog(getActivity());
        logout=(ImageView)rootview.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                new Student_Logout().execute(Sch_Mem_id,curdate);



               /* GlobalClass.rememberMe=false;
                getActivity().finish();*/

            }
        });
        relative_header=(RelativeLayout)rootview.findViewById(R.id.relative_header);
        swipeRefreshLayout = (SwipeRefreshLayout)rootview.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorlima);
        //  relative_header.setVisibility(View.VISIBLE);

        /*mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
               *//* Toast.makeText(MainActivity.this,"clicked"+" "+position,Toast.LENGTH_SHORT).show();*//*
                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                startActivity(intent);

            }
        }));*/
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
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });
        mListView = (ListView)rootview.findViewById(R.id.listview_archieved);
        mAdapter = new ListViewAdapter(getActivity());

        mAdapter.setMode(Attributes.Mode.Single);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                //((SwipeLayout)(mListView.getChildAt(position - mListView.getFirstVisiblePosition()))).open(true);
            }
        });
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("ListView", "OnTouch");
                return false;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), NavigationForStudent.class);
                defineClass = true;
                lessonselected=true;
                intent.putExtra("fromClass", defineClass);
                intent.putExtra("lessonselected",lessonselected);
                startActivity(intent);

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

    @Override
    public void onStart() {
        super.onStart();

        if(defineClass){

        }
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
            View v = LayoutInflater.from(mContext).inflate(R.layout.classes_item_list, null);
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
            v.findViewById(R.id.editbutton).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Info info = new Info();
                    fragmentTransaction.replace(R.id.container, info).addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
            return v;
        }

        @Override
        public void fillValues(int position, View convertView) {
             TextView text_class_name = (TextView)convertView.findViewById(R.id.text_class_name);
            text_class_name.setText(stulogindata.get(position).getClass_name());
            TextView text_subject_name=(TextView)convertView.findViewById(R.id.text_subject_name);
            text_subject_name.setText(stulogindata.get(position).getSubject());
            TextView text_studen_name=(TextView)convertView.findViewById(R.id.text_studen_name);
            text_studen_name.setText(stulogindata.get(position).getStudents());
        }

        @Override
        public int getCount() {
            return stulogindata.size();
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





    class StudentLogIn extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Student_login(params[0], params[1], params[2]);

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
            Log.e("classlistingstudent", "" + result);
            if (result.contains("true")) {

                updateStudentLogIn(result);


            } else if (result.contains("false")) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Wrong Credentials").setCancelable(false)
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

            }
        }


        private void updateStudentLogIn(String success) {
            stulogindata.clear();
            try {
//{"success":true,"user_type":"Teacher","Sch_Mem_id":"2155","Mem_Sch_Id":"487","Mem_Type":"1, 4","Mem_Name":"Ashish","Mem_Emailid":"brstdev@gmail.com",
// "class_data":[{"class_id":"183607","cls_createdby":"2155","cls_name":"9Edit12h","Cls_desc":"ok","subject":"Music","cla_classid":"1808","students":"6",
// "cls_image":"http:\/\/digitallearningtree.com\/class_photos\/1457699372star_wars_the_force_awakens_poe_rey_bb8-HD.jpg","orderid":"657","new_orderid":"133"},


//{"success":true,"user_type":"Student","Sch_Mem_id":"2322","Mem_Sch_Id":"487","Mem_Type":"16","Mem_Name":"komal","Mem_Emailid":"komal@gmail.com",
// "class_data":{"success":true,"data":[{"classid":"1808","classname":"9Edit12h","classdesc":"ok","topicname":"Music","student":"4","Cla_Id":"183607"}]}}
                JSONObject jsonObject = new JSONObject(success);

             //   String user_type=jsonObject.getString("user_type");
               // Log.e("user_type",""+user_type);
                String Sch_Mem_id = jsonObject.getString("Sch_Mem_id");
                Log.e("Sch_Mem_id", "" + Sch_Mem_id);
                String Mem_Sch_Id = jsonObject.getString("Mem_Sch_Id");
                Log.e("Mem_Sch_Id", "" + Mem_Sch_Id);
                String Mem_Type=jsonObject.getString("Mem_Type");
                String Mem_Name = jsonObject.getString("Mem_Name");
                Log.e("Mem_Name",""+Mem_Name);
                String Mem_Emailid = jsonObject.getString("Mem_Emailid");
                Log.e("Mem_Emailid",""+Mem_Emailid);

                JSONObject class_data=jsonObject.getJSONObject("class_data");
                Log.e("class_data",""+class_data);
                JSONArray arr = class_data.getJSONArray("data");
                Log.e("arr",""+arr);

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    Student_Login_Data student_login_data = new Student_Login_Data();
                    student_login_data.setClass_id(obj.optString("classid"));
                    student_login_data.setClass_name(obj.optString("classname"));
                    student_login_data.setClassdesc(obj.optString("classdesc"));
                    student_login_data.setTopicname(obj.optString("topicname"));
                    student_login_data.setCla_Id(obj.optString("Cla_Id"));

                    student_login_data.setSubject(obj.optString("subject"));
                    student_login_data.setStudents(obj.optString("student"));

                    String class_id = obj.getString("classid");
                    Log.e("class_id",""+class_id);
                    String class_name = obj.getString("classname");
                    String students = obj.getString("student");
                    String Cla_Id=obj.optString("Cla_Id");
                    Log.e("Cla_Id",""+Cla_Id);

                    stulogindata.add(student_login_data);
                    mListView.setAdapter(mAdapter);

                    preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    editor = preferences.edit();
                    editor.putString("cls_clsid",Cla_Id);
                    editor.putString("class_id",class_id);
                    Log.e("prefclassid",""+Cla_Id);
                    editor.commit();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        rellogin.setPivotX(pivot.x);
        rellogin.setPivotY(pivot.y);
        rellogin.setScaleX(scaleX);
        rellogin.setScaleY(scaleY);
    }

    @Override
    public void onResume() {
        super.onResume();
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        name = preferences.getString("name","");


        password=preferences.getString("password","");
        schoolID=preferences.getString("schoolID","");
        Sch_Mem_id=preferences.getString("Sch_Mem_id","");
        Mem_Sch_Id=preferences.getString("Mem_Sch_Id","");

        new StudentLogIn().execute(name, password, schoolID);
    }



    class Student_Logout extends AsyncTask<String, Integer, String> {



        @Override
        protected String doInBackground(String... params) {

            return WSConnector.student_logout(params[0], params[1]);
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
            Log.e("Student_Logout", "" + result);

            if (result.contains("true")) {


                Intent gototeacher=new Intent(getActivity(),StudentLoginActivity.class);
                preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("remembermecheckedstu", val);
                Log.e("val",""+val);
                editor.commit();
                startActivity(gototeacher);
                getActivity().finishAffinity();






            } else if (result.contains("false")) {
                            /*AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                            alertDialog.setMessage("No data").setCancelable(false)
                                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            // TODO Auto-generated method stub
                                            dialog.dismiss();
                               *//* Intent deletetoclass=new Intent(getActivity(),ClassActivity.class);
                                startActivity(deletetoclass);
                                getActivity().finish();*//*

                                        }
                                    });

                            AlertDialog dialog = alertDialog.create();
                            dialog.show();
                            TextView messageText = (TextView) dialog
                                    .findViewById(android.R.id.message);
                            messageText.setGravity(Gravity.CENTER);*/
            }
        }

    }
}
