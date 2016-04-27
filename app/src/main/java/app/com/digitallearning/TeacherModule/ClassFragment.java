package app.com.digitallearning.TeacherModule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Classes.CreateClassFragment;
import app.com.digitallearning.TeacherModule.Classes.EditClassFragment;
import app.com.digitallearning.TeacherModule.sdlv.DifferentActivity;
import app.com.digitallearning.TeacherModule.sdlv.DigitalLearningDao;
import app.com.digitallearning.TeacherModule.sdlv.Menu;
import app.com.digitallearning.TeacherModule.sdlv.MenuItem;
import app.com.digitallearning.TeacherModule.sdlv.QQ;
import app.com.digitallearning.TeacherModule.sdlv.SlideAndDragListView;
import app.com.digitallearning.Utill.GlobalClass;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${ShalviSharma} on 12/18/15.
 */
public class ClassFragment extends Fragment implements SlideAndDragListView.OnListItemLongClickListener,
        SlideAndDragListView.OnDragListener, SlideAndDragListView.OnSlideListener,
        SlideAndDragListView.OnListItemClickListener, SlideAndDragListView.OnMenuItemClickListener,
        SlideAndDragListView.OnItemDeleteListener {
    private static final String TAG = DifferentActivity.class.getSimpleName();

   // SwipeRefreshLayout swipeRefreshLayout;
    //View rootview;
    ProgressDialog dlg;
    RippleView rippleViewCreate;
    ImageView logout;
   // ListViewAdapter mAdapter;
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
    SharedPreferences preferences,prefrencesFinal;
    SharedPreferences.Editor editor;
    RelativeLayout newrelative;
    int val=0;
    public  String Cls_desc;
    ArrayList<String> newusreId, newschoolId,newclassName,newclassid,newclassSub,newclassStudent,newclassdescription,newsubject;
    String name,password,schoolID;
    private List<Menu> mMenuList;
    private List<QQ> mQQList;
    private List<QQ> mQQListfinal;

    private SlideAndDragListView<QQ> mListView;
    View rootview;
    ArrayList<String> detail=new ArrayList<>();
    DigitalLearningDao dao;
    String classnames,subjectName,students,classids,classDescriptions;

    public static ClassFragment newInstance() {
        ClassFragment mFragment = new ClassFragment();

        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_class, container, false);
        initData();

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        //prefrencesFinal=getActivity().getPreferences()

        name = preferences.getString("name", "");

        password=preferences.getString("password", "");
        schoolID=preferences.getString("schoolID", "");
        editor=preferences.edit();
        //editor.putString("data", "syall");
        editor.commit();
        dao=new DigitalLearningDao(getActivity());

        imageButtonZoomIn = (ImageButton)rootview. findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) rootview.findViewById(R.id.img_zoom_out);
        newrelative=(RelativeLayout)rootview.findViewById(R.id.newrelative);
        logout=(ImageView)rootview.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent gototeacher=new Intent(getActivity(),TeacherLoginActivity.class);

                preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("rememberMe", val);
                Log.e("val", "" + val);
                editor.commit();
                startActivity(gototeacher);
                getActivity().finishAffinity();

                dao.deleteAll();
                dao.deleteLesson();
                dao.deleteReSOURCE();
                editor.clear().commit();


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


        /*AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
*/
       /* headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Choose Class");*/

        rellogin=(RelativeLayout)rootview.findViewById(R.id.rellogin) ;
        relative_header=(RelativeLayout)rootview.findViewById(R.id.relative_header);
        rippleViewCreate=(RippleView)rootview.findViewById(R.id.ripple_create);
       // swipeRefreshLayout = (SwipeRefreshLayout)rootview.findViewById(R.id.swipeRefreshLayout);
        //swipeRefreshLayout.setColorSchemeColors(R.color.colorlima);
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

        rippleViewCreate.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                id = 1;
                dao.deleteAll();

                for(int i=0;i<mQQList.size();i++)

                    dao.insertClasses(Integer.parseInt(mQQList.get(i).getClassId()),"","","");

                Log.e("final", "" + preferences.getString("data", ""));
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CreateClassFragment createclassFragment = new CreateClassFragment();

                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putInt("id1", id1);
                bundle.putString("arrName", String.valueOf(arrName));
                bundle.putString("arrId", arrId);
                bundle.putString("arrChildNAme", String.valueOf(arrChildNAme));
                bundle.putString("Sch_Mem_id", Sch_Mem_id);
                bundle.putString("Mem_Sch_Id", Mem_Sch_Id);
                bundle.putString("classid", classid);
                fragmentTransaction.replace(R.id.container, createclassFragment).addToBackStack(null);
                createclassFragment.setArguments(bundle);
                fragmentTransaction.commit();
            }
        });
        /*dlg=new ProgressDialog(getActivity());

        imageButtonZoomIn = (ImageButton)rootview. findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) rootview.findViewById(R.id.img_zoom_out);
        newrelative=(RelativeLayout)rootview.findViewById(R.id.newrelative);
        logout=(ImageView)rootview.findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent gototeacher=new Intent(getActivity(),TeacherLoginActivity.class);

                preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("rememberMe", val);
                Log.e("val",""+val);
                editor.commit();
                startActivity(gototeacher);
                getActivity().finishAffinity();

               *//* GlobalClass.rememberMe=false;
                getActivity().finish();*//*

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


       *//* AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Choose Class");*//*

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
    }*/
        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();
        detail.clear();
        new TeacherLogIn().execute(name, password, schoolID);

    }

    public void initData() {
        mQQList = new ArrayList<>();
        mQQListfinal = new ArrayList<>();

        /*mQQList.add(new QQ("哑ghg", "njjhk.", "12:30", R.mipmap.ic_launcher));
         mQQList.add(new QQ("Funnythan", "哑hjkk", "12:30", R.mipmap.ic_launcher));
        mQQList.add(new QQ("hjkuk", "颜bnhbn", "12:30", R.mipmap.ic_launcher));
        mQQList.add(new QQ("ghgfh", "cghgfhgfh", "12:30", R.mipmap.ic_launcher));
*/

    }

    public void initMenu() {
        mMenuList = new ArrayList<>(2);
        Menu menu0 = new Menu(false, true, 0);
        menu0.addItem(new MenuItem.Builder().setWidth((int) getResources().getDimension(R.dimen.slv_item_bg_btn2_width))
                .setBackground(new ColorDrawable(Color.parseColor("#89C139")))
                .setText("Edit")
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .setTextColor(Color.WHITE)
                .setTextSize((int) getResources().getDimension(R.dimen.txt_qq))
                .build());
        mMenuList.add(menu0);
    }

    public void initUiAndListener() {
        mListView = (SlideAndDragListView) rootview.findViewById(R.id.lv_edit);
        mListView.setMenu(mMenuList);
        mListView.setAdapter(mAdapter);
        mListView.setOnListItemLongClickListener(this);
        mListView.setOnDragListener(this, mQQList);
        mListView.setOnListItemClickListener(this);
        mListView.setOnSlideListener(this);
        mListView.setOnMenuItemClickListener(this);
        mListView.setOnItemDeleteListener(this);
        mListView.setDivider(new ColorDrawable(Color.GRAY));
        mListView.setDividerHeight(1);
    }

    private BaseAdapter mAdapter = new BaseAdapter() {
        @Override
        public int getCount() {
            return mQQList.size();
        }

        @Override
        public Object getItem(int position) {
            return mQQList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemViewType(int position) {
            if (mQQList.get(position).isQun()) {
                return 1;
            } else {
                return 0;
            }
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CustomViewHolder cvh = null;
            if (convertView == null) {
                cvh = new CustomViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.class_item_edit, null);
                cvh.txtClass = (TextView) convertView.findViewById(R.id.text_class_name);
                cvh.txtSubject = (TextView) convertView.findViewById(R.id.text_subject_name);
                cvh.txtStudentName = (TextView) convertView.findViewById(R.id.text_studen_name);


               // cvh.txtContent = (TextView) convertView.findViewById(R.id.txt_item_qq_content);
                convertView.setTag(cvh);
            } else {
                cvh = (CustomViewHolder) convertView.getTag();
            }
            QQ item = (QQ) this.getItem(position);
            cvh.txtClass.setText(item.getName());
            cvh.txtSubject.setText(item.getContent());
            cvh.txtStudentName.setText(item.getTime());
            return convertView;
        }
        class CustomViewHolder {
            public TextView txtClass,txtSubject,txtStudentName;
        }

    };

    @Override
    public void onListItemLongClick(View view, int position) {
//        boolean bool = mListView.startDrag(position);
//        Toast.makeText(DifferentActivity.this, "onItemLongClick   position--->" + position + "   drag-->" + bool, Toast.LENGTH_SHORT).show();
      //  Toast.makeText(getActivity(), "onItemLongClick   position--->" + position, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onListItemLongClick   " + position);
    }

    @Override
    public void onDragViewStart(int position) {
       // Toast.makeText(getActivity(), "onDragViewStart   position--->" + position, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onDragViewStart   " + position);
    }

    @Override
    public void onDragViewMoving(int position) {
//        Toast.makeText(DifferentActivity.this, "onDragViewMoving   position--->" + position, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onDragViewMoving   " + position);
    }

    @Override
    public void onDragViewDown(int position) {
        //Toast.makeText(getActivity(), "onDragViewDown   position--->" + position, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onDragViewDown   " + position);
    }

    @Override
    public void onListItemClick(View v, int position) {
        //Toast.makeText(getActivity(), "onItemClick   position--->" + position+      mQQList.get(position).getName()
       // ,Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onListItemClick   " + position);
        /*for(int i=0;i<mQQList.size();i++)
        {
        detail.add(mQQList.get(i).getClassId());
        }
        editor.putString("data", "" + detail);
        editor.commit();
        Log.e("final", "" + preferences.getString("data", ""));
*/

        dao.deleteAll();

        for(int i=0;i<mQQList.size();i++)

            dao.insertClasses(Integer.parseInt(mQQList.get(i).getClassId()),"","","");

        pos=position;
        titleheader=mQQList.get(position).getName();
        testing=mQQList.get(position).getClassDescription();
        subject=mQQList.get(position).getContent();


        EditClassFragment.singleClassID=mQQList.get(position).getClassId();
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor = preferences.edit();
        editor.putString("cla_classid", mQQList.get(position).getClassId());
        editor.commit();

        Intent intent = new Intent(getActivity(), NavigationActivity.class);
        defineClass = true;
        intent.putExtra("fromClass", defineClass);
        startActivity(intent);
    }

    @Override
    public void onSlideOpen(View view, View parentView, int position, int direction) {
        //Toast.makeText(getActivity(), "onSlideOpen   position--->" + position + "  direction--->" + direction, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onSlideOpen   " + position);
    }

    @Override
    public void onSlideClose(View view, View parentView, int position, int direction) {
        //Toast.makeText(getActivity(), "onSlideClose   position--->" + position + "  direction--->" + direction, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onSlideClose   " + position);
    }

    @Override
    public int onMenuItemClick(View v, int itemPosition, int buttonPosition, int direction) {
        Log.i(TAG, "onMenuItemClick   " + itemPosition + "   " + buttonPosition + "   " + direction);
        int viewType = mAdapter.getItemViewType(itemPosition);
        switch (viewType) {
            case 0:
                return clickMenuBtn0(buttonPosition, direction);
            case 1:
                return clickMenuBtn1(buttonPosition, direction);
            default:
                return Menu.ITEM_NOTHING;
        }
    }

    private int clickMenuBtn0(int buttonPosition, int direction) {
        switch (direction) {
            case MenuItem.DIRECTION_LEFT:
                switch (buttonPosition) {
                    case 0:
                        return Menu.ITEM_SCROLL_BACK;
                }
                break;
            case MenuItem.DIRECTION_RIGHT:
                switch (buttonPosition) {
                    case 0:
                        return Menu.ITEM_DELETE_FROM_BOTTOM_TO_TOP;
                    case 1:
                        return Menu.ITEM_NOTHING;
                    case 2:
                        return Menu.ITEM_SCROLL_BACK;
                }
        }
        return Menu.ITEM_NOTHING;
    }

    private int clickMenuBtn1(int buttonPosition, int direction) {
        switch (direction) {
            case MenuItem.DIRECTION_LEFT:
                switch (buttonPosition) {
                    case 0:
                        return Menu.ITEM_SCROLL_BACK;
                }
                break;
            case MenuItem.DIRECTION_RIGHT:
                switch (buttonPosition) {
                    case 0:
                        return Menu.ITEM_DELETE_FROM_BOTTOM_TO_TOP;
                    case 1:
                        return Menu.ITEM_SCROLL_BACK;
                }
        }
        return Menu.ITEM_NOTHING;
    }

    @Override
    public void onItemDelete(View view, int position) {

        pos=position;
        int fromclassfag=11;
        EditClassFragment.singleClassID=mQQList.get(position).getClassId();

        Intent intent=new Intent(getActivity(),EditClassFragment.class);
        intent.putExtra("Sch_Mem_id",Sch_Mem_id);
        intent.putExtra("Mem_Sch_Id",Mem_Sch_Id);
        intent.putExtra("fromclassfag",fromclassfag);
        startActivity(intent);
        getActivity().finish();
        GlobalClass.backPress=true;
        dao.deleteAll();

        for(int i=0;i<mQQList.size();i++)

            dao.insertClasses(Integer.parseInt(mQQList.get(i).getClassId()),"","","");


        // mQQList.remove(position - mListView.getHeaderViewsCount());
        //mAdapter.notifyDataSetChanged();
    }

    class TeacherLogIn extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            return WSConnector.login(params[0], params[1], params[2]);

        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dlg=new ProgressDialog(getActivity());
            dlg.setMessage("Loading....");
            dlg.setCancelable(false);
            dlg.show();
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dlg.dismiss();
            Log.e("classlisting", "" + result);
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
            mQQList.clear();
            try {
                ArrayList<String> classId=dao.fecthAllClasses();

                Log.e("classIdlenth",""+classId.size());
                JSONObject jsonObject = new JSONObject(success);

                Sch_Mem_id = jsonObject.getString("Sch_Mem_id");
                Mem_Sch_Id = jsonObject.getString("Mem_Sch_Id");

                String Mem_Name = jsonObject.getString("Mem_Name");
                String Mem_Emailid = jsonObject.getString("Mem_Emailid");

                JSONArray arr = jsonObject.getJSONArray("class_data");
                //dao.deleteAll();

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
                    dao.insertClasses(Integer.parseInt(cla_classid), "", "", "");

                    mQQList.add(new QQ(cls_name, subject, students, R.mipmap.ic_launcher, cla_classid, Cls_desc));


                    //mQQList.add(new QQ("哑ghg", "njjhk.", "12:30", R.mipmap.ic_launcher));
                   // mQQList.add(new QQ("Funnythan", "哑hjkk", "12:30", R.mipmap.ic_launcher));
                    //mQQList.add(new QQ("hjkuk", "颜bnhbn", "12:30", R.mipmap.ic_launcher));
                   // mQQList.add(new QQ("ghgfh", "cghgfhgfh", "12:30", R.mipmap.ic_launcher));
                }


