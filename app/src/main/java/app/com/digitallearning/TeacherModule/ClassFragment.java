package app.com.digitallearning.TeacherModule;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageButton;
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
import java.util.Arrays;
import java.util.List;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Classes.CreateClassFragment;
import app.com.digitallearning.TeacherModule.Classes.EditClassFragment;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${ShalviSharma} on 12/18/15.
 */
public class ClassFragment extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    View rootview;
    ProgressDialog dlg;
    RippleView rippleViewCreate;
    private ListView mListView;
    ListViewAdapter mAdapter;
    public static RelativeLayout relative_header;
    TextView headerTitle;
    List<String> myList,myList1,myList2,myListclassId;
    String arrId,arrName,arrChildId,arrChildNAme,className,classStudent,classSub,a,a1,a2,b,b1,b2,a3,b3,classid,c,c1,c2,singleClassID;
    boolean defineClass = false;
    public static String titleheader,Sch_Mem_id,Mem_Sch_Id;
    public int pos;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    RelativeLayout rellogin;

   public static String classtpye = "";

    ArrayList<String> passcode,classType,topic,description;
    public static ClassFragment newInstance() {
        ClassFragment mFragment = new ClassFragment();
        /*Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);*/
        return mFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_class, container, false);
        dlg=new ProgressDialog(getActivity());
        imageButtonZoomIn = (ImageButton)rootview. findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) rootview.findViewById(R.id.img_zoom_out);
       /* AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Choose Class");*/
        rellogin=(RelativeLayout)rootview.findViewById(R.id.rellogin) ;
        relative_header=(RelativeLayout)rootview.findViewById(R.id.relative_header);
        rippleViewCreate=(RippleView)rootview.findViewById(R.id.ripple_create);
        swipeRefreshLayout = (SwipeRefreshLayout)rootview.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorlima);
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

        arrId=getArguments().getString("arrId");
        arrName=getArguments().getString("arrName");
        arrChildId=getArguments().getString("arrChildId");
        arrChildNAme=getArguments().getString("arrChildNAme");

        className=getArguments().getString("className");
        Log.e("className",""+className);
        classStudent=getArguments().getString("classStudent");
        Log.e("classStudent",""+classStudent);
        classSub=getArguments().getString("classSub");
        Log.e("classSub",""+classSub);

        classid=getArguments().getString("classid");
        a3 = classid.replace("[", "");
        Log.e("a3",""+a3);
        b3 = a3.replace("]", "");
        Log.e("b3",""+b3);

        passcode=new ArrayList<>();
        classType=new ArrayList<>();
        topic=new ArrayList<>();
        description =new ArrayList<>();
        myListclassId = new ArrayList<String>(Arrays.asList(b3.split(",")));

        Log.e("myListclassId",""+myListclassId);


        a = className.replace("[", "");
        Log.e("a",""+a);
        b = a.replace("]", "");
        Log.e("b",""+b);


        a1 = classStudent.replace("[", "");
        Log.e("a",""+a1);
        b1 = a1.replace("]", "");
        Log.e("b",""+b1);


        a2 = classSub.replace("[", "");
        Log.e("a",""+a2);
        b2 = a2.replace("]", "");
        Log.e("b",""+b2);


         myList = new ArrayList<String>(Arrays.asList(b.split(",")));
        Log.e("myList",""+myList);

        myList1 = new ArrayList<String>(Arrays.asList(b1.split(",")));
        Log.e("myList1",""+myList1);

         myList2 = new ArrayList<String>(Arrays.asList(b2.split(",")));
        Log.e("myList2",""+myList2);


        Sch_Mem_id=getArguments().getString("userId");
        Mem_Sch_Id=getArguments().getString("schoolId");


        //  relative_header.setVisibility(View.VISIBLE);

        /*mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
               *//* Toast.makeText(MainActivity.this,"clicked"+" "+position,Toast.LENGTH_SHORT).show();*//*
                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                startActivity(intent);

            }
        }));*/
        titleheader=myList.get(pos);
        Log.e("ISDefined",""+titleheader);
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
       /* mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "OnItemLongClickListener", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(),"clicked"+" "+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                defineClass = true;
                intent.putExtra("fromClass", defineClass);
                startActivity(intent);

                *//*FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ClassesDetailFragment classDetailFragment = new ClassesDetailFragment();
                fragmentTransaction.replace(R.id.container, classDetailFragment).addToBackStack(null);
                fragmentTransaction.commit();*//*


                return true;
            }
        });*/
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos=position;
                titleheader=myList.get(pos);
                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                defineClass = true;
                intent.putExtra("fromClass", defineClass);
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




        rippleViewCreate.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CreateClassFragment createclassFragment = new CreateClassFragment();

                Bundle bundle=new Bundle();
                bundle.putString("arrName",String.valueOf(arrName));
                bundle.putString("arrId",arrId);
                bundle.putString("arrChildNAme",String.valueOf(arrChildNAme));
                bundle.putString("Sch_Mem_id",Sch_Mem_id);
                bundle.putString("Mem_Sch_Id",Mem_Sch_Id);
                bundle.putString("classid",classid);
                fragmentTransaction.replace(R.id.container, createclassFragment).addToBackStack(null);
                createclassFragment.setArguments(bundle);
                fragmentTransaction.commit();
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
            v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "click delete", Toast.LENGTH_SHORT).show();
                }
            });
            return v;
        }

        @Override
        public void fillValues(final int position, View convertView) {
            // TextView t = (TextView)convertView.findViewById(R.id.position);
            // t.setText((position + 1) + ".");
           TextView text_class_name=(TextView)convertView.findViewById(R.id.text_class_name);
            text_class_name.setText(myList.get(position));
            TextView   text_subject_name=(TextView)convertView.findViewById(R.id.text_subject_name);
            text_subject_name.setText(myList2.get(position));
            TextView text_studen_name=(TextView)convertView.findViewById(R.id.text_studen_name);
            text_studen_name.setText(myList1.get(position));
            TextView editbutton=(TextView)convertView.findViewById(R.id.editbutton);
            editbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos=position;
                    singleClassID=myListclassId.get(position);

                    new Edit_Class().execute(singleClassID,Sch_Mem_id,Mem_Sch_Id);

                    /*FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    EditClassFragment classFragment = new EditClassFragment();
                    Bundle arg=new Bundle();
                    arg.putString("title",myList.get(position));
                    Log.e("titleSend",""+myList.get(position));
                    arg.putString("myListclassId",myListclassId.get(position));
                    fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                    classFragment.setArguments(arg);
                    fragmentTransaction.commit();
                    titleheader=myList.get(position);
                    Log.e("StaticTitle",""+titleheader);*/

                }
            });



        }

        @Override
        public int getCount() {
            return myList.size();
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


    class Edit_Class extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.edit_Class(params[0], params[1], params[2]);

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
            Log.e("REsulT", "" + result);
            if (result.contains("true")) {

                updateTeacherLogIn(result);


            } else if (result.contains("false")) {
                Toast.makeText(getActivity(), "Wrong User", Toast.LENGTH_SHORT).show();

            }
        }
