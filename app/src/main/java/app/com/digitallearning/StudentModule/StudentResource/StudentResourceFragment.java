package app.com.digitallearning.StudentModule.StudentResource;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;

import java.util.ArrayList;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 1/28/16.
 */
public class StudentResourceFragment extends  Fragment {
    View rootview;
    TextView headerTitle;
    String textHeader;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyRecyclerViewAdapter mAdapter;


    public static StudentResourceFragment newInstance() {
        StudentResourceFragment mFragment = new StudentResourceFragment();
        /*Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);*/
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_resource_student, container, false);
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.myrecycler);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Class Resources");


        initData();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
        });
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new MyRecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);

        return rootview;
    }

    private void initData() {
        textHeader ="sdhfygsjdgf";




    }

    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private Context mContext;
        private LayoutInflater inflater;
        ArrayList<String> arrSchoolList;
        ArrayList<String> arrSchoolImg;
        ArrayList<String> arrSchoolId;



        public MyRecyclerViewAdapter() {

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.resource_list, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {


           // holder.a3cat.setText(arrCategory.get(position));

        }

        @Override
        public int getItemCount() {
            return 5;
        }

        public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

            RippleView ripple_res;
            ImageView stresimg;
            TextView stresttext;



            public ViewHolder(View itemView) {
                super(itemView);
                ripple_res = (RippleView) itemView.findViewById(R.id.ripple_res);
                ripple_res.setOnClickListener(this);
                stresttext=(TextView) itemView.findViewById(R.id.stresttext);

                /*relativeLayout.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        int position = getLayoutPosition(); // gets item position
                        Intent i = new Intent(getActivity(), VideoViewActivity.class);
                        i.putExtra("position", "" + position);
                        startActivity(i);
                    }
                });*/
            }

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                int position = getLayoutPosition();
                Log.e("positionGetLay",""+position);
                int id = v.getId();
                switch (id){
                    case R.id.ripple_res:
                        if(position==0) {

                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            Student_Resource_Description student_resource_description = new Student_Resource_Description();
                            fragmentTransaction.replace(android.R.id.content, student_resource_description).addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                        else{
                            Toast.makeText(getActivity(),"Coming soon",Toast.LENGTH_SHORT).show();
                        }
                        break;
                }

            }
        }
    }
}
