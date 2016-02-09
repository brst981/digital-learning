package app.com.digitallearning.TeacherModule.Classes;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 1/28/16.
 */
public class TopicFragment extends Fragment {
    View rootview;
    ImageView img_Art;
    String arrName,a,b,arrChildNAme,arrId,a1,b1;
    ListView list;
   // ArrayList<String> categoryName;
    LayoutInflater inflater;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    static String topic;
    RippleView ripple_topic_save;
    String first="Art";
    ImageView back;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    RelativeLayout rellogin;
    int id;
    final CharSequence[] art = {"Theater","Art"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_topic, container, false);
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.myrecycler);
        back=(ImageView) rootview.findViewById(R.id.back);
        imageButtonZoomIn = (ImageButton)rootview. findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) rootview.findViewById(R.id.img_zoom_out);
        rellogin=(RelativeLayout)rootview.findViewById(R.id.rellogin) ;
        ripple_topic_save=(RippleView)rootview.findViewById(R.id.ripple_topic_save);
       // id=getArguments().getInt("id");
       // if(id==1){
            arrName=getArguments().getString("arrName");

            arrId=getArguments().getString("arrId");
            arrChildNAme=getArguments().getString("arrChildNAme");
            Log.e("arrName",""+arrName);
            Log.e("ChildarrId",""+arrId);
            a = arrName.replace("[", "");
            Log.e("a",""+a);
            b = a.replace("]", "");
            Log.e("b",""+b);


            a1 = arrId.replace("[", "");
            Log.e("a",""+a1);
            b1 = a1.replace("]", "");
            Log.e("b",""+b1);


     //   img_Art=(ImageView)rootview.findViewById(R.id.img_Art);




        //categoryName=new ArrayList<>();


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();

            }
        });
        ripple_topic_save.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                getFragmentManager().popBackStackImmediate();
            }
        });
       List<String> myList = new ArrayList<String>(Arrays.asList(b.split(",")));

        Log.e("myList",""+myList);

        List<String> myList1 = new ArrayList<String>(Arrays.asList(b1.split(",")));

        Log.e("myList1",""+myList1);


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


        mAdapter = new MyRecyclerViewAdapter(myList,myList1);
        mRecyclerView.setAdapter(mAdapter);

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
        return rootview;
    }

    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
        // List<HomeModal> homeModals;
        private Context mContext;
        private LayoutInflater inflater;
        List<String> myList;
        List<String> myList1;
        public MyRecyclerViewAdapter(List<String> myList,List<String> myList1) {
            this.myList = myList;
            this.myList1 = myList1;

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
                    .inflate(R.layout.list_topic, parent, false);

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

         String art=   holder.a3cat.getText().toString();
            Log.e("StingArt",""+art);

            if(art.contains("Art")){
                Log.e("VisitArt",""+"");
                holder.img_Art.setVisibility(View.VISIBLE);
            }
            /*if(holder.a3cat.getText()=="Art") {
                holder.img_Art.setVisibility(View.VISIBLE);
            }
            else {
                holder.checkBox.setVisibility(View.VISIBLE);
            }*/
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
            CheckBox checkBox;


            public ViewHolder(View itemView) {
                super(itemView);
                a3ripple = (RippleView) itemView.findViewById(R.id.a3ripple);
                a3ripple.setOnClickListener(this);
                a3cat = (TextView) itemView.findViewById(R.id.a3cat);
                checkBox=(CheckBox)itemView.findViewById(R.id.checkbox);
                img_Art=(ImageView)itemView.findViewById(R.id.img_Art);

                a3ripple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        int position = getLayoutPosition(); // gets item position
                        topic=myList1.get(position);
                        EditClassFragment.topic1=myList.get(position);
                        Log.e("TopicmyList1",""+myList1);
                        Log.e("EditClassFragmenttopic",""+EditClassFragment.topic1);
                        Log.e("SElected",""+topic);
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

                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setTitle("Make your selection");
                            builder.setItems(art, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int item) {
                                    // Do something with the selection
                                    //  mDoneButton.setText(items[item]);
                                    Log.e("items[item]",""+art[item]);
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
              //  });
                            /*FragmentManager fragmentManager=getFragmentManager();
                            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                            ArtFragment artFragment=new ArtFragment();
                            Bundle args=new Bundle();
                            args.putString("arrChildNAme",arrChildNAme);
                            fragmentTransaction.replace(R.id.container,artFragment).addToBackStack(null);
                            artFragment.setArguments(args);
                            fragmentTransaction.commit();*/

                            break;
                        }

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
