package app.com.digitallearning.StudentModule;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.com.digitallearning.R;
import app.com.digitallearning.StudentModule.Model.Student_Info;
import app.com.digitallearning.StudentModule.Model.Student_Social_Data;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 2/3/16.
 */
public class Info  extends Fragment {
    View rootview;
    LinearLayout deslin;
    ImageButton back;
    ProgressDialog dlg;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    SharedPreferences preferences;
    String Sch_Mem_id,name;
    TextView edtemailid,edtfirstname,edtphonenumber,edtwhatsapp,edtwechat;
    String firstname,lastName,email,photo,phonenumber,description,social_id,wechat,whatsapp,cla_classid;
    ArrayList<Student_Info> studentinfolist=new ArrayList<Student_Info>();
    ArrayList<Student_Social_Data> socialdatalist=new ArrayList<Student_Social_Data>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.info, container, false);

        edtemailid=(TextView)rootview.findViewById(R.id.edtemailid);
        edtfirstname=(TextView)rootview.findViewById(R.id.edtfirstname);
        edtphonenumber=(TextView)rootview.findViewById(R.id.edtphonenumber);
        edtwhatsapp=(TextView)rootview.findViewById(R.id.edtwhatsapp);
        edtwechat=(TextView)rootview.findViewById(R.id.edtwechat);



        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Sch_Mem_id = preferences.getString("Sch_Mem_id", "");
        Log.e("Sch_Mem_id", "" + Sch_Mem_id);
        name=preferences.getString("name","");
        cla_classid=preferences.getString("cla_classid","");
        Log.e("name",""+name);
        dlg=new ProgressDialog(getActivity());
        back=(ImageButton)rootview.findViewById(R.id.back);
        deslin=(LinearLayout)rootview.findViewById(R.id.deslin) ;
        imageButtonZoomIn = (ImageButton)rootview. findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) rootview.findViewById(R.id.img_zoom_out);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
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


        new Student_Profile().execute(cla_classid);

        return rootview;

    }
    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        deslin.setPivotX(pivot.x);
        deslin.setPivotY(pivot.y);
        deslin.setScaleX(scaleX);
        deslin.setScaleY(scaleY);
    }


    class Student_Profile extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Student_Info(params[0],params[1]);
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
            Log.e("Student_Profile", "" + result);

            if (result.contains("true")) {
               updateTeacher_getProfile(result);

            } else if (result.contains("false")) {
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
            }
        }
        private void updateTeacher_getProfile(String success) {

            try {

                JSONObject jsonObject = new JSONObject(success);

                JSONArray arr = jsonObject.getJSONArray("data");

                JSONObject obj;
                for (int i = 0; i < arr.length(); i++) {
                    obj = arr.getJSONObject(i);


                    firstname = obj.getString("firstname");
                    lastName = obj.getString("lastName");
                    email = obj.getString("email");
                    photo = obj.getString("photo");
                    description = obj.getString("description");
                    phonenumber = obj.getString("phonenumber");
                    JSONArray arr1 = obj.getJSONArray("social");
                    JSONObject obj1;
                    for (int j = 0; j < arr1.length(); j++) {
                        obj1 = arr1.getJSONObject(j);
                        social_id=obj1.getString("social_id");
                        wechat=obj1.getString("wechat");
                        whatsapp=obj1.getString("whatsapp");
                        edtfirstname.setText(firstname+" "+ lastName);
                        edtemailid.setText(email);
                        edtphonenumber.setText(phonenumber);
                        edtwechat.setText(wechat);
                        edtwhatsapp.setText(whatsapp);

                    }}


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
   /* Student_Info student_info=new Student_Info();
student_info.setFirstname(obj.optString("firstname"));
        student_info.setLastName(obj.optString("lastName"));
        student_info.setEmail(obj.optString("email"));
        student_info.setPhoto(obj.optString("photo"));
        student_info.setDescription(obj.optString("description"));
        student_info.setPhonenumber(obj.optString("phonenumber"));
        studentinfolist.add(student_info);

        Student_Social_Data student_social_data=new Student_Social_Data();
        student_social_data.setSocial_id(obj1.optString("social_id"));
        student_social_data.setWechat(obj1.optString("wechat"));
        student_social_data.setWhatsapp(obj1.optString("whatsapp"));
        socialdatalist.add(student_social_data);*/