package app.com.digitallearning.TeacherModule.Schedule;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.andexert.library.RippleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;

import app.com.digitallearning.R;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 2/4/16.
 */
public class AddSchedule extends Fragment {
    View rootview;
    SharedPreferences preferences;
    String timeId, userid, cla_classid;
    ProgressDialog dlg;
    int displayId;
    EditText loc;
    RelativeLayout hori, hori1;
    RippleView ripple_edit_save;
    TextView day, description, starttime, endtime,selectedday,descrptn;
    public static String scheduledescription,selecteday;
    String srday,srdescription,srtime,srendtime,srsthour,srstmin,srendhr,srendmin,srlocation,srdayid;
    String[] srstarthour,srendhour;
    final CharSequence[] items = {
            "Monday", "Tuesday", "Wednesday", "Thrusday", "Friday", "Saturday", "Sunday", "Every Day", "Every Weekday"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_schedule, container, false);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        userid = preferences.getString("Sch_Mem_id", "");
        Log.e("userid", "" + userid);

        cla_classid = preferences.getString("cla_classid", "");
        Log.e("cla_classid", "" + cla_classid);

try {
    timeId = getArguments().getString("timeID");
    Log.e("timeId", "" + timeId);
}
catch (Exception e){}


        loc=(EditText)rootview.findViewById(R.id.loc);
        selectedday=(TextView)rootview.findViewById(R.id.selectedday);
        hori=(RelativeLayout)rootview.findViewById(R.id.hori);

        ripple_edit_save=(RippleView)rootview.findViewById(R.id.ripple_edit_save);
        ripple_edit_save.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {


                srday=selectedday.getText().toString();
                Log.e("srday",""+srday);


                srdescription=descrptn.getText().toString();
                Log.e("srdescription",""+srdescription);

                srtime= starttime.getText().toString();
                Log.e("srtime",""+srtime);

                srstarthour=srtime.split(":");

                srstarthour[0]=srstarthour[0].trim();
                srsthour=srstarthour[0];
                Log.e("ssrsthour",""+srsthour);

                srstarthour[1]=srstarthour[1].trim();
                srstmin=srstarthour[0];
                Log.e("srstmin",""+srstmin);

                srendtime=endtime.getText().toString();

                srendhour=srendtime.split(":");

                srendhour[0]=srendhour[0].trim();
                srendhr=srendhour[0];
                Log.e("srendhr",""+srendhr);

                srendhour[1]=srendhour[1].trim();
                srendmin=srendhour[1];
                Log.e("srendmin",""+srendmin);

                srlocation=  loc.getText().toString();
                Log.e("srlocation",""+srlocation);


                new add_Schedule().execute(cla_classid,userid,srdayid,srsthour,srstmin,srendhr,srendmin,srlocation,srdescription);
            }
        });
        hori1=(RelativeLayout)rootview.findViewById(R.id.hori1);
        descrptn=(TextView)rootview.findViewById(R.id.descrptn);
        registerResources(rootview);
        dlg=new ProgressDialog(getActivity());
        try {
            timeId = getArguments().getString("timeID");
            Log.e("timeId", "" + timeId);
        }
        catch (Exception e){}


        hori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /**
                 *  Custom Alert Dialog ...
                 */
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Make your selection");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        //  mDoneButton.setText(items[item]);
                        Log.e("items[item]", "" + items[item]);
                        selecteday= String.valueOf(items[item]);
                        Log.e("selecteday", "" + selecteday);
                        selectedday.setText(selecteday);
                        srday=selectedday.getText().toString();
                        Log.e("srday",""+srday);
                        if(srday.contains("Monday")){
                            srdayid="1";}
                        else if(srday.contains("Tuesday")){
                            srdayid="2";
                        }
                        else if(srday.contains("Wednesday")){
                            srdayid="3";
                        }
                        else if(srday.contains("Thursday")){
                            srdayid="4";
                        }
                        else if(srday.contains("Friday")){
                            srdayid="5";
                        }
                        else if(srday.contains("Saturday")){
                            srdayid="6";
                        }
                        else if(srday.contains("Sunday")){
                            srdayid="7";
                        }
                        else if(srday.contains("Every Day")){
                            srdayid="8";
                        }
                        else if(srday.contains("Every Weekday")){
                            srdayid="9";
                        }




                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


            }


        });
        hori1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ScheduleDescriptionFragment scheduleDescriptionFragment = new ScheduleDescriptionFragment();
                fragmentTransaction.replace(R.id.container, scheduleDescriptionFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        Log.e("scheduledescription",""+scheduledescription);

        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        starttime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endtime.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        return rootview;
    }

    public void registerResources(View rootview) {

        day = (TextView) rootview.findViewById(R.id.day);
        description = (TextView) rootview.findViewById(R.id.description);
        starttime = (TextView) rootview.findViewById(R.id.starttime);
        endtime = (TextView) rootview.findViewById(R.id.endtime);
    }

    class add_Schedule extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.add_Schedule(params[0], params[1], params[2],params[3],params[4],params[5],params[6],params[7],params[8]);

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

               // updateTeacherLogIn(result);

                selecteday=" ";
                scheduledescription=" ";
                srendtime=" ";
                srtime=" ";
                srlocation=" ";
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ScheduleFragment scheduleFragment = new ScheduleFragment();
                fragmentTransaction.replace(R.id.container, scheduleFragment).addToBackStack(null);
                fragmentTransaction.commit();

            } else if (result.contains("false")) {
                Toast.makeText(getActivity(), "Wrong User", Toast.LENGTH_SHORT).show();

            }
        }


        private void updateTeacherLogIn(String success) {

            try {

                JSONObject jsonObject = new JSONObject(success);
                Log.e("jsonObject", "" + jsonObject);


                JSONArray arr = jsonObject.getJSONArray("data");
                Log.e("arr", " " + arr);
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Log.e("obj", "" + obj);

                    String day1 = obj.getString("day");
                    Log.e("day", "" + day1);

                    String Str_Hour = obj.getString("Str_Hour");
                    Log.e("Str_Hour", "" + Str_Hour);

                    String Str_Min = obj.getString("Str_Min");
                    Log.e("Str_Min", "" + Str_Min);

                    String En_Hour = obj.getString("En_Hour");
                    Log.e("En_Hour", "" + En_Hour);

                    String En_Min = obj.getString("En_Min");
                    Log.e("En_Min", "" + En_Min);

                    String Cls_Location = obj.getString("Cls_Location");
                    Log.e("Cls_Location", "" + Cls_Location);


                    String Description = obj.getString("Description");
                    Log.e("Description", "" + Description);



                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public void onStart() {
        super.onStart();
        // selecteday=" ";
        descrptn.setText(scheduledescription);
        Log.e("selectedayonstart",""+selecteday);
        selectedday.setText(selecteday);
    }

    @Override
    public void onPause() {
        super.onPause();
        descrptn.setText(scheduledescription);
        Log.e("selectedayonpause",""+selecteday);
        selectedday.setText(selecteday);
        //selecteday=" ";
    }
}
