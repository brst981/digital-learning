package app.com.digitallearning.TeacherModule.Grade;

/**
 * Created by ${PSR} on 2/3/16.
 */

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
import android.widget.TextView;

import com.andexert.library.RippleView;

import java.util.List;

import app.com.digitallearning.R;

public class Grade_Lesson extends  Fragment {
    View rootview;
    TextView headerTitle;
    String textHeader;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.grade_lesson, container, false);
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.myrecycler);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Lesson");

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
        // List<HomeModal> homeModals;
        private Context mContext;
        private LayoutInflater inflater;
        List<String> myList;
        List<String> myList1;
        /*public MyRecyclerViewAdapter(List<String> myList,List<String> myList1) {
            this.myList = myList;
            this.myList1 = myList1;

        }*/
       /* ArrayList<String> arrayListName;
        ArrayList<String> arrQusetion;
        ArrayList<String> arrAnswer;*/
        // ArrayList<String> videoName;
      /* public MyRecyclerViewAdapter(ArrayList<String> myDataset) {
           videoName = myDataset;
           Log.e("videoNamemyDataset", "" + videoName);



       }*/


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_grade_lesson, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            //  position=0;
            // int position1 = getLayoutPosition();

            //   st=arrayListName.get(position);
            Log.e("position1", "" + position);


          /*  if(myList.get(0).contains("Art"))
            {
                Log.e("VisIT","");
                holder.img_Art.setVisibility(View.VISIBLE);
            }
            else if(myList.get(1).contains("Music")){
                Log.e("VisiT","");
                holder.img_Art.setVisibility(View.GONE);
            }*/


        }

        @Override
        public int getItemCount() {
            return 1;
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


            RippleView a3ripple;



            public ViewHolder(View itemView) {
                super(itemView);
                a3ripple = (RippleView) itemView.findViewById(R.id.a3ripple);
               // a3ripple.setOnClickListener(this);

                a3ripple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        int position = getLayoutPosition(); // gets item position
                        FragmentManager fragmentManager=getFragmentManager();
                        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                        Grade_Details grade_details=new Grade_Details();
                        fragmentTransaction.replace(R.id.container,grade_details).addToBackStack(null);
                        fragmentTransaction.commit();

                    }
                });
            }

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                int position = getLayoutPosition();
                Log.e("positionGetLay", "" + position);
                int id = v.getId();
                switch (id) {
                    case R.id.a3ripple:
                        if (position == 0) {

                            break;
                        }

                }
            }
        }
    }
}