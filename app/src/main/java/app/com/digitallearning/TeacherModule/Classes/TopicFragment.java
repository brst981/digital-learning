package app.com.digitallearning.TeacherModule.Classes;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
import app.com.digitallearning.TeacherModule.Curriculum.CurriculumFragment;
import app.com.digitallearning.Utill.LogMessage;
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
    private String Other="";
    String first = "Art";
    ImageView back;
    ProgressDialog dlg;
    String newTitle, semester, coursecode, strpos;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    RelativeLayout rellogin, relative_header;
    int id, fromEdit;
    int fromsave = 1;
    String othrdes;
    int id1;
    public  static String itemart,artthe,nwstastr;
    ArrayList<String> arrId, arrName, arrChildId, arrChildNAme;
    final CharSequence[] art = {"Art", "Theater", "Visual Art"};
    int selectedItem ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_topic, container, false);
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.myrecycler);
        back = (ImageView) rootview.findViewById(R.id.back);
        dlg = new ProgressDialog(getActivity());
        new Before_Class_Listing().execute();
        relative_header = (RelativeLayout) rootview.findViewById(R.id.relative_header);
        imageButtonZoomIn = (ImageButton) rootview.findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) rootview.findViewById(R.id.img_zoom_out);
        rellogin = (RelativeLayout) rootview.findViewById(R.id.rellogin);
        ripple_topic_save = (RippleView) rootview.findViewById(R.id.ripple_topic_save);

        try {
            id = getArguments().getInt("id1");
            Log.e("idreceived", "" + id);
        } catch (Exception e) {
        }
        // if(id==1){

        try {
            id1 = getArguments().getInt("id");
            Log.e("id1", "" + id1);
            if (id1 == 1) {
                relative_header.setVisibility(View.GONE);
            }
            fromEdit = getArguments().getInt("fromEdit");
            newTitle = getArguments().getString("newTitle");
            semester = getArguments().getString("semester1");
            Log.e("semestertopic", "" + semester);
            coursecode = getArguments().getString("coursecode1");
            Log.e("coursecodetopic", "" + coursecode);
        } catch (Exception e) {
        }


        arrId = new ArrayList<>();
        arrName = new ArrayList<>();
        arrChildId = new ArrayList<>();
        arrChildNAme = new ArrayList<>();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();

            }
        });
        ripple_topic_save.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

                if (fromEdit == 12) {
                    Intent save = new Intent(getActivity(), EditClassFragment.class);
                    save.putExtra("fromsave", fromsave);
                    save.putExtra("newTitle", newTitle);
                    save.putExtra("semester", semester);
                    save.putExtra("coursecode", coursecode);
                    startActivity(save);
                    getActivity().finish();
                } else {
                    getFragmentManager().popBackStackImmediate();
                }
            }

        });


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


        public MyRecyclerViewAdapter(List<String> arrId, List<String> arrName, List<String> arrChildId, List<String> arrChildNAme) {//List<String> myList,List<String> myList1,
            this.myList = myList;
            this.myList1 = myList1;

            this.arrId = arrId;
            this.arrName = arrName;
            this.arrChildId = arrChildId;
            this.arrChildNAme = arrChildNAme;

        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder viewHolder;
            //
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_topic, parent, false);

            viewHolder = new ViewHolder(view);

            // parent.setTag(viewHolder);
