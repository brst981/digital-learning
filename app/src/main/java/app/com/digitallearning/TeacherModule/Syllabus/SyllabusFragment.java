package app.com.digitallearning.TeacherModule.Syllabus;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.ClassActivity;
import app.com.digitallearning.TeacherModule.Classes.ClassesDetailFragment;
import app.com.digitallearning.Utill.LogMessage;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 1/27/16.
 */
public class SyllabusFragment extends Fragment{
    View rootview;
    RippleView ripple_edit_save,ripple_edit_delete;
    SharedPreferences preferences;
    String Sch_Mem_id,cla_classid,sy_id,textupdate,textsave,srtitle,srdescription;
    EditText sylbsTitle,des;
    String title,description;
    ProgressDialog dlg;
    Button savebutton;
    RelativeLayout teacherlogin;
    ImageButton imageButtonZoomIn, imageButtonZoomOut,back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_syllabus, container, false);
        imageButtonZoomIn = (ImageButton)rootview. findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton)rootview. findViewById(R.id.img_zoom_out);
        back=(ImageButton)rootview.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ClassesDetailFragment classesDetailFragment = new ClassesDetailFragment();
                fragmentTransaction.replace(R.id.container, classesDetailFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        des=(EditText)rootview.findViewById(R.id.des);
        savebutton=(Button)rootview.findViewById(R.id.savebutton);
        sylbsTitle=(EditText)rootview.findViewById(R.id.sylbsTitle);
        ripple_edit_delete=(RippleView)rootview.findViewById(R.id.ripple_edit_delete);
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
                textsave=savebutton.getText().toString();
                if(textsave.contains("Save")){
                    ripple_edit_save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(title.isEmpty()){
                                LogMessage.showDialog(getActivity(), null,
                                        "Please enter title", "OK");
                            }
                          else if(description.isEmpty()){
                                LogMessage.showDialog(getActivity(), null,
                                        "Please enter description", "OK");
                            }
                            else{
                            new Add_syllabus().execute(cla_classid , Sch_Mem_id , title , description );
                        }}
                    });

                }



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
            Log.e("Add_syllabusAPI", "" + result);

            if (result.contains("true")) {

             /*   LogMessage.showDialog(getActivity(), null,
                        "Class successfully deleted", "OK");*/

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Syllabus inserted").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                               /*Intent deletetoclass=new Intent(getActivity(),ClassActivity.class);
                                startActivity(deletetoclass);
                                getActivity().finish();*/
                              //  getFragmentManager().popBackStackImmediate();
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
//{"success":false,"data":null}
             /*   LogMessage.showDialog(getActivity(), null,
                        "Class successfully deleted", "OK");*/

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





            } else if (result.contains("true")) {
                updateTeacherLogIn(result);

                ripple_edit_delete.setVisibility(View.VISIBLE);
                ripple_edit_delete.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        new Delete_syllabus().execute(sy_id);
                    }
                });
                savebutton.setText("Update");
                textupdate=savebutton.getText().toString();
                Log.e("textupdate",""+textupdate);
                if(textupdate.contains("Update")){
                    ripple_edit_save.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            srtitle=sylbsTitle.getText().toString();
                            srdescription=des.getText().toString();
                            new Update_syllabus().execute(cla_classid,Sch_Mem_id,srtitle,srdescription,sy_id);
                        }
                    });

                }

            /*    JSONObject obj=new JSONObject();
                String data=obj.getString("data");
                Log.e("data",""data);*/

                // Toast.makeText(getActivity(), "Wrong User", Toast.LENGTH_SHORT).show();

            }
        }
        private void updateTeacherLogIn(String success) {

            try {

                JSONObject jsonObject = new JSONObject(success);

//[{"sy_id":"196","syllabus":"cbshjd","title":"new"}]
                //  LogMessage.showDialog(getActivity(), null, "" +data, "OK");
                JSONArray arr = jsonObject.getJSONArray("data");
                Log.e("arr", " " + arr);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                     sy_id = obj.getString("sy_id");
                    Log.e("sy_id", "" + sy_id);
                    String syllabus = obj.getString("syllabus");
                    Log.e("syllabus", "" + syllabus);
                    String title = obj.getString("title");
                    Log.e("title", "" + title);
                    sylbsTitle.setText(title);
                    des.setText(syllabus);
                }

            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class Update_syllabus extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Update_Syllabus(params[0],params[1],params[2],params[3],params[4]);

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
            Log.e("Update_syllabusAPI", "" + result);

            if (result.contains("true")) {
                /*LogMessage.showDialog(getActivity(), null,
                        "Syllabus updated", "OK");*/
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Syllabus updated").setCancelable(false)
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


            }
        }
        }
        class Delete_syllabus extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Delete_syllabus(params[0]);

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
            Log.e("DeletesyllabusAPI", "" + result);

            if (result.contains("true")) {
                sylbsTitle.setText(" ");
                des.setText(" ");
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Syllabus updated").setCancelable(false)
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
            }}}


    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        teacherlogin.setPivotX(pivot.x);
        teacherlogin.setPivotY(pivot.y);
        teacherlogin.setScaleX(scaleX);
        teacherlogin.setScaleY(scaleY);
    }
}
