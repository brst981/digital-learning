package app.com.digitallearning.TeacherModule.Classes;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import app.com.digitallearning.Utill.GlobalClass;
import app.com.digitallearning.Utill.LogMessage;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 1/28/16.
 */
public class TopicFragment extends Fragment {
    static int lastSavePos = -1;

    View rootview;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    public static String topic;
    RippleView ripple_topic_save;
    private String Other = "";
    ImageView back;
    ProgressDialog dlg;
    String newTitle, semester, coursecode;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    RelativeLayout rellogin, relative_header;
    int id, fromEdit;
    int fromsave = 1;
    String othrdes, a, b;
    int id1;
    SharedPreferences pref;
    String get, loc, cheat;
    public static String itemart, artthe, again, nwstring, otherstring, otherstringid;
    ArrayList<String> arrId, arrName, arrChildId, arrChildNAme;
    boolean[] checkBoxState;
    SharedPreferences.Editor editor;
    ;

    int selectedItem;
    boolean checkboxselected;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        //  imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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
        pref = getActivity().getSharedPreferences("digitalLearning", Context.MODE_APPEND);
        editor = pref.edit();
Log.e("GlobalClass.lastValue","jkj"+GlobalClass.lastValue);
        try {
            id = getArguments().getInt("id1");
            Log.e("idreceived", "" + id);
        } catch (Exception e) {
        }
        try {


            get = getArguments().getString("get");
            Log.e("gettop", "" + get);
            Log.e("arrName", "" + arrName);
            Log.e("CONTAIN", "" + arrName.contains("get"));
        } catch (Exception e) {
        }
        try {
            if (GlobalClass.prefClear)
                editor.clear();
            editor.commit();
          //  Toast.makeText(getActivity(),"clear",Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){
          //  Toast.makeText(getActivity(),"clear1",Toast.LENGTH_SHORT).show();

            editor.clear();
            editor.commit();


        }

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

        List<String> arrId;
        List<String> arrName;
        List<String> arrChildId;
        List<String> arrChildNAme;
        String val = null;


        public MyRecyclerViewAdapter(List<String> arrId, List<String> arrName, List<String> arrChildId, List<String> arrChildNAme) {//List<String> myList,List<String> myList1,

            this.arrId = arrId;
            this.arrName = arrName;
            this.arrChildId = arrChildId;
            this.arrChildNAme = arrChildNAme;
            Log.e("arrId", " " + arrId.size());
            Log.e("arrName", " " + arrName.size());


        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            ViewHolder viewHolder;

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.list_topic, parent, false);

