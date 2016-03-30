package app.com.digitallearning.TeacherModule.Curriculum;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.andexert.library.RippleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Classes.TopicFragment;
import app.com.digitallearning.Utill.GlobalClass;
import app.com.digitallearning.Utill.LogMessage;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${ShalviSharma} on 12/21/15.
 */
public class CurriculumFragment extends Fragment {

    public static final String TOPIC_DATA = "topic_data";

    View rootview;
    TextView headerTitle;
    String title, value;
    SharedPreferences preferences;
    String Sch_Mem_id, cla_classid, textupdate, textsave, stindividualId, libid, nwstring;
    ProgressDialog dlg;
    String ab, ba;
    CharSequence[] mStringArray, a, b, c;
    ArrayList<String> countryList, countrydatalist;
    RelativeLayout relative_topic, relative_teacher_country;
    ;
    Button button_create;
    int id = 2;
    int id1 = 1;
    int n, p;
    String x, y, z, libID, location;
    int curiid;
    String[] strArr;
    String countryid, curid, topic_id;
    int item1;
    int individualId;
    JSONArray arr;
    String st, strstring;
    int i1;
    int idi;
    String gradefromId, gradetoId;
    String strcountry;
    RelativeLayout teacherlogin;
    ImageButton imageButtonZoomIn, imageButtonZoomOut, back;
    public static String curriculumtopic, curriculumtopicid, curriculuncountry, curriculumdes, curriculumlib, curriculumgradefrom, curriculumgradeto;
    EditText text_title_curriculum, edt_state_curriculum, edt_organization_curriculum;
    TextView topic, txtcountry, text_input_Description, txtlibrary, gradefrom, gradeto;
    String addsrtitle, addsrstate, addsrorganization, addsrtopic, addsrcountry, addsrdescription, addsrlibrary, addsrgradefrom, addsrgradeto;
    RippleView ripple_create, ripple_teacher_country, ripple_teacher_Description, ripple_teacher_schedule, ripple_teacher_syllabus, ripple_GradeTo, ripple_edit_delete;
    final CharSequence[] items = {
            "India", "Australia", "Canada", "America", "Newzealand", "abcd", "Sunday", "Every Day", "Every Weekday"};

    final String[] items1 = {
            "Other", "K", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "HigherEd"};
    final String[] items2 = {
            "Other", "K", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "HigherEd"};
    final String[] library = {"Personal", "School", "Cummunity"};

    // CharSequence[] charSequenceItems;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_teacher_curriculum, container, false);
        text_title_curriculum = (EditText) rootview.findViewById(R.id.text_title_curriculum);
        ripple_create = (RippleView) rootview.findViewById(R.id.ripple_create);
        edt_state_curriculum = (EditText) rootview.findViewById(R.id.edt_state_curriculum);
        edt_organization_curriculum = (EditText) rootview.findViewById(R.id.edt_organization_curriculum);
        topic = (TextView) rootview.findViewById(R.id.topic);
        txtcountry = (TextView) rootview.findViewById(R.id.txtcountry);
        text_input_Description = (TextView) rootview.findViewById(R.id.text_input_Description);
        txtlibrary = (TextView) rootview.findViewById(R.id.txtlibrary);
        gradefrom = (TextView) rootview.findViewById(R.id.gradefrom);
        gradeto = (TextView) rootview.findViewById(R.id.gradeto);

