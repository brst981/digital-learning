package app.com.digitallearning.TeacherModule.Lessons;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Model.Data;
import app.com.digitallearning.TeacherModule.Model.QuizData;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${ShalviSharma} on 12/28/15.
 */
public class LessonDetailFragment extends Fragment {
    View rootview;
    ViewPager _mViewPager;
    CustomPagerAdapter mPagerAdapter;
    TextView headerTitle;
    String textHeader;
    int positionValue,pos1;
    SharedPreferences preferences;
    String Sch_Mem_id,cla_classid;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    ProgressDialog dlg;
    ArrayList<Data> dataList = new ArrayList<Data>();
    ArrayList<QuizData> quizDatalist = new ArrayList<QuizData>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_lesson_detail, container, false);
        _mViewPager = (ViewPager)rootview. findViewById(R.id.viewPager);
        imageButtonZoomIn = (ImageButton) rootview.findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) rootview.findViewById(R.id.img_zoom_out);
        dlg=new ProgressDialog(getActivity());
        mPagerAdapter = new CustomPagerAdapter(getActivity());

     //   _mViewPager.setAdapter(mPagerAdapter);

        if (getArguments() != null) {
            Bundle bundle = this.getArguments();
            textHeader = bundle.getString("HEADER_TEXT");
            positionValue = bundle.getInt("POSITION");
        }
        //pos1=getArguments().getInt("positioninLesson");
       // Log.e("pos1",""+pos1);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Sch_Mem_id=preferences.getString("Sch_Mem_id","");
        Log.e("Sch_Mem_id",""+Sch_Mem_id);

        cla_classid=preferences.getString("cla_classid","");
        Log.e("cla_classid",""+cla_classid);
        new Get_Lesson().execute(cla_classid, Sch_Mem_id);
        Log.e("txtHeader",""+textHeader);
        Log.e("positionValue",""+positionValue);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        initData();
        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText(textHeader);




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
    private void initData() {
        textHeader ="sdhfygsjdgf";
    }

    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;



        public CustomPagerAdapter(Context mContext) {
            this.mContext = mContext;

        }

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {

            // Declare Variables


            mLayoutInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = mLayoutInflater.inflate(R.layout.layout_lesson_items, container,
                    false);

            TextView textView = (TextView)itemView.findViewById(R.id.textView_lesson_count);
            ImageView imageViewLeft =(ImageView)itemView.findViewById(R.id.img_left_icon);
            ImageView imageViewRight =(ImageView)itemView.findViewById(R.id.img_right_icon);
            ImageView thumbnail=(ImageView)itemView.findViewById(R.id.thumbnail);
            TextView textView_lesson_description2=(TextView)itemView.findViewById(R.id.textView_lesson_description2) ;
            textView_lesson_description2.setText(dataList.get(position).getDescription());
            Picasso.with(getActivity()).load(dataList.get(position).getVideoThumbnail()).into(thumbnail);
         /*  if (position==4){
               imageViewRight.setVisibility(View.GONE);
           }*/

            if (position==0){
                imageViewLeft.setVisibility(View.GONE);
            }
            final int pos=position+1;


            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent videoclass=new Intent(getActivity(),LessonVideo.class);
                    videoclass.putExtra("video_url",dataList.get(position).getVideoUrl());
                    startActivity(videoclass);
                }
            });
            textView.setText("Lesson"+" "+ pos +" "+"of"+" "+ dataList.size());


            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // Remove viewpager_item.xml from ViewPager
            ((ViewPager) container).removeView((RelativeLayout) object);

        }
    }
    class Get_Lesson extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Get_Lesson(params[0],params[1]);

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
            Log.e("Get_LessonAPI", "" + result);

            if (result.contains("false")) {
//{"success":false,"data":null}
             /*   LogMessage.showDialog(getActivity(), null,
                        "Class successfully deleted", "OK");*/

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("No data").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                               /* Intent deletetoclass=new Intent(getActivity(),ClassActivity.class);
                                startActivity(deletetoclass);
                                getActivity().finish();*/

                            }
                        });

                AlertDialog dialog = alertDialog.create();
                dialog.show();
                TextView messageText = (TextView) dialog
                        .findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER);





            } else if (result.contains("true")) {
                updateGet_Lesson(result);


            }
        }
        private void updateGet_Lesson(String success) {

            try {

                JSONObject jsonObject = new JSONObject(success);
//{"success":true,"data":[{"les_id":"4114","Les_Cls_Id":"1808","lesson_name":"androidTest","les_date":"2016-03-05",
// "desc":"Description","les_image":"","img_thumb":"","video_url":"","video_thumb":"",
// "quiz_data":[{"quiz_master_id":"","quiz_name":"","quiz_desc":""}]},{"les_id":"4115","Les_Cls_Id":"1808","lesson_name":"try",
// "les_date":"2016-03-08","desc":"Ref hi","les_image":"","img_thumb":"","video_url":"","video_thumb":"",
// "quiz_data":[{"quiz_master_id":"","quiz_name":"","quiz_desc":""}]},
                JSONArray arr = jsonObject.optJSONArray("data");
                Log.e("arr", " " + arr);


                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    Data data = new Data();
                    data.setLessonId(obj.optString("les_id"));
                    data.setLessonClassId(obj.getString("Les_Cls_Id"));
                    data.setLessonName(obj.getString("lesson_name"));
                    data.setLessonDate(obj.getString("les_date"));

                    data.setDescription(obj.getString("desc"));
                    data.setLessonImage(obj.getString("les_image"));
                    data.setImageThumbnail(obj.getString("img_thumb"));
                    data.setVideoUrl(obj.getString("video_url"));
                    data.setVideoThumbnail(obj.getString("video_thumb"));
                    dataList.add(data);

                    /*String les_id = obj.optString("les_id");
                    Log.e("les_id", "" + les_id);
                    String Les_Cls_Id = obj.getString("Les_Cls_Id");
                    Log.e("Les_Cls_Id", "" + Les_Cls_Id);
                    String lesson_name = obj.getString("lesson_name");
                    Log.e("lesson_name", "" + lesson_name);
                    String les_date = obj.getString("les_date");
                    Log.e("les_date", "" + les_date);
                    String desc = obj.getString("desc");
                    Log.e("desc", "" + desc);
                    String les_image = obj.getString("les_image");
                    Log.e("les_image", "" + les_image);
                    String img_thumb = obj.getString("img_thumb");
                    Log.e("img_thumb", "" + img_thumb);
                    String video_url = obj.getString("video_url");
                    Log.e("video_url", "" + video_url);
                    String video_thumb = obj.getString("video_thumb");
                    Log.e("video_thumb", "" + video_thumb);*/

                    JSONArray arr1 = obj.getJSONArray("quiz_data");
                    Log.e("arr1", "" + arr1);

                    for (int j = 0; j < arr1.length(); j++) {
                        JSONObject obj1 = arr1.getJSONObject(j);

                        QuizData quizData = new QuizData();
                        quizData.setQuizMasterId(obj1.getString("quiz_master_id"));
                        quizData.setQuizName(obj1.getString("quiz_name"));
                        quizData.setQuizDescription(obj1.getString("quiz_desc"));
                        quizDatalist.add(quizData);

                        /*String quiz_master_id = obj1.getString("quiz_master_id");
                        Log.e("quiz_master_id", "" + quiz_master_id);
                        String quiz_name = obj1.getString("quiz_name");
                        Log.e("quiz_name", "" + quiz_name);
                        String quiz_desc = obj1.getString("quiz_desc");
                        Log.e("quiz_desc", "" + quiz_desc);*/

                    }
                    _mViewPager.setAdapter(mPagerAdapter);
                }

            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        _mViewPager.setPivotX(pivot.x);
        _mViewPager.setPivotY(pivot.y);
        _mViewPager.setScaleX(scaleX);
        _mViewPager.setScaleY(scaleY);
    }
}
