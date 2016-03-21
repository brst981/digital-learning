package app.com.digitallearning.StudentModule;

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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.andexert.library.RippleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.com.digitallearning.R;
import app.com.digitallearning.StudentModule.Model.Student_Login_Data;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 1/28/16.
 */
public class StudentLoginFragment extends Fragment {
    RippleView rippleViewLogin;
    View rootview;
    ProgressDialog dlg;
    EditText edt_username, edt_pwd;
    ImageButton back;
    String schoolID, schoolName,name,password, Sch_Mem_id,Mem_Sch_Id;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.activity_student_login, container, false);
        rippleViewLogin = (RippleView) rootview.findViewById(R.id.ripple_login);
        edt_username = (EditText) rootview.findViewById(R.id.edt_username_student);
        back=(ImageButton)rootview.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });
        dlg = new ProgressDialog(getActivity());
        edt_pwd = (EditText) rootview.findViewById(R.id.edt_pwd_student);
        rippleViewLogin.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

                name=edt_username.getText().toString();
                password=edt_pwd.getText().toString();



                preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("name", name);
                editor.putString("schoolName", schoolName);
                editor.putString("password", password);
                editor.putString("schoolID", schoolID);
                editor.commit();

                new StudentLogIn().execute(name, password, schoolID);



            }
        });
        schoolID = getArguments().getString("SchoolID");
        Log.e("schoolID", "" + schoolID);
        schoolName = getArguments().getString("SchoolName");
        Log.e("schoolName", "" + schoolName);
        return rootview;
    }



    class StudentLogIn extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.login(params[0], params[1], params[2]);

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
            Log.e("classlisting",""+result);
            if (result.contains("true")) {

                updateStudentLogIn(result);



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




        private void updateStudentLogIn(String success) {

            try {

                JSONObject jsonObject = new JSONObject(success);

                Sch_Mem_id = jsonObject.getString("Sch_Mem_id");
                Log.e("Sch_Mem_id",""+Sch_Mem_id);
                Mem_Sch_Id = jsonObject.getString("Mem_Sch_Id");
                Log.e("Mem_Sch_Id",""+Mem_Sch_Id);
                String Mem_Name = jsonObject.getString("Mem_Name");

                String Mem_Emailid = jsonObject.getString("Mem_Emailid");
                preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                editor = preferences.edit();
                editor.putString("Sch_Mem_id", Sch_Mem_id);
                editor.putString("Mem_Sch_Id", Mem_Sch_Id);
                editor.commit();


                JSONArray arr = jsonObject.getJSONArray("class_data");

                for (int i = 0; i < arr.length(); i++) {

                    Student_Login_Data student_login_data=new Student_Login_Data();
                    JSONObject obj = arr.getJSONObject(i);

                    String class_id = obj.getString("class_id");
                    String class_name = obj.getString("class_name");
                    String subject = obj.getString("subject");
                    String students = obj.getString("students");

                    Log.e("class_id",""+class_id);
                    Log.e("class_name",""+class_name);
                    Log.e("subject",""+subject);
                    Log.e("students",""+students);

                }
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                StudentClass studentClass = new StudentClass();
                fragmentTransaction.replace(R.id.container, studentClass).addToBackStack(null);
                fragmentTransaction.commit();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}