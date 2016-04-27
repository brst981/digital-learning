package app.com.digitallearning.TeacherModule.Lessons;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
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
import java.util.List;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Classes.EditClassFragment;
import app.com.digitallearning.TeacherModule.Model.Data;
import app.com.digitallearning.TeacherModule.Model.QuizData;
import app.com.digitallearning.TeacherModule.NavigationActivity;
import app.com.digitallearning.TeacherModule.NavigationDrawerFragment;
import app.com.digitallearning.TeacherModule.sdlv.ChooseLessonList;
import app.com.digitallearning.TeacherModule.sdlv.DifferentActivity;
import app.com.digitallearning.TeacherModule.sdlv.DigitalLearningDao;
import app.com.digitallearning.TeacherModule.sdlv.Menu;
import app.com.digitallearning.TeacherModule.sdlv.MenuItem;
import app.com.digitallearning.TeacherModule.sdlv.QQ;
import app.com.digitallearning.TeacherModule.sdlv.SlideAndDragListView;
import app.com.digitallearning.Utill.GlobalClass;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${ShalviSharma} on 12/23/15.
 */
public class LessonFragment extends Fragment implements SlideAndDragListView.OnListItemLongClickListener,
        SlideAndDragListView.OnDragListener, SlideAndDragListView.OnSlideListener,
        SlideAndDragListView.OnListItemClickListener, SlideAndDragListView.OnMenuItemClickListener,
        SlideAndDragListView.OnItemDeleteListener{

    public static String LESSON_SIZE = "lesson_size";

    View rootview;
    RippleView rippleViewCreate;
    TextView headerTitle;
    String textHeader;
    //private ListView mListView;
   // ListViewAdapter mAdapter;
    SharedPreferences preferences;
    String Sch_Mem_id, cla_classid, deleteId;
    LinearLayout bottom_wrapper;
    ProgressDialog dlg;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter rAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Data> dataList = new ArrayList<Data>();
    ArrayList<QuizData> quizDatalist = new ArrayList<QuizData>();
    private List<Menu> mMenuList;
    private List<QQ> mQQList;
    private List<QQ> mQQListfinal;
    SharedPreferences.Editor editor;
    ArrayList detail=new ArrayList();

    DigitalLearningDao dao;

    private SlideAndDragListView<QQ> mListView;
    String lessonId;
    private static final String TAG = LessonFragment.class.getSimpleName();

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

        rippleViewCreate = (RippleView) rootview.findViewById(R.id.ripple_create_lesson);
        mQQList=new ArrayList<>();
        mQQListfinal=new ArrayList<>();
        dao=new DigitalLearningDao(getActivity());
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
//        NavigationActivity.mToolbar.setNavigationIcon(R.drawable.drawericon);
//        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        editor=preferences.edit();
        Sch_Mem_id = preferences.getString("Sch_Mem_id", "");
        cla_classid = preferences.getString("cla_classid", "");

        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        headerTitle.setText("Choose Lesson");
        //mListView = (SlideAndDragListView) rootview.findViewById(R.id.list_drag);
       // mAdapter = new ListViewAdapter(getActivity());

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
        init();
        initMenu();
        initUiAndListener();

       /* mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //((SwipeLayout)(mListView.getChildAt(position - mListView.getFirstVisiblePosition()))).open(true);

                Log.e("positioninLesson", "" + position + "  name" + dataList.get(position).getLessonName());

                FragmentManager fragmentManager = getFragmentManager();
                Bundle bundle = new Bundle();
                bundle.putString("positioninLesson", dataList.get(position).getLessonName());
                bundle.putString("lessonid", dataList.get(position).getLessonId());
                bundle.putString("sizeget", String.valueOf(dataList.size()));
                bundle.putSerializable("ArrayList", dataList);
                Log.e("sizeget", "" + String.valueOf(dataList.size()));
                bundle.putInt("position", position);
                bundle.putInt(LESSON_SIZE, dataList.size());
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
        });*/
        initData();


        return rootview;
    }

    @Override
    public void onResume() {
        super.onResume();
        dataList.clear();
        new Get_Lesson().execute(cla_classid, Sch_Mem_id);
        Log.e("Resume", "Resume");
    }

    private void initData() {
        textHeader = "sdhfygsjdgf";

        rippleViewCreate.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                detail=new ArrayList();

                dao.deleteLesson();

                for(int i=0;i<mQQList.size();i++)

                    dao.insertLesson(Integer.parseInt(cla_classid), mQQList.get(i).getContent(), "", "");

                //dao.insertLesson(Integer.parseInt(cla_classid), obj.getString("les_id"), "", "");

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AddLessonFragment classFragment = new AddLessonFragment();
                fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


    }


    /*class ListViewAdapter extends BaseSwipeAdapter {
        private Context mContext;

        public ListViewAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe;
        }


        @Override
        public View generateView(final int position, ViewGroup parent) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.lesson_item_list, null);
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
            v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toast.makeText(mContext, "click delete", Toast.LENGTH_SHORT).show();

                    deleteId = dataList.get(position).getLessonId();
                    new Delete_Lesson(position).execute(Sch_Mem_id, deleteId);
                }
            });

            v.findViewById(R.id.archive).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Lesson_Edit classFragment = new Lesson_Edit();
                    Bundle bundle = new Bundle();
                    bundle.putString("lessonId", dataList.get(position).getLessonId());
                    fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                    classFragment.setArguments(bundle);
                    fragmentTransaction.commit();
                }
            });
            return v;
        }

        @Override
        public void fillValues(int position, View convertView) {
            // TextView t = (TextView)convertView.findViewById(R.id.position);
            // t.setText((position + 1) + ".");
            TextView text_view = (TextView) convertView.findViewById(R.id.text_view);
            String title = dataList.get(position).getLessonName();
            text_view.setText(title);
        *//*   TextView text_view1=(TextView)convertView.findViewById(R.id.text_view1);
            text_view1.setText("Social Media:Embed");*//*
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
    }*/

    class Get_Lesson extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Get_Lesson(params[0], params[1]);

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