        imageButtonZoomIn = (ImageButton) rootview.findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) rootview.findViewById(R.id.img_zoom_out);
        back = (ImageButton) rootview.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ClassesDetailFragment classesDetailFragment = new ClassesDetailFragment();
                fragmentTransaction.replace(R.id.container, classesDetailFragment).addToBackStack(null);
                fragmentTransaction.commit();*/
                getFragmentManager().popBackStackImmediate();
                // getActivity().getFragmentManager().popBackStackImmediate();
            }
        });
        /*AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");*/
        dlg = new ProgressDialog(getActivity());
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        countryList = new ArrayList<>();
        countrydatalist = new ArrayList<>();
        Sch_Mem_id = preferences.getString("Sch_Mem_id", "");
        Log.e("Sch_Mem_id", "" + Sch_Mem_id);

        cla_classid = preferences.getString("cla_classid", "");
        Log.e("cla_classid", "" + cla_classid);


        //   headerTitle = (TextView) activity.findViewById(R.id.mytext);
        relative_topic = (RelativeLayout) rootview.findViewById(R.id.relative_topic);
        ripple_teacher_country = (RippleView) rootview.findViewById(R.id.ripple_teacher_country);
        ripple_edit_delete = (RippleView) rootview.findViewById(R.id.ripple_edit_delete);
        button_create = (Button) rootview.findViewById(R.id.button_create);
        ripple_teacher_schedule = (RippleView) rootview.findViewById(R.id.ripple_teacher_schedule);
        ripple_teacher_Description = (RippleView) rootview.findViewById(R.id.ripple_teacher_Description);
        ripple_GradeTo = (RippleView) rootview.findViewById(R.id.ripple_GradeTo);
        ripple_teacher_syllabus = (RippleView) rootview.findViewById(R.id.ripple_teacher_syllabus);
        // new Country_list().execute();