//            }else{
//                viewHolder = (ViewHolder) parent.getTag();
//            }
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Log.e("arrName", "" + arrName);
            Log.e("arrName.get(position)", "" + arrName.get(position));

            Log.e("Allpos", "" + position);

            holder.a3cat.setText(arrName.get(position));
            holder.pos = position;


            if (arrName.get(position).matches("Art")) {
                holder.img_Art.setVisibility(View.VISIBLE);
                holder.artitem.setVisibility(View.VISIBLE);
                holder.checkBox.setVisibility(View.GONE);
            } else if (arrName.get(position).matches("Other")) {
                holder.img_Art.setVisibility(View.VISIBLE);
                holder.checkBox.setVisibility(View.GONE);
                holder.artitem.setVisibility(View.VISIBLE);
            } else {
                holder.img_Art.setVisibility(View.GONE);
            }
            if(position==2) {
                holder.artitem.setText(Other);
            }
            else
                holder.artitem.setText(" ");

            String art = holder.a3cat.getText().toString();
            Log.e("StingArt", "" + art);

            if (art.contains("Art")) {
                Log.e("VisitArt", "" + "");

            }
        }

        @Override
        public int getItemCount() {
            return arrId.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView a3cat;
            RippleView a3ripple;
            public ImageView img_Art;
            CheckBox checkBox;
            TextView artitem;
            int pos;
            Integer selected_position = -1;


            public ViewHolder(View itemView) {
                super(itemView);
                a3ripple = (RippleView) itemView.findViewById(R.id.a3ripple);
                //  a3ripple.setOnClickListener(this);
                a3cat = (TextView) itemView.findViewById(R.id.a3cat);
                checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
                img_Art = (ImageView) itemView.findViewById(R.id.img_Art);
                artitem = (TextView) itemView.findViewById(R.id.artitem);
                //artitem.setText(itemart);
                artitem.setText(" ");
                //int position = getLayoutPosition();
                a3ripple.setTag(pos);

                a3ripple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        int position = getLayoutPosition();
                        // int position = (int) rippleView.getTag();
                        // gets item position
                        // img_Art.setVisibility(View.VISIBLE);
                        if (arrName.get(position).matches("Art")) {


                            if (position == 0) {

                                Log.e("Position0", "" + position);
                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                builder.setTitle("Make your selection");
                                builder.setItems(art, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int item) {
                                        // Do something with the selection
                                        //  mDoneButton.setText(items[item]);
                                        selectedItem = item;
                                        Log.e("items", "" + item);
                                        itemart = String.valueOf(item);
                                        Log.e("itemart", "" + itemart);
                                        if(itemart.equalsIgnoreCase("0")){
                                            EditClassFragment.newtopicsel = "Art";
                                            Log.e("EditClassFragmenttopic", "" + EditClassFragment.newtopicsel);
                                            EditClassFragment.sr_topic1 = "1";
                                            Log.e("EditClassFragmenttopic", "" + EditClassFragment.sr_topic1);
                                            Log.e("selectd ", "hbgfh" + EditClassFragment.topic1);
                                        artitem.setText("Art");}
                                       else if(itemart.equalsIgnoreCase("1")){
                                            EditClassFragment.newtopicsel = "Theater";
                                            Log.e("EditClassFragmenttopic", "" + EditClassFragment.newtopicsel);
                                            EditClassFragment.sr_topic1 = "3";
                                            Log.e("EditClassFragmenttopic", "" + EditClassFragment.sr_topic1);
                                            artitem.setText("Theater");}
                                       else if(itemart.equalsIgnoreCase("2")){
                                            EditClassFragment.newtopicsel = "Visual Art";
                                            Log.e("EditClassFragmenttopic", "" + EditClassFragment.newtopicsel);
                                            EditClassFragment.sr_topic1 = "5";
                                            Log.e("EditClassFragmenttopic", "" + EditClassFragment.sr_topic1);
                                            artitem.setText("Visual Art");}
                                    }
                                });
                                AlertDialog alert = builder.create();
                                alert.show();

                               // EditClassFragment.topic1 = arrName.get(selectedItem);]
                             //   Log.e("selectd ", "hbgfh" + EditClassFragment.topic1);




                                if (othrdes != null) {
                                    othrdes = null;
                                    artitem.setText(" ");
                                }
                            }

                            checkBox.setVisibility(View.GONE);
                        } else if (arrName.get(position).matches("Other")) {
                            if (position == 2) {
                                Log.e("artitemText", "" + itemart);
                                if(itemart!=null){
                                    itemart=" ";
                                    Log.e("itemTextnull", "" + itemart);
                                }
                                Log.e("Text", "" + itemart);
                                Log.e("Position2", "" + position);
                                final Dialog dialog = new Dialog(getActivity());

                                //tell the Dialog to use the dialog.xml as it's layout description
                                dialog.setContentView(R.layout.popup);
                                dialog.setCancelable(false);
                                dialog.setTitle("Other");

                                final EditText txt = (EditText) dialog.findViewById(R.id.othername);
                                Button buttonOk = (Button) dialog.findViewById(R.id.btnok);


                                buttonOk.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        othrdes = txt.getText().toString();
                                        Log.e("othrdes", "" + othrdes);
                                        artitem.setText(othrdes);
                                        Other=othrdes;
                                       /* if (artitem != null) {
                                            Log.e("artitemnotnull", "" + artitem);
                                        }*/

                                        if (othrdes.equalsIgnoreCase("")) {

                                            LogMessage.showDialog(getActivity(), null,
                                                    "Please Enter Title", "OK");


                                        } else {
                                            dialog.dismiss();
                                        }

                                    }


                                });
                                dialog.show();

                            }

                            //  artitem.setText(othrdes);
                            checkBox.setVisibility(View.GONE);

                        } else {
                            checkBox.setVisibility(View.VISIBLE);

                        }

                        Log.e("position", "" + position);

                        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if(isChecked==true){
                                    Log.e("isCheckedtr",""+isChecked);
                                    checkBox.setVisibility(View.VISIBLE);

                                }
                                else if(isChecked==false) {
                                    Log.e("visit","visit");
                                    Log.e("isCheckedfal",""+isChecked);
                                    // checkBox.setBackgroundDrawable(null);
                                    //checkBox.setVisibility(View.GONE);

                                    checkBox.setButtonDrawable(null);
                                }
                            }
                        });




                        if (id == 2) {
                            Log.e("idcurr", "" + id);
                            CurriculumFragment.curriculumtopic = arrName.get(position);
                            Log.e("Curriculum.curriculumto", "" + CurriculumFragment.curriculumtopic);
                            CurriculumFragment.curriculumtopicid = arrId.get(position);
                            Log.e("Curriculum.tid", "" + CurriculumFragment.curriculumtopicid);
                        }
                        Log.e("VALUEOntext",""+EditClassFragment.topic1);
                        CreateClassFragment.selectedtopic.setText(EditClassFragment.topic1);
                        Log.e("EditClassFragmenttopic", "" + EditClassFragment.sr_topic1);
                        Log.e("vbsh",""+EditClassFragment.topic1);
                        Log.e("TopicmyList1", "" + arrId);
                        Log.e("newtopicsel", "" + EditClassFragment.newtopicsel);

                        Log.e("SElected", "" + topic);

                        if (id == 6) {
                            Log.e("idtxtnull", "" + id);
                            topic = arrId.get(position);
                            if (itemart != null) {
                                Log.e("itemartnotn", "" + itemart);
                                artthe = itemart;
                                Log.e("artthe", "" + artthe);
                            }

                            //  else
                            //  if(artthe=="") {
                            EditClassFragment.newtopicsel = arrName.get(position);
                            EditClassFragment.sr_topic1 = arrId.get(position);
                            Log.e("EditClassFragmenttopic", "" + EditClassFragment.sr_topic1);
                            Log.e("newtopicsel", "" + EditClassFragment.newtopicsel);
                            EditClassFragment.topic1 = arrName.get(position);
                            Log.e("vbsh", "" + EditClassFragment.topic1);
                            Log.e("TopicmyList1", "" + arrId);

                        }
                        nwstastr=EditClassFragment.topic1;
                        Log.e("SEnwstastr", "vallue--" + EditClassFragment.topic1);
                        try {
                            if (nwstastr.equalsIgnoreCase("Art")) {
                                Log.e("SEnwstastr", "" + nwstastr);
                                Log.e("artthe", "xgdfgd" + artthe);
                                if (artthe.equalsIgnoreCase("Art")) {
                                    EditClassFragment.newtopicsel = "Art";
                                    EditClassFragment.sr_topic1 = "1";
                                } else if (artthe.equalsIgnoreCase("Theater")) {
                                    EditClassFragment.newtopicsel = "Theater";
                                    Log.e("EditClassFragmenttopic", "" + EditClassFragment.topic1);
                                    EditClassFragment.sr_topic1 = "3";
                                    Log.e("EditClassFragmenttopic", "" + EditClassFragment.sr_topic1);
                                } else if (artthe.equalsIgnoreCase("Visual Art")) {
                                    EditClassFragment.newtopicsel = "Visual Art";
                                    EditClassFragment.sr_topic1 = "5";
                                }

                            }
                        }catch (Exception e){}
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
                    Log.e("obj", "" + obj);
                    String id = obj.getString("id");
                    Log.e("id", "" + id);
                    String name = obj.getString("name");
                    Log.e("name", "" + name);
                    arrId.add(id);
                    Log.e("arrId", "" + arrId);
                    arrName.add(name);
                    Log.e("arrName", "" + arrName);
                    Object ss = obj.get("child");
                    if (ss instanceof JSONArray) {
                        JSONArray arr1 = obj.getJSONArray("child");
                        Log.e("arr1", "" + arr1);
                        if (arr1.length() != 0) {
                            for (int j = 0; j < arr1.length(); j++) {
                                JSONObject obj1 = arr1.getJSONObject(j);
                                Log.e("obj1", "" + obj1);
                                String id1 = obj1.getString("id");
                                Log.e("id1", "" + id1);
                                String name1 = obj1.getString("name");
                                Log.e("name1", "" + name1);

                                arrChildId.add(id1);
                                Log.e("arrChildId", "" + arrChildId);
                                arrChildNAme.add(name1);
                                Log.e("arrChildNAme", "" + arrChildNAme);
                            }
                        }
                    } else {
                        String child = obj.getString("child");
                        Log.e("Json String", "" + child);
                    }
                }
                mAdapter = new MyRecyclerViewAdapter(arrId, arrName, arrChildId, arrChildNAme);
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
        }
    }


    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        rellogin.setPivotX(pivot.x);
        rellogin.setPivotY(pivot.y);
        rellogin.setScaleX(scaleX);
        rellogin.setScaleY(scaleY);
    }
}
