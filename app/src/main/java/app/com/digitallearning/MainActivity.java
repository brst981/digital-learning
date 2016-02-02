package app.com.digitallearning;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import app.com.digitallearning.WebServices.WSConnector;

public class MainActivity extends Activity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    EditText search;
    ProgressDialog dlg;
    int pos;
    ArrayList<String> arrSchoolList,arrSchoolImg,mList,arrSchoolId;
    ArrayList<HashMap<String, String>> arrayList = new ArrayList<HashMap<String,String>>();
    ArrayList<HashMap<String,String>> cityZipList;
    //  RelativeLayout relativeLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_question);
        dlg = new ProgressDialog(MainActivity.this);
        imageButtonZoomIn = (ImageButton) findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) findViewById(R.id.img_zoom_out);
        // relativeLayout=(RelativeLayout)findViewById(R.id.)

        cityZipList = new ArrayList<HashMap<String, String>>();

        arrSchoolList = new ArrayList<>();
        arrSchoolImg=new ArrayList<>();
        arrSchoolId=new ArrayList<>();
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(MainActivity.this);
        mList=arrSchoolList;
        mRecyclerView.setLayoutManager(mLayoutManager);
        new School_Listing().execute();
        search = (EditText) findViewById(R.id.search);

       /* ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,android.R.layout.select_dialog_item, arrSchoolList);
        Log.e("ArrSchoolList",""+arrSchoolList);*/


//        search.setThreshold(2);
//        search.setAdapter(new AutoCompleteAdapter(this,R.layout.auto_item, cityZipList));
      //  search.setAdapter(adapter);
        /*mAdapter = new MyRecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);*/
       /* mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(MainActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
               *//* Toast.makeText(MainActivity.this,"clicked"+" "+position,Toast.LENGTH_SHORT).show();*//*
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);

            }
        }));*/


