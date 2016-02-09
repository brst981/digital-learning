package app.com.digitallearning.TeacherModule.Schedule;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 2/4/16.
 */
public class AddSchedule extends Fragment {
    View rootview;
    TextView day,description,starttime,endtime;
    final CharSequence[] items = {
            "Monday", "Tuesday", "Wednesday","Thrusday","Friday","Saturday","Sunday","Every Day","Every Weekday"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_schedule, container, false);
        registerResources(rootview);

        day.setOnClickListener(new View.OnClickListener() {
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
                        Log.e("items[item]",""+items[item]);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }


        });
        description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ScheduleDescriptionFragment scheduleDescriptionFragment = new ScheduleDescriptionFragment();
                fragmentTransaction.replace(R.id.container, scheduleDescriptionFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


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
                        starttime.setText( selectedHour + ":" + selectedMinute);
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
                        endtime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });


        return rootview;
    }

    public void registerResources(View rootview){

        day=(TextView)rootview.findViewById(R.id.day);
        description=(TextView)rootview.findViewById(R.id.description);
        starttime=(TextView)rootview.findViewById(R.id.starttime);
        endtime=(TextView)rootview.findViewById(R.id.endtime);
    }

}