if(mQQList.size()>classId.size()&&!preferences.getString("data","").toString().equals(""))
{
    Log.e("newadded","new");
    classId.add(mQQList.get(mQQList.size()-1).getClassId());

}

                editor.putString("data","data");
                editor.commit();
                /*classnames=mQQList.get(mQQList.size()-1).getName();

                subjectName=mQQList.get(mQQList.size()-1).getContent();
                students=mQQList.get(mQQList.size()-1).getTime();
                classids=mQQList.get(mQQList.size()-1).getClassId();
                classDescriptions=mQQList.get(mQQList.size()-1).getClassDescription();
*/

                Log.e("realll", "detailss" + preferences.getString("data", ""));
               // if(classId.size()>0)
                    //classId=dao.fecthAllClasses();
                Log.e("mQQLIST", "" + mQQList.size());
                Log.e("classId",""+classId.size());

                //String array[]=preferences.getString("data","").split(",");

                if(classId.size()>0)
                {
                    mQQListfinal.clear();
                    mQQListfinal=new ArrayList<>();

                    Log.e("sizezzz",""+classId.size());
                for(int i=0;i<classId.size();i++)
                {
                   //if(i==0)
                      // array[i]=array[i].replace("["," ");
                   // if(i==array.length-1)
                       // array[i]=array[i].replace("]"," ");
                    //Log.e("final", "" + array[i]);
                    mQQListfinal.add(mQQList.get(i));

                }
                mQQList=new ArrayList<>();
                for(int j=0;j<classId.size();j++)
                {
                    String id=classId.get(j);
                    for(int k=0;k<mQQListfinal.size();k++)
                    {
                        if(id.trim().equals(mQQListfinal.get(k).getClassId().toString().trim())) {
                            mQQList.add(new QQ(mQQListfinal.get(k).getName(), mQQListfinal.get(k).getContent(), mQQListfinal.get(k).getTime(), R.mipmap.ic_launcher, mQQListfinal.get(k).getClassId(), mQQListfinal.get(k).getClassDescription()));
                           // Log.e("idss", mQQList.get(k).getClassId());
                        }
                    }
                }}
/*
Log.e("classname",classnames);
                Log.e("subject",subjectName);
                Log.e("students",students);
                Log.e("classid",classids);
                Log.e("classid",classids);


                if(mQQList.size()>classId.size())
                {
                    mQQList.add(new QQ(classnames, subjectName, students, R.mipmap.ic_launcher, classids, classDescriptions));

                }
*/


                initMenu();
                initUiAndListener();
                Log.e("sizeee",""+mQQList.size());


               // mListView.setAdapter(mAdapter);
               // mAdapter.setMode(Attributes.Mode.Single);
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

