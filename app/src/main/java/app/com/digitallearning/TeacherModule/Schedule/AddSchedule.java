package app.com.digitallearning.TeacherModule.Schedule;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.andexert.library.RippleView;

import java.util.Calendar;

import app.com.digitallearning.R;
import app.com.digitallearning.Utill.LogMessage;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 2/4/16.
 */
public class AddSchedule extends FragmentActivity {
    // View rootview;
    static final int TIME_DIALOG_ID = 1111;
    ;
    SharedPreferences preferences;
    String timeId, userid, cla_classid;
    ProgressDialog dlg;
    int displayId;
    EditText loc;
    int shouldempty;
    RelativeLayout reladdsch, relative_header;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    RelativeLayout hori, hori1;
    RippleView ripple_edit_save;
    TextView day, description, starttime, endtime, selectedday, descrptn;
    public static String scheduledescription, selecteday;
    String srday, srdescription, srtime, srendtime, srsthour, newstarthours, srstmin, srendhr, srendmin, newendhours, srlocation, srdayid, ampm, endampm;
    String[] srstarthour = {"ghshx", "vxyh"};
    String[] srendhour;
    int sttime, entime;
    final CharSequence[] items = {
            "Monday", "Tuesday", "Wednesday", "Thrusday", "Friday", "Saturday", "Sunday", "Every Day", "Every Weekday"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_schedule);
        loc = (EditText) findViewById(R.id.loc);
        selectedday = (TextView) findViewById(R.id.selectedday);
        hori = (RelativeLayout) findViewById(R.id.hori);
        hori1 = (RelativeLayout) findViewById(R.id.hori1);
        descrptn = (TextView) findViewById(R.id.descrptn);
        day = (TextView) findViewById(R.id.day);


