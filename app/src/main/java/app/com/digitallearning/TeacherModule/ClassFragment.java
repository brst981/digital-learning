package app.com.digitallearning.TeacherModule;

import android.app.ProgressDialog;
import android.content.Context;
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

    String arrId,arrName,arrChildId,arrChildNAme,className,classStudent,classSub,a,a1,a2,b,b1,b2,a3,b3,classid,c,c1,c2,style,passcode,classType,topic,description,createdby,classname,desc;
    boolean defineClass = false;
    public static String titleheader,Sch_Mem_id,Mem_Sch_Id;
    public int pos,id,updateEdit;
    int id1=16;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    RelativeLayout rellogin,relframe;
    public static String classtpye = "";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    RelativeLayout newrelative;

    ArrayList<String> newusreId, newschoolId,newclassName,newclassid,newclassSub,newclassStudent,newclassdescription;
    String name,password,schoolID;



   // ArrayList<String> passcode,classType,topic,description,arrcreatedby,arrclassname;
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
        newrelative=(RelativeLayout)rootview.findViewById(R.id.newrelative);
       /* try {
            updateEdit = getArguments().getInt("updateEdit");
            Log.e("updateEdit", "" + updateEdit);
            if (updateEdit == 20) {
                rootview = inflater.inflate(R.layout.fragment_class, container, false);
            }
        }
        catch (Exception e){}*/

        newclassName=new ArrayList<>();
        newclassdescription=new ArrayList<>();
        newclassid=new ArrayList<>();
        newclassSub=new ArrayList<>();
        newclassStudent=new ArrayList<>();
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        name = preferences.getString("name","");
        Log.e("namenxj",""+name);

        password=preferences.getString("password","");
        Log.e("password",""+password);
        schoolID=preferences.getString("schoolID","");
        Log.e("schoolID",""+schoolID);
        Sch_Mem_id=preferences.getString("Sch_Mem_id","");
        Mem_Sch_Id=preferences.getString("Mem_Sch_Id","");
        Log.e("classSchidgetArguments",""+Sch_Mem_id);
        Log.e("classmemidgetArguments",""+Mem_Sch_Id);

       // name=getArguments().getString("name");


        new TeacherLogIn().execute(name, password, schoolID);
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


         /*   arrId = getArguments().getString("arrId");
            arrName = getArguments().getString("arrName");
            arrChildId = getArguments().getString("arrChildId");
            arrChildNAme = getArguments().getString("arrChildNAme");*/

        /*passcode=new ArrayList<>();
        arrcreatedby=new ArrayList<>();
        classType=new ArrayList<>();
        topic=new ArrayList<>();
        description =new ArrayList<>();
        arrclassname =new ArrayList<>();*/


        /*className=getArguments().getString("className");
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
        Log.e("myList2",""+myList2);*/



        //  relative_header.setVisibility(View.VISIBLE);

        /*mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
               *//* Toast.makeText(MainActivity.this,"clicked"+" "+position,Toast.LENGTH_SHORT).show();*//*
                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                startActivity(intent);

            }
        }));*/