// title , topic , desc , library ,String lo_age ,String hi_age ,String organization ,String country ,String state

        ripple_create.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                textsave = button_create.getText().toString();
                Log.e("textsave", "" + textsave);

                addsrtitle = text_title_curriculum.getText().toString();
                Log.e("textsave", "" + textsave);

                addsrstate = edt_state_curriculum.getText().toString();
                Log.e("addsrtitle", "" + addsrtitle);

                addsrorganization = edt_organization_curriculum.getText().toString();
                Log.e("addsrorganization", "" + addsrorganization);

                addsrtopic = topic.getText().toString();
                Log.e("addsrtopic", "" + addsrtopic);

                addsrcountry = txtcountry.getText().toString();
                Log.e("addsrcountry", "" + addsrcountry);

                addsrdescription = text_input_Description.getText().toString();
                Log.e("addsrdescription", "" + addsrdescription);

                addsrlibrary = txtlibrary.getText().toString();
                Log.e("addsrlibrary", "" + addsrlibrary);


                if (addsrlibrary.equalsIgnoreCase("Personal")) {
                    libid = "1";
                } else if (addsrlibrary.equalsIgnoreCase("School")) {
                    libid = "2";
                } else if (addsrlibrary.equalsIgnoreCase("Cummunity")) {
                    libid = "3";
                }
                Log.e("libid", "" + libid);
                addsrgradefrom = gradefrom.getText().toString();


                if (addsrgradefrom.equals("K")) {
                    gradefromId = "1";
                } else if (addsrgradefrom.equals("1")) {
                    gradefromId = "2";
                } else if (addsrgradefrom.equals("2")) {
                    gradefromId = "3";
                } else if (addsrgradefrom.equals("3")) {
                    gradefromId = "4";
                } else if (addsrgradefrom.equals("4")) {
                    gradefromId = "5";
                } else if (addsrgradefrom.equals("5")) {
                    gradefromId = "6";
                } else if (addsrgradefrom.equals("6")) {
                    gradefromId = "7";
                } else if (addsrgradefrom.equals("7")) {
                    gradefromId = "8";
                } else if (addsrgradefrom.equals("8")) {
                    gradefromId = "9";
                } else if (addsrgradefrom.equals("9")) {
                    gradefromId = "10";
                } else if (addsrgradefrom.equals("10")) {
                    gradefromId = "11";
                } else if (addsrgradefrom.equals("11")) {
                    gradefromId = "12";
                } else if (addsrgradefrom.equals("12")) {
                    gradefromId = "13";
                } else if (addsrgradefrom.equals("HigherEd")) {
                    gradefromId = "14";
                }
                addsrgradeto = gradeto.getText().toString();


                if (addsrgradeto.equals("K")) {
                    gradetoId = "1";
                } else if (addsrgradeto.equals("1")) {
                    gradetoId = "2";
                } else if (addsrgradeto.equals("2")) {
                    gradetoId = "3";
                } else if (addsrgradeto.equals("3")) {
                    gradetoId = "4";
                } else if (addsrgradeto.equals("4")) {
                    gradetoId = "5";
                } else if (addsrgradeto.equals("5")) {
                    gradetoId = "6";
                } else if (addsrgradeto.equals("6")) {
                    gradetoId = "7";
                } else if (addsrgradeto.equals("7")) {
                    gradetoId = "8";
                } else if (addsrgradeto.equals("8")) {
                    gradetoId = "9";
                } else if (addsrgradeto.equals("9")) {
                    gradetoId = "10";
                } else if (addsrgradeto.equals("10")) {
                    gradetoId = "11";
                } else if (addsrgradeto.equals("11")) {
                    gradetoId = "12";
                } else if (addsrgradeto.equals("12")) {
                    gradetoId = "13";
                } else if (addsrgradeto.equals("HigherEd")) {
                    gradetoId = "14";
                }


                Log.e("gradefromId", "" + gradefromId);

                Log.e("gradetoId", "" + gradetoId);

                //   addsrgradeto=gradeto.getText().toString();
                //  Log.e("addsrgradeto",""+addsrgradeto);

                if (textsave.contains("Save")) {

                    if (addsrtitle == null) {
                        LogMessage.showDialog(getActivity(), null,
                                "Please fill required Fields", "OK");
                    } else if (curriculumtopicid == null) {
                        LogMessage.showDialog(getActivity(), null,
                                "Please fill required Fields", "OK");
                    } else if (addsrdescription == null) {
                        LogMessage.showDialog(getActivity(), null,
                                "Please fill required Fields", "OK");
                    } else if (libid == null) {
                        LogMessage.showDialog(getActivity(), null,
                                "Please fill required Fields", "OK");
                    } else if (gradefromId == null) {
                        LogMessage.showDialog(getActivity(), null,
                                "Please fill required Fields", "OK");
                    }
                    else if(countryid==null){
                        LogMessage.showDialog(getActivity(), null,
                                "Please fill required Fields", "OK");
                    }
                    else if(gradetoId==null){
                        LogMessage.showDialog(getActivity(), null,
                                "Please fill required Fields", "OK");
                    }
                    else {
                        new Add_curriculum().execute(cla_classid, Sch_Mem_id, addsrtitle, curriculumtopicid, addsrdescription, libid, gradefromId, gradetoId, addsrorganization, countryid, addsrstate);
                    }
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
                        Log.e("items[item]", "" + items2[item]);
                        curriculumgradeto = items2[item];
                        gradeto.setText(curriculumgradeto);


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
                Bundle bundle = new Bundle();
                bundle.putInt("id1", id);
                if (topic.getText().toString().trim().length() > 0)
                    bundle.putString(TOPIC_DATA, topic.getText().toString());

                Log.e("presstopic", "" + topic.getText().toString());
                // bundle.putInt("id",id1);
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
                        Log.e("items[item]", "" + library[item]);
                        curriculumlib = library[item];
                        item1 = item;
                        Log.e("item1", "" + item1);
                        txtlibrary.setText(curriculumlib);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        ripple_teacher_syllabus.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Make your selection");
                builder.setItems(items1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        //  mDoneButton.setText(items[item]);
                        Log.e("items[item]", "" + items1[item]);
                        curriculumgradefrom = items1[item];
                        gradefrom.setText(curriculumgradefrom);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        ripple_teacher_country.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Make your selection");

                builder.setItems(strArr, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        //  mDoneButton.setText(items[item]);
                        Log.e("strArr", "" + strArr[item]);
                        curriculuncountry = strArr[item];
                        Log.e("Position", "" + item);
                        countryid = String.valueOf(item + 1);
                        Log.e("countryid", "" + countryid);
                        txtcountry.setText(curriculuncountry);
                        Log.e("addcurriculuncountry", "" + curriculuncountry);

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

        ripple_teacher_Description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CurriculumDescription curriculumDescription = new CurriculumDescription();
                Bundle bundle = new Bundle();
                bundle.putString("textDescription", text_input_Description.getText().toString());
                fragmentTransaction.replace(R.id.container, curriculumDescription).addToBackStack(null);
                curriculumDescription.setArguments(bundle);
                fragmentTransaction.commit();
            }
        });
