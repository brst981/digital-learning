package app.com.digitallearning.TeacherModule.Lessons;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.ClassActivity;
import app.com.digitallearning.TeacherModule.Model.Data;
import app.com.digitallearning.TeacherModule.Model.QuizData;
import app.com.digitallearning.Utill.LogMessage;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 3/28/16.
 */
public class FullDetailFragment extends Fragment {
    View rootview;
    SharedPreferences preferences;
    String Sch_Mem_id, cla_classid, lessonId,sizeget,lessonname;
    int position;
    ProgressDialog dlg;
    ArrayList<Data> dataList = new ArrayList<Data>();
    ArrayList<QuizData> quizDatalist = new ArrayList<QuizData>();
    ScrollView scrollquizlist;
    LinearLayout quizlinear;
    TextView quizname, textView, textView_lesson_description2, lessonquizname, headerTitle;
    ImageView imageViewLeft, imageViewRight, thumbnail;
    private View view;
    LayoutInflater inflater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.layout_lesson_items, container, false);

        quizname = (TextView) rootview.findViewById(R.id.quizname);
        textView = (TextView) rootview.findViewById(R.id.textView_lesson_count);
        imageViewLeft = (ImageView) rootview.findViewById(R.id.img_left_icon);
        imageViewRight = (ImageView) rootview.findViewById(R.id.img_right_icon);
        thumbnail = (ImageView) rootview.findViewById(R.id.thumbnail);
        textView_lesson_description2 = (TextView) rootview.findViewById(R.id.textView_lesson_description2);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        headerTitle = (TextView) activity.findViewById(R.id.mytext);


        dlg = new ProgressDialog(getActivity());

        lessonId = getArguments().getString("lessonId");
        lessonname=getArguments().getString("lessonname");
        position = getArguments().getInt("position");
        sizeget=getArguments().getString("sizeget");
        scrollquizlist = (ScrollView) rootview.findViewById(R.id.scrollquizlist);
        quizlinear = (LinearLayout) rootview.findViewById(R.id.quizlinear);

        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);



        if (position == 0) {
            imageViewLeft.setVisibility(View.GONE);
        }
        final int pos = position + 1;


        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Data data = (Data) v.getTag();

                if (data.getVideoUrl() != null && data.getVideoUrl().trim().length() > 0) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(data.getVideoUrl()));
                    startActivity(browserIntent);
                } else {
                    Toast.makeText(getActivity(), "Wrong data", Toast.LENGTH_SHORT).show();
                }
            }
        });



        return rootview;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Sch_Mem_id = preferences.getString("Sch_Mem_id", "");
        Log.e("Sch_Mem_id", "" + Sch_Mem_id);

        cla_classid = preferences.getString("cla_classid", "");
        new Get_Lesson().execute(cla_classid, Sch_Mem_id);
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
            if (dlg != null)
                dlg.dismiss();
            Log.e("Get_LessonAPI", "" + result);

            if (result.contains("false")) {
//{"success":false,"data":null}
                LogMessage.showDialog(getActivity(), null,
                        "Class successfully deleted", "OK");

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("No data").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                                Intent deletetoclass = new Intent(getActivity(), ClassActivity.class);
                                startActivity(deletetoclass);
                                getActivity().finish();

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
            dataList.clear();
            try {

                JSONObject jsonObject = new JSONObject(success);

                JSONArray arr = jsonObject.optJSONArray("data");
                Log.e("arr", " " + arr);

                JSONObject obj = arr.getJSONObject(position);

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

                int pos=position+1;
                textView.setText("Lesson" + " " + pos + " " + "of" + " " + sizeget);
                Log.e("sized",""+dataList.size());
                JSONArray arr1 = obj.getJSONArray("quiz_data");
                Log.e("arr1", "" + arr1);

                quizDatalist.clear();

                for (int j = 0; j < arr1.length(); j++) {
                    JSONObject obj1 = arr1.getJSONObject(j);

                    QuizData quizData = new QuizData();
                    quizData.setQuizMasterId(obj1.getString("quiz_master_id"));
                    quizData.setQuizName(obj1.getString("quiz_name"));
                    quizData.setQuizDescription(obj1.getString("quiz_desc"));
                    quizDatalist.add(quizData);

                }

                textView_lesson_description2.setText(data.getDescription());
             //   headerTitle.setText(data.getLessonName());
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                for (int k = 0; k < quizDatalist.size(); k++) {

                    inflater.inflate(R.layout.quiznameinlesson, quizlinear);
                    LinearLayout llChild = (LinearLayout) quizlinear.getChildAt(k);


                    lessonquizname = (TextView) llChild.findViewById(R.id.lessonquizname);
                    String st = quizDatalist.get(k).getQuizName();
                    lessonquizname.setText(st);


                }

                thumbnail.setTag(data);
               // headerTitle.setText(dataList.get(position).getLessonName());

//                headerTitle.setText(dataList.get(position).getLessonName());
                // Log.e("datalistsize",""+dataList.size());
                /*try {
                    Picasso.with(getActivity()).load(data.getVideoThumbnail());
                } catch (Exception e) {
                    e.printStackTrace();
                }*/


                try {
                    Picasso.with(getActivity()).load(dataList.get(position).getVideoThumbnail()).into(thumbnail);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}