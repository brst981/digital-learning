package app.com.digitallearning.TeacherModule.Curriculum;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Classes.TopicFragment;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${ShalviSharma} on 12/21/15.
 */
public class CurriculumFragment extends Fragment {
    View rootview;
    TextView headerTitle;
    String title;
    SharedPreferences preferences;
    String Sch_Mem_id,cla_classid,textupdate,textsave;
    ProgressDialog dlg;
    CharSequence[] mStringArray,a,b,c;
    ArrayList<String> countryList;
    RelativeLayout relative_topic,relative_teacher_country;;
    Button button_create;
    int id=1;
    EditText text_title_curriculum,edt_state_curriculum,edt_organization_curriculum;
    TextView topic,txtcountry,text_input_Description,txtlibrary,gradefrom,gradeto;
    RippleView ripple_create,ripple_teacher_country,ripple_teacher_Description,ripple_teacher_schedule,ripple_teacher_syllabus,ripple_GradeTo,ripple_edit_delete;
    final CharSequence[] items = {
            "India", "Australia", "Canada","America","Newzealand","abcd","Sunday","Every Day","Every Weekday"};

    final CharSequence[] items1 = {
            "Other", "K", "1","2","3","4","5","6","7","8","9","10","11","12","HigherEd"};
    final CharSequence[] items2 = {
            "Other", "K", "1","2","3","4","5","6","7","8","9","10","11","12","HigherEd"};
    final CharSequence[] library = {"Personal","School","Cummunity"};
    // CharSequence[] charSequenceItems;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_teacher_curriculum, container, false);
        text_title_curriculum=(EditText)rootview.findViewById(R.id.text_title_curriculum);
        ripple_create=(RippleView)rootview.findViewById(R.id.ripple_create);
        edt_state_curriculum=(EditText)rootview.findViewById(R.id.edt_state_curriculum);
        edt_organization_curriculum=(EditText)rootview.findViewById(R.id.edt_organization_curriculum);
        topic=(TextView)rootview.findViewById(R.id.topic);
        txtcountry=(TextView)rootview.findViewById(R.id.txtcountry);
        text_input_Description=(TextView)rootview.findViewById(R.id.text_input_Description);
        txtlibrary=(TextView)rootview.findViewById(R.id.txtlibrary);
        gradefrom=(TextView)rootview.findViewById(R.id.gradefrom);
        gradeto=(TextView)rootview.findViewById(R.id.gradeto);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        dlg=new ProgressDialog(getActivity());
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        countryList=new ArrayList<>();
        Sch_Mem_id=preferences.getString("Sch_Mem_id","");
        Log.e("Sch_Mem_id",""+Sch_Mem_id);

        cla_classid=preferences.getString("cla_classid","");
        Log.e("cla_classid",""+cla_classid);
        new Get_carriculum().execute(cla_classid , Sch_Mem_id );
        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        relative_topic=(RelativeLayout)rootview.findViewById(R.id.relative_topic);
        ripple_teacher_country=(RippleView)rootview.findViewById(R.id.ripple_teacher_country);
        ripple_edit_delete=(RippleView)rootview.findViewById(R.id.ripple_edit_delete);
        button_create=(Button)rootview.findViewById(R.id.button_create);
        ripple_teacher_schedule=(RippleView)rootview.findViewById(R.id.ripple_teacher_schedule);
        ripple_teacher_Description=(RippleView)rootview.findViewById(R.id.ripple_teacher_Description);
        ripple_GradeTo=(RippleView)rootview.findViewById(R.id.ripple_GradeTo);
        ripple_teacher_syllabus=(RippleView)rootview.findViewById(R.id.ripple_teacher_syllabus);




