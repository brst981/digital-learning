package app.com.digitallearning.StudentModule;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andexert.library.RippleView;

import org.json.JSONException;
import org.json.JSONObject;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.ClassActivity;
import app.com.digitallearning.Utill.LogMessage;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 1/29/16.
 */
public class StudentEnroll extends Fragment {
    View rootview;
    TextView headerTitle,showclassid;
    String textHeader,clsid,Sch_Mem_id,password;
    SharedPreferences preferences;
    ProgressDialog dlg;
    RippleView ripple_create;
    public static StudentEnroll newInstance() {
        StudentEnroll mFragment = new StudentEnroll();
        /*Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);*/
        return mFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_student_enroll, container, false);
        showclassid=(TextView)rootview.findViewById(R.id.showclassid);
        dlg=new ProgressDialog(getActivity());
        ripple_create=(RippleView)rootview.findViewById(R.id.ripple_create);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        //   getActivity().getActionBar().setHomeButtonEnabled(false);

        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        headerTitle.setText("Enroll");
        preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
        clsid = preferences.getString("cls_clsid", "");
        Sch_Mem_id=preferences.getString("Sch_Mem_id","");
        password=preferences.getString("password","");
        showclassid.setText(clsid);
        ripple_create.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                new Student_Enroll_class().execute(clsid,Sch_Mem_id,password);
            }
        });

        return rootview;

    }
    class Student_Enroll_class extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Student_Enroll_class(params[0],params[1],params[2]);

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
            Log.e("DeleteAPI", "" + result);

            if (result.contains("true")) {

             /*   LogMessage.showDialog(getActivity(), null,
                        "Class successfully deleted", "OK");*/

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Class successfully deleted").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                                Intent deletetoclass=new Intent(getActivity(),ClassActivity.class);
                                startActivity(deletetoclass);
                                getActivity().finish();

                            }
                        });

                AlertDialog dialog = alertDialog.create();
                dialog.show();
                TextView messageText = (TextView) dialog
                        .findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER);





            } else if (result.contains("false")) {
                updateTeacherLogIn(result);
            /*    JSONObject obj=new JSONObject();
                String data=obj.getString("data");
                Log.e("data",""data);*/

                // Toast.makeText(getActivity(), "Wrong User", Toast.LENGTH_SHORT).show();

            }
        }
        private void updateTeacherLogIn(String success) {

            try {

                JSONObject jsonObject = new JSONObject(success);
                String data=jsonObject.getString("data");
                Log.e("data",""+data);
                LogMessage.showDialog(getActivity(), null,
                        "" +data, "OK");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}