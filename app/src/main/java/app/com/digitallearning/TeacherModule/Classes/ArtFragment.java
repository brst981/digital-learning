package app.com.digitallearning.TeacherModule.Classes;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 1/28/16.
 */
public class ArtFragment extends Fragment {
    View rootview;
    ImageView img_Art;
    String arrChildNAme,a,b;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.art_layout, container, false);
        mRecyclerView=(RecyclerView)rootview.findViewById(R.id.myrecycler) ;


        arrChildNAme=getArguments().getString("arrChildNAme");
        Log.e("ART",""+arrChildNAme);


        a = arrChildNAme.replace("[", "");

        b = a.replace("]", "");

        List<String> myList = new ArrayList<String>(Arrays.asList(b.split(",")));

        Log.e("myList",""+myList);



        /*img_Art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                ArtFragment artFragment=new ArtFragment();
                fragmentTransaction.replace(R.id.container,artFragment).addToBackStack(null);
                fragmentTransaction.commit();

            }
        });*/

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
        });
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new MyRecyclerViewAdapter(myList);
        mRecyclerView.setAdapter(mAdapter);


        return rootview;
    }

    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
        // List<HomeModal> homeModals;
        private Context mContext;
        private LayoutInflater inflater;
        List<String> myList;

        public MyRecyclerViewAdapter(List<String> myList) {
            this.myList = myList;

        }
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
                    .inflate(R.layout.list_art, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            //  position=0;
            // int position1 = getLayoutPosition();

            //   st=arrayListName.get(position);
            Log.e("position1", "" + position);

            holder.a3cat.setText(myList.get(position));
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
            return myList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            TextView a3cat;
            RippleView a3ripple;
            ImageView img_Art;


            public ViewHolder(View itemView) {
                super(itemView);
                a3ripple = (RippleView) itemView.findViewById(R.id.a3ripple);
                a3ripple.setOnClickListener(this);
                a3cat = (TextView) itemView.findViewById(R.id.a3cat);
                img_Art=(ImageView)itemView.findViewById(R.id.img_Art);

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