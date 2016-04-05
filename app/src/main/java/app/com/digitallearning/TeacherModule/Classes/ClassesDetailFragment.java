package app.com.digitallearning.TeacherModule.Classes;
//Cls_desc

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.ClassFragment;
import app.com.digitallearning.TeacherModule.Curriculum.CurriculumActivity;
import app.com.digitallearning.TeacherModule.Schedule.ScheduleFragment;
import app.com.digitallearning.TeacherModule.Syllabus.SyllabusActivity;
import app.com.digitallearning.Utill.GlobalClass;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${ShalviSharma} on 12/19/15.
 */
public class ClassesDetailFragment extends Fragment {
    View rootview;
    FloatingActionMenu menu_main;
    FloatingActionButton floatingActionButtonEdit, floatingActionButtonChange, floatingActionButtonDelete;
    TextView headerTitle, text_passcode_detail, curriculum, scheduleday, syllabus, text_school_name, text_topic_name, text_des;
    String title, arrDay, titlesyllabus, titlecurriculum;
    int curiid = 10;
    ImageView img_edit_picture;
    ProgressDialog dlg;
    SharedPreferences preferences;
    String cla_classid, schoolName, Sch_Mem_id, class_image;
    boolean classID = false;
    RippleView rippleViewTeacherCollab, rippleViewCurriculum, ripple_teacher_syllabus, ripple_teacher_schedule;


    public static ClassesDetailFragment newInstance() {
        ClassesDetailFragment mFragment = new ClassesDetailFragment();
        /*Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);*/
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_class_details, container, false);
        menu_main = (FloatingActionMenu) rootview.findViewById(R.id.menu_main);
        text_school_name = (TextView) rootview.findViewById(R.id.text_school_name);
        text_topic_name = (TextView) rootview.findViewById(R.id.text_topic_name);
        text_des = (TextView) rootview.findViewById(R.id.text_des);
        Log.e("ClassDetail.newtopicsel", "" + EditClassFragment.newtopicsel);
        Log.e("isheaderrec1", "" + ClassFragment.titleheader);
        dlg = new ProgressDialog(getActivity());
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        cla_classid = preferences.getString("cla_classid", "");
        schoolName = preferences.getString("schoolName", "");
        Log.e("cla_classid", "" + cla_classid);
        Sch_Mem_id = preferences.getString("Sch_Mem_id", "");
        Log.e("Sch_Mem_id", "" + Sch_Mem_id);
        text_school_name.setText(schoolName);
        try {
            new Get_Class_image().execute(cla_classid);
        } catch (Exception e) {
        }


        text_passcode_detail = (TextView) rootview.findViewById(R.id.text_passcode_detail);
        text_passcode_detail.setText(cla_classid);
        scheduleday = (TextView) rootview.findViewById(R.id.scheduleday);
        syllabus = (TextView) rootview.findViewById(R.id.syllabus);
        curriculum = (TextView) rootview.findViewById(R.id.curriculum);
        img_edit_picture = (ImageView) rootview.findViewById(R.id.img_edit_picture);
        floatingActionButtonEdit = (FloatingActionButton) rootview.findViewById(R.id.menu_item_edit);
        floatingActionButtonChange = (FloatingActionButton) rootview.findViewById(R.id.menu_item_change_pic);
        floatingActionButtonDelete = (FloatingActionButton) rootview.findViewById(R.id.menu_item_delete);
        rippleViewTeacherCollab = (RippleView) rootview.findViewById(R.id.ripple_teacher_collaboration);
        rippleViewCurriculum = (RippleView) rootview.findViewById(R.id.ripple_teacher_curriculum);
        ripple_teacher_syllabus = (RippleView) rootview.findViewById(R.id.ripple_teacher_syllabus);
        ripple_teacher_schedule = (RippleView) rootview.findViewById(R.id.ripple_teacher_schedule);


