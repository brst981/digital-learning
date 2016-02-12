package app.com.digitallearning.TeacherModule.Classes;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andexert.library.RippleView;

import app.com.digitallearning.R;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${ShalviSharma} on 12/22/15.
 */
public class DeleteClassFragment extends Fragment {
    View rootview;
    TextView headerTitle;
    RippleView ripple_delete;
    SharedPreferences preferences;
    String password,Sch_Mem_id,Mem_Sch_Id,cla_classid;
    ProgressDialog dlg;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_delete_class, container, false);
        dlg=new ProgressDialog(getActivity());
        ripple_delete=(RippleView)rootview.findViewById(R.id.ripple_delete);
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

                new Delete_class().execute(cla_classid , Sch_Mem_id , Mem_Sch_Id , password );
            }
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
            Log.e("DeleteAPI",""+result);
            if (result.contains("true")) {

                // updateDelete_class(result);

               // Toast.makeText(getActivity(), "Class successfully created", Toast.LENGTH_SHORT).show();


            } else if (result.contains("false")) {
               // Toast.makeText(getActivity(), "Wrong User", Toast.LENGTH_SHORT).show();

            }
        }
    }

}