//        initData();


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

   /* private void initData() {

        headerTitle.setText("Curriculum");
    }*/


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
            Log.e("REsulTCountry_list", "" + result);
            if (result.contains("true")) {
                updatecountrylist(result);

            } else if (result.contains("false")) {
                Toast.makeText(getActivity(), "Wrong User", Toast.LENGTH_SHORT).show();

            }
        }

        private void updatecountrylist(String success) {

            try {

                JSONObject jsonObject = new JSONObject(success);
                arr = jsonObject.getJSONArray("data");

                strArr = new String[arr.length()];
                for (i1 = 0; i1 < arr.length(); i1++) {
                    strArr[i1] = arr.getString(i1);
                    Log.e("wwstrArr[i1]", "" + strArr[i1]);


                   /* st= (String) arr.get(i1);
                    Log.e("st",""+st);
*/
                    Log.e("arr.get(i1);", "" + arr.get(i1));


                    individualId = i1 + 1;
                    stindividualId = String.valueOf(individualId);
                    Log.e("individualId", "" + individualId);
                    Log.e("stindividualId", "" + stindividualId);
                    if (idi == 10) {
                        Log.e("receivedstrcountry", "" + strcountry);
                        Log.e("fourth", "" + arr.get(Integer.parseInt(strcountry)));

                        int str = Integer.parseInt(strcountry);
                        z = String.valueOf(str - 1);
                        x = String.valueOf(arr.get(Integer.parseInt(z)));
                        Log.e("nvjh", "" + x);

                        if (curriculumtopic != null) {

                            txtcountry.setText(curriculuncountry);

                        }
                        if (curriculumdes != null) {
                            txtcountry.setText(curriculuncountry);
                        } else {

                            txtcountry.setText(x);
                        }
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    class Get_carriculum extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Get_curriculum(params[0], params[1]);

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
            //   try{
            dlg.dismiss();//}
            //   catch (Exception e){}
            Log.e("Get_syllabusAPI", "" + result);

            if (result.contains("false")) {
                // curriculumtopic=" ";
                // topic.setText("");


//{"success":false,"data":null}
             /*   LogMessage.showDialog(getActivity(), null,
                        "Class successfully deleted", "OK");*/


                if (curriculuncountry != null) {
                    Log.e("received", "" + curriculuncountry);
                    txtcountry.setText(curriculuncountry);
                }

                if (curriculumlib != null) {
                    Log.e("received", "" + curriculuncountry);
                    txtlibrary.setText(curriculumlib);
                }

                if (curriculumgradefrom != null) {
                    Log.e("received", "" + curriculuncountry);
                    gradefrom.setText(curriculumgradefrom);
                }

                if (curriculumgradeto != null) {
                    Log.e("received", "" + curriculuncountry);
                    gradeto.setText(curriculumgradeto);
                }

                if (curriculumtopic != null) {
                    Log.e("curriculumtopicnotnull", "" + curriculumtopic);
                    topic.setText(curriculumtopic);


                }
                if (curriculumdes != null) {
                    Log.e("curriculumdesnotnull", "" + curriculumdes);

                    text_input_Description.setText(curriculumdes);
                   /* txtcountry.setText(curriculuncountry);
                    txtlibrary.setText(curriculumlib);
                    gradeto.setText(curriculumgradeto);
                    gradefrom.setText(curriculumgradefrom);
                    topic.setText(curriculumtopic);*/


                }

                try {

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
                                    getFragmentManager().popBackStack();

                                }
                            });

                    AlertDialog dialog = alertDialog.create();


                    if (curriculumdes == null & curriculumtopic == null) {
                        Log.e("curriculumdescnull", "" + curriculumdes);
                        dialog.show();
                        TextView messageText = (TextView) dialog
                                .findViewById(android.R.id.message);
                        messageText.setGravity(Gravity.CENTER);

                    } else {
                        dialog.dismiss();
                    }
                } catch (Exception e) {
                }
            } else if (result.contains("true")) {
                idi = 10;
                updateTeacherLogIn(result);


                button_create.setText("Update");
                textupdate = button_create.getText().toString();
                Log.e("serviccurriculumtopicid", "" + curriculumtopicid);


                ripple_create.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        Log.e("textupdateget", "" + textupdate);
                        Log.e("countryid", "" + countryid);
                        textsave = button_create.getText().toString();
                        Log.e("textsave", "" + textsave);

                        addsrtitle = text_title_curriculum.getText().toString();
                        Log.e("textsave", "" + textsave);

                        addsrstate = edt_state_curriculum.getText().toString();
                        Log.e("addsrtitle", "" + addsrtitle);

                        addsrorganization = edt_organization_curriculum.getText().toString();
                        Log.e("addsrorganization", "" + addsrorganization);

                        addsrtopic = topic.getText().toString();
                        Log.e("addsrtopic", "" + addsrtopic);

                        addsrcountry = txtcountry.getText().toString();
                        Log.e("addsrcountry", "" + addsrcountry);

                        addsrdescription = text_input_Description.getText().toString();
                        Log.e("addsrdescription", "" + addsrdescription);

                        addsrlibrary = txtlibrary.getText().toString();
                        Log.e("addsrlibrary", "" + addsrlibrary);
                        if (addsrlibrary.equalsIgnoreCase("Personal")) {
                            libid = "1";
                        } else if (addsrlibrary.equalsIgnoreCase("School")) {
                            libid = "2";
                        } else if (addsrlibrary.equalsIgnoreCase("Cummunity")) {
                            libid = "3";
                        }
                        addsrgradefrom = gradefrom.getText().toString();
                        Log.e("addsrgradefrom", "" + addsrgradefrom);


                        if (addsrgradefrom.equals("K")) {
                            gradefromId = "1";
                        } else if (addsrgradefrom.equals("1")) {
                            gradefromId = "2";
                        } else if (addsrgradefrom.equals("2")) {
                            gradefromId = "3";
                        } else if (addsrgradefrom.equals("3")) {
                            gradefromId = "4";
                        } else if (addsrgradefrom.equals("4")) {
                            gradefromId = "5";
                        } else if (addsrgradefrom.equals("5")) {
                            gradefromId = "6";
                        } else if (addsrgradefrom.equals("6")) {
                            gradefromId = "7";
                        } else if (addsrgradefrom.equals("7")) {
                            gradefromId = "8";
                        } else if (addsrgradefrom.equals("8")) {
                            gradefromId = "9";
                        } else if (addsrgradefrom.equals("9")) {
                            gradefromId = "10";
                        } else if (addsrgradefrom.equals("10")) {
                            gradefromId = "11";
                        } else if (addsrgradefrom.equals("11")) {
                            gradefromId = "12";
                        } else if (addsrgradefrom.equals("12")) {
                            gradefromId = "13";
                        } else if (addsrgradefrom.equals("HigherEd")) {
                            gradefromId = "14";
                        }
                        addsrgradeto = gradeto.getText().toString();


                        if (addsrgradeto.equals("K")) {
                            gradetoId = "1";
                        } else if (addsrgradeto.equals("1")) {
                            gradetoId = "2";
                        } else if (addsrgradeto.equals("2")) {
                            gradetoId = "3";
                        } else if (addsrgradeto.equals("3")) {
                            gradetoId = "4";
                        } else if (addsrgradeto.equals("4")) {
                            gradetoId = "5";
                        } else if (addsrgradeto.equals("5")) {
                            gradetoId = "6";
                        } else if (addsrgradeto.equals("6")) {
                            gradetoId = "7";
                        } else if (addsrgradeto.equals("7")) {
                            gradetoId = "8";
                        } else if (addsrgradeto.equals("8")) {
                            gradetoId = "9";
                        } else if (addsrgradeto.equals("9")) {
                            gradetoId = "10";
                        } else if (addsrgradeto.equals("10")) {
                            gradetoId = "11";
                        } else if (addsrgradeto.equals("11")) {
                            gradetoId = "12";
                        } else if (addsrgradeto.equals("12")) {
                            gradetoId = "13";
                        } else if (addsrgradeto.equals("HigherEd")) {
                            gradetoId = "14";
                        }


                        if (curriculumtopicid == null) {
                            curriculumtopicid = topic_id;
                        }
                        if (countryid == null) {
                            countryid = String.valueOf(strcountry);
                        }

                        if (addsrdescription == null) {
                            addsrdescription = curriculumdes;
                        }

                        new Update_curriculum().execute(cla_classid, Sch_Mem_id, addsrtitle, curriculumtopicid, addsrdescription, libid, gradefromId, gradetoId, addsrorganization, countryid, addsrstate, curid);

                    }
                });

                ripple_edit_delete.setVisibility(View.VISIBLE);
                ripple_edit_delete.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {

                        new Delete_carriculum().execute(curid, Sch_Mem_id);
                    }
                });
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

                    curid = obj.getString("curid");
                    Log.e("curid", "" + curid);
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
                    topic_id = obj.getString("topic_id");
                    Log.e("topic_id", "" + topic_id);
                    String curid = obj.getString("curid");
                    Log.e("curid", "" + curid);
                    strcountry = obj.getString("country");
                    Log.e("strcountry", "" + strcountry);
                    String cur_class_id = obj.getString("cur_class_id");
                    Log.e("cur_class_id", "" + cur_class_id);

                    if (hi_age.equals("1")) {
                        gradeto.setText("K");
                    } else if (hi_age.equals("2")) {
                        gradeto.setText("1");
                    } else if (hi_age.equals("3")) {
                        gradeto.setText("2");
                    } else if (hi_age.equals("4")) {
                        gradeto.setText("3");
                    } else if (hi_age.equals("5")) {
                        gradeto.setText("4");
                    } else if (hi_age.equals("6")) {
                        gradeto.setText("5");
                    } else if (hi_age.equals("7")) {
                        gradeto.setText("6");
                    } else if (hi_age.equals("8")) {
                        gradeto.setText("7");
                    } else if (hi_age.equals("9")) {
                        gradeto.setText("8");
                    } else if (hi_age.equals("10")) {
                        gradeto.setText("9");
                    } else if (hi_age.equals("11")) {
                        gradeto.setText("10");
                    } else if (hi_age.equals("12")) {
                        gradeto.setText("11");
                    } else if (hi_age.equals("13")) {
                        gradeto.setText("12");
                    } else if (hi_age.equals("14")) {
                        gradeto.setText("HigherEd");
                    }
                    if (curriculumtopic != null) {
                        Log.e("ctnotnull", "" + curriculumtopic);
                        topic.setText(curriculumtopic);
                    } else {
                        topic.setText(topic_name);
                    }
                    if (curriculumdes != null) {
                        Log.e("cdnotnull", "" + curriculumdes);

                        text_input_Description.setText(curriculumdes);
                    } else {
                        text_input_Description.setText(desc);
                    }
                    text_title_curriculum.setText(title);


                    //  gradefrom.setText(lo_age);
                    //   gradeto.setText(hi_age);


                    Log.e("ValUE", "" + lo_age);

                    Log.e("ValUEe", "" + hi_age);
                    if (lo_age.equals("1")) {
                        gradefrom.setText("K");
                    } else if (lo_age.equals("2")) {
                        gradefrom.setText("1");
                    } else if (lo_age.equals("3")) {
                        gradefrom.setText("2");
                    } else if (lo_age.equals("4")) {
                        gradefrom.setText("3");
                    } else if (lo_age.equals("5")) {
                        gradefrom.setText("4");
                    } else if (hi_age.equals("6")) {
                        gradefrom.setText("5");
                    } else if (lo_age.equals("7")) {
                        gradefrom.setText("6");
                    } else if (lo_age.equals("8")) {
                        gradefrom.setText("7");
                    } else if (lo_age.equals("9")) {
                        gradefrom.setText("8");
                    } else if (lo_age.equals("10")) {
                        gradefrom.setText("9");
                    } else if (lo_age.equals("11")) {
                        gradefrom.setText("10");
                    } else if (lo_age.equals("12")) {
                        gradefrom.setText("11");
                    } else if (lo_age.equals("13")) {
                        gradefrom.setText("12");
                    } else if (lo_age.equals("14")) {
                        gradefrom.setText("HigherEd");
                    }


                    edt_organization_curriculum.setText(organisation);
                    edt_state_curriculum.setText(state);
                    if (library.contains("1")) {
                        txtlibrary.setText("Personal");
                    } else if (library.contains("2")) {
                        txtlibrary.setText("School");
                    } else {
                        txtlibrary.setText("Cummunity");
                    }


                    txtcountry.setText(strcountry);


                    Log.e("ssindividualId", "" + individualId);


                    //   txtcountry.setText((Integer) arr.get(i1));