        ripple_teacher_schedule.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent gotoschedule = new Intent(getActivity(), ScheduleFragment.class);
                startActivity(gotoschedule);
                //   getActivity().finish();


            }
        });

        title = ClassFragment.titleheader;
        Log.e("DetailClasstitle", "" + title);

        text_des.setText(ClassFragment.testing);

        text_topic_name.setText(ClassFragment.subject);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        headerTitle.setText(ClassFragment.titleheader);


        createCustomAnimation();
        initData();


        return rootview;
    }

    private void initData() {

        floatingActionButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditClassFragment.class);
                startActivity(intent);
                getActivity().finish();
                GlobalClass.classDetail=true;
            }
        });



        floatingActionButtonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ChangePictureFragment classFragment = new ChangePictureFragment();
                Bundle bundle = new Bundle();
                bundle.putString("class_image", class_image);
                Log.e("sendclass_image", "" + class_image);
                fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                classFragment.setArguments(bundle);
                fragmentTransaction.commit();
            }
        });


        floatingActionButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DeleteClassFragment classFragment = new DeleteClassFragment();
                fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        rippleViewTeacherCollab.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TeacherCollabrationFragment classFragment = new TeacherCollabrationFragment();
                fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        rippleViewCurriculum.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent gotocurri = new Intent(getActivity(), CurriculumActivity.class);
                gotocurri.putExtra("curiid", curiid);
                startActivity(gotocurri);
                //  getActivity().finish();
            }
        });
        ripple_teacher_syllabus.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent gotocurri = new Intent(getActivity(), SyllabusActivity.class);
                startActivity(gotocurri);
                //  getActivity().finish();
            }
        });
    }

    private void createCustomAnimation() {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(menu_main.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(menu_main.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(menu_main.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(menu_main.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                menu_main.getMenuIconView().setImageResource(menu_main.isOpened()
                        ? R.drawable.ic_edit : R.drawable.ic_close);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        menu_main.setIconToggleAnimatorSet(set);
    }

    class Get_Class_image extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Get_image(params[0]);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dlg.setMessage("Loading....");
            dlg.setCancelable(false);
            dlg.show();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dlg.dismiss();
            Log.e("REsulTinAddSchedule", "" + result);
            if (result.contains("true")) {
                updateTeacherLogIn(result);

            } else if (result.contains("false")) {
                //Toast.makeText(getActivity(), "Wrong User", Toast.LENGTH_SHORT).show();

            }
        }


        private void updateTeacherLogIn(String success) {

            try {

                JSONObject jsonObject = new JSONObject(success);
                Log.e("jsonObject", "" + jsonObject);


                JSONArray arr = jsonObject.getJSONArray("data");
                Log.e("arr", " " + arr);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Log.e("obj", "" + obj);

                    class_image = obj.getString("class_image");
                    Log.e("class_image", "" + class_image);
                    Log.e("Checkimg_edit_picture", "" + img_edit_picture);
                    if (img_edit_picture == null) {
                        Log.e("ifnullimg_edit_picture", "" + img_edit_picture);
                        img_edit_picture.setImageResource(R.drawable.no_image_icon);
                    }
                    // try {
                    // if (img_edit_picture != null) {
                    Log.e("img_edit_picture", "" + class_image);


                    Picasso.with(getActivity()).load(class_image).networkPolicy(NetworkPolicy.OFFLINE).into(img_edit_picture,
                            new Callback() {
                                @Override
                                public void onSuccess() {

                                    Log.e("Onsuccess", "ss");

                                }

                                @Override
                                public void onError() {
                                    Log.e("OnError", "ss");

                                    Picasso.with(getActivity())
                                            .load(class_image).placeholder(R.drawable.img_loading)

                                            .into(img_edit_picture, new Callback() {
                                                @Override
                                                public void onSuccess() {

                                                }

                                                @Override
                                                public void onError() {
                                                    Log.e("Picasso", "Could not fetch image");
                                                }
                                            });

                                }
                            });

                    //  Picasso.with(getActivity()).load(class_image).placeholder(R.drawable.no_image_icon).into(img_edit_picture);
                    //  } /*else {
                    // img_edit_picture.setImageResource(R.drawable.img_loading);
                    // }*/
//                    } catch (Exception e) {
//                    }

                }

                headerTitle.setText(ClassFragment.titleheader);
                Log.e("isheaderrec", "" + ClassFragment.titleheader);


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    class Get_carriculum extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Get_curriculum(params[0], params[1]);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dlg.setMessage("Loading .....");
            dlg.setCancelable(false);
            dlg.show();

        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(dlg.isShowing()){
               // dlg.dismiss();
            }


            Log.e("Get_syllabusAPI", "" + result);
            if (result.contains("false")) {


                curriculum.setText(" ");


            } else if (result.contains("true")) {
                updateTeacherLogIn(result);

            }

            new Schedule_listing().execute(Sch_Mem_id, cla_classid);

        }

        private void updateTeacherLogIn(String success) {

            try {

                JSONObject jsonObject = new JSONObject(success);//addsrtitle
                JSONArray arr = jsonObject.getJSONArray("data");
                Log.e("arr", " " + arr);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    titlecurriculum = obj.getString("title");
                    Log.e("title", "kk" + titlecurriculum);
                    /*if(titlecurriculum.equals("")){
                        curriculum.setText(" ");
                    }
                    else{*/
                    curriculum.setText(titlecurriculum);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class Schedule_listing extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Schedule_listing(params[0], params[1]);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dlg.setMessage("Loading ....");
            dlg.setCancelable(false);
            dlg.show();


        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(dlg.isShowing())
            //dlg.dismiss();
            Log.e("REsulTinSchedule", "" + result);
            if (result.contains("true")) {

                updateTeacherLogIn(result);


            } else if (result.contains("false")) {
            //    Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();
                scheduleday.setText(" ");
            }

            new Get_syllabus().execute(cla_classid, Sch_Mem_id);
        }

        private void updateTeacherLogIn(String success) {

            try {

                JSONObject jsonObject = new JSONObject(success);
                Log.e("jsonObject", "" + jsonObject);


                JSONArray arr = jsonObject.getJSONArray("data");
                Log.e("arr", " " + arr);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    arrDay = obj.getString("day");
                    Log.e("dayOn", "" + arrDay);

                    if (arrDay.contains("1")) {
                        scheduleday.setText("Monday");
                    } else if (arrDay.contains("2")) {
                        scheduleday.setText("Tuesday");
                    } else if (arrDay.contains("3")) {
                        scheduleday.setText("Wednesday");
                    } else if (arrDay.contains("4")) {
                        scheduleday.setText("Thrusday");
                    } else if (arrDay.contains("5")) {
                        scheduleday.setText("Friday");
                    } else if (arrDay.contains("6")) {
                        scheduleday.setText("Saturday");
                    } else if (arrDay.contains("7")) {
                        scheduleday.setText("Sunday");
                    } else if (arrDay.contains("8")) {
                        scheduleday.setText("Every Day");
                    } else if (arrDay.contains("9")) {
                        scheduleday.setText("Every Weekday");
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    class Get_syllabus extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Get_syllabus(params[0], params[1]);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dlg.setMessage("Loading .....");
            dlg.setCancelable(false);
            dlg.show();


        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dlg.dismiss();
            Log.e("Get_syllabusAPI", "" + result);

            if (result.contains("false")) {

                syllabus.setText(" ");

            } else if (result.contains("true")) {
                updateTeacherLogIn(result);
            }

        }
    }

    private void updateTeacherLogIn(String success) {

        try {

            JSONObject jsonObject = new JSONObject(success);

            JSONArray arr = jsonObject.getJSONArray("data");
            Log.e("arr", " " + arr);
            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);


                titlesyllabus = obj.getString("title");
                Log.e("title", "" + titlesyllabus);
                syllabus.setText(titlesyllabus);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        new Get_carriculum().execute(cla_classid, Sch_Mem_id);
//        new Schedule_listing().execute(Sch_Mem_id, cla_classid);
//        new Get_syllabus().execute(cla_classid, Sch_Mem_id);
    }
}
