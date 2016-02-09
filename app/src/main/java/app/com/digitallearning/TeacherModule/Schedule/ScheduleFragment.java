package app.com.digitallearning.TeacherModule.Schedule;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
import app.com.digitallearning.TeacherModule.Lessons.Lesson_Edit;

/**
 * Created by ${PSR} on 2/4/16.
 */
public class ScheduleFragment extends Fragment {
    View rootview;
    RippleView rippleViewCreate;
    TextView headerTitle;
    String textHeader;
    private ListView mListView;
    ListViewAdapter mAdapter;
    LinearLayout bottom_wrapper;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.frag_schedule, container, false);

        rippleViewCreate = (RippleView) rootview.findViewById(R.id.ripple_create_lesson);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        activity.getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Schedule");
        mListView = (ListView)rootview.findViewById(R.id.listview_archieved);
        mAdapter = new ListViewAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mAdapter.setMode(Attributes.Mode.Single);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AddSchedule addSchedule = new AddSchedule();
                fragmentTransaction.replace(R.id.container, addSchedule).addToBackStack(null);
                fragmentTransaction.commit();
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
                AddSchedule addSchedule = new AddSchedule();
                fragmentTransaction.replace(R.id.container, addSchedule).addToBackStack(null);
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
            View v = LayoutInflater.from(mContext).inflate(R.layout.list_schedule, null);
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