//{"success":true,"data":[{"clsid":"183599","createdby":"2155","schoolid":"487","classid":"1800","classname":"1",
// "classpassword":"","style":"2","topic_id":"12","topic_name":"Training ","desc":"Test","class_full_image":"",
// "class_thumb_image":"","Cls_Features":{"enable_calendar":"","enable_students_tab":"","enable_chat":"","enable_grades":""}}]},

        private void updateTeacherLogIn(String success) {

            try {

                JSONObject jsonObject = new JSONObject(success);
                Log.e("jsonObject", "" + jsonObject);

                JSONArray arr = jsonObject.getJSONArray("data");
                Log.e("arr", " " + arr);
                for (int i = 0; i <= arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Log.e("obj", "" + obj);
                    String clsid = obj.getString("clsid");
                    Log.e("clsid", "" + clsid);
                    String createdby = obj.getString("createdby");
                    Log.e("createdby", "" + createdby);
                    String schoolid = obj.getString("schoolid");
                    Log.e("schoolid", "" + schoolid);
                    String classid = obj.getString("classid");
                    Log.e("classid", "" + classid);
                    String classname = obj.getString("classname");
                    Log.e("classname", "" + classname);
                    String classpassword = obj.getString("classpassword");
                    Log.e("classpassword", "" + classpassword);
                    String style = obj.getString("style");
                    Log.e("style", "" + style);
                    String topic_id = obj.getString("topic_id");
                    Log.e("topic_id", "" + topic_id);
                    String topic_name = obj.getString("topic_name");
                    Log.e("topic_name", "" + topic_name);
                    String desc = obj.getString("desc");
                    Log.e("desc", "" + desc);
                    String class_full_image = obj.getString("class_full_image");
                    Log.e("class_full_image", "" + class_full_image);
                    String class_thumb_image = obj.getString("class_thumb_image");
                    Log.e("class_thumb_image", "" + class_thumb_image);


                    JSONObject obj1=obj.getJSONObject("Cls_Features"); {
                        Log.e("OBJ1",""+obj1);
                        String enable_calendar = obj1.getString("enable_calendar");
                        Log.e("enable_calendar", "" + enable_calendar);
                        String enable_students_tab = obj1.getString("enable_students_tab");
                        Log.e("enable_students_tab", "" + enable_students_tab);
                        String enable_chat = obj1.getString("enable_chat");
                        Log.e("enable_chat", "" + enable_chat);
                        String enable_grades = obj1.getString("enable_grades");
                        Log.e("enable_grades", "" + enable_grades);

                        passcode.add(clsid);
                        classType.add(style);
                        topic.add(topic_name);
                        description.add(desc);



                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        EditClassFragment classFragment = new EditClassFragment();
                        Bundle arg=new Bundle();
                        arg.putString("title",myList.get(pos));
                        Log.e("titleSend",""+myList.get(pos));

                        arg.putString("myListclassId",myListclassId.get(pos));
                        Log.e("titleSend",""+myList.get(pos));

                        arg.putString("passcode",passcode.get(pos));
                        Log.e("passcode",""+passcode.get(pos));

                        arg.putString("classType",classType.get(pos));
                        Log.e("classType",""+classType.get(pos));

                        arg.putString("topic",topic.get(pos));
                        Log.e("topic",""+topic.get(pos));

                        arg.putString("description",description.get(pos));
                        Log.e("description",""+description.get(pos));

                        fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                        classFragment.setArguments(arg);
                        fragmentTransaction.commit();
                        titleheader=myList.get(pos);
                        Log.e("StaticTitle",""+titleheader);
                    }


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

}
