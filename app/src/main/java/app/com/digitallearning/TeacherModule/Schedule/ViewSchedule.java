package app.com.digitallearning.TeacherModule.Schedule;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
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
import app.com.digitallearning.Utill.LogMessage;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 2/19/16.
 */
public class ViewSchedule extends FragmentActivity {
    SharedPreferences preferences;
    String timeId, userid, cla_classid,desp;
    ProgressDialog dlg;
    int displayId;
    EditText loc;
    RelativeLayout hori, hori1;
    RippleView rippleupdate;
    TextView day, description, starttime, endtime,selectedday,descrptn;
    public static String scheduledescription,selecteday;
    String srday,srdescription,srtime,srendtime,srsthour,srstmin,srendhr,srendmin,srlocation,srdayid;
    String[] srstarthour,srendhour;
    int sttime,entime;
    final CharSequence[] items = {
            "Monday", "Tuesday", "Wednesday", "Thrusday", "Friday", "Saturday", "Sunday", "Every Day", "Every Weekday"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_schedule);
        preferences = PreferenceManager.getDefaultSharedPreferences(ViewSchedule.this);

        userid = preferences.getString("Sch_Mem_id", "");
        Log.e("userid", "" + userid);

        cla_classid = preferences.getString("cla_classid", "");
        Log.e("cla_classid", "" + cla_classid);

        try {
            timeId = getIntent().getStringExtra("timeID");
            Log.e("timeId", "" + timeId);
        } catch (Exception e) {
        }


