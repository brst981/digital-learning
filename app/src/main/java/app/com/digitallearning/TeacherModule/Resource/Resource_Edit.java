package app.com.digitallearning.TeacherModule.Resource;

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
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;

import app.com.digitallearning.R;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 2/3/16.
 */
public class Resource_Edit extends Fragment {
    View rootview;
    TextView headerTitle;
    SharedPreferences preferences;
    String Sch_Mem_id,Mem_Sch_Id,cla_classid, textHeader,title,description,resourceId,updatetitle,updatedescription;
    RippleView ripple_preview_lesson,rippleupdateresource;
    ProgressDialog dlg;
    EditText edt_title_lesson,edt_description_lesson;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.resource_edit, container, false);
        dlg=new ProgressDialog(getActivity());
        edt_title_lesson=(EditText)rootview.findViewById(R.id.edt_title_lesson);
        edt_description_lesson=(EditText)rootview.findViewById(R.id.edt_description_lesson);
        rippleupdateresource=(RippleView)rootview.findViewById(R.id.rippleupdateresource);
        title=getArguments().getString("title");
        description=getArguments().getString("description");
        resourceId=getArguments().getString("resourceId");
        Log.e("resourceId",""+resourceId);
        Log.e("title",""+title);
        edt_title_lesson.setText(title);
        edt_description_lesson.setText(description);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Sch_Mem_id=preferences.getString("Sch_Mem_id","");
        Log.e("Sch_Mem_id",""+Sch_Mem_id);
        Mem_Sch_Id=preferences.getString("Mem_Sch_Id","");
        cla_classid=preferences.getString("cla_classid","");
        Log.e("cla_classid",""+cla_classid);
        ripple_preview_lesson=(RippleView)rootview.findViewById(R.id.ripple_preview_lesson);
        ripple_preview_lesson.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle=new Bundle();
                bundle.putString("title",title);
                bundle.putString("des",description);
                Preview preview = new Preview();
                fragmentTransaction.replace(R.id.container, preview).addToBackStack(null);
                preview.setArguments(bundle);
                fragmentTransaction.commit();
            }
        });
        rippleupdateresource.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                updatetitle=edt_title_lesson.getText().toString();
               updatedescription=edt_description_lesson.getText().toString();
Log.e("updatetitle",""+updatetitle);
                new Update_Resource().execute(cla_classid,Sch_Mem_id,Mem_Sch_Id,updatetitle,updatedescription,resourceId);
            }
        });
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        //   getActivity().getActionBar().setHomeButtonEnabled(false);

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Add Class Resource");
        initData();
        return rootview;

    }

    private void initData() {
        textHeader ="sdhfygsjdgf";

    }
    class Update_Resource extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Update_Resource(params[0], params[1], params[2], params[3], params[4], params[5]);

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
            Log.e("Get_LessonAPI", "" + result);

            if (result.contains("false")) {
                Toast.makeText(getActivity(), "Wrong user", Toast.LENGTH_SHORT).show();

            } else if (result.contains("true")) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Resource updated").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                                getFragmentManager().popBackStack();
//                                FragmentManager fragmentManager = getFragmentManager();
//                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                                LessonFragment lessonFragment = new LessonFragment();
//                                fragmentTransaction.replace(android.R.id.content, lessonFragment).addToBackStack(null);
//                                fragmentTransaction.commit();
                               /* Intent gotoclass=new Intent(getActivity(), ClassActivity.class);
                                startActivity(gotoclass);
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
    }
}
