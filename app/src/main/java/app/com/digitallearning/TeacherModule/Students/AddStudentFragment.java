package app.com.digitallearning.TeacherModule.Students;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;

import java.text.SimpleDateFormat;
import java.util.Date;

import app.com.digitallearning.R;
import app.com.digitallearning.Utill.LogMessage;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${ShalviSharma} on 1/1/16.
 */
public class AddStudentFragment extends Fragment {
    View rootview;
    TextView headerTitle;
    EditText edt_first_name, edt_last_name, edt_password, edt_email;
    RippleView ripple_save;
    String firstname, lastname, email, password, userid, cla_classid, schid,createddate;
    SharedPreferences preferences;
    ProgressDialog dlg;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_add_student, container, false);
        dlg = new ProgressDialog(getActivity());
       /* AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        headerTitle.setText("Add Student(s)");*/
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        userid = preferences.getString("Sch_Mem_id", "");
        cla_classid = preferences.getString("cla_classid", "");
        schid = preferences.getString("Mem_Sch_Id", "");

        edt_first_name = (EditText) rootview.findViewById(R.id.edt_first_name);
        edt_last_name = (EditText) rootview.findViewById(R.id.edt_last_name);
        edt_password = (EditText) rootview.findViewById(R.id.edt_password);
        edt_email = (EditText) rootview.findViewById(R.id.edt_email);
        ripple_save=(RippleView)rootview.findViewById(R.id.ripple_save);
        ripple_save.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

                String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z]+\\.+[a-zA-Z]+";
                firstname = edt_first_name.getText().toString();
                lastname = edt_last_name.getText().toString();
                email = edt_email.getText().toString();
                password = edt_password.getText().toString();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
                final Date date = new Date();
                Log.e("createdDate",""+date);
                createddate=String.valueOf(date);
                Log.e("createdDat",""+createddate);

                if(firstname==null){
                    LogMessage.showDialog(getActivity(), null,
                            "Please fill required Fields" , "OK");
                }
                else if(lastname==null){
                    LogMessage.showDialog(getActivity(), null,
                            "Please fill required Fields" , "OK");
                }
                else if(email==null){
                    LogMessage.showDialog(getActivity(), null,
                            "Please fill required Fields" , "OK");
                }
                else if (!email.matches(emailPattern)) {
                    LogMessage.showDialog(getActivity(), null,
                            "Incorrect email" , "OK");
                }
                else if(password==null){
                    LogMessage.showDialog(getActivity(), null,
                            "Please fill required Fields" , "OK");
                }
                else{
                new AddStudent().execute(userid,cla_classid,schid,firstname,lastname,email,password,createddate );}

            }
        });


        return rootview;
    }

    class AddStudent extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Add_Student(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]);

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
            Log.e("REsulTinAddSchedule", "" + result);
            if (result.contains("true")) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Student added").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                // TODO Auto-generated method stub
                                dialog.dismiss();
                               getFragmentManager().popBackStack();


                            }
                        });

                AlertDialog dialog = alertDialog.create();
                dialog.show();
                TextView messageText = (TextView) dialog
                        .findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER);


            } else if (result.contains("false")) {
                Toast.makeText(getActivity(), "Wrong User", Toast.LENGTH_SHORT).show();

            }
        }
    }
}