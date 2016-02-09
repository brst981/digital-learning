package app.com.digitallearning.TeacherModule.Classes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;

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
    RelativeLayout relative_class_type, relative_topic, relative_description, relative_features,relative;
    RippleView ripple_edit_update;
    ProgressDialog dlg;
    Switch switch_upload;
    ImageButton back;
    private Toolbar mToolbar;
    final CharSequence[] features = {"Calender", "Chat", "Grades", "Student Tab"};
    final static CharSequence[] classtype1 = {"Instructor", "Blended", "Self Paced"};

    // Fragment mFragment;
    public static String style, classType1, topic1, topic, description, des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_edit_class);


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
        semester = (EditText) findViewById(R.id.semester);
        coursecode = (EditText) findViewById(R.id.coursecode);
        edt_passcode = (EditText) findViewById(R.id.edt_passcode);
        switch_upload = (Switch) findViewById(R.id.switch_upload);

        dlg = new ProgressDialog(EditClassFragment.this);


        /*AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");

       headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Edit Class");*/


            title = getIntent().getStringExtra("title");
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
            classId = getIntent().getStringExtra("myListclassId");
            Log.e("classIdInEdit", "" + classId);

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
    }







   /* @Override
    public void onStart() {
        super.onStart();
        Log.e("inside Start",""+ClassFragment.classtpye);
        if (ClassFragment.classtpye.equalsIgnoreCase("1")){

            text_type_detail.setText("Instructor");
        }
        else  if(ClassFragment.classtpye.equalsIgnoreCase("2")){
            text_type_detail.setText("Blended");
        }
        else  if(ClassFragment.classtpye.equalsIgnoreCase("3")){
            text_type_detail.setText("Self-Paced");
        }
    }*/