//       titleheader=newclassName.get(pos);
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
                //Toast.makeText(getActivity(),"not wrong coding",Toast.LENGTH_SHORT).show();
                pos=position;
                titleheader=newclassName.get(pos);
                EditClassFragment.singleClassID=newclassid.get(position);
                preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                editor = preferences.edit();
                editor.putString("cla_classid", newclassid.get(position));
                editor.commit();

                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                defineClass = true;
                intent.putExtra("fromClass", defineClass);
                startActivity(intent);
                getActivity().finish();

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
                id=1;
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CreateClassFragment createclassFragment = new CreateClassFragment();

                Bundle bundle=new Bundle();
                bundle.putInt("id",id);
                bundle.putInt("id1",id1);
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
            View v = LayoutInflater.from(mContext).inflate(R.layout.class_item_edit, null);
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
            text_class_name.setText(newclassName.get(position));
            TextView   text_subject_name=(TextView)convertView.findViewById(R.id.text_subject_name);
            text_subject_name.setText(newclassSub.get(position));
            TextView text_studen_name=(TextView)convertView.findViewById(R.id.text_studen_name);
            text_studen_name.setText(newclassStudent.get(position));
            TextView editbutton=(TextView)convertView.findViewById(R.id.editbutton);
            editbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos=position;
                    int fromclassfag=11;
                    Log.e("classSchid",""+Sch_Mem_id);
                    Log.e("classmemid",""+Mem_Sch_Id);
                    EditClassFragment.singleClassID=newclassid.get(position);


                    Intent intent=new Intent(getActivity(),EditClassFragment.class);
                    intent.putExtra("Sch_Mem_id",Sch_Mem_id);
                    intent.putExtra("Mem_Sch_Id",Mem_Sch_Id);
                    intent.putExtra("fromclassfag",fromclassfag);
                    Log.e("classSchidrr",""+Sch_Mem_id);
                    Log.e("classmemidrr",""+Mem_Sch_Id);
                    startActivity(intent);
                    getActivity().finish();
                                    //    new Edit_Class().execute(singleClassID,Sch_Mem_id,Mem_Sch_Id);

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
          //  Log.e("sizeqw",""+myList.size());
            return newclassName.size();
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
    class TeacherLogIn extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.login(params[0], params[1], params[2]);

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
            Log.e("REsulTinClassfragment", "" + result);
           if (result.contains("true")) {

                updateTeacherLogIn(result);


            } else if (result.contains("false")) {
                Toast.makeText(getActivity(), "Wrong Userkuiiuh", Toast.LENGTH_SHORT).show();

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

                Sch_Mem_id = jsonObject.getString("Sch_Mem_id");
                Log.e("Sch_Mem_id", " " + Sch_Mem_id);

                Mem_Sch_Id = jsonObject.getString("Mem_Sch_Id");
                Log.e("Mem_Sch_Id", " " + Mem_Sch_Id);

                String Mem_Name = jsonObject.getString("Mem_Name");
                Log.e("Mem_Name", " " + Mem_Name);

                String Mem_Emailid = jsonObject.getString("Mem_Emailid");
                Log.e("Mem_Emailid", " " + Mem_Emailid);


                JSONArray arr = jsonObject.getJSONArray("class_data");
                Log.e("arr", " " + arr);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Log.e("obj", "" + obj);
                    String class_id = obj.getString("class_id");
                    Log.e("class_id", "" + class_id);
                    String cls_createdby = obj.getString("cls_createdby");
                    Log.e("cls_createdby", "" + cls_createdby);
                    String cls_name = obj.getString("cls_name");
                    Log.e("cls_name", "" + cls_name);
                    String Cls_desc = obj.getString("Cls_desc");
                    Log.e("Cls_desc", "" + Cls_desc);
                    String subject = obj.getString("subject");
                    Log.e("subject", "" + subject);
                    String cla_classid = obj.getString("cla_classid");
                    Log.e("cla_classid", "" + cla_classid);
                    String students = obj.getString("students");
                    Log.e("students", "" + students);
                    String cls_image = obj.getString("cls_image");
                    Log.e("cls_image", "" + cls_image);
                    String orderid = obj.getString("orderid");
                    Log.e("orderid", "" + orderid);

                    String new_orderid = obj.getString("new_orderid");
                    Log.e("new_orderid", "" + new_orderid);


                    newclassName.add(cls_name);
                    Log.e("listCname",""+className);
                    newclassStudent.add(students);
                    Log.e("listCstu",""+classStudent);
                    newclassSub.add(subject);
                    Log.e("listCsub",""+classSub);
                    newclassid.add(cla_classid);
                    Log.e("ghbdjclassid",""+classid);


                }
                mListView.setAdapter(mAdapter);
                mAdapter.setMode(Attributes.Mode.Single);
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
