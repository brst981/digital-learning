package app.com.digitallearning.TeacherModule.Classes;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PointF;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.andexert.library.RippleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.com.digitallearning.R;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 1/28/16.
 */
public class TopicFragment extends Fragment {
    View rootview;
    ImageView img_Art;
   // String arrName,a,b,arrId,a1,b1;
    ListView list;
   // ArrayList<String> categoryName;
    LayoutInflater inflater;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    public static String topic;
    RippleView ripple_topic_save;
    String first="Art";
    ImageView back;
    ProgressDialog dlg;
    String newTitle,semester,coursecode;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    RelativeLayout rellogin,relative_header;
    int id,fromEdit;
    int fromsave=1;
    int id1;
    ArrayList<String > arrId,arrName,arrChildId,arrChildNAme;
    final CharSequence[] art = {"Art","Theater","Visual Art"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_topic, container, false);
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.myrecycler);
        back=(ImageView) rootview.findViewById(R.id.back);
        dlg = new ProgressDialog(getActivity());
        new Before_Class_Listing().execute();
        relative_header=(RelativeLayout)rootview.findViewById(R.id.relative_header);
        imageButtonZoomIn = (ImageButton)rootview. findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) rootview.findViewById(R.id.img_zoom_out);
        rellogin=(RelativeLayout)rootview.findViewById(R.id.rellogin) ;
        ripple_topic_save=(RippleView)rootview.findViewById(R.id.ripple_topic_save);
       // id=getArguments().getInt("id");
       // if(id==1){

        try {
            id1=getArguments().getInt("id");
            if(id1==1){
                relative_header.setVisibility(View.GONE);
            }
            fromEdit = getArguments().getInt("fromEdit");
            newTitle = getArguments().getString("newTitle");
            semester = getArguments().getString("semester1");
            Log.e("semestertopic", "" + semester);
            coursecode = getArguments().getString("coursecode1");
            Log.e("coursecodetopic", "" + coursecode);
        }
        catch (Exception e){}
            /*arrName=getArguments().getString("arrName");

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

            List<String> myList = new ArrayList<String>(Arrays.asList(b.split(",")));

        Log.e("myList",""+myList);

        List<String> myList1 = new ArrayList<String>(Arrays.asList(b1.split(",")));

        Log.e("myList1",""+myList1);

            */


     //   img_Art=(ImageView)rootview.findViewById(R.id.img_Art);




        //categoryName=new ArrayList<>();


        arrId=new ArrayList<>();
        arrName=new ArrayList<>();
        arrChildId=new ArrayList<>();
        arrChildNAme=new ArrayList<>();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();

            }
        });
        ripple_topic_save.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

                if(fromEdit==12) {
                    Intent save = new Intent(getActivity(), EditClassFragment.class);
                    save.putExtra("fromsave", fromsave);
                    save.putExtra("newTitle",newTitle);
                    save.putExtra("semester",semester);
                    save.putExtra("coursecode",coursecode);
                    startActivity(save);
                    getActivity().finish();
                }
                else{
                    getFragmentManager().popBackStackImmediate();
                }
            }
        });



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
        List<String> arrId;
        List<String> arrName;
        List<String> arrChildId;
        List<String> arrChildNAme;



        public MyRecyclerViewAdapter(List<String> arrId,List<String> arrName,List<String> arrChildId,List<String> arrChildNAme) {//List<String> myList,List<String> myList1,
            this.myList = myList;
            this.myList1 = myList1;

            this.arrId = arrId;
            this.arrName = arrName;
            this.arrChildId = arrChildId;
            this.arrChildNAme = arrChildNAme;

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
            Log.e("arrName", "" + arrName);
            Log.e("arrName.get(position)", "" + arrName.get(position));

            holder.a3cat.setText(arrName.get(position));

         String art=   holder.a3cat.getText().toString();
            Log.e("StingArt",""+art);

            if(art.contains("Art")){
                Log.e("VisitArt",""+"");
            //    holder.img_Art.setVisibility(View.VISIBLE);
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
            return arrId.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder  {

            TextView a3cat;
            RippleView a3ripple;
            public  ImageView img_Art;
            CheckBox checkBox;


            public ViewHolder(View itemView) {
                super(itemView);
                a3ripple = (RippleView) itemView.findViewById(R.id.a3ripple);
              //  a3ripple.setOnClickListener(this);
                a3cat = (TextView) itemView.findViewById(R.id.a3cat);
                checkBox=(CheckBox)itemView.findViewById(R.id.checkbox);
                img_Art=(ImageView)itemView.findViewById(R.id.img_Art);

                a3ripple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        int position = getLayoutPosition(); // gets item position
                       // img_Art.setVisibility(View.VISIBLE);
                        topic=arrId.get(position);
                        EditClassFragment.newtopicsel=arrName.get(position);

                        Log.e("newtopicsel",""+EditClassFragment.newtopicsel);
                    //    CreateClassFragment.selectedtopic.setText(EditClassFragment.newtopicsel);
                        EditClassFragment.sr_topic1=arrId.get(position);
                        EditClassFragment.topic1=arrName.get(position);
                        Log.e("TopicmyList1",""+arrId);
                        Log.e("EditClassFragmenttopic",""+EditClassFragment.sr_topic1);
                        Log.e("SElected",""+topic);
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
                            EditClassFragment.topic1=arrName.get(position);
                        }

                        else if (position==2){
                          //  img_Art.setVisibility(View.VISIBLE);
                        }
                    }

                });
            }

            }
        }




    class Before_Class_Listing extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Before_Class_Listing_DoIn();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dlg.setMessage("Fetching Data.....");
            dlg.setCancelable(false);
            dlg.show();


        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dlg.dismiss();
            Log.e("REsulT", "" + result);
            if (result.contains("true")) {

                updateBefore_Class_Listing(result);


            } else if (result.contains("false")) {
                Toast.makeText(getActivity(), "Wrong User", Toast.LENGTH_SHORT).show();

            }
        }


        private void updateBefore_Class_Listing(String success) {
            try {
                JSONObject jsonObject = new JSONObject(success);
                JSONArray arr = jsonObject.getJSONArray("data");
                Log.e("Data", "" + arr);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                     Log.e("obj",""+obj);
                    String id = obj.getString("id");
                      Log.e("id", "" + id);
                    String name = obj.getString("name");
                     Log.e("name", "" + name);
                    arrId.add(id);
                      Log.e("arrId",""+arrId);
                    arrName.add(name);
                     Log.e("arrName",""+arrName);
                    Object ss = obj.get("child");
                    if (ss instanceof JSONArray) {
                        JSONArray arr1 = obj.getJSONArray("child");
                          Log.e("arr1",""+arr1);
                        if (arr1.length() != 0) {
                            for (int j = 0; j < arr1.length(); j++) {
                                JSONObject obj1 = arr1.getJSONObject(j);
                                  Log.e("obj1", "" + obj1);
                                String id1 = obj1.getString("id");
                                  Log.e("id1", "" + id1);
                                String name1 = obj1.getString("name");
                                  Log.e("name1", "" + name1);

                                arrChildId.add(id1);
                                 Log.e("arrChildId",""+arrChildId);
                                arrChildNAme.add(name1);
                                Log.e("arrChildNAme",""+arrChildNAme);
                            }
                        }
                    } else {
                        String child = obj.getString("child");
                         Log.e("Json String",""+child);
                    }
                }
                mAdapter = new MyRecyclerViewAdapter(arrId,arrName,arrChildId,arrChildNAme);
                mRecyclerView.setAdapter(mAdapter);


                /*FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ClassFragment classFragment = new ClassFragment();
                Bundle bundle = new Bundle();
                bundle.putString("arrId", String.valueOf(arrId));
                bundle.putString("userId",Sch_Mem_id);
                bundle.putString("schoolId",Mem_Sch_Id);
                bundle.putString("arrName", String.valueOf(arrName));
                bundle.putString("arrChildId", String.valueOf(arrChildId));
                bundle.putString("arrChildNAme", String.valueOf(arrChildNAme));

                bundle.putString("className", String.valueOf(className));
                bundle.putString("classStudent", String.valueOf(classStudent));
                bundle.putString("classSub", String.valueOf(classSub));
                bundle.putString("classid", String.valueOf(classid));


                Log.e("userIdSch_Mem_id", "" + Sch_Mem_id);
                Log.e("userIdMem_Sch_Id", "" + Mem_Sch_Id);

                fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                classFragment.setArguments(bundle);
                fragmentTransaction.commit();*/

            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("New exception", "" + e);
            }
        }}


    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        rellogin.setPivotX(pivot.x);
        rellogin.setPivotY(pivot.y);
        rellogin.setScaleX(scaleX);
        rellogin.setScaleY(scaleY);
    }
    }
