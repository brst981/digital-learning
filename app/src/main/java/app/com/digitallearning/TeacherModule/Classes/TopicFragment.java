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

        try {
            id = getArguments().getInt("id1");
        } catch (Exception e) {
        }
        try {


            get = getArguments().getString("get");

        } catch (Exception e) {
        }
        try {
            if (GlobalClass.prefClear)
                editor.clear();
            editor.commit();

        }
        catch(Exception e){

            editor.clear();
            editor.commit();


        }

        try {
            id1 = getArguments().getInt("id");
            if (id1 == 1) {
                relative_header.setVisibility(View.GONE);
            }
            fromEdit = getArguments().getInt("fromEdit");
            newTitle = getArguments().getString("newTitle");
            semester = getArguments().getString("semester1");
            coursecode = getArguments().getString("coursecode1");
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



            try {

                String match = String.valueOf(get.equals(arrName.get(position)));
                if (match.equalsIgnoreCase("true")) {

                    a = String.valueOf(get.equals(arrName.get(position)));

                    if (a.equalsIgnoreCase("true")) {

                        b = String.valueOf(arrName.indexOf(get));
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

            if (position != 0 & position != 2) {
                holder.checkBox.setVisibility(View.VISIBLE);
            }

            String art = holder.a3cat.getText().toString();

            if (art.contains("Art")) {

            }

            holder.checkBox.setTag(holder);


            if (checkBoxState[position] == true)
                holder.checkBox.setChecked(true);
            else
                holder.checkBox.setChecked(false);
            holder.mainLayout.setTag(holder);
            holder.checkBox.setTag(holder);
            holder.checkBox.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    ViewHolder holder = (ViewHolder) v.getTag();
                    holder.a3ripple.performClick();



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

                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle("Make your selection");
                        builder.setCancelable(false);
                        builder.setItems(art, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int item) {


                                selectedItem = item;

                                itemart = String.valueOf(item);
                                if (itemart.equalsIgnoreCase("0")) {
                                    notifyDataSetChanged();
                                    nwstring = "Art";
                                    val = nwstring;
                                    again = "1";

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

                                    again = "3";
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
                                    again = "5";
                                    holder.artitem.setText(val);
                                    editor.clear();
                                    editor.putInt("pos", holder.pos);
                                    editor.putString("val", val);
                                    editor.commit();
                                    GlobalClass.prefClear=false;

                                }

                                if (id == 6) {
                                    EditClassFragment.sr_topic1 = again;
                                    EditClassFragment.topic1 = nwstring;


                                    topic = nwstring;

                                    EditClassFragment.topic1 = nwstring;
                                    EditClassFragment.sr_topic1 = again;
                                }
                                if (id == 2) {

                                    CurriculumFragment.curriculumtopic = nwstring;//name
                                    CurriculumFragment.curriculumtopicid = again;  //id
                                }
                            }
                        });
                        AlertDialog alert = builder.create();
                        alert.show();

                    } else if (arrName.get(holder.pos).matches("Other")) {


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
                                        EditClassFragment.topic1 = otherstring;
                                    }

                                    topic = otherstringid;
                                    if (id == 2) {
                                        CurriculumFragment.curriculumtopic = otherstring;
                                        CurriculumFragment.curriculumtopicid = otherstringid;

                                    }

                                    dialog.dismiss();
                                }

                            }


                        });
                        dialog.show();

                    }




                    if (id == 2) {

                        CurriculumFragment.curriculumtopic = arrName.get(holder.pos);
                        CurriculumFragment.curriculumtopicid = arrId.get(holder.pos);


                        if (CurriculumFragment.curriculumtopic.equalsIgnoreCase("Art")) {

                            CurriculumFragment.curriculumtopic = nwstring;//name
                            CurriculumFragment.curriculumtopicid = again;  //id

                        } else if (CurriculumFragment.curriculumtopic.equalsIgnoreCase("Other")) {

                            CurriculumFragment.curriculumtopic = otherstring;
                            CurriculumFragment.curriculumtopicid = otherstringid;

                        }


                    }

                    if (id == 6) {

                        topic = arrId.get(holder.pos);
                        if (itemart != null) {

                            artthe = itemart;

                        }

                        EditClassFragment.newtopicsel = arrName.get(holder.pos);


                        EditClassFragment.topic1 = arrName.get(holder.pos);
                        EditClassFragment.sr_topic1 = arrId.get(holder.pos);
                        if (EditClassFragment.newtopicsel.equalsIgnoreCase("Art")) {

                            EditClassFragment.newtopicsel = nwstring;//name
                            EditClassFragment.topic1 = again;  //id
                            topic = nwstring;

                            EditClassFragment.topic1 = nwstring;

                            EditClassFragment.sr_topic1 = again;
                        } else if (EditClassFragment.newtopicsel.equalsIgnoreCase("Other")) {

                            EditClassFragment.sr_topic1 = otherstringid;
                            EditClassFragment.topic1 = otherstring;
                            topic = otherstring;
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
                checkBoxState = new boolean[arr.length()];

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    String id = obj.getString("id");
                    String name = obj.getString("name");

                    arrId.add(id);
                    arrName.add(name);
                    Object ss = obj.get("child");

                    try {
                        if (GlobalClass.lastValue.equals(name)) {
                            checkBoxState[i] = true;

                        } else
                            checkBoxState[i] = false;
                    } catch (Exception e) {
                    }
                    if (ss instanceof JSONArray) {
                        JSONArray arr1 = obj.getJSONArray("child");
                        if (arr1.length() != 0) {
                            for (int j = 0; j < arr1.length(); j++) {
                                JSONObject obj1 = arr1.getJSONObject(j);
                                String id1 = obj1.getString("id");
                                String name1 = obj1.getString("name");

                                arrChildId.add(id1);
                                arrChildNAme.add(name1);

                            }
                        }
                    } else {
                        String child = obj.getString("child");
                    }
                }
                mAdapter = new MyRecyclerViewAdapter(arrId, arrName, arrChildId, arrChildNAme);
                mRecyclerView.setAdapter(mAdapter);

            } catch (JSONException e) {
                e.printStackTrace();

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
