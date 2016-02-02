package app.com.digitallearning.TeacherModule.Classes;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.ClassFragment;

/**
 * Created by ${ShalviSharma} on 12/22/15.
 */
public class EditClassFragment extends Fragment {
    View rootview;
    TextView headerTitle,text_type_detail,text_topic_detail,text_description_detail;
    String title,classtype,classId,userid,schid,passcode,classType,topic,description,reclasstype;
    EditText edt_title,edt_passcode;
    RelativeLayout relative_class_type,relative_topic,relative_description,relative_features;
    RippleView ripple_edit_update;
    ProgressDialog dlg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_edit_class, container, false);

        text_type_detail=(TextView)rootview.findViewById(R.id.text_type_detail);
        text_topic_detail=(TextView)rootview.findViewById(R.id.text_topic_detail);
        text_description_detail=(TextView)rootview.findViewById(R.id.text_description_detail);

        edt_title=(EditText)rootview.findViewById(R.id.edt_title);
        edt_passcode=(EditText)rootview.findViewById(R.id.edt_passcode);

        dlg=new ProgressDialog(getActivity());
      /*  AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");*/

       /// headerTitle = (TextView) activity.findViewById(R.id.mytext);

       /// headerTitle.setText("Edit Class");




     /* title=getArguments().getString("title");
        Log.e("titleOn Edit",""+title);

        passcode=getArguments().getString("passcode");
        Log.e("passcodeOn Edit",""+passcode);
        edt_passcode.setText(passcode);

       // classType=getArguments().getString("classType");

        ClassFragment.classtpye =  classType;
        Log.e("ClassFragment.classtpye",""+ClassFragment.classtpye);
        Log.e("classTypeOn Edit",""+classType);
        Log.e("reclasstype",""+reclasstype);
*/




//        ClassTypeFragment.classtpye = reclasstype;
//        Log.e("EditClassclasstpye",""+reclasstype);

        /*text_type_detail.setText(classType);
         if(reclasstype.equalsIgnoreCase("1")){
            text_type_detail.setText("Instructor");
        }

        else  if(reclasstype.equalsIgnoreCase("2")){
            text_type_detail.setText("Blended");
        }
        else  if(reclasstype.equalsIgnoreCase("3")){
            text_type_detail.setText("Self-Paced");
        }

        topic=getArguments().getString("topic");
        Log.e("topiceOn Edit",""+topic);
        text_topic_detail.setText(topic);

        description=getArguments().getString("description");
        Log.e("descriptionOn Edit",""+description);
        text_description_detail.setText(description);


        edt_title.setText(title);
        userid= ClassFragment.Sch_Mem_id;
        schid=ClassFragment.Mem_Sch_Id;
        classId=getArguments().getString("myListclassId");
        Log.e("classIdInEdit",""+classId);*/

        ripple_edit_update=(RippleView)rootview.findViewById(R.id.ripple_edit_update);
        relative_class_type=(RelativeLayout)rootview.findViewById(R.id.relative_class_type);
        relative_topic=(RelativeLayout)rootview.findViewById(R.id.relative_topic);

        relative_features=(RelativeLayout)rootview.findViewById(R.id.relative_features);
        relative_features.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                FeatureFragment featureFragment = new FeatureFragment();
                fragmentTransaction.replace(R.id.container, featureFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        relative_description=(RelativeLayout)rootview.findViewById(R.id.relative_description);
        relative_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DescriptionFragment descriptionFragment = new DescriptionFragment();
                fragmentTransaction.replace(R.id.container, descriptionFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        relative_class_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=1;
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ClassTypeFragment classTypeFragment = new ClassTypeFragment();
              /*  Bundle args = new Bundle();
                args.putInt("ID",a);*/
                fragmentTransaction.replace(R.id.container, classTypeFragment).addToBackStack(null);
               // classTypeFragment.setArguments(args);
                fragmentTransaction.commit();
            }
        });
        relative_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               /* FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TopicFragment topicFragment = new TopicFragment();
               *//* Bundle args = new Bundle();
                args.putInt("ID",a);*//*
                fragmentTransaction.replace(R.id.container, topicFragment).addToBackStack(null);
             //   topicFragment.setArguments(args);
                fragmentTransaction.commit();*/
            }
        });

        classtype=ClassFragment.classtpye;
        Log.e("classtypeInEDITCLAss",""+classtype);
        ripple_edit_update.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

            }
        });

        /*if (ClassFragment.classtpye.equalsIgnoreCase("1")){

            text_type_detail.setText("Instructor");
        }
        else  if(ClassFragment.classtpye.equalsIgnoreCase("2")){
            text_type_detail.setText("Blended");
        }
        else  if(ClassFragment.classtpye.equalsIgnoreCase("3")){
            text_type_detail.setText("Self-Paced");
        }/*/
        return rootview;
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
}
