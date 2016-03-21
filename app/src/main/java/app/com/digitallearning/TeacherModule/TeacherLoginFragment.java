package app.com.digitallearning.TeacherModule;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.com.digitallearning.LoginActivity;
import app.com.digitallearning.R;
import app.com.digitallearning.Utill.GlobalClass;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${ShalviSharma} on 12/18/15.
 */
public class TeacherLoginFragment extends Fragment {
    RippleView rippleViewLogin;
    View rootview;
    TextView text_school_name;
    ProgressDialog dlg;
    EditText edt_username, edt_pwd;
    String name, password, schoolID, schoolName,Sch_Mem_id,Mem_Sch_Id,cla_classid;
    ArrayList<String> usreId, schoolId,className,classid,classSub,classStudent;
    ArrayList<String> arrId, arrName, arrChildId, arrChildNAme;
    RelativeLayout teacherlogin;
    ImageButton imageButtonZoomIn, imageButtonZoomOut,back;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private static final int CONTENT_VIEW_ID = 0x7f0c006c;
    CheckBox checked;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_teacher_login, container, false);
        rippleViewLogin = (RippleView) rootview.findViewById(R.id.ripple_login);
        text_school_name = (TextView) rootview.findViewById(R.id.text_school_name);
        teacherlogin=(RelativeLayout)rootview.findViewById(R.id.teacherlogin);
        imageButtonZoomIn = (ImageButton)rootview. findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton)rootview. findViewById(R.id.img_zoom_out);
        checked=(CheckBox)rootview.findViewById(R.id.checkbox_remember);
        checked.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true){
                    GlobalClass.rememberMe=true;
                    Toast.makeText(getActivity(),"Checked",Toast.LENGTH_SHORT).show();
                }
                else if(isChecked=false){
                    GlobalClass.rememberMe=false;
                    Toast.makeText(getActivity(),"Unchecked",Toast.LENGTH_SHORT).show();
                }
            }
        });
        back=(ImageButton)rootview.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        arrId = new ArrayList<>();
        arrName = new ArrayList<>();
        arrChildId = new ArrayList<>();
        arrChildNAme = new ArrayList<>();
        className = new ArrayList<>();
        classSub = new ArrayList<>();
        classStudent = new ArrayList<>();
        classid = new ArrayList<>();

        usreId = new ArrayList<>();
        schoolId = new ArrayList<>();


        schoolID = getArguments().getString("SchoolID");
        Log.e("schoolID", "" + schoolID);
        schoolName = getArguments().getString("SchoolName");
        Log.e("schoolName", "" + schoolName);
        text_school_name.setText(schoolName);

        edt_username = (EditText) rootview.findViewById(R.id.edt_username);
        dlg = new ProgressDialog(getActivity());
        edt_pwd = (EditText) rootview.findViewById(R.id.edt_pwd);
        rippleViewLogin.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                name = edt_username.getText().toString();
                Log.e("name", "" + name);
                password = edt_pwd.getText().toString();
                Log.e("password", "" + password);
                preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("name", name);
                editor.putString("schoolName", schoolName);
                editor.putString("password", password);
                editor.putString("schoolID", schoolID);
                editor.commit();


                new TeacherLogIn().execute(name, password, schoolID);

              /*  FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ClassFragment classFragment = new ClassFragment();
                fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                fragmentTransaction.commit();*/

            // new Before_Class_Listing().execute();
            }
        });
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
    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        teacherlogin.setPivotX(pivot.x);
        teacherlogin.setPivotY(pivot.y);
        teacherlogin.setScaleX(scaleX);
        teacherlogin.setScaleY(scaleY);
    }

    class TeacherLogIn extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.login(params[0], params[1], params[2]);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dlg.setMessage("Authenticating User.....");
            dlg.setCancelable(false);
            dlg.show();


        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dlg.dismiss();
           Log.e("REsulT", "" + result);
            if (result.contains("true")) {

                updateTeacherLogIn(result);


            } else if (result.contains("false")) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Wrong Credentials").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();

                            }
                        });

                AlertDialog dialog = alertDialog.create();
                dialog.show();
                TextView messageText = (TextView) dialog
                        .findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER);
            }
        }
// {"success":true,"user_type":"Student","Sch_Mem_id":"2279","Mem_Sch_Id":"487","Mem_Type":"16","Mem_Name":"stu","Mem_Emailid":"ram@rkm.com",
// "class_data":[{"class_id":"1837","class_name":"k","subject":"Music","students":"1"}]}

        private void updateTeacherLogIn(String success) {

            try {

                JSONObject jsonObject = new JSONObject(success);

                 Sch_Mem_id = jsonObject.getString("Sch_Mem_id");

                 Mem_Sch_Id = jsonObject.getString("Mem_Sch_Id");

                String Mem_Name = jsonObject.getString("Mem_Name");

                String Mem_Emailid = jsonObject.getString("Mem_Emailid");
                preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                editor = preferences.edit();
                editor.putString("Sch_Mem_id", Sch_Mem_id);
                editor.putString("Mem_Sch_Id", Mem_Sch_Id);
                editor.commit();


                JSONArray arr = jsonObject.getJSONArray("class_data");

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    String class_id = obj.getString("class_id");

                    String cls_createdby = obj.getString("cls_createdby");

                    String cls_name = obj.getString("cls_name");

                    String Cls_desc = obj.getString("Cls_desc");

                    String subject = obj.getString("subject");

                    cla_classid = obj.getString("cla_classid");

                    String students = obj.getString("students");

                    String cls_image = obj.getString("cls_image");

                    String orderid = obj.getString("orderid");


                    String new_orderid = obj.getString("new_orderid");


                    className.add(cls_name);
                    classStudent.add(students);
                    classSub.add(subject);
                    classid.add(cla_classid);



                }
                Intent intenttoClass=new Intent(getActivity() , ClassActivity.class);
                startActivity(intenttoClass);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


}
