package app.com.digitallearning.TeacherModule.Resource;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.andexert.library.RippleView;

import app.com.digitallearning.R;
import app.com.digitallearning.Utill.LogMessage;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${ShalviSharma} on 12/29/15.
 */
public class AddResourceFragment extends Fragment{
    View rootview;
    TextView headerTitle,txtDate;
    RippleView ripple_save,rippleViewPreview;
    EditText edt_title_resource,edt_description_resource;
    SharedPreferences preferences;
    String Sch_Mem_id,Mem_Sch_Id,cla_classid, title, description;
    ProgressDialog dlg;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_add_resource, container, false);
        dlg=new ProgressDialog(getActivity());
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Sch_Mem_id=preferences.getString("Sch_Mem_id","");
        Log.e("Sch_Mem_id",""+Sch_Mem_id);
        Mem_Sch_Id=preferences.getString("Mem_Sch_Id","");
        cla_classid=preferences.getString("cla_classid","");
        Log.e("cla_classid",""+cla_classid);
        ripple_save=(RippleView)rootview.findViewById(R.id.ripple_save) ;
        rippleViewPreview=(RippleView)rootview.findViewById(R.id.ripple_preview) ;
        edt_title_resource=(EditText)rootview.findViewById(R.id.edt_title_resource);
        edt_description_resource=(EditText)rootview.findViewById(R.id.edt_description_resource);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("");
        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        headerTitle.setText("Add Class Resource");



        ripple_save.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
             /*   FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                PreviewLessonFragment classFragment = new PreviewLessonFragment();
                fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                fragmentTransaction.commit();*/
                title=edt_title_resource.getText().toString();
                description=edt_description_resource.getText().toString();

                if(title==null){
                    LogMessage.showDialog(getActivity(), null,
                            "Please fill required Fields", "OK");
                }
                else if(description==null){
                    LogMessage.showDialog(getActivity(), null,
                            "Please fill required Fields", "OK");
                }
                else {
                    new Add_Resource().execute(cla_classid, Sch_Mem_id, Mem_Sch_Id, title, description);
                }
            }
        });

        rippleViewPreview.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                title=edt_title_resource.getText().toString();
                description=edt_description_resource.getText().toString();
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Preview classFragment = new Preview();
                Bundle bundle=new Bundle();
                bundle.putString("title",title);
                bundle.putString("des",description);
                fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                classFragment.setArguments(bundle);
                fragmentTransaction.commit();

            }
        });
        return rootview;
    }




    class Add_Resource extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Add_Resource(params[0],params[1],params[2],params[3],params[4]);

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
            Log.e("Add_ResourceAPI", "" + result);

            if (result.contains("false")) {

            } else if (result.contains("true")) {
                getFragmentManager().popBackStack();

            }
        }

    }
}
