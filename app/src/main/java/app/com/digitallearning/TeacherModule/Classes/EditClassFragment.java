package app.com.digitallearning.TeacherModule.Classes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import app.com.digitallearning.TeacherModule.ClassActivity;
import app.com.digitallearning.TeacherModule.ClassFragment;
import app.com.digitallearning.Utill.GlobalClass;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${ShalviSharma} on 12/22/15.
 */
public class EditClassFragment extends FragmentActivity {

    TextView headerTitle, text_type_detail, text_topic_detail, text_description_detail, text_features_detail,edt_passcode;
    String title, classtype, classId, userid, schid, passcode, createdby, arrclassname, arrName, arrId, arrChildNAme, classid, newTopic, cal, chat, grades, stu, semeserval, coursecodeval;
    EditText edt_title,  semester, coursecode;
    String passcodes,classname,created,styles,topics,desc;
    RelativeLayout relative_class_type, relative_topic, relative_description, relative_features,relative;
    RippleView ripple_edit_update;
    ProgressDialog dlg;
    Switch switch_upload;

    ImageButton back;
    String check="0";
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    private Toolbar mToolbar;
    public static String top,newtopicsel;
    final CharSequence[] features = {"Calender", "Chat", "Grades", "Student Tab"};
    final static CharSequence[] classtype1 = {"Instructor", "Blended", "Self Paced"};
    int fromsave;
    int fromdes=0;
    int id;
    SharedPreferences preferences;
    // Fragment mFragment;
    public static String style, classType1, topic1, topic, description, des,singleClassID,sr_topic1;
    int c1,c2,c3,c4;
    int fromEdit=12;
    int updateEdit=20;
    String cal1,chat1,enable1,tab1,topic_id;
    public static String textcal,textchat,textenable,texttab,feature,newfeature=null;
    static  String newTitle,newTitle1,semester1,semester2,coursecode1,coursecode2,total,newtotal,ondialog;
    String sr_title, sr_passcode,sr_classtppe,sr_classtppeval,sr_topic,sr_feature,sr_description,classidgetupdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_class);

        dlg=new ProgressDialog(EditClassFragment.this);
        fromsave=getIntent().getIntExtra("fromsave",0);
        newTitle1=getIntent().getStringExtra("newTitle");
        semester2=getIntent().getStringExtra("semester");

        coursecode2=getIntent().getStringExtra("coursecode");


        fromdes=getIntent().getIntExtra("fromdes",0);


        new Edit_Class().execute(singleClassID,ClassFragment.Sch_Mem_id,ClassFragment.Mem_Sch_Id);


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


        edt_passcode = (TextView) findViewById(R.id.edt_passcode);
        switch_upload = (Switch) findViewById(R.id.switch_upload);

        switch_upload.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(isChecked){
                    check="1";

                }
                else{
                    check="0";

                }

            }
        });


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

                    feature="";
                    newfeature="";
                    textcal=" ";
                    textchat=" ";
                    textenable=" ";
                    texttab=" ";

                    text_features_detail.setText("");

                    final Dialog dialog = new Dialog(EditClassFragment.this);
                    feature="";
                    newfeature="";
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
                                textcal="Calender";
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
                                textchat="Chat";
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
                                textenable="Grades";

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
                                texttab="Student Tab";

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
                            feature="";
                            newfeature="";

                             feature=textcal+" "+textchat+" "+textenable+" "+texttab;
                             newfeature= feature.replace("null","");

                                text_features_detail.setText(newfeature);
                            ondialog=newfeature;

                            dialog.dismiss();
                            feature="";
                            newfeature="";

                        }
                    });
                }


            });

            relative_description = (RelativeLayout) findViewById(R.id.relative_description);
            relative_description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    DescriptionFragment descriptionFragment = new DescriptionFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString("text_description_detail",text_description_detail.getText().toString());
                    fragmentTransaction.replace(android.R.id.content, descriptionFragment).addToBackStack(null);
                    descriptionFragment.setArguments(bundle);
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


            relative_topic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    newTitle=edt_title.getText().toString();
                    semester1=semester.getText().toString();
                    coursecode1=coursecode.getText().toString();

                    FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    TopicFragment topicFragment = new TopicFragment();
                    Bundle arg = new Bundle();
                    id=6;
                    arg.putInt("id1",id);
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


                if(sr_topic1==null){
                    sr_topic1=topic_id;
                }
              //  cal1,chat1,enable1,tab1  cal,stu,chat,grades
                if(cal1!=null){
                    cal= String.valueOf(true);
                }
                else if(chat1!=null){
                    chat= String.valueOf(true);
                }
                else if(enable1!=null){
                    grades= String.valueOf(true);
                }
                else if(tab1!=null){
                    stu= String.valueOf(true);
                }
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
            Log.e("singleclass",""+result);
            dlg.dismiss();

            if (result.contains("true")) {

                updateTeacherLogIn(result);


            } else if (result.contains("false")) {


            }
        }


        private void updateTeacherLogIn(String success) {

            try {

                JSONObject jsonObject = new JSONObject(success);

                JSONArray arr = jsonObject.getJSONArray("data");

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    passcodes = obj.getString("clsid");

                    created = obj.getString("createdby");

                    String schoolid = obj.getString("schoolid");


                  classidgetupdate = obj.getString("classid");

                     classname = obj.getString("classname");
                    String classpassword = obj.getString("classpassword");
                     styles = obj.getString("style");
                    Log.e("STYLE",""+styles);
                     topic_id = obj.getString("topic_id");
                    Log.e("topic_id",""+topic_id);
                    topics = obj.getString("topic_name");

                     desc = obj.getString("desc");


                    String class_full_image = obj.getString("class_full_image");

                    String class_thumb_image = obj.getString("class_thumb_image");

                    JSONObject obj1=obj.getJSONObject("Cls_Features"); {


                        String enable_calendar = obj1.optString("enable_calendar");


                        String enable_students_tab = obj1.optString("enable_students_tab");

                        String enable_chat = obj1.optString("enable_chat");

                        String enable_grades = obj1.optString("enable_grades");


                        if(enable_calendar.equalsIgnoreCase("true")){
                            cal1="Calender";

                        }
                        else{
                            cal1=" ";
                        }
                         if(enable_chat.equalsIgnoreCase("true")){
                             chat1="Chat";

                        }
                         else{
                             chat1=" ";
                         }
                         if(enable_grades.equalsIgnoreCase("true")){

                             enable1="Grades";

                        }

                         else{
                             enable1=" ";
                         }
                         if(enable_students_tab.equalsIgnoreCase("true")){
                             tab1="Student Tab";

                        }
                         else{
                             tab1=" ";
                         }

                        text_features_detail.setText(cal1+" "+chat1+" "+enable1+" "+tab1);
                    }

                }

                newTitle=edt_title.getText().toString();

                if(newTitle1!=null){


                    edt_title.setText(newTitle1);


                }
                    //edt_title.setText(classname);

                else{

                    edt_title.setText(classname);

                }

                //edt_title.setText(classname);
                edt_passcode.setText(classidgetupdate);



                Log.e("StyleSt",""+style);
                if(style==null){

                    if(styles.equals("1")){
                        text_type_detail.setText("Instructor");
                    }
                   else if(styles.equals("2")){
                        text_type_detail.setText("Blended");
                    }
                  else  if(styles.equals("3")){
                        text_type_detail.setText("Self Paced");
                    }


                }

           else  if(style==" "){

                    if(styles.equals("1")){
                        text_type_detail.setText("Instructor");
                    }
                    else if(styles.equals("2")){
                        text_type_detail.setText("Blended");
                    }
                    else  if(styles.equals("3")){
                        text_type_detail.setText("Self Paced");
                    }


                }

                else if(style!=null){

                    Log.e("Stylenot","" +style);
                    if(style.equals("1")){
                        text_type_detail.setText("Instructor");
                    }
                    else if(style.equals("2")){
                        text_type_detail.setText("Blended");
                    }
                    else  if(style.equals("3")){
                        text_type_detail.setText("Self Paced");
                    }
                }

                if(fromsave==1){

                    text_topic_detail.setText(topic1);


                }
                else if (fromsave!=1 &topic1==""){
                    text_topic_detail.setText(topics);



                }

                else if(topic1==null){

                    text_topic_detail.setText(topics);
                }
                else if(topic1==""){

                    text_topic_detail.setText(topics);
                }
                else{

                    text_topic_detail.setText(topics);
                }
                if(fromdes==1){

                    text_description_detail.setText(des);

                }
                else if (fromdes!=1 &des==null){
                    text_description_detail.setText(desc);


                }

                else if(des==null){
                    text_description_detail.setText(desc);
                }
                else{
                    text_description_detail.setText(des);
                }

            } catch (JSONException e) {
                e.printStackTrace();

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

                EditClassFragment.style=" ";

                TopicFragment.topic=" ";
                DescriptionFragment.description=" ";
                EditClassFragment.topic1=" ";




                TopicFragment.nwstring=" ";
                TopicFragment.otherstring=" ";
                EditClassFragment.newtopicsel="";
                GlobalClass.prefClear=true;


                Intent intenttoClass=new Intent(EditClassFragment.this , ClassActivity.class);
                startActivity(intenttoClass);
                finish();

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


            text_features_detail.setText(newfeature);



        text_topic_detail.setText(topic1);


        if(fromdes==1) {
            text_description_detail.setText(des);

        }
        semester.setText(semester2);
        coursecode.setText(coursecode2);

    }

    @Override
    protected void onPause() {
        super.onPause();

        text_features_detail.setText(newfeature);
        text_topic_detail.setText(topic1);

        if(fromdes==1) {
        text_description_detail.setText(des);

    }}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        EditClassFragment.style=" ";
        TopicFragment.topic=" ";
        DescriptionFragment.description=" ";
        EditClassFragment.topic1=" ";


        TopicFragment.nwstring=" ";
        TopicFragment.otherstring=" ";
        EditClassFragment.newtopicsel="";
        GlobalClass.prefClear=true;



    }
}