        ripple_create.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                textsave=button_create.getText().toString();
                if(textsave.contains("Save")) {

                }

            }
        });

        ripple_GradeTo.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Make your selection");
                builder.setItems(items2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        //  mDoneButton.setText(items[item]);
                        Log.e("items[item]",""+items2[item]);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });



        relative_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TopicFragment topicFragment = new TopicFragment();
                Bundle bundle=new Bundle();
                bundle.putInt("id",id);
                fragmentTransaction.replace(R.id.container, topicFragment).addToBackStack(null);
                topicFragment.setArguments(bundle);
                fragmentTransaction.commit();
            }
        });


        ripple_teacher_schedule.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Make your selection");
                builder.setItems(library, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        //  mDoneButton.setText(items[item]);
                        Log.e("items[item]",""+library[item]);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
               /* FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LibraryFragment libraryFragment = new LibraryFragment();
                fragmentTransaction.replace(R.id.container, libraryFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });*/

        ripple_teacher_syllabus.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Make your selection");
                builder.setItems(items1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        //  mDoneButton.setText(items[item]);
                        Log.e("items[item]",""+items1[item]);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

   //     new Country_list().execute();


        ripple_teacher_country.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Make your selection");

                builder.setItems(mStringArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        //  mDoneButton.setText(items[item]);
                        Log.e("items[item]",""+items[item]);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

               /* final Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.country_dialog);
                dialog.setCancelable(false);
                 TextView country=(TextView)dialog.findViewById(R.id.country);
               // country.setText(countryList);
                dialog.show();*/
            }
        });

        ripple_teacher_Description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CurriculumDescription curriculumDescription = new CurriculumDescription();
                fragmentTransaction.replace(R.id.container, curriculumDescription).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        initData();

        return rootview;
    }

    private void initData() {

        headerTitle.setText("Curriculum");
    }




    class Country_list extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Country_list();

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
                updatecountrylist(result);

            } else if (result.contains("false")) {
                Toast.makeText(getActivity(), "Wrong User", Toast.LENGTH_SHORT).show();

            }}

        private void updatecountrylist(String success) {

            try {

                JSONObject jsonObject = new JSONObject(success);
                Log.e("jsonObject", "" + jsonObject);


                JSONArray arr = jsonObject.getJSONArray("data");
               // Log.e("arr", " " + arr);
                countryList.add(String.valueOf(arr));
              //  Log.e("String.valueOf(arr)",""+String.valueOf(arr));
              //  Log.e("countryList",""+countryList);
                /*for (int i = 0; i < arr.length(); i++) {


                }*/
              //charSequenceItems = countryList.toArray(new CharSequence[countryList.size()]);
                Log.e("countryListreceived",""+countryList);

                mStringArray = countryList.toArray(new CharSequence[countryList.size()]);

                for(int i = 0; i < mStringArray.length ; i++){
                    Log.e("stringis",(String)mStringArray[i]);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }











    class Get_carriculum extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Get_curriculum(params[0],params[1]);

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

                        new Delete_carriculum().execute(cla_classid , Sch_Mem_id );
                    }
                });
                button_create.setText("Update");
               textupdate=button_create.getText().toString();
                Log.e("textupdate",""+textupdate);
          /*      if(textupdate.contains("Update")){
                    ripple_create.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            new Update_syllabus().execute(cla_classid,Sch_Mem_id,srtitle,srdescription,sy_id);
                        }
                    });

                }*/

            /*    JSONObject obj=new JSONObject();
                String data=obj.getString("data");
                Log.e("data",""data);*/

                // Toast.makeText(getActivity(), "Wrong User", Toast.LENGTH_SHORT).show();

            }
        }
        private void updateTeacherLogIn(String success) {

            try {
//[{"title":"Ggg","desc":"G\n","hi_age":"7","organisation":"f","state":"g","library":"1","topic_name":"Training ",
// "lo_age":"2","topic_id":"12","curid":"225","country":"8","cur_class_id":"1869"}]
                JSONObject jsonObject = new JSONObject(success);
                JSONArray arr = jsonObject.getJSONArray("data");
                Log.e("arr", " " + arr);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                   String title = obj.getString("title");
                    Log.e("title", "" + title);
                    String desc = obj.getString("desc");
                    Log.e("desc", "" + desc);
                    String hi_age = obj.getString("hi_age");
                    Log.e("hi_age", "" + hi_age);
                    String organisation = obj.getString("organisation");
                    Log.e("organisation", "" + organisation);
                    String state = obj.getString("state");
                    Log.e("state", "" + state);
                    String library = obj.getString("library");
                    Log.e("library", "" + library);
                    String topic_name = obj.getString("topic_name");
                    Log.e("topic_name", "" + topic_name);
                    String lo_age = obj.getString("lo_age");
                    Log.e("lo_age", "" + lo_age);
                    String topic_id = obj.getString("topic_id");
                    Log.e("topic_id", "" + topic_id);
                    String curid = obj.getString("curid");
                    Log.e("curid", "" + curid);
                    String country = obj.getString("country");
                    Log.e("country", "" + country);
                    String cur_class_id = obj.getString("cur_class_id");
                    Log.e("cur_class_id", "" + cur_class_id);

                    text_title_curriculum.setText(title);
                    text_input_Description.setText(desc);
                    gradefrom.setText(hi_age);
                    edt_organization_curriculum.setText(organisation);
                    edt_state_curriculum.setText(state);
                    txtlibrary.setText(library);
                    topic.setText(topic_name);
                    txtcountry.setText(country);
                }

            }catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

















    class Delete_carriculum extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Delete_Curriculum(params[0],params[1]);

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
                text_title_curriculum.setText(" ");
                text_input_Description.setText(" ");
                gradefrom.setText(" ");
                edt_organization_curriculum.setText(" ");
                edt_state_curriculum.setText(" ");
                txtlibrary.setText(" ");
                topic.setText(" ");
                txtcountry.setText(" ");

            } else if (result.contains("false")) {


            }
        }

    }
    }

