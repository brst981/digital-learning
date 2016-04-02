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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
import app.com.digitallearning.Utill.GlobalClass;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${ShalviSharma} on 12/18/15.
 */
public class ClassFragment extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    View rootview;
    ProgressDialog dlg;
    RippleView rippleViewCreate;
    ImageView logout;
    private ListView mListView;
    ListViewAdapter mAdapter;
    public static RelativeLayout relative_header;
    TextView headerTitle;

    String arrId,arrName,arrChildId,arrChildNAme,className,classStudent,classSub,a,a1,a2,b,b1,b2,a3,b3,classid,c,c1,c2,style,passcode,classType,topic,description,createdby,classname,desc;
    boolean defineClass = false;
    public static String titleheader,Sch_Mem_id,Mem_Sch_Id,testing,subject,nameclass;
    public int pos,id,updateEdit;
    int id1=16;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    RelativeLayout rellogin,relframe;
    public static String classtpye = "";
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    RelativeLayout newrelative;

    public  String Cls_desc;
    ArrayList<String> newusreId, newschoolId,newclassName,newclassid,newclassSub,newclassStudent,newclassdescription,newsubject;
    String name,password,schoolID;

    public static ClassFragment newInstance() {
        ClassFragment mFragment = new ClassFragment();

        return mFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_class, container, false);
        dlg=new ProgressDialog(getActivity());

        imageButtonZoomIn = (ImageButton)rootview. findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) rootview.findViewById(R.id.img_zoom_out);
        newrelative=(RelativeLayout)rootview.findViewById(R.id.newrelative);
        logout=(ImageView)rootview.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GlobalClass.rememberMe=false;
                getActivity().finish();
            }
        });

        GlobalClass.lastValue=" ";

        newclassName=new ArrayList<>();
        newclassdescription=new ArrayList<>();
        newclassid=new ArrayList<>();
        newclassSub=new ArrayList<>();
        newclassStudent=new ArrayList<>();
        newsubject=new ArrayList<>();
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        name = preferences.getString("name","");


        password=preferences.getString("password","");
        schoolID=preferences.getString("schoolID","");
        Sch_Mem_id=preferences.getString("Sch_Mem_id","");
        Mem_Sch_Id=preferences.getString("Mem_Sch_Id","");


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

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

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

                return false;
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                pos=position;
                titleheader=newclassName.get(pos);
                testing=newclassdescription.get(pos);
                subject=newsubject.get(pos);


                EditClassFragment.singleClassID=newclassid.get(position);
                preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                editor = preferences.edit();
                editor.putString("cla_classid", newclassid.get(position));
                editor.commit();

                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                defineClass = true;
                intent.putExtra("fromClass", defineClass);
                startActivity(intent);
              //  getActivity().finish();

            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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

                }
            });
            v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
            return v;
        }

        @Override
        public void fillValues(final int position, View convertView) {
            // TextView t = (TextView)convertView.findViewById(R.id.position);
            // t.setText((position + 1) + ".");
           TextView text_class_name=(TextView)convertView.findViewById(R.id.text_class_name);
            nameclass=newclassName.get(position);
            text_class_name.setText(nameclass);
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
                    EditClassFragment.singleClassID=newclassid.get(position);

                    Intent intent=new Intent(getActivity(),EditClassFragment.class);
                    intent.putExtra("Sch_Mem_id",Sch_Mem_id);
                    intent.putExtra("Mem_Sch_Id",Mem_Sch_Id);
                    intent.putExtra("fromclassfag",fromclassfag);
                    startActivity(intent);
                    getActivity().finish();
                    GlobalClass.backPress=true;

                }
            });



        }

        @Override
        public int getCount() {
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
            Log.e("classlisting",""+result);
           if (result.contains("true")) {

                updateTeacherLogIn(result);


            } else if (result.contains("false")) {


           }
        }


        private void updateTeacherLogIn(String success) {


            newclassName.clear();
            newclassStudent.clear();
            newclassSub.clear();
            newclassid.clear();
            newclassdescription.clear();
            newsubject.clear();
            try {

                JSONObject jsonObject = new JSONObject(success);

                Sch_Mem_id = jsonObject.getString("Sch_Mem_id");
                Mem_Sch_Id = jsonObject.getString("Mem_Sch_Id");

                String Mem_Name = jsonObject.getString("Mem_Name");
                String Mem_Emailid = jsonObject.getString("Mem_Emailid");

                JSONArray arr = jsonObject.getJSONArray("class_data");

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    String class_id = obj.getString("class_id");
                    String cls_createdby = obj.getString("cls_createdby");
                    String cls_name = obj.getString("cls_name");
                     Cls_desc = obj.getString("Cls_desc");
                    String subject = obj.getString("subject");
                    String cla_classid = obj.getString("cla_classid");
                    String students = obj.getString("students");
                    String cls_image = obj.getString("cls_image");
                    String orderid = obj.getString("orderid");
                    String new_orderid = obj.getString("new_orderid");

                    newclassName.add(cls_name);
                    newclassStudent.add(students);
                    newclassSub.add(subject);
                    newclassid.add(cla_classid);
                    newclassdescription.add(Cls_desc);
                    newsubject.add(subject);


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

    @Override
    public void onResume() {
        super.onResume();
        new TeacherLogIn().execute(name, password, schoolID);
    }
}