//        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//             /*   pos= Integer.parseInt(arrSchoolId.get(position));
//                Log.e("pos",""+pos);*/
//                String selection = (String) parent.getItemAtPosition(position);
//
//                Log.e("selection","" +selection);
//                Log.e("position","" +position);
//                Log.e("ItemPOSition",""+ parent.getItemAtPosition(position));
//                Log.e("Item Id ", ""+arrSchoolId.get(position));
//                Log.e("pos.", ""+pos);
//
//  //              String item = (String) view.getItemAtPosition(position);
//
//                Toast.makeText(MainActivity.this, "clickeditem" + " " + position, Toast.LENGTH_SHORT).show();
////                Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
////                intent1.putExtra("SchoolName",arrSchoolList.get(position));
////                intent1.putExtra("SchoolID",arrSchoolId.get(position));
////                startActivity(intent1);
//
//
//
//
//            }
//        });

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


    }

    @Override
    protected void onStart() {
        super.onStart();

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                ArrayList<String> arr = new ArrayList<String>();

                ArrayList<String> arr1 = new ArrayList<String>();
                Log.e("Array Size",""+arrayList.size());
                if(s.length() > 1){
                    for(int i=0; i<arrayList.size(); i++) {
                        Log.e("Succes list1", "" + arrayList.get(i).get("sch_name"));


                        String str = arrayList.get(i).get("sch_name");
                        String str1=arrayList.get(i).get("sch_id");
                        Log.e("STRnameFilter",""+str);

                        if (str.indexOf(s.toString().substring(0, s.length())) == 0) {
                            // do something..
                           // Toast.makeText(MainActivity.this, "Matched" + str + "-" + s, Toast.LENGTH_SHORT).show();
                            arr.add(str);
                            arr1.add(str1);
                            Log.e("addArr",""+arr.add(str));
                        }


                    }
                    mAdapter = new MyRecyclerViewAdapter(arr,arrSchoolImg,arr1);
                    mRecyclerView.setAdapter(mAdapter);
                }

            }
        });
    }

    private class AutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {


        public AutoCompleteAdapter(Context context, int textViewResourceId,ArrayList<HashMap<String,String>> arrayList) {
            super(context, textViewResourceId);

        }

        @Override
        public int getCount() {
            return cityZipList.size();
        }

        @Override
        public String getItem(int position) {
            Log.e("SchoolIDItem",""+cityZipList.get(position).get("sch_id"));
            return cityZipList.get(position).get("sch_id");
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(final CharSequence constraint) {
                    Filter.FilterResults filterResults = new FilterResults();
                    if (constraint != null) {

                    //    autoCompleteList = new ArrayList<HashMap<String, String>>();
                        for (int i = 0; i < arrayList.size(); i++) {
                            if (arrayList.get(i).get("sch_id").startsWith(
                                    constraint.toString()) || arrayList.get(i).get("sch_name").startsWith(
                                    constraint.toString())) {
                                cityZipList.add(arrayList.get(i));
                            }
                        }

                        // Now assign the values and count to the FilterResults
                        // object
                        filterResults.values = arrayList;

                        filterResults.count = arrayList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }
    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
        // List<HomeModal> homeModals;
        private Context mContext;
        private LayoutInflater inflater;
        ArrayList<String> arrSchoolList;
        ArrayList<String> arrSchoolImg;
        ArrayList<String> arrSchoolId;

        public MyRecyclerViewAdapter(ArrayList<String> arrSchoolList,ArrayList<String> arrSchoolImg,ArrayList<String> arrSchoolId) {
            this.arrSchoolList = arrSchoolList;
            this.arrSchoolImg=arrSchoolImg;
            this.arrSchoolId=arrSchoolId;
        }

        @Override
        public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_item_list, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;

        }

        @Override
        public void onBindViewHolder(MyRecyclerViewAdapter.ViewHolder holder, int position) {
            Log.e("POSITION",""+position);



           holder.school_name.setText(arrSchoolList.get(position));
            Log.e("arrschool_name",""+arrSchoolList.get(position));

            Log.e("arrIMAGE",""+arrSchoolImg);
            Log.e("arrIMAGEpos",""+arrSchoolImg.get(position));

            Log.e("Iposition", ""+position);
            String str = arrSchoolImg.get(position);
            Log.e("Img url", "No url"+str);
            if (!str.isEmpty()) {
                Picasso.with(getApplicationContext()).load(str).into(holder.school_Image);


            }else{
                holder.school_Image.setImageResource(R.drawable.ic_launcher);
            }


        }

        @Override
        public int getItemCount() {
            return arrSchoolList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            /* ImageView imageView;
             TextView textView;
             RippleView relativeLayout;
             LinearLayout imageButtonAnswer;
             FrameLayout frameLayout;*/
            RippleView rippleView;
            TextView school_name;
            ImageView school_Image;


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
                rippleView = (RippleView) itemView.findViewById(R.id.ripple_main);
                school_name=(TextView)itemView.findViewById(R.id.schoolName);
                school_Image=(ImageView)itemView.findViewById(R.id.schoolImg);

                rippleView.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {

                        int position = getLayoutPosition(); // gets item position
                        pos=getLayoutPosition();
                        Log.e("Pos",""+pos);
                        Toast.makeText(MainActivity.this, "clicked" + " " + position, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        intent.putExtra("SchoolName",arrSchoolList.get(position));
                        Log.e("Sname",""+arrSchoolList.get(position));
                        intent.putExtra("SchoolID",arrSchoolId.get(position));
                        Log.e("SID",""+arrSchoolId.get(position));
                        startActivity(intent);

                    }
                });
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


    /**
     * zooming is done from here
     */
    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        mRecyclerView.setPivotX(pivot.x);
        mRecyclerView.setPivotY(pivot.y);
        mRecyclerView.setScaleX(scaleX);
        mRecyclerView.setScaleY(scaleY);
    }


    class School_Listing extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.schoolListing();

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dlg.setMessage("Loading.....");
            dlg.setCancelable(false);
            dlg.show();


        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dlg.dismiss();
            Log.e("MainResult",""+result);
            if (result.contains("true")) {

                updateUISchool_Listing(result);


            } else if (result.contains("false")) {
                Toast.makeText(getApplicationContext(), "Wrong User", Toast.LENGTH_SHORT).show();

            }
        }

        private void updateUISchool_Listing(String success) {
            try {



                JSONObject jsonObject = new JSONObject(success);

                JSONArray arr = jsonObject.getJSONArray("data");

                for (int i = 0; i < arr.length(); i++) {

                    JSONObject obj = arr.getJSONObject(i);

                    String sch_id=obj.getString("sch_id");

                    String sch_name = obj.getString("sch_name");

                 //   Log.e("sch_name", "" + sch_name);

                    String sch_photo_thumb = obj.getString("sch_photo_thumb");

                   // Log.e("sch_photo_thumb", "" + sch_photo_thumb);

                    HashMap<String, String> h1 = new HashMap<String, String>();
                    h1.put("sch_id",sch_id);
                    h1.put("sch_name",sch_name);
                    arrayList.add(h1);
                //    Log.e("h1",""+h1);
                //    Log.e("arrayList",""+arrayList);
                    arrSchoolList.add(sch_name);
                    if(arrSchoolImg!=null){
                        arrSchoolImg.add(sch_photo_thumb);
                    }

                    arrSchoolId.add(sch_id);
                //   Log.e("imageList", "" + arrSchoolImg);
                    mAdapter = new MyRecyclerViewAdapter(arrSchoolList,arrSchoolImg,arrSchoolId);
                    mRecyclerView.setAdapter(mAdapter);


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
