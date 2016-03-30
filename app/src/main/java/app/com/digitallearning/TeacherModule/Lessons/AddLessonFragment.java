package app.com.digitallearning.TeacherModule.Lessons;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.andexert.library.RippleView;

import java.text.SimpleDateFormat;
import java.util.Date;

import app.com.digitallearning.R;
import app.com.digitallearning.Utill.LogMessage;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${ShalviSharma} on 12/29/15.
 */
public class AddLessonFragment extends Fragment{
    View rootview;
    TextView headerTitle,txtDate;
    RippleView rippleViewPreview,ripple_save_lesson;
    AppCompatActivity activity;
    ProgressDialog dlg;
    SharedPreferences preferences;
    String Sch_Mem_id,cla_classid,title,videolink,currentdate,description;
    String lessontitle,lessondate,url,lessondescription;
    EditText edt_title_lesson,edt_videoUrl_lesson,edt_description_lesson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_add_lesson, container, false);
        rippleViewPreview=(RippleView)rootview.findViewById(R.id.ripple_preview_lesson) ;
        txtDate=(TextView)rootview.findViewById(R.id.txt_date_lesson) ;

        edt_title_lesson=(EditText) rootview.findViewById(R.id.edt_title_lesson) ;
        edt_videoUrl_lesson=(EditText) rootview.findViewById(R.id.edt_videoUrl_lesson) ;
        edt_description_lesson=(EditText) rootview.findViewById(R.id.edt_description_lesson) ;


        ripple_save_lesson=(RippleView)rootview.findViewById(R.id.ripple_save_lesson) ;
        dlg=new ProgressDialog(getActivity());
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Sch_Mem_id=preferences.getString("Sch_Mem_id","");
        Log.e("Sch_Mem_id",""+Sch_Mem_id);

        cla_classid=preferences.getString("cla_classid","");
        Log.e("cla_classid",""+cla_classid);
         activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
     //   activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        mToolbar = (Toolbar)ro findViewById(R.id.toolbar_actionbar);
//        setSupportActionBar(mToolbar);
 /*       NavigationActivity.mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "working", Toast.LENGTH_SHORT).show();
                 getFragmentManager().popBackStackImmediate();

                *//* FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LessonFragment lessonFragment = new LessonFragment();
                fragmentTransaction.replace(R.id.container, lessonFragment).addToBackStack(null);
                fragmentTransaction.commit();*//*
            }
        });*/
     //   getActivity().getActionBar().setHomeButtonEnabled(false);





        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Add Lesson");


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        final Date date = new Date();
        txtDate.setText(dateFormat.format(date));
        rippleViewPreview.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
            //    lessontitle=edt_title_lesson.getText().toString();
             //   lessondate=txtDate.getText().toString();
                url=edt_videoUrl_lesson.getText().toString();
                lessondescription=edt_description_lesson.getText().toString();

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle data=new Bundle();
                PreviewLessonFragment classFragment = new PreviewLessonFragment();
            //    data.putString("lessontitle",lessontitle);
            //    data.putString("lessondate",lessondate);
                data.putString("url",url);
                data.putString("lessondescription",lessondescription);
                fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                classFragment.setArguments(data);
                fragmentTransaction.commit();
            }
        });

        ripple_save_lesson.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                title=edt_title_lesson.getText().toString();
                videolink=edt_videoUrl_lesson.getText().toString();
                description=edt_description_lesson.getText().toString();
                currentdate=txtDate.getText().toString();
                Log.e("currentdate",""+currentdate);

                if(title==null){
                    LogMessage.showDialog(getActivity(), null,
                            "Please fill required Fields", "OK");
                }
                else if(description==null){
                    LogMessage.showDialog(getActivity(), null,
                            "Please fill required Fields", "OK");
                }
                else if(videolink==null){
                    LogMessage.showDialog(getActivity(), null,
                            "Please fill required Fields", "OK");
                }
                else{
                new Add_Lesson().execute(cla_classid,Sch_Mem_id,title,description,currentdate,videolink);}
            }
        });

        return rootview;
    }

    class Add_Lesson extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Add_Lesson(params[0],params[1],params[2],params[3],params[4],params[5]);

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
               // getActivity().finish();
                getFragmentManager().popBackStack();
            }
        }

    }
}