//                    Log.e("(Integer) arr.get(i1)",""+(Integer) arr.get(i1));
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class Delete_carriculum extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Delete_Curriculum(params[0], params[1]);

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
            Log.e("Delete_carriculum", "" + result);

            if (result.contains("true")) {
                text_title_curriculum.setText(" ");
                text_input_Description.setText(" ");
                gradefrom.setText(" ");
                gradeto.setText(" ");
                edt_organization_curriculum.setText(" ");
                edt_state_curriculum.setText(" ");
                txtlibrary.setText(" ");
                topic.setText(" ");
                txtcountry.setText(" ");


                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Curriculum deleted").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                                GlobalClass.curriculumvalue = true;
                                getActivity().finish();
                            }
                        });

                AlertDialog dialog = alertDialog.create();
                dialog.show();
                TextView messageText = (TextView) dialog
                        .findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER);


//curriculumtopic,curriculumtopicid,curriculuncountry,curriculumdes,curriculumlib,curriculumgradefrom,curriculumgradeto
            } else if (result.contains("false")) {

            }
        }
    }


    class Add_curriculum extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Add_curriculum(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9], params[10]);

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
            Log.e("Add_curriculumAPI", "" + result);
            curriculumtopic = null;

            curriculumdes = null;
            curriculuncountry = null;
            curriculumlib = null;
            curriculumgradefrom = null;
            curriculumgradeto = null;
            GlobalClass.prefClear = true;
            GlobalClass.lastValue = " ";

            if (result.contains("true")) {

             /*   LogMessage.showDialog(getActivity(), null,
                        "Class successfully deleted", "OK");*/

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Curriculum added").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                               /* Intent deletetoclass = new Intent(getActivity(), ClassActivity.class);
                                startActivity(deletetoclass);
                                getActivity().finish();*/

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
                String data = jsonObject.getString("data");
                Log.e("data", "" + data);
                LogMessage.showDialog(getActivity(), null,
                        "" + data, "OK");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    class Update_curriculum extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Update_curriculum(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8], params[9], params[10], params[11]);

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
            Log.e("Update_curriculumAPI", "" + result);


         /*   GlobalClass.prefClear=true;
            GlobalClass.lastValue=" ";
            curriculumtopic =null;*/

            curriculumdes = null;
            curriculuncountry = null;
            curriculumlib = null;
            curriculumgradefrom = null;
            curriculumgradeto = null;
            GlobalClass.prefClear = true;
            GlobalClass.lastValue = " ";


            if (result.contains("true")) {
                updateTeacherLogIn(result);
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Curriculum successfully upated").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                               /* Intent deletetoclass=new Intent(getActivity(),ClassActivity.class);
                                startActivity(deletetoclass);
                                getActivity().finish();*/
                                //  getFragmentManager().popBackStack();

                                getActivity().finish();


                            }
                        });

                AlertDialog dialog = alertDialog.create();
                dialog.show();
                TextView messageText = (TextView) dialog
                        .findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER);
             /*   LogMessage.showDialog(getActivity(), null,
                        "Class successfully deleted", "OK");*/

               /* AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Syllabus inserted").setCancelable(false)
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
*/


            } else if (result.contains("false")) {
                // updateTeacherLogIn(result);
            /*    JSONObject obj=new JSONObject();
                String data=obj.getString("data");
                Log.e("data",""data);*/

                // Toast.makeText(getActivity(), "Wrong User", Toast.LENGTH_SHORT).show();

            }
        }

        private void updateTeacherLogIn(String success) {

            try {

                JSONObject jsonObject = new JSONObject(success);
                String data = jsonObject.getString("data");
                Log.e("data", "" + data);
                LogMessage.showDialog(getActivity(), null,
                        "" + data, "OK");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        teacherlogin.setPivotX(pivot.x);
        teacherlogin.setPivotY(pivot.y);
        teacherlogin.setScaleX(scaleX);
        teacherlogin.setScaleY(scaleY);
    }

    @Override
    public void onResume() {
        super.onResume();
        curiid = getArguments().getInt("curiid");
        Log.e("curiid", "" + curiid);
        if (curiid == 10) {

            //  curriculumtopic=" ";
            /*curriculumdes="";*/

            try {

                new Get_carriculum().execute(cla_classid, Sch_Mem_id);
            } catch (Exception e) {
            }
            new Country_list().execute();
        }
    }
}