//           if (dlg != null)
            dlg.dismiss();
            Log.e("Get_LessonAPI", "" + result);

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

            try {

                JSONObject jsonObject = new JSONObject(success);
                ArrayList<String> classId=dao.fecthAllLesson(Integer.parseInt(cla_classid));


                Log.e("resukltttt",success);
                JSONArray arr = jsonObject.optJSONArray("data");
                Log.e("arr", " " + arr);
                GlobalClass.lessonsize = new String[arr.length()];

                mQQList.clear();
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    Data data = new Data();
                    data.setLessonId(obj.optString("les_id"));
                    data.setLessonClassId(obj.getString("Les_Cls_Id"));
                    data.setLessonName(obj.getString("lesson_name"));
                    GlobalClass.lessonsize[i] = obj.getString("lesson_name");
                    data.setLessonDate(obj.getString("les_date"));

                    data.setDescription(obj.getString("desc"));
                    data.setLessonImage(obj.getString("les_image"));
                    data.setImageThumbnail(obj.getString("img_thumb"));
                    data.setVideoUrl(obj.getString("video_url"));
                    data.setVideoThumbnail(obj.getString("video_thumb"));
                    dataList.add(data);
                    dao.insertLesson(Integer.parseInt(cla_classid), obj.getString("les_id"), "", "");

                    mQQList.add(new QQ(obj.getString("lesson_name").toString(), obj.optString("les_id"), "SDSD", R.drawable.ic_close, "dfd", "Df"));


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
                                       // mAdapter.setMode(Attributes.Mode.Single);
                }
                Log.e("calssidd",cla_classid);
                Log.e("detailss",detail.size()+"");
                if(mQQList.size()>classId.size()&&!preferences.getString("lessonDetail","").toString().equals(""))
                //if(mQQList.size()>classId.size())
                {
                    Log.e("newadded","new");
                    classId.add(mQQList.get(mQQList.size()-1).getContent());
                }
                editor.putString("lessonDetail","data");
                editor.commit();


                Log.e("realll", "detailss" + preferences.getString("data", ""));
                // if(classId.size()>0)
                //classId=dao.fecthAllClasses();
                Log.e("mQQLIST", "" + mQQList.size());
                Log.e("classId",""+classId.size());

                //String array[]=preferences.getString("data","").split(",");
                if(classId.size()!=mQQList.size())
                    new Get_Lesson().execute(cla_classid, Sch_Mem_id);


                if(classId.size()>0)
                {
                    mQQListfinal.clear();
                    mQQListfinal=new ArrayList<>();

                    Log.e("sizezzz",""+classId.size());
                    for(int i=0;i<classId.size();i++)
                    {
                        mQQListfinal.add(mQQList.get(i));

                    }
                    mQQList=new ArrayList<>();
                    for(int j=0;j<classId.size();j++)
                    {
                        String id=classId.get(j);
                        for(int k=0;k<mQQListfinal.size();k++)
                        {
                            if(id.trim().equals(mQQListfinal.get(k).getContent().toString().trim())) {
                                mQQList.add(new QQ(mQQListfinal.get(k).getName(), mQQListfinal.get(k).getContent(), "fgf", R.mipmap.ic_launcher, "gh", "ghg"));

                                // Log.e("idss", mQQList.get(k).getContent());
                            }
                        }
                    }}

                initMenu();
                initUiAndListener();
               // mListView.setAdapter(mAdapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    class Delete_Lesson extends AsyncTask<String, Integer, String> {
        private int pos;

        public Delete_Lesson(int pos) {
            this.pos = pos;

        }

        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Delete_Lesson(params[0], params[1]);

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
            Log.e("Get_LessonAPI", "" + result);

            if (result.contains("true")) {


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Lesson deleted").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                                dataList.remove(pos);
                                mQQList.remove(pos);
                               // mAdapter = new ListViewAdapter(getActivity());

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("");
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        headerTitle.setText("Choose Lesson");
    }
    public void init() {
        mQQList = new ArrayList<>();/*
        mQQList.add(new QQ("哑ghg", "njjhk.", "12:30", R.mipmap.ic_launcher,"dfd","dfd"));
         mQQList.add(new QQ("Funnythan", "哑hjkk", "12:30", R.mipmap.ic_launcher,"sdf","Df"));
        mQQList.add(new QQ("hjkuk", "颜bnhbn", "12:30", R.mipmap.ic_launcher,"df","Df"));
        mQQList.add(new QQ("ghgfh", "cghgfhgfh", "12:30", R.mipmap.ic_launcher,"Df","df"));*/

    }

    public void initMenu() {
        mMenuList = new ArrayList<>(2);
        Menu menu0 = new Menu(false, true, 0);

        menu0.addItem(new MenuItem.Builder().setWidth((int) getResources().getDimension(R.dimen.slv_item_bg_btn2_width))
                .setBackground(new ColorDrawable(Color.parseColor("#ff0000")))
                .setText("Delete")
                .setDirection(MenuItem.DIRECTION_RIGHT)
                .setTextColor(Color.WHITE)
                .setTextSize((int) getResources().getDimension(R.dimen.txt_qq))
                .build());

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
        mListView = (SlideAndDragListView) rootview.findViewById(R.id.list_drag);
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
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.lesson_item_list, null);
                cvh.txtClass = (TextView) convertView.findViewById(R.id.text_view);
                //cvh.txtSubject = (TextView) convertView.findViewById(R.id.text_subject_name);
                //cvh.txtStudentName = (TextView) convertView.findViewById(R.id.text_studen_name);
                // cvh.txtContent = (TextView) convertView.findViewById(R.id.txt_item_qq_content);
                convertView.setTag(cvh);
            } else {
                cvh = (CustomViewHolder) convertView.getTag();
            }
            QQ item = (QQ) this.getItem(position);
            cvh.txtClass.setText(item.getName());
            //cvh.txtSubject.setText(item.getContent());
           // cvh.txtStudentName.setText(item.getTime());
            return convertView;
        }
        class CustomViewHolder {
            public TextView txtClass;
        }
    };
    @Override
    public void onListItemLongClick(View view, int position) {
//        boolean bool = mListView.startDrag(position);
//        Toast.makeText(DifferentActivity.this, "onItemLongClick   position--->" + position + "   drag-->" + bool, Toast.LENGTH_SHORT).show();
       // Toast.makeText(getActivity(), "onItemLongClick   position--->" + position, Toast.LENGTH_SHORT).show();
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
       // Toast.makeText(getActivity(), "onDragViewDown   position--->" + position, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onDragViewDown   " + position);
    }

    @Override
    public void onListItemClick(View v, int position) {
       // Toast.makeText(getActivity(), "onItemClick   position--->" + position
                //,Toast.LENGTH_SHORT).show();

        Log.e("positioninLesson", "" + position + "  name" + dataList.get(position).getLessonName());
        detail=new ArrayList();

        dao.deleteLesson();

        for(int i=0;i<mQQList.size();i++)

            dao.insertLesson(Integer.parseInt(cla_classid), mQQList.get(i).getContent(), "", "");
        Log.e("final", "" + preferences.getString("lessonDetails", ""));


        FragmentManager fragmentManager = getFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString("positioninLesson", mQQList.get(position).getName());
        bundle.putString("lessonid", mQQList.get(position).getContent());
        bundle.putString("sizeget", String.valueOf(mQQList.size()));
        bundle.putSerializable("ArrayList", dataList);
        Log.e("sizeget", "" + String.valueOf(dataList.size()));
        bundle.putInt("position", position);
        bundle.putInt(LESSON_SIZE, dataList.size());
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LessonDetailFragment lessonDetailFragment = new LessonDetailFragment();
        fragmentTransaction.replace(R.id.container, lessonDetailFragment).addToBackStack(null);
        lessonDetailFragment.setArguments(bundle);
        fragmentTransaction.commit();

    }

    @Override
    public void onSlideOpen(View view, View parentView, int position, int direction) {
        //Toast.makeText(getActivity(), "onSlideOpen   position--->" + position + "  direction--->" + direction, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onSlideOpen   " + position);
    }

    @Override
    public void onSlideClose(View view, View parentView, int position, int direction) {
       // Toast.makeText(getActivity(), "onSlideClose   position--->" + position + "  direction--->" + direction, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "onSlideClose   " + position);
    }

    @Override
    public int onMenuItemClick(View v, int itemPosition, int buttonPosition, int direction) {
        Log.i(TAG, "onMenuItemClick   " + itemPosition + "   " + buttonPosition + "   " + direction);
         lessonId=mQQList.get(itemPosition).getContent();
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
                        dao.deleteLesson();

                        for(int i=0;i<mQQList.size();i++)
                            dao.insertLesson(Integer.parseInt(cla_classid), mQQList.get(i).getContent(), "", "");

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        Lesson_Edit classFragment = new Lesson_Edit();
                        Bundle bundle = new Bundle();
                        bundle.putString("lessonId", lessonId);
                        fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                        classFragment.setArguments(bundle);
                        fragmentTransaction.commit();
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
        deleteId = mQQList.get(position).getContent();
        dao.deleteLessonId(Integer.parseInt(deleteId));
        new Delete_Lesson(position).execute(Sch_Mem_id, deleteId);
        // mQQList.remove(position - mListView.getHeaderViewsCount());
        //mAdapter.notifyDataSetChanged();
    }

}
