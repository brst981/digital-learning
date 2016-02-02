package app.com.digitallearning.TeacherModule.Lessons;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import app.com.digitallearning.R;

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
    LinearLayout bottom_wrapper;


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

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        activity.getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Choose Lesson");
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
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), "OnItemLongClickListener", Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(),"clicked"+" "+position,Toast.LENGTH_SHORT).show();
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

    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
        // List<HomeModal> homeModals;
        private Context mContext;
        private LayoutInflater inflater;

        @Override
        public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.lesson_item_list, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;

        }

        @Override
        public void onBindViewHolder(MyRecyclerViewAdapter.ViewHolder holder, int position) {
            // holder.imageView.setImageResource(arrayListImage.get(position));
           holder.textView.setText(textHeader);
        }

        @Override
        public int getItemCount() {
            return 7;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            /* ImageView imageView;
             TextView textView;
             RippleView relativeLayout;
             LinearLayout imageButtonAnswer;
             FrameLayout frameLayout;*/
            TextView textView;
            RippleView relativeLayout;

            public ViewHolder(View itemView) {
                super(itemView);
               /* relativeLayout = (RippleView) itemView.findViewById(R.id.relative_video_it);
                imageView = (ImageView) itemView.findViewById(R.id.img_it_Video);
                textView=(TextView)itemView.findViewById(R.id.txt_it_duration);
                imageButtonAnswer=(LinearLayout)itemView.findViewById(R.id.img_answers);
                textView.setText("22m");
                frameLayout =(FrameLayout)itemView.findViewById(R.id.relative_play_img);
                frameLayout.setOnClickListener(this);

                imageButtonAnswer.setOnClickListener(this);*/

                textView=(TextView)itemView.findViewById(R.id.text_view);


               /* relativeLayout = (RippleView) itemView.findViewById(R.id.relative_video_item);
                relativeLayout.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        int position = getLayoutPosition(); // gets item position
                        Toast.makeText(getActivity(), "clicked" + " " + position, Toast.LENGTH_SHORT).show();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        LessonDetailFragment lessonDetailFragment = new LessonDetailFragment();
                        Bundle savedInstanceState = new Bundle();
                        savedInstanceState.putString("HEADER_TEXT", textHeader);
                        savedInstanceState.putInt("POSITION", position);
                        fragmentTransaction.replace(R.id.container, lessonDetailFragment).addToBackStack(null);
                        lessonDetailFragment.setArguments(savedInstanceState);
                        fragmentTransaction.commit();

                    }
                });*/
            }

            /*@Override
            public void onClick(View v) {
                int position = getLayoutPosition();
                int id = v.getId();
                switch (id){
                    case R.id.relative_play_img:
                        Intent i = new Intent(getActivity(),VideoViewActivity.class);
                        i.putExtra("position",""+position);
                        startActivity(i);
                        break;
                    case R.id.img_answers:
                        // Toast.makeText(getActivity(),"Clicked"+" "+position,Toast.LENGTH_SHORT).show();
                        if (hasCamera()) {
                            timestamp = "ic_plus_";
                            timestamp = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss")
                                    .format(Calendar.getInstance().getTime());
                            File filepath = Environment.getExternalStorageDirectory();
                            File dir = new File(filepath.getAbsolutePath() + "/Demosvideo/");
                            dir.mkdirs();

                            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                            File mediaFile = new File(Environment
                                    .getExternalStorageDirectory().getAbsolutePath()
                                    + "/Demosvideo/Video_" + timestamp + ".mp4");
                            if (mediaFile != null) {
                                Uri fileUri = Uri.fromFile(mediaFile);

                                intent.putExtra("Camera.Camera_Facing_Front", ic_plus_);
                                intent.putExtra("Camera.Camera_Facing_Back", 0);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
                                intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 102400);
                                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                                startActivityForResult(intent, VIDEO_CAPTURE);
                            }
                        } else {
                            Toast.makeText(getActivity(), "No Media player found ",
                                    Toast.LENGTH_SHORT).show();
                        }

                        break;
                }


            }*/
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
            return v;
        }

        @Override
        public void fillValues(int position, View convertView) {
            // TextView t = (TextView)convertView.findViewById(R.id.position);
            // t.setText((position + 1) + ".");
        }

        @Override
        public int getCount() {
            return 5;
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
}
