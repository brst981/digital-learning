package app.com.digitallearning.TeacherModule.Lessons;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.net.Uri;
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
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

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
public class LessonDetailFragment extends Fragment implements ViewPager.OnPageChangeListener {
    View rootview;
    ViewPager _mViewPager;
    CustomPagerAdapter mPagerAdapter;
    TextView headerTitle;
    String textHeader;
    int positionValue, pos1;
    SharedPreferences preferences;
    String Sch_Mem_id, cla_classid, lessonname;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    ProgressDialog dlg;
    ArrayList<Data> dataList = new ArrayList<Data>();
    ArrayList<QuizData> quizDatalist = new ArrayList<QuizData>();
    public static int currentPage_postion = 0;
    LayoutInflater inflater;
    CareerAdapter career;
    private int currentPage;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_lesson_detail, container, false);
        _mViewPager = (ViewPager) rootview.findViewById(R.id.viewPager);
        imageButtonZoomIn = (ImageButton) rootview.findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) rootview.findViewById(R.id.img_zoom_out);
        dlg = new ProgressDialog(getActivity());


        if (getArguments() != null) {
            Bundle bundle = this.getArguments();
            textHeader = bundle.getString("HEADER_TEXT");
            positionValue = bundle.getInt("POSITION");
        }
        //pos1=getArguments().getInt("positioninLesson");
        // Log.e("pos1",""+pos1);
        lessonname = getArguments().getString("positioninLesson");
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Sch_Mem_id = preferences.getString("Sch_Mem_id", "");
        Log.e("Sch_Mem_id", "" + Sch_Mem_id);

        cla_classid = preferences.getString("cla_classid", "");
        Log.e("cla_classid", "" + cla_classid);
        new Get_Lesson().execute(cla_classid, Sch_Mem_id);
        Log.e("txtHeader", "" + textHeader);
        Log.e("positionValue", "" + positionValue);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        initData();

        mPagerAdapter = new CustomPagerAdapter(getActivity());
       /* _mViewPager.setAdapter(mPagerAdapter);
        _mViewPager.setOnPageChangeListener(this);*/
        // currentPage_postion = _mViewPager.getCurrentItem();
        Log.e("currentPage_postionSer", "" + currentPage_postion);
       /* _mViewPager.setCurrentItem(currentPage_postion);
          //  career=new CareerAdapter(getActivity(),quizDatalist);
        quizlist.setAdapter(career);
*/


        headerTitle = (TextView) activity.findViewById(R.id.mytext);


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
        textHeader = "sdhfygsjdgf";
    }


    @Override
    public void onPageSelected(int position) {
        Toast.makeText(getActivity(),
                "Selected page position: " + position, Toast.LENGTH_SHORT).show();
    }

    // This method will be invoked when the current page is scrolled
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // Code goes here
        Toast.makeText(getActivity(),
                "Selected page positionagain: " + position, Toast.LENGTH_SHORT).show();
    }

    // Called when the scroll state changes:
    // SCROLL_STATE_IDLE, SCROLL_STATE_DRAGGING, SCROLL_STATE_SETTLING
    @Override
    public void onPageScrollStateChanged(int state) {
        // Code goes here

    }


    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
       // LayoutInflater mLayoutInflater;


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


            inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(R.layout.layout_lesson_items, container,
                    false);

           // ListView quizlist = (ListView) itemView.findViewById(R.id.quizlist);
          //  quizlist.setAdapter(career);
            ScrollView scrollquizlist = (ScrollView) itemView.findViewById(R.id.scrollquizlist);
            LinearLayout quizlinear = (LinearLayout) itemView.findViewById(R.id.quizlinear);

            inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.quiznameinlesson, container,false);

            TextView lessonquizname=(TextView)view.findViewById(R.id.lessonquizname);
            String st=quizDatalist.get(position).getQuizName();
          //  lessonquizname.setText(st);
          //  Log.e("nameQuiz",""+quizDatalist.get(position).getQuizName());

            for (int k = 0; k < quizDatalist.size(); k++) {
                Log.e("Visites","bdj");
                lessonquizname.setText(quizDatalist.get(position).getQuizName());
                Log.e("Namequizlesson",""+quizDatalist.get(position).getQuizName());
                Log.e("according",""+quizDatalist.get(k).getQuizName());


            }
            TextView quizname = (TextView) itemView.findViewById(R.id.quizname);
            TextView textView = (TextView) itemView.findViewById(R.id.textView_lesson_count);
            ImageView imageViewLeft = (ImageView) itemView.findViewById(R.id.img_left_icon);
            ImageView imageViewRight = (ImageView) itemView.findViewById(R.id.img_right_icon);
            ImageView thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            TextView textView_lesson_description2 = (TextView) itemView.findViewById(R.id.textView_lesson_description2);
            textView_lesson_description2.setText(dataList.get(position).getDescription());

            try {
                Picasso.with(getActivity()).load(dataList.get(position).getVideoThumbnail()).into(thumbnail);
            } catch (Exception e) {
                e.printStackTrace();
            }



         /*  if (position==4){
               imageViewRight.setVisibility(View.GONE);
           }*/

            String size = String.valueOf(dataList.get(currentPage_postion));

            //     int in= Integer.parseInt(size);
            //      in=in-1;
            //    String size1=String.valueOf(in);
            Log.e("Size", "" + size);

            //   Log.e("LessonNAme", "" + dataList.get(n).getLessonName());
            Log.e("LessonNAmeposition", "" + position);



            if (position == 0) {
                imageViewLeft.setVisibility(View.GONE);
            }
            final int pos = position + 1;


            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (dataList.get(position).getVideoUrl() != null && dataList.get(position).getVideoUrl().trim().length() > 0) {
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(dataList.get(position).getVideoUrl()));
                        startActivity(browserIntent);
                    } else {
                        Toast.makeText(getActivity(), "Wrong data", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            textView.setText("Lesson" + " " + pos + " " + "of" + " " + dataList.size());
          //  for (int n = 0; n < size.length(); n++) {
                String str = dataList.get(position).getLessonName();
                headerTitle.setText(str);
        //    }

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

            return WSConnector.Get_Lesson(params[0], params[1]);

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
                    mPagerAdapter.notifyDataSetChanged();


                    JSONArray arr1 = obj.getJSONArray("quiz_data");
                    Log.e("arr1", "" + arr1);

                    for (int j = 0; j < arr1.length(); j++) {
                        JSONObject obj1 = arr1.getJSONObject(j);

                        QuizData quizData = new QuizData();
                        quizData.setQuizMasterId(obj1.getString("quiz_master_id"));
                        quizData.setQuizName(obj1.getString("quiz_name"));
                        quizData.setQuizDescription(obj1.getString("quiz_desc"));
                        quizDatalist.add(quizData);

                    }
                    data.setQuizData(quizDatalist);


                }


                _mViewPager.setAdapter(mPagerAdapter);
                currentPage_postion = _mViewPager.getCurrentItem();



                Log.e("CurrentPos", "" + currentPage_postion);
                // _mViewPager.setOnPageChangeListener(this);
            } catch (JSONException e) {
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


    class CareerAdapter extends BaseAdapter {
        Context context;
        ArrayList<Data> quizDatalist;


        public CareerAdapter(Context context, ArrayList<Data> quizDatalist) {
            this.quizDatalist = quizDatalist;
            this.context = context;

        }


        @Override
        public int getCount() {
            Log.e("arr", "" + quizDatalist.size());
            return quizDatalist.size();
        }

        @Override
        public Object getItem(int position) {
            return quizDatalist.get(position);
        }


        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;  //=new ViewHolder();

            if (holder == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.lesson_detail_quiz, null);
                holder = new ViewHolder();
                holder.quizname = (TextView) convertView.findViewById(R.id.quizname);
                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }


            Data data = quizDatalist.get(position);
            QuizData quizData = data.getQuizData().get(position);

            holder.quizname.setText(quizData.getQuizName());
            Log.e("nameofQUiz", "" + quizData.getQuizName());


            return convertView;
        }


    }

    static class ViewHolder {
        TextView quizname;


    }


}