            viewHolder = new ViewHolder(view);


            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {


            Log.e("arrName", "" + arrName);
            Log.e("arrName.get(position)", "" + arrName.get(position));

            Log.e("Allpos", "" + position);
            try {
                Log.e("getfound", "" + get);
                Log.e("getfoundarrName", "" + arrName);


                String match = String.valueOf(get.equals(arrName.get(position)));
                if (match.equalsIgnoreCase("true")) {

                    a = String.valueOf(get.equals(arrName.get(position)));
                    Log.e("a", "" + a);
                    if (a.equalsIgnoreCase("true")) {

                        b = String.valueOf(arrName.indexOf(get));
                        Log.e("b", "" + b);
                        holder.checkBox.setChecked(true);

                    }

                }


            } catch (Exception e) {
            }


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


            if (pref.getInt("pos", 0) == position) {

                if (!pref.getString("val", "").equals("")) {
                    holder.artitem.setText(pref.getString("val", ""));

                } else {
                    holder.artitem.setText(" ");
                }
            } else {
                holder.artitem.setText(" ");
            }


            /*if(position==0) {
                holder.artitem.setText("");
                holder.artitem.setText(nwstring);
            }
            else
                holder.artitem.setText("");
            if(position==2) {
                holder.artitem.setText("");
                holder.artitem.setText(otherstring);
            }
            else
                holder.artitem.setText("");
*/

            if (position != 0 & position != 2) {
                holder.checkBox.setVisibility(View.VISIBLE);
            }

            String art = holder.a3cat.getText().toString();
            Log.e("StingArt", "" + art);

            if (art.contains("Art")) {
                Log.e("VisitArt", "" + "");

            }

            holder.checkBox.setTag(holder);
//            if (val == null)
//                holder.artitem.setText("");
//            else {
//                if (position == 0)
//                    holder.artitem.setText(val);
//                else if (position == 2)
//                    holder.artitem.setText(val);
//            }

            if (checkBoxState[position] == true)
                holder.checkBox.setChecked(true);
            else
                holder.checkBox.setChecked(false);
            holder.mainLayout.setTag(holder);
            Log.e("main", holder.mainLayout + "");
            holder.checkBox.setTag(holder);
            holder.checkBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    ViewHolder holder = (ViewHolder) v.getTag();
                    holder.a3ripple.performClick();

                    //notifyDataSetChanged();


                }

            });

            holder.a3ripple.setTag(holder);
            holder.a3ripple.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                @Override
                public void onComplete(RippleView rippleView) {

                    final ViewHolder holder = (ViewHolder) rippleView.getTag();
                    try {
                        if (lastSavePos > -1) {
                            checkBoxState[lastSavePos] = false;
                            holder.checkBox.setChecked(checkBoxState[lastSavePos]);

                        }
                    } catch (Exception e) {

                    }

                    checkBoxState[holder.pos] = true;
                    holder.checkBox.setChecked(checkBoxState[holder.pos]);

                    lastSavePos = holder.pos;

                    if (!arrName.get(holder.pos).matches("Art") && !arrName.get(holder.pos).matches("Other")){
                        editor.putInt("pos", holder.pos);
                        editor.commit();
                        notifyDataSetChanged();
                    }


                    if (arrName.get(holder.pos).matches("Art")) {

                        CharSequence[] art = {"Art", "Theater", "Visual Art"};

                        Log.e("ho", "" + arrName.get(holder.pos).matches("Art"));

                        Log.e("Position0", "" + holder.pos);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Make your selection");
                        builder.setCancelable(false);
                        builder.setItems(art, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {


                                selectedItem = item;
                                Log.e("items", "" + item);
                                itemart = String.valueOf(item);
                                Log.e("itemart", "" + itemart);
                                if (itemart.equalsIgnoreCase("0")) {
                                    notifyDataSetChanged();
                                    nwstring = "Art";
                                    val = nwstring;
                                    Log.e("nwstring", "" + nwstring);
                                    again = "1";
                                    Log.e("again", "" + again);
                                    Log.e("selectd ", "hbgfh" + EditClassFragment.topic1);
                                   /* for(int i=0;i<checkBoxState.length;i++)
                                    {
                                        checkBoxState[i]=false;
                                        notifyDataSetChanged();
                                    }*/
                                    holder.artitem.setText(val);

                                    editor.clear();

                                    editor.putInt("pos", holder.pos);
                                    editor.putString("val", val);
                                    editor.commit();
                                    GlobalClass.prefClear=false;
                                    //notifyDataSetChanged();

                                } else if (itemart.equalsIgnoreCase("1")) {
                                    notifyDataSetChanged();
                                    nwstring = "Theater";
                                    val = nwstring;
                                    Log.e("nwstring", "" + nwstring);
                                    Log.e("val", "" + val);
                                    again = "3";
                                    Log.e("again", "" + again);
                                    holder.artitem.setText(val);
                                    editor.clear();
                                    editor.putInt("pos", holder.pos);
                                    editor.putString("val", val);
                                    editor.commit();
                                    GlobalClass.prefClear=false;

                                } else if (itemart.equalsIgnoreCase("2")) {
                                    notifyDataSetChanged();
                                    nwstring = "Visual Art";
                                    val = nwstring;
                                    Log.e("nwstring", "" + nwstring);
                                    again = "5";
                                    Log.e("again", "" + again);
                                    holder.artitem.setText(val);
                                    editor.clear();
                                    editor.putInt("pos", holder.pos);
                                    editor.putString("val", val);
                                    editor.commit();
                                    GlobalClass.prefClear=false;

                                }

                                if (id == 6) {
                                    EditClassFragment.sr_topic1 = again;
                                    Log.e("nwstring", "dd" + nwstring);
                                    EditClassFragment.topic1 = nwstring;


                                    topic = nwstring;
                                    Log.e("SElected", "" + topic);

                                    EditClassFragment.topic1 = nwstring;
                                    Log.e("name", "" + EditClassFragment.topic1);

                                    EditClassFragment.sr_topic1 = again;
                                    Log.e("id", "" + EditClassFragment.sr_topic1);
                                }
                                if (id == 2) {

                                    CurriculumFragment.curriculumtopic = nwstring;//name
                                    Log.e("Currinwstringd", "" + CurriculumFragment.curriculumtopic);
                                    CurriculumFragment.curriculumtopicid = again;  //id
                                    Log.e("Currtopicidd", "" + CurriculumFragment.curriculumtopicid);
                                }
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();

                    } else if (arrName.get(holder.pos).matches("Other")) {


                        Log.e("postionn", "" + holder.pos + " " + lastSavePos);

                        Log.e("Position2", "" + holder.pos);
                        final Dialog dialog = new Dialog(getActivity());

                        dialog.setContentView(R.layout.popup);
                        dialog.setCancelable(false);
                        dialog.setTitle("Other");

                        final EditText txt = (EditText) dialog.findViewById(R.id.othername);
                        Button buttonOk = (Button) dialog.findViewById(R.id.btnok);


                        buttonOk.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                notifyDataSetChanged();

                                othrdes = txt.getText().toString();
                                Log.e("othrdes", "" + othrdes);
                                holder.artitem.setText(othrdes);
                                Other = othrdes;
                                GlobalClass.prefClear=false;

                                otherstring = othrdes;
                                editor.clear();
                                editor.putInt("pos", holder.pos);
                                editor.putString("val", othrdes);
                                editor.commit();
                                if (othrdes.equalsIgnoreCase("")) {

                                    LogMessage.showDialog(getActivity(), null,
                                            "Please Enter Title", "OK");

                                } else {

                                    otherstring = othrdes;
                                    otherstringid = "9";
                                    if (id == 6) {
                                        EditClassFragment.sr_topic1 = otherstringid;
                                        Log.e("otherstringid", "" + EditClassFragment.sr_topic1);
                                        EditClassFragment.topic1 = otherstring;
                                    }

                                    topic = otherstringid;
                                    Log.e("SElectedother", "" + topic);
                                    if (id == 2) {
                                        CurriculumFragment.curriculumtopic = otherstring;
                                        Log.e("Currinwstringd", "" + CurriculumFragment.curriculumtopic);
                                        CurriculumFragment.curriculumtopicid = otherstringid;
                                        Log.e("Currtopicidd", "" + CurriculumFragment.curriculumtopicid);
                                    }

                                    dialog.dismiss();
                                }

                            }


                        });
                        dialog.show();

                    }

                    Log.e("position", "" + holder.pos);


                    if (id == 2) {
                        Log.e("idcurr", "" + id);

                        CurriculumFragment.curriculumtopic = arrName.get(holder.pos);
                        Log.e("Curriculum.curriculumto", "" + CurriculumFragment.curriculumtopic);
                        CurriculumFragment.curriculumtopicid = arrId.get(holder.pos);
                        Log.e("Curriculum.tid", "" + CurriculumFragment.curriculumtopicid);


                        if (CurriculumFragment.curriculumtopic.equalsIgnoreCase("Art")) {

                            Log.e("bhejfkif", "FBRJKFN");
                            CurriculumFragment.curriculumtopic = nwstring;//name
                            Log.e("Currinwstring", "dd" + CurriculumFragment.curriculumtopic);
                            CurriculumFragment.curriculumtopicid = again;  //id
                            Log.e("Currtopicid", "" + CurriculumFragment.curriculumtopicid);

                        } else if (CurriculumFragment.curriculumtopic.equalsIgnoreCase("Other")) {

                            CurriculumFragment.curriculumtopic = otherstring;
                            Log.e("curriotherstring", "" + CurriculumFragment.curriculumtopic);
                            CurriculumFragment.curriculumtopicid = otherstringid;
                            Log.e("curriotherstringid", "" + CurriculumFragment.curriculumtopicid);

                        }


                    }
                    Log.e("VALUEOntext", "" + EditClassFragment.topic1);
                    Log.e("EditClassFragmenttopic", "" + EditClassFragment.sr_topic1);
                    Log.e("vbsh", "" + EditClassFragment.topic1);
                    Log.e("TopicmyList1", "" + arrId);
                    Log.e("newtopicsel", "" + EditClassFragment.newtopicsel);


                    if (id == 6) {
                        Log.e("idtxtnull", "" + id);
                        topic = arrId.get(holder.pos);
                        if (itemart != null) {
                            Log.e("itemartnotn", "" + itemart);
                            artthe = itemart;
                            Log.e("artthe", "" + artthe);
                        }


                        Log.e("itemartnotnullEdit", "" + itemart);

                        EditClassFragment.newtopicsel = arrName.get(holder.pos);

                        Log.e("EditClassFragmenttopic", "" + EditClassFragment.sr_topic1);
                        Log.e("newtopicsel", "" + EditClassFragment.newtopicsel);
                        EditClassFragment.topic1 = arrName.get(holder.pos);
                        Log.e("vbsh", "" + EditClassFragment.topic1);
                        Log.e("TopicmyList1", "" + arrId);
                        EditClassFragment.sr_topic1 = arrId.get(holder.pos);
                        if (EditClassFragment.newtopicsel.equalsIgnoreCase("Art")) {

                            Log.e("bhejfkif", "FBRJKFN");
                            EditClassFragment.newtopicsel = nwstring;//name
                            Log.e("nwstring", "dd" + nwstring);
                            EditClassFragment.topic1 = again;  //id
                            topic = nwstring;

                            Log.e("SElected", "" + topic);


                            EditClassFragment.topic1 = nwstring;
                            Log.e("name", "" + EditClassFragment.topic1);

                            EditClassFragment.sr_topic1 = again;
                            Log.e("id", "" + EditClassFragment.sr_topic1);
                        } else if (EditClassFragment.newtopicsel.equalsIgnoreCase("Other")) {

                            EditClassFragment.sr_topic1 = otherstringid;
                            Log.e("otherstringid", "" + EditClassFragment.sr_topic1);
                            EditClassFragment.topic1 = otherstring;
                            topic = otherstring;
                            Log.e("SElectedother", "" + topic);
                        }
                    }
                    //notifyDataSetChanged();
                }


            });

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
            RelativeLayout mainLayout;
            int pos;


            public ViewHolder(View itemView) {
                super(itemView);
                a3ripple = (RippleView) itemView.findViewById(R.id.a3ripple);
                a3cat = (TextView) itemView.findViewById(R.id.a3cat);
                checkBox = (CheckBox) itemView.findViewById(R.id.checkbox);
                img_Art = (ImageView) itemView.findViewById(R.id.img_Art);
                artitem = (TextView) itemView.findViewById(R.id.artitem);

                mainLayout = (RelativeLayout) itemView.findViewById(R.id.relative_class);
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
                checkBoxState = new boolean[arr.length()];

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
                    Log.e("GlobalClass", "global" + GlobalClass.lastValue);
                    Log.e("namekl", "name" + name);


                    try {
                        if (GlobalClass.lastValue.equals(name)) {

                            checkBoxState[i] = true;

                        } else
                            checkBoxState[i] = false;
                    } catch (Exception e) {
                    }
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
                        Log.e("JsonString", "" + child);
                    }
                }
                mAdapter = new MyRecyclerViewAdapter(arrId, arrName, arrChildId, arrChildNAme);
                mRecyclerView.setAdapter(mAdapter);

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

    @Override
    public void onStart() {
        super.onStart();
    }
}
