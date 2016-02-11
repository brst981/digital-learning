package app.com.digitallearning.TeacherModule.Classes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.ClassFragment;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${ShalviSharma} on 12/22/15.
 */
public class EditClassFragment extends FragmentActivity {

    TextView headerTitle, text_type_detail, text_topic_detail, text_description_detail, text_features_detail;
    String title, classtype, classId, userid, schid, passcode, createdby, arrclassname, arrName, arrId, arrChildNAme, classid, newTopic, cal, chat, grades, stu, semeserval, coursecodeval, check;
    EditText edt_title, edt_passcode, semester, coursecode;
    String passcodes,classname,created,styles,topics,desc;
    RelativeLayout relative_class_type, relative_topic, relative_description, relative_features,relative;
    RippleView ripple_edit_update;
    ProgressDialog dlg;
    Switch switch_upload;
    ImageButton back;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    private Toolbar mToolbar;
    public static String top;
    final CharSequence[] features = {"Calender", "Chat", "Grades", "Student Tab"};
    final static CharSequence[] classtype1 = {"Instructor", "Blended", "Self Paced"};
    int fromsave;
    // Fragment mFragment;
    public static String style, classType1, topic1, topic, description, des,singleClassID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_class);
        dlg=new ProgressDialog(EditClassFragment.this);
        fromsave=getIntent().getIntExtra("fromsave",0);
        Log.e("fromsave",""+fromsave);
        new Edit_Class().execute(singleClassID,ClassFragment.Sch_Mem_id,ClassFragment.Mem_Sch_Id);
        Log.e("ClassFragment.sing",""+singleClassID);
        Log.e("ClassFragment.S",""+ClassFragment.Sch_Mem_id);
        Log.e("ClassFragment.M",""+ClassFragment.Mem_Sch_Id);


        imageButtonZoomIn = (ImageButton) findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) findViewById(R.id.img_zoom_out);
        text_type_detail = (TextView) findViewById(R.id.text_type_detail);
        text_topic_detail = (TextView) findViewById(R.id.text_topic_detail);
        text_description_detail = (TextView) findViewById(R.id.text_description_detail);
        text_features_detail = (TextView) findViewById(R.id.text_features_detail);
        relative=(RelativeLayout)findViewById(R.id.relative);
        edt_title = (EditText) findViewById(R.id.edt_title);
        back=(ImageButton)findViewById(R.id.back);
        back.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       onBackPressed();
                    }
                }
        );
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
        semester = (EditText) findViewById(R.id.semester);
        coursecode = (EditText) findViewById(R.id.coursecode);
        edt_passcode = (EditText) findViewById(R.id.edt_passcode);
        switch_upload = (Switch) findViewById(R.id.switch_upload);

        switch_upload.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e("switch",""+isChecked);
                if(isChecked){
                    check="1";
                    Log.e("check",""+check);
                }
                else{
                    check="0";
                    Log.e("check1",""+check);
                }

            }
        });

        /*AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");

       headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Edit Class");*/

          /* title = getIntent().getStringExtra("title");
                dscrptn=getIntent().getStringExtra("description");
        Log.e("dscrptn Edit", "" + dscrptn);
        text_description_detail.setText(dscrptn);
            Log.e("titleOn Edit", "" + title);
            arrName = getIntent().getStringExtra("arrName");
            arrId = getIntent().getStringExtra("arrId");
            arrChildNAme = getIntent().getStringExtra("arrChildNAme");
            classid = getIntent().getStringExtra("classid");


            passcode = getIntent().getStringExtra("passcode");
            Log.e("passcodeOn Edit", "" + passcode);

            createdby = getIntent().getStringExtra("arrcreatedby");
            Log.e("createdby", "" + createdby);
            arrclassname = getIntent().getStringExtra("arrcreatedby");
            Log.e("arrclassname", "" + arrclassname);

        classtype=getIntent().getStringExtra("style");
        Log.e("classtypestyle",""+classtype);
        classId = getIntent().getStringExtra("myListclassId");
        Log.e("classIdInEdit", "" + classId);*/












        /*if(classtype=="1"){
            text_type_detail.setText("Instructor");
        }
        if(classtype=="2"){
            text_type_detail.setText("Blended");
        }
        else{
            text_type_detail.setText("Self Paced");

        }*/





      /*  edt_passcode.setText(passcode);
        if(topic1==null){
            topic=getArguments().getString("topic");
            Log.e("topiceOn Edit",""+topic);
            text_topic_detail.setText(topic);
        }
        else{
            text_topic_detail.setText(topic1);
        }

        if (style==null) {
            Log.e("Visit","dd");
            Log.e("style",""+style);
            classType1=getArguments().getString("classType");

            if(classType1.equalsIgnoreCase("1")){
                text_type_detail.setText("Instructor");
            }

            else  if(classType1.equalsIgnoreCase("2")){
                text_type_detail.setText("Blended");
            }
            else  if(classType1.equalsIgnoreCase("3")){
                text_type_detail.setText("Self-Paced");
            }
        }
     else{
            //text_type_detail.setText(style);

            if(style.equalsIgnoreCase("1")){
                text_type_detail.setText("Instructor");
            }

            else  if(style.equalsIgnoreCase("2")){
                text_type_detail.setText("Blended");
            }
            else  if(style.equalsIgnoreCase("3")){
                text_type_detail.setText("Self-Paced");
            }
        }
       // ClassFragment.classtpye =  classType;
    //    Log.e("ClassFragment.classtpye",""+ClassFragment.classtpye);
        Log.e("classTypeOn Edit",""+classType1);
    //    Log.e("reclasstype",""+reclasstype);

//        ClassTypeFragment.classtpye = reclasstype;
//        Log.e("EditClassclasstpye",""+reclasstype);


        if(des==null){
            description=getArguments().getString("description");
            Log.e("descriptionOn Edit",""+description);
            text_description_detail.setText(description);
        }
        else{
            text_description_detail.setText(des);
        }


*/


            edt_title.setText(title);
            userid = ClassFragment.Sch_Mem_id;
            schid = ClassFragment.Mem_Sch_Id;


            ripple_edit_update = (RippleView) findViewById(R.id.ripple_edit_update);
            relative_class_type = (RelativeLayout) findViewById(R.id.relative_class_type);
            relative_topic = (RelativeLayout) findViewById(R.id.relative_topic);

            relative_features = (RelativeLayout) findViewById(R.id.relative_features);
            relative_features.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(EditClassFragment.this);
                    builder.setTitle("Make your selection");
                    builder.setItems(features, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            // Do something with the selection
                            //  mDoneButton.setText(items[item]);
                            Log.e("items[features]", "" + features[item]);


                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

                /*FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FeatureFragment featureFragment = new FeatureFragment();
                fragmentTransaction.replace(android.R.id.content, featureFragment).addToBackStack(null);
                fragmentTransaction.commit();*/


            relative_description = (RelativeLayout) findViewById(R.id.relative_description);
            relative_description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    DescriptionFragment descriptionFragment = new DescriptionFragment();
                    fragmentTransaction.replace(android.R.id.content, descriptionFragment).addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        text_description_detail.setText(des);
            relative_class_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(EditClassFragment.this);
                    builder.setTitle("Make your selection");
                    builder.setItems(classtype1, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            // Do something with the selection
                            //  mDoneButton.setText(items[item]);
                            Log.e("items[features]", "" + classtype1[item]);
                            if (classtype1[item] == "Instructor") {
                                style = "1";
                            } else if (classtype1[item] == "Blended") {
                                style = "2";
                            } else {
                                style = "3";
                            }
                            if (style == "1") {
                                text_type_detail.setText("Instructor");
                            } else if (style == "2") {
                                text_type_detail.setText("Blended");
                            }
                            else if (style =="3") {
                                text_type_detail.setText("Self Paced");
                            }
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });
            /*    int a=1;
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ClassTypeFragment classTypeFragment = new ClassTypeFragment();
             // *//**//*  Bundle args = new Bundle();
               // args.putInt("ID",a);*//**//*
                fragmentTransaction.replace(android.R.id.content, classTypeFragment).addToBackStack(null);
               // classTypeFragment.setArguments(args);
                fragmentTransaction.commit();*/
            relative_topic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    TopicFragment topicFragment = new TopicFragment();
                    Bundle arg = new Bundle();
                    arg.putString("arrName", String.valueOf(arrName));
                    arg.putString("arrId", arrId);
                    arg.putString("arrChildNAme", String.valueOf(arrChildNAme));
                    arg.putString("classid", classid);
                    fragmentTransaction.replace(android.R.id.content, topicFragment).addToBackStack(null);
                    topicFragment.setArguments(arg);
                    fragmentTransaction.commit();
                }
            });




       /* classtype=ClassFragment.classtpye;
        Log.e("classtypeInEDITCLAss",""+classtype);


       if (ClassFragment.classtpye.equalsIgnoreCase("1")){

            text_type_detail.setText("Instructor");
        }
        else  if(ClassFragment.classtpye.equalsIgnoreCase("2")){
            text_type_detail.setText("Blended");
        }
        else  if(ClassFragment.classtpye.equalsIgnoreCase("3")){
            text_type_detail.setText("Self-Paced");
        }
        cal=FeatureFragment.cal;
        chat=FeatureFragment.chat;
        grades=FeatureFragment.grades;
        stu=FeatureFragment.stu;

        String feature=cal+" "+chat+" "+grades+" "+stu;
        String newfeature= feature.replace("null","");
        text_features_detail.setText(newfeature);






        switch_upload.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               Log.e("switch",""+isChecked);
                if(isChecked){
                     check="1";
                    Log.e("check",""+check);
                }
                else{
                     check="0";
                    Log.e("check1",""+check);
                }

            }
        });*/




        /*ripple_edit_update.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                coursecodeval=coursecode.getText().toString();
                semeserval=semester.getText().toString();
                Log.e("coursecodeval",""+coursecodeval);
                Log.e("semeserval",""+semeserval);
             //   new Update_Class().execute(classid,createdby,ClassFragment.Sch_Mem_id,arrclassname,style,topic,des,check,semeserval,coursecodeval,cal,stu,chat,grades);

            }
        });*/

        }


    class Edit_Class extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.edit_Class(params[0], params[1], params[2]);

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
            //    Log.e("REsulT", "" + result);
            if (result.contains("true")) {

                updateTeacherLogIn(result);


            } else if (result.contains("false")) {


            }
        }
