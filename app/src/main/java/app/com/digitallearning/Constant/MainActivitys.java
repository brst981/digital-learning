/*
package app.com.digitallearning.Constant;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity {

	Context context;
	private Button timeButton, dateButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		context = this;
		timeButton = (Button) findViewById(R.id.button1);
		dateButton = (Button) findViewById(R.id.button2);

		timeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showTimePicker();
			}
		});

		dateButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				showDatePicker();
			}
		});

	}

	protected void showTimePicker() {
		DialogFragment newFragment = new TimePickerFragment();
		newFragment.show(getFragmentManager(), "timePicker");
	}

	protected void showDatePicker() {
		DialogFragment newFragment = new DatePickerFragment();
		newFragment.show(getFragmentManager(), "datePicker");
	}

	public class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(context, this, hour, minute,
					DateFormat.is24HourFormat(context));
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

			timeButton.setText(hours + " : " + minute + " : " + AM_PM);

		}
	}

	public class DatePickerFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		public void onDateSet(DatePicker view, int year, int month, int day) {
			// Do something with the date chosen by the user

			*/
/*
			 * MOnth returned from datepicker is always one less than actual
			 * value for example: December will be 11, January will be 0 and so
			 * on.
			 *//*


			month = month + 1;

			dateButton.setText(day + " - " + month + " - " + year);

		}
	}

}
*/
