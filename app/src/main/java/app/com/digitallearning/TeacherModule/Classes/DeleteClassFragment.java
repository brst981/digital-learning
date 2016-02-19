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
import android.support.v7.app.AppCompatActivity;
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
 * Created by ${ShalviSharma} on 12/22/15.
 */
public class DeleteClassFragment extends Fragment {
    View rootview;
    TextView headerTitle;
    RippleView ripple_delete;
    SharedPreferences preferences;
    String password,Sch_Mem_id,Mem_Sch_Id,cla_classid,editpassword;
    ProgressDialog dlg;
    EditText edt_password;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_delete_class, container, false);
        dlg=new ProgressDialog(getActivity());
        ripple_delete=(RippleView)rootview.findViewById(R.id.ripple_delete);
        edt_password=(EditText)rootview.findViewById(R.id.edt_password);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("");
       // activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  activity.getSupportActionBar().invalidateOptionsMenu();
        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        headerTitle.setText("Delete");

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        password=preferences.getString("password","");
        Log.e("password",""+password);
        Sch_Mem_id=preferences.getString("Sch_Mem_id","");
        Log.e("Sch_Mem_id",""+Sch_Mem_id);
        Mem_Sch_Id=preferences.getString("Mem_Sch_Id","");
        Log.e("Mem_Sch_Id",""+Mem_Sch_Id);
        cla_classid=preferences.getString("cla_classid","");
        Log.e("cla_classid",""+cla_classid);


        ripple_delete.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                if(edt_password.getText().toString().isEmpty()){
                    LogMessage.showDialog(getActivity(), null,
                            "Please enter password ", "OK");
                }
                else{

                    editpassword=edt_password.getText().toString();
                    Log.e("editpassword",""+editpassword);
                new Delete_class().execute(cla_classid , Sch_Mem_id , Mem_Sch_Id , editpassword );
            }}
        });

        return rootview;
    }
    class Delete_class extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Delete_class(params[0],params[1],params[2],params[3]);

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
