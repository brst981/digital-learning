package app.com.digitallearning.TeacherModule.Classes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

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
    String title, classtype, classId, userid, schid, passcode, createdby, arrclassname, arrName, arrId, arrChildNAme, classid, newTopic, cal, chat, grades, stu, semeserval, coursecodeval;
    EditText edt_title, edt_passcode, semester, coursecode;
    String passcodes,classname,created,styles,topics,desc,feature,newfeature;
    RelativeLayout relative_class_type, relative_topic, relative_description, relative_features,relative;
    RippleView ripple_edit_update;
    ProgressDialog dlg;
    Switch switch_upload;
    ImageButton back;
    String check="0";
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    private Toolbar mToolbar;
    public static String top;
    final CharSequence[] features = {"Calender", "Chat", "Grades", "Student Tab"};
    final static CharSequence[] classtype1 = {"Instructor", "Blended", "Self Paced"};
    int fromsave,fromdes;
    SharedPreferences preferences;
    // Fragment mFragment;
    public static String style, classType1, topic1, topic, description, des,singleClassID,sr_topic1;
    int c1,c2,c3,c4;
    int fromEdit=12;

    static  String newTitle,newTitle1,semester1,semester2,coursecode1,coursecode2;
    String sr_title, sr_passcode,sr_classtppe,sr_classtppeval,sr_topic,sr_feature,sr_description,classidgetupdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_class);
        dlg=new ProgressDialog(EditClassFragment.this);
        fromsave=getIntent().getIntExtra("fromsave",0);
        newTitle1=getIntent().getStringExtra("newTitle");
        semester2=getIntent().getStringExtra("semester");
        Log.e("semester2",""+semester2);
        coursecode2=getIntent().getStringExtra("coursecode");
        Log.e("coursecode2",""+coursecode2);
        new Before_Class_Listing().execute();
        fromdes=getIntent().getIntExtra("fromdes",0);
       /* preferences = PreferenceManager.getDefaultSharedPreferences(EditClassFragment.this);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("fromdes", fromdes);
        editor.commit();*/
        Log.e("fromsave",""+fromsave);
        Log.e("fromdes",""+fromdes);
        Log.e("check0",""+check);
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
        semester1=semester.getText().toString();
        coursecode = (EditText) findViewById(R.id.coursecode);
        coursecode1=coursecode.getText().toString();


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

                    feature=" ";
                    newfeature=" ";
                    cal=" ";
                    chat=" ";
                    grades=" ";
                    stu=" ";
                    text_features_detail.setText("");
                    Log.e("featureempty",""+feature);
                    Log.e("newfeatureempty",""+newfeature);
                    Log.e("emptycal",""+cal);
                    Log.e("emptychat",""+chat);
                   /* AlertDialog.Builder builder = new AlertDialog.Builder(EditClassFragment.this);
                    builder.setTitle("Make your selection");
                    builder.setItems(features, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {
                            // Do something with the selection
                            //  mDoneButton.setText(items[item]);
                            Log.e("items[features]", "" + features[item]);
                           *//* cal=FeatureFragment.cal;
                            chat=FeatureFragment.chat;
                            grades=FeatureFragment.grades;
                            stu=FeatureFragment.stu;*//*
                            String feature=cal+" "+chat+" "+grades+" "+stu;
                            String newfeature= feature.replace("null","");
                            text_features_detail.setText(newfeature);  }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();*/

                    final Dialog dialog = new Dialog(EditClassFragment.this);
                    //tell the Dialog to use the dialog.xml as it's layout description
                    dialog.setContentView(R.layout.feature_fragment);
                    dialog.setCancelable(false);
                    dialog.setTitle("Make your selection");
                    RelativeLayout rel=(RelativeLayout)dialog.findViewById(R.id.rel) ;
                    RelativeLayout rel1=(RelativeLayout)dialog.findViewById(R.id.rel1) ;
                    RelativeLayout rel2=(RelativeLayout)dialog.findViewById(R.id.rel2) ;
                    RelativeLayout rel3=(RelativeLayout)dialog.findViewById(R.id.rel3) ;


                    TextView calender=(TextView)dialog.findViewById(R.id.calender) ;
                    TextView chattxt=(TextView)dialog.findViewById(R.id.chat) ;
                    TextView grade=(TextView)dialog.findViewById(R.id.grade) ;
                    TextView stutab=(TextView)dialog.findViewById(R.id.stutab) ;
                    Button buttonSave=(Button) dialog.findViewById(R.id.buttonSave);
                    final CheckBox checkbox1=(CheckBox)dialog.findViewById(R.id.checkbox1) ;
                    final CheckBox checkbox2=(CheckBox)dialog.findViewById(R.id.checkbox2) ;
                    final  CheckBox  checkbox3=(CheckBox)dialog.findViewById(R.id.checkbox3) ;
                    final CheckBox checkbox4=(CheckBox)dialog.findViewById(R.id.checkbox4) ;
                    rel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            checkbox1.setVisibility(View.VISIBLE);
                            c1=1;
                            if (checkbox1.isChecked()) {
                                cal="true";
                                Log.e("cal",""+cal);
                            }
                            else if (!checkbox1.isChecked()) {
                                cal="\" \" ";

                            }
                        }
                    });

                    rel1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkbox2.setVisibility(View.VISIBLE);
                            c2=1;

                            if (checkbox2.isChecked()) {
                                chat="true";
                                Log.e("chat",""+chat);
                            }
                            else if (!checkbox2 .isChecked()) {
                                chat="\" \" ";

                            }

                        }
                    });
                    rel2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkbox3.setVisibility(View.VISIBLE);
                            c3=1;
                            if (checkbox3.isChecked()) {
                                grades="true";
                                Log.e("grades",""+grades);
                            }
                            else if (!checkbox3 .isChecked()) {
                                grades="\" \" ";

                            }
                        }
                    });
                    rel3.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            checkbox4.setVisibility(View.VISIBLE);
                            c4=1;

                            if (checkbox4.isChecked()) {
                                stu="true";
                                Log.e("stu",""+stu);
                            }
                            else if (!checkbox4 .isChecked()) {
                                stu="\" \" ";

                            }
                        }
                    });



                    dialog.show();
                    buttonSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                             feature=cal+" "+chat+" "+grades+" "+stu;
                             newfeature= feature.replace("null","");
                            Log.e("newfeature",""+newfeature);
                            text_features_detail.setText(newfeature);
                            dialog.dismiss();
                        }
                    });

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
                            Log.e("style", "" + style);
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

                    newTitle=edt_title.getText().toString();
                    semester1=semester.getText().toString();
                    coursecode1=coursecode.getText().toString();

                    Log.e("newTitle",""+newTitle);

                    Log.e("newTitle",""+newTitle);
                    Log.e("newTitle",""+newTitle);

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    TopicFragment topicFragment = new TopicFragment();
                    Bundle arg = new Bundle();
                    arg.putInt("fromEdit",fromEdit);
                    arg.putString("arrName", String.valueOf(arrName));
                    arg.putString("arrId", arrId);
                    arg.putString("newTitle", newTitle);
                    arg.putString("semester1", semester1);
                    arg.putString("coursecode1", coursecode1);
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




        ripple_edit_update.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                coursecodeval=coursecode.getText().toString();
                semeserval=semester.getText().toString();
                sr_title=edt_title.getText().toString();
                sr_passcode=edt_passcode.getText().toString();
                sr_classtppe=text_type_detail.getText().toString();

                if(sr_classtppe.contains("Instructor")){
                    sr_classtppeval="1";
                }
                else if(sr_classtppe.contains("Blended")){
                    sr_classtppeval="2";
                }
                else{
                    sr_classtppeval="3";
                }
                sr_topic=text_topic_detail.getText().toString();
                sr_feature=text_features_detail.getText().toString();
                sr_description=text_description_detail.getText().toString();


                Log.e("classidgetupdate",""+classidgetupdate);
                Log.e("srcreated",""+created);
                Log.e("srClassFragment",""+ClassFragment.Mem_Sch_Id);
                Log.e("srclassname",""+classname);
                Log.e("srsr_classtppeval",""+sr_classtppeval);
                Log.e("srtopic",""+topic);
                Log.e("srsr_description",""+sr_description);
                Log.e("srcheck",""+check);
                Log.e("srcoursecodeval",""+coursecodeval);
                Log.e("srsemeserval",""+semeserval);
                Log.e("srcal",""+cal);
                Log.e("srstu",""+stu);
                Log.e("srchat",""+chat);
                Log.e("srgrades",""+grades);

               new Update_Class().execute(classidgetupdate,created,ClassFragment.Mem_Sch_Id,sr_title,sr_classtppeval,sr_topic1,sr_description,check,semeserval,coursecodeval,cal,stu,chat,grades);

            }
        });

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
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    passcodes = obj.getString("clsid");

                    created = obj.getString("createdby");

                    String schoolid = obj.getString("schoolid");
                    Log.e("schoolid",""+schoolid);

                  classidgetupdate = obj.getString("classid");

                     classname = obj.getString("classname");
                    Log.e("classname",""+classname);

                    String classpassword = obj.getString("classpassword");

                     styles = obj.getString("style");

                    String topic_id = obj.getString("topic_id");

                    topics = obj.getString("topic_name");

                     desc = obj.getString("desc");
                    Log.e("desc",""+desc);

                    String class_full_image = obj.getString("class_full_image");

                    String class_thumb_image = obj.getString("class_thumb_image");
                    Log.e("class_thumb_image",""+class_thumb_image);



                    JSONObject obj1=obj.getJSONObject("Cls_Features"); {
                        Log.e("obj1",""+obj1);

                        String enable_calendar = obj1.optString("enable_calendar");
                        Log.e("enable_calendar",""+enable_calendar);

                        String enable_students_tab = obj1.optString("enable_students_tab");
                        Log.e("enable_students_tab",""+enable_students_tab);
                        String enable_chat = obj1.optString("enable_chat");
                        Log.e("enable_chat",""+enable_chat);
                        String enable_grades = obj1.optString("enable_grades");
                        Log.e("enable_grades",""+enable_grades);

                        /*if(enable_calendar==null){
                          //  enable_students_tab="false";
                        }

                        else if(enable_students_tab==null){
                            enable_students_tab="false";
                        }
                        else if(enable_chat==null){
                            enable_chat="false";
                        }

                        else if(enable_grades==null){
                            enable_grades="false";

                        }
*/


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

            /*    if(sr_title!=null) {
                    Log.e("sr_titlenotnull",""+sr_title);
                    edt_title.setText(sr_title);

                }
                else{
                    Log.e("classnameprint",""+classname);
                    Log.e("sr_titlenull",""+sr_title);*/
                newTitle=edt_title.getText().toString();
                Log.e("newTitleafter",""+newTitle1);
                if(newTitle1==null){

                   // Log.e("newTitle",""+newTitle);
                    edt_title.setText(classname);

                }
                    //edt_title.setText(classname);

                else{
                   // Log.e("newTitle",""+newTitle);
                   // Log.e("classname",""+classname);

                    edt_title.setText(newTitle1);
                }

                //edt_title.setText(classname);
                edt_passcode.setText(classidgetupdate);
                Log.e("passcodes",""+passcodes);
                Log.e("styles",""+styles);

                       /* if(fromsave==1){
                            text_topic_detail.setText(style);
                            Log.e("fromsaverec",""+fromsave);
                            Log.e("fromsaverecstyle",""+style);
                        }
*/
                if (style!=null){
                    if(style.contains("1")){
                        Log.e("visit","visit"+"");
                        //   styles= "Instructor";
                        text_type_detail.setText("Instructor");
                    }
                    else if(style.contains("2")){
                        //  styles= "Blended";
                        text_type_detail.setText("Blended");
                    }
                    else {
                        //  styles= "Self Paced";
                        text_type_detail.setText("Self Paced");

                    }

                }

                else if(styles.contains("1")){
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
                        /*if(fromsave==1){
                            Log.e("fromsave",""+fromsave);
                            text_topic_detail.setText(topic1);
                            Log.e("topic1",""+topic1);

                        }
                        else{
                            text_topic_detail.setText(topics);
                            Log.e("topics",""+topics);
                        }
                        Log.e("topics",""+topics);*/


                if(fromsave==1){
                    Log.e("fromsave",""+fromsave);
                    text_topic_detail.setText(topic1);
                    Log.e("topic1",""+topic1);

                }
                else if (fromsave!=1 &topic1==null){
                    text_topic_detail.setText(topics);
                    Log.e("topics",""+topics);


                }

                else if(topic1==null){
                    Log.e("topic1",""+topic1);
                    text_topic_detail.setText(topics);
                }
                else{
                    Log.e("topic1notnull",""+topic1);
                    text_topic_detail.setText(topic1);
                }










                // text_description_detail.setText(desc);



                if(fromdes==1){
                    Log.e("fromdesisRes",""+fromdes);
                    //   Log.e("fromsave",""+fromsave);
                    text_description_detail.setText(des);
                    Log.e("des",""+des);


                }
                else if (fromdes!=1 &des==null){
                    text_description_detail.setText(desc);
                    Log.e("desc",""+desc);


                }

                else if(des==null){
                    Log.e("desnull",""+des);
                    text_description_detail.setText(desc);
                }
                else{
                    Log.e("desnotnull",""+des);
                    text_description_detail.setText(des);
                }









            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("NotSEtdata","");
            }
        }
    }










        class Update_Class extends AsyncTask<String, Integer, String> {


            @Override
            protected String doInBackground(String... params) {

                return WSConnector.update_class(params[0], params[1], params[2],params[3],params[4],params[5],params[6],params[7],params[8],params[9],params[10],params[11],params[12],params[13]);

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

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ClassFragment classFragment = new ClassFragment();

                fragmentTransaction.replace(android.R.id.content, classFragment).addToBackStack(null);

                fragmentTransaction.commit();


                //    Log.e("REsulT", "" + result);
               /* if (result.contains("true")) {

                    //updateTeacherLogIn(result);


                } else if (result.contains("false")) {
                    Toast.makeText(getApplicationContext(), "Wrong User", Toast.LENGTH_SHORT).show();

                }*/
            }


        }
    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        relative.setPivotX(pivot.x);
        relative.setPivotY(pivot.y);
        relative.setScaleX(scaleX);
        relative.setScaleY(scaleY);
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




            } else if (result.contains("false")) {


            }
        }





        }















        @Override
    public void onStart() {
        super.onStart();

        //fromdes=getIntent().getIntExtra("fromdes",0);

        /*preferences=PreferenceManager.getDefaultSharedPreferences(EditClassFragment.this);
        fromdes=preferences.getInt("fromdes",0);
        Log.e("fromdes", "" + fromdes);*/
        text_topic_detail.setText(topic1);
        Log.e("topic111",""+topic1);

        if(fromdes==1) {
            text_description_detail.setText(des);
            Log.e("des", "" + des);
            Log.e("fromdesfdg", "" + fromdes);
        }
        semester.setText(semester2);
        coursecode.setText(coursecode2);

       /* if(newTitle!=null){

            Log.e("newTitle",""+newTitle);
            edt_title.setText(newTitle);
        }
        //edt_title.setText(classname);

        else{
            Log.e("newTitle",""+newTitle);
            Log.e("classname",""+classname);

            edt_title.setText(classname);
        }*/
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
        Log.e("sr_title11111",""+sr_title);
        Log.e("classname11111",""+classname);
        Log.e("topic1111",""+topic1);
        if(fromdes==1) {
        text_description_detail.setText(des);
        Log.e("des11111",""+des);

           /* if(newTitle!=null){

                Log.e("newTitle",""+newTitle);
                edt_title.setText(newTitle);
            }
            //edt_title.setText(classname);

            else{
                Log.e("newTitle",""+newTitle);
                Log.e("classname",""+classname);

                edt_title.setText(classname);
            }*/

    }}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
       // Intent gotoclass=new Intent(EditClassFragment.this,Cla)


        /*FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        ClassFragment artFragment=new ClassFragment();

        fragmentTransaction.replace(android.R.id.content,artFragment).addToBackStack(null);

        fragmentTransaction.commit();*/
    }
}