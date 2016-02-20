package app.com.digitallearning.TeacherModule.Classes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.andexert.library.RippleView;

import org.json.JSONException;
import org.json.JSONObject;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.ClassActivity;
import app.com.digitallearning.Utill.LogMessage;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 1/27/16.
 */
public class SyllabusFragment extends Fragment{
    View rootview;
    RippleView ripple_edit_save;
    SharedPreferences preferences;
    String Sch_Mem_id,cla_classid;
    EditText sylbsTitle,des;
    String title,description;
    ProgressDialog dlg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_syllabus, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        des=(EditText)rootview.findViewById(R.id.des);
        sylbsTitle=(EditText)rootview.findViewById(R.id.sylbsTitle);
        dlg=new ProgressDialog(getActivity());
        Sch_Mem_id=preferences.getString("Sch_Mem_id","");
        Log.e("Sch_Mem_id",""+Sch_Mem_id);

        cla_classid=preferences.getString("cla_classid","");
        Log.e("cla_classid",""+cla_classid);
        new Get_syllabus().execute(cla_classid , Sch_Mem_id );

        ripple_edit_save=(RippleView)rootview.findViewById(R.id.ripple_edit_save);
        ripple_edit_save.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                //getFragmentManager().popBackStackImmediate();
                title=sylbsTitle.getText().toString();
                description=des.getText().toString();

                new Add_syllabus().execute(cla_classid , Sch_Mem_id , title , description );
            }
        });



        return rootview;
    }
    class Add_syllabus extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Add_syllabus(params[0],params[1],params[2],params[3]);

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










    class Get_syllabus extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Get_syllabus(params[0],params[1]);

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
            Log.e("Get_syllabusAPI", "" + result);

            if (result.contains("false")) {

             /*   LogMessage.showDialog(getActivity(), null,
                        "Class successfully deleted", "OK");*/

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("No data").setCancelable(false)
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





            } else if (result.contains("true")) {
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