//{"success":true,"data":[{"clsid":"183599","createdby":"2155","schoolid":"487","classid":"1800","classname":"1",
// "classpassword":"","style":"2","topic_id":"12","topic_name":"Training ","desc":"Test","class_full_image":"",
// "class_thumb_image":"","Cls_Features":{"enable_calendar":"","enable_students_tab":"","enable_chat":"","enable_grades":""}}]},

        private void updateTeacherLogIn(String success) {

            try {

                JSONObject jsonObject = new JSONObject(success);
                Log.e("jsonObject", "" + jsonObject);

                JSONArray arr = jsonObject.getJSONArray("data");
                Log.e("arr", " " + arr);
                for (int i = 0; i <= arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    passcodes = obj.getString("clsid");

                    created = obj.getString("createdby");

                    String schoolid = obj.getString("schoolid");
                    Log.e("schoolid",""+schoolid);

                 String classid = obj.getString("classid");

                     classname = obj.getString("classname");

                    String classpassword = obj.getString("classpassword");

                    String styles = obj.getString("style");

                    String topic_id = obj.getString("topic_id");

                    topics = obj.getString("topic_name");

                     desc = obj.getString("desc");

                    String class_full_image = obj.getString("class_full_image");

                    String class_thumb_image = obj.getString("class_thumb_image");



                    JSONObject obj1=obj.getJSONObject("Cls_Features"); {

                        String enable_calendar = obj1.getString("enable_calendar");

                        String enable_students_tab = obj1.getString("enable_students_tab");

                        String enable_chat = obj1.getString("enable_chat");

                        String enable_grades = obj1.getString("enable_grades");

                        edt_title.setText(classname);

                        edt_passcode.setText(passcodes);
                        Log.e("passcodes",""+passcodes);
                        Log.e("styles",""+styles);


                        if(styles.contains("1")){
                            Log.e("visit","visit"+"");
                         //   styles= "Instructor";
                            text_type_detail.setText("Instructor");
                        }
                       else if(styles.contains("2")){
                          //  styles= "Blended";
                           text_type_detail.setText("Blended");
                        }
                        else {
                          //  styles= "Self Paced";
                           text_type_detail.setText("Self Paced");

                        }
                      //  text_type_detail.setText(styles);
                        Log.e("styles",""+styles);
                        if(fromsave==1){
                            Log.e("fromsave",""+fromsave);
                            text_topic_detail.setText(topic1);
                            Log.e("topic1",""+topic1);

                        }
                        else{
                            text_topic_detail.setText(topics);
                            Log.e("topics",""+topics);
                        }
                        Log.e("topics",""+topics);








                        if(fromsave==1){
                            Log.e("fromsave",""+fromsave);
                            text_description_detail.setText(des);
                            Log.e("des",""+des);

                        }
                        else{
                            text_description_detail.setText(desc);

                            Log.e("desc",""+desc);
                        }

                       /* passcode.add(clsid);
                        Log.e("..passcode","" +passcode);
                        classType.add(style);
                        Log.e("..classType","" +classType);
                        arrcreatedby.add(createdby);
                        Log.e("..arrcreatedby","" +arrcreatedby);
                        topic.add(topic_name);
                        Log.e("..topic","" +topic);
                        arrclassname.add(classname);
                        Log.e("..passcode","" +passcode);
                        description.add(desc);
                        Log.e("..description","" +description);*/



                        /*Intent intent=new Intent(getActivity(),EditClassFragment.class);
                        intent.putExtra("title",myList.get(pos));
                        intent.putExtra("myListclassId",myListclassId.get(pos));
                        intent.putExtra("passcode",passcode);
                        intent.putExtra("classType",classType);
                        intent.putExtra("arrcreatedby",createdby);
                        intent.putExtra("arrclassname",classname);
                        intent.putExtra("topic",topic);
                        intent.putExtra("style",style);

                        intent.putExtra("description",desc);
                        intent.putExtra("arrName",String.valueOf(arrName));
                        intent.putExtra("arrId",arrId);
                        intent.putExtra("arrChildNAme",String.valueOf(arrChildNAme));
                        intent.putExtra("Sch_Mem_id",Sch_Mem_id);
                        intent.putExtra("Mem_Sch_Id",Mem_Sch_Id);
                        intent.putExtra("classid",classid);
                        startActivity(intent);*/


                    }


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }










        class Update_Class extends AsyncTask<String, Integer, String> {


            @Override
            protected String doInBackground(String... params) {

                return WSConnector.update_class(params[0], params[1], params[2]);

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
                //    Log.e("REsulT", "" + result);
                if (result.contains("true")) {

                    //updateTeacherLogIn(result);


                } else if (result.contains("false")) {
                    Toast.makeText(getApplicationContext(), "Wrong User", Toast.LENGTH_SHORT).show();

                }
            }


        }
    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        relative.setPivotX(pivot.x);
        relative.setPivotY(pivot.y);
        relative.setScaleX(scaleX);
        relative.setScaleY(scaleY);
    }










    @Override
    public void onStart() {
        super.onStart();


        text_topic_detail.setText(topic1);
        Log.e("topic111",""+topic1);
        /*Log.e("inside Start",""+ClassFragment.classtpye);
        if (ClassFragment.classtpye.equalsIgnoreCase("1")){

            text_type_detail.setText("Instructor");
        }
        else  if(ClassFragment.classtpye.equalsIgnoreCase("2")){
            text_type_detail.setText("Blended");
        }
        else  if(ClassFragment.classtpye.equalsIgnoreCase("3")){
            text_type_detail.setText("Self-Paced");
        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();
        text_topic_detail.setText(topic1);
        Log.e("topic1111",""+topic1);
    }
}