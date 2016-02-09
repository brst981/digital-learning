package app.com.digitallearning.TeacherModule;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    String name, password, schoolID, schoolName,Sch_Mem_id,Mem_Sch_Id;
    ArrayList<String> usreId, schoolId,className,classid,classSub,classStudent;
    ArrayList<String> arrId, arrName, arrChildId, arrChildNAme;
    RelativeLayout teacherlogin;
    ImageButton imageButtonZoomIn, imageButtonZoomOut,back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_teacher_login, container, false);
        rippleViewLogin = (RippleView) rootview.findViewById(R.id.ripple_login);
        text_school_name = (TextView) rootview.findViewById(R.id.text_school_name);
        teacherlogin=(RelativeLayout)rootview.findViewById(R.id.teacherlogin);
        imageButtonZoomIn = (ImageButton)rootview. findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton)rootview. findViewById(R.id.img_zoom_out);
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


                new TeacherLogIn().execute(name, password, schoolID);

              /*  FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ClassFragment classFragment = new ClassFragment();
                fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                fragmentTransaction.commit();*/

             new Before_Class_Listing().execute();
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
                Toast.makeText(getActivity(), "Wrong User", Toast.LENGTH_SHORT).show();

            }
        }
//{"success":true,"user_type":"Teacher","Sch_Mem_id":"2155","Mem_Sch_Id":"487","Mem_Type":"1, 4","Mem_Name":"Ashish",
// "Mem_Emailid":"brstdev@gmail.com","class_data":[{"class_id":"183599","cls_createdby":"2155","cls_name":"1",
// "Cls_desc":"Test","subject":"Training ","cla_classid":"1800","students":"0","cls_image":"","orderid":"649",
// "new_orderid":"125"},

        private void updateTeacherLogIn(String success) {

            try {

                JSONObject jsonObject = new JSONObject(success);
                Log.e("jsonObject", "" + jsonObject);

                 Sch_Mem_id = jsonObject.getString("Sch_Mem_id");
                Log.e("Sch_Mem_id", " " + Sch_Mem_id);

                 Mem_Sch_Id = jsonObject.getString("Mem_Sch_Id");
                Log.e("Mem_Sch_Id", " " + Mem_Sch_Id);

                String Mem_Name = jsonObject.getString("Mem_Name");
                Log.e("Mem_Name", " " + Mem_Name);

                String Mem_Emailid = jsonObject.getString("Mem_Emailid");
                Log.e("Mem_Emailid", " " + Mem_Emailid);


                JSONArray arr = jsonObject.getJSONArray("class_data");
                Log.e("arr", " " + arr);
                for (int i = 0; i <= arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Log.e("obj", "" + obj);
                    String class_id = obj.getString("class_id");
                    Log.e("class_id", "" + class_id);
                    String cls_createdby = obj.getString("cls_createdby");
                    Log.e("cls_createdby", "" + cls_createdby);
                    String cls_name = obj.getString("cls_name");
                    Log.e("cls_name", "" + cls_name);
                    String Cls_desc = obj.getString("Cls_desc");
                    Log.e("Cls_desc", "" + Cls_desc);
                    String subject = obj.getString("subject");
                    Log.e("subject", "" + subject);
                    String cla_classid = obj.getString("cla_classid");
                    Log.e("cla_classid", "" + cla_classid);
                    String students = obj.getString("students");
                    Log.e("students", "" + students);
                    String cls_image = obj.getString("cls_image");
                    Log.e("cls_image", "" + cls_image);
                    String orderid = obj.getString("orderid");
                    Log.e("orderid", "" + orderid);

                    String new_orderid = obj.getString("new_orderid");
                    Log.e("new_orderid", "" + new_orderid);


                    className.add(cls_name);
                    Log.e("listCname",""+className);
                    classStudent.add(students);
                    Log.e("listCstu",""+classStudent);
                    classSub.add(subject);
                    Log.e("listCsub",""+classSub);
                    classid.add(cla_classid);


                   // new Before_Class_Listing().execute();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

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

                    updateBefore_Class_Listing(result);


                } else if (result.contains("false")) {
                    Toast.makeText(getActivity(), "Wrong User", Toast.LENGTH_SHORT).show();

                }
            }


            private void updateBefore_Class_Listing(String success) {
                try {
                    JSONObject jsonObject = new JSONObject(success);
                    JSONArray arr = jsonObject.getJSONArray("data");
                    Log.e("Data", "" + arr);
                    for (int i = 0; i < arr.length(); i++) {
                        JSONObject obj = arr.getJSONObject(i);
                        //  Log.e("obj",""+obj);
                        String id = obj.getString("id");
                        //  Log.e("id", "" + id);
                        String name = obj.getString("name");
                        // Log.e("name", "" + name);
                        arrId.add(id);
                        //  Log.e("arrId",""+arrId);
                        arrName.add(name);
                        //  Log.e("arrName",""+arrName);
                        Object ss = obj.get("child");
                        if (ss instanceof JSONArray) {
                            JSONArray arr1 = obj.getJSONArray("child");
                            //  Log.e("arr1",""+arr1);
                            if (arr1.length() != 0) {
                                for (int j = 0; j < arr1.length(); j++) {
                                    JSONObject obj1 = arr1.getJSONObject(j);
                                    //  Log.e("obj1", "" + obj1);
                                    String id1 = obj1.getString("id");
                                    //  Log.e("id1", "" + id1);
                                    String name1 = obj1.getString("name");
                                    //  Log.e("name1", "" + name1);

                                    arrChildId.add(id1);
                                    //  Log.e("arrChildId",""+arrChildId);
                                    arrChildNAme.add(name1);
                                    //Log.e("arrChildNAme",""+arrChildNAme);
                                }
                            }
                        } else {
                            String child = obj.getString("child");
                            // Log.e("Json String",""+child);
                        }
                    }


                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    ClassFragment classFragment = new ClassFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("arrId", String.valueOf(arrId));
                    bundle.putString("userId",Sch_Mem_id);
                    bundle.putString("schoolId",Mem_Sch_Id);
                    bundle.putString("arrName", String.valueOf(arrName));
                    bundle.putString("arrChildId", String.valueOf(arrChildId));
                    bundle.putString("arrChildNAme", String.valueOf(arrChildNAme));

                    bundle.putString("className", String.valueOf(className));
                    bundle.putString("classStudent", String.valueOf(classStudent));
                    bundle.putString("classSub", String.valueOf(classSub));
                    bundle.putString("classid", String.valueOf(classid));


                    Log.e("userIdSch_Mem_id", "" + Sch_Mem_id);
                    Log.e("userIdMem_Sch_Id", "" + Mem_Sch_Id);

                    fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                    classFragment.setArguments(bundle);
                    fragmentTransaction.commit();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("New exception", "" + e);
                }

            }

        }
}