        loc = (EditText) findViewById(R.id.loc);
        selectedday = (TextView) findViewById(R.id.selectedday);
        hori = (RelativeLayout) findViewById(R.id.hori);
        day = (TextView) findViewById(R.id.day);
        description = (TextView) findViewById(R.id.description);
        starttime = (TextView) findViewById(R.id.starttime);
        endtime = (TextView) findViewById(R.id.endtime);
        rippleupdate = (RippleView) findViewById(R.id.rippleupdate);
        rippleupdate.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {


                srday = selectedday.getText().toString();
                Log.e("srday", "" + srday);
                if (srday.contains("Monday")) {
                    srdayid = "1";
                    Log.e("srdayid", "" + srdayid);
                } else if (srday.contains("Tuesday")) {
                    srdayid = "2";
                    Log.e("srdayid", "" + srdayid);
                } else if (srday.contains("Wednesday")) {
                    srdayid = "3";
                    Log.e("srdayid", "" + srdayid);
                } else if (srday.contains("Thursday")) {
                    srdayid = "4";
                    Log.e("srdayid", "" + srdayid);
                } else if (srday.contains("Friday")) {
                    srdayid = "5";
                    Log.e("srdayid", "" + srdayid);
                } else if (srday.contains("Saturday")) {
                    srdayid = "6";
                    Log.e("srdayid", "" + srdayid);
                } else if (srday.contains("Sunday")) {
                    srdayid = "7";
                    Log.e("srdayid", "" + srdayid);
                } else if (srday.contains("Every Day")) {
                    srdayid = "8";
                    Log.e("srdayid", "" + srdayid);
                } else if (srday.contains("Every Weekday")) {
                    srdayid = "9";
                    Log.e("srdayid", "" + srdayid);
                }

                srdescription = descrptn.getText().toString();
                Log.e("srdescription", "" + srdescription);

                srtime = starttime.getText().toString();
                Log.e("srtime", "" + srtime);

                srstarthour = srtime.split(":");

                srstarthour[0] = srstarthour[0].trim();
                srsthour = srstarthour[0];
                Log.e("ssrsthour", "" + srsthour);

                srstarthour[1] = srstarthour[1].trim();
                srstmin = srstarthour[0];
                Log.e("srstmin", "" + srstmin);

                srendtime = endtime.getText().toString();

                srendhour = srendtime.split(":");

                srendhour[0] = srendhour[0].trim();
                srendhr = srendhour[0];
                Log.e("srendhr", "" + srendhr);

                srendhour[1] = srendhour[1].trim();
                srendmin = srendhour[1];
                Log.e("srendmin", "" + srendmin);

                srlocation = loc.getText().toString();
                Log.e("srlocation", "" + srlocation);


                Log.e("srdayid..",""+srdayid);
                if(srday.equalsIgnoreCase(" ")){

                    LogMessage.showDialog(ViewSchedule.this, null,
                            "Please select day", "OK");
                }
                else  if(srdescription.equalsIgnoreCase(" ")){

                    LogMessage.showDialog(ViewSchedule.this, null,
                            "Please select description", "OK");
                }
                else if(srtime.equalsIgnoreCase("")){




                    LogMessage.showDialog(ViewSchedule.this, null,
                            "Please select start time", "OK");
                }
                else  if(srendtime.equalsIgnoreCase("")){

                    LogMessage.showDialog(ViewSchedule.this, null,
                            "Please select end time", "OK");
                    Log.e("issttime",""+sttime);
                    Log.e("issttime",""+sttime);

                }

                else   if(sttime > entime){
                    LogMessage.showDialog(ViewSchedule.this, null,
                            "Please select valid time", "OK");
                }
                new update_Schedule().execute(cla_classid, userid, srdayid, srsthour, srstmin, srendhr, srendmin, timeId, srlocation, srdescription);
            }
        });
        hori1 = (RelativeLayout) findViewById(R.id.hori1);
        descrptn = (TextView) findViewById(R.id.descrptn);

        dlg = new ProgressDialog(ViewSchedule.this);
        try {
            timeId = getIntent().getStringExtra("timeID");
            Log.e("timeId", "" + timeId);
        } catch (Exception e) {
        }


        new Before_Schedule_listing().execute(userid, cla_classid, timeId);

        hori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /**
                 *  Custom Alert Dialog ...
                 */
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewSchedule.this);
                builder.setTitle("Make your selection");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        //  mDoneButton.setText(items[item]);
                        Log.e("items[item]", "" + items[item]);
                        selecteday = String.valueOf(items[item]);
                        Log.e("selecteday", "" + selecteday);
                        selectedday.setText(selecteday);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


            }


        });
        hori1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ScheduleDescriptionFragment scheduleDescriptionFragment = new ScheduleDescriptionFragment();
                fragmentTransaction.replace(R.id.container, scheduleDescriptionFragment).addToBackStack(null);
                fragmentTransaction.commit();*/
                int viewsch=12;
                desp=descrptn.getText().toString();
                Intent gotodesp=new Intent(ViewSchedule.this,ScheduleDescriptionFragment.class);
                gotodesp.putExtra("viewsch",viewsch);
                gotodesp.putExtra("desp",desp);
                startActivity(gotodesp);
                finish();
            }
        });

        Log.e("scheduledescription", "" + scheduledescription);
        //     descrptn.setText(scheduledescription);
        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });
        endtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker1();
            }
        });


    }



    class Before_Schedule_listing extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Before_Edit_Schedule(params[0], params[1], params[2]);

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

                updateTeacherLogIn(result);


            } else if (result.contains("false")) {
                Toast.makeText(ViewSchedule.this, "Wrong User", Toast.LENGTH_SHORT).show();

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

                    if (selecteday == null) {
                        Log.e("selectedaynull", "" + selecteday);

                        if (day1.contains("1")) {
                            selectedday.setText("Monday");
                        } else if (day1.contains("2")) {
                            selectedday.setText("Tuesday");
                        } else if (day1.contains("3")) {
                            selectedday.setText("Wednesday");
                        } else if (day1.contains("4")) {
                            selectedday.setText("Thrusday");
                        } else if (day1.contains("5")) {
                            selectedday.setText("Friday");
                        } else if (day1.contains("6")) {
                            selectedday.setText("Saturday");
                        }else if (day1.contains("7")) {
                            selectedday.setText("Sunday");
                        }
                        else if (day1.contains("8")) {
                            selectedday.setText("Every Day");
                        }
                        else if (day1.contains("9")) {
                            selectedday.setText("Every WeekDay");
                        }
                    } else {
                        selectedday.setText(selecteday);
                    }
                    if(scheduledescription==null){
                    descrptn.setText(Description);}
                    else{
                        descrptn.setText(scheduledescription);
                    }

                    //showTimePicker();
                    starttime.setText(pad(Integer.parseInt(Str_Hour)) + ":" + pad(Integer.parseInt(Str_Min)) );
                    endtime.setText(pad(Integer.parseInt(En_Hour)) + ":" + pad(Integer.parseInt(En_Min))  );
                   /* starttime.setText(Str_Hour + ":" + Str_Min);
                    endtime.setText(En_Hour + ":" + En_Min);*/
                    loc.setText(Cls_Location);
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }




        class update_Schedule extends AsyncTask<String, Integer, String> {


            @Override
            protected String doInBackground(String... params) {

                return WSConnector.update_Schedule(params[0], params[1], params[2],params[3],params[4],params[5],params[6],params[7],params[8],params[9]);

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

                    /*FragmentManager fragmentManager = getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    ScheduleFragment scheduleFragment = new ScheduleFragment();
                    fragmentTransaction.replace(R.id.container, scheduleFragment).addToBackStack(null);
                    fragmentTransaction.commit();*/

                    Intent gotoschedule=new Intent(ViewSchedule.this, ScheduleActivity.class);
                    startActivity(gotoschedule);
                    finish();
                } else if (result.contains("false")) {
                    Toast.makeText(ViewSchedule.this, "Wrong User", Toast.LENGTH_SHORT).show();

                }
            }
        }





    protected void showTimePicker() {
        TimePickerFragmentStartTime newFragment = new TimePickerFragmentStartTime();
        newFragment.show(getFragmentManager(), "timePicker");
    }




    public class TimePickerFragmentStartTime extends android.app.DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            String AM_PM = "";

            Calendar datetime = Calendar.getInstance();
            datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            datetime.set(Calendar.MINUTE, minute);

            if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                AM_PM = "AM";
            else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                AM_PM = "PM";

            String hours = (datetime.get(Calendar.HOUR) == 0) ? "12" : datetime
                    .get(Calendar.HOUR) + "";
            String min = (datetime.get(Calendar.MINUTE) == 0) ? "12" : datetime
                    .get(Calendar.MINUTE) + "";



            //    starttime.setText(hours + " : " + min + AM_PM);
            starttime.setText(pad(Integer.parseInt(hours)) + ":" + pad(Integer.parseInt(min)) + AM_PM );

        }


    }


    protected void showTimePicker1() {
        TimePickerFragmentEndTime newFragment = new TimePickerFragmentEndTime();
        newFragment.show(getFragmentManager(), "timePicker");
    }




    public class TimePickerFragmentEndTime extends android.app.DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // Do something with the time chosen by the user
            String AM_PM = "";

            Calendar datetime = Calendar.getInstance();
            datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
            datetime.set(Calendar.MINUTE, minute);

            if (datetime.get(Calendar.AM_PM) == Calendar.AM)
                AM_PM = "AM";
            else if (datetime.get(Calendar.AM_PM) == Calendar.PM)
                AM_PM = "PM";

            String hours = (datetime.get(Calendar.HOUR) == 0) ? "12" : datetime
                    .get(Calendar.HOUR) + "";
            String min = (datetime.get(Calendar.MINUTE) == 0) ? "12" : datetime
                    .get(Calendar.MINUTE) + "";



            //    starttime.setText(hours + " : " + min + AM_PM);
            endtime.setText(pad(Integer.parseInt(hours)) + ":" + pad(Integer.parseInt(min)) + AM_PM );

        }


    }
    private static String pad(int c) {
        if (c >= 10)
            return String.valueOf(c);
        else
            return "0" + String.valueOf(c);
    }

    @Override
    public void onStart() {
        super.onStart();
       // selecteday=" ";
        Log.e("selectedayonstart",""+selecteday);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("selectedayonpause",""+selecteday);
        //selecteday=" ";
    }


}