        relative_header = (RelativeLayout) findViewById(R.id.relative_header);
        imageButtonZoomIn = (ImageButton) findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) findViewById(R.id.img_zoom_out);
        reladdsch = (RelativeLayout) findViewById(R.id.reladdsch);


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


        description = (TextView) findViewById(R.id.description);
        starttime = (TextView) findViewById(R.id.starttime);
        endtime = (TextView) findViewById(R.id.endtime);
        dlg = new ProgressDialog(AddSchedule.this);
        ripple_edit_save = (RippleView) findViewById(R.id.ripple_edit_save);
        shouldempty = getIntent().getIntExtra("shouldempty", 0);
        Log.e("shouldempty", "" + shouldempty);
        if (shouldempty == 2) {
            selecteday = " ";
            scheduledescription = " ";


        }
        preferences = PreferenceManager.getDefaultSharedPreferences(AddSchedule.this);

        userid = preferences.getString("Sch_Mem_id", "");
        Log.e("userid", "" + userid);

        cla_classid = preferences.getString("cla_classid", "");
        Log.e("cla_classid", "" + cla_classid);

        try {
            timeId = getIntent().getStringExtra("timeID");
            Log.e("timeId", "" + timeId);
        } catch (Exception e) {
        }


        ripple_edit_save.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {


                srday = selectedday.getText().toString();
                Log.e("srday", "" + srday);

                if (srday.contains("Monday")) {
                    srdayid = "1";
                    Log.e("srdayid", "" + srdayid);
                } else if (srday.contains("Tuesday")) {
                    srdayid = "2";
                } else if (srday.contains("Wednesday")) {
                    srdayid = "3";
                } else if (srday.contains("Thursday")) {
                    srdayid = "4";
                } else if (srday.contains("Friday")) {
                    srdayid = "5";
                } else if (srday.contains("Saturday")) {
                    srdayid = "6";
                } else if (srday.contains("Sunday")) {
                    srdayid = "7";
                } else if (srday.contains("Every Day")) {
                    srdayid = "8";
                } else if (srday.contains("Every Weekday")) {
                    srdayid = "9";
                }

                srdescription = descrptn.getText().toString();
                Log.e("srdescription", "" + srdescription);

                srtime = starttime.getText().toString();
                Log.e("srtime", "" + srtime);

                srstarthour = srtime.split(":");

                srstarthour[0] = srstarthour[0].trim();
                srsthour = srstarthour[0];
                Log.e("ssrsthour", "" + srsthour);

                try {
                    srstarthour[1] = srstarthour[1].trim();
                    srstmin = srstarthour[1];
                    Log.e("srstmin", "" + srstmin);
                } catch (Exception e) {
                }

                try {


                sttime = Integer.parseInt(srsthour);
                Log.e("intsttime", "" + sttime);
                } catch (Exception e) {
                }
                try {

                ampm = srstmin.substring(Math.max(srstmin.length() - 2, 0));
                Log.e("ampm", "" + ampm);
            } catch (Exception e) {
            }
               try {
                   if (ampm.contains("PM")) {
                       Log.e("Visit", "VIST");
                       int hor = Integer.parseInt(srsthour);
                       newstarthours = String.valueOf(hor + 12);
                       Log.e("plus", "" + newstarthours);
                       srsthour = newstarthours;

                   }
               } catch (Exception e) {
               }


                try{
                srendtime = endtime.getText().toString();
                } catch (Exception e) {
                }
                try{
                srendhour = srendtime.split(":");

                srendhour[0] = srendhour[0].trim();
                srendhr = srendhour[0];
                } catch (Exception e) {
                }
                //   Log.e("srendhr",""+srendhr);
                try {
                    srendhour[1] = srendhour[1].trim();
                    srendmin = srendhour[1];
                    Log.e("srendmin", "" + srendmin);
                } catch (Exception e) {
                }
                try {

                    entime = Integer.parseInt(srendhr);
                    Log.e("entime", "" + entime);
                } catch (Exception e) {
                }

                try {
                    endampm = srendmin.substring(Math.max(srendmin.length() - 2, 0));
                    Log.e("endampm", "" + endampm);
                } catch (Exception e) {
                }



                try{
                if (endampm.contains("PM")) {
                    Log.e("Visit", "VIST");
                    int hor = Integer.parseInt(srendhr);
                    newendhours = String.valueOf(hor + 12);
                    Log.e("plus", "" + newendhours);
                    srendhr = newendhours;
                }
                } catch (Exception e) {
                }
                srlocation = loc.getText().toString();
                Log.e("srlocation", "" + srlocation);
                if (srday.equals(" ")) {


                    LogMessage.showDialog(AddSchedule.this, null,
                            "Please select day", "OK");
                } else if (srdescription.equals(" ")) {

                    LogMessage.showDialog(AddSchedule.this, null,
                            "Please select description", "OK");
                } else if (srtime.equalsIgnoreCase("")) {

Log.e("DAY",""+srday);
                    LogMessage.showDialog(AddSchedule.this, null,
                            "Please select start time", "OK");
                } else if (srendtime.equalsIgnoreCase("")) {

                    LogMessage.showDialog(AddSchedule.this, null,
                            "Please select end time", "OK");
                    Log.e("issttime", "" + sttime);
                    Log.e("issttime", "" + sttime);

                } else if (sttime > entime) {
                    LogMessage.showDialog(AddSchedule.this, null,
                            "Please select valid time", "OK");
                } else {
                    Log.e("eintsttime", "" + sttime);
                    Log.e("eintentime", "" + entime);
                    Log.e("newstarthours", "" + newstarthours);
                    Log.e("newendhours", "" + newendhours);


                    /*if(srdayid==null){
                        LogMessage.showDialog(getApplicationContext(), null,
                                "Please fill required Fields" , "OK");
                    }
                    else if(srsthour==null){
                        LogMessage.showDialog(getApplicationContext(), null,
                                "Please fill required Fields" , "OK");
                    }
                    else if(srstmin==null){
                        LogMessage.showDialog(getApplicationContext(), null,
                                "Please fill required Fields" , "OK");
                    }
                    else if(srendhr==null){
                        LogMessage.showDialog(getApplicationContext(), null,
                                "Please fill required Fields" , "OK");
                    }
                    else if(srendmin==null){
                        LogMessage.showDialog(getApplicationContext(), null,
                                "Please fill required Fields" , "OK");
                    }
                    else if(srdescription==null){
                        LogMessage.showDialog(getApplicationContext(), null,
                                "Please fill required Fields" , "OK");
                    }
                    else{*/

                    new add_Schedule().execute(cla_classid, userid, srdayid, srsthour, srstmin, srendhr, srendmin, srlocation, srdescription);}
              // }
            }
        });

        try {
            timeId = getIntent().getStringExtra("timeID");
            Log.e("timeId", "" + timeId);
        } catch (Exception e) {
        }


        hori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /**
                 *  Custom Alert Dialog ...
                 */
                AlertDialog.Builder builder = new AlertDialog.Builder(AddSchedule.this);
                builder.setTitle("Make your selection");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        //  mDoneButton.setText(items[item]);
                        Log.e("items[item]", "" + items[item]);
                        selecteday = String.valueOf(items[item]);
                        Log.e("selecteday", "" + selecteday);
                        selectedday.setText(selecteday);
                        srday = selectedday.getText().toString();
                        Log.e("srday", "" + srday);
                        if (srday.contains("Monday")) {
                            srdayid = "1";
                        } else if (srday.contains("Tuesday")) {
                            srdayid = "2";
                        } else if (srday.contains("Wednesday")) {
                            srdayid = "3";
                        } else if (srday.contains("Thursday")) {
                            srdayid = "4";
                        } else if (srday.contains("Friday")) {
                            srdayid = "5";
                        } else if (srday.contains("Saturday")) {
                            srdayid = "6";
                        } else if (srday.contains("Sunday")) {
                            srdayid = "7";
                        } else if (srday.contains("Every Day")) {
                            srdayid = "8";
                        } else if (srday.contains("Every Weekday")) {
                            srdayid = "9";
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
                /*FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ScheduleDescriptionFragment scheduleDescriptionFragment = new ScheduleDescriptionFragment();
                fragmentTransaction.replace(R.id.container, scheduleDescriptionFragment).addToBackStack(null);
                fragmentTransaction.commit();*/
                int addsch = 10;
                Intent gotodesp = new Intent(AddSchedule.this, ScheduleDescriptionFragment.class);
                gotodesp.putExtra("textDescription", descrptn.getText().toString());
                gotodesp.putExtra("addsch", addsch);
                startActivity(gotodesp);
                finish();
            }
        });

        Log.e("scheduledescription", "" + scheduledescription);

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


    class add_Schedule extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.add_Schedule(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7], params[8]);

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

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(AddSchedule.this);
                alertDialog.setMessage("Syllabus inserted").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();


                                Intent gotoschedule = new Intent(AddSchedule.this, ScheduleFragment.class);
                                startActivity(gotoschedule);
                                finish();


                            }
                        });

                AlertDialog dialog = alertDialog.create();
                dialog.show();
                TextView messageText = (TextView) dialog
                        .findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER);


            } else if (result.contains("false")) {
                Toast.makeText(AddSchedule.this, "Wrong User", Toast.LENGTH_SHORT).show();

            }
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
        descrptn.setText(scheduledescription);
        Log.e("selectedayonstart", "" + selecteday);
        selectedday.setText(selecteday);
    }

    @Override
    public void onPause() {
        super.onPause();
        descrptn.setText(scheduledescription);
        Log.e("selectedayonpause", "" + selecteday);
        selectedday.setText(selecteday);
        //selecteday=" ";
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
            starttime.setText(pad(Integer.parseInt(hours)) + ":" + pad(Integer.parseInt(min)) + " " + AM_PM);


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
            endtime.setText(pad(Integer.parseInt(hours)) + ":" + pad(Integer.parseInt(min)) + " " + AM_PM);

        }


    }

    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        reladdsch.setPivotX(pivot.x);
        reladdsch.setPivotY(pivot.y);
        reladdsch.setScaleX(scaleX);
        reladdsch.setScaleY(scaleY);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }
}
