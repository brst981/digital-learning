package app.com.digitallearning.TeacherModule.Schedule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.andexert.library.RippleView;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 2/4/16.
 */
public class ScheduleDescriptionFragment  extends FragmentActivity {
    View rootview;
    RippleView ripple_edit_save;
    EditText scheduledesc;
    int viewsch,addsch;
    String desp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_desc_fragment);

        scheduledesc=(EditText)findViewById(R.id.scheduledesc);
        ripple_edit_save=(RippleView)findViewById(R.id.ripple_edit_save);


        viewsch=getIntent().getIntExtra("viewsch",0);
        addsch=getIntent().getIntExtra("addsch",0);
        desp=getIntent().getStringExtra("desp");
        scheduledesc.setText(desp);

        ripple_edit_save.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                ViewSchedule.scheduledescription=scheduledesc.getText().toString();
                AddSchedule.scheduledescription=scheduledesc.getText().toString();
                Log.e("schduledescription",""+AddSchedule.scheduledescription);
               // getFragmentManager().popBackStackImmediate();
                if ((addsch==10)){
                    Intent backadsch=new Intent(ScheduleDescriptionFragment.this,AddSchedule.class);
                    startActivity(backadsch);
                    finish();
                }
                else if(viewsch==12){
                    Intent backadsch=new Intent(ScheduleDescriptionFragment.this,ViewSchedule.class);
                    startActivity(backadsch);
                    finish();
                }
            }
        });



    }



}
